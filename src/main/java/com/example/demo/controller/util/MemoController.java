package com.example.demo.controller.util;


import com.example.demo.dto.httpDto.requestDto.MemoRequestDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.service.Util.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "/auth")
@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    // 메모 저장
    @PostMapping(value = "/memo")
    public ResponseDto<?> saveMemo(@RequestBody MemoRequestDto memoRequestDto, HttpServletRequest request){
        return ResponseDto.success(memoService.saveMemo(memoRequestDto,request));
    }

    // 해당 방 메모 조회
    @GetMapping(value = "/memo/{roomId}")
    public ResponseDto<?> getMemo(@PathVariable String roomId,HttpServletRequest request){
        return ResponseDto.success(memoService.getMemo(roomId,request));
    }

}
