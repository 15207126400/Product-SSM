package com.yunwei.product.backend.sales.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yunwei.common.util.Base32;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.context.service.PictureService;
import com.yunwei.product.backend.product.controller.YwProductManage;
import com.yunwei.product.backend.service.YwTeamFollowService;
import com.yunwei.product.common.model.YwTeamFollow;

/**
 * 参团信息
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/sales/ywTeamFollow")
public class YwTeamFollowManage {
	
	@Autowired
	private YwTeamFollowService ywTeamFollowService;
	@Autowired  
    private PictureService pictureService;

	/**
	 * 图片链接前缀
	 */
	private static final String FRONTPATH = "https://xcx.whywxx.com/yongyou/image/home/detail";
	
	// 日志打印
    Logger logger = Logger.getLogger(YwProductManage.class);
	
	@RequestMapping
	public String ywTeamFollowList(Model model){
		return "/sales/teamfollow/ywTeamFollowList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwTeamFollow ywTeamFollow,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywTeamFollow));
		List<YwTeamFollow> list = ywTeamFollowService.queryPage(map);
		map.put("error_no", "0");
		map.put("error_info", "查询成功");
		map.put("resultList", MapUtil.toMapList(list));
		return map;
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwTeamFollow ywTeamFollow,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywTeamFollowService.queryTotals(ywTeamFollow);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwTeamFollow ywTeamFollow,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			YwTeamFollow teamFollow = ywTeamFollowService.query(ywTeamFollow);
			teamFollow.setFollow_user_nickname(Base32.decode(teamFollow.getFollow_user_nickname()));
			model.addAttribute("ywTeamFollow", teamFollow);
		} else {
			model.addAttribute("ywTeamFollow", ywTeamFollow);
		}
		
		return "/sales/teamfollow/ywTeamFollowEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(@RequestParam(value = "file", required = false) MultipartFile file,YwTeamFollow ywTeamFollow,String op_type){
		Map<String,Object> resMap = new HashMap<String, Object>();
		try {
			if(file != null && file.getSize() != 0){
				String ftpPath = pictureService.uploadOriginalImageBySftp(file);
				String posPath = ftpPath.substring(20);
				String url = FRONTPATH + posPath;
				logger.info("*******图片上传成功*******");
				ywTeamFollow.setFollow_user_head_pic(url);
				resMap.put("url", url);
			}
			
			if(StringUtils.equals(op_type, "1")){
				ywTeamFollowService.insert(ywTeamFollow);
				resMap.put("error_no", "0");
				resMap.put("error_info", "添加成功");
			} else {
				ywTeamFollowService.update(ywTeamFollow);
				resMap.put("error_no", "0");
				resMap.put("error_info", "修改成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return resMap;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(YwTeamFollow ywTeamFollow){
		Map<String,Object> map = new HashMap<String, Object>();
		ywTeamFollowService.delete(ywTeamFollow);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwTeamFollow ywTeamFollow,String follow_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = follow_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("follow_id",strings);
		ywTeamFollowService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
}
