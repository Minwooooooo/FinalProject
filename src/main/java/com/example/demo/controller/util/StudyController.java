package com.example.demo.controller.util;

import com.example.demo.dto.httpDto.requestDto.TimerRequestDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.httpDto.requestDto.TranslationRequestDto;
import com.example.demo.service.Chat.ChatHandler;
import com.example.demo.service.Util.TimerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class StudyController {
    private final TimerService timerService;
    private final ChatHandler chatHandler;


    // 타이머 저장
    @RequestMapping(value = "/auth/timer",method = RequestMethod.POST)
    public ResponseDto<?> timer(@RequestBody TimerRequestDto timerRequestDto, HttpServletRequest request){
        return timerService.StudyTimer(timerRequestDto,request);
    }

    //번역
    @PostMapping("/chat/message/translation")
    public ResponseDto<?> translateMessage(@RequestBody TranslationRequestDto translationRequestDto) {
        String message = translationRequestDto.getMessage();
        return chatHandler.sendTranslateMessage(message);
    }
}
