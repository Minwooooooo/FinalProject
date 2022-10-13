package com.example.demo.controller.util;

import com.example.demo.dto.httpDto.requestDto.TimerRequestDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.service.Util.TimerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class StudyController {
    private final TimerService timerService;

    @RequestMapping(value = "/auth/timer",method = RequestMethod.POST)
    public ResponseDto<?> timer(@RequestBody TimerRequestDto timerRequestDto, HttpServletRequest request){
     // todo checking value 
      preventSqlTocheck(timerRequestDto);

      return timerService.StudyTimer(timerRequestDto,request);
    }
}
