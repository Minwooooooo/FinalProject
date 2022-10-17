package com.example.demo.controller;

import com.example.demo.repository.member.MemberRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.service.Chat.ChatToExcel;
import com.example.demo.service.Room.RoomHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;


@RestController
@RequiredArgsConstructor
public class TESTcontroller {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RoomHandler roomHandler;
    private final ChatToExcel chatToExcel;

    //Test Login

    @RequestMapping(value = "/test/quit",method = RequestMethod.GET)
    private void testLogin(HttpServletRequest request){
        roomHandler.quitProcess(request);
    }

//  @GetMapping("/file/{roomId}")
//  public void getFile(@PathVariable String roomId){
//      chatToExcel.logSave(roomId);
//  }

}
