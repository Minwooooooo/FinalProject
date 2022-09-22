package com.example.demo.Dummy;



import com.example.demo.Entity.Room;
import com.example.demo.Repository.RoomRepository;
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
        Room testRoom1 = Room.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName("나는 공부하기 너무 싫다")
                .category("스크립트")
                .memberCount(4)
                .lock(false)
                .roomPw("")
                .build();
        roomRepository.save(testRoom1);

        Room testRoom2 = Room.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName("나는 생활영어조차 못한다")
                .category("생활영어")
                .memberCount(2)
                .lock(true)
                .roomPw("1234")
                .build();
        roomRepository.save(testRoom2);

        Room testRoom3 = Room.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName("토익 시험비는 너무 비싸다")
                .category("시험대비")
                .memberCount(4)
                .lock(false)
                .roomPw("")
                .build();
        roomRepository.save(testRoom3);

        Room testRoom4 = Room.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName("캠스터디인 척 보미님 노래부르는방")
                .category("스크립트")
                .memberCount(6)
                .lock(true)
                .roomPw("5000")
                .build();
        roomRepository.save(testRoom4);

    }
}

