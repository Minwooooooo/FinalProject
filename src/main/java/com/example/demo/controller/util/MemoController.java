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

    @PostMapping(value = "/memo")
    public ResponseDto<?> saveMemo(@RequestBody MemoRequestDto memoRequestDto, HttpServletRequest request){
      // value checking
      preventSqlToCheckCollection(memoRequestDto);
      return ResponseDto.success(memoService.saveMemo(memoRequestDto,request));
    }

}
