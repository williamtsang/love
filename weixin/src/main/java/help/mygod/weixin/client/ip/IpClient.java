
package help.mygod.weixin.client.ip;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import help.mygod.weixin.common.access.AccessTokenClient;
import help.mygod.weixin.common.util.RestUtil;


/**
 * 获取微信服务器IP地址
 * @author Administrator
 *
 */
public class IpClient{
	private static Logger logger = LogManager.getLogger(IpClient.class);
	/**
	 * API地址
	 */
	public final static String IP_URL = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=";

	public static List<String> get() {
		if (logger.isInfoEnabled()) {
			logger.info("获取微信服务器IP地址");
		}

		IpRsp temp = RestUtil.get(IP_URL + AccessTokenClient.getInstance().getAccessToken(),IpRsp.class);
		if(null != temp)
			return temp.getIp_list();
		return null;
	}
	
	public static void main(String[] args) {
		List<String> temps = get();
		for(String temp : temps){
			System.out.println(temp);
		}
	}
}
