package com.example.demo.controller;


import com.example.demo.dto.requestDto.MemoRequestDto;
import com.example.demo.dto.responseDto.ResponseDto;
import com.example.demo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "/auth")
@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @PostMapping(value = "/memo")
    public ResponseDto<?> saveMemo(@RequestBody MemoRequestDto memoRequestDto, HttpServletRequest request){
        return ResponseDto.success(memoService.saveMemo(memoRequestDto,request));
    }

}
