package com.example.demo.dummy;


import com.example.demo.entity.Category;
import com.example.demo.entity.ChatRoom;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.RoomRepository;

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
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

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

        ChatRoom testRoom5 = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName("캠스터디캠스터디캠스터디캠스터디캠스터디캠스터디")
                .category("캠스터디")
                .maxEnterMember(6)
                .memberCount(0L)
                .lock(false)
                .roomPw("")
                .build();
        roomRepository.save(testRoom5);




// 스크립트,생활영어,시험대비,캠스터디
        Category category1=Category.builder()
                .category("Common Conversation")
                .story("Whyyyyyyyyyyyyyyyyyyyyyy")
                .build();
        categoryRepository.save(category1);

        Category category2=Category.builder()
                .category("Testing")
                .story("For instance, For example?")
                .build();
        categoryRepository.save(category2);

        Category category3=Category.builder()
                .category("Study")
                .story("TOEFL/IELTS, OPI/OPIC, Cambridge Exams, SAT/EBSi 수능")
                .build();
        categoryRepository.save(category3);

        Category category4=Category.builder()
                .category("Script")
                .story("A: Hello\n" +
                        "B: Hi\n" +
                        "A: How are you?\n" +
                        "B: I'm good how are you?\n" +
                        "A: Very well. do you spreak english?\n" +
                        "B: A little. are you ___(ur contury)?\n" +
                        "A: Yes\n" +
                        "B: Where are you from?\n" +
                        "A: I'm from _____(ur city)\n" +
                        "B: Nice to meet you\n" +
                        "A: Nice to meet you too?")
                .build();
        categoryRepository.save(category4);


    }
}

