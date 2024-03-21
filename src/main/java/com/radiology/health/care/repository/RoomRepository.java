package com.radiology.health.care.repository;

import com.radiology.health.care.domain.Room;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Room entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {
    @Query("select room from Room room where room.user.login = ?#{authentication.name}")
    List<Room> findByUserIsCurrentUser();
}
