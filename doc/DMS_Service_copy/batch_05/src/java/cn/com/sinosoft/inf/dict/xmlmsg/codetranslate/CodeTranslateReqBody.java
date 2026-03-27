package cn.com.sinosoft.inf.dict.xmlmsg.codetranslate;

import java.util.List;

public class CodeTranslateReqBody {
	private List TRANSLATELIST;
	private String TRANSTYPE;

	public List getTRANSLATELIST() {
		return TRANSLATELIST;
	}

	public void setTRANSLATELIST(List tRANSLATELIST) {
		TRANSLATELIST = tRANSLATELIST;
	}

	public String getTRANSTYPE() {
		return TRANSTYPE;
	}

	public void setTRANSTYPE(String tRANSTYPE) {
		TRANSTYPE = tRANSTYPE;
	}
}
