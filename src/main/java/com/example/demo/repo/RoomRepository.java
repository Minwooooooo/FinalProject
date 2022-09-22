package com.example.demo.repo;


import com.example.demo.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<ChatRoom,String> {
}
