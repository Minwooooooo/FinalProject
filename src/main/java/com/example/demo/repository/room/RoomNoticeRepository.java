package com.example.demo.repository.room;

import com.example.demo.entity.room.ChatRoom;
import com.example.demo.entity.room.RoomNotice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomNoticeRepository extends JpaRepository<RoomNotice, Long> {
    Optional<RoomNotice> findByChatRoom(ChatRoom chatRoom);
}
