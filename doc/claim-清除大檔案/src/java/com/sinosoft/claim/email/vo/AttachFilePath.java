package com.sinosoft.claim.email.vo;

/**
 * 压缩文件名称，地址
 * @author 中科软
 */
public class AttachFilePath {
	private String fileName;// 文件名称，保存文件名称
	private String filePath;// 保存地址
	private String fileType;// 压缩类型：rar、zip

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileType() {
		return fileType;
	}

}
