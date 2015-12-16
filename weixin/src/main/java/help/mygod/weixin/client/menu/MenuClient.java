
package help.mygod.weixin.client.menu;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import help.mygod.weixin.common.Constant;
import help.mygod.weixin.common.access.AccessTokenClient;
import help.mygod.weixin.common.util.RestUtil;

public class MenuClient{
	
	/**
	  * 1、click：点击推事件
		用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event	的结构给开发者（参考消息接口指南），并且带上按钮中开发者填写的key值，开发者可以通过自定义的key值与用户进行交互；
		2、view：跳转URL
		用户点击view类型按钮后，微信客户端将会打开开发者在按钮中填写的网页URL，可与网页授权获取用户基本信息接口结合，获得用户基本信息。
		3、scancode_push：扫码推事件
		用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），且会将扫码的结果传给开发者，开发者可以下发消息。
		4、scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框
		用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后，将扫码的结果传给开发者，同时收起扫一扫工具，然后弹出“消息接收中”提示框，随后可能会收到开发者下发的消息。
		5、pic_sysphoto：弹出系统拍照发图
		用户点击按钮后，微信客户端将调起系统相机，完成拍照操作后，会将拍摄的相片发送给开发者，并推送事件给开发者，同时收起系统相机，随后可能会收到开发者下发的消息。
		6、pic_photo_or_album：弹出拍照或者相册发图
		用户点击按钮后，微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。用户选择后即走其他两种流程。
		7、pic_weixin：弹出微信相册发图器
		用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，将选择的相片发送给开发者的服务器，并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息。
		8、location_select：弹出地理位置选择器
		用户点击按钮后，微信客户端将调起地理位置选择工具，完成选择操作后，将选择的地理位置发送给开发者的服务器，同时收起位置选择工具，随后可能会收到开发者下发的消息。

	 */
	private final static String POST_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
	/**
	 * 使用接口创建自定义菜单后，开发者还可使用接口查询自定义菜单的结构。 
	 */
	private final static String GET_QUERY_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=";

	/**
	 * 删除MENU
	 */
	private final static String DELETE_QUERY_URL="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=";
	
	public static void main(String [] args) throws UnsupportedEncodingException{
		//System.out.println(URLEncoder.encode("http://william.mygod.help/m/weixinOAuth?scheme=https","utf-8"));
		//System.out.println(URLEncoder.encode("index?app_type=co","utf-8"));
		//System.out.println(URLEncoder.encode("index?app_type=p","utf-8")); 
		//System.out.println(URLEncoder.encode(URLEncoder.encode("index?app_type=co","utf-8"),"utf-8"));
		
		deleteMenu();
		createMenu2();
		queryMenu();
//		deleteMenu();
		//queryMenu();
		//https://william.mygod.help/m/#co/home
		//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx36d6366ae9633b68&redirect_uri=https://william.mygod.help/m/weixinOAuth&response_type=code&scope=snsapi_base&state=co/home#wechat_redirect
	}
	
	
   public static void queryMenu(){
		String result = RestUtil.getJson(GET_QUERY_URL + AccessTokenClient.getInstance().getAccessToken());
		System.out.println(result);
   }
   
   public static void  deleteMenu(){
		String result = RestUtil.getJson(DELETE_QUERY_URL + AccessTokenClient.getInstance().getAccessToken());
		System.out.println(result);
   }
   public static void createMenu2() throws UnsupportedEncodingException{
	   String menuJson= 
			    "{"
			        + "\"button\": ["
			            + "{"
			                + "\"type\": \"view\"," 
			                + "\"name\": \"曾文亮\"," 
			                //+ "\"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Constant.APP_ID + "&redirect_uri=" + URLEncoder.encode("http://william.mygod.help/m/weixinOAuth?type=1&scheme=https","utf-8") + "&response_type=code&scope=snsapi_base&state=bs201501#wechat_redirect\""
			                //+ "\"url\": \"http://mp.weixin.qq.com/s?__biz=MzA4MDkyNDgzNw==&mid=204364536&idx=1&sn=a42294bb5315ac5a870d03ea2719b75d#rd\""
			                //+ "\"url\": \"http://william.mygod.help/spread/ali20150312\""
			                //+ "\"url\": \"http://william.mygod.help/m/#bs201508 \""
			                //+ "\"url\": \"http://william.mygod.help/m/#bs201506\""
			                + "\"url\": \"http://william.mygod.help/weixin/index.html\""
			                //+ "\"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Constant.APP_ID + "&redirect_uri=" + URLEncoder.encode("http://william.mygod.help/m/weixinOAuth?type=1&scheme=https","utf-8") + "&response_type=code&scope=snsapi_base&state=" + URLEncoder.encode("bs201506","utf-8") + "#wechat_redirect\""
			            + "},"
			            + "{"
			                + "\"name\": \"我要赚钱\"," 
			                + "\"sub_button\": ["
				                    + "{"
			                        + "\"type\": \"view\"," 
			                        + "\"name\": \"网银看板成长社区\"," 
			                        + "\"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Constant.APP_ID + "&redirect_uri=" + URLEncoder.encode("http://william.mygod.help/m/weixinOAuth?scheme=https","utf-8") + "&response_type=code&scope=snsapi_base&state=" + URLEncoder.encode("index?app_type=co","utf-8") + "#wechat_redirect\""
				                    + "},"
				                    + "{"
			                        + "\"type\": \"view\"," 
			                        + "\"name\": \"个人理财\"," 
			                        + "\"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Constant.APP_ID + "&redirect_uri=" + URLEncoder.encode("http://william.mygod.help/m/weixinOAuth?scheme=https","utf-8") + "&response_type=code&scope=snsapi_base&state=" + URLEncoder.encode("index?app_type=p","utf-8") + "#wechat_redirect\""
			                    + "}," 
			                    + "{"
				                    + "\"type\": \"click\"," 
				                    + "\"name\": \"收益指南\"," 
				                    + "\"key\": \"BENEFIT_QUERY\""
			                    + "}," 
			                    /*+ "{"
			                    + "\"type\": \"view\"," 
			                    + "\"name\": \"下载客户端\"," 
			                    + "\"url\": \"http://fusion.qq.com/app_download?appid=1101479358&platform=qzone&via=QZ.MOBILEDETAIL.QRCODE\""
			                    + "},"*/ 
			                    + "{"
			                        + "\"type\": \"view\"," 
			                        + "\"name\": \"安全保障\"," 
			                        + "\"url\": \"http://william.mygod.help/m/loan/all-item.html\""
			                    + "}"
			                + "]"
			            + "},"
			            + "{"
			                + "\"name\": \"我要贷款\"," 
			                + "\"sub_button\": ["
			                    /*+ "{"
			                        + "\"type\": \"view\"," 
			                        + "\"name\": \"产品精选\"," 
			                        + "\"url\": \"http://william.mygod.help/m/loan/item-detail.html\""
			                    + "},"
			                    + "{"
			                        + "\"type\": \"view\"," 
			                        + "\"name\": \"金蝶阿里信用贷\"," 
			                        + "\"url\": \"http://william.mygod.help/m/loan/ali.html\""
			                    + "},"
			                    + "{"
			                        + "\"type\": \"view\"," 
			                        + "\"name\": \"金蝶招行信用贷\"," 
			                        + "\"url\": \"http://william.mygod.help/m/loan/zhaoshang.html\""
			                    + "}," 
			                    + "{"
			                        + "\"type\": \"view\"," 
			                        + "\"name\": \"KIS业务平台\"," 
			                        + "\"url\": \"http://mob.cmcloud.cn/kisplus_wx/bind_wx/AcctInfoList.html?fromtag=kisipenid=oTUmtt8B8wUghp948WVFYL3kzVKo\""
			                    + "}",*/
				                /*+ "{"
			                        + "\"type\": \"view\"," 
			                        + "\"name\": \"金蝶员工公职贷\"," 
			                        + "\"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Constant.APP_ID + "&redirect_uri=" + URLEncoder.encode("http://william.mygod.help/m/weixinOAuth?type=1&scheme=https","utf-8") + "&response_type=code&scope=snsapi_base&state=" + URLEncoder.encode("mloan/staff_loan","utf-8") + "#wechat_redirect\""
			                    + "},"*/
				                + "{"
			                        + "\"type\": \"view\"," 
			                        + "\"name\": \"蚂蚁微贷大数贷\"," 
			                        + "\"url\": \"http://william.mygod.help/m/#mloan/product_detail?productId=84\""
			                    + "},"
			                    + "{"
			                        + "\"type\": \"view\"," 
			                        + "\"name\": \"平安银行数据贷\"," 
			                        + "\"url\": \"http://william.mygod.help/m/#mloan/product_detail?productId=142\""
			                    + "}," 
			                    + "{"
			                        + "\"type\": \"view\"," 
			                        + "\"name\": \"贷款产品集中营\"," 
			                        + "\"url\": \"http://william.mygod.help/m/#mloan/product\""
			                    + "}"
			                + "]"
			            + "}"
			        + "]"
			    + "}";
		String result = RestUtil.postJson(POST_CREATE_URL + AccessTokenClient.getInstance().getAccessToken(),menuJson);;
		System.out.println(result);
	}
	
}
