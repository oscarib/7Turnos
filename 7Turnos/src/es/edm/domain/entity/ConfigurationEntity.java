package es.edm.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="configurations")
public class ConfigurationEntity {

	@Id
	@Column
	private Integer uid;
	
	@Column
	private Integer chain;
	
	@Column
	private Integer prayersPerTurn;
	
	@Column
	private String ftpServerName;
	
	@Column
	private Integer ftpPort;
	
	@Column
	private String ftpUser;
	
	@Column
	private String ftpPwd;
	
	@Column
	private String remoteFilePath;
	
	@Column
	private String localFilePath;
	
	@Column
	private String calendarFileName;
	
	@Column
	private String statisticsFileName;
	
	@Column
	private String MailchimpUrlPwd;
	
	@Column
	private String emailServiceUserName;
	
	@Column
	private String emailServiceUserPassword;
	
	@Column
	private String emailServiceHostName;
	
	@Column
	private boolean isEmailServiceSSL;
	
	@Column
	private int emailServiceSmtpPort;
	
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getChain() {
		return chain;
	}
	public void setChain(Integer chain) {
		this.chain = chain;
	}
	public Integer getPrayersPerTurn() {
		return prayersPerTurn;
	}
	public void setPrayersPerTurn(Integer prayersPerTurn) {
		this.prayersPerTurn = prayersPerTurn;
	}
	public String getFtpServerName() {
		return ftpServerName;
	}
	public void setFtpServerName(String ftpServerName) {
		this.ftpServerName = ftpServerName;
	}
	public Integer getFtpPort() {
		return ftpPort;
	}
	public void setFtpPort(Integer ftpPort) {
		this.ftpPort = ftpPort;
	}
	public String getFtpUser() {
		return ftpUser;
	}
	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}
	public String getFtpPwd() {
		return ftpPwd;
	}
	public void setFtpPwd(String ftpPwd) {
		this.ftpPwd = ftpPwd;
	}
	public String getRemoteFilePath() {
		return remoteFilePath;
	}
	public void setRemoteFilePath(String remoteFilePath) {
		this.remoteFilePath = remoteFilePath;
	}
	public String getLocalFilePath() {
		return localFilePath;
	}
	public void setLocalFilePath(String localFilePath) {
		this.localFilePath = localFilePath;
	}
	public String getCalendarFileName() {
		return calendarFileName;
	}
	public void setCalendarFileName(String calendarFileName) {
		this.calendarFileName = calendarFileName;
	}
	public String getStatisticsFileName() {
		return statisticsFileName;
	}
	public void setStatisticsFileName(String statisticsFileName) {
		this.statisticsFileName = statisticsFileName;
	}
	public String getMailchimpUrlPwd() {
		return MailchimpUrlPwd;
	}
	public void setMailchimpUrlPwd(String mailchimpUrlPwd) {
		MailchimpUrlPwd = mailchimpUrlPwd;
	}
	public String getEmailServiceUserName() {
		return emailServiceUserName;
	}
	public void setEmailServiceUserName(String emailServiceUserName) {
		this.emailServiceUserName = emailServiceUserName;
	}
	public String getEmailServiceUserPassword() {
		return emailServiceUserPassword;
	}
	public void setEmailServiceUserPassword(String emailServiceUserPassword) {
		this.emailServiceUserPassword = emailServiceUserPassword;
	}
	public String getEmailServiceHostName() {
		return emailServiceHostName;
	}
	public void setEmailServiceHostName(String emailServiceHostName) {
		this.emailServiceHostName = emailServiceHostName;
	}
	public int getEmailServiceSmtpPort() {
		return emailServiceSmtpPort;
	}
	public void setEmailServiceSmtpPort(int emailServiceSmtpPort) {
		this.emailServiceSmtpPort = emailServiceSmtpPort;
	}
	public boolean isEmailServiceSSL() {
		return isEmailServiceSSL;
	}
	public void setEmailServiceSSL(boolean isEmailServiceSSL) {
		this.isEmailServiceSSL = isEmailServiceSSL;
	}
}
