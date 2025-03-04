package cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.office;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipOutputStream;

public class WordUtils {

    private final HttpServletResponse response;
    private final XWPFDocument document;

    public WordUtils(HttpServletResponse response) {
        this.response = response;
        this.document = new XWPFDocument();

    }

    public XWPFDocument getDocument() {
        return document;
    }


    /**
     * 设置页脚
     * 格式为：第X页 总共X页
     */
    public void createPageFooter() {

        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);

        //创建页脚
        XWPFFooter xwpfFooter = document.createFooter(HeaderFooterType.DEFAULT);
        XWPFParagraph xwpfParagraph = xwpfFooter.createParagraph();
        xwpfParagraph.setAlignment(ParagraphAlignment.CENTER);

        // 设置页码
        XWPFRun xwpfRun = xwpfParagraph.createRun();
        xwpfRun.setText("第");
        xwpfParagraph.getCTP().addNewFldSimple().setInstr("PAGE \\* MERGEFORMAT");
        xwpfRun = xwpfParagraph.createRun();
        xwpfRun.setText("页 总共");

        xwpfParagraph.getCTP().addNewFldSimple().setInstr("NUMPAGES \\* MERGEFORMAT");
        xwpfRun = xwpfParagraph.createRun();
        xwpfRun.setText("页");
    }

    public void begin() {


    }


    public void end(ZipOutputStream zipOutputStream)
            throws IOException {

        document.write(zipOutputStream);
        document.close();

    }

    public void end()
            throws IOException {

        String fileName = new String("导出表格".getBytes(StandardCharsets.UTF_8), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";" + "filename*=utf-8''" + fileName);


        OutputStream outputStream = response.getOutputStream();
        document.write(outputStream);
        outputStream.flush();
        outputStream.close();
        document.close();
    }

    public void createTable() {
        //基本信息表格
        XWPFTable infoTable = document.createTable();
        //去表格边框
        infoTable.getCTTbl().getTblPr().unsetTblBorders();

        //列宽自动分割
        CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
        infoTableWidth.setType(STTblWidth.DXA);
        infoTableWidth.setW(BigInteger.valueOf(9072));

        //表格第一行
        XWPFTableRow infoTableRowOne = infoTable.getRow(0);
        infoTableRowOne.getCell(0).setText("职位");
        infoTableRowOne.addNewTableCell().setText(": Java 开发工程师");

        //表格第二行
        XWPFTableRow infoTableRowTwo = infoTable.createRow();
        infoTableRowTwo.getCell(0).setText("姓名");
        infoTableRowTwo.getCell(1).setText(": seawater");


    }

    public void createInfoTable(String userName,
                                String realName,
                                String commitType,
                                String startAndEndTime,
                                String totalScore) {
        //基本信息表格
        XWPFTable infoTable = document.createTable();
        //去表格边框
        //infoTable.getCTTbl().getTblPr().unsetTblBorders();

        //列宽自动分割
        CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
        infoTableWidth.setType(STTblWidth.DXA);
        infoTableWidth.setW(BigInteger.valueOf(9072));

        //表格第一行
        XWPFTableRow infoTableRowOne = infoTable.getRow(0);
        infoTableRowOne.getCell(0).setText("学号");
        infoTableRowOne.addNewTableCell().setText(userName);

        //表格第二行
        XWPFTableRow infoTableRowTwo = infoTable.createRow();
        infoTableRowTwo.getCell(0).setText("姓名");
        infoTableRowTwo.getCell(1).setText(realName);

        //表格第3行
        XWPFTableRow infoTableRow3 = infoTable.createRow();
        infoTableRow3.getCell(0).setText("交卷方式");
        infoTableRow3.getCell(1).setText(commitType);

        //表格第4行
        XWPFTableRow infoTableRow4 = infoTable.createRow();
        infoTableRow4.getCell(0).setText("时间");
        infoTableRow4.getCell(1).setText(startAndEndTime);

        //表格第5行
        XWPFTableRow infoTableRow5 = infoTable.createRow();
        infoTableRow5.getCell(0).setText("学生分数");
        infoTableRow5.getCell(1).setText(totalScore);


    }

    public void createExamPaperAnswerQuestionTestTable(List<List<String>> results) {
        //基本信息表格
        XWPFTable infoTable = document.createTable();
        //去表格边框
        //infoTable.getCTTbl().getTblPr().unsetTblBorders();

        //列宽自动分割
        CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
        infoTableWidth.setType(STTblWidth.DXA);
        infoTableWidth.setW(BigInteger.valueOf(9072));

        //表格第一行
        XWPFTableRow infoTableRowOne = infoTable.getRow(0);
        infoTableRowOne.getCell(0).setText("输入");
        infoTableRowOne.addNewTableCell().setText("正确答案");
        infoTableRowOne.addNewTableCell().setText("学生答案");
        infoTableRowOne.addNewTableCell().setText("是否正确");

        if (results != null) {
            for (List<String> result : results) {
                if (result != null && result.size() == 4) {
                    XWPFTableRow infoTabletemp = infoTable.createRow();
                    infoTabletemp.getCell(0).setText(result.get(0));
                    infoTabletemp.getCell(1).setText(result.get(1));
                    infoTabletemp.getCell(2).setText(result.get(2));
                    infoTabletemp.getCell(3).setText(result.get(3));
                }
            }
        }


    }

    //添加标题
    public void createTitle(
            String content) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r1 = paragraph.createRun();
        r1.setBold(true);
        r1.setText(content);
        r1.setFontSize(22);

        //设置行间距
        CTP ctp = paragraph.getCTP();
        CTPPr ctpPr = ctp.isSetPPr() ? ctp.getPPr() : ctp.addNewPPr();
        CTSpacing ctSpacing = ctpPr.isSetSpacing() ? ctpPr.getSpacing() : ctpPr.addNewSpacing();
        ctSpacing.setAfter(BigInteger.valueOf(480));
        ctSpacing.setBefore(BigInteger.valueOf(480));
        ctSpacing.setLineRule(STLineSpacingRule.EXACT);
        ctSpacing.setLine(BigInteger.valueOf(480));//1磅数是20
    }

    public void createChapterH1(
            String content) {
        XWPFParagraph actTheme = document.createParagraph();
        actTheme.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun runText1 = actTheme.createRun();
        runText1.setBold(true);
        runText1.setText(content);
        runText1.setFontSize(18);
    }

    public void createChapterH2(
            String content) {
        XWPFParagraph actType = document.createParagraph();
        XWPFRun runText2 = actType.createRun();
        runText2.setBold(true);
        runText2.setText(content);
        runText2.setFontSize(14);
    }

    public void createParagraph(
            String content) {
        XWPFParagraph actType = document.createParagraph();
        XWPFRun runText2 = actType.createRun();
        runText2.setText(content);
        runText2.setFontSize(11);
    }

    public void createParagraphAlignmentCenter(
            String content) {
        XWPFParagraph actType = document.createParagraph();
        actType.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun runText2 = actType.createRun();
        runText2.setText(content);
        runText2.setFontSize(11);
    }

    //空行
    public void createLineBreakParagraph() {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun paragraphRun = paragraph.createRun();
        paragraphRun.setText("\r");
    }

    public void createNewPageParagraph() {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setPageBreak(true);
        // XWPFRun paragraphRun = paragraph.createRun();
        // paragraphRun.setText("\r");
    }


    public void createPageHeader(
            String headerText) {

        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);

        //添加页眉
        CTP ctpHeader = CTP.Factory.newInstance();
        CTR ctrHeader = ctpHeader.addNewR();
        CTText ctHeader = ctrHeader.addNewT();

        ctHeader.setStringValue(headerText);

        XWPFParagraph headerParagraph = new XWPFParagraph(ctpHeader, document);
        XWPFRun run = headerParagraph.createRun();
        run.setFontSize(10);

        //设置为右对齐
        headerParagraph.setAlignment(ParagraphAlignment.RIGHT);

        XWPFParagraph[] parsHeader = new XWPFParagraph[1];
        parsHeader[0] = headerParagraph;
        policy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, parsHeader);
    }
}
