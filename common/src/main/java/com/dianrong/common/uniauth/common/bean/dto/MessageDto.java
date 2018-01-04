package com.dianrong.common.uniauth.common.bean.dto;

public class MessageDto extends TenancyBaseDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = -536911469153787668L;
	/**
     * <p>响应码<br>
     */
    private String            timestamp;
    /**
     * <p>状态码<br>
     */
    private String            status;
    /**
     * <p>错误码<br>
     */
    private String            error;
    /**
     * <p>异常描述<br>
     */
    private String            exception;
    /**
     * <p>Path<br>
     */
    private String            path;
    /**
     * <p>短信平台返回值来判断是否发送成功的信息<br>
     */
    private String            message;
    /**
     * <p>返回值<br>
     */
    private String            code;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
