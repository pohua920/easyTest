package com.sinosoft.app.mail.model;

import java.io.File;

/**
 * 定义附件信息的类
 */
public class Attachment {
	private String attachmentFileName;
	private File attachmentFile;

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
