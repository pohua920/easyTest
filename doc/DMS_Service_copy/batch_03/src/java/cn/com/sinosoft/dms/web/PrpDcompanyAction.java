package cn.com.sinosoft.dms.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jcreate.e3.tree.Node;
import net.jcreate.e3.tree.TreeDirector;
import net.jcreate.e3.tree.TreeModel;
import net.jcreate.e3.tree.UncodeException;
import net.jcreate.e3.tree.UserDataUncoder;
import net.jcreate.e3.tree.support.AbstractWebTreeModelCreator;
import net.jcreate.e3.tree.support.DefaultNodeComparator;
import net.jcreate.e3.tree.support.DefaultTreeDirector;
import net.jcreate.e3.tree.support.DefaultTreeModel;
import net.jcreate.e3.tree.support.RequestUtil;
import net.jcreate.e3.tree.support.WebTreeBuilder;
import net.jcreate.e3.tree.support.WebTreeDynamicNode;
import net.jcreate.e3.tree.xtree.XLoadSubTreeBuilder;
import net.jcreate.e3.tree.xtree.XLoadTreeBuilder;
import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.dms.model.PrpDcompanyGrade;
import cn.com.sinosoft.dms.model.PrpDcompanyGradeConfig;
import cn.com.sinosoft.dms.model.PrpDcompanyTrace;
import cn.com.sinosoft.dms.service.facade.PrpDcompanyGradeService;
import cn.com.sinosoft.dms.service.facade.PrpDcompanyService;
import cn.com.sinosoft.dms.service.facade.PrpDcompanyTraceService;
import cn.com.sinosoft.ims.user.vo.UtiIUserVO;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.inf.cross.crossorg.CrossOrgSender;
import cn.com.sinosoft.inf.dict.util.BeanUtilsEx;

public class PrpDcompanyAction extends Struts2Action {

	/**
	 * 代码类型
	 */
	private static final long serialVersionUID = 1L;
	private PrpDcompanyTraceService prpDcompanyTraceService;// 审核表的接口
	private PrpDcompanyTrace prpDcompanyTrace; // 审核对象
	private PrpDcompanyService prpDcompanyService;
	private PrpDcompany prpDcompany;// 有效状态可以为1 和0
	private PrpDcompany prpDcompany1;// 有效状态为1的公司信息
	private String editType;
	private PrpDcompanyGrade prpDcompanyGrade;
	private PrpDcompanyGradeConfig prpDcompanyGradeConfig;
	private List comCodList;// 公司代码所有信息
	private String uplevel;// 上级代码
	private String currentCode;// 用户点击树的当前节点的机构代码
	private Map<String, String> upCodeMap;// 所有上级机构的代码,代码中文名
	private PrpDcompanyGradeService prpDcompanyGradeService;
	private String comCode;
	private String flag1;	//prpdcompany中标志字段第一位
	private String flag2;	//prpdcompany中标志字段第二位
	private String auditSuggest;//审核意见
	private String applicantDesc;//申请描述
	private String remark;//备注
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getApplicantDesc() {
		return applicantDesc;
	}

	public void setApplicantDesc(String applicantDesc) {
		this.applicantDesc = applicantDesc;
	}
	public String getAuditSuggest() {
		return auditSuggest;
	}

	public void setAuditSuggest(String auditSuggest) {
		this.auditSuggest = auditSuggest;
	}
	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public PrpDcompanyTraceService getPrpDcompanyTraceService() {
		return prpDcompanyTraceService;
	}

	public void setPrpDcompanyTraceService(
			PrpDcompanyTraceService prpDcompanyTraceService) {
		this.prpDcompanyTraceService = prpDcompanyTraceService;
	}

	public PrpDcompanyTrace getPrpDcompanyTrace() {
		return prpDcompanyTrace;
	}

	public void setPrpDcompanyTrace(PrpDcompanyTrace prpDcompanyTrace) {
		this.prpDcompanyTrace = prpDcompanyTrace;
	}

	public PrpDcompanyGradeService getPrpDcompanyGradeService() {
		return prpDcompanyGradeService;
	}

	public void setPrpDcompanyGradeService(
			PrpDcompanyGradeService prpDcompanyGradeService) {
		this.prpDcompanyGradeService = prpDcompanyGradeService;
	}

	public String getUplevel() {
		return uplevel;
	}

	public void setUplevel(String uplevel) {
		this.uplevel = uplevel;
	}

	public Map<String, String> getUpCodeMap() {
		return upCodeMap;
	}

	public void setUpCodeMap(Map<String, String> upCodeMap) {
		this.upCodeMap = upCodeMap;
	}

	public String getCurrentCode() {
		return currentCode;
	}

	public void setCurrentCode(String currentCode) {
		this.currentCode = currentCode;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	public PrpDcompany getPrpDcompany() {
		return prpDcompany;
	}

	public void setPrpDcompany(PrpDcompany prpDcompany) {
		this.prpDcompany = prpDcompany;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public PrpDcompanyGrade getPrpDcompanyGrade() {
		return prpDcompanyGrade;
	}

	public void setPrpDcompanyGrade(PrpDcompanyGrade prpDcompanyGrade) {
		this.prpDcompanyGrade = prpDcompanyGrade;
	}

	public PrpDcompanyGradeConfig getPrpDcompanyGradeConfig() {
		return prpDcompanyGradeConfig;
	}

	public void setPrpDcompanyGradeConfig(
			PrpDcompanyGradeConfig prpDcompanyGradeConfig) {
		this.prpDcompanyGradeConfig = prpDcompanyGradeConfig;
	}

	public List getComCodList() {
		return comCodList;
	}

	public void setComCodList(List comCodList) {
		this.comCodList = comCodList;
	}
	public String getFlag1() {
		return flag1;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	public String getFlag2() {
		return flag2;
	}

	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}
	// ----------------------------------------------------
	// 
	/**
	 * 注销/启用机构，将机构的有效状态设置为1/0 情况1注销：如果存在下级机构则不能注销本机构。 情况2启用：如果上级机构为无效则不能启用本机构。
	 */
//	public void changeValidStatus() {
//		comCode = prpDcompany.getComCode();
//		prpDcompany = prpDcompanyService.getPrpDcompany(comCode);
//		String validStatus = prpDcompany.getValidStatus();
//		String userCode = getSession().getAttribute("UserCode").toString();
//		if ("1".equals(validStatus)) {
//			if (isBottomNode(prpDcompany.getComCode())
//					&& prpDcompanyService.isHadUser(prpDcompany.getComCode())) {
//				prpDcompany.setValidStatus("0");
//				prpDcompanyService.updatePrpDcompany(prpDcompany, userCode);
//				renderText("success");
//			} else {
//				renderText("error");
//			}
//
//		} else {
//			if (upperComValid(prpDcompany)) {
//				prpDcompany.setValidStatus("1");
//				prpDcompanyService.updatePrpDcompany(prpDcompany, userCode);
//				renderText("success");
//			} else {
//				renderText("error1");
//			}
//		}
//	}
	/**
	 * 注销/启用机构，需要进行审核，将机构的有效状态设置为1/0 情况1注销：如果存在下级机构则不能注销本机构。 情况2启用：如果上级机构为无效则不能启用本机构。
	 */
	public String changeValidStatus() {
		comCode = prpDcompany.getComCode();
		prpDcompany = prpDcompanyService.getPrpDcompany(comCode);
		PrpDcompanyTrace prpDcompanyTrace = new PrpDcompanyTrace();
		String userCode = getSession().getAttribute("UserCode").toString();
		BeanUtilsEx.copyProperties(prpDcompanyTrace, prpDcompany);
		int serialNo = prpDcompanyTraceService.findByMaxId("PrpDcompanyTrace",
				"serialNo");
		prpDcompanyTrace.setSerialNo(serialNo);
		prpDcompanyTrace.setApplicantMen(userCode);
		prpDcompanyTrace.setApplicantType("3");//3为申请注销/启用
		prpDcompanyTrace.setApplicantDate(new Date());
		prpDcompanyTrace.setCurrentStatus("0");//0为初始化
		prpDcompanyTraceService.insertPrpDcompanyTrace(prpDcompanyTrace);
		return SUCCESS;
	}
	/** 判断上级代码是否有效 */
	private boolean upperComValid(PrpDcompany prpDcompany) {
		String upperComCode = prpDcompany.getUpperComCode();
		PrpDcompany company = prpDcompanyService.getPrpDcompany(upperComCode);
		String validStatus = company.getValidStatus();
		//modify by duanfa 20110726 start 总公司改为31000000
//		if ("00000000".equals(prpDcompany.getComCode())) {
		if ("31000000".equals(prpDcompany.getComCode())) {// 如果是总公司则返回真(目的:总公司是无效也可以更改本身)
				//modify by duanfa 20110726 start 总公司改为31000000
			return true;
		}
		if ("1".equals(validStatus)) {
			return true;
		} else {
			return false;
		}
	}

	public PrpDcompany getPrpDcompany1() {
		return prpDcompany1;
	}

	public void setPrpDcompany1(PrpDcompany prpDcompany1) {
		this.prpDcompany1 = prpDcompany1;
	}

	/**
	 * 加载frame
	 */
	public String initframe() {
		return SUCCESS;
	}

	/**
	 * 初始化树
	 */
	public String initTree() {
		comCodList = prpDcompanyService.getPrpDcompanyList();
		return SUCCESS;
	}

	/**
	 * 点击树的节点，右面出现查询界面，包括当前公司和下级公司的所有信息
	 */
	public String prepareQueryPrpDcompany() {
		// 此处填补权限控制逻辑
		if (currentCode != null && !"".equals(currentCode)) {// 设置查询内容
			setPrpDcompany(prpDcompanyService.getPrpDcompany(currentCode));
		}
		return SUCCESS;
	}

	/**
	 * 进入增加页面，添加公司
	 */
	public String prepareInsertPrpDcompany() {
		if (currentCode != null && !"".equals(currentCode)) {// 设置查询内容
			setPrpDcompany(prpDcompanyService.getPrpDcompany(currentCode));
		}
		return SUCCESS;
	}

	/**
	 * 通过公司代码查询公司信息并返回到更新页面。
	 */
	public String prepareUpdatePrpDcompany() {
		// TODO 编辑代码的权限校验 编辑代码的权限校验
		// String currcomCode = prpDcompany.getComCode();
		setPrpDcompany(prpDcompanyService.getPrpDcompany(comCode));
		String flag = prpDcompany.getFlag(); //获取prpDcompany的flag字段，然后set到flag1和flag2中　2010-7-16
		if(flag != null){
			setFlag1(flag.substring(0,1));
			setFlag2(flag.substring(1,2));
			prpDcompany.setBranchType(flag.substring(2,3));
			prpDcompany.setCenterFlag(flag.substring(3,4));
		}	
		// uplevel = prpDcompany.getUpperComCode();
		// upCodeMap = prpDcompanyService.upCodeMap(prpDcompany.getComCode());
		// upCodeMap.remove(prpDcompany.getComCode());//
		return SUCCESS;
	}

	// ---------------------------------------------------------------------
	/**
	 * 插入prpDcompany
	 */
	public String insertPrpDcompany() {
		// prpDcompany.setUpperComCode(uplevel);
		String userCode = getSession().getAttribute("UserCode").toString();
//		//以下为新增加的内容,2010-07-16 wanghaibo 针对于标志为flag(1,2,3,4位相加)
//		String flagfour = prpDcompany.getCenterFlag();		
//		String flagthree = prpDcompany.getBranchType();
//		String flagone = flag1;
//		String flagtwo = flag2;
//		prpDcompany.setFlag(flagone+flagtwo+flagthree+flagfour);
//		prpDcompanyService.insertPrpDcompany(prpDcompany, userCode);
		return SUCCESS;
	}

//	/**
//	 * 注释原有的更改方法 by wanghaibo
//	 */
//	public String updatePrpDcompany() {
//		prpDcompany.setUpperComCode(uplevel);
//		String userCode = getSession().getAttribute("UserCode").toString();
//		//以下为要更改的内容,2010-07-16 wanghaibo 针对于修改过的标志flag(1,2,3,4位相加)
//		String flagfour = prpDcompany.getCenterFlag();		
//		String flagthree = prpDcompany.getBranchType();
//		String flagone = flag1;
//		String flagtwo = flag2;
//		prpDcompany.setFlag(flagone+flagtwo+flagthree+flagfour);
//		prpDcompanyService.updatePrpDcompany(prpDcompany,userCode);
//		setEditType("view");
//		return SUCCESS;
//	}
	
	// 删除的是prpDcompanyGrade的级别关系
	public void deletePrpDcompany() {
		if (isBottomNode(prpDcompany.getComCode())
				&& prpDcompanyService.isHadUser(prpDcompany.getComCode())) {
			prpDcompanyService.deletePrpDcompany(prpDcompany.getComCode());
			renderText("success");
		}
		renderText("error");
	}

	/**
	 * (新代码)点击申请修改时，把PrpDcompany表数据添加到PrpDcompanyTrace表中
	 *  by wanghaibo
	 */
	public String updatePrpDcompany() {
		HttpServletRequest request = getRequest();
		// prpDcompany.setUpperComCode(uplevel);
		PrpDcompanyTrace prpDcompanyTrace = new PrpDcompanyTrace();
		String userCode = getSession().getAttribute("UserCode").toString();
		// 使用的是cn.com.sinosoft.inf.dict.util下的BeanUtilsEx.copyProperties(dest, orig);
		//以下为要更改的内容,2010-07-16 wanghaibo 针对于修改过的标志flag(1,2,3,4位相加)	
		String flagfour = prpDcompany.getCenterFlag();		
		String flagthree = prpDcompany.getBranchType();
		String flagone=flag1;
		String flagtwo = flag2;
		if(flagtwo == null || flagtwo.equals("")){
			flagtwo=" ";
		}
		prpDcompany.setFlag(flagone+flagtwo+flagthree+flagfour);
		BeanUtilsEx.copyProperties(prpDcompanyTrace, prpDcompany);
		int serialNo = prpDcompanyTraceService.findByMaxId("PrpDcompanyTrace",
				"serialNo");
		prpDcompanyTrace.setSerialNo(serialNo);
		prpDcompanyTrace.setApplicantMen(userCode);
		prpDcompanyTrace.setRemark(getRemark());
		prpDcompanyTrace.setApplicantDesc(getApplicantDesc());
		prpDcompanyTrace.setApplicantType("2");//2为申请修改
		prpDcompanyTrace.setApplicantDate(new Date());
		String flagEdit = request.getParameter("flagEdit");
		if(flagEdit.equals("1")){
			prpDcompanyTrace.setCurrentStatus("0");//0为初始化
		}
		else{
			prpDcompanyTrace.setCurrentStatus("1");//1为待审核
		}
		
		setEditType("view");
		prpDcompanyTraceService.insertPrpDcompanyTrace(prpDcompanyTrace);
		return SUCCESS;
	}

	public String queryPrpDcompany() {
		try {
			Page page = prpDcompanyService.getPrpDcompanyList(prpDcompany,
					this.pageNo, this.pageSize);
			this.writeJSONData(page, "comCode", "comCName", "comEName",
					"addressCName", "validStatus");
		} catch (Exception e) {
			e.printStackTrace();
			this.writeJSONMsg(e.getMessage());
		}
		return null;
	}

	// 生成公司级别表数据
	public void generatedPrpDcompanyGrade() {

		try {
			// if(prpDcompany==null){
			// prpDcompany = new PrpDcompany();
			// }
			// prpDcompanyGradeService.clearPrpDcompanyGrade();
			// prpDcompany =
			// prpDcompanyService.getPrpDcompany("00000000");//获得总公司信息
			// if(prpDcompany!=null){
			prpDcompanyService.generatePrpDcompanyGrade(prpDcompany);
			// }
			renderText("success");
		} catch (Exception e) {
			renderText("error");
			e.printStackTrace();
		}
	}

	/**
	 * 检查是否是叶子节点。 如果是叶子节点返回true 否则返回false 如果 所有下级机构有效状态为0则为叶子节点
	 */
	private boolean isBottomNode(String comCode) {
		List list = prpDcompanyService.getSubCode(comCode);
		if (list.isEmpty()) {
			return true;
		} else {
			boolean flag = true;
			for (int i = 0; i < list.size(); i++) {
				PrpDcompany company = (PrpDcompany) list.get(i);
				if ("1".equals(company.getValidStatus())) {
					flag = false;
				}
			}
			return flag;
		}
	}

	public String preComCodeBySys() throws Exception {
		HttpServletRequest pRequest = getRequest();
		String comCode = getSession().getAttribute("ComCode").toString();
		String comCName = getSession().getAttribute("ComCName").toString();
		WebTreeDynamicNode rootNode = new WebTreeDynamicNode(""+comCName+"", "org"
				+ ""+comCode+"");//原代码中comCName为"总公司",comCode为00000000，更改为动态获得机构名，机构代码 by wanghaibo 2010-11-17
		String rootUrl = "ims/dictionary/prepareQueryprpDcompany.do?currentCode="+comCode+"";
		rootNode.setAction("javascript:doAction('" + rootUrl + "')");// 设置根节点的url
		rootNode.setSubTreeURL(RequestUtil.getUrl(
				"/dictionary/loadSubOrgs.do?parentID="+comCode+"", pRequest));
		DefaultTreeModel treeModel = new DefaultTreeModel();
		treeModel.addRootNode(rootNode);
		TreeDirector director = new DefaultTreeDirector();
		director.setComparator(new DefaultNodeComparator());
		WebTreeBuilder treeBuilder = new XLoadTreeBuilder();
		treeBuilder.init(pRequest);  
		director.build(treeModel, treeBuilder);
		String treeScript = treeBuilder.getTreeScript();
		pRequest.setAttribute("treeScript", treeScript);
		return SUCCESS;
	}

	public void loadSubOrgs() throws Exception {
		HttpServletRequest pRequest = getRequest();
		HttpServletResponse pResponse = getResponse();
		pResponse.setHeader("Pragma", "No-cache");
		pResponse.setHeader("Cache-Control", "no-cache");
		pResponse.setDateHeader("Expires", 0);
		final String parentID = pRequest.getParameter("parentID");
		final String userCode = getSession().getAttribute("UserCode").toString();
		// 从数据库中查出来的子节点 新增usercode参数，原来只有parentID参数 by wanghaibo 2010-11-17
		// 修改目的：原来的机构树中，只有根节点00000000，且显示全国数据，更改为动态或得根节点；获取数据权限，显示登陆人可以操作的所有机构
		List subOrgs = prpDcompanyService.getSubSystemListByParentId(parentID,userCode);
		// System.out.println(subOrgs.size());
		UserDataUncoder orgUncoder = new UserDataUncoder() {
			public Object getID(Object pUserData) throws UncodeException {
				UtiIUserVO org = (UtiIUserVO) pUserData;
				return org.getComCode(); 
			}

			public Object getParentID(Object pUserData) throws UncodeException {
				UtiIUserVO org = (UtiIUserVO) pUserData;
				return org.getUpperComCode();
			}
		};
		AbstractWebTreeModelCreator treeModelCreator = new AbstractWebTreeModelCreator() {
			protected Node createNode(Object pUserData, UserDataUncoder pUncoder){
				UtiIUserVO org = (UtiIUserVO) pUserData;
				WebTreeDynamicNode result = new WebTreeDynamicNode(org
						.getComCName(), "org" + org.getComCode());
				result 
						.setSubTreeURL(getUrl("/dictionary/loadSubOrgs.do?parentID="
								+ org.getComCode()));
				String url = "ims/dictionary/prepareQueryprpDcompany.do?currentCode="
						+ org.getComCode();
				result.setAction("javascript:doAction('" + url + "')");
				result.setValue(org.getComCName());
				result.setIcon(this
						.getUrl("/e3/tree/xtree/images/drop-add.gif"));
				result.setOpenIcon(this
						.getUrl("/e3/tree/xtree/images/drop-yes.gif"));
//				List list = prpDcompanyService.getSubSystemListByParentId(org
//						.getComCode(),userCode);
//				if (list.size() == 0) {
//					result.setIcon(this
//							.getUrl("/e3/tree/xtree/images/drop-yes.gif"));
//				}
				return result;
			}
		};
		treeModelCreator.init(pRequest);
		TreeModel treeModel = treeModelCreator.create(subOrgs, orgUncoder);
		TreeDirector director = new DefaultTreeDirector();
		director.setComparator(new DefaultNodeComparator());
		WebTreeBuilder treeBuilder = new XLoadSubTreeBuilder();
		treeBuilder.init(pRequest);
		director.build(treeModel, treeBuilder);
		String treeScript = treeBuilder.getTreeScript();
		pResponse.setBufferSize(1024 * 10);
		pResponse.setContentType("text/xml;charset=utf-8");
		pResponse.getWriter().write(treeScript);
		pResponse.flushBuffer();

	}

	// ----------------------------e3-tree----------------------------------------

	// ----------------------------新功能代码 start
	// wanghaibo-------------------------------------------
	/**
	 * (新代码)点击申请增加时，数据添加到PrpDcompanyTrace表中 审核状态（0/1） 0：初始化 1：待审核
	 */
	public String insertPrpDcompanyTrace() {
		HttpServletRequest request = getRequest();
		String userCode = getSession().getAttribute("UserCode").toString();
		String flagf = request.getParameter("flagf");
		//以下为新增加的内容,2010-07-16 wanghaibo 针对于标志为flag(1,2,3,4位相加)
		String flagfour = prpDcompanyTrace.getCenterFlag();		
		String flagthree = prpDcompanyTrace.getBranchType();
		String flagone = flag1;
		String flagtwo = flag2;
		if(flagtwo == null || flagtwo.equals("")){
			flagtwo=" ";
		}
		prpDcompanyTrace.setFlag(flagone+flagtwo+flagthree+flagfour);
		prpDcompanyTrace.setApplicantMen(userCode);
		prpDcompanyTrace.setApplicantDesc(getApplicantDesc());
		prpDcompanyTrace.setApplicantDate(new Date());
		prpDcompanyTrace.setApplicantType("1");//1为申请新增
		if (flagf.equals("1")) {
			prpDcompanyTrace.setCurrentStatus("0");//CurrentStatus为0是初始化
		} else {
			prpDcompanyTrace.setCurrentStatus("1");//CurrentStatus为1是待审核
		}
		prpDcompanyTraceService.insertPrpDcompanyTrace(prpDcompanyTrace);
		return SUCCESS;
	}

	/**
	 * (新代码)准备查询prpDcompanyTrace表中所有未审核的数据
	 */
	public String prepareQueryPrpDcompanyAudit() {
		return SUCCESS;
	}

	/**
	 * (新代码)显示prpDcompanyTrace表中所有未审核的数据
	 */
	public String queryPrpDcompanyTraceNotAudit() {
		try {
			Page page = prpDcompanyTraceService
					.getPrpDcompanyTraceNotAuditList(prpDcompanyTrace,
							this.pageNo, this.pageSize);
			logger.debug("【查询结果数：" + page.getTotalCount() + "】");
			this.writeJSONData(page, "serialNo", "comCode", "comCName",
					"addressCName", "applicantMen", "currentStatus",
					"applicantDate", "applicantType", "updateDate",
					"applicantDesc");
			logger.debug("【writeJSONData over】");
		} catch (Exception e) {
			e.printStackTrace();
			this.writeJSONMsg(e.getMessage());
		}
		return null;
	}

	/**
	 * (新代码)准备查询prpDcompanyTrace表的所有数据
	 */
	public String prepareQueryPrpDcompanyTrace() {
		return SUCCESS;
	}

	/**
	 * (新代码)显示prpDcompanyTrace表的所有数据
	 */
	public String queryPrpDcompanyTrace() {
		try {
			Page page = prpDcompanyTraceService.getPrpDcompanyTraceList(
					prpDcompanyTrace, this.pageNo, this.pageSize);
			logger.debug("【查询结果数：" + page.getTotalCount() + "】");
			this.writeJSONData(page, "serialNo", "comCode", "comCName",
					"addressCName", "applicantMen", "currentStatus",
					"applicantDate", "applicantType", "updateDate",
					"applicantDesc", "auditSuggest","approvalMen");
			logger.debug("【writeJSONData over】");
		} catch (Exception e) {
			e.printStackTrace();
			this.writeJSONMsg(e.getMessage());
		}
		return null;
	}

	/**
	 * (新代码)根据主键序号获得PrpDcompanyTrace对象
	 */
	public String prepareUpdatePrpDcompanyTrace() {
		String serialNo = getRequest().getParameter("serialNo");
		prpDcompanyTrace = prpDcompanyTraceService.findByPrimaryKey(new Integer(serialNo));
		String flag = prpDcompanyTrace.getFlag(); //获取prpDcompanyTrace的flag字段，然后set到flag1和flag2中
		if(flag != null){
			setFlag1(flag.substring(0,1));
			setFlag2(flag.substring(1,2));
			prpDcompanyTrace.setBranchType(flag.substring(2,3));
			prpDcompanyTrace.setCenterFlag(flag.substring(3,4));
		}
		return SUCCESS;
	}

	/**
	 * (新代码)对新申请(审核状态为0）的进行修改(由初始化状态(0)或者未通过审核状态（9）更改为待审核状态(1))
	 */
	public String updatePrpDcompanyTrace() {
		HttpServletRequest request = getRequest();
		String flagf = request.getParameter("flagf");
		prpDcompanyTrace.setUpdateDate(new Date());
		//以下为新修改的内容,针对于标志为flag(1,2,3,4位相加)
		String flagfour = prpDcompanyTrace.getCenterFlag();	
		String flagthree = prpDcompanyTrace.getBranchType();
		String flagone =flag1;	
		String flagtwo = flag2;
		prpDcompanyTrace.setFlag(flagone+flagtwo+flagthree+flagfour);
		if (flagf.equals("1") && (prpDcompanyTrace.getCurrentStatus().equals("0")||prpDcompanyTrace.getCurrentStatus().equals("9"))) {// 9为未通过审核
			prpDcompanyTrace.setCurrentStatus("1");//1为待审核
		}
		prpDcompanyTraceService.updatePrpDcompanyTrace(prpDcompanyTrace);
		return SUCCESS;
	}

	/**
	 * （新代码）审核（跳转到审核页面）
	 */
	public String prepareAudit() {
		String serialNo = getRequest().getParameter("serialNo");
		prpDcompanyTrace = prpDcompanyTraceService
				.findByPrimaryKey(new Integer(serialNo));
		setAuditSuggest(prpDcompanyTrace.getAuditSuggest());
		String flag = prpDcompanyTrace.getFlag(); //获取prpDcompanyTrace的flag字段，然后set到flag1和flag2中
		if(flag != null){
			setFlag1(flag.substring(0,1));
			setFlag2(flag.substring(1,2));
			prpDcompanyTrace.setBranchType(flag.substring(2,3));
			prpDcompanyTrace.setCenterFlag(flag.substring(3,4));
		}	
		return SUCCESS;
	}

	/**
	 * （新代码）审核(audit)所有申请，根据申请类型进行判断,执行具体操作
	 */
	public String auditApply() {
		HttpServletRequest request = getRequest();
		PrpDcompany prpDcompany = new PrpDcompany();
		String flagf = request.getParameter("flagf");
		String userCode = getSession().getAttribute("UserCode").toString();
		int  serialNo = prpDcompanyTrace.getSerialNo();
		prpDcompanyTrace = prpDcompanyTraceService.findByPrimaryKey(serialNo);
		CrossOrgSender sender = new CrossOrgSender();
		if (flagf.equals("1")) {
			if (prpDcompanyTrace.getApplicantType().equals("1")) {//1为新增
				BeanUtilsEx.copyProperties(prpDcompany, prpDcompanyTrace);
				prpDcompanyService.insertPrpDcompany(prpDcompany,userCode);
				prpDcompanyTrace.setCurrentStatus("8");//8 为审核通过
				prpDcompanyTrace.setApprovalMen(userCode);
				prpDcompanyTrace.setAuditSuggest(getAuditSuggest());
				prpDcompanyTraceService.updatePrpDcompanyTrace(prpDcompanyTrace);
				String CrossSendFlag = ReadProperties.getString("CrossSendFlag");
				if(CrossSendFlag.equals("1")){
					sender.sender("I", prpDcompany);	//交叉销售：报送集团交叉销售系统
				}			
				return SUCCESS;
			} else if (prpDcompanyTrace.getApplicantType().equals("2")) {//2为修改
				// 使用的是cn.com.sinosoft.inf.dict.util下的BeanUtilsEx.copyProperties(dest, orig);
				BeanUtilsEx.copyProperties(prpDcompany, prpDcompanyTrace);
				prpDcompanyService.updatePrpDcompany(prpDcompany, userCode);
				prpDcompanyTrace.setCurrentStatus("8");//8为审核通过
				prpDcompanyTrace.setApprovalMen(userCode);
				prpDcompanyTrace.setAuditSuggest(getAuditSuggest());
				prpDcompanyTraceService.updatePrpDcompanyTrace(prpDcompanyTrace);
				String CrossSendFlag = ReadProperties.getString("CrossSendFlag");
				if(CrossSendFlag.equals("1")){
					sender.sender("U", prpDcompany);	//交叉销售：报送集团交叉销售系统
				}
				return SUCCESS;
			} 
			  else if(prpDcompanyTrace.getApplicantType().equals("3")){//3为注销/启用
				// 使用的是cn.com.sinosoft.inf.dict.util下的BeanUtilsEx.copyProperties(dest, orig);
				 BeanUtilsEx.copyProperties(prpDcompany, prpDcompanyTrace);
					String validStatus = prpDcompany.getValidStatus();
					if ("1".equals(validStatus)) {
						if (isBottomNode(prpDcompany.getComCode()) && prpDcompanyService.isHadUser(prpDcompany.getComCode())) {
							prpDcompany.setValidStatus("0");
							prpDcompanyService.updatePrpDcompany(prpDcompany, userCode);
							prpDcompanyTrace.setCurrentStatus("8");//8为审核通过
							prpDcompanyTrace.setApprovalMen(userCode);
							prpDcompanyTrace.setAuditSuggest(getAuditSuggest());
							prpDcompanyTraceService.updatePrpDcompanyTrace(prpDcompanyTrace);
							renderText("success");
						} else {
							prpDcompanyTrace.setCurrentStatus("9");//9为未通过审核
							prpDcompanyTrace.setApprovalMen(userCode);
							prpDcompanyTrace.setAuditSuggest(getAuditSuggest());
							prpDcompanyTraceService
									.updatePrpDcompanyTrace(prpDcompanyTrace);
							renderText("error");//error本机构存在下级机构，不能注销
						}
					} else {
						if (upperComValid(prpDcompany)) {
							prpDcompany.setValidStatus("1");
							prpDcompanyService.updatePrpDcompany(prpDcompany, userCode);
							prpDcompanyTrace.setCurrentStatus("8");//8为审核通过
							prpDcompanyTrace.setApprovalMen(userCode);
							prpDcompanyTrace.setAuditSuggest(getAuditSuggest());
							prpDcompanyTraceService
									.updatePrpDcompanyTrace(prpDcompanyTrace);
							renderText("success");
						} else {
							prpDcompanyTrace.setCurrentStatus("9");//9为未通过审核
							prpDcompanyTrace.setApprovalMen(userCode);
							prpDcompanyTrace.setAuditSuggest(getAuditSuggest());
							prpDcompanyTraceService
									.updatePrpDcompanyTrace(prpDcompanyTrace);
							renderText("error1");//error1本机构上级机构已注销，不能启用
						}
					}			  
			  }
		} else {		//MODIFY WULEI 20101130 没有大括号，逻辑有问题。
			prpDcompanyTrace.setCurrentStatus("9");//9为未通过审核
			prpDcompanyTrace.setAuditSuggest(getAuditSuggest());
			prpDcompanyTraceService.updatePrpDcompanyTrace(prpDcompanyTrace);
		}
		return SUCCESS;
	}
	// ----------------------------新功能代码 end
	// wanghaibo-------------------------------------------

}
