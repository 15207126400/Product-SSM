package com.yunwei.product.common.backend.model.form;

import java.util.Date;

/**
 * 订单导入sqlserver系统
* @ClassName: OrderImportSqlserverForm 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年7月30日 下午4:39:54 
*
 */
public class YwOrderImportSqlserverForm {

	/***** 销货单 ******/
	private int ID;// 销货单主键
	private String code;// 销货单编号
	private int idcustomer;// 客户id
	private int invoiceType = 281;// 发票类型(默认值281)
	private String taxAmount;// 含税金额
	private Date SourceVoucherDate;// 单据日期
	private String amount;// 金额
	private int idbusinesstype = 65;// 业务类型id(默认值65)
	private String maker = "";// 制单人
	private int makerid;// 制单人ID
	
	/***** 销货单 明细******/
	private String code_b;// 销货单明细编号
	private int idSaleDeliveryDTO;// 销货单id
	private String quantity;// 数量
	private String idinventory;// 存货id
	private String idunit;// 单位id
	private String taxPrice;// 含税单价
	private String DetailMemo;// 备注(本地系统订单编号)
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getIdcustomer() {
		return idcustomer;
	}
	public void setIdcustomer(int idcustomer) {
		this.idcustomer = idcustomer;
	}
	public String getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}
	public Date getSourceVoucherDate() {
		return SourceVoucherDate;
	}
	public void setSourceVoucherDate(Date sourceVoucherDate) {
		SourceVoucherDate = sourceVoucherDate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public int getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(int invoiceType) {
		this.invoiceType = invoiceType;
	}
	public int getIdbusinesstype() {
		return idbusinesstype;
	}
	public void setIdbusinesstype(int idbusinesstype) {
		this.idbusinesstype = idbusinesstype;
	}
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	public int getMakerid() {
		return makerid;
	}
	public void setMakerid(int makerid) {
		this.makerid = makerid;
	}
	public String getCode_b() {
		return code_b;
	}
	public void setCode_b(String code_b) {
		this.code_b = code_b;
	}
	public int getIdSaleDeliveryDTO() {
		return idSaleDeliveryDTO;
	}
	public void setIdSaleDeliveryDTO(int idSaleDeliveryDTO) {
		this.idSaleDeliveryDTO = idSaleDeliveryDTO;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getIdinventory() {
		return idinventory;
	}
	public void setIdinventory(String idinventory) {
		this.idinventory = idinventory;
	}
	public String getIdunit() {
		return idunit;
	}
	public void setIdunit(String idunit) {
		this.idunit = idunit;
	}
	public String getTaxPrice() {
		return taxPrice;
	}
	public void setTaxPrice(String taxPrice) {
		this.taxPrice = taxPrice;
	}
	public String getDetailMemo() {
		return DetailMemo;
	}
	public void setDetailMemo(String detailMemo) {
		DetailMemo = detailMemo;
	}
	
	
}
