package com.yunwei.context.qrcode;

import org.springframework.context.annotation.Description;

import com.alibaba.fastjson.JSONObject;

/**
 * 二维码服务接口
* @ClassName: QrcodeService 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年2月21日 下午4:46:27 
*
 */
public interface QrcodeService {

	/**
	 * 获取小程序码，适用于需要的码数量较少的业务场景。通过该接口生成的小程序码，永久有效，有数量限制
	 * @param access_token 接口调用凭证 【必填】
	 * @param path 扫码进入的小程序页面路径，最大长度 128 字节，不能为空【必填】
	 * @param width 二维码的宽度，单位 px。最小 280px，最大 1280px 默认值【430】
	 * @param auto_color 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调  默认值【false】
	 * @param line_color auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示 默认值【{"r":0,"g":0,"b":0}】
	 * @param is_hyaline 是否需要透明底色，为 true 时，生成透明底色的小程序码 默认值【false】
	 * {@link Description} 与 createWXAQRCode 总共生成的码数量限制为 100,000，请谨慎调用
	 * @return
	 */
	public byte[] getWXACode(String access_token,String path,int width,boolean auto_color,JSONObject line_color,boolean is_hyaline);
	
	/**
	 * 获取小程序码，适用于需要的码数量极多的业务场景。通过该接口生成的小程序码，永久有效，数量暂无限制
	 * @param access_token 接口调用凭证 【必填】
	 * @param scene 最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，其它字符请自行编码为合法字符（因不支持%，中文无法使用 urlencode 处理，请使用其他编码方式）【必填】
	 * @param page 必须是已经发布的小程序存在的页面（否则报错），例如 pages/index/index, 根路径前不要填加 /,不能携带参数（参数请放在scene字段里），如果不填写这个字段，默认跳主页面 默认值【主页】
	 * @param width 二维码的宽度，单位 px。最小 280px，最大 1280px 默认值【430】
	 * @param auto_color 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调  默认值【false】
	 * @param line_color auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示 默认值【{"r":0,"g":0,"b":0}】
	 * @param is_hyaline 是否需要透明底色，为 true 时，生成透明底色的小程序码 默认值【false】
	 * {@link Description} 调用分钟频率受限（5000次/分钟），如需大量小程序码，建议预生成
	 * @return
	 */
	public byte[] getWXACodeUnlimit(String access_token,String scene,String page,int width,boolean auto_color,JSONObject line_color,boolean is_hyaline);
	
	/**
	 * 获取小程序二维码，适用于需要的码数量较少的业务场景。通过该接口生成的小程序码，永久有效，有数量限制
	 * @param access_token 接口调用凭证 【必填】
	 * @param path 扫码进入的小程序页面路径，最大长度 128 字节，不能为空【必填】
	 * @param width 二维码的宽度，单位 px。最小 280px，最大 1280px 默认值【430】
	 * {@link Description} 与 getWXACode 总共生成的码数量限制为 100,000，请谨慎调用
	 * @return
	 */
	public byte[] createwxaqrcode(String access_token,String path,int width);
}
