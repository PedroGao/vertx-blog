package com.pedro.vertx.model;

import com.lin.cms.core.utils.EncryptUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

  private final Logger logger = LoggerFactory.getLogger(UserTest.class);

  @Test
  void toJson() {
    String encrypt = EncryptUtil.encrypt("123456");
    logger.info("{}", encrypt);
    boolean verify = EncryptUtil.verify(encrypt, "123456");
    logger.info("{}", verify);
  }
}
