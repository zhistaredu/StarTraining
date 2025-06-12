package com.edu.common.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 导出excel 表格 工具类
 * @author lixiang
 * @date 2023/5/26 10:18
 */
public class EasyExcelUtil implements RowWriteHandler {

    private int mergeRowIndex;//从哪一行开始合并
    private int[] mergeColumnIndex;//excel合并的列
    private int[] signNum;//合并的唯一标识
    private int total;//总行数
    private int lastRow;
    private int firstCol;
    private int lastCol;
    private int firstRow;
    private int mergeCount = 1;

    private EasyExcelUtil(){}

    private EasyExcelUtil(int mergeRowIndex, int[] mergeColumnIndex, int[] signNum, int total) {
        this.mergeRowIndex = mergeRowIndex;
        this.mergeColumnIndex = mergeColumnIndex;
        this.signNum = signNum;
        this.total = total;
    }

    /**
     * 导出excel
     * @param response
     * @param fileName 文件名称
     * @param exportList 导出数据
     * @param clazz 导出实体bean class对象
     * @param <T>
     * @throws IOException
     */
    public static <T> void createExcel(HttpServletResponse response, String fileName, List<T> exportList,
                                       Class<T> clazz)
            throws IOException {
        createExcel(response,fileName,exportList,clazz,null);
    }

    /**
     * 导出excel
     * @param response
     * @param fileName 文件名称
     * @param exportList 导出数据
     * @param clazz 导出实体bean class对象
     * @param cellMerge 单元格合并规则
     * @param <T>
     * @throws IOException
     */
    public static <T> void createExcel(HttpServletResponse response, String fileName, List<T> exportList,
                                       Class<T> clazz, CellMerge cellMerge)
            throws IOException {
        // 设置下载信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        //定义ExcelWriterSheetBuilder
        ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcel
                .write(response.getOutputStream(), clazz)
                .sheet(fileName);

        //合并单元格
        if (cellMerge != null) {
            // 从那一行开始合并
            int mergeRowIndex = 1;
            EasyExcelUtil
                    excelMergeRowByRowStrategy = new EasyExcelUtil(mergeRowIndex, cellMerge.getMergeColumIndex(),
                    cellMerge.getMergeRuleColumIndex(), exportList.size());
            excelWriterSheetBuilder.registerWriteHandler(excelMergeRowByRowStrategy);
        }

        //设置头样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置内容格式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        //设计内容居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置内容自动换行
        contentWriteCellStyle.setWrapped(true);
        excelWriterSheetBuilder.registerWriteHandler(horizontalCellStyleStrategy);

        //调用doWrite方法
        excelWriterSheetBuilder.doWrite(exportList);
    }

    @Override
    public void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer integer, Boolean aBoolean) {

    }

    /**
     * 单元格合并类
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class CellMerge {
        private int[] mergeColumIndex;
        private int[] mergeRuleColumIndex;
    }

    /**
     * 设置单元格合并规则
     * @param mergeColumIndex
     * @param mergeRuleColumIndex
     * @return
     */
    public static CellMerge setCellMerge(int[] mergeColumIndex,int[] mergeRuleColumIndex){
        EasyExcelUtil excelUtil = new EasyExcelUtil();
        CellMerge cellMerge = excelUtil.new CellMerge();
        cellMerge.setMergeColumIndex(mergeColumIndex);
        cellMerge.setMergeRuleColumIndex(mergeRuleColumIndex);
        return cellMerge;
    }
}
