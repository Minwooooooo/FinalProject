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

    // 마이페이지 기본정보 조회
    @GetMapping(value = "/info")
    public ResponseDto<?> getMyInfo(HttpServletRequest request){
        return ResponseDto.success(mypageService.myInfo(request));
    }

    // 나의 스터디룸별 메모 조회
    @GetMapping(value = "/memo/{roomid}")
    public ResponseDto<?> getMemo(HttpServletRequest request, @PathVariable String roomId){
        return ResponseDto.success(mypageService.getMemo(request, roomId));
    }

    // 나의 전체 메모 조회
    @GetMapping(value = "/memo")
    public ResponseDto<?> getAllMemo(HttpServletRequest request){
        return ResponseDto.success(mypageService.getAllMemo(request));
    }

    // 메모 삭제
    @DeleteMapping(value = "/memo/{roomid}")
    public ResponseDto<?> deleteMemo(@PathVariable String roomId, HttpServletRequest request){
        return ResponseDto.success(mypageService.deleteMemo(request, roomId));
    }

    // 나의 모든 타이머 조회
    @GetMapping(value = "/timer")
    public ResponseDto<?> getAllTimer(HttpServletRequest request){
        return ResponseDto.success(mypageService.allMyTimer(request));
    }

    // 나의 스터디룸별 타이머 조회
    @GetMapping(value = "/timer/{roomid}")
    public ResponseDto<?> getTimer(HttpServletRequest request, @PathVariable String roomId){
        return ResponseDto.success(mypageService.myTimer(request, roomId));
    }

}
