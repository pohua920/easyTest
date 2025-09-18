package com.tlg.aps.vo;

import java.util.ArrayList;

public class FileListResponseVo {

	private String status;
	private String message;
	private ArrayList<FileVo> fileList;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<FileVo> getFileList() {
		return fileList;
	}

	public void setFileList(ArrayList<FileVo> fileList) {
		this.fileList = fileList;
	}

}
