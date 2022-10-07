package com.example.demo.dto.messageDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class MessageDto {
    private int type;
    private String sender;
    private String msg;
    private String image;
}
