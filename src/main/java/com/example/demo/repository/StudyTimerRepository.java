package com.example.demo.repository;

import com.example.demo.entity.Member;
import com.example.demo.entity.StudyTimer;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StudyTimerRepository extends JpaRepository<StudyTimer, Long> {

    List<StudyTimer> findAllByMember(Member member);

    Optional<StudyTimer> findByRoomId(String roomId);

    Optional<StudyTimer> findByRoomIdAndMember(String roomId, Member member);
}
