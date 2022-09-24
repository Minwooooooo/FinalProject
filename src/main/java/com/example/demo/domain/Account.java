package com.example.demo.domain;

import com.example.demo.dto.request.AccountRequestDto;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {

    //    @Todo err 이 부분 저번에 민우님이 이야기해주신 부분. 카톡 id를 인덱스로 쓰자.
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @NotNull
    private String kakaoId;

    @NotNull
    private String email;

//    nick name

    public Account(AccountRequestDto requestDto){
        this.kakaoId= requestDto.getKakaoId();
        this.email= requestDto.getEmail();
    }




//    For Test
    public Account(Long kakaoId, String email) {
        this.kakaoId = kakaoId;
        this.email = email;
    }
}
