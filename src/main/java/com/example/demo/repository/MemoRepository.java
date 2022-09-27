package com.example.demo.repository;


import com.example.demo.entity.Member;
import com.example.demo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo,String> {

    List<Memo> findAllByMember(Member member);

    Memo findByMemberAndRoomName(Member member, String roomName);
}
