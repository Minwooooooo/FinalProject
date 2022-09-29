package com.example.demo.repository;

import com.example.demo.entity.RoomDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomDetailRepository extends JpaRepository<RoomDetail, Long> {
    Optional<RoomDetail> findByRoomId(String roomId);
}
