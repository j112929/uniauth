package com.dianrong.common.uniauth.cas.controller.msg;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dianrong.common.uniauth.cas.service.UserInfoManageService;
import com.dianrong.common.uniauth.cas.service.msg.MessageResponse;
import com.dianrong.common.uniauth.cas.service.msg.MessageVaildCodeRequest;
import com.dianrong.common.uniauth.cas.service.msg.SMSService;
import com.dianrong.common.uniauth.cas.util.WebScopeUtil;
import com.dianrong.common.uniauth.common.bean.Response;
import com.dianrong.common.uniauth.common.bean.dto.UserDto;
import com.dianrong.common.uniauth.common.cons.AppConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/msg")
public class MsgController {

	/**
	 * . 用户信息管理服务
	 */
	@Autowired
	private UserInfoManageService userInfoManageService;
	@Autowired
	SMSService smsService;
	@ResponseBody
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public Response<?> send(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map<String, Object> map = new HashMap<>();
		// 根据账号查手机号 from db，uniauth-job 做了从ldap到db的data同步；
		String reqTenancyCode = WebScopeUtil.getParamFromRequest(request,
				AppConstants.REQUEST_PARAMETER_KEY_TENANCY_CODE);
		String reqAccount = WebScopeUtil.getParamFromRequest(request, AppConstants.REQUEST_PARAMETER_KEY_EMAIL);
		// 根据邮箱获取用户原始信息
		UserDto userinfo = null;
		try {
			userinfo = userInfoManageService.getSingleUser(reqAccount, 1L, reqTenancyCode);
		} catch (Exception ex) {
			log.error("查询用户异常：{}", ex);
			map.put("code", 4);
			return Response.success(map);
		}

		// 用户不存在
		if (userinfo == null || userinfo.getId() == null) {
			log.error("未查询到用户！");
			map.put("code", 5);
			return Response.success(map);
		}
		HttpSession session = request.getSession();
		if (session == null) {
			log.error("往session放验证码异常！");
			map.put("code", 6);
			return Response.success(map);
		}
		if(StringUtils.isNotBlank(userinfo.getPhone())){
			String validCode = smsService.getRandNum(6);
			MessageVaildCodeRequest param = new MessageVaildCodeRequest();
			param.setMobilenumber(userinfo.getPhone());
			param.setVaildCode(validCode);
			session.setAttribute("validCode", validCode);
	
			MessageResponse resp = smsService.sendMessage(param);
			if ("0".equals(resp.getCode())) {
				log.info("发送短信验证码成功！");
				map.put("code", 1);
				return Response.success(map);
			} else {
				log.error("发送短信验证码异常！");
				map.put("code", 0);
				return Response.success(map);
			}
		}else{
			if(userinfo.getLoginTimesNoPhone() != null && userinfo.getLoginTimesNoPhone() >0){
				int times = userinfo.getLoginTimesNoPhone() - 1;
				userInfoManageService.updateLoginTimesById(userinfo.getId(), times);
				session.setAttribute("loginTimes", times);
				log.warn("未登记手机号用户进行登陆！");
				map.put("code", 2);
				return Response.success(map);
			}else{
				log.warn("未登记手机号用户无法进行登陆,请联系系统管理员设置免验证码登陆次数！");
				map.put("code", 3);
				return Response.success(map);
			}
		}
	}

}
