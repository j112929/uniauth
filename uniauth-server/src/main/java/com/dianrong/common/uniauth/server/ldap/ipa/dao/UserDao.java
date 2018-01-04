package com.dianrong.common.uniauth.server.ldap.ipa.dao;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import com.dianrong.common.uniauth.common.util.Assert;
import com.dianrong.common.uniauth.server.ldap.ipa.entity.User;
import com.dianrong.common.uniauth.server.data.mapper.UserMapper;
import com.dianrong.common.uniauth.server.ldap.ipa.support.IpaConstants;
import com.dianrong.common.uniauth.server.ldap.ipa.support.IpaUtil;
import javax.naming.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Repository
public class UserDao {

  @Autowired
  private LdapTemplate ldapTemplate;

  @Autowired
  private UserMapper userMapper;

  /**
   * 调用IPA数据服务,进行身份认证.
   *
   * @param account 账号
   * @param password 密码
   * @throws AuthenticationException 账号和密码不匹配
   * @throws EmptyResultDataAccessException 账号不存在
   * @throws OperationNotSupportedException 连续登陆失败次数过多
   */
  public void authenticate(String account, String password) {
    //ldapTemplate.authenticate(query().base(IpaConstants.USER_BASE).where("uid").is(account),
        //password);
    Map<String, String> map = new HashMap<String, String>();
    map.put("email", account);
    map.put("tenancyId", "1");

    List<com.dianrong.common.uniauth.server.data.entity.User> users = userMapper.selectByEmailOrPhone(map);
    String base = "";

    for (com.dianrong.common.uniauth.server.data.entity.User user : users) {
      if (user.getDepartment() != null && user.getDepartment() != "") {
        String[] multi = user.getDepartment().split(",");
        String ou = "";

		for (String str : multi) {
		    ou += "ou=" + str + ",";
		}

        base =  ou + "dc=shenmajr,dc=com";
     
        log.warn("base=" + base);
        log.warn("mail=" + account);
        log.warn("password=" + password);
        ldapTemplate.authenticate(query().base(base).where("mail").is(account),
                password);

        break;
      }
    }

    if (base == "") {
      throw new EmptyResultDataAccessException(1);
    }
  }

  /**
   * 根据uid获取用户的基本信息.
   *
   * @param uid 不能为空
   */
  public User getUserByAccount(String uid) {
    Assert.notNull(uid);
    //Name dn = IpaUtil.buildDn(IpaConstants.USER_BASE, "uid", uid);
    //return ldapTemplate.findByDn(dn, User.class);
    
    Map<String, String> map = new HashMap<String, String>();
    map.put("email", uid);
    map.put("tenancyId", "1");

    List<com.dianrong.common.uniauth.server.data.entity.User> users = userMapper.selectByEmailOrPhone(map);

    for (com.dianrong.common.uniauth.server.data.entity.User user : users) {
      if (user.getDepartment() != null && user.getDepartment() != "") {
        //String base = "ou=" + user.getDepartment() + ",dc=shenmajr,dc=com";
        //log.warn("base=" + base);
        //Name dn = IpaUtil.buildDn(base, "mail", uid);
        //return ldapTemplate.findByDn(dn, User.class);
        User ldapUser = new User();
        ldapUser.setEmail(user.getEmail());
        ldapUser.setDisplayName(user.getName());

         return ldapUser;
      }
    }

    return null;
  }
}
