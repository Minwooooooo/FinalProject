package com.example.demo.service;

import com.example.demo.dto.requestDto.FollowingRequestDto;
import com.example.demo.dto.responseDto.DoFollowingResponseDto;
import com.example.demo.dto.responseDto.ResponseDto;
import com.example.demo.entity.Follow;
import com.example.demo.entity.Member;
import com.example.demo.repository.FollowRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    @Transactional
    public ResponseDto<?> doFollowingOthers(FollowingRequestDto requestDto, HttpServletRequest request) {

        List<Follow> follows = newFollowListAsNullChecked(requestDto);

        Member subjectMember= newMember(requestDto.getSendFollowingUserId());
        Follow newFollower = SettingNewFollower(requestDto, subjectMember);

        try {
            followRepository.save(newFollower);
        }catch (Exception e){
            System.err.println(e);
            return ResponseDto.fail("SAVE_ERR", "Can't save");
        }

        follows.add(newFollower);

        var responseDto= DoFollowingResponseDto.builder()
                .followList(follows)
                .msg("Success Following")
                .size(follows.size())
                .build();

        return ResponseDto.success(responseDto);
    }


    @Transactional
    public ResponseDto<?> doUnfollowingOthers(FollowingRequestDto requestDto, HttpServletRequest request) {

        List<Follow> follows = newFollowListAsNullChecked(requestDto);


//        todo getFollowerId Testing Inline


        Long index = getFollowerId(requestDto, follows);
        try {
            followRepository.deleteById(index);
        }catch (Exception e){
            System.err.println(e);
            return ResponseDto.fail("DELETING_ERR", e+"");
        }

//        ReLoad
        List<Follow> removedFollows = newFollowList(requestDto);

        var responseDto= DoFollowingResponseDto.builder()
                .followList(removedFollows)
                .msg("Success UnFollowing")
                .size(removedFollows.size())
                .build();

        return ResponseDto.success(responseDto);
    }


    public ResponseDto<?> loadFollowingMembers(Long memberId, HttpServletRequest request) {
//        Optional
        List<Follow> followers= newFollowerList(newMember(memberId));

        var responseDto= DoFollowingResponseDto.builder()
                .followList(followers)
                .msg("List up Members")
                .size(followers.size())
                .build();

        return ResponseDto.success(responseDto);
    }


//    Private
//    Common
    private Member newMember(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("Not Found By ID")
        );
    }
    private List<Follow> newFollowListAsNullChecked(FollowingRequestDto requestDto) {
        Optional<List<Follow>> optionalFollows= Optional
                .ofNullable(followRepository.findAllByMember(newMember(requestDto.getSendFollowingUserId())));

        if (optionalFollows.isEmpty()){
            System.err.println("doFollowingOthers - NULL");
        }

        return optionalFollows.orElseThrow(
                () -> new IllegalArgumentException("NOT_FIND_ALL_ERR")
        );
    }



//    doUnfollowingOthers
    private static Long getFollowerId(FollowingRequestDto requestDto, List<Follow> follows) {
        Long index= 0L;
        for (var follower : follows){
            if (requestDto.getSendFollowingUserId() == follower.getFollowingMemberId()){
                index= follower.getId();
            }
        }
        return index;
    }
    private List<Follow> newFollowList(FollowingRequestDto requestDto) {
        Optional<List<Follow>> removedFollowsOptional= Optional
                .ofNullable(followRepository.findAllByMember(newMember(requestDto.getSendFollowingUserId())));

        List<Follow> removedFollows= removedFollowsOptional.orElseThrow(
                () -> new IllegalArgumentException("NOT_FIND_ALL_ERR")
        );
        return removedFollows;
    }
    private static Follow SettingNewFollower(FollowingRequestDto requestDto, Member subjectMember) {
        Follow newFollower= new Follow();
        newFollower.setMember(subjectMember);
        newFollower.setFollowingMemberId(requestDto.getFollowingSubjectId());
        return newFollower;
    }



//      loadFollowingMembers
    private List<Follow> newFollowerList(Member member){
        return Optional.ofNullable(followRepository.findAllByMember(member)).orElseThrow(
                () -> new IllegalArgumentException("")
        );
    }




    //    Test Mock
//        for test
//        List<Follow> follows= getFollowerListForTesting();
    private static List<Follow> getFollowerListForTesting() {
        var follower2= new Follow();
        var follower1= new Follow();
        List<Follow> follows= new ArrayList<>();
        follows.add(follower1);
        follows.add(follower2);
        return follows;
    }

}