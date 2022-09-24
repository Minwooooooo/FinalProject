package com.example.demo.repository;


import com.example.demo.Entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<ChatRoom,String> {

    List<ChatRoom> findAll();

    ChatRoom findByRoomId(String roomId);
}
