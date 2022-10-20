package com.example.demo.service.Chat;

import com.example.demo.dto.messageDto.responseDto.ChatMessageResponseDto;
import com.example.demo.entity.room.ChatRoom;
import com.example.demo.entity.room.RoomNotice;
import com.example.demo.repository.room.RoomNoticeRepository;
import com.example.demo.repository.room.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatBotService {
    private final ChatRoomRepository chatRoomRepository;
    private final RoomNoticeRepository roomNoticeRepository;
    /*
    명령어 모음
    !공지 : 공지확인
    !공지등록 : 공지 등록(권한 확인후)
    !
     */
    @Transactional
    public String botRunner(ChatMessageResponseDto chatMessageResponseDto){
        String message= chatMessageResponseDto.getMessage();
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageResponseDto.getRoomId()).get();
        String new_message = null;
        if(message.trim().equals("!공지")){
            String get_notice=getNotice(chatRoom);
            new_message=get_notice;
        }

        else if(message.length()>4&&message.substring(0,5).equals("!공지등록")){
            Optional<RoomNotice> notice= roomNoticeRepository.findByChatRoom(chatRoom);
            String temp_notice=registNotice(message);

            if(notice.isEmpty()){
            RoomNotice newRoomNotice = RoomNotice.builder()
                    .chatRoom(chatRoom)
                    .notice(temp_notice)
                    .build();
            roomNoticeRepository.save(newRoomNotice);
            }
            else {
                notice.get().editNotice(temp_notice);
            }
            new_message="<신규 공지>"+temp_notice;
        }
        else if(message.trim().equals("!명령어")){
            new_message="<명령어>\n" +
                    "[일반 명령어] :\n"+
                    " !공지 : 공지조회\n" +
                    "\n[방장 명령어] :\n"+
                    " !공지등록 : 공지등록(공지버튼과 동일기능)\n";

        }
        else {
            new_message="잘못된 명령어 입니다. 채팅봇 명령어를 보시려면 [!명령어]를 입력해주세요.";
        }
        return new_message;
    }


    //"!"로 시작하는지 검증
    public boolean botCheck(String message){
        String init=message.substring(0,1);
        if(init.equals("!")){
            return true;
        }
        else return false;
    }

    // 공지등록
    public String registNotice(String message){
        String notice = message.substring(5);
        return notice;
    }

    // 공지조회
    public String getNotice(ChatRoom chatRoom){
        try {
            String notice= roomNoticeRepository.findByChatRoom(chatRoom).get().getNotice();
            return notice;
        }catch (NoSuchElementException e){
            String notice="등록된 공지가 없습니다.";
            return notice;
        }
    }

}
