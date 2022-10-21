package com.blackred.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.blackred.service.MemberService;
import com.blackred.service.ReportService;
import com.blackred.service.SetmealService;
import com.blackred.utils.ResultVo;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private SetmealService setmealService;

    @Reference
    private ReportService reportService;



    @RequestMapping("/getMemberReport")
    public ResultVo getMemberReport(){
        return memberService.getMemberReport();
    }

    @RequestMapping("/getSetmealReport")
    public ResultVo getSetmealReport(){
        return setmealService.getSetmealReport();
    }

    @RequestMapping("/getBusinessReportData")
    public ResultVo getBusinessReportData(){
        return reportService.getBusinessReportData();
    }

    @RequestMapping("/exportBusinessReport")
    public void exportBusinessReport(HttpServletResponse response){
        ResultVo resultVo = reportService.getBusinessReportData();
        Map<String,Object> map = (Map<String, Object>) resultVo.getData();
        try {
            XSSFWorkbook book = new XSSFWorkbook("D:\\upload1/report_template.xlsx");
            XSSFSheet sheetAt = book.getSheetAt(0);
            XSSFRow row = sheetAt.getRow(2);
            XSSFCell cell = row.getCell(5);
            cell.setCellValue((String) map.get("reportDate"));

            row = sheetAt.getRow(4);
            cell = row.getCell(5);
            cell.setCellValue((Integer) map.get("todayNewMember"));

            row = sheetAt.getRow(4);
            cell = row.getCell(7);
            cell.setCellValue((Integer) map.get("totalMember"));

            row = sheetAt.getRow(5);
            cell = row.getCell(5);
            cell.setCellValue((Integer) map.get("thisWeekNewMember"));

            row = sheetAt.getRow(5);
            cell = row.getCell(7);
            cell.setCellValue((Integer) map.get("thisMonthNewMember"));

            row = sheetAt.getRow(7);
            cell = row.getCell(5);
            cell.setCellValue((Integer) map.get("todayOrderNumber"));

            row = sheetAt.getRow(7);
            cell = row.getCell(7);
            cell.setCellValue((Integer) map.get("todayVisitsNumber"));

            row = sheetAt.getRow(8);
            cell = row.getCell(5);
            cell.setCellValue((Integer) map.get("thisWeekOrderNumber"));

            row = sheetAt.getRow(8);
            cell = row.getCell(7);
            cell.setCellValue((Integer) map.get("thisWeekVisitsNumber"));

            row = sheetAt.getRow(9);
            cell = row.getCell(5);
            cell.setCellValue((Integer) map.get("thisMonthOrderNumber"));

            row = sheetAt.getRow(9);
            cell = row.getCell(7);
            cell.setCellValue((Integer) map.get("thisMonthVisitsNumber"));

//          12 4
            int i = 12;
            List<Map<String,Object>> hotSetmeal = (List<Map<String, Object>>) map.get("hotSetmeal");
            for (Map<String, Object> objectMap : hotSetmeal) {
                sheetAt.getRow(i).getCell(4).setCellValue((String) objectMap.get("name"));
                sheetAt.getRow(i).getCell(5).setCellValue((Integer) objectMap.get("setmeal_count"));
                sheetAt.getRow(i++).getCell(6).setCellValue((Double) objectMap.get("proportion"));
                i++;
            }
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("Content-disposition","attachment;filename=report.xlsx");
            book.write(outputStream);
            outputStream.close();
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
