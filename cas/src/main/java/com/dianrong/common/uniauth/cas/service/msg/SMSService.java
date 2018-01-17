package com.dianrong.common.uniauth.cas.service.msg;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dianrong.common.uniauth.cas.util.HttpClientUtils;


/**
 * <p>短信发送接口实现类（实现方法为sendMessage(MessageRequest  msg))<br>
 * @version 1.0
 */
@Service("smsService")
public class SMSService {
		
	private static final Logger logger = LoggerFactory.getLogger(SMSService.class);

	/**
	 * . zk配置bean
	 */
	@Resource(name = "uniauthConfig")
	private Map<String, String> allZkNodeMap;
//    private String url = "http://message.sit01.shenmajr.com/";//test
//    private String url = "http://message.shenmajr.com/";//pro
    

	public MessageResponse sendMessage(MessageVaildCodeRequest messageVaildCodeRequest) {
		  /**
         * 用于接收返回值的对象
         */
        MessageResponse messageResponse = null;
        String result = "";
        try {
            logger.info("调用短信接口开始,输入参数：{}", JSONObject.toJSONString(messageVaildCodeRequest));
            /**
             * 调用webclient类发送请求并接收返回结果
             */
            Map<String, String> map = new HashMap<>();
            if(messageVaildCodeRequest != null){
            	map.put("type", "CAS_LOGIN_VALID_CODE");
            	map.put("category", "HUATENG");
            	map.put("mobilenumber", "");
            	map.put("vaildCode", "");//生成4位随机数
            	if(StringUtils.isNotBlank(messageVaildCodeRequest.getType())){
            		map.put("type", messageVaildCodeRequest.getType());
            	}
            	if(StringUtils.isNotBlank(messageVaildCodeRequest.getCategory())){
            		map.put("category", messageVaildCodeRequest.getCategory());
            	}
            	if(StringUtils.isNotBlank(messageVaildCodeRequest.getMobilenumber())){
            		map.put("mobilenumber", messageVaildCodeRequest.getMobilenumber());
            	}
            	if(StringUtils.isNotBlank(messageVaildCodeRequest.getVaildCode())){
            		map.put("vaildCode", messageVaildCodeRequest.getVaildCode());
            	}
            }
            result = sendMsg(map);
            logger.info("输出参数：{}", result);
            messageResponse = JSONObject.parseObject(result, MessageResponse.class);
            /**
             * 将result转换为MessageResult对象
             */
            if ("0".equals(messageResponse.getCode())) {
                logger.info("短信发送成功");
            }
        } catch (IOException e) {
            logger.error(WsErrorCode.ERROR_CODE_8003, e);
            throw new BussinessRuntimeException(WsErrorCode.ERROR_CODE_8003, e);
        } catch (Exception e) {
			e.printStackTrace();
        } finally {
            logger.info("调用短信接口结束,输出参数：{}", result);
        }
        return messageResponse;
		
	}
	/***
     * 发送短信
	 * @throws Exception 
     */
    public String sendMsg(Map<String, String> reqDTO) throws Exception {
    	logger.info("短信发送参数:{}", JSON.toJSONString(reqDTO));
    	String url = allZkNodeMap.get("sms.send.url");
    	if(StringUtils.isNotBlank(url)){
	    	logger.info("accessUrl = {}", url+"send");
	    	String ret = HttpClientUtils.connect(url+"send", JSON.toJSONString(reqDTO), "POST", HttpClientUtils.getJsonHead());
	        logger.info("短信发送返回结果:{}", ret);
			return ret;
    	}else{
    		return null;
    	}
    }
    
    public String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;

    }

    public int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }

}