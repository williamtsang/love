package help.mygod.weixin.common.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import help.mygod.weixin.service.BaseMessage;

/**
 * 消息工具类 用于解析微信平台消息xml报文
 * 
 * @author peiyu
 */
public final class MessageUtil {


	/**
	 * 此类不需要实例化
	 */
	private MessageUtil() {
	}

	/**
	 * 解析从微信服务器来的请求，将消息或者事件返回出去
	 * 
	 * @param request http请求对象
	 * @param token 用户设置的taken
	 * @param appId 公众号的APPID
	 * @param aesKey 用户设置的加密密钥
	 * @return 微信消息或者事件Map
	 * @throws XMLStreamException
	 */
	public static Map<String, Object> parseXml(String message) throws XMLStreamException {
		Map<String, Object> map = new HashMap<String, Object>();

		InputStream inputStream = new ByteArrayInputStream(message.getBytes());
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLEventReader reader = factory.createXMLEventReader(inputStream);
		while (reader.hasNext()) {
			XMLEvent event = reader.nextEvent();
			if (event.isStartElement()) {
				String tagName = event.asStartElement().getName().toString();
				if ("SendPicsInfo".equals(tagName)) {
					map.put(tagName, eventSendPicsInfo(reader));
				} else if ("SendLocationInfo".equals(tagName)) {
					map.put(tagName, eventSendLocationInfo(reader));
				} else if ("ScanCodeInfo".equals(tagName)) {
					map.put(tagName, eventScanCodePush(reader));
				} else if ("xml".equals(tagName)) {
					
				} else {
					map.put(tagName, reader.getElementText());
				}
			}
		}
		return map;
	}

	/**
	 * Event为pic_sysphoto, pic_photo_or_album, pic_weixin时触发
	 * 
	 * @param reader reader
	 * @return 读取结果
	 * @throws XMLStreamException
	 */
	protected static Map<String, Object> eventSendPicsInfo(XMLEventReader reader) throws XMLStreamException {
		Map<String, Object> sendPicsInfoMap = new HashMap<String, Object>();
		while (reader.hasNext()) {
			XMLEvent event = reader.nextEvent();
			if (event.isStartElement()) {
				String tagName = event.asStartElement().getName().toString();
				if ("Count".equals(tagName)) {
					sendPicsInfoMap.put(tagName, reader.getElementText());
				} else if ("PicList".equals(tagName)) {
					StringBuilder sb = new StringBuilder();
					while (reader.hasNext()) {
						XMLEvent event1 = reader.nextEvent();
						if (event1.isStartElement() && "PicMd5Sum".equals(event1.asStartElement().getName().toString())) {
							sb.append(reader.getElementText());
							sb.append(",");
						} else if (event1.isEndElement()
								&& "PicList".equals(event1.asEndElement().getName().toString())) {
							break;
						}
					}
					sendPicsInfoMap.put(tagName, sb.substring(0, sb.length()));
				}
			}
		}

		return sendPicsInfoMap;
	}

	/**
	 * Event为location_select时触发
	 * 
	 * @param reader reader
	 * @return 读取结果
	 * @throws XMLStreamException
	 */
	protected static Map<String, Object> eventSendLocationInfo(XMLEventReader reader) throws XMLStreamException {
		Map<String, Object> sendLocationInfo = new HashMap<String, Object>();
		while (reader.hasNext()) {
			XMLEvent event = reader.nextEvent();
			if (event.isStartElement()) {
				String tagName = event.asStartElement().getName().toString();
				sendLocationInfo.put(tagName, reader.getElementText());
			}
		}
		return sendLocationInfo;
	}

	/**
	 * Event为scancode_push, scancode_waitmsg时触发
	 * 
	 * @param reader reader
	 * @return 读取结果
	 * @throws XMLStreamException
	 */
	protected static Map<String, Object> eventScanCodePush(XMLEventReader reader) throws XMLStreamException {
		Map<String, Object> scanCodePush = new HashMap<String, Object>();
		while (reader.hasNext()) {
			XMLEvent event = reader.nextEvent();
			if (event.isStartElement()) {
				String tagName = event.asStartElement().getName().toString();
				scanCodePush.put(tagName, reader.getElementText());
			}
		}
		return scanCodePush;
	}
	
	/**
	 * 接收消息的基类
	 * 
	 * @param map
	 * 			接受消息解析结果
	 * @return
	 */
	public static BaseMessage getBaseMessage(Map<String, Object> map) {
		BaseMessage baseMessage = new BaseMessage();
		baseMessage.setFromUserName((String) map.get("FromUserName"));
        baseMessage.setToUserName((String) map.get("ToUserName"));
        baseMessage.setMsgType((String) map.get("MsgType"));
        baseMessage.setCreateTime(Long.parseLong(map.get("CreateTime").toString()));
		return baseMessage;
		
		
	}
}
