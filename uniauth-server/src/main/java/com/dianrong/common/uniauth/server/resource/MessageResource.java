package com.dianrong.common.uniauth.server.resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.dianrong.common.uniauth.common.bean.Response;
import com.dianrong.common.uniauth.common.bean.dto.MessageDto;
import com.dianrong.common.uniauth.common.bean.request.MessageParam;
import com.dianrong.common.uniauth.common.interfaces.read.IMessageResource;
import com.dianrong.common.uniauth.server.service.msg.IMessageService;
import com.dianrong.common.uniauth.server.service.msg.MessageResponse;
import com.dianrong.common.uniauth.server.service.msg.MessageVaildCodeRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api("发送短信操作接口")
@RestController
public class MessageResource implements IMessageResource{
	@Autowired
	private IMessageService messageService;
	@ApiOperation("发送短信验证码接口")
	@Override
	public Response<MessageDto> sendMessage(MessageParam param) {
		MessageVaildCodeRequest messageVaildCodeRequest = new MessageVaildCodeRequest();
		BeanUtils.copyProperties(param, messageVaildCodeRequest);
		MessageResponse resp = messageService.sendMessage(messageVaildCodeRequest);
		MessageDto msgDto = new MessageDto();
		BeanUtils.copyProperties(resp, msgDto);
		return new Response<MessageDto>(msgDto);
	}

}
