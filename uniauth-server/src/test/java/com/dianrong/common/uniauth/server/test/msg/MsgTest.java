package com.dianrong.common.uniauth.server.test.msg;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.dianrong.common.uniauth.server.service.msg.IMessageService;
import com.dianrong.common.uniauth.server.service.msg.MessageResponse;
import com.dianrong.common.uniauth.server.service.msg.MessageServiceImpl;
import com.dianrong.common.uniauth.server.service.msg.MessageVaildCodeRequest;

public class MsgTest {
//	@Autowired
	private static IMessageService messageService = new MessageServiceImpl();
	@Test
	public  void send(){
		MessageVaildCodeRequest msg = new MessageVaildCodeRequest();
		msg.setMobilenumber("13120729219");
//		msg.setCategory("HUATENG");
//		msg.setType("CAS_LOGIN_VALID_CODE");
//		msg.setVaildCode("0101");
		MessageResponse sendMessage = messageService.sendMessage(msg);
		System.err.println(JSON.toJSON(sendMessage));
	}
	public static void main(String[] args) {
//		send();
	}
}
