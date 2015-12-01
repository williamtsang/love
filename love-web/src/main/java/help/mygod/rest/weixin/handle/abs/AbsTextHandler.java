package help.mygod.rest.weixin.handle.abs;

import java.util.ArrayList;
import java.util.List;

import help.mygod.weixin.common.entity.Message;
import help.mygod.weixin.common.entity.MessageItem;
import help.mygod.weixin.entity.SendMessage;
import help.mygod.weixin.service.BaseMessage;
import help.mygod.weixin.service.IHandler;
import help.mygod.weixin.service.receive.MsgTypeEnum;
import help.mygod.weixin.service.send.custom.CustomClient;
import help.mygod.weixin.service.send.custom.CustomConverter;
import help.mygod.weixin.service.send.passive.PassiveConverter;

/**
 * 接受普通信息处理
 * @author Administrator
 *
 */
public abstract class AbsTextHandler implements IHandler {
	
	
	public String sendMessage(BaseMessage baseMessage,List<SendMessage> sendMessages){
		List<Message> messages = toMessage(sendMessages);
		Message temp = messages.remove(0);//被动响应第一条信息
		if(messages.size() > 0){//如果有多条信息，则异步通过客服信息发送
			CustomClient.sendMessage(baseMessage, CustomConverter.convertMessage(baseMessage, messages));
		}
		return PassiveConverter.convertMessage(baseMessage, temp);
	}
	
	private List<Message> toMessage(List<SendMessage> sendMessages){
		List<Message> messages = new ArrayList<Message>();
		Message message;
		String msgtype;
		List<MessageItem> items = new ArrayList<MessageItem>();
		for(SendMessage sendMessage : sendMessages){
			message = new Message();
			msgtype = sendMessage.getMsgtype();
			message.setMsgtype(msgtype);
			if(MsgTypeEnum.text.name().equals(msgtype)){//回复文本
				message.setContent(sendMessage.getContent());
			}else if(MsgTypeEnum.image.name().equals(msgtype) || MsgTypeEnum.voice.name().equals(msgtype)){//回复图片，语音
				message.setMediaid(sendMessage.getMediaid());
			}else if(MsgTypeEnum.video.name().equals(msgtype)){//回复视频
				message.setMediaid(sendMessage.getMediaid());
				message.setTitle(sendMessage.getTitle());
				message.setDescription(sendMessage.getDescription());
			}else if(MsgTypeEnum.music.name().equals(msgtype)){//回复音乐
				message.setTitle(sendMessage.getTitle());
				message.setDescription(sendMessage.getDescription());
				message.setMusicurl(sendMessage.getMusicurl());
				message.setHqmusicurl(sendMessage.getHqmusicurl());
				message.setThumbmediaid(sendMessage.getThumbmediaid());
			}else if(MsgTypeEnum.news.name().equals(msgtype)){//封装图文信息
				MessageItem item = new MessageItem();
				item.setTitle(sendMessage.getTitle());
				item.setDescription(sendMessage.getDescription());
				item.setPicUrl(sendMessage.getPicurl());
				item.setUrl(sendMessage.getUrl());
				items.add(item);
				continue;
			}
			
			messages.add(message);
		}
		
		if(!items.isEmpty()){//加载图文信息
			message = new Message();
			message.setMsgtype(MsgTypeEnum.news.name());
			message.setItems(items);
			messages.add(message);
		}
		
		
		return messages;
	}
}
