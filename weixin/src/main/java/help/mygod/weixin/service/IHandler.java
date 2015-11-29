package help.mygod.weixin.service;

import java.util.Map;

/**
 * 最终事件处理器接口类
 * @author RD_guowei_liu
 *
 */
public interface IHandler {
	
	
	/**事件处理 
	 * @param message
	 * @param xmlText
	 * @return
	 * @throws Exception
	 */
	public  String handle(BaseMessage baseMessage,Map<String, Object> map) throws Exception ;
	
}