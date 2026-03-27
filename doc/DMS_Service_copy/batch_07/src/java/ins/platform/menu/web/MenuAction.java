package ins.platform.menu.web;

import ins.framework.web.Struts2Action;
import ins.platform.menu.model.SmcMenu;
import ins.platform.menu.service.facade.MenuService;

import java.io.IOException;
import java.util.HashMap;
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
import net.jcreate.e3.tree.xtree.RadioXLoadTreeBuilder;
import net.jcreate.e3.tree.xtree.XLoadSubTreeBuilder;
import cn.com.sinosoft.ims.svr.model.UtiISvr;
import cn.com.sinosoft.ims.svr.service.facade.UtiISvrService;
import cn.com.sinosoft.saa.vo.SaaTaskVO;

public class MenuAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	private SmcMenu smcMenu;

	private String companyCode;

	private String userCode;
	private UtiISvr utiISvr;
	private String editType;
	private String gradeCodes;

	private String systemCode;

	private String systemName;

	private String menuStyle;

	private String language = "C";

	private Integer menuId;

	private String menuContent;

	private int powerType;

	private String gradesIdString;

	private List smcMenuList;
	private String svrCode;
	private String taskCode;
	private Map<Integer, String> svrMap = new HashMap<Integer, String>();

	public Map<Integer, String> getSvrMap() {
		return svrMap;
	}

	public void setSvrMap(Map<Integer, String> svrMap) {
		this.svrMap = svrMap;
	}

	private transient MenuService menuService;
	private UtiISvrService utiISvrService;

	public UtiISvrService getUtiISvrService() {
		return utiISvrService;
	}

	public void setUtiISvrService(UtiISvrService utiISvrService) {
		this.utiISvrService = utiISvrService;
	}

	public String showMenu() {
		logger.debug("--------------:11111111111111");
		// menuId:
		//companyCode:
		//userCode:
		//gradeCodes:
		//systemCode:
		//languange:
		//powerType:
		//gradesIdString:
		menuContent = menuService.showMenu(menuId, companyCode, userCode,
				gradeCodes, systemCode, menuStyle, language, powerType,
				gradesIdString);
		logger.debug("--------------:" + menuContent);
		return SUCCESS;
	}

	// liyu 寰楀埌褰撳墠鏈嶅姟浠ｇ爜涓嬬殑鑿滃崟
	public String getMenuList() {
		smcMenuList = menuService.findMenuVOList(smcMenu.getUtiISvr()
				.getSvrCode());
		return SUCCESS;
	}

	// liyu 鍑嗗杩涘叆宸﹀彸FRAME
	public String prepareFrame() {
		return SUCCESS;
	}

	// liyu閫夋嫨鍩烘湰鐨勬湇鍔′唬鐮�
	public String selectSvr() {
		String userCode = (String)getSession().getAttribute("UserCode");
		svrMap = utiISvrService.getSvrList(userCode);

		return SUCCESS;
	}

	// liyu 鍚戣彍鍗曡〃涓彃鍏ユ暟鎹�
	public String insertMenu() {
		String userCodeOper = (String) getSession().getAttribute("UserCode");
		String taskCodes = getRequest().getParameter("taskCodes");
		System.out.println("**********************" + taskCodes);
		smcMenu.setTaskCode(taskCodes);
		System.out.println("**********************" + smcMenu.getTaskCode());
		menuService.insertMenu(smcMenu, userCodeOper);
		return SUCCESS;
	}

	// liyu 鏇存柊鑿滃崟鏁版嵁
	public String updateMenu() {
		String userCodeOper = (String) getSession().getAttribute("UserCode");
		String taskCodes = getRequest().getParameter("taskCodes");
		System.out.println("**********************" + taskCodes);
		smcMenu.setTaskCode(taskCodes);
		System.out.println("**********************" + smcMenu.getTaskCode());
		menuService.updateMenu(smcMenu, userCodeOper);
		return SUCCESS;
	}

	// liyu 鏌ョ湅鑿滃崟
	public String viewMenu() {
		this.smcMenu = menuService.queryMenu(menuId);

		return SUCCESS;

	}

	public String delMenu() {
		menuService.delMenu(menuId,svrCode);
		return SUCCESS;
	}

	public List getSmcMenuList() {
		return smcMenuList;
	}

	public void setSmcMenuList(List smcMenuList) {
		this.smcMenuList = smcMenuList;
	}

	public int getPowerType() {
		return powerType;
	}

	public void setPowerType(int powerType) {
		this.powerType = powerType;
	}

	public String getGradesIdString() {
		return gradesIdString;
	}

	public void setGradesIdString(String gradesIdString) {
		this.gradesIdString = gradesIdString;
	}

	public SmcMenu getSmcMenu() {
		return smcMenu;
	}

	public void setSmcMenu(SmcMenu smcMenu) {
		this.smcMenu = smcMenu;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public void setGradeCodes(String gradeCodes) {
		this.gradeCodes = gradeCodes;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setMenuStyle(String menuStyle) {
		this.menuStyle = menuStyle;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public String getMenuContent() {
		return menuContent;
	}

	public void setMenuContent(String menuContent) {
		this.menuContent = menuContent;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public String getGradeCodes() {
		return gradeCodes;
	}

	public String getLanguage() {
		return language;
	}

	public String getMenuStyle() {
		return menuStyle;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public String getSvrCode() {
		return svrCode;
	}

	public void setSvrCode(String svrCode) {
		this.svrCode = svrCode;
	}

	public UtiISvr getUtiISvr() {
		return utiISvr;
	}

	public void setUtiISvr(UtiISvr utiISvr) {
		this.utiISvr = utiISvr;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

}
