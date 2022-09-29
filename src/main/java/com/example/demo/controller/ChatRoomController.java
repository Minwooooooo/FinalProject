package com.example.demo.controller;


import com.example.demo.dto.requestDto.CreatRoomRequestDto;
import com.example.demo.dto.responseDto.ResponseDto;
import com.example.demo.service.ChatRoomService;
import com.example.demo.service.RoomHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/auth/chat")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final RoomHandler roomHandler;

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

    @GetMapping(value = "/enter/{roomId}")
    public ResponseDto<?> enterRoom(@PathVariable String roomId,HttpServletRequest request){
        return ResponseDto.success(roomHandler.enterRoomHandler(roomId,request));
    }

    @GetMapping(value = "/quit/{roomId}")
    public ResponseDto<?> quitRoom(@PathVariable String roomId,HttpServletRequest request){
        return ResponseDto.success(roomHandler.quitRoomHandler(roomId,request));
    }
}
