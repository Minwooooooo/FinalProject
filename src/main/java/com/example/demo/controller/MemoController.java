package com.example.demo.controller;


import com.example.demo.dto.requestDto.MemoRequestDto;
import com.example.demo.dto.responseDto.ResponseDto;
import com.example.demo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @PostMapping(value = "/memo")
    public ResponseDto<?> saveMemo(@RequestBody MemoRequestDto memoRequestDto, HttpServletRequest request){
        return ResponseDto.success(memoService.saveMemo(memoRequestDto,request));
    }

    @GetMapping(value = "/memo/{roomid}")
    public ResponseDto<?> getMemo(HttpServletRequest request, @PathVariable String roomId){
        return ResponseDto.success(memoService.getMemo(request, roomId));
    }


    @GetMapping(value = "/memo")
    public ResponseDto<?> getAllMemo(HttpServletRequest request){
        return ResponseDto.success(memoService.getAllMemo(request));
    }


    @DeleteMapping(value = "/memo/{roomid}")
    public ResponseDto<?> deleteMemo(@PathVariable String roomId, HttpServletRequest request){
        return ResponseDto.success(memoService.deleteMemo(request, roomId));
    }

}
