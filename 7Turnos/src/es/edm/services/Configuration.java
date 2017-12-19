package es.edm.services;

import es.edm.domain.entity.ConfigurationEntity;

public interface Configuration {

    String getFtpServerName();

    int getPrayersPerTurn();

    int getFtpPort();

    String getFtpUser();

    String getFtpPwd();

    String getCalendarFile2UploadURI();

    String getCalendarRemoteFileURI();

    String getStatisticsFile2UploadURI();

    String getStatisticsRemoteFileURI();

    String getMailchimpUrlPwd();

    String getEmailServiceUserName();

    String getEmailServiceUserPassword();

    String getEmailServiceHostName();

    boolean isSecureFTP();

    boolean isEmailServiceSSL();

    int getEmailServiceSmtpPort();

    ConfigurationEntity getConfigurationEntity();
}
