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
 * <p>着重说明本类的作用</p>
 * @author  Kaiyun.Fu
 * @version 1.0.0
 */
public class BussinessRuntimeException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6998423943088134198L;

    /**
     * 无参构造方法
     */
    public BussinessRuntimeException() {
        super();
    }

    /**
     * 只有失败原因描述的构造方法
     * @param message 失败原因
     */
    public BussinessRuntimeException(String message) {
        super(message);
    }

    /**
     * 失败原因及其他异常
     * @param message 失败描述
     * @param cause 其他异常处理
     */
    public BussinessRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 其他异常处理
     * @param cause 其他异常
     */
    public BussinessRuntimeException(Throwable cause) {
        super(cause);
    }
}