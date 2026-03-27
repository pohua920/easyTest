package com.sinosoft.undwrt.common.vo;

import java.io.Serializable;
import java.util.*;

import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.undwrt.common.model.PrpDuser;

/**
 * 这是PrpDuser-员工代码表的数据传输对象类 <br>
 * 创建于 2005-02-17 17:22:21.607 <br>
 * JToolpad(1.3.3) Vendor:zhouxianli1978@hotmail.com
 */
public class PrpDuserVo extends PrpDuser implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** 屬性The sinosoft query condition. */
    private QueryCondition queryCondition = new QueryCondition("", "", 1, 10);
    
    /** 屬性The sinosoft new passwd. */
    private String newPasswd = ""; // 新密码
    
    /** 屬性The sinosoft repeat passwd. */
    private String repeatPasswd = ""; // 重复密码
    
    /** 屬性The sinosoft sid. */
    private String sid = ""; // Session ID
    
    /** 屬性The sinosoft remote addr. */
    private String remoteAddr = ""; // Remote Addr
    
    /** 屬性The sinosoft login time. */
    private DateTime loginTime = new DateTime(); // Login time
    
    /** 屬性The sinosoft old passwd. */
    private String oldPasswd = "";// 老密码
    
    /** 屬性The sinosoft login com code. */
    private String loginComCode = ""; // 当前登录的机构代码
    
    /** 屬性The sinosoft login grade codes. */
    private String loginGradeCodes = ""; // 当前登录的岗位代码列表
    
    /** 屬性The sinosoft login system code. */
    private String loginSystemCode = ""; // 当前登录的系统代码
    
    /** 屬性The sinosoft config system code. */
    private String configSystemCode = "";// 当前正在配置的系统代码，主要用于菜单配置
    
    /** 屬性The sinosoft valid status name. */
    private String validStatusName = "无效"; // 存储效率状态的中文名称
    // 将validStatus中的0转化为无效1为有效
    /** 屬性The sinosoft current risk code. */
    private String currentRiskCode = "";
    
    /** 屬性The sinosoft prp duser flag2. */
    private String prpDuserFlag2 = ""; // 存储员工类别
    
    /** 屬性The sinosoft last config menu system code. */
    private String lastConfigMenuSystemCode = "";// 最后配置的菜单的系统代码
    
    /** 屬性The sinosoft current language. */
    private String currentLanguage = "C";//当前登录的语言
    
    /** 屬性機構名稱. */
    private String comName = "";// 所属机构名称
    
    /** 屬性The sinosoft input code. */
    private String inputCode = "";//当前操作员代码

//    private PrpDuserSubDto subDto = new PrpDuserSubDto();
    /** 屬性The sinosoft com agents. */
private Collection comAgents = new ArrayList();
    //Modify Begin Add By ChenYang 20070605,用户授权登录改造
//    private PrpDuserCADto cADto = new PrpDuserCADto();
    //Modify End Add By ChenYang 20070605,用户授权登录改造
    /**
	 * 默认构造方法,构造一个默认的PrpDuserDto对象.
	 */
    public PrpDuserVo() {
        //super.setValidStatus("1");
    }

    /**
	 * 獲取屬性the sinosoft login time.
	 * 
	 * @return 屬性the sinosoft login time的值
	 */
    public DateTime getLoginTime() {
        return loginTime;
    }

    /**
	 * 設置屬性the sinosoft login time.
	 * 
	 * @param loginTime
	 *            待設置的the sinosoft login time的值
	 */
    public void setLoginTime(DateTime loginTime) {
        this.loginTime = loginTime;
    }

    /**
	 * 獲取屬性the sinosoft new passwd.
	 * 
	 * @return 屬性the sinosoft new passwd的值
	 */
    public String getNewPasswd() {
        return newPasswd;
    }

    /**
	 * 設置屬性the sinosoft new passwd.
	 * 
	 * @param newPasswd
	 *            待設置的the sinosoft new passwd的值
	 */
    public void setNewPasswd(String newPasswd) {
        this.newPasswd = newPasswd;
    }

    /**
	 * 獲取屬性the sinosoft old passwd.
	 * 
	 * @return 屬性the sinosoft old passwd的值
	 */
    public String getOldPasswd() {
        return oldPasswd;
    }

    /**
	 * 設置屬性the sinosoft old passwd.
	 * 
	 * @param oldPasswd
	 *            待設置的the sinosoft old passwd的值
	 */
    public void setOldPasswd(String oldPasswd) {
        this.oldPasswd = oldPasswd;
    }

    /**
	 * 獲取屬性the sinosoft remote addr.
	 * 
	 * @return 屬性the sinosoft remote addr的值
	 */
    public String getRemoteAddr() {
        return remoteAddr;
    }

    /**
	 * 設置屬性the sinosoft remote addr.
	 * 
	 * @param remoteAddr
	 *            待設置的the sinosoft remote addr的值
	 */
    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    /**
	 * 獲取屬性the sinosoft repeat passwd.
	 * 
	 * @return 屬性the sinosoft repeat passwd的值
	 */
    public String getRepeatPasswd() {
        return repeatPasswd;
    }

    /**
	 * 設置屬性the sinosoft repeat passwd.
	 * 
	 * @param repeatPasswd
	 *            待設置的the sinosoft repeat passwd的值
	 */
    public void setRepeatPasswd(String repeatPasswd) {
        this.repeatPasswd = repeatPasswd;
    }

    /**
	 * 獲取屬性the sinosoft sid.
	 * 
	 * @return 屬性the sinosoft sid的值
	 */
    public String getSid() {
        return sid;
    }

    /**
	 * 設置屬性the sinosoft sid.
	 * 
	 * @param sid
	 *            待設置的the sinosoft sid的值
	 */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
	 * The Class QueryCondition.
	 */
    public class QueryCondition implements Serializable {
        
        /** 屬性The sinosoft query key. */
        private String queryKey = ""; // 处理的关键字,用于对应conditons
        
        /** 屬性The sinosoft conditions. */
        private String conditions = ""; // 查询条件
        
        /** 屬性The sinosoft page no. */
        private int pageNo = 1;
        
        /** 屬性The sinosoft rows per page. */
        private int rowsPerPage = 10;

        /**
		 * Instantiates a new query condition.
		 * 
		 * @param queryKey
		 *            the query key
		 * @param conditions
		 *            the conditions
		 * @param pageNo
		 *            the page no
		 * @param rowsPerPage
		 *            the rows per page
		 */
        public QueryCondition(String queryKey, String conditions, int pageNo,
                int rowsPerPage) {
            super();
            this.queryKey = queryKey;
            this.conditions = conditions;
            this.pageNo = pageNo;
            if (this.pageNo < 1) {
                this.pageNo = 1;
            }
            this.rowsPerPage = rowsPerPage;
        }

        /**
		 * 獲取屬性the sinosoft conditions.
		 * 
		 * @return 屬性the sinosoft conditions的值
		 */
        public String getConditions() {
            return conditions;
        }

        /**
		 * 設置屬性the sinosoft conditions.
		 * 
		 * @param conditions
		 *            待設置的the sinosoft conditions的值
		 */
        public void setConditions(String conditions) {
            this.conditions = conditions;
        }

        /**
		 * 獲取屬性the sinosoft page no.
		 * 
		 * @return 屬性the sinosoft page no的值
		 */
        public int getPageNo() {
            if (this.pageNo < 1) {
                this.pageNo = 1;
            }
            return pageNo;
        }

        /**
		 * 設置屬性the sinosoft page no.
		 * 
		 * @param pageNo
		 *            待設置的the sinosoft page no的值
		 */
        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        /**
		 * 獲取屬性the sinosoft query key.
		 * 
		 * @return 屬性the sinosoft query key的值
		 */
        public String getQueryKey() {
            return queryKey;
        }

        /**
		 * 設置屬性the sinosoft query key.
		 * 
		 * @param queryKey
		 *            待設置的the sinosoft query key的值
		 */
        public void setQueryKey(String queryKey) {
            this.queryKey = queryKey;
        }

        /**
		 * 獲取屬性the sinosoft rows per page.
		 * 
		 * @return 屬性the sinosoft rows per page的值
		 */
        public int getRowsPerPage() {
            return rowsPerPage;
        }

        /**
		 * 設置屬性the sinosoft rows per page.
		 * 
		 * @param rowsPerPage
		 *            待設置的the sinosoft rows per page的值
		 */
        public void setRowsPerPage(int rowsPerPage) {
            this.rowsPerPage = rowsPerPage;
        }
    }

    /**
	 * 獲取屬性the sinosoft query condition.
	 * 
	 * @return 屬性the sinosoft query condition的值
	 */
    public QueryCondition getQueryCondition() {
        return queryCondition;
    }

    /**
	 * Sets the query condition.
	 * 
	 * @param queryKey
	 *            the query key
	 * @param conditions
	 *            the conditions
	 * @param pageNo
	 *            the page no
	 * @param rowsPerPage
	 *            the rows per page
	 */
    public void setQueryCondition(String queryKey, String conditions,
            int pageNo, int rowsPerPage) {
        this.queryCondition = new QueryCondition(queryKey, conditions, pageNo,
                rowsPerPage);
    }

    /**
	 * 獲取屬性the sinosoft config system code.
	 * 
	 * @return 屬性the sinosoft config system code的值
	 */
    public String getConfigSystemCode() {
        return configSystemCode;
    }

    /**
	 * 設置屬性the sinosoft config system code.
	 * 
	 * @param configSystemCode
	 *            待設置的the sinosoft config system code的值
	 */
    public void setConfigSystemCode(String configSystemCode) {
        this.configSystemCode = configSystemCode;
    }

    /**
	 * 獲取屬性the sinosoft login system code.
	 * 
	 * @return 屬性the sinosoft login system code的值
	 */
    public String getLoginSystemCode() {
        return loginSystemCode;
    }

    /**
	 * 設置屬性the sinosoft login system code.
	 * 
	 * @param loginSystemCode
	 *            待設置的the sinosoft login system code的值
	 */
    public void setLoginSystemCode(String loginSystemCode) {
        this.loginSystemCode = loginSystemCode;
    }

//    public String getValidStatusName() {
//        String tempValidStatus = getValidStatus().trim();
//        if (tempValidStatus.equalsIgnoreCase("1")) {
//            return "有效";
//        }
//        if (tempValidStatus.equalsIgnoreCase("0")) {
//            return "无效";
//        }
//
//        return validStatusName;
//    }

    /**
 * 設置屬性the sinosoft valid status name.
 * 
 * @param validStatusName
 *            待設置的the sinosoft valid status name的值
 */
public void setValidStatusName(String validStatusName) {
        this.validStatusName = validStatusName;
    }

    /**
	 * 獲取屬性the sinosoft current risk code.
	 * 
	 * @return 屬性the sinosoft current risk code的值
	 */
    public String getCurrentRiskCode() {
        return currentRiskCode;
    }

    /**
	 * 設置屬性the sinosoft current risk code.
	 * 
	 * @param currentRiskCode
	 *            待設置的the sinosoft current risk code的值
	 */
    public void setCurrentRiskCode(String currentRiskCode) {
        this.currentRiskCode = currentRiskCode;
    }

    /**
	 * 獲取屬性the sinosoft prp duser flag2.
	 * 
	 * @return 屬性the sinosoft prp duser flag2的值
	 */
    public String getPrpDuserFlag2() {
        return prpDuserFlag2;
    }

    /**
	 * 設置屬性the sinosoft prp duser flag2.
	 * 
	 * @param prpDuserFlag2
	 *            待設置的the sinosoft prp duser flag2的值
	 */
    public void setPrpDuserFlag2(String prpDuserFlag2) {
        this.prpDuserFlag2 = prpDuserFlag2;
    }

    // -------------------------------------------------------------------------------------------
    /** 屬性The sinosoft query condition map. */
    private HashMap queryConditionMap = new HashMap();

    /**
	 * 獲取屬性the sinosoft query condition.
	 * 
	 * @param mapKey
	 *            the map key
	 * @return 屬性the sinosoft query condition的值
	 */
    public QueryCondition getQueryCondition(String mapKey) {
        QueryCondition queryCondition = (QueryCondition) queryConditionMap
                .get(mapKey);
        return queryCondition;
    }

    /**
	 * Sets the query condition.
	 * 
	 * @param mapKey
	 *            the map key
	 * @param pageNo
	 *            the page no
	 * @param rowsPerPage
	 *            the rows per page
	 * @param conditions
	 *            the conditions
	 */
    public void setQueryCondition(String mapKey, int pageNo, int rowsPerPage,
            String conditions) {
        QueryCondition queryCondition = new QueryCondition("", conditions,
                pageNo, rowsPerPage);
        queryConditionMap.put(mapKey, queryCondition);
    }

    /**
	 * 獲取屬性the sinosoft login com code.
	 * 
	 * @return 屬性the sinosoft login com code的值
	 */
    public String getLoginComCode() {
        return loginComCode;
    }

    /**
	 * 設置屬性the sinosoft login com code.
	 * 
	 * @param loginComCode
	 *            待設置的the sinosoft login com code的值
	 */
    public void setLoginComCode(String loginComCode) {
        this.loginComCode = loginComCode;
    }

    /**
	 * 獲取屬性the sinosoft login grade codes.
	 * 
	 * @return 屬性the sinosoft login grade codes的值
	 */
    public String getLoginGradeCodes() {
        return loginGradeCodes;
    }

    /**
	 * 設置屬性the sinosoft login grade codes.
	 * 
	 * @param loginGradeCodes
	 *            待設置的the sinosoft login grade codes的值
	 */
    public void setLoginGradeCodes(String loginGradeCodes) {
        this.loginGradeCodes = loginGradeCodes;
    }

    /**
	 * 獲取屬性the sinosoft last config menu system code.
	 * 
	 * @return 屬性the sinosoft last config menu system code的值
	 */
    public String getLastConfigMenuSystemCode() {
        return lastConfigMenuSystemCode;
    }

    /**
	 * 設置屬性the sinosoft last config menu system code.
	 * 
	 * @param lastConfigMenuSystemCode
	 *            待設置的the sinosoft last config menu system code的值
	 */
    public void setLastConfigMenuSystemCode(String lastConfigMenuSystemCode) {
        this.lastConfigMenuSystemCode = lastConfigMenuSystemCode;
    }

    /**
	 * 獲取屬性機構名稱.
	 * 
	 * @return 屬性機構名稱的值
	 */
    public String getComName() {
        return comName;
    }

    /**
	 * 設置屬性機構名稱.
	 * 
	 * @param comName
	 *            待設置的機構名稱的值
	 */
    public void setComName(String comName) {
        this.comName = comName;
    }

    //Modify Begin Add By ChenYang 20070605,用户授权登录改造
//    public PrpDuserCADto getCADto() {
//        return cADto;
//    }
//
//    public void setCADto(PrpDuserCADto cADto) {
//        this.cADto = cADto;
//    }
    //Modify End Add By ChenYang 20070605,用户授权登录改造

//    public PrpDuserSubDto getSubDto() {
//        return subDto;
//    }
//
//    public void setSubDto(PrpDuserSubDto subDto) {
//        this.subDto = subDto;
//    }

    /**
	 * 獲取屬性the sinosoft current language.
	 * 
	 * @return 屬性the sinosoft current language的值
	 */
    public String getCurrentLanguage() {
        return currentLanguage;
    }

    /**
	 * 設置屬性the sinosoft current language.
	 * 
	 * @param currentLanguage
	 *            待設置的the sinosoft current language的值
	 */
    public void setCurrentLanguage(String currentLanguage) {
        this.currentLanguage = currentLanguage;
    }
    
    /**
	 * 获取属性代理点机构对应代理人.
	 * 
	 * @return 属性机构代理信息集合
	 */
    public Collection getComAgents() {
        return comAgents;
    }

    /**
	 * 設置屬性the sinosoft com agents.
	 * 
	 * @param comAgents
	 *            待設置的the sinosoft com agents的值
	 */
    public void setComAgents(Collection comAgents) {
        this.comAgents = comAgents;
    }

	/**
	 * 獲取屬性the sinosoft input code.
	 * 
	 * @return 屬性the sinosoft input code的值
	 */
	public String getInputCode() {
		return inputCode;
	}

	/**
	 * 設置屬性the sinosoft input code.
	 * 
	 * @param inputCode
	 *            待設置的the sinosoft input code的值
	 */
	public void setInputCode(String inputCode) {
		this.inputCode = inputCode;
	}

}
