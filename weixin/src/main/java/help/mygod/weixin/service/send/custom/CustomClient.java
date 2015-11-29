package help.mygod.weixin.service.send.custom;

import java.util.List;

import help.mygod.weixin.common.access.AccessTokenClient;
import help.mygod.weixin.common.util.RestUtil;
import help.mygod.weixin.service.BaseMessage;

/**
 * 
 * 客服消息转换类
 * 
 * @author RD_guowei_liu
 * 
 */
public class CustomClient {

	/**
	 * 客服信息发送接口 参考http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF
	 */
	public static final String CUSTOME_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

	public static void sendMessage(BaseMessage message,List<String> sendMessages){
		for(String temp : sendMessages){
			RestUtil.postJson(CUSTOME_SEND_URL + AccessTokenClient.getInstance().getAccessToken(), temp,String.class);
		}
	}
	
}
