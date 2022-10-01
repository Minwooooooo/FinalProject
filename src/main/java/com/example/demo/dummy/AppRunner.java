package com.example.demo.dummy;


import com.example.demo.dto.requestDto.CreatRoomRequestDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.ChatRoom;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.RoomRepository;

import com.example.demo.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {

    private final ChatRoomService chatRoomService;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        HttpServletRequest request = null;
        CreatRoomRequestDto roomRequestDto = CreatRoomRequestDto.builder()
                .roomName("생활영어TEST")
                .category("생활영어")
                .maxEnterMember(6)
                .lock(false)
                .roomPw("")
                .build();
        chatRoomService.createChatRoom(roomRequestDto,request);
        roomRequestDto = CreatRoomRequestDto.builder()
                .roomName("캠스터디TEST")
                .category("캠스터디")
                .maxEnterMember(6)
                .lock(false)
                .roomPw("")
                .build();
        chatRoomService.createChatRoom(roomRequestDto,request);
        roomRequestDto = CreatRoomRequestDto.builder()
                .roomName("시험대비TEST")
                .category("시험대비")
                .maxEnterMember(6)
                .lock(false)
                .roomPw("")
                .build();
        chatRoomService.createChatRoom(roomRequestDto,request);
        roomRequestDto = CreatRoomRequestDto.builder()
                .roomName("스크립트TEST")
                .category("스크립트")
                .maxEnterMember(6)
                .lock(false)
                .roomPw("")
                .build();
        chatRoomService.createChatRoom(roomRequestDto,request);




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

