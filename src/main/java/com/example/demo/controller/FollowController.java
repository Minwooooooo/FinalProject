package com.example.demo.controller;

import com.example.demo.dto.requestDto.FollowingRequestDto;
import com.example.demo.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.responseDto.ResponseDto;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class FollowController {
    private final FollowService followService;

//    Following
    @PostMapping(value = "/followers")
    public ResponseDto<?> doFollowingOthers(@RequestBody FollowingRequestDto requestDto, HttpServletRequest request){
        return followService.doFollowingOthers(requestDto, request);
    }

//    Unfollowing
    @PostMapping(value = "/unfollowers")
    public ResponseDto<?> doUnfollowingOthers(@RequestBody FollowingRequestDto requestDto, HttpServletRequest request){
        return followService.doUnfollowingOthers(requestDto, request);
    }

//    LoadDate
    @GetMapping(value = "/followers/{subjectMemberId}")
    public ResponseDto<?> loadFollowingMembers(@PathVariable Long subjectMemberId, HttpServletRequest request){
        return followService.loadFollowingMembers(subjectMemberId, request);
    }


}
