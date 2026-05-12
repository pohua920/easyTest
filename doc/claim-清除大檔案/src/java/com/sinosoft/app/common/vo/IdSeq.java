package com.sinosoft.app.common.vo;

public class IdSeq {
	private String IdSeqName = "";// 序列名称
	private String IdHead = "";// ID头字母
	private int IdLength = 10;// 填充长度,默认10

	public IdSeq(String idSeqName, String idHead, int idLength) {
		IdSeqName = idSeqName;
		IdHead = idHead;
		IdLength = idLength;
	}

	public String getIdSeqName() {
		return IdSeqName;
	}

	public void setIdSeqName(String idSeqName) {
		IdSeqName = idSeqName;
	}

	public String getIdHead() {
		return IdHead;
	}

	public void setIdHead(String idHead) {
		IdHead = idHead;
	}

	public int getIdLength() {
		return IdLength;
	}

	public void setIdLength(int idLength) {
		IdLength = idLength;
	}
}
