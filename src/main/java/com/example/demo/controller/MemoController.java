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

    @GetMapping(value = "/memo/{id}")
    public ResponseDto<?> getMemo(@PathVariable Long id, @RequestBody String roomName){
        return ResponseDto.success(memoService.getMemo(id, roomName));
    }

    @GetMapping(value = "/memo/{id}")
    public ResponseDto<?> getAllMemo(@PathVariable Long id){
        return ResponseDto.success(memoService.getAllMemo(id));
    }

    @PutMapping(value = "/memo/{id}")
    public ResponseDto<?> updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto memoRequestDto){
        return ResponseDto.success(memoService.updateMemo(id, memoRequestDto));
    }

    @DeleteMapping(value = "/memo/{id}")
    public ResponseDto<?> deleteMemo(@PathVariable Long id, @RequestBody String roomName){
        return ResponseDto.success(memoService.deleteMemo(id, roomName));
    }

}
