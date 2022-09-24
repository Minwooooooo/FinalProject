package com.example.demo.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoUserInfoDto {
    private Long id;
    private String email;
    private String nicknmae;
}
