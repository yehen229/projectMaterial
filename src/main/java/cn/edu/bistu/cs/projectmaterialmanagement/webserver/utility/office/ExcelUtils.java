package cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.office;


import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipOutputStream;

public class ExcelUtils {

    private final HttpServletResponse response;
    private final XSSFWorkbook workbook;

    public ExcelUtils(HttpServletResponse response) {
        this.response = response;
        workbook = new XSSFWorkbook();
    }

    private static void setBorder(XSSFCellStyle style,
                                  BorderStyle border,
                                  XSSFColor color) {
        style.setBorderTop(border);
        style.setBorderLeft(border);
        style.setBorderRight(border);
        style.setBorderBottom(border);
        style.setBorderColor(XSSFCellBorder.BorderSide.TOP, color);
        style.setBorderColor(XSSFCellBorder.BorderSide.LEFT, color);
        style.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, color);
        style.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, color);
    }

    public void autoSizeColumns(XSSFSheet sheet,
                                int columnNumber) {

        for (int i = 0; i < columnNumber; i++) {
            int orgWidth = sheet.getColumnWidth(i);
            sheet.autoSizeColumn(i, true);
            int newWidth = (int) (sheet.getColumnWidth(i) + 100);
            if (newWidth > orgWidth) {
                sheet.setColumnWidth(i, newWidth);
            } else {
                sheet.setColumnWidth(i, orgWidth);
            }
        }
    }

    public void setColAutoSize(XSSFSheet sheet,
                               int col) {


        int orgWidth = sheet.getColumnWidth(col);
        sheet.autoSizeColumn(col, true);
        int newWidth = (int) (sheet.getColumnWidth(col) + 100);
        if (newWidth > orgWidth) {
            sheet.setColumnWidth(col, newWidth);
        } else {
            sheet.setColumnWidth(col, orgWidth);
        }

    }

    public void setColSize(XSSFSheet sheet,
                           int col,
                           int width) {


        sheet.setColumnWidth(col, width);

    }


    /**
     * 添加一行内容
     *
     * @param sheet
     * @param row
     * @param rowIndex
     * @return
     */
    public int writeRow(XSSFSheet sheet,
                        List<Object> row,
                        int rowIndex) {
        int colIndex = 0;


        Font dataFont = workbook.createFont();
        dataFont.setFontName("simsun");
        // dataFont.setFontHeightInPoints((short) 14);
        dataFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setFont(dataFont);
        setBorder(dataStyle, BorderStyle.THIN, new XSSFColor(new byte[]{(byte) 0, (byte) 0, (byte) 0}));

        Row dataRow = sheet.createRow(rowIndex);


        colIndex = 0;

        for (Object cellData : row) {

            Cell cell = dataRow.createCell(colIndex);
            if (cellData != null) {
                cell.setCellValue(cellData.toString());
            } else {
                cell.setCellValue("");
            }

            cell.setCellStyle(dataStyle);
            colIndex++;
        }
        rowIndex++;

        return rowIndex;
    }

    public void setCellStyle(XSSFSheet sheet,
                             int rowIndex,
                             int col) {
        Font cellFont = workbook.createFont();
        cellFont.setFontName("simsun");
        cellFont.setBold(true);
        // titleFont.setFontHeightInPoints((short) 14);
        cellFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        cellStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 255, (byte) 0, (byte) 0}));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFont(cellFont);
        //setBorder(cellStyle, BorderStyle.THIN, new XSSFColor(new byte[]{(byte) 0, (byte) 0, (byte) 0}));

        Cell cell = sheet.getRow(rowIndex).getCell(col);
        cell.setCellStyle(cellStyle);
    }


    /**
     * 添加表格标题
     *
     * @param sheet
     * @param titles
     * @param rowIndex
     * @return
     */
    public int writeHeadsToExcel(
            XSSFSheet sheet,
            List<String> titles,
            int rowIndex) {

        int colIndex = 0;

        Font titleFont = workbook.createFont();
        titleFont.setFontName("simsun");
        titleFont.setBold(true);
        // titleFont.setFontHeightInPoints((short) 14);
        titleFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        titleStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 182, (byte) 184, (byte) 192}));
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFont(titleFont);
        setBorder(titleStyle, BorderStyle.THIN, new XSSFColor(new byte[]{(byte) 0, (byte) 0, (byte) 0}));

        Row titleRow = sheet.createRow(rowIndex);
        // titleRow.setHeightInPoints(25);
        colIndex = 0;

        for (String field : titles) {
            Cell cell = titleRow.createCell(colIndex);
            cell.setCellValue(field);
            cell.setCellStyle(titleStyle);
            colIndex++;
        }

        rowIndex++;
        return rowIndex;
    }


    public int writeSubTitle(
            XSSFSheet sheet,
            int rowIndex,
            String subtitle,
            int span) {
        return writeSubTitle(sheet, rowIndex, subtitle, span, true);
    }

    public int writeSubTitleAlignLeft(
            XSSFSheet sheet,
            int rowIndex,
            String subtitle,
            int span) {
        return writeSubTitle(sheet, rowIndex, subtitle, span, false);
    }

    /**
     * 添加副标题
     *
     * @param sheet
     * @param rowIndex
     * @param subtitle
     * @param span
     * @return
     */
    public int writeSubTitle(
            XSSFSheet sheet,
            int rowIndex,
            String subtitle,
            int span,
            boolean alignCenter) {

        if (subtitle == null || subtitle.length() == 0)
            return rowIndex;


        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, span));
        Row titleRow = sheet.createRow(rowIndex);

        Font titleFont = workbook.createFont();
        titleFont.setFontName("simsun");
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 12);
        titleFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle titleStyle = workbook.createCellStyle();
        if (alignCenter)
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
        else
            titleStyle.setAlignment(HorizontalAlignment.LEFT);

        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //    titleStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(182, 184, 192)));
        //   titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFont(titleFont);
        //  setBorder(titleStyle, BorderStyle.THIN, new XSSFColor(new java.awt.Color(0, 0, 0)));

        Cell cell = titleRow.createCell(0);
        cell.setCellValue(subtitle);
        cell.setCellStyle(titleStyle);


        return rowIndex + 1;

    }


    /**
     * 添加标题
     *
     * @param sheet
     * @param rowIndex
     * @param title
     * @param span
     * @return
     */
    public int writeTitle(XSSFSheet sheet,
                          int rowIndex,
                          String title,
                          int span) {

        if (title == null || title.length() == 0)
            return rowIndex;

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, span));
        Row titleRow = sheet.createRow(rowIndex);

        titleRow.setHeightInPoints(30);//行高

        Font titleFont = workbook.createFont();
        titleFont.setFontName("simsun");
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 14);
        titleFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //   titleStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(182, 184, 192)));
        //   titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFont(titleFont);
        //   setBorder(titleStyle, BorderStyle.THIN, new XSSFColor(new java.awt.Color(0, 0, 0)));

        Cell cell = titleRow.createCell(0);
        cell.setCellValue(title);
        cell.setCellStyle(titleStyle);


        return rowIndex + 1;

    }

    public void begin() {
    }

    public void end()
            throws IOException {
        String fileName = new String("导出表格".getBytes(StandardCharsets.UTF_8), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";" + "filename*=utf-8''" + fileName);

        response.flushBuffer();
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
        workbook.close();
    }

    public void end(ZipOutputStream zipOutputStream)
            throws IOException {


        workbook.write(zipOutputStream);

        workbook.close();
    }

    public void exportExcel(
            String fileName)
            throws Exception {

        // fileName = URLEncoder.encode("导出表格.xlsx", "UTF-8");
        // response.reset();

        //  response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";" + "filename*=utf-8''" + fileName);
        //   DgExcelUtil.getXlsx("导出sheet", exportClass, titleList, exportList).write(out);

        // response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");

        // response.getWriter().println(OBJECT_MAPPER.writeValueAsString("注销成功"));

        // 告诉浏览器用什么软件可以打开此文件
        //  response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        //   response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));

        //   response.setContentType("application/octet-stream");
        //   response.setHeader("Content-disposition", "attachment:filename=result.xlsx");

        //fileName = new String("导出表格".getBytes(StandardCharsets.UTF_8), "ISO8859-1");
        // response.addHeader("Content-Disposition", "attachment:filename" + fileName + ".xlsx");
        //  response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        response.flushBuffer();
        OutputStream outputStream = response.getOutputStream();
        // exportExcel(data, outputStream);

        outputStream.flush();
        outputStream.close();

    }

    public XSSFSheet createSheet(String sheetName) {
       //注意sheetName不能太长，最长不能超过32位，否则会自动截取为32位，容易造成sheet已存在问题
        return workbook.createSheet(sheetName);
    }


    public XSSFWorkbook getWorkbook() {
        return workbook;
    }
}
