package help.mygod.weixin;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import help.mygod.weixin.client.oauth.UserInfoRsp;
import help.mygod.weixin.common.Constant;
import help.mygod.weixin.common.util.OauthUtil;
import help.mygod.weixin.common.util.StrUtil;


public abstract class AbsWebApi {
	private static Logger logger = LogManager.getLogger(AbsWebApi.class.getName());
	
	public String hello(){
		return "hello";
	}
	
	/**
	 * OAUTH授权时，微信服务器重定向地址
	 * @param code
	 * 			微信传递的CODE，用来换取access_token
	 * @param state
	 * 			自己填写的校验参数
	 * @param redirectUri
	 * 			绑定自动登录后的跳转地址
	 * @param response
	 * @throws IOException
	 */
	public void execute(String code,String state,String redirectUri,int type,HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			if(!Constant.OAUTH_STATE.equals(state)){
				if(logger.isInfoEnabled())
					logger.info("校验参数state为空或者state校验不通过，请求失败，请求的state为" + state);
				
				return;
			}
			if(StrUtil.isNotBlank(code)){
				if(logger.isInfoEnabled())
					logger.info("微信授权成功获得code，code为" + code );
				
				
				HttpSession session = request.getSession();
				String openid = (String)session.getAttribute("weixin_openid");
				if(StrUtil.isBlank(openid)){
					openid = OauthUtil.getOpenId(code);
					session.setAttribute("weixin_openid", openid);
				}
				
				weixinRedirect(response,openid, redirectUri,type);
			}else{
				if(logger.isInfoEnabled())
					logger.info("跳转参数code不能为空！");
				response.sendRedirect(getErrorPage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			 logger.error(e);
			 response.sendRedirect(getErrorPage());
		}
	}
	
	private void weixinRedirect(HttpServletResponse response,String openid,String redirectUri,int type) throws Exception{
		if(bindByOpenid(openid)){
			if(logger.isInfoEnabled())
				logger.info("此用户已绑定");
			response.sendRedirect(redirectUri);
		}else{//还没绑定
			switch (type) {
			case 0:
				toBind(response,redirectUri);
				break;
			case 1:
				toSame(response,redirectUri);
				break;

			default:
				toBind(response,redirectUri);
				break;
			}
		}
	}
	private  void toBind(HttpServletResponse response,String redirectUri) throws IOException{
		String url = getBindUrl(redirectUri);
		if(logger.isInfoEnabled())
			logger.info("此用户还没绑定,跳转到提示绑定页面为" + url);
		response.sendRedirect(url);
	}
	
	private void toSame(HttpServletResponse response,String redirectUri) throws IOException{
		if(logger.isInfoEnabled())
			logger.info("此用户还没绑定，跳转到请求页面为" + redirectUri);
		response.sendRedirect(redirectUri);
	}
	
	
	protected abstract boolean bindByOpenid(String openid);
	
	/**
	 * 不绑定时，跳转到的绑定页面
	 * @param openid
	 * 			微信帐号ID
	 * @param redirectUri
	 * 			绑定成功后跳转到的地址
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	protected String getBindUrl(String redirectUri) throws UnsupportedEncodingException {
		return "/m/#login?redirect=" + URLEncoder.encode(redirectUri,"utf-8");
	}
	
	protected String getErrorPage(){
		return "/";
	}
	
	
	/**
	 * OAUTH授权时，微信服务器重定向地址
	 * @param code
	 * 			微信传递的CODE，用来换取access_token
	 * @param state
	 * 			自己填写的校验参数
	 * @param redirectUri
	 * 			绑定自动登录后的跳转地址
	 * @param type
	 * 			不绑定时的跳转判断，0为跳转到绑定页面，1为跳转到redirectUri（此时没登录）
	 * @param response
	 * @throws IOException
	 */
	public void activity(String code,String state,String redirectUri,MultivaluedMap<String, String> parameters,HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			if(null == state || !Constant.OAUTH_STATE.equals(state)){
				if(logger.isInfoEnabled())
					logger.info("校验参数state为空或者state校验不通过，请求失败，请求的state为" + state);
				
				return;
			}
			if(null != code){
				if(logger.isInfoEnabled())
					logger.info("微信授权成功获得code，code为" + code );
				
				HttpSession session = request.getSession();
				UserInfoRsp userInfoRsp = (UserInfoRsp)session.getAttribute("weixin_userInfoRsp");
				if(null == userInfoRsp){
					userInfoRsp = OauthUtil.getUserInfo(code);
					session.setAttribute("weixin_userInfoRsp", userInfoRsp);
					session.setAttribute("weixin_openid", userInfoRsp.getOpenid());
				}
				
				String appendParams = afterUserInfo(userInfoRsp,parameters);
				response.sendRedirect(appendParams(redirectUri,appendParams));
			}else{
				if(logger.isInfoEnabled())
					logger.info("跳转参数code不能为空");
				response.sendRedirect(getErrorPage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			 logger.error(e);
			 response.sendRedirect(getErrorPage());
		}
	}
	
	/**
	 * 追加参数
	 * @param url
	 * @param params
	 * @return
	 */
	private String appendParams(String url,String params){
		String temp = url;
		if(StrUtil.isNotBlank(params)){
			if(-1 != url.indexOf("=")){//有=号
				temp += "&";
			}else{
				temp += "?";
			}
			return temp += params;
		}
		return temp;
	}
	protected abstract String afterUserInfo(UserInfoRsp userInfo,MultivaluedMap<String, String> parameters) ;
	
}
