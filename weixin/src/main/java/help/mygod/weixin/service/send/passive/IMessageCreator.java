package help.mygod.weixin.service.send.passive;

import java.io.Serializable;

/**
 * 消息接口：所以需要实现发送的消息都需要实现此接口
 * @author RD_guowei_liu
 *
 */
public interface IMessageCreator extends Serializable{
	/**
	 * 转化为消息
	 * @return
	 */
	String createMessage();
}
