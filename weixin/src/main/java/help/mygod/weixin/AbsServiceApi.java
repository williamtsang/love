package help.mygod.weixin;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLStreamException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import help.mygod.weixin.common.Constant;
import help.mygod.weixin.common.util.MessageUtil;
import help.mygod.weixin.common.util.SignUtil;
import help.mygod.weixin.service.BaseMessage;
import help.mygod.weixin.service.Handlers;
import help.mygod.weixin.service.receive.MsgTypeEnum;
import help.mygod.weixin.service.receive.event.EventEnum;
import help.mygod.weixin.service.receive.event.menu.EventKeyEnum;


public class AbsServiceApi {
	private static Logger logger = LogManager.getLogger(AbsServiceApi.class);
	
	public String hello(){
		return "hello";
	}
	
	public String service(HttpServletRequest request) {
		if (isLegal(request)) {
            //绑定微信服务器成功
            return request.getParameter("echostr");
        } else {
            //绑定微信服务器失败
            return Constant.EMPTY_STRING;
        }
	}
	
	protected boolean isLegal(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        return SignUtil.checkSignature(signature, timestamp, nonce);
    }
	
	/**微信服务总入口
	 * @param req
	 * @return
	 */
	public String service(String xmlText) {
		if(logger.isInfoEnabled()){
			logger.info("接受微信服务请求，请求参数为" + xmlText);
		}
		
		Map<String, Object> reqMap;
		BaseMessage baseMessage;
		try {
			reqMap = MessageUtil.parseXml(xmlText);//解析所有微信请求文本
			baseMessage = MessageUtil.getBaseMessage(reqMap);
			
			if(MsgTypeEnum.event.name().equals(baseMessage.getMsgType())){//接收事件推送
				
				String event = ((String)reqMap.get("Event")).toUpperCase();
				if(logger.isInfoEnabled())
					logger.info("event 的值为：" + event);
				
				if(EventEnum.CLICK.name().equals(event)){//自定义菜单再细分
					
					String eventKey = ((String)reqMap.get("EventKey")).toUpperCase();
					logger.info("eventKey 的值为：" + eventKey);

					if (null != eventKey && Handlers.getHandlers().get(eventKey) != null) {
						return Handlers.getHandlers().get(eventKey).handle(baseMessage,reqMap);
					}
					
				}else if(null != event && null != Handlers.getHandlers().get(event)){
					return Handlers.getHandlers().get(event).handle(baseMessage,reqMap);
				}
				
			}else{//接受普通信息
				String msgType = baseMessage.getMsgType().toUpperCase();
				if(logger.isInfoEnabled())
					logger.info("baseMessage.getMsgType().toUpperCase() 的值为：" + msgType);

				if (null != msgType && Handlers.getHandlers().get(msgType) != null) {
					return Handlers.getHandlers().get(msgType).handle(baseMessage,reqMap);
				}
			}
			
			
			//如果前面没返回 则返回默认未实现信息
			return Handlers.getHandlers().get(EventKeyEnum.KEY_UNREALIZE.name().toUpperCase()).handle(baseMessage,reqMap);
			
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e.getCause());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e.getCause());
		}
		return Constant.EMPTY_STRING;
	}
	
	
	
}
