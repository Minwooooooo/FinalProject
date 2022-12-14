package com.example.demo.entity.room;

import com.example.demo.dto.httpDto.requestDto.CreatRoomRequestDto;
import lombok.*;
import javax.persistence.*;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChatRoom{
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
    private boolean lockChecker;

    @Column
    private String roomImage;

    @Column
    private String roomPw;

    @Column
    private int statusChecker; // 0 : 초기상태, 1 : 활성화, 2 : 비활성화


    public static ChatRoom create(CreatRoomRequestDto creatRoomRequestDto) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.roomName = creatRoomRequestDto.getRoomName();
        chatRoom.category = creatRoomRequestDto.getCategory();
        chatRoom.roomImage = creatRoomRequestDto.getRoomImage();
        chatRoom.maxEnterMember = creatRoomRequestDto.getMaxEnterMember();
        chatRoom.memberCount = 0L;
        chatRoom.lockChecker = creatRoomRequestDto.isLock();
        chatRoom.roomPw = creatRoomRequestDto.getRoomPw();
        chatRoom.statusChecker = 0;
        return chatRoom;
    }

    public void editMember(Long memberCount) {
        this.memberCount=memberCount;
        if(this.memberCount!=0){
            this.statusChecker =1;
        } else {
            this.statusChecker =2;
        }
    }

}
