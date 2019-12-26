package com.yunwei.product.backend.sales.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.context.service.PictureService;
import com.yunwei.product.backend.service.YwProductService;
import com.yunwei.product.backend.service.YwTeamActivityService;
import com.yunwei.product.common.model.YwTeamActivity;

/**
 * 拼团活动
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/sales/ywTeamActivity")
public class YwTeamActivityManage {
	
	@Autowired
	private YwTeamActivityService ywTeamActivityService;
	@Autowired
	private YwProductService ywProductService;
	@Autowired  
    private PictureService pictureService;
	 
	@RequestMapping
	public String ywTeamActivityList(Model model){
		
		
		return "/sales/teamactivity/ywTeamActivityList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwTeamActivity ywTeamActivity,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywTeamActivity));
		List<YwTeamActivity> list = ywTeamActivityService.queryPage(map);
		map.put("error_no", "0");
		map.put("error_info", "查询成功");
		map.put("resultList", list);
		return map;
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwTeamActivity ywTeamActivity,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywTeamActivityService.queryTotals(ywTeamActivity);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwTeamActivity ywTeamActivity,String op_type,Model model){
		// 查询商品
		model.addAttribute("products", ywProductService.queryPage(null));
		
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywTeamActivity", ywTeamActivityService.query(ywTeamActivity));
		} else {
			model.addAttribute("ywTeamActivity", ywTeamActivity);
		}
		
		return "/sales/teamactivity/ywTeamActivityEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(@RequestParam(value = "file", required = false) MultipartFile file,YwTeamActivity ywTeamActivity,String op_type){
		Map<String,Object> resMap = new HashMap<String, Object>();
		try {
			// 查询商品是否已经添加团购活动
			if(StringUtils.equals(op_type, "1")){
				YwTeamActivity teamActivity = new YwTeamActivity();
				teamActivity.setProduct_id(ywTeamActivity.getProduct_id());
				teamActivity = ywTeamActivityService.query(teamActivity);
				if(teamActivity != null){
					throw new BizException("您已经添加过商品为团购活动！请重新选择商品");
				}
			}
			
			// 判断文件是否为空
			if(file != null && file.getSize() != 0){
				String url = pictureService.uploadOriginalImage(file);
				ywTeamActivity.setShare_img(url);
			}
			
			if(StringUtils.equals(op_type, "1")){
				ywTeamActivityService.teamActivityInsertAndProductUpdate(ywTeamActivity);
				resMap.put("error_no", "0");
				resMap.put("error_info", "添加成功");
			} else {
				ywTeamActivityService.teamActivityUpdateAndProductUpdate(ywTeamActivity);
				resMap.put("error_no", "0");
				resMap.put("error_info", "修改成功");
			}	
		} catch (Exception e) {
			throw new BizException(e);
		}
		return resMap;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(YwTeamActivity ywTeamActivity){
		Map<String,Object> map = new HashMap<String, Object>();
		ywTeamActivityService.delete(ywTeamActivity);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwTeamActivity ywTeamActivity,String team_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = team_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("team_id",strings);
		ywTeamActivityService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	

}
