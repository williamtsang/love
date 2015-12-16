package help.mygod.weixin.common.util;

import java.util.Random;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


/**
 * 字符串常用方法工具类
 *
 * @author peiyu
 */
public final class StrUtil {

    /**
     * 此类不需要实例化
     */
    private StrUtil() {
    }

    /**
     * 判断一个字符串是否为空，null也会返回true
     *
     * @param str 需要判断的字符串
     * @return 是否为空，null也会返回true
     */
    public static boolean isBlank(String str) {
        return null == str || "".equals(str.trim());
    }

    /**
     * 判断一个字符串是否不为空
     *
     * @param str 需要判断的字符串
     * @return 是否为空
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 判断一组字符串是否有空值
     *
     * @param strs 需要判断的一组字符串
     * @return 判断结果，只要其中一个字符串为null或者为空，就返回true
     */
    public static boolean hasBlank(String... strs) {
        if (null == strs || 0 == strs.length) {
            return true;
        } else {
            //这种代码如果用java8就会很优雅了
            for (String str : strs) {
                if (isBlank(str)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
	 * 利用反射将指定对象的属性打印成字符串形式！
	 * 
	 * @param obj
	 * @return
	 */
	public final static String reflectObj(Object obj) {
		if (obj == null)
			return "";
		return ReflectionToStringBuilder.reflectionToString(obj);
	}
	
	 /**
     * 
     * generateAccount 生成用户名
     * 
     */
	public final static String generateAccount() {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        int length = 4;
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 
     * generateAccount 生成用户名
     * 
     */
	public final static String generateNum() {
        String base = "0123456789";
        int length = 3;
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 
     * generateAccount 生成随机数
     * 
     */
	public final static String generateCodeNum() {
        String base = "0123456789";
        int length = 6;
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}