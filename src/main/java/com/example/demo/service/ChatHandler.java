package com.example.demo.service;

import com.example.demo.dto.responseDto.MessageDto;
import com.example.demo.entity.ChatMessage;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ChatHandler {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatBotService chatBotService;

    public String getImageByToken(String token) {
        String temp_id = jwtTokenProvider.tempClaim(token).getSubject();
        Long id = Long.valueOf(temp_id);
        Member member = memberRepository.findById(id).get();
        String image =member.getProfileImage();
        return image;
    }

    public MessageDto ChatTypeHandler(ChatMessage chatMessage, String memberName, String image) {
        MessageDto temp_msg = null;
        if (chatMessage.getType().equals(chatMessage.getType().ENTER)) { // websocket 연결요청
            temp_msg = MessageDto.builder()
                    .type(chatMessage.getType().ordinal())
                    .sender("알림")
                    .image("")
                    .msg(memberName+"님 하잉")
                    .build();
            //인원수 +
        }
        else if (chatMessage.getType().equals(chatMessage.getType().QUIT)) { // websocket 연결요청
            temp_msg = MessageDto.builder()
                    .type(chatMessage.getType().ordinal())
                    .sender("알림")
                    .image("")
                    .msg(memberName+"님 빠잉")
                    .build();
            //인원수 -
        }
        else if (chatMessage.getType().equals(chatMessage.getType().TALK)||!chatMessage.getMessage().trim().equals("".trim())) { // websocket 연결요청
            String msg=chatMessage.getMessage();
            if(chatBotService.botCheck(msg)){
                String new_message= chatBotService.botRunner(chatMessage);
                temp_msg = MessageDto.builder()
                        .type(3)
                        .sender("알리미")
                        .image("")
                        .msg(new_message)
                        .build();
                return temp_msg;
            }
            temp_msg = MessageDto.builder()
                    .type(chatMessage.getType().ordinal())
                    .sender(memberName)
                    .image(image)
                    .msg(chatMessage.getMessage())
                    .build();
        }
        return temp_msg;
    }
}
