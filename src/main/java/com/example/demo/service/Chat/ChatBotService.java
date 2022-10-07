package com.example.demo.service.Chat;

import com.example.demo.entity.chat.ChatMessage;
import com.example.demo.entity.room.ChatRoom;
import com.example.demo.entity.room.Notice;
import com.example.demo.repository.room.NoticeRepository;
import com.example.demo.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatBotService {
    private final RoomRepository roomRepository;
    private final NoticeRepository noticeRepository;
    /*
    명령어 모음
    !공지 : 공지확인
    !공지등록 : 공지 등록(권한 확인후)
    !
     */
    @Transactional
    public String botRunner(ChatMessage chatMessage){
        String message=chatMessage.getMessage();
        System.out.println(message.trim());
        ChatRoom chatRoom = roomRepository.findById(chatMessage.getRoomId()).get();
        String new_message = null;
        if(message.trim().equals("!공지")){
            String get_notice=getNotice(chatRoom);
            new_message=get_notice;
        }

        else if(message.length()>4&&message.substring(0,5).equals("!공지등록")){
            Optional<Notice> notice=noticeRepository.findByChatRoom(chatRoom);
            String temp_notice=registNotice(message);

            if(notice.isEmpty()){
            Notice new_notice= Notice.builder()
                    .chatRoom(chatRoom)
                    .notice(temp_notice)
                    .build();
            noticeRepository.save(new_notice);
            }
            else {
                notice.get().editNotice(temp_notice);
            }
            new_message="<신규 공지>"+temp_notice;
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
            String notice=noticeRepository.findByChatRoom(chatRoom).get().getNotice();
            return notice;
        }catch (NoSuchElementException e){
            String notice="등록된 공지가 없습니다.";
            return notice;
        }
    }

}
