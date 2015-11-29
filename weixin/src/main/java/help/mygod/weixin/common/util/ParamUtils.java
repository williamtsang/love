package help.mygod.weixin.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;


/**
 * @author JoelLeung
 */
public class ParamUtils {
	

	public static String getString(HttpServletRequest request, String paramName, String defaultValue) {
		String result = request.getParameter(paramName);

		if (result == null || "".equals(result.trim())) result = defaultValue;

		result = escape(result);

		return result;
	}

	public static Date getDate(HttpServletRequest request, String paramName, Date defaultValue) {
		String ori = request.getParameter(paramName);
		if (StrUtil.isBlank(ori)) return defaultValue;

		try {
			return DateUtils.convertStrToDate(ori);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static String getDateString(HttpServletRequest request, String paramName) {
		String result = request.getParameter(paramName);

		if (result == null || "".equals(result.trim())) {
			result = "";
		} else {
			result = result.replaceAll("-", "");
			result = result.replaceAll("/", "");
		}

		return result;
	}

	public static Date getDate(HttpServletRequest request, String paramName, Date defaultValue, String format) {
		String ori = request.getParameter(paramName);
		if (StrUtil.isBlank(ori)) return defaultValue;

		try {
			return DateUtils.convertStrToDate(ori, format);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	@SuppressWarnings("rawtypes")
	public static String getParameterIgnoreCase(HttpServletRequest request, String paramName) {
		for (Enumeration em = request.getParameterNames(); em.hasMoreElements();) {
			String name = (String) em.nextElement();
			if (name.equalsIgnoreCase(paramName)) return request.getParameter(name);
		}
		return null;
	}

	public static String getString(HttpServletRequest request, String paramName) {
		return getString(request, paramName, "");
	}

	public static String[] getStrings(HttpServletRequest request, String paramName) {
		return request.getParameterValues(paramName);
	}

	public static Integer getInteger(HttpServletRequest request, String paramName, Integer defaultValue) {
		Integer result = null;
		if (paramName == null || paramName.trim().length() == 0) 
			return defaultValue;
		
		String paramValue = request.getParameter(paramName);		
		if(StrUtil.isBlank(paramValue))
			return defaultValue;
		try {
			result = Integer.valueOf(paramValue);
		}
		catch (NumberFormatException e ) {
			e.printStackTrace();
			result = defaultValue;
		}
		return result;
	}

	public static int getInt(HttpServletRequest request, String paramName, int defaultValue) {
		if (paramName == null || paramName.trim().length() == 0) 
			return defaultValue;
		
		String paramValue = request.getParameter(paramName);
		if(StrUtil.isBlank(paramValue))
			return defaultValue;
		try {
			return Integer.parseInt(request.getParameter(paramName));
		}
		catch (NumberFormatException e ) {
			e.printStackTrace();
		}
		return defaultValue;
	}

	public static float getFloat(HttpServletRequest request, String paramName, int defaultValue) {
		if (paramName == null || paramName.trim().length() == 0) 
			return defaultValue;
		
		String paramValue = request.getParameter(paramName);
		if(StrUtil.isBlank(paramValue))
			return defaultValue;
		
		return Float.parseFloat(request.getParameter(paramName));
	}
	
	public static long getLong(HttpServletRequest request, String paramName, long defaultValue) {
		try {
			if (paramName == null || paramName.trim().length() == 0) return defaultValue;			
			return Long.parseLong(request.getParameter(paramName));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static Integer[] getIntegers(HttpServletRequest request, String paramName, Integer defaultValue) {
		String[] temp = request.getParameterValues(paramName);
		if (temp != null) {
			Integer[] result = new Integer[temp.length];
			for (int i = 0; i < result.length; i++)
				try {

					result[i] = Integer.valueOf(temp[i]);
				} catch (NumberFormatException e) {
					result[i] = defaultValue;
				}
			return result;
		} else
			return new Integer[0];
	}

	public static Long[] getLongs(HttpServletRequest request, String paramName, Integer defaultValue) {
		String[] temp = request.getParameterValues(paramName);
		if (temp != null) {
			Long[] result = new Long[temp.length];
			for (int i = 0; i < result.length; i++)
				try {
					result[i] = Long.valueOf(temp[i]);
				} catch (NumberFormatException e) {
					String de = String.valueOf(defaultValue);
					long dd = Long.valueOf(de);
					result[i] = dd;
				}
			return result;
		} else
			return new Long[0];
	}
	
	public static Long[] getLongs(HttpServletRequest request, String paramName, String split) {
		String temp = request.getParameter(paramName);
		if (temp != null) {
			String[] a = temp.split(split);
			if(a == null || a.length == 0)
				return new Long[0];
			
			Long[] res = new Long[a.length];
			for(int i = 0; i < a.length; i++){
				res[i] = Long.parseLong(a[i]);
			}
			
			return res;
		} else
			return new Long[0];
	}

	public static BigDecimal getBigDecimal(HttpServletRequest request, String paramName, BigDecimal defaultValue) {
		BigDecimal result = null;
		try {
			result = new BigDecimal(request.getParameter(paramName));
		} catch (RuntimeException e) {
			result = defaultValue;
		}
		return result;
	}

	public static Boolean getBoolean(HttpServletRequest request, String paramName, Boolean defaultValue) {
		Boolean result = null;
		try {
			result = Boolean.valueOf(request.getParameter(paramName));
		} catch (NumberFormatException e) {
			result = defaultValue;
		}
		return result;
	}

	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) for (Cookie cookie : cookies)
			if (cookie.getName().equals(name)) return cookie.getValue();
		return null;
	}

	public static void setCookieValue(HttpServletRequest request, String name, String value) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) for (Cookie cookie : cookies)
			if (cookie.getName().equals(name)) cookie.setValue(value);
	}
	
	public static String[] getStrings(HttpServletRequest request, String param, String split){
		String raw = request.getParameter(param);
		if(raw != null){
			String[] strs = raw.split(split);
			
			if(strs == null)
				return new String[0];
			
			return strs;
		}else
			return new String[0];
	}
	
	/**
	 * 生成商品编码过滤条件（尚不安全，未考虑sql注入）
	 * @param args
	 * @return
	 */
	public static String getNumberFilter(String[] args,String field) {
		if(args.length == 0 ) {
			return "";
		}
		if(args.length == 1 && (StringUtils.isBlank(args[0])||"undefined".equalsIgnoreCase(args[0]))) {
			return "";
		}
		StringBuffer bs = new StringBuffer();
		bs.append("AND (");
		for (int i = 0; i < args.length; i++) {
			if(i > 0) {
				bs.append(" OR ");
			}
			if(args[i].indexOf("--") > 0) {
				String[] number = args[i].split("--");
				bs.append("(");
				bs.append("UPPER(").append(field).append(") >= '" + number[0].trim().toUpperCase()
						+ "' and UPPER(").append(field).append(") <= '" + number[1].trim().toUpperCase() + "')");
				//bs.append(" OR (UPPER(").append(field).append(") = '" + args[i].trim().toUpperCase() + "')");
			} else {
				bs.append("UPPER(").append(field).append(") = '" + args[i].trim().toUpperCase() + "'");
			}
		}
		bs.append(")");
		return bs.toString();
	}
	
	public static String escape(String html) {

		if (html == null) return null;

		String result = html.replace("<", "");
		result = result.replace(">", "");
		result = result.replace("&", "");
		result = result.replace("\"", "");
		result = result.replace("'", "");
		return result;
	}

	/**
	 * 拼凑地址
	 */
	public static String getFundUrl(HttpServletRequest request) {
		return getFundUrl(request, null);
	}

	/**
	 * 拼凑地址
	 * 
	 * @param request
	 * @param jsp 目标地址
	 * @return
	 */
	public static String getFundUrl(HttpServletRequest request, String jsp) {
		String targetid = ParamUtils.getString(request, "targetid", "");
		String prdcode = ParamUtils.getString(request, "prdcode", "");

		StringBuffer sb = new StringBuffer();

		if (!StringUtils.isEmpty(jsp)) {
			sb.append(jsp + ".jsp?");
		}

		sb.append("targetid=").append(targetid);

		if (!StringUtils.isEmpty(prdcode)) {
			sb.append("&");
			sb.append("prdcode=").append(prdcode);
		}

		return sb.toString();
	}
	
	/**
	 * 购买
	 * 
	 * @param request
	 * @return
	 */
	public static String getPurchaseUrl(HttpServletRequest request) {
		String targetid = ParamUtils.getString(request, "targetid", "");
		String prdcode = ParamUtils.getString(request, "prdcode", "");

		StringBuffer sb = new StringBuffer();
		sb.append("kdb/funds.do?action=goto_buy&");

		sb.append("targetid=").append(targetid);

		if (!StringUtils.isEmpty(prdcode)) {
			sb.append("&");
			sb.append("prdcode=").append(prdcode);
		}

		return sb.toString();
	}
	
	/**
	 * 赎回
	 * 
	 * @param request
	 * @return
	 */
	public static String getRedeemUrl(HttpServletRequest request) {
		String targetid = ParamUtils.getString(request, "targetid", "");
		String prdcode = ParamUtils.getString(request, "prdcode", "");

		StringBuffer sb = new StringBuffer();
		sb.append("kdb/funds.do?action=goto_redeem&");

		sb.append("targetid=").append(targetid);

		if (!StringUtils.isEmpty(prdcode)) {
			sb.append("&");
			sb.append("prdcode=").append(prdcode);
		}

		return sb.toString();
	}
	
	/**
	 * 产品详情
	 * 
	 * @param request
	 * @return
	 */
	public static String getPrdDetailUrl(HttpServletRequest request) {
		String targetid = ParamUtils.getString(request, "targetid", "");
		String prdcode = ParamUtils.getString(request, "prdcode", "");

		StringBuffer sb = new StringBuffer();
		sb.append("nologin/funds.do?action=prd_detail&");

		sb.append("targetid=").append(targetid);

		if (!StringUtils.isEmpty(prdcode)) {
			sb.append("&");
			sb.append("prdcode=").append(prdcode);
		}

		return sb.toString();
	}
	
	/**
	 * 
	 * @param request
	 * @param sourceUrl
	 * @param fromUrl
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String buildAwareUrl(HttpServletRequest request, String sourceUrl, String fromUrl)
			throws UnsupportedEncodingException {
		String queryString = request.getQueryString();

		String url = null;
		if (StringUtils.isBlank(fromUrl)) {
			url = sourceUrl;
		} else {
			String encodeUrl = null;

			if (StringUtils.isBlank(queryString)) {
				encodeUrl = URLEncoder.encode(fromUrl, "utf-8");
			} else {
				encodeUrl = URLEncoder.encode(fromUrl + "?" + queryString, "utf-8");
			}

			url = sourceUrl + "?url=" + encodeUrl;
		}

		return url;
	}

	
	public static double getDouble(HttpServletRequest request, String paramName, double defaultValue) {
		Double result = null;
		if (paramName == null || paramName.trim().length() == 0) 
			return defaultValue;
		
		String paramValue = request.getParameter(paramName);		
		if(StrUtil.isBlank(paramValue))
			return defaultValue;
		try {
			result = Double.parseDouble(paramValue);
		}
		catch (NumberFormatException e ) {
			e.printStackTrace();
			result = defaultValue;
		}
		return result;
	}
}
