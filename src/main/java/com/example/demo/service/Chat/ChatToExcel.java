package com.example.demo.service.Chat;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.demo.entity.chat.ChatMessage;
import com.example.demo.repository.chat.ChatMessageRepository;
import com.example.demo.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatToExcel {
    @Autowired
    AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.credentials.bucket-name}")
    String S3_BUCKET;
    private final ChatMessageRepository chatMessageRepository;

    public void logSave(String roomId){
        List<ChatMessage> chatMessageList = chatMessageRepository.findAllByRoomId(roomId);
        ChatLogToXlsx(chatMessageList);
    }

    private void ChatLogToXlsx(List<ChatMessage> chatMessageList) {
        try(Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {

            String[] headers={"Room ID","Room NAME",
                    "Chat TYPE","Chat Sender ID","Chat sender NAME",
                    "Chat MESSAGE","Chat ETC","Date"
            };
            String roomName = "null";
            String fileName = "null";

            if(!chatMessageList.isEmpty()){
                roomName=chatMessageList.get(0).getRoomName();
                String date=dateCustomFormat(chatMessageList.get(0).getDate());
                fileName=roomName+"("+chatMessageList.get(0).getRoomId()+")_"+date;
            }

            Sheet sheet = workbook.createSheet(roomName);
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowIndex=1;
            List<ChatMessage> vanMessages=new ArrayList<>();
            for (int i = 0; i < chatMessageList.size(); i++) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(chatMessageList.get(i).getRoomId());
                row.createCell(1).setCellValue(chatMessageList.get(i).getRoomName());
                row.createCell(2).setCellValue(chatMessageList.get(i).getType());
                row.createCell(3).setCellValue(chatMessageList.get(i).getSenderId());
                row.createCell(4).setCellValue(chatMessageList.get(i).getSenderName());
                row.createCell(5).setCellValue(chatMessageList.get(i).getMessage());
                row.createCell(6).setCellValue(chatMessageList.get(i).getEtc());
                row.createCell(7).setCellValue(chatMessageList.get(i).getDate().toString());
                if(chatMessageList.get(i).getType().equals("REPORT")){
                    vanMessages.add(chatMessageList.get(i));
                }
            }
            workbook.write(outputStream);
            upload(new ByteArrayInputStream(outputStream.toByteArray()),fileName);
            chatMessageList.removeAll(vanMessages);
            // chatMessageRepository.deleteAll(chatMessageList);
        }
        catch (IOException e) {
            throw new RuntimeException("실패 : "+e.getMessage());
        }
    }


    public void upload(ByteArrayInputStream chatLog, String fileName) {
        //ObjectMetadata 생성
        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        amazonS3Client.putObject(
                new PutObjectRequest(S3_BUCKET,fileName,chatLog,objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

    }

    public String dateCustomFormat(Date temp_date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
        String date=simpleDateFormat.format(temp_date);
        return date;
    }
}
