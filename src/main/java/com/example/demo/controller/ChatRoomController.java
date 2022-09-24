package com.example.demo.controller;


import com.example.demo.dto.requestDto.CreatRoomRequestDto;
import com.example.demo.model.ChatRoom;
import com.example.demo.model.LoginInfo;
import com.example.demo.repo.RoomRepository;
import com.example.demo.service.ChatRoomService;
import com.example.demo.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    private final RoomRepository RoomRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/room")
    public String rooms() {
        return "/chat/room";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        List<ChatRoom> chatRooms = RoomRepository.findAll();
        chatRooms.stream().forEach(room -> room.setMemberCount(chatRoomService.getUserCount(room.getRoomId())));
        return chatRooms;
    }

    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestBody CreatRoomRequestDto creatRoomRequestDto) {
        return chatRoomService.createChatRoom(creatRoomRequestDto);
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return RoomRepository.findByRoomId(roomId);
    }

    @GetMapping("/user")
    @ResponseBody
    public LoginInfo getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return LoginInfo.builder().name(name).token(jwtTokenProvider.generateToken(name)).build();
    }
}
