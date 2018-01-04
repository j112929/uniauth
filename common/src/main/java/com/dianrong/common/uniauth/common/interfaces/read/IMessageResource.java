package com.dianrong.common.uniauth.common.interfaces.read;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.dianrong.common.uniauth.common.bean.Response;
import com.dianrong.common.uniauth.common.bean.dto.MessageDto;
import com.dianrong.common.uniauth.common.bean.request.MessageParam;

@Path("message")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public interface IMessageResource {
	  @POST
	  @Path("send")
	  Response<MessageDto> sendMessage(MessageParam param);
}
