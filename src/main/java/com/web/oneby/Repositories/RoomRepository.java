package com.web.oneby.Repositories;

import com.web.oneby.Models.Room;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
@EnableJpaRepositories
public interface RoomRepository extends JpaRepository<Room, Long> {
}
