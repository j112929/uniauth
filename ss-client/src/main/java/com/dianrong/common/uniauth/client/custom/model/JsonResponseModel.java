package com.dianrong.common.uniauth.client.custom.model;

import com.dianrong.common.uniauth.common.util.Assert;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Uniauth的json结果model.
 *
 * @author wanglin
 */
public class JsonResponseModel<T extends Serializable> implements Serializable {

  private static final long serialVersionUID = -8045161535931878288L;

  /** 定义结果常量. **/
  /**
   * 登陆成功.
   */
  private static final String RESULT_SUCCESS = "success";

  /**
   * 登陆失败.
   */
  private static final String RESULT_FAILURE = "error";

  /**
   * 结果code.
   */
  private final Integer code;

  /**
   * 结果字符串, success 或 failure.
   */
  private final String result;

  /**
   * 登陆成功的返回结果.
   */
  private final T content;

  /**
   * 登陆失败返回的message.
   */
  private final String message;

  /**
   * 具体异常信息.
   */
  private final List<String> errors;

  /**
   * 构造登陆返回结果
   *
   * @param success boolean值,是否成功
   * @param content 成功的返回值
   * @param code 返回结果code, 不能为空
   * @param message 返回结果信息,一般是失败才会有结果信息.
   */
  public JsonResponseModel(boolean success, T content, Integer code, String message) {
    Assert.notNull(code, "response code can not be null");
    this.code = code;
    if (success) {
      this.result = RESULT_SUCCESS;
    } else {
      this.result = RESULT_FAILURE;
    }
    this.content = content;
    this.message = String.valueOf(code);
    this.errors = Arrays.asList(message);
  }

  public Integer getCode() {
    return code;
  }

  public String getResult() {
    return result;
  }

  public T getContent() {
    return content;
  }

  public String getMessage() {
    return message;
  }

  public List<String> getErrors() {
    return errors;
  }

  /**
   * 生成一个成功的结果.
   *
   * @return 表示成功的返回结果
   */
  public static <T extends Serializable> JsonResponseModel<T> success() {
    return new JsonResponseModel<T>(true, null, ResponseCode.SUCCESS, null);
  }

  /**
   * 便利方法, 用于生成一个成功的返回结果,采用默认的结果code.
   *
   * @param content 返回结果内容, 必须是一个序列化的对象类型
   * @return 表示成功的返回结果
   */
  public static <T extends Serializable> JsonResponseModel<T> success(T content) {
    return new JsonResponseModel<T>(true, content, ResponseCode.SUCCESS, null);
  }

  /**
   * 便利方法,用于生成一个操作失败的结果.
   *
   * @param message 失败的具体描述信息
   * @return 表示失败的返回结果
   */
  public static JsonResponseModel<String> failure(String message) {
    return new JsonResponseModel<String>(false, null, ResponseCode.FAILURE, message);
  }

  /**
   * 便利方法,用于生成一个操作失败的结果.
   *
   * @param code 失败的错误码,不能为空
   * @param message 失败的具体描述信息
   * @return 表示失败的返回结果
   */
  public static JsonResponseModel<String> failure(Integer code, String message) {
    return new JsonResponseModel<String>(false, null, code, message);
  }

}
