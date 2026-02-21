package com.asss.zavrsni.rad.repository;

import com.asss.zavrsni.rad.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u " +
            "FROM User u " +
            "LEFT JOIN FETCH u.favoriteVehicles " +
            "WHERE u.id = :userId")
    Optional<User> findByIdWithFavorites(int userId);
}