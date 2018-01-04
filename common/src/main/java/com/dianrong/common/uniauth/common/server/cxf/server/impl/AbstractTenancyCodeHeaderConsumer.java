package com.dianrong.common.uniauth.common.server.cxf.server.impl;

import com.dianrong.common.uniauth.common.server.cxf.CxfHeaderKeyDefine;
import com.dianrong.common.uniauth.common.server.cxf.server.HeaderConsumer;

public abstract class AbstractTenancyCodeHeaderConsumer implements HeaderConsumer {

  @Override
  public String key() {
    return CxfHeaderKeyDefine.TENANCY_CODE_KEY;
  }
}
