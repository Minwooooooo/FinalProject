package com.example.demo.service.Chat;

import com.example.demo.entity.member.Member;
import com.example.demo.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatToExcel {
    private final MemberRepository memberRepository;
    public ByteArrayInputStream load(){
        List<Member> members = memberRepository.findAll();
        ByteArrayInputStream inputStream = MemberListToExcel(members);
        return inputStream;
    }

    private ByteArrayInputStream MemberListToExcel(List<Member> members){
        try(Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {

            String type="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            String[] headers={"아이디","이름"};
            String sheetName="members";

            CellStyle cellStyle= workbook.createCellStyle();
            cellStyle.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.PINK.getIndex());
            cellStyle.setFillPattern(FillPatternType.ALT_BARS);

            Sheet sheet = workbook.createSheet(sheetName);

            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowIndex=1;
            for (int i = 0; i < members.size(); i++) {
                Row row = sheet.createRow(rowIndex++);

                row.createCell(0).setCellValue(members.get(i).getId());
                row.createCell(1).setCellValue(members.get(i).getMemberName());

                Cell cell2=row.createCell(2);
                cell2.setCellStyle(cellStyle);
                cell2.setCellValue(members.get(i).getMemberImage());
            }

            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());

        }
        catch (IOException e) {
            throw new RuntimeException("실패 : "+e.getMessage());
        }
    }
}
