package com.example.demo.entity;

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
public class Member {

    @Id
    private Long id;

    @Column
    private String memberName;

    @Column
    private String profileImage;

    @Column
    private Authority userRole;

    public enum Authority {
        ROLE_USER,ROLE_ADMIN,ROLE_BLACK
    }
}
