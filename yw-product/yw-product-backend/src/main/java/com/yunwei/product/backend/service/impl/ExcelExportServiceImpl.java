package com.yunwei.product.backend.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.util.IOUtils;
import com.yunwei.common.exception.BizException;


/**
 * excel文档导出
* @ClassName: ExcelExportServiceImpl 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年6月27日 上午11:18:09 
*
 */
@Component
public class ExcelExportServiceImpl{
	
	Logger logger = LoggerFactory.getLogger(ExcelExportServiceImpl.class);

	/**
	 * excel web方式导出
	 * @param excel_name excel文件名称
	 * @param sheet_name sheet名称
	 * @param columnNames[List<String>] sheet第一行名称描述cell信息
	 * @param dataList[List<String[]>] 对应sheet第一行名称的相应的数据
	 * @param response web导出
	 */
	public void exportWithResponse(String excel_name,String sheet_name, List<String> columnNames, List<String[]> dataList, HttpServletRequest request , HttpServletResponse response){
        OutputStream outputStream = null;
		try {
			// 设置一个导出状态标识 , 当有值时说明导出中 , 否则说明导出完成
        	
			// 创建Excel表格对象
			SXSSFWorkbook  book = new SXSSFWorkbook(100);
	        // 在当前Excel创建sheet页面
	        Sheet sheet = book.createSheet(sheet_name);
	        // 在当前sheet页面创建第一行记录(主要显示说明信息)
	        Row row = sheet.createRow(0);
	        // 创建sheet第一行cell标题信息
	        if(columnNames.size() > 0){
	        	for (int i = 0; i < columnNames.size(); i++)   
	            {  
		        	// 创建cell标题单元格
	                Cell cell = row.createCell(i);  
	                cell.setCellValue(columnNames.get(i));  
	                // 设置表格列宽自适应
	                sheet.setColumnWidth(i, 60 * 100);
	            }  
	        }
	        
	        // 创建sheet第一行对应的列表数据
	        if(dataList.size() > 0){
	        	for(int i = 0; i < dataList.size(); i++){
		        	// 从第二行开始显示数据
		        	Row row2 = sheet.createRow(i+1);
		        	
		        	// 获取数据
		        	String[] datas = dataList.get(i);
		        	if(datas.length > 0){
		        		for(int j = 0; j < datas.length; j++){
			        		// 创建cell数据单元格
			                Cell cell = row2.createCell(j);  
			                cell.setCellValue(datas[j]);  
			        	}
		        	}
		        }
	        }
	        /*request.getSession().removeAttribute("rowCount");
	        request.getSession().removeAttribute("exportFlag");*/
	        String filename = excel_name + ".csv";
			outputStream = response.getOutputStream();
			//response.reset();
	        response.setHeader("Content-disposition", "attachment; filename=".concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
	        response.setContentType("application/msexcel");        
	        book.write(outputStream);
	        
		} catch (IOException e) {
			logger.info("excel文件导出失败:{}",e);
			throw new BizException(e);
		} finally {
			IOUtils.close(outputStream);
		}
        
	}
	
}
