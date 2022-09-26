package com.example.demo.controller;


import com.example.demo.dto.requestDto.CreatRoomRequestDto;
import com.example.demo.dto.responseDto.ResponseDto;
import com.example.demo.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/auth/chat")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping(value = "/room")
    public ResponseDto<?> creatRoom(@RequestBody CreatRoomRequestDto creatRoomRequestDto, HttpServletRequest request){
        return ResponseDto.success(chatRoomService.createChatRoom(creatRoomRequestDto,request));
    }

    @GetMapping(value = "/rooms")
    public ResponseDto<?> roomList(HttpServletRequest request){
        return ResponseDto.success(chatRoomService.roomList());

    }
    // 현재 -> 방입장시 해당 URL redirect
    // 수정 -> 방입장시 로딩페이지 BE 입장 가능여부를 확인해야함

//    @GetMapping(value = "room/{roomId}")
//    public ResponseDto<?> enterRoom(@PathVariable String roomId,HttpServletRequest request){
            // request -> 유저정보 확인
            // roomID 로 room을 찾아서 있는 방인지 확인
            //  + 방 타입이 비밀방이면 비밀번호 확인
            // 인원수 확인
            // 입장가능하다고 FE
            // 아니면 거부
//        return ResponseDto.success(chatRoomService.enterRoom(roomId,request));
//    }
}
