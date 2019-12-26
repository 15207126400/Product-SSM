package com.yunwei.product.backend.product.controller;

import java.beans.Transient;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.FileUtil;
import com.yunwei.product.backend.service.YwProductService;
import com.yunwei.product.common.model.YwProduct;

@Controller
@RequestMapping("/product/ywProduct")
public class YwProductImportManage {
	
	@Autowired
	private YwProductService ywProductService;

	private static Logger logger = LoggerFactory.getLogger(YwProductImportManage.class);

	// 模板文件名
	private static final String FILE_NAME = "ProductInfoTemplate.xlsx";
	// 读取的sheet名
	private static final String SHEET_NAME = "product";

	@RequestMapping("downloadtemplatefile")
	public void downloadtemplatefile(HttpServletResponse response) {
		// String filename = FILE_NAME;
		File file = new File(this.getClass().getClassLoader().getResource("").getPath() + File.separator + FILE_NAME);
		OutputStream os = null;
		InputStream fis = null;
		try {
			// 以流的形式下载文件。
			fis = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.setContentType("application/msexcel;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			response.addHeader("Content-Disposition", "attachment;filename=" + FILE_NAME);
			response.addHeader("Content-Length", "" + file.length());
			os = response.getOutputStream();
			os.write(buffer);
			os.flush();
			os.close();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new BizException(e);
		} finally {
			try {
				fis.close();
				os.close();
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
				throw new BizException(e);
			}
		}
	}

	@RequestMapping("importoperatorinfo")
	@ResponseBody
	@Transient
	public Map<String, String> importoperatorinfo(HttpServletResponse response, @RequestParam("import_file") MultipartFile mfile) {
		
		InputStream is = null;
		int insert_count = 0;
		int update_count = 0;
		int rowNum = 0;
		String resultInfo = null;
		String error_no = "0";
		Map<String, String> map = new HashMap<String, String>();
		try {
			File tmpFile = new File(FileUtil.getTempDirectory() + File.separator + mfile.getOriginalFilename());
			mfile.transferTo(tmpFile);
			is = new FileInputStream(tmpFile);
			POIFSFileSystem fs = null;
			Workbook wb = null;
			Sheet sheet = null;
			try {
				fs = new POIFSFileSystem(is);
				wb = new HSSFWorkbook(fs);
			} catch (Exception e) {
				wb = WorkbookFactory.create(new FileInputStream(tmpFile));
			}
			sheet = wb.getSheet(SHEET_NAME);
			if (sheet == null) {
				error_no = "-1";
				resultInfo = "【导入失败】：" + tmpFile.getName() + "文件中没有【" + SHEET_NAME + "】sheet表格";
			} else {
				rowNum = sheet.getLastRowNum();
				
				StringBuffer outfilecontent = new StringBuffer();
				if (rowNum == 0) {
					outfilecontent.append("【导入失败】：" +"没有数据，请重新填写");
				} else {
					List<YwProduct> list = readExcelContent(sheet, outfilecontent);
					is.close();
					if (tmpFile.exists()) {
						tmpFile.delete();
					}
					if (CollectionUtils.isNotEmpty(list)) {
						
						// 遍历excl读取的数据
						for (YwProduct ywProduct : list) {
							if(StringUtils.isNotBlank(ywProduct.getProduct_code())){
								// 判断该商品编码所属商品是否存在
								YwProduct product = ywProductService.queryByProductCode(ywProduct.getProduct_code());
								if(product != null){
									product = ywProductService.queryById(product.getId());
									product.setProduct_code(ywProduct.getProduct_code());
									product.setTitle(ywProduct.getTitle());
									product.setPrice(ywProduct.getPrice());
									product.setProduct_virtualprice(ywProduct.getProduct_virtualprice());
									product.setKeyword(ywProduct.getKeyword());
									product.setStock(ywProduct.getStock());
									product.setProduct_sort(ywProduct.getProduct_sort());
									product.setProduct_carriage(ywProduct.getProduct_carriage());
									product.setProduct_virtualsales(ywProduct.getProduct_virtualsales());
									
									int num = ywProductService.updateByExcel(product);
									if(num > 0){
										update_count++;
									}
								}else{
									int num = ywProductService.addYwProduct(ywProduct);
									if(num > 0){
										insert_count++;
									}
								}
							}
						}
						outfilecontent.append("【导入结果】：成功导入" + insert_count + "条数据,更新"+update_count+"条数据");
					}
				}
				if (StringUtils.isNoneBlank(outfilecontent)) {
					resultInfo = outfilecontent.toString();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
					e.printStackTrace();
				}
			}
		}
		map.put("error_no", error_no);
		map.put("resultInfo", resultInfo);
		return map;
	}

	/**
	 * 读取Excel数据内容
	 * @param sysUser
	 * @param outFile
	 * @param is
	 * @param tmpFile
	 * @return Map 包含单元格数据内容的Map对象
	 * @throws Exception
	 */
	public List<YwProduct> readExcelContent(Sheet sheet, StringBuffer sb) throws Exception {
		Row row = null;
		// 得到总行数
		int rowNum  = newRowNum(sheet);
		//int rowNum = sheet.getLastRowNum();
		String product_code = null;					// 商品编码
		String title = null;						// 标题
		String price = null;						// 价格
		String product_virtualprice = null;			// 虚拟价格
		String keyword = null;						// 特色描述
		String stock = null;						// 库存
		String product_sort = null;					// 商品排序
		String product_carriage = null;				// 运费设置
		String product_virtualsales = null;			// 虚拟销量
		// 正文内容应该从第二行开始,第一行为表头的标题
		List<YwProduct> list = new ArrayList<YwProduct>();
		for (int i=1 ; i < rowNum ; i++) {
			row = sheet.getRow(i);
			YwProduct ywProduct = new YwProduct();
			/*************** 导入数据 ******************/
			if (StringUtils.isNotBlank(getCellFormatValue(row.getCell(0)).trim())) {
				product_code = getCellFormatValue(row.getCell(0)).trim();
			}
			if (StringUtils.isNotBlank(getCellFormatValue(row.getCell(1)).trim())) {
				title = getCellFormatValue(row.getCell(1)).trim();
			}
			if (StringUtils.isNotBlank(getCellFormatValue(row.getCell(2)).trim())) {
				price = getCellFormatValue(row.getCell(2)).trim();
			}
			if (StringUtils.isNotBlank(getCellFormatValue(row.getCell(3)).trim())) {
				product_virtualprice = getCellFormatValue(row.getCell(3)).trim();
			}
			if (StringUtils.isNotBlank(getCellFormatValue(row.getCell(4)).trim())) {
				keyword = getCellFormatValue(row.getCell(4)).trim();
			}
			if (StringUtils.isNotBlank(getCellFormatValue(row.getCell(5)).trim())) {
				stock = getCellFormatValue(row.getCell(5)).trim();
			}
			if (StringUtils.isNotBlank(getCellFormatValue(row.getCell(6)).trim())) {
				product_sort = getCellFormatValue(row.getCell(6)).trim();
			}
			if (StringUtils.isNotBlank(getCellFormatValue(row.getCell(7)).trim())) {
				product_carriage = getCellFormatValue(row.getCell(7)).trim();
			}
			if (StringUtils.isNotBlank(getCellFormatValue(row.getCell(8)).trim())) {
				product_virtualsales = getCellFormatValue(row.getCell(8)).trim();
			}

			/*************** end ******************/

			/*if (StringUtils.isNoneBlank(getCellFormatValue(row.getCell(7)).trim())) {
				image_path = PropertiesUtils.get("user.image.path", System.getProperty("user.home") + "/upload/image/") + getCellFormatValue(row.getCell((short)7)).trim();
			}*/
			ywProduct.setProduct_code(product_code);
			ywProduct.setTitle(title);
			ywProduct.setPrice(price);
			ywProduct.setProduct_virtualprice(product_virtualprice);
			ywProduct.setKeyword(keyword);
			ywProduct.setStock(stock);
			ywProduct.setProduct_sort(product_sort);
			ywProduct.setProduct_carriage(product_carriage);
			ywProduct.setProduct_virtualsales(product_virtualsales);
			list.add(ywProduct);
		}
		return list;
	}


	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * @param cell Excel单元格
	 * @return String 单元格数据内容
	 */
	@SuppressWarnings("unused")
	private String getStringCellValue(HSSFCell cell) {
		String strCell = "";
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				strCell = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				strCell = String.valueOf(cell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				strCell = String.valueOf(cell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				strCell = "";
				break;
			default:
				strCell = "";
				break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		if (cell == null) {
			return "";
		}
		return strCell;
	}

	/**
	 * 获取单元格数据内容为日期类型的数据
	 * @param cell
	 *        Excel单元格
	 * @return String 单元格数据内容
	 */
	@SuppressWarnings({"unused", "deprecation"})
	private String getDateCellValue(HSSFCell cell) {
		String result = "";
		try {
			int cellType = cell.getCellType();
			if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
				Date date = cell.getDateCellValue();
				result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
			} else if (cellType == HSSFCell.CELL_TYPE_STRING) {
				String date = getStringCellValue(cell);
				result = date.replaceAll("[年月]", "-").replace("日", "").trim();
			} else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
				result = "";
			}
		} catch (Exception e) {
			System.out.println("日期格式不正确!");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据HSSFCell类型设置数据
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(Cell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
				case HSSFCell.CELL_TYPE_NUMERIC:
				case HSSFCell.CELL_TYPE_FORMULA: {
					// 判断当前的cell是否为Date
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						// 如果是Date类型则，转化为Data格式

						// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
						// cellvalue = cell.getDateCellValue().toLocaleString();

						// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
						Date date = cell.getDateCellValue();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						cellvalue = sdf.format(date);

					}
					// 如果是纯数字
					else {
						// 取得当前Cell的数值
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cellvalue = cell.getStringCellValue();
					}
					break;
				}
				// 如果当前Cell的Type为STRIN
				case HSSFCell.CELL_TYPE_STRING:
					// 取得当前的Cell字符串
					cellvalue = cell.getStringCellValue();
					break;
				// 默认的Cell值
				default:
					cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}
	
	public int newRowNum(Sheet sheet){
		boolean flag = false;
		System.out.println("总行数："+(sheet.getLastRowNum()+1));
		CellReference cellReference = new CellReference("A4");
		for (int i = cellReference.getRow(); i <= sheet.getLastRowNum();) {
			Row r = sheet.getRow(i);
			if(r == null){
				// 如果是空行（即没有任何数据、格式），直接把它以下的数据往上移动
				sheet.shiftRows(i+1, sheet.getLastRowNum(),-1);
				continue;
			}
			flag = false;
			for(Cell c:r){
				if(c.getCellType() != Cell.CELL_TYPE_BLANK){
					flag = true;
					break;
				}
			}
			if(flag){
				i++;
				continue;
			}
			else{//如果是空白行（即可能没有数据，但是有一定格式）
				if(i == sheet.getLastRowNum())//如果到了最后一行，直接将那一行remove掉
					sheet.removeRow(r);
				else//如果还没到最后一行，则数据往上移一行
				    sheet.shiftRows(i+1, sheet.getLastRowNum(),-1);
			}
		}
		System.out.println("总行数："+(sheet.getLastRowNum()+1));
		return sheet.getLastRowNum()+1;
	}

	
	
}
