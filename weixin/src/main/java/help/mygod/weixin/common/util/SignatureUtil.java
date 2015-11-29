package help.mygod.weixin.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class SignatureUtil {

	/**
	 * 生成消息签名
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 * @throws AesException
	 */
	public static String createSignature(String noncestr, String jsapi_ticket,
			long timestamp, String url) throws NoSuchAlgorithmException, UnsupportedEncodingException{
			Map<String, String> paramMap = new TreeMap<String, String>();
	        paramMap.put("jsapi_ticket", jsapi_ticket);
	        paramMap.put("noncestr", noncestr);
	        paramMap.put("timestamp", Long.toString(timestamp));
	        paramMap.put("url", url);

	        StringBuilder sb = new StringBuilder();
	        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
	            sb.append("&").append(entry.toString());
	        }
	        
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(sb.toString().getBytes("UTF-8"));
			return byteToHex(crypt.digest());
	}
	
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	public static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	public static long create_timestamp() {
		return System.currentTimeMillis() / 1000;
	}
}
