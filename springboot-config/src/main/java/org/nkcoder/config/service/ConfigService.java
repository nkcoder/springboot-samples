package org.nkcoder.config.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.nkcoder.config.config.MailConfigByProperties;
import org.nkcoder.config.config.MailConfigByYml;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConfigService {

  private final String appName;
  private final MailConfigByProperties mailConfig;
  private final MailConfigByYml mailConfigByYml;

  public ConfigService(@Value("${app.name}") String appName, MailConfigByProperties mailConfig,
      MailConfigByYml mailConfigByYml) {
    this.appName = appName;
    this.mailConfig = mailConfig;
    this.mailConfigByYml = mailConfigByYml;
  }

  public String getAppName() {
    return appName;
  }

  public List<String> getMailRecipients() {
    log.info("appName: {}, mail config: {}", appName, mailConfig.toString());
    log.info("appName: {}, mail config by yml: {}", appName, mailConfigByYml.toString());
    return mailConfig.getDefaultRecipients();
  }

}
