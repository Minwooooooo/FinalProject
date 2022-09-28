package com.example.demo.repository;

import com.example.demo.entity.ChatRoom;
import com.example.demo.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findByChatRoom(ChatRoom chatRoom);
}
