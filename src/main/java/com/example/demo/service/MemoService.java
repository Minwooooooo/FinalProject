package com.example.demo.service;

import com.example.demo.dto.responseDto.MemoResponseDto;
import com.example.demo.dto.responseDto.ResponseDto;
import com.example.demo.dto.requestDto.MemoRequestDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.Memo;
import com.example.demo.repository.MemoRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemoService {
    private final MemoRepository memoRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseDto<?> createMemo(MemoRequestDto requestDto, HttpServletRequest request){
        Memo memo = getMemoAsOptional(requestDto);

        if (memo.getContent().length() == 0){
            try {
                memoRepository.save(memo);
            }catch (Exception e){
                return ResponseDto.fail("SAVE_ERR", "memo 저장 에러");
            }
        }else {
            try {
                memo.update(requestDto.getContent());
            }catch (Exception e){
                return ResponseDto.fail("UPDATE_ERR", "memo 수정 에러");
            }
        }

        return ResponseDto.success("메모 생성 저장 완료");
    }

    public ResponseDto<?> getMemos(){

        String name = getNameAsOptional();
        Member member = getMemberAsOptional(name);
        List<Memo> memos = getMemoListAsOptional(member);

        MemoResponseDto responseDto= MemoResponseDto.builder()
                .memoList(memos)
                .build();

        return ResponseDto.success(responseDto);
    }



    //    Private
    private static Memo getMemoAsOptional(MemoRequestDto requestDto) {
        Optional<Memo> memoOptional= Optional.ofNullable(new Memo(requestDto));
        Memo memo= memoOptional.orElseThrow(
                () -> new RuntimeException("MEMO_REQUEST_ERR")
        );
        return memo;
    }
    private static Member getMemberAsOptional(String name) {
        Optional<Member> memberOptional= Optional.ofNullable(memberRepository.findByMemberName(name));
        Member member= memberOptional.orElseThrow(
                () -> new RuntimeException("NOT_FOUND_MEMBER_NAME")
        );
        return member;
    }
    private String getNameAsOptional() {
        Optional<String> memberNameOptional = Optional.ofNullable(jwtTokenProvider.getMemberName());
        String name= memberNameOptional.orElseThrow(
                () -> new RuntimeException("NOT_FOUND_TOKEN OR NOT_FOUND_MEMBER")
        );
        return name;
    }
    private List<Memo> getMemoListAsOptional(Member member) {
        Optional<List<Memo>> memoOptional= Optional.ofNullable(memoRepository.findAllByMember(member));
        List<Memo> memos= memoOptional.orElseThrow(
                () -> new RuntimeException("NOT_FIND_MEMOS")
        );
        return memos;
    }
}



//  todo 방 ID로 방 이름, 카테고리 검색
