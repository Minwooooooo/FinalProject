package com.example.demo.repository.util;

import com.example.demo.entity.member.Member;
import com.example.demo.entity.util.StudyTimer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyTimerRepository extends JpaRepository<StudyTimer, Long> {

    List<StudyTimer> findAllByMember(Member member);

    Optional<StudyTimer> findByRoomId(String roomId);

    Optional<StudyTimer> findByRoomIdAndMember(String roomId, Member member);
}
