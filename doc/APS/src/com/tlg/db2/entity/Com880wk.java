package com.tlg.db2.entity;

import java.math.BigDecimal;

import com.tlg.iBatis.IBatisBaseEntity;

/**
 * mantis：OTH0132，處理人員：BI086，需求單編號：OTH0132  保單存摺AS400資料寫入核心中介Table
 */
public class Com880wk extends IBatisBaseEntity<BigDecimal> {

	private static final long serialVersionUID = 1L;
	/**
	 * 唯一序號
	 */
	private String wk01;
	/**
	 * 產生類型 1 =  保單 , 2 =  批單
	 */
	private String wk02;
	/**
	 * 險種代號
	 */
	private String wk03;
	/**
	 * 保單號碼
	 */
	private String wk04;
	/**
	 * 傳輸種類 E: 電子保單、 P: 紙本保單
	 */
	private String wk05;
	/**
	 * 公司代碼
	 */
	private String wk06;
	/**
	 * 方式註記 A: 新增 U: 資料覆蓋  D: 刪除資料
	 */
	private String wk07;
	/**
	 * 被保險人證號原 1&A123456789
	 *  
	 *  1 ：身分證字號  2 ：舊式居留證統一證號
 		3 ：新式居留證統一證號  4 ：統一編號 
 		5 ：其他                             
	 */
	private String wk08;
	/**
	 * 保單生效日原
	 */
	private String wk09;
	/**
	 * 要保人證號新
	 */
	private String wk10;
	/**
	 * 要保人姓名新
	 */
	private String wk11;
	/**
	 * 被保人證號新
	 */
	private String wk12;
	/**
	 * 被保人姓名新
	 */
	private String wk13;
	/**
	 * 保單生效日新
	 */
	private String wk14;
	/**
	 * 保單到期日新
	 */
	private String wk15;
	/**
	 * 險種分類代碼
	 *  FIR 火災保險  LOP 意外保險    
		MCI 貨物運輸保險及內陸運輸保險
		AVI 航空保險 MHI 船體保險     
		FVI 漁船保險                  
	 */
	private String wk16;
	/**
	 * 保險標的
	 */
	private String wk17;
	/**
	 * 簽單 / 輸入日期
	 */
	private String wk18;
	/**
	 * 備註
	 */
	private String wk19;
	/**
	 * 建檔人員
	 */
	private String wk20;
	/**
	 * 建檔日期
	 */
	private String wk21;
	/**
	 * 批次號碼
	 */
	private String wk22;
	/**
	 * 批次處理人員
	 */
	private String wk23;
	/**
	 * 批次處理日期
	 */
	private String wk24;
	/**
	 * 來源
	 */
	private String wk25;
	/**
	 * 批次號碼 _NC
	 */
	private String wk26;
	/**
	 * 保發異常識別碼
	 */
	private String wk27;
	/**
	 * 批單號碼
	 */
	private String wk28;
	/**
	 * 處理問題備註(新增時發生問題會記錄錯誤訊息 100字以內)
	 */
	private String wk29;
	/**
	 * 被保險人證號原(佩君新增)
	 */
	private String wk081;

	public String getWk01() {
		return wk01;
	}

	public void setWk01(String wk01) {
		this.wk01 = wk01;
	}

	public String getWk02() {
		return wk02;
	}

	public void setWk02(String wk02) {
		this.wk02 = wk02;
	}

	public String getWk03() {
		return wk03;
	}

	public void setWk03(String wk03) {
		this.wk03 = wk03;
	}

	public String getWk04() {
		return wk04;
	}

	public void setWk04(String wk04) {
		this.wk04 = wk04;
	}

	public String getWk05() {
		return wk05;
	}

	public void setWk05(String wk05) {
		this.wk05 = wk05;
	}

	public String getWk06() {
		return wk06;
	}

	public void setWk06(String wk06) {
		this.wk06 = wk06;
	}

	public String getWk07() {
		return wk07;
	}

	public void setWk07(String wk07) {
		this.wk07 = wk07;
	}

	public String getWk08() {
		return wk08;
	}

	public void setWk08(String wk08) {
		this.wk08 = wk08;
	}

	public String getWk09() {
		return wk09;
	}

	public void setWk09(String wk09) {
		this.wk09 = wk09;
	}

	public String getWk10() {
		return wk10;
	}

	public void setWk10(String wk10) {
		this.wk10 = wk10;
	}

	public String getWk11() {
		return wk11;
	}

	public void setWk11(String wk11) {
		this.wk11 = wk11;
	}

	public String getWk12() {
		return wk12;
	}

	public void setWk12(String wk12) {
		this.wk12 = wk12;
	}

	public String getWk13() {
		return wk13;
	}

	public void setWk13(String wk13) {
		this.wk13 = wk13;
	}

	public String getWk14() {
		return wk14;
	}

	public void setWk14(String wk14) {
		this.wk14 = wk14;
	}

	public String getWk15() {
		return wk15;
	}

	public void setWk15(String wk15) {
		this.wk15 = wk15;
	}

	public String getWk16() {
		return wk16;
	}

	public void setWk16(String wk16) {
		this.wk16 = wk16;
	}

	public String getWk17() {
		return wk17;
	}

	public void setWk17(String wk17) {
		this.wk17 = wk17;
	}

	public String getWk18() {
		return wk18;
	}

	public void setWk18(String wk18) {
		this.wk18 = wk18;
	}

	public String getWk19() {
		return wk19;
	}

	public void setWk19(String wk19) {
		this.wk19 = wk19;
	}

	public String getWk20() {
		return wk20;
	}

	public void setWk20(String wk20) {
		this.wk20 = wk20;
	}

	public String getWk21() {
		return wk21;
	}

	public void setWk21(String wk21) {
		this.wk21 = wk21;
	}

	public String getWk22() {
		return wk22;
	}

	public void setWk22(String wk22) {
		this.wk22 = wk22;
	}

	public String getWk23() {
		return wk23;
	}

	public void setWk23(String wk23) {
		this.wk23 = wk23;
	}

	public String getWk24() {
		return wk24;
	}

	public void setWk24(String wk24) {
		this.wk24 = wk24;
	}

	public String getWk25() {
		return wk25;
	}

	public void setWk25(String wk25) {
		this.wk25 = wk25;
	}

	public String getWk26() {
		return wk26;
	}

	public void setWk26(String wk26) {
		this.wk26 = wk26;
	}

	public String getWk27() {
		return wk27;
	}

	public void setWk27(String wk27) {
		this.wk27 = wk27;
	}

	public String getWk28() {
		return wk28;
	}

	public void setWk28(String wk28) {
		this.wk28 = wk28;
	}

	public String getWk29() {
		return wk29;
	}

	public void setWk29(String wk29) {
		this.wk29 = wk29;
	}

	public String getWk081() {
		return wk081;
	}

	public void setWk081(String wk081) {
		this.wk081 = wk081;
	}
}