package com.sinosoft.undwrt.common.web;

import ins.framework.cache.CacheManager;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.power.PowerService;
import ins.framework.web.Struts2Action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.sinosoft.platform.common.service.facade.CodeService;
import com.sinosoft.undwrt.common.model.PrpDrisk;
import com.sinosoft.undwrt.common.service.facade.PrpDriskService;
import com.sinosoft.undwrt.common.service.facade.PrpDuserService;

/**
 * 雙擊域代碼處理類.
 */
@SuppressWarnings("serial")
public class CodeInputAction extends Struts2Action {
	
	/** The Constant CHANGE_METHOD. */
	public static final String CHANGE_METHOD = "change";

	/** The Constant QUERY_METHOD. */
	public static final String QUERY_METHOD = "query";

	/** The Constant SELECT_METHOD. */
	public static final String SELECT_METHOD = "select";

	/** The Constant CODE_INPUT. */
	public static final String CODE_INPUT = "codeInput";

	/** The Constant SESS_KEY. */
	public static final String SESS_KEY = "CodeInputCondition";

	/** 屬性默認每頁記錄數. */
	private int defaultPageSize = 20;

	/** 屬性總記錄數. */
	private int totalCount;

	/** 屬性總頁數. */
	private int totalPage;

	/** 屬性codeselect. */
	private String codeselect;

	/** 屬性代碼域本身. */
	private String codeselectText;

	/** 屬性代碼域本身. */
	private String fieldIndex;

	/** 屬性代碼域的值. */
	private String fieldValue;

	/** 屬性codeMethod. */
	private String codeMethod;

	/** 屬性代碼類型. */
	private String codeType;

	/** 屬性類型參數. */
	private String typeParam;

	/** 屬性相關賦值域偏移量，以","分割. */
	private String codeRelation;

	/** 屬性查不到代碼時是否清空相關域. */
	private String isClear;

	/** 屬性其它條件（如key = value，key = value的）形式. */
	private String otherCondition;

	/** 屬性執行完後回調的方法. */
	private String callBackMethod;

	/** 屬性取值方法，當CODETYPE為自定義時取數據用（暫不支持）. */
	private String getDataMethod;

	/** 屬性elementOrder. */
	private String elementOrder;

	/** 屬性elementLength. */
	private String elementLength;

	/** 屬性險種代碼. */
	private String riskCode;

	/** 屬性extraCond. */
	private String extraCond;

	/** 屬性代碼值. */
	private List<String> codeValues = new ArrayList<String>();

	/** 屬性代碼表. */
	private List<String> codeLabels = new ArrayList<String>();

	/** 屬性代碼列表. */
	private List<Object[]> codeList = new ArrayList<Object[]>(0);

	/** 屬性代碼接口. */
	private CodeService codeService;

	/** 屬性權限接口. */
	private PowerService powerService;


	/** 屬性險種接口. */
	private PrpDriskService prpDriskService;

	/** 屬性用戶訊息接口. */
	private PrpDuserService prpDuserService;


	/**
	 * 雙擊域代碼查詢.
	 * 
	 * @return 查詢結果
	 * @throws Exception
	 *             異常
	 */
	public String query() throws Exception {
		String conditions = "";
		if (codeMethod.equalsIgnoreCase(SELECT_METHOD)
				|| codeMethod.equalsIgnoreCase(QUERY_METHOD)) {
			if (fieldValue == null || fieldValue.equals("null")) {
				fieldValue = "";
			}
			fieldValue = "%" + fieldValue + "%";
		}

		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = defaultPageSize;
		}

		if (getDataMethod == null || getDataMethod.equalsIgnoreCase("null")
				|| getDataMethod.equalsIgnoreCase("")) {
			QueryRule queryRule = QueryRule.getInstance();
			Page page = null;
			List<Object[]> list = null;

			riskCode = otherCondition;
			conditions = genQueryConditions(codeType, fieldValue, "code",
					riskCode, codeMethod, otherCondition, otherCondition);
			queryRule.addSql(conditions);
			// 险种
			if (codeType.equals("RiskCode")) {
				page = prpDriskService.queryRulePrpDriskPage(queryRule, pageNo,
						pageSize);
				list = new ArrayList<Object[]>();
				List<PrpDrisk> prpDriskList = page.getResult();
				for (PrpDrisk prpDrisk : prpDriskList) {
					Object[] obj = new Object[2];
					obj[0] = prpDrisk.getRiskCode();
					obj[1] = prpDrisk.getRiskCName();
					list.add(obj);
				}
			} else {
				/*
				 * page = codeService .listCodeSelect(codeType, riskCode, "C",
				 * fieldValue, pageNo, pageSize, (String) getSession()
				 * .getAttribute("UserCode"), typeParam, extraCond);
				 */
				list = (List<Object[]>) page.getResult();
			}
			codeList = list;
			if ((int) page.getTotalCount() != -1) {
				totalCount = (int) page.getTotalCount();
				totalPage = (int) page.getTotalPageCount();
			}
			if (totalPage == 0) {// 使无查询记录时不能翻页
				// pageNo=0;
				totalPage = 1;
			}
			int objectLength;
			for (int i = 0; i < list.size(); i++) {
				String valueString = "";
				Object[] arrValue = list.get(i);
				objectLength = arrValue.length;
				for (int j = 0; j < objectLength; j++) {
					String value = (arrValue[j] + "").trim();
					if (j != objectLength - 1) {
						valueString += value + "_FIELD_SEPARATOR_";
					} else {
						valueString += value;
					}
				}
				codeValues.add(valueString);

				if (codeType.equals("KPICode")) {
					codeLabels.add(arrValue[0] + "--" + arrValue[1] + "--"
							+ arrValue[2]);
				} else {
					codeLabels.add(arrValue[0] + "--" + arrValue[1]);
				}
			}
		} else {
			try {
				Class[] paramTypes = new Class[0];
				Object[] args = new Object[0];
				Method method = this.getClass().getDeclaredMethod(
						getDataMethod, paramTypes);
				method.invoke(this, args);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	/**
	 * 生成代码查询条件.
	 * 
	 * @param codeType
	 *            ：代码类型
	 * @param codeValue
	 *            ：代码值
	 * @param inputType
	 *            ：录入类型
	 * @param riskCode
	 *            ：险种
	 * @param method
	 *            the method
	 * @param fieldext
	 *            the fieldext
	 * @param accepterCode
	 *            the accepter code
	 * @return the string
	 * @throws Exception
	 *             異常
	 */
	private String genQueryConditions(String codeType, String codeValue,
			String inputType, String riskCode, String method, String fieldext,
			String accepterCode) throws Exception {

		String conditions = "";
		// 险种
		if (codeType.equals("RiskCode")) {
			if (inputType.equals("code")) {
				if (method.equals("select"))
					conditions = " RiskCode like '" + codeValue
							+ "%' AND ValidStatus='1' ";
				else
					conditions = " RiskCode ='" + codeValue
							+ "'  AND ValidStatus='1' ";
			} else {
				if (method.equals("select"))
					conditions = " RiskCName like '" + codeValue
							+ "%'  AND ValidStatus='1' ";
				else
					conditions = " RiskName ='" + codeValue
							+ "' AND ValidStatus='1' ";
			}
			conditions += "  ORDER BY RiskCode";
		}
		return conditions;
	}

	/**
	 * 清除系统中的缓存.
	 * 
	 * @return the string
	 */
	public String clearMemory() {
		CacheManager.clearAllCacheManager();
		// PowerBean.clearMemory();
		return SUCCESS;
	}

	/**
	 * 得到代码的树状结构.
	 * 
	 * @return 屬性the sinosoft code tree的值
	 */

	public String getCodeTree() {

		return null;
	}

	/**
	 * Query continue.
	 * 
	 * @return the string
	 * @throws Exception
	 *             異常
	 */
	public String queryContinue() throws Exception {
		this.query();
		return SUCCESS;
	}

	/**
	 * 獲取屬性執行完後回調的方法.
	 * 
	 * @return 屬性執行完後回調的方法的值
	 */
	public String getCallBackMethod() {
		return callBackMethod;
	}

	/**
	 * 設置屬性執行完後回調的方法.
	 * 
	 * @param callBackMethod
	 *            待設置的執行完後回調的方法的值
	 */
	public void setCallBackMethod(String callBackMethod) {
		this.callBackMethod = callBackMethod;
	}

	/**
	 * 獲取屬性codeMethod.
	 * 
	 * @return 屬性codeMethod的值
	 */
	public String getCodeMethod() {
		return codeMethod;
	}

	/**
	 * 設置屬性codeMethod.
	 * 
	 * @param codeMethod
	 *            待設置的codeMethod的值
	 */
	public void setCodeMethod(String codeMethod) {
		this.codeMethod = codeMethod;
	}

	/**
	 * 獲取屬性相關賦值域偏移量，以","分割.
	 * 
	 * @return 屬性相關賦值域偏移量，以","分割的值
	 */
	public String getCodeRelation() {
		return codeRelation;
	}

	/**
	 * 設置屬性相關賦值域偏移量，以","分割.
	 * 
	 * @param codeRelation
	 *            待設置的相關賦值域偏移量，以","分割的值
	 */
	public void setCodeRelation(String codeRelation) {
		this.codeRelation = codeRelation;
	}

	/**
	 * 獲取屬性代碼類型.
	 * 
	 * @return 屬性代碼類型的值
	 */
	public String getCodeType() {
		return codeType;
	}

	/**
	 * 設置屬性代碼類型.
	 * 
	 * @param codeType
	 *            待設置的代碼類型的值
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	/**
	 * 獲取屬性elementLength.
	 * 
	 * @return 屬性elementLength的值
	 */
	public String getElementLength() {
		return elementLength;
	}

	/**
	 * 設置屬性elementLength.
	 * 
	 * @param elementLength
	 *            待設置的elementLength的值
	 */
	public void setElementLength(String elementLength) {
		this.elementLength = elementLength;
	}

	/**
	 * 獲取屬性elementOrder.
	 * 
	 * @return 屬性elementOrder的值
	 */
	public String getElementOrder() {
		return elementOrder;
	}

	/**
	 * 設置屬性elementOrder.
	 * 
	 * @param elementOrder
	 *            待設置的elementOrder的值
	 */
	public void setElementOrder(String elementOrder) {
		this.elementOrder = elementOrder;
	}

	/**
	 * 獲取屬性代碼域本身.
	 * 
	 * @return 屬性代碼域本身的值
	 */
	public String getFieldIndex() {
		return fieldIndex;
	}

	/**
	 * 設置屬性代碼域本身.
	 * 
	 * @param fieldIndex
	 *            待設置的代碼域本身的值
	 */
	public void setFieldIndex(String fieldIndex) {
		this.fieldIndex = fieldIndex;
	}

	/**
	 * 獲取屬性代碼域的值.
	 * 
	 * @return 屬性代碼域的值的值
	 */
	public String getFieldValue() {
		return fieldValue;
	}

	/**
	 * 設置屬性代碼域的值.
	 * 
	 * @param fieldValue
	 *            待設置的代碼域的值的值
	 */
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	/**
	 * 獲取屬性the sinosoft gets the data method.
	 * 
	 * @return 屬性the sinosoft gets the data method的值
	 */
	public String getGetDataMethod() {
		return getDataMethod;
	}

	/**
	 * 設置屬性the sinosoft gets the data method.
	 * 
	 * @param getDataMethod
	 *            待設置的the sinosoft gets the data method的值
	 */
	public void setGetDataMethod(String getDataMethod) {
		this.getDataMethod = getDataMethod;
	}

	/**
	 * 獲取屬性the sinosoft checks if is clear.
	 * 
	 * @return 屬性the sinosoft checks if is clear的值
	 */
	public String getIsClear() {
		return isClear;
	}

	/**
	 * 設置屬性the sinosoft checks if is clear.
	 * 
	 * @param isClear
	 *            待設置的the sinosoft checks if is clear的值
	 */
	public void setIsClear(String isClear) {
		this.isClear = isClear;
	}

	/**
	 * 獲取屬性其它條件（如key = value，key = value的）形式.
	 * 
	 * @return 屬性其它條件（如key = value，key = value的）形式的值
	 */
	public String getOtherCondition() {
		return otherCondition;
	}

	/**
	 * 設置屬性其它條件（如key = value，key = value的）形式.
	 * 
	 * @param otherCondition
	 *            待設置的其它條件（如key = value，key = value的）形式的值
	 */
	public void setOtherCondition(String otherCondition) {
		this.otherCondition = otherCondition;
	}

	/**
	 * 設置屬性代碼接口.
	 * 
	 * @param codeService
	 *            待設置的代碼接口的值
	 */
	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	/**
	 * 獲取屬性代碼接口.
	 * 
	 * @return 屬性代碼接口的值
	 */
	public CodeService getCodeService() {
		return codeService;
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
	 * 獲取屬性總記錄數.
	 * 
	 * @return 屬性總記錄數的值
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 設置屬性總記錄數.
	 * 
	 * @param totalCount
	 *            待設置的總記錄數的值
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 獲取屬性總頁數.
	 * 
	 * @return 屬性總頁數的值
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * 設置屬性總頁數.
	 * 
	 * @param totalPage
	 *            待設置的總頁數的值
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * 獲取屬性codeselect.
	 * 
	 * @return 屬性codeselect的值
	 */
	public String getCodeselect() {
		return codeselect;
	}

	/**
	 * 設置屬性codeselect.
	 * 
	 * @param codeselect
	 *            待設置的codeselect的值
	 */
	public void setCodeselect(String codeselect) {
		this.codeselect = codeselect;
	}

	/**
	 * 獲取屬性代碼域本身.
	 * 
	 * @return 屬性代碼域本身的值
	 */
	public String getCodeselectText() {
		return codeselectText;
	}

	/**
	 * 設置屬性代碼域本身.
	 * 
	 * @param codeselectText
	 *            待設置的代碼域本身的值
	 */
	public void setCodeselectText(String codeselectText) {
		this.codeselectText = codeselectText;
	}

	/**
	 * 獲取屬性類型參數.
	 * 
	 * @return 屬性類型參數的值
	 */
	public String getTypeParam() {
		return typeParam;
	}

	/**
	 * 設置屬性類型參數.
	 * 
	 * @param typeParam
	 *            待設置的類型參數的值
	 */
	public void setTypeParam(String typeParam) {
		this.typeParam = typeParam;
	}

	/**
	 * 獲取屬性extraCond.
	 * 
	 * @return 屬性extraCond的值
	 */
	public String getExtraCond() {
		return extraCond;
	}

	/**
	 * 設置屬性extraCond.
	 * 
	 * @param extraCond
	 *            待設置的extraCond的值
	 */
	public void setExtraCond(String extraCond) {
		this.extraCond = extraCond;
	}

	/**
	 * 獲取屬性用戶訊息接口.
	 * 
	 * @return 屬性用戶訊息接口的值
	 */
	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	/**
	 * 設置屬性用戶訊息接口.
	 * 
	 * @param prpDuserService
	 *            待設置的用戶訊息接口的值
	 */
	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	/**
	 * 獲取屬性險種接口.
	 * 
	 * @return 屬性險種接口的值
	 */
	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}

	/**
	 * 設置屬性險種接口.
	 * 
	 * @param prpDriskService
	 *            待設置的險種接口的值
	 */
	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}

	/**
	 * 獲取屬性代碼列表.
	 * 
	 * @return 屬性代碼列表的值
	 */
	public List<Object[]> getCodeList() {
		return codeList;
	}

	/**
	 * 設置屬性代碼列表.
	 * 
	 * @param codeList
	 *            待設置的代碼列表的值
	 */
	public void setCodeList(List<Object[]> codeList) {
		this.codeList = codeList;
	}

	/**
	 * 獲取屬性權限接口.
	 * 
	 * @return 屬性權限接口的值
	 */
	public PowerService getPowerService() {
		return powerService;
	}

	/**
	 * 設置屬性權限接口.
	 * 
	 * @param powerService
	 *            待設置的權限接口的值
	 */
	public void setPowerService(PowerService powerService) {
		this.powerService = powerService;
	}

	/**
	 * 獲取屬性代碼表.
	 * 
	 * @return 屬性代碼表的值
	 */
	public List<String> getCodeLabels() {
		return codeLabels;
	}

	/**
	 * 設置屬性代碼表.
	 * 
	 * @param codeLabels
	 *            待設置的代碼表的值
	 */
	public void setCodeLabels(List<String> codeLabels) {
		this.codeLabels = codeLabels;
	}

	/**
	 * 獲取屬性代碼值.
	 * 
	 * @return 屬性代碼值的值
	 */
	public List<String> getCodeValues() {
		return codeValues;
	}

	/**
	 * 設置屬性代碼值.
	 * 
	 * @param codeValues
	 *            待設置的代碼值的值
	 */
	public void setCodeValues(List<String> codeValues) {
		this.codeValues = codeValues;
	}

}