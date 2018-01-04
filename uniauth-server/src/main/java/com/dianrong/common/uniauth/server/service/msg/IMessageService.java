/**
 * ===============================================================================
 * The Shenma Internet Financial Information Software License
 *
 * Copyright (c) 2017 Shenma Internet Financial Information Service Co., Ltd.
 *
 * @Company:  什马金融 WWW.SHENMAJR.COM 
 *
 * @Project: shenma-samp-service
 *  
 * @Author:  Kaiyun.Fu
 * @Created at 2017年11月7日下午5:23:11
 * ===============================================================================
 */
 
package com.dianrong.common.uniauth.server.service.msg;


/**
 * 
 * <p>短信发送接口类(接口为：sendMessage(MessageRequest  msg))<br>
 * @Created at 2017年11月7日下午5:23:11
 */
public interface IMessageService {
    /**
     * <p>发送信息方法<br>
     * @param msg 发送请求bean
     * @return MessageResponse
     * @throws BussinessRuntimeException 业务运行异常
     */
	public MessageResponse sendMessage(MessageVaildCodeRequest messageVaildCodeRequest)throws BussinessRuntimeException;
	
	
}