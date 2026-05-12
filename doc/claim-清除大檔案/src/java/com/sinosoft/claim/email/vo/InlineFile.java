package com.sinosoft.claim.email.vo;

import java.io.File;

/**
 * 定义内嵌文件信息的类
 */
public class InlineFile {
	private String inlineFileName;
	private File inlineFile;

	public File getInlineFile() {
		return inlineFile;
	}

	public void setInlineFile(File inlineFile) {
		this.inlineFile = inlineFile;
	}

	public String getInlineFileName() {
		return inlineFileName;
	}

	public void setInlineFileName(String inlineFileName) {
		this.inlineFileName = inlineFileName;
	}
}
