package com.example.demo.repository.room;

import com.example.demo.entity.room.ChatRoom;
import com.example.demo.entity.room.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findByChatRoom(ChatRoom chatRoom);
}
