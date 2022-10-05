package com.example.demo.repository;


import com.example.demo.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<ChatRoom,String> {

    List<ChatRoom> findAll();

    List<ChatRoom> findAllByStatusOrStatus(int status0, int status1);

    ChatRoom findByRoomId(String roomId);
}
