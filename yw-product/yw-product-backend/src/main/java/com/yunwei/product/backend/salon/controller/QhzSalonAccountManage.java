package com.yunwei.product.backend.salon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.product.backend.service.QhzSalonAccountService;
import com.yunwei.product.common.model.QhzSalonAccount;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;

/**
 * 沙龙注册用户信息表控制层
* @ClassName: QhzSalonAccountManage 
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年06月21日 下午15:38:55
*
 */
@Controller
@RequestMapping("/qhz_salon_account/qhzSalonAccount")
public class QhzSalonAccountManage {
	private static Logger logger = Logger.getLogger(QhzSalonAccountManage.class);
	@Autowired
	private QhzSalonAccountService qhzSalonAccountService;
 
	@RequestMapping
	public String qhzSalonAccountList(Model model){

		return "/salon/account/qhzSalonAccountList";
	}
	
	/**
	 * 查询列表集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,QhzSalonAccount qhzSalonAccount,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(qhzSalonAccount));
		List<QhzSalonAccount> list = qhzSalonAccountService.queryPage(map);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(QhzSalonAccount qhzSalonAccount,@RequestParam(defaultValue = "10") int pageSize){
		int count = qhzSalonAccountService.queryTotals(qhzSalonAccount);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,QhzSalonAccount qhzSalonAccount,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("qhzSalonAccount", qhzSalonAccountService.query(qhzSalonAccount));
		} else {
			model.addAttribute("qhzSalonAccount", qhzSalonAccount);
		}
		
		return "/salon/account/qhzSalonAccountEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(QhzSalonAccount qhzSalonAccount,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(op_type, "1")){
				qhzSalonAccountService.insert(qhzSalonAccount);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				qhzSalonAccountService.update(qhzSalonAccount);
				map.put("error_no", "0");
				map.put("error_info", "修改成功");
			}
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(QhzSalonAccount qhzSalonAccount){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			qhzSalonAccountService.delete(qhzSalonAccount);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(QhzSalonAccount qhzSalonAccount){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = qhzSalonAccount.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			qhzSalonAccountService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}
}
