package com.example.demo.controller.util;

import com.example.demo.dto.ResponseDto;
import com.example.demo.service.Util.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "/auth/mypage")
@RestController
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    @GetMapping(value = "/info")
    public ResponseDto<?> getMyInfo(HttpServletRequest request){
        return ResponseDto.success(mypageService.myInfo(request));
    }

    @GetMapping(value = "/memo/{roomid}")
    public ResponseDto<?> getMemo(HttpServletRequest request, @PathVariable String roomId){
        return ResponseDto.success(mypageService.getMemo(request, roomId));
    }


    @GetMapping(value = "/memo")
    public ResponseDto<?> getAllMemo(HttpServletRequest request){
        return ResponseDto.success(mypageService.getAllMemo(request));
    }


    @DeleteMapping(value = "/memo/{roomid}")
    public ResponseDto<?> deleteMemo(@PathVariable String roomId, HttpServletRequest request){
        return ResponseDto.success(mypageService.deleteMemo(request, roomId));
    }

    @GetMapping(value = "/timer")
    public ResponseDto<?> getAllTimer(HttpServletRequest request){
        return ResponseDto.success(mypageService.allMyTimer(request));
    }

    @GetMapping(value = "/timer/{roomid}")
    public ResponseDto<?> getTimer(HttpServletRequest request, @PathVariable String roomId){
        return ResponseDto.success(mypageService.myTimer(request, roomId));
    }

}
