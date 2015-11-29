package help.mygod.weixin.service;

import java.util.HashMap;

/**
 * 实际处理器
 *
 */
public class Handlers{

	/**
	 * 处理器
	 */
	public static final HashMap<String, IHandler> handlers = new HashMap<String, IHandler>();

	

	public static HashMap<String, IHandler> getHandlers() {
		return handlers;
	}

	/**
	 * 注册处理器
	 * 
	 * @param menuEventKeyEnum
	 * @param handler
	 */
	public static void registerHanlder(String key, IHandler handler) {
		handlers.put(key.toUpperCase(), handler);
	}

}
