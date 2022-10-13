package com.example.demo.repository.util;


import com.example.demo.entity.member.Member;
import com.example.demo.entity.util.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo,Long> {

    List<Memo> findAllByMember(Member member);

    Optional<Memo> findByMemberAndRoomId(Member member, String roomId);
}
