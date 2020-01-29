package com.pedro.vertx.model;

import com.lin.cms.core.utils.EncryptUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserTest {

  private final Logger logger = LoggerFactory.getLogger(UserTest.class);

  @Test
  public void toJson() {
    String encrypt = EncryptUtil.encrypt("123456");
    logger.info("{}", encrypt);
    boolean verify = EncryptUtil.verify(encrypt, "123456");
    logger.info("{}", verify);
  }
}
