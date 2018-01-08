package com.dianrong.common.uniauth.cas;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.dianrong.common.uniauth.cas.service.msg.MessageResponse;
import com.dianrong.common.uniauth.cas.service.msg.MessageVaildCodeRequest;
import com.dianrong.common.uniauth.cas.service.msg.SMSService;

public class MsgTest {
	private static SMSService messageService = new SMSService();
	public static  void send(){
		MessageVaildCodeRequest msg = new MessageVaildCodeRequest();
		msg.setMobilenumber("13120729219");
//		msg.setCategory("HUATENG");
//		msg.setType("CAS_LOGIN_VALID_CODE");
//		msg.setVaildCode("0101");
		MessageResponse sendMessage = messageService.sendMessage(msg);
		System.err.println(JSON.toJSON(sendMessage));
	}
	public static void main(String[] args) {
		send();
	}
}

