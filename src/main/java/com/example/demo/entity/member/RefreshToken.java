package com.example.demo.entity.member;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    private String refreshTokenId;

    @Column
    private String refreshToken;

    @Column
    private String memberAddress;

    @Column
    private int status;

    public enum statusType{

    }
}
