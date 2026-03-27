package com.sinosoft.claim.schema.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRPLEMAILCONFIG")
public class PrpLemailConfig implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/** 主鍵*/
	private String id;
	/** 邮件标题*/
	private String title;
	/** 邮件服务器地址*/
	private String smtpurl;
	/** 邮箱端口号*/
	private String port;
	/** 邮件服务器名称*/
	private String smtpusername;
	/** 邮件服务器密码*/
	private String smtppassword;
	/** 邮件地址*/
	private String username;
	/** 邮件昵称*/
	private String nickname;
	/** 模板路径*/
	private String velocityFilePath;
	/** Socket I/O超时值，单位毫秒，缺省值不超时*/
	private Integer smtptimeout;
	/** 如果邮件服务器需要认证，则需确保设为true,不然控制台会报错 */
	private String smtpauth;
	/** 附件地址*/
	private String attachip;
	/** 附件根目录*/
	private String rootpath;
	/** 文件导出地址*/
	private String exportPath;
	/** 文件压缩地址*/
	private String compressPath;
	/** 文件压缩地址及名称*/
	private String compressName;
	/** 系统地址 */
	private String localhost;
	/** 收件人地址*/
	private String address;
	/** 标志位*/
	private String flag;
	/** 備註*/
	private String remark;
	/** 是否有效状态 1有效，0无效*/
	private String validStatus;

	public PrpLemailConfig() {
		super();
	}
	
	@Id
	@Column(name = "ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "SMTPURL")
	public String getSmtpurl() {
		return smtpurl;
	}
	public void setSmtpurl(String smtpurl) {
		this.smtpurl = smtpurl;
	}
	@Column(name = "SMTPUSERNAME")
	public String getSmtpusername() {
		return smtpusername;
	}
	public void setSmtpusername(String smtpusername) {
		this.smtpusername = smtpusername;
	}
	@Column(name = "SMTPPASSWORD")
	public String getSmtppassword() {
		return smtppassword;
	}
	public void setSmtppassword(String smtppassword) {
		this.smtppassword = smtppassword;
	}
	@Column(name = "USERNAME")
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column(name = "NICKNAME")
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Column(name = "VELOCITYFILEPATH")
	public String getVelocityFilePath() {
		return velocityFilePath;
	}
	public void setVelocityFilePath(String velocityFilePath) {
		this.velocityFilePath = velocityFilePath;
	}
	@Column(name = "SMTPTIMEOUT")
	public Integer getSmtptimeout() {
		return smtptimeout;
	}
	public void setSmtptimeout(Integer smtptimeout) {
		this.smtptimeout = smtptimeout;
	}
	@Column(name = "SMTPAUTH")
	public String getSmtpauth() {
		return smtpauth;
	}
	public void setSmtpauth(String smtpauth) {
		this.smtpauth = smtpauth;
	}
	@Column(name = "ATTACHIP")
	public String getAttachip() {
		return attachip;
	}
	public void setAttachip(String attachip) {
		this.attachip = attachip;
	}
	@Column(name = "ROOTPATH")
	public String getRootpath() {
		return rootpath;
	}
	public void setRootpath(String rootpath) {
		this.rootpath = rootpath;
	}
	@Column(name = "EXPORTPATH")
	public String getExportPath() {
		return exportPath;
	}
	public void setExportPath(String exportPath) {
		this.exportPath = exportPath;
	}
	@Column(name = "COMPRESSPATH")
	public String getCompressPath() {
		return compressPath;
	}
	public void setCompressPath(String compressPath) {
		this.compressPath = compressPath;
	}
	@Column(name = "COMPRESSNAME")
	public String getCompressName() {
		return compressName;
	}
	public void setCompressName(String compressName) {
		this.compressName = compressName;
	}
	@Column(name = "LOCALHOST")
	public String getLocalhost() {
		return localhost;
	}
	public void setLocalhost(String localhost) {
		this.localhost = localhost;
	}
	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "FLAG")
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	@Column(name = "PORT")
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
}
