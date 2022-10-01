package com.example.demo.entity;

import com.example.demo.dto.requestDto.CreatRoomRequestDto;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    @Id
    private String roomId;

    @Column
    private String roomName;

    @Column
    private String category;

    @Column
    private long maxEnterMember;

    @Column
    private Long memberCount;

    @Column
    private boolean lock;

    @Column
    private String roomPw;

    @Column
    private int status; // 0 : 초기상태, 1 : 활성화, 2 : 비활성화




    public static ChatRoom create(CreatRoomRequestDto creatRoomRequestDto) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.roomName = creatRoomRequestDto.getRoomName();
        chatRoom.category = creatRoomRequestDto.getCategory();
        chatRoom.maxEnterMember = creatRoomRequestDto.getMaxEnterMember();
        chatRoom.memberCount = 0L;
        chatRoom.lock = creatRoomRequestDto.isLock();
        chatRoom.roomPw = creatRoomRequestDto.getRoomPw();
        chatRoom.status = 0;
        return chatRoom;
    }

    public void enterMember() {
        this.memberCount+=1;
    }

    public void quitMember() {
        this.memberCount-=1;
    }

    public enum categoryList{
        생활영어, 캠스터디, 시험대비, 스크립트
    }
}
