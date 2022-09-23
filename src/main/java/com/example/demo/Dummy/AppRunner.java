package com.example.demo.Dummy;


import com.example.demo.model.ChatRoom;
import com.example.demo.repo.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {

    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
// 스크립트,생활영어,시험대비,캠스터디
        ChatRoom testRoom1 = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName("나는 공부하기 너무 싫다")
                .category("스크립트")
                .maxEnterMember(4)
                .memberCount(0L)
                .lock(false)
                .roomPw("")
                .build();
        roomRepository.save(testRoom1);

        ChatRoom testRoom2 = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName("나는 생활영어조차 못한다")
                .category("생활영어")
                .maxEnterMember(2)
                .memberCount(0L)
                .lock(true)
                .roomPw("1234")
                .build();
        roomRepository.save(testRoom2);

        ChatRoom testRoom3 = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName("토익 시험비는 너무 비싸다")
                .category("시험대비")
                .maxEnterMember(4)
                .memberCount(0L)
                .lock(false)
                .roomPw("")
                .build();
        roomRepository.save(testRoom3);

        ChatRoom testRoom4 = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName("캠스터디인 척 보미님 노래부르는방")
                .category("스크립트")
                .maxEnterMember(6)
                .memberCount(0L)
                .lock(true)
                .roomPw("5000")
                .build();
        roomRepository.save(testRoom4);

    }
}

