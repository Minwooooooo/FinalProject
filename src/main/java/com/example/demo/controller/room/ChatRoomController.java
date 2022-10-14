package com.example.demo.controller.room;


import com.example.demo.dto.httpDto.requestDto.CreatRoomRequestDto;
import com.example.demo.dto.httpDto.requestDto.RoomPasswordDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.service.Room.ChatRoomService;
import com.example.demo.service.Room.RoomHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/auth/chat")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final RoomHandler roomHandler;


    // 방생성
    @PostMapping(value = "/room")
    public ResponseDto<?> creatRoom(@RequestBody CreatRoomRequestDto creatRoomRequestDto, HttpServletRequest request){
        String roomId=chatRoomService.createChatRoom(creatRoomRequestDto,request).getRoomId();
        String roomPw=creatRoomRequestDto.getRoomPw();
        // 퇴장 오류 검증 및 오류 발생시 퇴장처리
        roomHandler.quitProcess(request);
        // 생성후 자동입장
        return roomHandler.enterRoomHandler(roomId,roomPw,request);
    }

    // 방 목록 조회
    @GetMapping(value = "/rooms")
    public ResponseDto<?> roomList(HttpServletRequest request){
        // 퇴장 오류 검증 및 오류 발생시 퇴장처리
        roomHandler.quitProcess(request);
        return ResponseDto.success(chatRoomService.roomList());

    }

    // 방 입장 검증
    @PostMapping(value = "/enter/{roomId}")
    public ResponseDto<?> enterRoom(@PathVariable String roomId, @RequestBody RoomPasswordDto roomPasswordDto, HttpServletRequest request){
        // 퇴장 오류 검증 및 오류 발생시 퇴장처리
        roomHandler.quitProcess(request);
        return roomHandler.enterRoomHandler(roomId,roomPasswordDto.getPassword(),request);
    }

    // 방 퇴장 처리
    @GetMapping(value = "/quit/{roomId}")
    public ResponseDto<?> quitRoom(@PathVariable String roomId,HttpServletRequest request){
        return roomHandler.quitRoomHandler(roomId,request);

    }
}
