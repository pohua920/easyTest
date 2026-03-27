package cn.com.sinosoft.dms.web;

import ins.framework.cache.CacheManager;
import ins.framework.common.Page;
import ins.framework.power.PowerService;
import ins.framework.web.Struts2Action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.saa.service.facade.CodeService;

@SuppressWarnings("serial")
public class CodeInputAction extends Struts2Action {
	public static final String CHANGE_METHOD = "change";

	public static final String QUERY_METHOD = "query";

	public static final String SELECT_METHOD = "select";

	public static final String CODE_INPUT = "codeInput";

	public static final String SESS_KEY = "CodeInputCondition";

	private int defaultPageSize = 50;

	private int totalCount;

	private int totalPage;

	private String codeselect;

	private String codeselectText;

	private String fieldIndex;

	private String fieldValue;

	private String codeMethod;

	private String codeType;

	private String typeParam;

	private String codeRelation;

	private String isClear;

	private String otherCondition;

	private String callBackMethod;

	private String getDataMethod;

	private String elementOrder;

	private String elementLength;

	private String riskCode;

	private String extraCond;

	private List<String> codeValues = new ArrayList<String>();

	private List<String> codeLabels = new ArrayList<String>();

	private List<Object[]> codeList = new ArrayList<Object[]>(0);

	private CodeService codeService;

	private PowerService powerService;

	private static CacheManager cacheManager = CacheManager.getIntance("Code");

	private String treeContent;

	public String getTreeContent() {
		return treeContent;
	}

	public void setTreeContent(String treeContent) {
		this.treeContent = treeContent;
	}

	public List<Object[]> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<Object[]> codeList) {
		this.codeList = codeList;
	}

	public PowerService getPowerService() {
		return powerService;
	}

	public void setPowerService(PowerService powerService) {
		this.powerService = powerService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public List<String> getCodeLabels() {
		return codeLabels;
	}

	public void setCodeLabels(List<String> codeLabels) {
		this.codeLabels = codeLabels;
	}

	public List<String> getCodeValues() {
		return codeValues;
	}

	public void setCodeValues(List<String> codeValues) {
		this.codeValues = codeValues;
	}

	@SuppressWarnings("unchecked")
	public String query() throws Exception {

		if (codeMethod.equalsIgnoreCase(SELECT_METHOD)
				|| codeMethod.equalsIgnoreCase(QUERY_METHOD)) {
			if (fieldValue == null || fieldValue.equals("null")) {
				fieldValue = "";
			}
			fieldValue = fieldValue + "%";
		}

		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = defaultPageSize;
		}

		if (getDataMethod == null || getDataMethod.equalsIgnoreCase("null")
				|| getDataMethod.equalsIgnoreCase("")) {
			if (codeType.equals("PropKindCode")
					|| codeType.equals("CarKindCode")
					|| codeType.equals("PersonKindCode")) {
				extraCond = otherCondition;
				riskCode = null;
			} else {
				riskCode = otherCondition;
			}
			Page page = codeService.listCodeSelect(codeType, riskCode, "C",
					fieldValue, pageNo, pageSize, (String) getSession()
							.getAttribute("UserCode"), typeParam, extraCond);
			List<Object[]> list = (List<Object[]>) page.getResult();

			codeList = list;
			totalCount = (int) page.getTotalCount();
			totalPage = (int) page.getTotalPageCount();
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

				codeLabels.add(arrValue[0] + "--" + arrValue[1]);

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

	// public String getRiskTree(){
	// // new一个树模型构造器
	// DefaultTreeModel treeModel = new DefaultTreeModel();
	// List<SaaBusinessline> businesslines= new ArrayList<SaaBusinessline>();
	// List<SaaClass> classes = new ArrayList<SaaClass>();
	// List<SaaRisk> risks = new ArrayList<SaaRisk>();
	// WebTreeNode rootNode = new WebTreeNode("请选择...", "rootNode");
	// rootNode.setAction("javascript:selectAllClass();");
	//		
	// Object result =
	// cacheManager.getCache(IConstants.ALL_BUSINESSLINE_CODE_KEY);
	// if (result!=null) {
	// businesslines = (List<SaaBusinessline>)result;
	// }else {
	// businesslines = codeService.getAllbusinesslineCodes();
	// cacheManager.putCache(IConstants.ALL_BUSINESSLINE_CODE_KEY,
	// businesslines);
	// }
	// treeModel.addRootNode(rootNode);
	// for (SaaBusinessline dbusinessline : businesslines) {
	// WebTreeNode businesslineNode = new
	// WebTreeNode(dbusinessline.getBusinessLineName()+"("+dbusinessline.getBusinessLineCode()+")","businesslineNode"+dbusinessline.getBusinessLineCode());
	// // String key =
	// cacheManager.generateCacheKey(IConstants.ALL_BUSINESSLINE_CODE_KEY,dbusinessline.getBusinessLineCode());
	// // Object object = cacheManager.getCache(key);
	// // if (object!=null) {
	// // classes = (List<SaaClass>)object;
	// // } else {
	// classes =
	// codeService.getAllClassCodeBybusinessLineCode(dbusinessline.getBusinessLineCode());
	// // cacheManager.putCache(key, classes);
	// // }
	// if (classes.size()>0) {
	// businesslineNode.setValue(dbusinessline.getBusinessLineCode());
	// // if ("01".startsWith(dbusinessline.getBusinessLineCode())) {
	// //
	// // businesslineNode.setDisabled(true);
	// // }
	// rootNode.addNode(businesslineNode);
	// }
	// for (SaaClass dclass : classes) {
	// WebTreeNode classNode = new
	// WebTreeNode(dclass.getClassName()+"("+dclass.getClassCode()+")","classNode"+dclass.getClassCode());
	// // String keys =
	// cacheManager.generateCacheKey(IConstants.ALL_BUSINESSLINE_CODE_KEY,dclass.getClassCode());
	// // Object objects = cacheManager.getCache(key);
	// // if (object!=null) {
	// // risks = (List<SaaRisk>)object;
	// // } else {
	// risks = codeService.getAllRiskCodeByClassCode(dclass.getClassCode());
	// // cacheManager.putCache(key, risks);
	// // }
	// if (risks.size()>0) {
	// // if (businesslineNode.isDisabled()) {
	// // classNode.setDisabled(true);
	// // }
	// classNode.setValue(businesslineNode.getValue()+"."+dclass.getClassCode());
	// businesslineNode.addNode(classNode);
	// }
	// for (SaaRisk drisk : risks) {
	// // if (drisk.getRiskcname()!=null && !"".equals(drisk.getRiskcname())) {
	// // WebTreeNode riskNode = new
	// WebTreeNode(drisk.getRiskcname()+"("+drisk.getRiskCode()+")","riskNode"+drisk.getRiskCode());
	// // if (classNode.isDisabled()) {
	// // riskNode.setDisabled(true);
	// // }
	// // riskNode.setValue(classNode.getValue()+"."+drisk.getRiskCode());
	// // classNode.addNode(riskNode);
	// }
	// }
	// }
	// }
	//		  
	// TreeDirector director = new DefaultTreeDirector();
	// CheckXTreeBuilder treeBuilder = new CheckXTreeBuilder();
	// // RadioXTreeBuilder treeBuilder = new RadioXTreeBuilder ();
	// // ExtTreeBuilder treeBuilder = new ExtLoadTreeBuilder();
	// // treeBuilder.init(getRequest());
	// // director.build(treeModel, treeBuilder);
	// // this.treeContent = treeBuilder.getTreeScript();
	// //
	// //
	// //
	// // return SUCCESS;
	// // }
	// // /**
	// // * 清除系统中的缓存
	// // *
	// // */
	// // public String clearMemory() {
	// // CacheManager.clearAllCacheManager();
	// // return SUCCESS;
	// // }

	/**
	 * 得到代码的树状结构
	 * 
	 * @return
	 * @throws Exception 
	 */

	/*
	 * public String getCodeTree() { pageNo = 1; try { pageSize =
	 * codeService.getCountCompany().intValue(); this.query(); List<Code>
	 * comCodeList = new ArrayList<Code>(0); for (Object[] obj : codeList) {
	 * Code code = new Code(); code.setCode((String) obj[0]);
	 * code.setName((String) obj[1]); code.setUpperCode((String) obj[2]);
	 * comCodeList.add(code); } this.writeJSONData(comCodeList, "code", "name",
	 * "upperCode"); } catch (Exception e) { this.writeJSONMsg(e.getMessage());
	 * }
	 * 
	 * return null; }
	 */

	public String queryContinue() throws Exception {
		this.query();
		return SUCCESS;
	}

	public String getCallBackMethod() {
		return callBackMethod;
	}

	public void setCallBackMethod(String callBackMethod) {
		this.callBackMethod = callBackMethod;
	}

	public String getCodeMethod() {
		return codeMethod;
	}

	public void setCodeMethod(String codeMethod) {
		this.codeMethod = codeMethod;
	}

	public String getCodeRelation() {
		return codeRelation;
	}

	public void setCodeRelation(String codeRelation) {
		this.codeRelation = codeRelation;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getElementLength() {
		return elementLength;
	}

	public void setElementLength(String elementLength) {
		this.elementLength = elementLength;
	}

	public String getElementOrder() {
		return elementOrder;
	}

	public void setElementOrder(String elementOrder) {
		this.elementOrder = elementOrder;
	}

	public String getFieldIndex() {
		return fieldIndex;
	}

	public void setFieldIndex(String fieldIndex) {
		this.fieldIndex = fieldIndex;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getGetDataMethod() {
		return getDataMethod;
	}

	public void setGetDataMethod(String getDataMethod) {
		this.getDataMethod = getDataMethod;
	}

	public String getIsClear() {
		return isClear;
	}

	public void setIsClear(String isClear) {
		this.isClear = isClear;
	}

	public String getOtherCondition() {
		return otherCondition;
	}

	public void setOtherCondition(String otherCondition) {
		this.otherCondition = otherCondition;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public String getCodeselect() {
		return codeselect;
	}

	public void setCodeselect(String codeselect) {
		this.codeselect = codeselect;
	}

	public String getCodeselectText() {
		return codeselectText;
	}

	public void setCodeselectText(String codeselectText) {
		this.codeselectText = codeselectText;
	}

	public String getTypeParam() {
		return typeParam;
	}

	public void setTypeParam(String typeParam) {
		this.typeParam = typeParam;
	}

	public String getExtraCond() {
		return extraCond;
	}

	public void setExtraCond(String extraCond) {
		this.extraCond = extraCond;
	}

}
