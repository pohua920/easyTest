package com.tlg.aps.vo;

import java.io.File;

public class FirCtbcRewNoticeBatchVo {
	
	private String ctbcno;
	private String p180FileName;
	private File p180File;
	private String c180FileName;
	private File c180File;
	private String dontnoticeFileName;
	private File dontnoticeFile;
	
	public String getCtbcno() {
		return ctbcno;
	}
	public void setCtbcno(String ctbcno) {
		this.ctbcno = ctbcno;
	}
	public String getP180FileName() {
		return p180FileName;
	}
	public void setP180FileName(String p180FileName) {
		this.p180FileName = p180FileName;
	}
	public String getC180FileName() {
		return c180FileName;
	}
	public void setC180FileName(String c180FileName) {
		this.c180FileName = c180FileName;
	}
	public String getDontnoticeFileName() {
		return dontnoticeFileName;
	}
	public void setDontnoticeFileName(String dontnoticeFileName) {
		this.dontnoticeFileName = dontnoticeFileName;
	}
	public File getP180File() {
		return p180File;
	}
	public void setP180File(File p180File) {
		this.p180File = p180File;
	}
	public File getC180File() {
		return c180File;
	}
	public void setC180File(File c180File) {
		this.c180File = c180File;
	}
	public File getDontnoticeFile() {
		return dontnoticeFile;
	}
	public void setDontnoticeFile(File dontnoticeFile) {
		this.dontnoticeFile = dontnoticeFile;
	}

}
