package com.tlg.aps.vo;

import java.math.BigDecimal;
import java.util.Date;

/** mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 */
public class Aps034genFileVo {
	//差異明細檔、剔退檔用到的欄位
    private BigDecimal oid;
    private String batchNo;
    private Integer batchSeq;
    private String filename;
    private String rawdata;
    private String pStatus;
    private String diffFlag;
    private String diffReason;
    private String fbPolicynoF;
    private String fbPolicynoQ;
    private String fbNameI;
    private String fbIdI;
    private Date fbStartdate;
    private Date fbEnddate;
    private String fbAddress;
    private String fbMortgagee;
    private String fbWallno;
    private String fbSumfloors;
    private String fbBuildarea;
    private String fbUseNature;
    private String fbAmountF;
    private String fbAmountQ;
    private String fbColInsurcNo;
    private String fbIdA;
    private String fbNameA;
    private String fbInsCom;
    private String fbProcessCenter;
    private String fbSalesName;
    private String fbSalesId;
    private String nameI;
    private String idI;
    private Date startdate;
    private Date enddate;
    private String addressdetailinfo;
    private String wallno;
    private int sumfloors;
    private String buildarea;
    private String amountF;
    private String amountQ;
    private String idA;
    private String nameA;
    private String icreate;
    private Date dcreate;
    private String iupdate;
    private Date dupdate;
    
    private String codecname;
    private String sfReason;
    
    //大保單、續保明細用到的欄位
    private String collateralnumber;
    private String policyno;
    private String oldpolicyno;
    private String piiInsuredname;
    private String piiId;
    private String piaInsuredname;
    private String piaId;
    private String piaPostcode;
    private String postaddress;
    private String addresscode;
    private String builddetailinfo;
    private String strSumfloors;
    private String structure;
    private BigDecimal numBuildarea;
    private String amtQ;
    private String premQ;
    private String amtTot;
    private String renewinsuranceflag;
    private String extracomcode;
    private String wallmaterial;
    
    private String amount;
    private String premium;
    
    //查詢核心未出單資料欄位
    private String bseq;
    
	public BigDecimal getOid() {
		return oid;
	}
	public void setOid(BigDecimal oid) {
		this.oid = oid;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public Integer getBatchSeq() {
		return batchSeq;
	}
	public void setBatchSeq(Integer batchSeq) {
		this.batchSeq = batchSeq;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getRawdata() {
		return rawdata;
	}
	public void setRawdata(String rawdata) {
		this.rawdata = rawdata;
	}
	public String getpStatus() {
		return pStatus;
	}
	public void setpStatus(String pStatus) {
		this.pStatus = pStatus;
	}
	public String getDiffFlag() {
		return diffFlag;
	}
	public void setDiffFlag(String diffFlag) {
		this.diffFlag = diffFlag;
	}
	public String getDiffReason() {
		return diffReason;
	}
	public void setDiffReason(String diffReason) {
		this.diffReason = diffReason;
	}
	public String getFbPolicynoF() {
		return fbPolicynoF;
	}
	public void setFbPolicynoF(String fbPolicynoF) {
		this.fbPolicynoF = fbPolicynoF;
	}
	public String getFbPolicynoQ() {
		return fbPolicynoQ;
	}
	public void setFbPolicynoQ(String fbPolicynoQ) {
		this.fbPolicynoQ = fbPolicynoQ;
	}
	public String getFbNameI() {
		return fbNameI;
	}
	public void setFbNameI(String fbNameI) {
		this.fbNameI = fbNameI;
	}
	public String getFbIdI() {
		return fbIdI;
	}
	public void setFbIdI(String fbIdI) {
		this.fbIdI = fbIdI;
	}
	public Date getFbStartdate() {
		return fbStartdate;
	}
	public void setFbStartdate(Date fbStartdate) {
		this.fbStartdate = fbStartdate;
	}
	public Date getFbEnddate() {
		return fbEnddate;
	}
	public void setFbEnddate(Date fbEnddate) {
		this.fbEnddate = fbEnddate;
	}
	public String getFbAddress() {
		return fbAddress;
	}
	public void setFbAddress(String fbAddress) {
		this.fbAddress = fbAddress;
	}
	public String getFbMortgagee() {
		return fbMortgagee;
	}
	public void setFbMortgagee(String fbMortgagee) {
		this.fbMortgagee = fbMortgagee;
	}
	public String getFbWallno() {
		return fbWallno;
	}
	public void setFbWallno(String fbWallno) {
		this.fbWallno = fbWallno;
	}
	public String getFbSumfloors() {
		return fbSumfloors;
	}
	public void setFbSumfloors(String fbSumfloors) {
		this.fbSumfloors = fbSumfloors;
	}
	public String getFbBuildarea() {
		return fbBuildarea;
	}
	public void setFbBuildarea(String fbBuildarea) {
		this.fbBuildarea = fbBuildarea;
	}
	public String getFbUseNature() {
		return fbUseNature;
	}
	public void setFbUseNature(String fbUseNature) {
		this.fbUseNature = fbUseNature;
	}
	public String getFbAmountF() {
		return fbAmountF;
	}
	public void setFbAmountF(String fbAmountF) {
		this.fbAmountF = fbAmountF;
	}
	public String getFbAmountQ() {
		return fbAmountQ;
	}
	public void setFbAmountQ(String fbAmountQ) {
		this.fbAmountQ = fbAmountQ;
	}
	public String getFbColInsurcNo() {
		return fbColInsurcNo;
	}
	public void setFbColInsurcNo(String fbColInsurcNo) {
		this.fbColInsurcNo = fbColInsurcNo;
	}
	public String getFbIdA() {
		return fbIdA;
	}
	public void setFbIdA(String fbIdA) {
		this.fbIdA = fbIdA;
	}
	public String getFbNameA() {
		return fbNameA;
	}
	public void setFbNameA(String fbNameA) {
		this.fbNameA = fbNameA;
	}
	public String getFbInsCom() {
		return fbInsCom;
	}
	public void setFbInsCom(String fbInsCom) {
		this.fbInsCom = fbInsCom;
	}
	public String getFbProcessCenter() {
		return fbProcessCenter;
	}
	public void setFbProcessCenter(String fbProcessCenter) {
		this.fbProcessCenter = fbProcessCenter;
	}
	public String getFbSalesName() {
		return fbSalesName;
	}
	public void setFbSalesName(String fbSalesName) {
		this.fbSalesName = fbSalesName;
	}
	public String getFbSalesId() {
		return fbSalesId;
	}
	public void setFbSalesId(String fbSalesId) {
		this.fbSalesId = fbSalesId;
	}
	public String getNameI() {
		return nameI;
	}
	public void setNameI(String nameI) {
		this.nameI = nameI;
	}
	public String getIdI() {
		return idI;
	}
	public void setIdI(String idI) {
		this.idI = idI;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getAddressdetailinfo() {
		return addressdetailinfo;
	}
	public void setAddressdetailinfo(String addressdetailinfo) {
		this.addressdetailinfo = addressdetailinfo;
	}
	public String getWallno() {
		return wallno;
	}
	public void setWallno(String wallno) {
		this.wallno = wallno;
	}
	public int getSumfloors() {
		return sumfloors;
	}
	public void setSumfloors(int sumfloors) {
		this.sumfloors = sumfloors;
	}
	public String getBuildarea() {
		return buildarea;
	}
	public void setBuildarea(String buildarea) {
		this.buildarea = buildarea;
	}
	public String getAmountF() {
		return amountF;
	}
	public void setAmountF(String amountF) {
		this.amountF = amountF;
	}
	public String getAmountQ() {
		return amountQ;
	}
	public void setAmountQ(String amountQ) {
		this.amountQ = amountQ;
	}
	public String getIdA() {
		return idA;
	}
	public void setIdA(String idA) {
		this.idA = idA;
	}
	public String getNameA() {
		return nameA;
	}
	public void setNameA(String nameA) {
		this.nameA = nameA;
	}
	public String getIcreate() {
		return icreate;
	}
	public void setIcreate(String icreate) {
		this.icreate = icreate;
	}
	public Date getDcreate() {
		return dcreate;
	}
	public void setDcreate(Date dcreate) {
		this.dcreate = dcreate;
	}
	public String getIupdate() {
		return iupdate;
	}
	public void setIupdate(String iupdate) {
		this.iupdate = iupdate;
	}
	public Date getDupdate() {
		return dupdate;
	}
	public void setDupdate(Date dupdate) {
		this.dupdate = dupdate;
	}
	public String getCodecname() {
		return codecname;
	}
	public void setCodecname(String codecname) {
		this.codecname = codecname;
	}
	public String getSfReason() {
		return sfReason;
	}
	public void setSfReason(String sfReason) {
		this.sfReason = sfReason;
	}
	public String getCollateralnumber() {
		return collateralnumber;
	}
	public void setCollateralnumber(String collateralnumber) {
		this.collateralnumber = collateralnumber;
	}
	public String getPolicyno() {
		return policyno;
	}
	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}
	public String getOldpolicyno() {
		return oldpolicyno;
	}
	public void setOldpolicyno(String oldpolicyno) {
		this.oldpolicyno = oldpolicyno;
	}
	public String getPiiInsuredname() {
		return piiInsuredname;
	}
	public void setPiiInsuredname(String piiInsuredname) {
		this.piiInsuredname = piiInsuredname;
	}
	public String getPiiId() {
		return piiId;
	}
	public void setPiiId(String piiId) {
		this.piiId = piiId;
	}
	public String getPiaInsuredname() {
		return piaInsuredname;
	}
	public void setPiaInsuredname(String piaInsuredname) {
		this.piaInsuredname = piaInsuredname;
	}
	public String getPiaId() {
		return piaId;
	}
	public void setPiaId(String piaId) {
		this.piaId = piaId;
	}
	public String getPiaPostcode() {
		return piaPostcode;
	}
	public void setPiaPostcode(String piaPostcode) {
		this.piaPostcode = piaPostcode;
	}
	public String getPostaddress() {
		return postaddress;
	}
	public void setPostaddress(String postaddress) {
		this.postaddress = postaddress;
	}
	public String getAddresscode() {
		return addresscode;
	}
	public void setAddresscode(String addresscode) {
		this.addresscode = addresscode;
	}
	public String getBuilddetailinfo() {
		return builddetailinfo;
	}
	public void setBuilddetailinfo(String builddetailinfo) {
		this.builddetailinfo = builddetailinfo;
	}
	public String getStrSumfloors() {
		return strSumfloors;
	}
	public void setStrSumfloors(String strSumfloors) {
		this.strSumfloors = strSumfloors;
	}
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public BigDecimal getNumBuildarea() {
		return numBuildarea;
	}
	public void setNumBuildarea(BigDecimal numBuildarea) {
		this.numBuildarea = numBuildarea;
	}
	public String getAmtQ() {
		return amtQ;
	}
	public void setAmtQ(String amtQ) {
		this.amtQ = amtQ;
	}
	public String getPremQ() {
		return premQ;
	}
	public void setPremQ(String premQ) {
		this.premQ = premQ;
	}
	public String getAmtTot() {
		return amtTot;
	}
	public void setAmtTot(String amtTot) {
		this.amtTot = amtTot;
	}
	public String getRenewinsuranceflag() {
		return renewinsuranceflag;
	}
	public void setRenewinsuranceflag(String renewinsuranceflag) {
		this.renewinsuranceflag = renewinsuranceflag;
	}
	public String getExtracomcode() {
		return extracomcode;
	}
	public void setExtracomcode(String extracomcode) {
		this.extracomcode = extracomcode;
	}
	public String getBseq() {
		return bseq;
	}
	public void setBseq(String bseq) {
		this.bseq = bseq;
	}
	public String getWallmaterial() {
		return wallmaterial;
	}
	public void setWallmaterial(String wallmaterial) {
		this.wallmaterial = wallmaterial;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPremium() {
		return premium;
	}
	public void setPremium(String premium) {
		this.premium = premium;
	}
}
