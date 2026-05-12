package com.sinosoft.claim.email.vo;

import java.io.File;

/**
 * 定义附件信息的类
 */
public class Attachment {
	/** 附件名称  */
	private String attachmentFileName;
	/** 附件 */
	private File attachmentFile;
	
	public Attachment() {
	}
	
	public Attachment(File attachmentFile) {
		this.attachmentFile = attachmentFile;
		this.attachmentFileName = attachmentFile.getName();
	}

	public Attachment(String attachmentFileName, File attachmentFile) {
		this.attachmentFileName = attachmentFileName;
		this.attachmentFile = attachmentFile;
	}

	public File getAttachmentFile() {
		return attachmentFile;
	}

	public void setAttachmentFile(File attachmentFile) {
		this.attachmentFile = attachmentFile;
	}

	public String getAttachmentFileName() {
		return attachmentFileName;
	}

	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}
}
