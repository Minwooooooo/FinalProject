package com.example.demo.repository.room;


import com.example.demo.entity.room.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,String> {

    List<ChatRoom> findAll();

    List<ChatRoom> findAllByStatusCheckerOrStatusChecker(int status0, int status1);

    ChatRoom findByRoomId(String roomId);

    List<ChatRoom> findAllByStatusChecker(int status);
}
