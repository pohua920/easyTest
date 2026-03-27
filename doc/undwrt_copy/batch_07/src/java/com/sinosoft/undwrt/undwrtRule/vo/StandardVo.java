package com.sinosoft.undwrt.undwrtRule.vo;

import java.lang.reflect.Method;

/**
 * 核保權限VO類.
 */
public class StandardVo {

    /** 屬性險類代碼. */
    private String classCode = "";
    
    /** 屬性險種代碼. */
    private String riskCode = "";
    
    /** 屬性機構代碼. */
    private String comCode = "";
    
    /** 屬性保單號. */
    private String policyno = "";
    
    /** 屬性簽到機構. */
    private String makeCom = "";
    
    /** 屬性保單類型. */
    private String policySort = "";
    
    /** 屬性車輛使用性質. */
    private String useNature = "";
    
    /** 屬性車輛種類. */
    private String carKind = "";
    
    /** 屬性險種大類. */
    private String riskClass ="";
    
    /** 屬性國民經濟行業代碼. */
    private String businessSource="";
    
    /** 屬性佔用性質. */
    private String possessNature="";
    
    /** 屬性模版號. */
    private int modelNo = 0;
    
    /** 屬性節點號. */
    private int nodeNo = 0;
    
    /** 屬性核保類型. */
    private String uwType = "";
    
    /** 屬性最大值. */
    private  double MaxNumber = 2147483647;
    
    /** 屬性Class. */
    private Class cl  = null;
    
    /** 屬性Method. */
    private Method method = null;

    /** 屬性產品代碼. */
    private String productcode = "";

    /**
	 * 獲取屬性險類代碼.
	 * 
	 * @return 屬性險類代碼的值
	 */
    public String getClassCode() {
        return classCode;
    }

    /**
	 * 設置屬性險類代碼.
	 * 
	 * @param classCode
	 *            待設置的險類代碼的值
	 */
    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    /**
	 * 獲取屬性險種代碼.
	 * 
	 * @return 屬性險種代碼的值
	 */
    public String getRiskCode() {
        return riskCode;
    }

    /**
	 * 設置屬性險種代碼.
	 * 
	 * @param riskCode
	 *            待設置的險種代碼的值
	 */
    public void setRiskCode(String riskCode) {
        this.riskCode = riskCode;
    }

    /**
	 * 獲取屬性機構代碼.
	 * 
	 * @return 屬性機構代碼的值
	 */
    public String getComCode() {
        return comCode;
    }

    /**
	 * 設置屬性機構代碼.
	 * 
	 * @param comCode
	 *            待設置的機構代碼的值
	 */
    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    /**
	 * 獲取屬性保單號.
	 * 
	 * @return 屬性保單號的值
	 */
    public String getPolicyno() {
        return policyno;
    }

    /**
	 * 設置屬性保單號.
	 * 
	 * @param policyno
	 *            待設置的保單號的值
	 */
    public void setPolicyno(String policyno) {
        this.policyno = policyno;
    }

    /**
	 * 獲取屬性簽到機構.
	 * 
	 * @return 屬性簽到機構的值
	 */
    public String getMakeCom() {
        return makeCom;
    }

    /**
	 * 設置屬性簽到機構.
	 * 
	 * @param makeCom
	 *            待設置的簽到機構的值
	 */
    public void setMakeCom(String makeCom) {
        this.makeCom = makeCom;
    }

    /**
	 * 獲取屬性保單類型.
	 * 
	 * @return 屬性保單類型的值
	 */
    public String getPolicySort() {
        return policySort;
    }

    /**
	 * 設置屬性保單類型.
	 * 
	 * @param policySort
	 *            待設置的保單類型的值
	 */
    public void setPolicySort(String policySort) {
        this.policySort = policySort;
    }

    /**
	 * 獲取屬性車輛使用性質.
	 * 
	 * @return 屬性車輛使用性質的值
	 */
    public String getUseNature() {
        return useNature;
    }

    /**
	 * 設置屬性車輛使用性質.
	 * 
	 * @param useNature
	 *            待設置的車輛使用性質的值
	 */
    public void setUseNature(String useNature) {
        this.useNature = useNature;
    }

    /**
	 * 獲取屬性車輛種類.
	 * 
	 * @return 屬性車輛種類的值
	 */
    public String getCarKind() {
        return carKind;
    }

    /**
	 * 設置屬性車輛種類.
	 * 
	 * @param carKind
	 *            待設置的車輛種類的值
	 */
    public void setCarKind(String carKind) {
        this.carKind = carKind;
    }

    /**
	 * 獲取屬性險種大類.
	 * 
	 * @return 屬性險種大類的值
	 */
    public String getRiskClass() {
        return riskClass;
    }

    /**
	 * 設置屬性險種大類.
	 * 
	 * @param riskClass
	 *            待設置的險種大類的值
	 */
    public void setRiskClass(String riskClass) {
        this.riskClass = riskClass;
    }

    /**
	 * 獲取屬性國民經濟行業代碼.
	 * 
	 * @return 屬性國民經濟行業代碼的值
	 */
    public String getBusinessSource() {
        return businessSource;
    }

    /**
	 * 設置屬性國民經濟行業代碼.
	 * 
	 * @param businessSource
	 *            待設置的國民經濟行業代碼的值
	 */
    public void setBusinessSource(String businessSource) {
        this.businessSource = businessSource;
    }

    /**
	 * 獲取屬性佔用性質.
	 * 
	 * @return 屬性佔用性質的值
	 */
    public String getPossessNature() {
        return possessNature;
    }

    /**
	 * 設置屬性佔用性質.
	 * 
	 * @param possessNature
	 *            待設置的佔用性質的值
	 */
    public void setPossessNature(String possessNature) {
        this.possessNature = possessNature;
    }

    /**
	 * 獲取屬性模版號.
	 * 
	 * @return 屬性模版號的值
	 */
    public int getModelNo() {
        return modelNo;
    }

    /**
	 * 設置屬性模版號.
	 * 
	 * @param modelNo
	 *            待設置的模版號的值
	 */
    public void setModelNo(int modelNo) {
        this.modelNo = modelNo;
    }

    /**
	 * 獲取屬性節點號.
	 * 
	 * @return 屬性節點號的值
	 */
    public int getNodeNo() {
        return nodeNo;
    }

    /**
	 * 設置屬性節點號.
	 * 
	 * @param nodeNo
	 *            待設置的節點號的值
	 */
    public void setNodeNo(int nodeNo) {
        this.nodeNo = nodeNo;
    }

    /**
	 * 獲取屬性核保類型.
	 * 
	 * @return 屬性核保類型的值
	 */
    public String getUwType() {
        return uwType;
    }

    /**
	 * 設置屬性核保類型.
	 * 
	 * @param uwType
	 *            待設置的核保類型的值
	 */
    public void setUwType(String uwType) {
        this.uwType = uwType;
    }

    /**
	 * 獲取屬性最大值.
	 * 
	 * @return 屬性最大值的值
	 */
    public double getMaxNumber() {
        return MaxNumber;
    }

    /**
	 * 設置屬性最大值.
	 * 
	 * @param maxNumber
	 *            待設置的最大值的值
	 */
    public void setMaxNumber(double maxNumber) {
        MaxNumber = maxNumber;
    }

    /**
	 * 獲取屬性Class.
	 * 
	 * @return 屬性Class的值
	 */
    public Class getCl() {
        return cl;
    }

    /**
	 * 設置屬性Class.
	 * 
	 * @param cl
	 *            待設置的Class的值
	 */
    public void setCl(Class cl) {
        this.cl = cl;
    }

    /**
	 * 獲取屬性Method.
	 * 
	 * @return 屬性Method的值
	 */
    public Method getMethod() {
        return method;
    }

    /**
	 * 設置屬性Method.
	 * 
	 * @param method
	 *            待設置的Method的值
	 */
    public void setMethod(Method method) {
        this.method = method;
    }

    /**
	 * 獲取屬性產品代碼.
	 * 
	 * @return 屬性產品代碼的值
	 */
    public String getProductcode() {
        return productcode;
    }

    /**
	 * 設置屬性產品代碼.
	 * 
	 * @param productcode
	 *            待設置的產品代碼的值
	 */
    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }
}
