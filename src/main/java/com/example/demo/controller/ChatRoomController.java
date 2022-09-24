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
//    @GetMapping(value = "room/{roomId}")
//    public ResponseDto<?> enterRoom(@PathVariable String roomId,HttpServletRequest request){
//        return ResponseDto.success(chatRoomService.enterRoom(roomId,request));
//    }
}
