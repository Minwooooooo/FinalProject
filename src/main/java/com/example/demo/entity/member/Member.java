package com.example.demo.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    private String id;

    @Column
    private Long kakaoId;

    @Column
    private String naverId;

    @Column
    private String memberName;

    @Column
    private String memberImage;

    @Column
    @JsonIgnore
    private String phoneNumber;

    @Column
    private Authority userRole;

    @Column
    private Date lastPing;

    public enum Authority {
        ROLE_USER,ROLE_ADMIN,ROLE_BLACK
    }

    public void setMemberImage(String url){
        this.memberImage = url;
    }
    public void setMemberName(String name){
        this.memberName=name;
    }
    public void setlastPing(Date pingDate){
        this.lastPing=pingDate;
    }

}
