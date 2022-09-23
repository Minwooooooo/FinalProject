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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long kakaoId;

    @NotNull
    private String email;

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
