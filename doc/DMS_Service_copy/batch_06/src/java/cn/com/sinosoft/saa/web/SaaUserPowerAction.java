package cn.com.sinosoft.saa.web;

import ins.framework.exception.BusinessException;
import ins.framework.web.Struts2Action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import net.jcreate.e3.tree.TreeDirector;
import net.jcreate.e3.tree.support.DefaultNodeComparator;
import net.jcreate.e3.tree.support.DefaultTreeDirector;
import net.jcreate.e3.tree.support.DefaultTreeModel;
import net.jcreate.e3.tree.support.RequestUtil;
import net.jcreate.e3.tree.support.WebTreeBuilder;
import net.jcreate.e3.tree.support.WebTreeDynamicNode;
import net.jcreate.e3.tree.xtree.CheckXLoadTreeBuilder;
import cn.com.sinosoft.common.util.CodeImage;
import cn.com.sinosoft.ims.svr.service.facade.UtiISvrService;
import cn.com.sinosoft.saa.model.SaaAuthRisk;
import cn.com.sinosoft.saa.model.SaaUser;
import cn.com.sinosoft.saa.service.facade.SaaPowerHelpService;
import cn.com.sinosoft.saa.service.facade.SaaUserPowerService;
import cn.com.sinosoft.saa.service.facade.SaaUserService;
import cn.com.sinosoft.saa.vo.PrpDClassVO;
import cn.com.sinosoft.saa.vo.SaaAuthTaskVO;
import cn.com.sinosoft.saa.vo.SaaRiskObjectVO;

import com.sinosoft.bpsdriver.service.facade.SaaAPIService;
import com.sinosoft.bpsdriver.service.facade.UserMgrAPIService;
import com.sinosoft.bpsdriver.service.spring.SaaAPIServiceImpl;
import com.sinosoft.bpsdriver.service.spring.UserMgrAPIServiceImpl;

public class SaaUserPowerAction extends Struts2Action {
	private List<SaaUser> saaUserList;
	private SaaUserPowerService saaUserPowerService;
	private SaaPowerHelpService saaPowerHelpService;
	private SaaUserService saaUserService;
	private String userCodeTo;
	private String userCodeFrom;
	private String flag;
	private SaaUser saaUser;
	private String userCode;
	private String password;
	private String authComCode;
	private String authExceptComCode;
	private String authComName;
	private String authExceptComName;
	private String[] treeCheckBox;
	private String[] intranetCheckBox;
	public String[] getIntranetCheckBox() {
		return intranetCheckBox;
	}

	public void setIntranetCheckBox(String[] intranetCheckBox) {
		this.intranetCheckBox = intranetCheckBox;
	}
	private String comCodes;
	private String svrCode;
	private String loginMethod;
	private List<SaaAuthTaskVO> saaAuthTaskVOList = new ArrayList<SaaAuthTaskVO>(
			0);
	private List<SaaRiskObjectVO> saaAuthProductVOList = new ArrayList<SaaRiskObjectVO>(
			0);
	private File powerImportExcel;
	private List<SaaAuthTaskVO> systemTasks = new ArrayList<SaaAuthTaskVO>(0);

	private String rootTaskCode;
	private Map<Integer, String> comCodeMap = new HashMap<Integer, String>();
	private UtiISvrService utiISvrService;
	private String verifyCode; //add by duanfa20110824 验证码

	// public String queryUser(){
	// try {
	// Page page = saaUserPowerService.findSaaUserList(saaUser, this.pageNo,
	// this.pageSize,(String)getSession().getAttribute("UserCode"));
	// this.writeJSONData(page, "userCode", "userName",
	// "comCode","validStatus");
	// } catch (Exception e) {
	// this.writeJSONMsg(e.getMessage());
	// }
	// return null;
	// }
	public String login() throws Exception {
		
		//add by duanfa20110824 验证码校验
	    String imageCode = (String) this.getSession().getAttribute("ImageCode");
	    if(verifyCode==null||!verifyCode.equals(imageCode)){
			throw new Exception("验证码错误");
	    }
//		// String userCode=request.getParameter("userCode");
//		// String password=request.getParameter("password");
//		// int powerTpye = Integer.parseInt(IpSelectAction.getEnvironment()
//		// .getNetType());
//		// if (powerTpye>1) {
//		// throw new BusinessException("权限系统只允许内网登录维护!",false);
//		// }
//		UtiISvrService utiISvrService = (UtiISvrService) ServiceFactory
//		.getService("utiISvrService");
//		
//		UtiISvr utiISvr = utiISvrService.findSvrByCode(svrCode);
//		/**调用接口实现登陆*/
		SaaAPIService sa = new SaaAPIServiceImpl();
		UserMgrAPIService um = new UserMgrAPIServiceImpl();
//		password = new SAAFGLPasswordEncoder().encode(password.trim());	//设置加密
		if (getSession().getAttribute("edu.yale.its.tp.cas.client.filter.user") != null) {
			//单点登录状态不校验用户名和密码
		} else {
			//正常登录状态，校验用户名和密码
			sa.checkLoginByPwd(svrCode, userCode, password, loginMethod);
		}
//		if(utiISvr != null){
//			System.out.println(loginMethod);
//			Binding binding = new Binding();
//			boolean values = false;
//			String flag = "true";
//			binding.setVariable("card", true);
//			binding.setVariable("usbkey", true);
//			binding.setVariable("nameAndPwd", true);
//			binding.setVariable("and", true);
//			binding.setVariable("or", true);
//			GroovyShell shell = new GroovyShell(binding);
//			values = (Boolean)shell.evaluate(loginMethod);
//			System.out.println(values);
//			if(values){
//				if("nameAndPwd".equals(loginMethod)){
//					saaPowerService.login(svrCode, userCode, password);
//				}else if("card".equals(loginMethod)){
//					//磁卡登录
//				}else if("usbkey".equals(loginMethod)){
//					//usbkey登录
//				}
//			}else{
//				throw new BusinessException("服务(" + utiISvr.getSvrName()+ ")不支持这种方式登录！", false);
//			}
//		}else{
//			throw new BusinessException("该服务不能使用！", false);
//		}
//		
//		
//		
//		// List<String>
//		// preUserCom=(List<String>)saaPowerHelpService.getAuthPermitCom(userCode);
//		// if(userCode.equals("0000000000")){
//		// HttpSession session = getSession(true);
//		UtiIAccount utiIAccount = utiIAccountService
//				.getUtiIAccountByCode(userCode);

		String UserCode = um.getAccMsg(userCode, svrCode).getUSERCODE();
		getSession().setAttribute("UserCode", UserCode);
		/**输入的就是userCode 不用查找userCode  去掉 2009-10-29 end*/
		/***/
//		// 因为前台login页面中没有更改，因此这里的userCode其实是accCode
//		getSession().setAttribute("UserCode", userCode); //将session的名字accCode改为UserCode 修改时间2009-10-29
		return SUCCESS;
		// }
		// else
		// throw new BusinessException("该用户无权登录权限平台!", false);
	}

	public String prepareUserPowerCopy() {
//		String userCode1 = (String) getSession().getAttribute("UserCode");
//		boolean hasPower = saaPowerService.checkPower(userCode1,
//				IConstants.SAA_USERPOWER_POWERFULLCOPY, (Integer) getSession()
//						.getAttribute("PowerType"), "");
//		if (!hasPower) {
//			throw new BusinessException("您沒有人员权限复制权限！", false);
//		}
		saaUserList = saaUserPowerService.findSaaUserList((String) getSession()
				.getAttribute("UserCode"));
		return SUCCESS;
	}

	public String userPowerCopy() {
//		String userCode1 = (String) getSession().getAttribute("UserCode");
//		boolean hasPower = saaPowerService.checkPower(userCode1,
//				IConstants.SAA_USERPOWER_POWERFULLCOPY, (Integer) getSession()
//						.getAttribute("PowerType"), "");
//		if (!hasPower) {
//			throw new BusinessException("您沒有人员权限复制权限！", false);
//		}
		saaUserPowerService.copyUserPower(userCodeFrom, userCodeTo,
				(String) getSession().getAttribute("UserCode"), new Date());
		return SUCCESS;
	}

	public String prepareGrantUserPower() {
		return SUCCESS;
	}

	public String showUserInfo() {
		saaUser = saaUserService.findSaaUserByUserCode(userCode,
				(String) getSession().getAttribute("UserCode"));
		if (null == saaUser) {
			throw new BusinessException("对不起您没有员工：" + userCode + " 的管理权限！",
					false);
		}
		return SUCCESS;
	}

	public String userPowerAllConfig() {
		return SUCCESS;
	}

	public String prepareGrantUserPowerSelect() {
//		String userCode1 = (String) getSession().getAttribute("UserCode");
//		boolean hasPower = saaPowerService.checkPower(userCode1,
//				IConstants.SAA_USERPOWER_AUTHADMIN, (Integer) getSession()
//						.getAttribute("PowerType"), "");
//		if (!hasPower) {
//			throw new BusinessException("您沒有管理员授权权限！", false);
//		}
		comCodeMap = utiISvrService.getCompanyListMap();
		return SUCCESS;
	}

	public String taskPowerConfig() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
//		boolean hasPower = saaPowerService.checkPower(userCode1,
//				IConstants.SAA_USERPOWER_AUTHADMIN, (Integer) getSession()
//						.getAttribute("PowerType"), "");
//		if (!hasPower) {
//			throw new BusinessException("您沒有管理员授权权限！", false);
//		}
		// saaAuthTaskVOList =
		// saaUserPowerService.findSaaAuthTaskVOListByUserCode((String)getSession().getAttribute("UserCode"),userCode);
		systemTasks = saaUserPowerService.findRootSaaAuthTaskVOList(userCode1);
		return SUCCESS;
	}

	public String taskPowerConfigByRootCode() {
//		String userCode1 = (String) getSession().getAttribute("UserCode");
//		boolean hasPower = saaPowerService.checkPower(userCode1,
//				IConstants.SAA_USERPOWER_AUTHADMIN, (Integer) getSession()
//						.getAttribute("PowerType"), "");
//		if (!hasPower) {
//			throw new BusinessException("您沒有管理员授权权限！", false);
//		}
		saaAuthTaskVOList = saaUserPowerService
				.findSaaAuthTaskVOListByUserCodeRootTask((String) getSession()
						.getAttribute("UserCode"), userCode, rootTaskCode);
		return SUCCESS;
	}

	public String productPowerConfig() {
		List<SaaAuthRisk> saaAuthRiskList = saaUserPowerService.getSaaPermitRiskByCode(userCode);
		//授权产品
		String permitRiskCodes = "";
		SaaAuthRisk saaAuthRisk = null;
		if(saaAuthRiskList != null){
			for (int i = 0;i<saaAuthRiskList.size();i++) {
				
				saaAuthRisk = saaAuthRiskList.get(i);
				permitRiskCodes += saaAuthRisk.getRiskCode();
				permitRiskCodes += ",";
			}
		}
		
		String treeScript = this.viewUserGradePowerOnRisk(permitRiskCodes);
		getRequest().setAttribute("treeScript", treeScript);

		return SUCCESS;
	}
	//产品的e3tree
	public String viewUserGradePowerOnRisk(String permitRiskCodes) {
		String userCode1 = (String) getSession().getAttribute("UserCode");

		//创建e3Tree
		HttpServletRequest pRequest = getRequest();
			
		//创建产品的e3Tree
		WebTreeDynamicNode rootNode = new WebTreeDynamicNode("产品选择","topRisk");
		List<PrpDClassVO> saaClassList = saaUserPowerService.getSaaClassList();
		PrpDClassVO saaClass = null;
		if(saaClassList != null){
			for(int i=0; i<saaClassList.size(); i++){
				saaClass = new PrpDClassVO();
				saaClass = saaClassList.get(i);
				WebTreeDynamicNode jcrjNode = new WebTreeDynamicNode(saaClass.getClassCName(), "orgRisk" + saaClass.getClassCode());
				jcrjNode.setSubTreeURL(RequestUtil.getUrl(
						"/saaUserGrade/loadSubOrgs.do?parentIDRisk=" + saaClass.getClassCode() +"&permitRiskCodes="+permitRiskCodes, pRequest));
				if(permitRiskCodes.contains(saaClass.getClassCode())){
					jcrjNode.setSelected(true);
		        }
				jcrjNode.setValue(saaClass.getClassCode());
				rootNode.addNode(jcrjNode);
				
			}
		}
		
		DefaultTreeModel treeModel = new DefaultTreeModel();
		treeModel.addRootNode(rootNode);
		TreeDirector director = new DefaultTreeDirector();
		director.setComparator(new DefaultNodeComparator());
		WebTreeBuilder treeBuilder = new CheckXLoadTreeBuilder();
		treeBuilder.init(pRequest);
		director.build(treeModel, treeBuilder);
		String treeScript = treeBuilder.getTreeScript();
		//pRequest.setAttribute("treeScript", treeScript);
		
		return treeScript;
	}

	public String taskPowerGrant() {
		String operUserCode = (String) getSession().getAttribute("UserCode");
//		boolean hasPower = saaPowerService.checkPower(userCode1,
//				IConstants.SAA_USERPOWER_AUTHADMIN, (Integer) getSession()
//						.getAttribute("PowerType"), "");
//		if (!hasPower) {
//			throw new BusinessException("您沒有管理员授权权限！", false);
//		}
		
		saaUserPowerService.updateTaskPower(treeCheckBox, userCode, operUserCode);
		return SUCCESS;
	}
    //更新授权允许机构
	public String comPowerGrant() {
		String operUserCode = (String) getSession().getAttribute("UserCode");
//		boolean hasPower = saaPowerService.checkPower(userCode1,
//				IConstants.SAA_USERPOWER_AUTHADMIN, (Integer) getSession()
//						.getAttribute("PowerType"), "");
//		if (!hasPower) {
//			throw new BusinessException("您沒有管理员授权权限！", false);
//		}
		saaUserPowerService.updateComPower(treeCheckBox, intranetCheckBox,userCode,operUserCode);
		return SUCCESS;
	}
	//更新授权除外机构
	public String exceptComPowerGrant() {
		String operUserCode = (String) getSession().getAttribute("UserCode");
//		boolean hasPower = saaPowerService.checkPower(userCode1,
//				IConstants.SAA_USERPOWER_AUTHADMIN, (Integer) getSession()
//						.getAttribute("PowerType"), "");
//		if (!hasPower) {
//			throw new BusinessException("您沒有管理员授权权限！", false);
//		}
		saaUserPowerService.updateExceptComPower(treeCheckBox, intranetCheckBox,userCode,operUserCode);
		return SUCCESS;
	}

	public String productPowerGrant() {
		String operUserCode = (String) getSession().getAttribute("UserCode");
//		boolean hasPower = saaPowerService.checkPower(userCode1,
//				IConstants.SAA_USERPOWER_AUTHADMIN, (Integer) getSession()
//						.getAttribute("PowerType"), "");
//		if (!hasPower) {
//			throw new BusinessException("您沒有管理员授权权限！", false);
//		}
		String riskCode = getRequest().getParameter("riskCodes");
		System.out.println("------riskCode******-------"+riskCode);
		String[] riskCodes = riskCode.split(",");
		saaUserPowerService.updateProductPower(riskCodes, userCode, operUserCode);
		return SUCCESS;
	}

	public String prepareImportUserPower() {
//		String userCode1 = (String) getSession().getAttribute("UserCode");
//		boolean hasPower = saaPowerService.checkPower(userCode1,
//				IConstants.SAA_USERPOWER_POWERDATAIMP, (Integer) getSession()
//						.getAttribute("PowerType"), "");
//		if (!hasPower) {
//			throw new BusinessException("您沒有人员权限导入权限！", false);
//		}
		return SUCCESS;
	}

	public String userPowerImport() {
		try {
			saaUserPowerService.updateUserPowerByExcel(new FileInputStream(
					powerImportExcel), (String) getSession().getAttribute(
					"UserCode"));
		} catch (FileNotFoundException e) {
			throw new BusinessException("没有找到相关文件", false);
		}
		return SUCCESS;
	}

	public String prepareExportUserPower() {
//		String userCode1 = (String) getSession().getAttribute("UserCode");
//		boolean hasPower = saaPowerService.checkPower(userCode1,
//				IConstants.SAA_USERPOWER_POWERDATAEXPL, (Integer) getSession()
//						.getAttribute("PowerType"), "");
//		if (!hasPower) {
//			throw new BusinessException("您沒有人员权限导出权限！", false);
//		}
		return SUCCESS;
	}

	public String userPowerExport() {
		saaUserPowerService.exportUserPowerToExcel(comCodes);
		try {
			getResponse().sendRedirect(
					"/saa/pages/downloadFiles/UserPowers.xls");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//add by duanfa20110824 生成验证码
	public void imageValidate()	{
		Random random = new Random();
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			// 将认证码显示到图象中 
		}
		//sRand="123";//改成常量
		this.getSession().setAttribute("ImageCode", sRand);
		CodeImage.getInstanse().getImage(this.getResponse(), sRand);
	}

	public String generateRiskCodes() {
		return SUCCESS;
	}

	public List<SaaUser> getSaaUserList() {
		return saaUserList;
	}

	public void setSaaUserList(List<SaaUser> saaUserList) {
		this.saaUserList = saaUserList;
	}

	public SaaUserPowerService getSaaUserPowerService() {
		return saaUserPowerService;
	}

	public void setSaaUserPowerService(SaaUserPowerService saaUserPowerService) {
		this.saaUserPowerService = saaUserPowerService;
	}

	public String getUserCodeTo() {
		return userCodeTo;
	}

	public void setUserCodeTo(String userCodeTo) {
		this.userCodeTo = userCodeTo;
	}

	public String getUserCodeFrom() {
		return userCodeFrom;
	}

	public void setUserCodeFrom(String userCodeFrom) {
		this.userCodeFrom = userCodeFrom;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public SaaUser getSaaUser() {
		return saaUser;
	}

	public void setSaaUser(SaaUser saaUser) {
		this.saaUser = saaUser;
	}

	public SaaUserService getSaaUserService() {
		return saaUserService;
	}

	public void setSaaUserService(SaaUserService saaUserService) {
		this.saaUserService = saaUserService;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public List<SaaAuthTaskVO> getSaaAuthTaskVOList() {
		return saaAuthTaskVOList;
	}

	public void setSaaAuthTaskVOList(List<SaaAuthTaskVO> saaAuthTaskVOList) {
		this.saaAuthTaskVOList = saaAuthTaskVOList;
	}

	public List<SaaRiskObjectVO> getSaaAuthProductVOList() {
		return saaAuthProductVOList;
	}

	public void setSaaAuthProductVOList(
			List<SaaRiskObjectVO> saaAuthProductVOList) {
		this.saaAuthProductVOList = saaAuthProductVOList;
	}

	public String[] getTreeCheckBox() {
		return treeCheckBox;
	}

	public void setTreeCheckBox(String[] treeCheckBox) {
		this.treeCheckBox = treeCheckBox;
	}

	public String getAuthComCode() {
		return authComCode;
	}

	public void setAuthComCode(String authComCode) {
		this.authComCode = authComCode;
	}

	public String getAuthExceptComCode() {
		return authExceptComCode;
	}

	public void setAuthExceptComCode(String authExceptComCode) {
		this.authExceptComCode = authExceptComCode;
	}

	public String getAuthComName() {
		return authComName;
	}

	public void setAuthComName(String authComName) {
		this.authComName = authComName;
	}

	public String getAuthExceptComName() {
		return authExceptComName;
	}

	public void setAuthExceptComName(String authExceptComName) {
		this.authExceptComName = authExceptComName;
	}

	public File getPowerImportExcel() {
		return powerImportExcel;
	}

	public void setPowerImportExcel(File powerImportExcel) {
		this.powerImportExcel = powerImportExcel;
	}

	public String getComCodes() {
		return comCodes;
	}

	public void setComCodes(String comCodes) {
		this.comCodes = comCodes;
	}


	public SaaPowerHelpService getSaaPowerHelpService() {
		return saaPowerHelpService;
	}

	public void setSaaPowerHelpService(SaaPowerHelpService saaPowerHelpService) {
		this.saaPowerHelpService = saaPowerHelpService;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<SaaAuthTaskVO> getSystemTasks() {
		return systemTasks;
	}

	public void setSystemTasks(List<SaaAuthTaskVO> systemTasks) {
		this.systemTasks = systemTasks;
	}

	public String getRootTaskCode() {
		return rootTaskCode;
	}

	public void setRootTaskCode(String rootTaskCode) {
		this.rootTaskCode = rootTaskCode;
	}

	public String getSvrCode() {
		return svrCode;
	}

	public void setSvrCode(String svrCode) {
		this.svrCode = svrCode;
	}

	public String getLoginMethod() {
		return loginMethod;
	}

	public void setLoginMethod(String loginMethod) {
		this.loginMethod = loginMethod;
	}
	public Map<Integer, String> getComCodeMap() {
		return comCodeMap;
	}
	public void setComCodeMap(Map<Integer, String> comCodeMap) {
		this.comCodeMap = comCodeMap;
	}
	
	public UtiISvrService getUtiISvrService() {
		return utiISvrService;
	}
	public void setUtiISvrService(UtiISvrService utiISvrService) {
		this.utiISvrService = utiISvrService;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
}
