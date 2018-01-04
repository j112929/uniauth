package com.dianrong.common.uniauth.server.data.entity;

import java.util.Date;

import lombok.ToString;

@ToString
public class UserThirdAccount {
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column
   * user_third_account.id
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  private Long id;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column
   * user_third_account.user_id
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  private Long userId;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column
   * user_third_account.third_account
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  private String thirdAccount;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column
   * user_third_account.type
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  private String type;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column
   * user_third_account.last_login_ip
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  private Date lastLoginIp;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column
   * user_third_account.last_login_time
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  private Date lastLoginTime;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column
   * user_third_account.create_date
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  private Date createDate;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column
   * user_third_account.last_update
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  private Date lastUpdate;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column
   * user_third_account.tenancy_id
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  private Long tenancyId;

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database
   * column user_third_account.id
   *
   * @return the value of user_third_account.id
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  public Long getId() {
    return id;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database
   * column user_third_account.id
   *
   * @param id the value for user_third_account.id
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database
   * column user_third_account.user_id
   *
   * @return the value of user_third_account.user_id
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database
   * column user_third_account.user_id
   *
   * @param userId the value for user_third_account.user_id
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database
   * column user_third_account.third_account
   *
   * @return the value of user_third_account.third_account
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  public String getThirdAccount() {
    return thirdAccount;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database
   * column user_third_account.third_account
   *
   * @param thirdAccount the value for user_third_account.third_account
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  public void setThirdAccount(String thirdAccount) {
    this.thirdAccount = thirdAccount;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database
   * column user_third_account.type
   *
   * @return the value of user_third_account.type
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  public String getType() {
    return type;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database
   * column user_third_account.type
   *
   * @param type the value for user_third_account.type
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database
   * column user_third_account.last_login_ip
   *
   * @return the value of user_third_account.last_login_ip
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  public Date getLastLoginIp() {
    return lastLoginIp;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database
   * column user_third_account.last_login_ip
   *
   * @param lastLoginIp the value for user_third_account.last_login_ip
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  public void setLastLoginIp(Date lastLoginIp) {
    this.lastLoginIp = lastLoginIp;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database
   * column user_third_account.last_login_time
   *
   * @return the value of user_third_account.last_login_time
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  public Date getLastLoginTime() {
    return lastLoginTime;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database
   * column user_third_account.last_login_time
   *
   * @param lastLoginTime the value for user_third_account.last_login_time
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  public void setLastLoginTime(Date lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database
   * column user_third_account.create_date
   *
   * @return the value of user_third_account.create_date
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database
   * column user_third_account.create_date
   *
   * @param createDate the value for user_third_account.create_date
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database
   * column user_third_account.last_update
   *
   * @return the value of user_third_account.last_update
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  public Date getLastUpdate() {
    return lastUpdate;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database
   * column user_third_account.last_update
   *
   * @param lastUpdate the value for user_third_account.last_update
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database
   * column user_third_account.tenancy_id
   *
   * @return the value of user_third_account.tenancy_id
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  public Long getTenancyId() {
    return tenancyId;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database
   * column user_third_account.tenancy_id
   *
   * @param tenancyId the value for user_third_account.tenancy_id
   *
   * @mbggenerated Tue Jun 06 19:39:54 CST 2017
   */
  public void setTenancyId(Long tenancyId) {
    this.tenancyId = tenancyId;
  }
}
