const API_URL = "http://localhost:8080";
let allVehicles = [];

// --- POMOĆNE FUNKCIJE ---

function getNormalizedRole() {
    let role = localStorage.getItem('role');
    if (!role) return null;
    return role.startsWith('ROLE_') ? role : 'ROLE_' + role;
}

function getJwtPayload(token) {
    try {
        return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
        return null;
    }
}

// --- AUTH ---

async function login(username, password) {
    try {
        const response = await fetch(`${API_URL}/login/log`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('jwt', data.token);
            localStorage.setItem('username', data.username);
            localStorage.setItem('role', data.role);
            
            const payload = getJwtPayload(data.token);
            if (payload && payload.id) {
                localStorage.setItem('id', payload.id);
            } else if (data.id) {
                localStorage.setItem('id', data.id);
            }
            return true;
        }
        return false;
    } catch (err) {
        console.error("Login error:", err);
        return false;
    }
}

function logout() {
    localStorage.clear();
    window.location.href = 'index.html';
}

// --- VOZILA (READ) ---

async function loadVehicles() {
    const list = document.getElementById('vehicleList');
    if (!list) return;

    try {
        const token = localStorage.getItem('jwt');
        const userId = localStorage.getItem('id');
        const role = getNormalizedRole();

        let endpoint = `${API_URL}/vehicles/get-all-vehicle`;

        if (role === 'ROLE_SELLER') {
            endpoint = `${API_URL}/vehicles/seller/${userId}`;
        }

        const res = await fetch(endpoint, {
            headers: { 'Authorization': `Bearer ${token}` }
        });

        if (res.status === 401 || res.status === 403) {
            logout();
            return;
        }

        allVehicles = await res.json();

        let favoriteIds = [];
        if (role === 'ROLE_BUYER' && userId) {
            const favRes = await fetch(`${API_URL}/favorites/${userId}`, {
                headers: { 'Authorization': `Bearer ${token}` }
            });
            if (favRes.ok) {
                const favs = await favRes.json();
                favoriteIds = favs.map(f => f.id);
            }
        }

        renderVehicles(allVehicles, 'vehicleList', favoriteIds);
    } catch (err) {
        console.error("Error in loadVehicles:", err);
        list.innerHTML = `<div class="alert alert-danger">Error loading vehicles.</div>`;
    }
}

function renderVehicles(listData, targetId = 'vehicleList', favoriteIds = []) {
    const listContainer = document.getElementById(targetId);
    if (!listContainer) return;

    const role = getNormalizedRole();
    const userId = localStorage.getItem('id'); // ID ulogovanog korisnika (String)

    if (listData.length === 0) {
        listContainer.innerHTML = `<div class="col-12 text-center mt-5"><p class="text-muted">No vehicles found.</p></div>`;
        return;
    }

    listContainer.innerHTML = listData.map(v => {
        const hasImages = v.images && v.images.length > 0;
        const imageUrl = hasImages 
            ? `${API_URL}/uploads/${v.images[0].imageUrl}` 
            : `https://placehold.co/400x250?text=No+Image`;

        const vehicleSellerId = v.seller?.id ?? v.sellerId;

        const isOwner = (role === 'ROLE_ADMIN') || 
                (role === 'ROLE_SELLER' && vehicleSellerId != null && vehicleSellerId == userId);

        const isFav = favoriteIds.includes(v.id);
        const heartClass = isFav ? 'bi-heart-fill' : 'bi-heart';

        return `
        <div class="col-md-4 mb-4">
            <div class="card h-100 border-0 shadow-sm">
                <div class="position-absolute top-0 end-0 p-2" style="z-index: 5;">
                    ${role === 'ROLE_BUYER' ? `<i class="bi ${heartClass} fs-4 text-danger ms-2" onclick="toggleFavorite(${v.id})" style="cursor:pointer;"></i>` : ''}
                </div>
                <img src="${imageUrl}" class="card-img-top" style="height:200px; object-fit:cover;">
                <div class="card-body">
                    <h5 class="card-title fw-bold">${v.make} ${v.model}</h5>
                    <p class="text-muted small">${v.year} | ${v.mileage.toLocaleString()} km</p>
                    <p class="text-success fw-bold fs-5">${v.price.toLocaleString()} €</p>
                    <div class="d-grid gap-2">
                        <button class="btn btn-outline-dark btn-sm" onclick="showDetails(${v.id})">Details</button>
                        
                        ${role === 'ROLE_BUYER' ? `<button class="btn btn-primary btn-sm" onclick="makeOffer(${v.id})">Make an Offer</button>` : ''}
                        
                        ${isOwner ? `
                            <div class="d-flex gap-2 mt-2 border-top pt-2">
                                <button class="btn btn-warning btn-sm flex-grow-1" onclick="openEditModal(${v.id})">
                                    <i class="bi bi-pencil"></i> Edit
                                </button>
                                <button class="btn btn-danger btn-sm flex-grow-1" onclick="deleteVehicle(${v.id})">
                                    <i class="bi bi-trash"></i> Delete
                                </button>
                            </div>
                        ` : ''}
                    </div>
                </div>
            </div>
        </div>`;
    }).join('');
}

// --- CRUD OPERACIJE (UPDATE & DELETE) ---

function openEditModal(vehicleId) {
    const v = allVehicles.find(veh => veh.id === vehicleId);
    if (!v) return;

    document.getElementById('edit-id').value = v.id;
    document.getElementById('edit-make').value = v.make;
    document.getElementById('edit-model').value = v.model;
    document.getElementById('edit-year').value = v.year;
    document.getElementById('edit-mileage').value = v.mileage;
    document.getElementById('edit-price').value = v.price;
    document.getElementById('edit-description').value = v.description || '';
    document.getElementById('edit-fuelType').value = v.fuelType || '';
    document.getElementById('edit-transmission').value = v.transmission || '';
    document.getElementById('edit-color').value = v.color || '';
    document.getElementById('edit-engineCapacity').value = v.engineCapacity || '';

    const modalInstance = new bootstrap.Modal(document.getElementById('editVehicleModal'));
    modalInstance.show();
}

async function submitUpdate() {
    const id = document.getElementById('edit-id').value;
    const token = localStorage.getItem('jwt');

    const updateData = {
        make: document.getElementById('edit-make').value,
        model: document.getElementById('edit-model').value,
        year: parseInt(document.getElementById('edit-year').value),
        mileage: parseInt(document.getElementById('edit-mileage').value),
        price: parseFloat(document.getElementById('edit-price').value),
        description: document.getElementById('edit-description').value,
        fuelType: document.getElementById('edit-fuelType').value,
        transmission: document.getElementById('edit-transmission').value,
        color: document.getElementById('edit-color').value,
        engineCapacity: parseFloat(document.getElementById('edit-engineCapacity').value)
    };

    try {
        const res = await fetch(`${API_URL}/vehicles/update-vehicle/${id}`, {
            method: 'PUT',
            headers: { 
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}` 
            },
            body: JSON.stringify(updateData)
        });

        if (res.ok) {
            alert("Vehicle updated successfully!");
            bootstrap.Modal.getInstance(document.getElementById('editVehicleModal')).hide();
            loadVehicles();
        } else {
            alert("Update failed: " + await res.text());
        }
    } catch (err) {
        console.error("Update error:", err);
    }
}

async function deleteVehicle(vehicleId) {
    if (!confirm("Are you sure you want to delete this vehicle?")) return;
    const token = localStorage.getItem('jwt');

    try {
        const res = await fetch(`${API_URL}/vehicles/delete-vehicle/${vehicleId}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${token}` }
        });

        if (res.ok) {
            alert("Vehicle deleted successfully!");
            loadVehicles();
        } else {
            alert("Error deleting vehicle.");
        }
    } catch (err) {
        console.error("Delete error:", err);
    }
}

// --- FAVORITI ---

async function toggleFavorite(vehicleId) {
    const token = localStorage.getItem('jwt');
    const userId = localStorage.getItem('id');
    
    if (!token || !userId) {
        alert("Please log in first!");
        return;
    }

    try {
        const res = await fetch(`${API_URL}/favorites/${userId}/vehicle/${vehicleId}`, {
            method: 'POST',
            headers: { 
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (res.ok) {
            if (window.location.pathname.includes('favorites.html')) {
                loadFavoriteVehicles();
            } else {
                loadVehicles();
            }
        }
    } catch (err) {
        console.error("Error toggling favorite:", err);
    }
}

async function loadFavoriteVehicles() {
    const listContainer = document.getElementById('favoriteVehicleList');
    if (!listContainer) return;

    const userId = localStorage.getItem('id');
    const token = localStorage.getItem('jwt');

    try {
        const res = await fetch(`${API_URL}/favorites/${userId}`, {
            headers: { 'Authorization': `Bearer ${token}` }
        });

        if (res.ok) {
            const favorites = await res.json();
            renderVehicles(favorites, 'favoriteVehicleList');
        }
    } catch (err) {
        console.error("Error loading favorites:", err);
    }
}

// --- PONUDE (OFFERS) ---

async function loadOffers() {
    const table = document.getElementById('offersTable');
    if (!table) return;

    const token = localStorage.getItem('jwt');
    const role = getNormalizedRole();
    const userId = localStorage.getItem('id');

    try {
        let endpoint = `${API_URL}/offers/all`;
        if (role === 'ROLE_SELLER') {
            endpoint = `${API_URL}/offers/seller/${userId}`;
        } 

        const res = await fetch(endpoint, {
            headers: { 'Authorization': `Bearer ${token}` }
        });
        
        if (!res.ok) throw new Error("Could not fetch offers");
        const offers = await res.json();
        
        if (offers.length === 0) {
            table.innerHTML = `<tr><td colspan="5" class="text-center text-muted">No offers found.</td></tr>`;
            return;
        }

        table.innerHTML = offers.map(o => {
            let statusClass = o.status === 'ACCEPTED' ? 'bg-success' : o.status === 'DENIED' ? 'bg-danger' : 'bg-primary';
            const vehicleInfo = o.vehicle ? `${o.vehicle.make} ${o.vehicle.model}` : "N/A";
            const buyerInfo = o.buyer ? o.buyer.username : "User";

            return `
            <tr>
                <td>${vehicleInfo}</td>
                <td>${buyerInfo}</td>
                <td>${o.offerPrice.toLocaleString()} €</td>
                <td><span class="badge ${statusClass}">${o.status}</span></td>
                <td>
                    ${(role === 'ROLE_SELLER') && o.status === 'WAITING' ? `
                        <button class="btn btn-success btn-sm" onclick="changeOfferStatus(${o.id}, 'ACCEPTED')">Accept</button>
                        <button class="btn btn-danger btn-sm" onclick="changeOfferStatus(${o.id}, 'DENIED')">Reject</button>
                    ` : (role === 'ROLE_BUYER' ? '<small>Sent</small>' : '-')}
                </td>
            </tr>`;
        }).join('');
    } catch (err) { 
        console.error("Error loading offers:", err);
        table.innerHTML = `<tr><td colspan="5" class="text-danger">Error loading data.</td></tr>`;
    }
}

async function makeOffer(vehicleId) {
    const amount = prompt("Enter your offer amount (€):");
    if (!amount || isNaN(amount)) return;

    const token = localStorage.getItem('jwt');
    const userId = localStorage.getItem('id');

    try {
        const res = await fetch(`${API_URL}/offers/send-offer`, { 
            method: 'POST',
            headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${token}` },
            body: JSON.stringify({ 
                vehicleId: parseInt(vehicleId), 
                buyerId: parseInt(userId),
                offerPrice: parseFloat(amount),
                message: "Offer sent from website"
            })
        });

        if (res.ok) alert("Offer sent successfully!");
        else alert("Error: " + await res.text());
    } catch (err) { console.error(err); }
}

async function changeOfferStatus(id, status) {
    const token = localStorage.getItem('jwt');
    const sellerId = localStorage.getItem('id');
    const endpoint = (status === 'ACCEPTED') ? 'accept' : 'reject';

    try {
        const res = await fetch(`${API_URL}/offers/${id}/${endpoint}?sellerId=${sellerId}`, {
            method: 'PUT',
            headers: { 'Authorization': `Bearer ${token}` }
        });
        if (res.ok) {
            alert(`Offer ${status.toLowerCase()}!`);
            loadOffers();
        }
    } catch (err) { console.error(err); }
}

// --- NAVIGACIJA I NOTIFIKACIJE ---

function renderNavigationButtons() {
    const actions = document.getElementById('navActions');
    if (!actions) return;

    const role = getNormalizedRole(); 
    let buttons = ''; 

    buttons += `
    <div class="dropdown d-inline-block me-3">
        <button class="btn btn-outline-light position-relative" type="button" 
                data-bs-toggle="dropdown" 
                onclick="markNotificationsAsRead()"> <i class="bi bi-bell fs-5"></i>
            <span id="notif-count" class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger" style="display:none;">
                0
            </span>
        </button>
        <ul class="dropdown-menu dropdown-menu-end shadow" id="notif-list"></ul>
    </div>
    `;

    if (role === 'ROLE_ADMIN') {
        buttons += '<span class="badge bg-danger me-3 p-2">ADMIN</span>';
    }
    
    if (role === 'ROLE_BUYER') {
        buttons += '<a href="favorites.html" class="btn btn-outline-danger btn-sm me-2"><i class="bi bi-heart-fill"></i> My Favorites</a>';
    }
    
    buttons += `<a href="reservations.html" class="btn btn-outline-info btn-sm me-2 text-white">Offers</a>`;
    
    if (role === 'ROLE_SELLER' || role === 'ROLE_ADMIN') {
        buttons += '<a href="add.html" class="btn btn-warning btn-sm me-2">Add Vehicle</a>';
    }
    
    buttons += '<button onclick="logout()" class="btn btn-outline-light btn-sm">Log out</button>';
    actions.innerHTML = buttons;
    loadNotifications();
}

async function loadNotifications() {
    const token = localStorage.getItem('jwt');
    const id = localStorage.getItem('id');
    if (!token || !id) return;

    try {
        const res = await fetch(`${API_URL}/notifications/user/${id}`, {
            headers: { 'Authorization': `Bearer ${token}` }
        });
        if (!res.ok) return;

        const notes = await res.json();
        const list = document.getElementById('notif-list');
        const countBadge = document.getElementById('notif-count');
        if (!list) return;

        const unread = notes.filter(n => !n.read);
        if (countBadge) {
            countBadge.innerText = unread.length;
            countBadge.style.display = unread.length > 0 ? 'block' : 'none';
        }

        list.innerHTML = '<li><h6 class="dropdown-header">Notifications</h6></li>';
        notes.forEach(n => {
            list.innerHTML += `
                <li>
                    <a class="dropdown-item ${n.read ? 'text-muted' : 'fw-bold'}" href="#">
                        <div class="small">${n.title}</div>
                        <div style="font-size:0.75rem;">${n.message}</div>
                    </a>
                </li>`;
        });
    } catch (err) { console.error(err); }
}

async function markNotificationsAsRead() {
    const userId = localStorage.getItem('id');
    const token = localStorage.getItem('jwt');
    const countBadge = document.getElementById('notif-count');

    if (countBadge) countBadge.style.display = 'none';

    try {
        await fetch(`${API_URL}/notifications/user/${userId}/mark-read`, {
            method: 'PUT',
            headers: { 'Authorization': `Bearer ${token}` }
        });
    } catch (err) {
        console.error("Error updating notifications:", err);
    }
}

function showDetails(id) {
    window.location.href = `details.html?id=${id}`;
}

// --- DOM INIT ---

document.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('vehicleList')) loadVehicles();
    if (document.getElementById('favoriteVehicleList')) loadFavoriteVehicles();
    if (document.getElementById('offersTable')) loadOffers();
    
    renderNavigationButtons();
});