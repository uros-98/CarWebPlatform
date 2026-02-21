package com.asss.zavrsni.rad.repository;

import com.asss.zavrsni.rad.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {

    List<Conversation> findByVehicleId(int vehicleId);

    Optional<Conversation> findByOfferId(int offerId);

    @Query("SELECT c " +
            "FROM Conversation c " +
            "JOIN c.messages m " +
            "WHERE c.vehicle.id = :vehicleId AND m.sender.id IN (:userId1, :userId2) " +
            "GROUP BY c.id HAVING COUNT(DISTINCT m.sender.id) = 2")
    Optional<Conversation> findConversationBetweenUserForVehicle(int vehicleId, int userId1, int userId2);
}
