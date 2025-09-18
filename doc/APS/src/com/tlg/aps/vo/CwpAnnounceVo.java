package com.tlg.aps.vo;

import java.math.BigDecimal;
import java.util.Date;

public class CwpAnnounceVo {
	/* mantis：OTH0093，處理人員：BJ085，需求單編號：OTH0093 傷害險通報查詢、重送介面 start */

	private BigDecimal oid;
	/**
	 * 批次號
	 */
	private String checkno;
	/**
	 * 被保險人身份證字號
	 */
	private String keyidno;
	/**
	 * 資料序號，被保險人身分證字號為key值分組，從1開始
	 */
	private String dataserno;
	/**
	 * 產壽險別，r:產險業之收件通報資料;R:壽險業之收件通報資料;L:壽險業之承保通報資料;N:產險業之承保通報資料
	 */
	private String cmptype;
	/**
	 * 公司代號
	 */
	private String cmpno;
	/**
	 * 被保險人姓名
	 */
	private String name;
	/**
	 * 被保險人身分證號碼
	 */
	private String idno;
	/**
	 * 被保險人出生日期
	 */
	private String birdate;
	/**
	 * 被保險人性別，1:男2:女
	 */
	private String sex;
	/**
	 * 主約保單號碼。依主契約保單號碼最多40位數填列。例如：AA123456789
	 */
	private String insnom;
	/**
	 * 保單號碼
	 */
	private String insno;
	/**
	 * 來源別，1:OIU保單，預設為０
	 */
	private String origin;
	/**
	 * 銷售通路別，1:網路投保 2:業務員 3:保經、保代 4:電話行銷 5:機場櫃檯
	 */
	private String channel;
	/**
	 * 商品代碼
	 */
	private String prdcode;
	/**
	 * 保單分類，1:個人 2:團體
	 */
	private String insclass;
	/**
	 * 險種分類，1:人壽保險 2:傷害保險 3:健康保險 4:年金保險
	 */
	private String inskind;
	/**
	 * 險種，01:一般 02:特定 03:投資型 04:日額型 05:實支實付型 06:日額或實支實付擇一 07:手術型 08:重大疾病
	 * 09:帳戶型 10:長期看護型 11:喪失工作能力 12:防癌 13:旅行平安 14:微型 15:微型實支實付型 16:小額終老保險
	 * 17:失能扶助保險 18:登山綜合保險 19:定期壽險 99:意外身故(當險別是03) 註：此險種99只限定理賠通報系統自動轉換使用
	 */
	private String insitem;
	/**
	 * 公、自費件，0:無 1:公費 2:自費
	 */
	private String paytype;
	/**
	 * 給付項目(身故)
	 */
	private String itema;
	/**
	 * 給付項目(完全失能或最高級失能)
	 */
	private String itemb;
	/**
	 * 給付項目(失能扶助金)
	 */
	private String itemc;
	/**
	 * 給付項目(特定事故保險金)
	 */
	private String itemd;
	/**
	 * 給付項目(初次罹患)
	 */
	private String iteme;
	/**
	 * 給付項目(醫療限額)
	 */
	private String itemf;
	/**
	 * 給付項目(醫療限額自負額)
	 */
	private String itemg;
	/**
	 * 給付項目(日額)
	 */
	private String itemh;
	/**
	 * 給付項目(住院手術)
	 */
	private String itemi;
	/**
	 * 給付項目(門診手術)
	 */
	private String itemj;
	/**
	 * 給付項目(門診)
	 */
	private String itemk;
	/**
	 * 給付項目(重大疾病(含特定傷病))
	 */
	private String iteml;
	/**
	 * 給付項目(重大燒燙傷)
	 */
	private String itemm;
	/**
	 * 給付項目(癌症療養)
	 */
	private String itemn;
	/**
	 * 給付項目(出院療養)
	 */
	private String itemo;
	/**
	 * 給付項目(喪失工作能力)
	 */
	private String itemp;
	/**
	 * 給付項目(喪葬費用)
	 */
	private String itemq;
	/**
	 * 給付項目(銜接原醫療限額之自負額)
	 */
	private String itemr;
	/**
	 * 給付項目(分期給付)
	 */
	private String items;
	/**
	 * 契約生效日期，8碼民國年，例01090101
	 */
	private String valdate;
	/**
	 * 4碼時分，旅平險專用
	 */
	private String valtime;
	/**
	 * 契約滿期日期，8碼民國年，例01090101
	 */
	private String ovrdate;
	/**
	 * 4碼時分
	 */
	private String ovrtime;
	/**
	 * 保費
	 */
	private String prm;
	/**
	 * 保費繳別，0:無 1:躉繳 2:年繳 3:半年繳 4:季繳 5:月繳 6:彈性繳 9:繳費期滿
	 */
	private String bamttype;
	/**
	 * 保費繳費年期
	 */
	private String prmyears;
	/**
	 * 保單狀況，01:有效 02:增額 03:減額 04:展期 05:繳清 06:契約撤銷/未承保取消件 07:停效/契約註銷
	 * 10:解除契約 11:滿期(契約到期) 12:鍵值欄位通報錯誤終止 15:通報更正 20:終止1 21:終止2
	 * 30:被保險人因自然死身故 31:被保險人因意外身故 32:被保險人因其他原因身故
	 * 50:一○七條/一○七條之一承保資料(精神障礙或其他心智缺陷/受監護宣告尚未撤銷)
	 * 51:一O七條理賠資料(未滿14足歲之未成年人) 52:一O七條理賠資料(心神喪失或精神耗弱之人)
	 */
	private String con;
	/**
	 * 保單狀況生效日期，8碼民國年，例01090101
	 */
	private String condate;
	/**
	 * 4碼時分，旅平險專用
	 */
	private String contime;
	/**
	 * 要保人姓名
	 */
	private String askname;
	/**
	 * 要保人身分證號碼
	 */
	private String askidno;
	/**
	 * 要保人出生日期，8碼民國年，例01090101
	 */
	private String askbirdate;
	/**
	 * 要保人與被保險人關係，00:無 01:本人 02:配偶 03:父母 04:子女 05:其他
	 */
	private String asktype;
	/**
	 * 要保書填寫日期(收件) / 要保日期(一0七條)。8碼民國年，例01090101
	 * (此欄位為收件與一0七條用)
	 */
	private String filldate;
	/**
	 * 保經代類別，00:無 01:保經代傳真件 02:保經代健康險及傷害醫療險 03:保經代壽險與傷害險
	 */
	private String broktype;
	/**
	 * 回應碼，00:成功;11:該功能[@arg1@]目前停用中; 12:貴公司會員公司[@arg1@]目前停用中; 13:該功能[@arg1@]已達最大使用連線數; 
	 * 14:貴公司會員公司[@arg1@]已達最大使用連線數; 15:貴公司[@arg1@]已超過單次最大處理筆數; 21:會員公司[@arg1@]認證碼不存在;
	 * 22:會員公司[@arg1@]認證碼失效; 23:會員公司[@arg1@]IP[@arg1@]錯誤; 24:會員公司[@arg1@]未授權使用該功能[@arg2@]; 
	 * 25:公司別不符; 31:[@arg1@]格式不正確; 32:表頭欄位[@arg1@]名稱不正確; 33:資料欄位[@arg1@]名稱不正確;
	 * 34:欄位[total]值與明細數量不符; 35:表頭欄位[@arg1@]不可為空值; 36:資料欄位[@arg1@]不可為空值;
	 * 37:資料欄位[@arg1@]格式錯誤; 38:資料欄位[@arg1@]長度錯誤; 39:資料欄位[@arg1@]須為[@arg2@];
	 * 40:資料欄位[@arg1@]不得大於[@arg2@]; 41:資料欄位[@arg1@]不得小於[@arg2@]; 
	 * 42:資料欄位[@arg1@]必須介於[@arg2@]~[@arg3@]; 91:查無資料; 99:其它;
	 */
	private String rtncode;
	/**
	 * 回應訊息
	 */
	private String rtnmsg;
	/**
	 * 建立時間(由各中介轉進時間)
	 */
	private Date dcreate;
	/**
	 * 傳送時間(送公會時間)
	 */
	private Date sendtime;
	/**
	 * 資料來源
	 */
	private String sourceRemark;
	/**
	 * 重送原始OID
	 */
	private BigDecimal resendSourceOid;
	/**
	 * 重送原因
	 */
	private String resendReason;

	public BigDecimal getOid() {
		return oid;
	}
	public void setOid(BigDecimal oid) {
		this.oid = oid;
	}
	public String getCheckno() {
		return checkno;
	}
	public void setCheckno(String checkno) {
		this.checkno = checkno;
	}
	public String getKeyidno() {
		return keyidno;
	}
	public void setKeyidno(String keyidno) {
		this.keyidno = keyidno;
	}
	public String getDataserno() {
		return dataserno;
	}
	public void setDataserno(String dataserno) {
		this.dataserno = dataserno;
	}
	public String getCmptype() {
		return cmptype;
	}
	public void setCmptype(String cmptype) {
		this.cmptype = cmptype;
	}
	public String getCmpno() {
		return cmpno;
	}
	public void setCmpno(String cmpno) {
		this.cmpno = cmpno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}
	public String getBirdate() {
		return birdate;
	}
	public void setBirdate(String birdate) {
		this.birdate = birdate;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getInsnom() {
		return insnom;
	}
	public void setInsnom(String insnom) {
		this.insnom = insnom;
	}
	public String getInsno() {
		return insno;
	}
	public void setInsno(String insno) {
		this.insno = insno;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getPrdcode() {
		return prdcode;
	}
	public void setPrdcode(String prdcode) {
		this.prdcode = prdcode;
	}
	public String getInsclass() {
		return insclass;
	}
	public void setInsclass(String insclass) {
		this.insclass = insclass;
	}
	public String getInskind() {
		return inskind;
	}
	public void setInskind(String inskind) {
		this.inskind = inskind;
	}
	public String getInsitem() {
		return insitem;
	}
	public void setInsitem(String insitem) {
		this.insitem = insitem;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getItema() {
		return itema;
	}
	public void setItema(String itema) {
		this.itema = itema;
	}
	public String getItemb() {
		return itemb;
	}
	public void setItemb(String itemb) {
		this.itemb = itemb;
	}
	public String getItemc() {
		return itemc;
	}
	public void setItemc(String itemc) {
		this.itemc = itemc;
	}
	public String getItemd() {
		return itemd;
	}
	public void setItemd(String itemd) {
		this.itemd = itemd;
	}
	public String getIteme() {
		return iteme;
	}
	public void setIteme(String iteme) {
		this.iteme = iteme;
	}
	public String getItemf() {
		return itemf;
	}
	public void setItemf(String itemf) {
		this.itemf = itemf;
	}
	public String getItemg() {
		return itemg;
	}
	public void setItemg(String itemg) {
		this.itemg = itemg;
	}
	public String getItemh() {
		return itemh;
	}
	public void setItemh(String itemh) {
		this.itemh = itemh;
	}
	public String getItemi() {
		return itemi;
	}
	public void setItemi(String itemi) {
		this.itemi = itemi;
	}
	public String getItemj() {
		return itemj;
	}
	public void setItemj(String itemj) {
		this.itemj = itemj;
	}
	public String getItemk() {
		return itemk;
	}
	public void setItemk(String itemk) {
		this.itemk = itemk;
	}
	public String getIteml() {
		return iteml;
	}
	public void setIteml(String iteml) {
		this.iteml = iteml;
	}
	public String getItemm() {
		return itemm;
	}
	public void setItemm(String itemm) {
		this.itemm = itemm;
	}
	public String getItemn() {
		return itemn;
	}
	public void setItemn(String itemn) {
		this.itemn = itemn;
	}
	public String getItemo() {
		return itemo;
	}
	public void setItemo(String itemo) {
		this.itemo = itemo;
	}
	public String getItemp() {
		return itemp;
	}
	public void setItemp(String itemp) {
		this.itemp = itemp;
	}
	public String getItemq() {
		return itemq;
	}
	public void setItemq(String itemq) {
		this.itemq = itemq;
	}
	public String getItemr() {
		return itemr;
	}
	public void setItemr(String itemr) {
		this.itemr = itemr;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public String getValdate() {
		return valdate;
	}
	public void setValdate(String valdate) {
		this.valdate = valdate;
	}
	public String getValtime() {
		return valtime;
	}
	public void setValtime(String valtime) {
		this.valtime = valtime;
	}
	public String getOvrdate() {
		return ovrdate;
	}
	public void setOvrdate(String ovrdate) {
		this.ovrdate = ovrdate;
	}
	public String getOvrtime() {
		return ovrtime;
	}
	public void setOvrtime(String ovrtime) {
		this.ovrtime = ovrtime;
	}
	public String getPrm() {
		return prm;
	}
	public void setPrm(String prm) {
		this.prm = prm;
	}
	public String getBamttype() {
		return bamttype;
	}
	public void setBamttype(String bamttype) {
		this.bamttype = bamttype;
	}
	public String getPrmyears() {
		return prmyears;
	}
	public void setPrmyears(String prmyears) {
		this.prmyears = prmyears;
	}
	public String getCon() {
		return con;
	}
	public void setCon(String con) {
		this.con = con;
	}
	public String getCondate() {
		return condate;
	}
	public void setCondate(String condate) {
		this.condate = condate;
	}
	public String getContime() {
		return contime;
	}
	public void setContime(String contime) {
		this.contime = contime;
	}
	public String getAskname() {
		return askname;
	}
	public void setAskname(String askname) {
		this.askname = askname;
	}
	public String getAskidno() {
		return askidno;
	}
	public void setAskidno(String askidno) {
		this.askidno = askidno;
	}
	public String getAskbirdate() {
		return askbirdate;
	}
	public void setAskbirdate(String askbirdate) {
		this.askbirdate = askbirdate;
	}
	public String getAsktype() {
		return asktype;
	}
	public void setAsktype(String asktype) {
		this.asktype = asktype;
	}
	public String getFilldate() {
		return filldate;
	}
	public void setFilldate(String filldate) {
		this.filldate = filldate;
	}
	public String getBroktype() {
		return broktype;
	}
	public void setBroktype(String broktype) {
		this.broktype = broktype;
	}
	public String getRtncode() {
		return rtncode;
	}
	public void setRtncode(String rtncode) {
		this.rtncode = rtncode;
	}
	public String getRtnmsg() {
		return rtnmsg;
	}
	public void setRtnmsg(String rtnmsg) {
		this.rtnmsg = rtnmsg;
	}
	public Date getDcreate() {
		return dcreate;
	}
	public void setDcreate(Date dcreate) {
		this.dcreate = dcreate;
	}
	public Date getSendtime() {
		return sendtime;
	}
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	public String getSourceRemark() {
		return sourceRemark;
	}
	public void setSourceRemark(String sourceRemark) {
		this.sourceRemark = sourceRemark;
	}
	public BigDecimal getResendSourceOid() {
		return resendSourceOid;
	}
	public void setResendSourceOid(BigDecimal resendSourceOid) {
		this.resendSourceOid = resendSourceOid;
	}
	public String getResendReason() {
		return resendReason;
	}
	public void setResendReason(String resendReason) {
		this.resendReason = resendReason;
	}
}
