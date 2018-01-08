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
 
package com.dianrong.common.uniauth.cas.service.msg;

import java.io.Serializable;

/**
 * <p>发送短信时需要传入参数的Bean<br>
 *
 * @author 
 * @version 1.0
 */
public class MessageVaildCodeRequest implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MessageVaildCodeRequest(String type, String category, String mobilenumber) {
        this.type = type;
        this.category = category;
        this.mobilenumber = mobilenumber;
    }

    public MessageVaildCodeRequest() {
	}

	/**
     * <p>短信类型<br>
     */
    private String type;

    /**
     * <p>业务类型<br>
     */
    private String category;

    /**
     * 手机号
     */
    private String mobilenumber;

    /**
     * 验证码短信所需字段 验证码
     */
    private String vaildCode;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getVaildCode() {
        return vaildCode;
    }

    public void setVaildCode(String vaildCode) {
        this.vaildCode = vaildCode;
    }
}
