package com.example.demo.controller;

import com.example.demo.dto.requestDto.TimerRequestDto;
import com.example.demo.dto.responseDto.ResponseDto;
import com.example.demo.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;

    @RequestMapping(value = "/auth/timer",method = RequestMethod.POST)
    public ResponseDto<?> timer(@RequestBody TimerRequestDto timerRequestDto, HttpServletRequest request){
        return studyService.StudyTimer(timerRequestDto,request);
    }
}
