package com.dianrong.common.uniauth.cas.service;

import com.dianrong.common.uniauth.common.bean.Response;
import com.dianrong.common.uniauth.common.bean.dto.DomainDto;
import com.dianrong.common.uniauth.common.bean.request.DomainParam;
import com.dianrong.common.uniauth.common.client.UniClientFacade;
import com.dianrong.common.uniauth.common.cons.AppConstants;
import com.dianrong.common.uniauth.common.util.ZkNodeUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("domainService")
public class DomainService extends BaseService {

  @Resource(name = "uniauthConfig")
  private Map<String, String> allZkNodeMap;

  @Autowired
  private UniClientFacade uniClientFacade;

  /**
   * 获取在ZK和Techops中都配置了的域信息.
   */
  public List<DomainDto> getAllLoginPageDomains() {
    DomainParam domainParam = new DomainParam();
    List<String> domainCodeList = new ArrayList<String>();
    Iterator<Entry<String, String>> iterator = allZkNodeMap.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, String> entry = iterator.next();
      String zkNodeName = entry.getKey();
      log.warn("========= origin2 zkNode =========");
      log.warn(zkNodeName);

      if (ZkNodeUtils.isDomainNode(zkNodeName)) {
        zkNodeName = ZkNodeUtils.getDomainName(zkNodeName);
        log.warn("========= origin zkNode =========");
        log.warn(zkNodeName);
        // 过滤有自定义页面的域
        String customizedLoginUrl =
            allZkNodeMap.get(ZkNodeUtils.getDomainCustomUrlNodeKey(zkNodeName));
        boolean showInHomePage = ZkNodeUtils.isShowInHomePage(zkNodeName, allZkNodeMap);
        log.warn("========= origin1 zkNode =========");
        log.warn(zkNodeName);
        if (!showInHomePage && customizedLoginUrl != null && !customizedLoginUrl.isEmpty()) {
          continue;
        }
        log.warn("========= zkNode =========");
        log.warn(zkNodeName);
        domainCodeList.add(zkNodeName);
      }
    }
    domainParam.setDomainCodeList(domainCodeList);
    Response<List<DomainDto>> response =
        uniClientFacade.getDomainResource().getAllLoginDomains(domainParam);
    List<DomainDto> domainDtoList = response.getData();
    if (domainDtoList != null && !domainDtoList.isEmpty()) {
      for (DomainDto domainDto : domainDtoList) {
        String domainCode = domainDto.getCode();
        String zkDomainUrl = allZkNodeMap.get(AppConstants.ZK_DOMAIN_PREFIX + domainCode);
        zkDomainUrl += AppConstants.SERVICE_LOGIN_SUFFIX;
        domainDto.setZkDomainUrl(zkDomainUrl);
        String zkDomainUrlEncoded = null;
        try {
          zkDomainUrlEncoded = URLEncoder.encode(zkDomainUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
          log.warn("URIEncode " + zkDomainUrl + " failed", e);
        }
        domainDto.setZkDomainUrlEncoded(zkDomainUrlEncoded);
        String customizedLoginUrl =
            allZkNodeMap.get(ZkNodeUtils.getDomainCustomUrlNodeKey(domainCode));
        // 自定义登陆页面的系统
        if (!StringUtils.isEmpty(customizedLoginUrl)) {
          domainDto.setIsCustomizedLoginPage(Boolean.TRUE);
        }
      }
    }
    return domainDtoList;
  }
}
