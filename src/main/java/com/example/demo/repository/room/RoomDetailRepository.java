package com.example.demo.repository.room;

import com.example.demo.entity.room.ChatRoom;
import com.example.demo.entity.member.Member;
import com.example.demo.entity.room.RoomDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomDetailRepository extends JpaRepository<RoomDetail, Long> {
    Optional<RoomDetail> findByChatRoom(ChatRoom chatRoom);

    Optional<RoomDetail> findByEnterMembers(Member member);
}
