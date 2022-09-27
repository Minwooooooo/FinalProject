package com.example.demo.controller;

import com.example.demo.dto.responseDto.ResponseDto;
import com.example.demo.dto.requestDto.MemoRequestDto;
import com.example.testing.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class MemoController {
    private final MemoService memoService;

    @GetMapping(value = "/memos")
    public ResponseDto<?> getMemos(HttpServletRequest request){
        return ResponseDto.success(memoService.getMemos());
    }

    @PostMapping(value = "/memos")
    public ResponseDto<?> postMemo(@RequestBody MemoRequestDto requestDto, HttpServletRequest request){
        return ResponseDto.success(memoService.createMemo(requestDto, request));
    }
}

//    API 생성
//    받을 내용 : Memo,Token(HttpServletRequest),방 I
