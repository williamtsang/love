package help.mygod.weixin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import help.mygod.weixin.dao.SendMessageMapper;
import help.mygod.weixin.entity.SendMessage;
import help.mygod.weixin.entity.SendMessageExample;

@Service
public class WeixinService{
	
	@Autowired
	private SendMessageMapper sendMessageMapper;

	
	public List<SendMessage> selectSendMessageList(SendMessageExample example) {
		// TODO Auto-generated method stub
		
		return sendMessageMapper.selectByExample(example);
	}

	
	public List<SendMessage> selectSendMessageList(String keyword) {
		SendMessageExample example = new SendMessageExample();
		example.createCriteria().andKeywordEqualTo(keyword);
		return sendMessageMapper.selectByExample(example);
	}

}
