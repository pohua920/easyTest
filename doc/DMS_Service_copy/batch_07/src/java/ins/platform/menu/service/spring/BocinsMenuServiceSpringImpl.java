package ins.platform.menu.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.StringUtils;
import ins.platform.menu.model.SmcMenu;
import ins.platform.menu.service.facade.BocinsMenuService;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sinosoft.bpsdriver.domain.getMenuBySvr.MenuResInfo;
import com.sinosoft.bpsdriver.service.facade.SaaAPIService;
import com.sinosoft.bpsdriver.service.facade.UserMgrAPIService;
import com.sinosoft.bpsdriver.service.spring.SaaAPIServiceImpl;
import com.sinosoft.bpsdriver.service.spring.UserMgrAPIServiceImpl;

/**
 * Bocins样式的菜单生成器 ----目前这个文件没做过configMenu的调试----
 * 
 * @author FanML
 */
public class BocinsMenuServiceSpringImpl extends
		GenericDaoHibernate<SmcMenu, Integer> implements BocinsMenuService {

	private static Logger logger = Logger
			.getLogger(BocinsMenuServiceSpringImpl.class);

	public static final String SHOW_MENU = "showMenu";

	public static final String CONFIG_MENU = "configMenu";

	protected List<SmcMenu> menuList = new ArrayList();//通过接口查询出的所有级别的菜单

	protected  Map validChildMap = new HashMap();

	protected static Map powerMap = new HashMap();

	protected String riskCode;
	
	protected String classCode;

	protected boolean isShowRiskMenu; // 是否显示带险种菜单

	private String defaultCharset = "GBK"; // 默认的编码

	/**
	 * 执行生成Bocins样式的树(用于显示） *
	 * 
	 * @param upperMenuId
	 * @param comCode
	 *            机构代码
	 * @param userCode
	 *            用户代码
	 * @param gradeCodes
	 *            岗位代码列表
	 * @param systemCode
	 *            系统代码
	 * @param language
	 *            语言
	 * @return
	 */
	public String showMenu(Integer upperMenuId, String comCode,
			String userCode, String gradeCodes, String systemCode,
			String language, int powerType, String gradesIdString){
		logger.debug("Show Menu");
		this.isShowRiskMenu = false;
		validChildMap.clear();
		return execute(SHOW_MENU, upperMenuId, comCode, userCode, gradeCodes,
				systemCode, language, powerType, gradesIdString);
	}

	/**
	 * 执行生成简单样式的树(用于显示）
	 * 
	 * @param upperMenuId
	 * @param userCode
	 *            用户代码
	 * @param gradeCodes
	 *            岗位代码列表
	 * @param language
	 *            语言
	 * @return
	 */
	public String configMenu(Integer upperMenuId, String userCode,
			String systemCode, String language, int powerType,
			String gradesIdString){
		logger.debug("Config Menu");
		return execute(CONFIG_MENU, upperMenuId, "", userCode, "", systemCode,
				language, powerType, gradesIdString);
	}

	/**
	 * 执行生成简单样式的树
	 * 
	 * @param dbManager
	 * @param actionType
	 *            命令类型
	 * @param userCode
	 *            用户代码
	 * @param systemCode
	 *            系统代码
	 * @param riskCode
	 *            险种代码
	 * @return
	 */
	protected String execute(String actionType, Integer upperMenuId,
			String comCode, String userCode, String gradeCodes,
			String systemCode, String language, int powerType,
			String gradesIdString){
		StringBuffer buffer = new StringBuffer(3000);

		if (systemCode.startsWith("reins")) {
			systemCode = "reins";
		}
		if (upperMenuId == null) {
			return "";
		}
		// 如果是第一级菜单
		if (upperMenuId.intValue() == 0) {
			buffer
					.append(processMenu(actionType, comCode, userCode,
							gradeCodes, systemCode, language, powerType,
							gradesIdString));
			// 如果不是，则获取其子菜单
		} else {
			buffer.append(processSubMenu(actionType, upperMenuId, comCode,
					userCode, gradeCodes, systemCode, language, powerType,
					gradesIdString));
		}
		logger.debug("++++++菜单++++++" + buffer.toString());
		return buffer.toString();
	}

	/**
	 * 实际处理菜单的统一入口
	 * 
	 * @param dbManager
	 */
	protected StringBuffer processMenu(String actionType, String comCode,
			String userCode, String gradeCodes, String systemCode,
			String language, int powerType, String gradesIdString){
		StringBuffer buffer = new StringBuffer(3000);
		buffer.append("<html>");
		buffer.append("<head>");
		buffer.append("    <script language=JavaScript>");
		buffer.append("        function showMenu(elementId)");
		buffer.append("        {");
		buffer
				.append("          var obj = document.getElementById(elementId);");
		buffer.append("         if (obj.style.display == \"\")");
		buffer.append("         {");
		buffer.append("            obj.style.display=\"none\";");
		buffer.append("          }");
		buffer.append("          else");
		buffer.append("          {");
		buffer.append("            obj.style.display=\"\";");
		buffer.append("          }");
		buffer.append("        }");
		buffer.append("        function processMenuClick(theHREF){");
		buffer.append("        }");
		buffer.append("    </script>");
		if (actionType.equals(CONFIG_MENU)) {
			buffer
					.append("    <link href=\"/pages/style/menu.css\" rel=\"stylesheet\" type=\"text/css\">");
		} else {
			buffer
					.append("    <link href=\"/"
							+ systemCode
							+ "/pages/style/menu.css\" rel=\"stylesheet\" type=\"text/css\">");
		}
		buffer
				.append("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset="
						+ defaultCharset + "\">");
		buffer.append("</head>");
		buffer
				.append("<body leftmargin=0 topmargin=0 marginwidth=0 marginheight=0>");
		buffer.append("    <form name=\"fm\" method=\"post\">");
		// 处理第0级菜单
		buffer.append(processZeroLevelMenu(actionType, systemCode, language));
		// 处理第1级菜单，内部包含其子菜单的处理
		buffer.append(processFirstLevelMenu(actionType, comCode, userCode,
				gradeCodes, systemCode, language, powerType, gradesIdString));
		buffer.append("    </form>");
		/** 有可能不一样 */
		if (actionType.equals(CONFIG_MENU)) {
			buffer
					.append("    <script language=\"Javascript\" src=\"/common/js/Common.js\"></script>");
			buffer
					.append("    <script language=\"Javascript\" src=\"/common/js/Application.js\"></script>");
		} else {
			buffer.append("    <script language=\"Javascript\" src=\"/"
					+ systemCode + "/common/js/Common.js\"></script>");
			buffer.append("    <script language=\"Javascript\" src=\"/"
					+ systemCode + "/common/js/Application.js\"></script>");
		}
		buffer.append("</body>");
		buffer.append("</html>");
		return buffer;
	}

	protected StringBuffer processSubMenu(String actionType,
			Integer upperMenuId, String comCode, String userCode,
			String gradeCodes, String systemCode, String language,
			int powerType, String gradesIdString) {
		StringBuffer buffer = new StringBuffer(3000);
		SmcMenu smcMenu = this.get(upperMenuId);
		if (smcMenu == null) {
			return buffer;
		}
		// liyu mod 20090709 start
		StringBuffer hql = new StringBuffer(
				" select smcMenu from SmcMenu smcMenu where smcMenu.utiISvr.svrCode=? and upperId=? and menuLevel=? ");

		// QueryRule queryRule = QueryRule.getInstance();
		// queryRule.addEqual("systemCode", systemCode);
		// queryRule.addEqual("upperId", smcMenu.getId());
		// queryRule.addEqual("menuLevel", new Integer(smcMenu.getMenuLevel()
		// .intValue() + 1));
		// 
		if (actionType.equals(CONFIG_MENU) == false) {
			// queryRule.addEqual("validInd", "1");
			hql.append(" and validInd='1' ");
		}
		// queryRule.addAscOrder("displayNo");
		// queryRule.addAscOrder("createTime");
		hql.append("order by displayNo,createTime");
		// liyu mod 20090709 end
		Integer menuLevel = smcMenu.getMenuLevel().intValue() + 1;
		List<SmcMenu> menus = this.findByHql(hql.toString(), systemCode,
				smcMenu.getId(), menuLevel);

		for (Iterator iter = menus.iterator(); iter.hasNext();) {
			SmcMenu smcMenuNew = (SmcMenu) iter.next();
			buffer.append(processMenu(smcMenuNew, actionType, comCode,
					userCode, gradeCodes, systemCode, language, powerType,
					gradesIdString));
		}
		return buffer;
	}

	/**
	 * 处理第一级菜单，内部包含其子菜单的处理
	 * 
	 * @param buffer
	 * @param systemCode
	 */

	protected StringBuffer processFirstLevelMenu(String actionType,
			String comCode, String userCode, String gradeCodes,
			String systemCode, String language, int powerType,
			String gradesIdString){
		StringBuffer buffer = new StringBuffer(3000);
//		StringBuffer hql = new StringBuffer(
//				" select smcMenu from SmcMenu smcMenu where smcMenu.utiISvr.svrCode=? and menuLevel=1 ");
		// QueryRule queryRule = QueryRule.getInstance();
		// queryRule.addEqual("smcMenu.utiISvr.svrCode", systemCode);
		// queryRule.addEqual("menuLevel", Integer.valueOf("1"));

		// 
//		if (actionType.equals(CONFIG_MENU) == false) {
//			// queryRule.addEqual("validInd", "1");
//			hql.append(" and validInd='1' ");
//		}
//		hql.append(" order by upperId,displayNo");
		// queryRule.addAscOrder("upperId");
		// queryRule.addAscOrder("displayNo");
		// logger.debug("------------------" + queryRule.toString());
		// List<SmcMenu> menus = super.find(queryRule);
//		List<SmcMenu> menus = super.findByHql(hql.toString(), systemCode);
		UserMgrAPIService um = new UserMgrAPIServiceImpl();
		List<MenuResInfo> menuResInfo;
		try {
			
		/**将MenuResInfo转换为SmcMenu对象*/
//			if(menuList.size()==0){//如果menuList的有值，则说明已经将接口调用的数据加载进来不用重复调用接口，直接使用静态值
				menuList.clear();
//				menuResInfo = um.getMenuBySvr(systemCode);
				menuResInfo = um.getMenuByUser(systemCode, userCode, "1");
				for(int i =0;i<menuResInfo.size();i++){
					SmcMenu smcMenu = new SmcMenu();
					if(!"".equals(menuResInfo.get(i).getID())&&null!=menuResInfo.get(i).getID()){
						smcMenu.setId(Integer.parseInt(menuResInfo.get(i).getID()));
					}
					if(!"".equals(menuResInfo.get(i).getUPPERID())&&null!=menuResInfo.get(i).getUPPERID()){
						smcMenu.setUpperId(Integer.parseInt(menuResInfo.get(i).getUPPERID()));
					}
					if(!"".equals(menuResInfo.get(i).getMENULEVEL())&&null!=menuResInfo.get(i).getMENULEVEL()){
						smcMenu.setMenuLevel(Integer.parseInt(menuResInfo.get(i).getMENULEVEL()));
					}
					
					smcMenu.setMenuCName(menuResInfo.get(i).getMENUCNAME());
					smcMenu.setMenuTName(menuResInfo.get(i).getMENUTNAME());
					smcMenu.setMenuEName(menuResInfo.get(i).getMENUENAME());
					smcMenu.setActionURL(menuResInfo.get(i).getACTIONURL());
					smcMenu.setTarget(menuResInfo.get(i).getTARGET());
					if(!"".equals(menuResInfo.get(i).getDISPLAYNO())&&null!=menuResInfo.get(i).getDISPLAYNO()){
						smcMenu.setDisplayNo(Integer.parseInt(menuResInfo.get(i).getDISPLAYNO()));
					}
					smcMenu.setTaskCode(menuResInfo.get(i).getTASKCODE());
					menuList.add(smcMenu);
				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<SmcMenu> menus = new ArrayList();
		for(int i = 0 ;i<menuList.size() ; i++){
			if(menuList.get(i).getMenuLevel()==1){
				menus.add(menuList.get(i));
			}
		}
		// 增加统一的重新登录按钮
		String relogin = "/" + systemCode + "/logout";
		String changePass = "/" + systemCode
				+ "/common/system/ChangePassword.jsp";
		String target = "_top";
		String pageTarget = "";
		// if ("prpall".equals(systemCode)) {
		// target = "_parent";
		// }
		if ("reserve".equals(systemCode)) {
			pageTarget = "frainner";
		} else if ("claim".equals(systemCode) || "ims".equals(systemCode)
				|| "undwrt".equals(systemCode)
				|| "hrinsplatform".equals(systemCode)) {
			pageTarget = "page";
		} else if ("platform".equals(systemCode)) {
			pageTarget = "mainFrame";
		} else {
			pageTarget = "fraInterface";
		}
		if ("claim".equals(systemCode) || "undwrt".equals(systemCode)) {
			relogin = "/" + systemCode + "/common/exitframe.do";
		}
		/*******************根据客户需求，重新登录不在菜单显示**hualimin**2009-10-20 start**********
		buffer
				.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		buffer.append("  <tr>");
		buffer.append("    <td  width=\"100%\" class=menu>");
		
		buffer
				.append("      <a href=\""
						+ relogin
						+ "\" target=\""
						+ target
						+ "\" title=\"重新登录\" onclick=\"processMenuClick(this)\">重新登录</a>");
		
		buffer.append("     </td>");
		buffer.append("  </tr>");
		buffer.append("</table>");
		*******************根据客户需求，重新登录不在菜单显示**hualimin**2009-10-20 end****************/
		if (!"dms".equals(systemCode) && !"hrinsplatform".equals(systemCode)) {

			buffer
					.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			buffer.append("  <tr>");
			buffer.append("    <td  width=\"100%\" class=menu>");

			buffer
					.append("      <a href=\""
							+ changePass
							+ "\" target=\""
							+ pageTarget
							+ "\" title=\"修改密码\" onclick=\"processMenuClick(this)\">修改密码</a>");
			buffer.append("     </td>");
			buffer.append("  </tr>");
			buffer.append("</table>");
		}
		for (Iterator iter = menus.iterator(); iter.hasNext();) {
			SmcMenu ggMenu = (SmcMenu) iter.next();
			buffer
					.append(processMenu(ggMenu, actionType, comCode, userCode,
							gradeCodes, systemCode, language, powerType,
							gradesIdString));
		}

		logger.debug("--------左边菜单----------" + buffer);
		return buffer;
	}

	protected StringBuffer processMenu(SmcMenu smcMenu, String actionType,
			String comCode, String userCode, String gradeCodes,
			String systemCode, String language, int powerType,
 String gradesIdString) {
		StringBuffer buffer = new StringBuffer(3000);

		boolean hasChild = false;// 
		if (actionType.equals(CONFIG_MENU)) {
			hasChild = hasChildMenu(smcMenu, systemCode);
		} else {
			hasChild = hasValidChildMenu(smcMenu, systemCode);
		}
		buffer.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		buffer.append("  <tr>");
		if (hasChild) {
			buffer.append("    <td  width=\"100%\" class=menu onclick=\"showMenu('menu" + smcMenu.getId() + "');\">");
		} else {
			// 由于目前各系统的menu.css中没有class=menu1样式所以将menu1转成class=menu
			if ("1".equals(smcMenu.getMenuLevel().toString())) {
				buffer.append("    <td  width=\"100%\" class=menu>");
			} else {
				buffer.append("    <td  width=\"100%\" class=menu" + smcMenu.getMenuLevel() + ">");
			}
		}
		buffer.append(processConfigButton(smcMenu, actionType, language));
		/** 图片显示，bocins里在css中使用了背景图片 */
		// if (actionType.equals(CONFIG_MENU)) {
		// buffer.append("      &nbsp;<img src=/platform/"
		// + getImage(smcMenu,systemCode) + " align=\"absmiddle\" >");
		// } else {
		// buffer.append("      &nbsp;<img src=/"
		// + getImage(smcMenu,systemCode) + " align=\"absmiddle\" >");
		// }
		buffer.append(processHref(smcMenu, actionType, language));
		buffer.append("     </td>");
		buffer.append("  </tr>");
		buffer.append("</table>");
		if (hasChild) {
			buffer.append(processNextLevelMenu(smcMenu, actionType, comCode, userCode, gradeCodes, systemCode,
					language, powerType, gradesIdString));
		}
		return buffer;
	}

	/**
	 * 递归操作 生成子菜单
	 * */
	protected String processNextLevelMenu(SmcMenu upperSmcMenu,
			String actionType, String comCode, String userCode,
			String gradeCodes, String systemCode, String language,
			int powerType, String gradesIdString) {
		StringBuffer buffer = new StringBuffer(2000);
//		StringBuffer hql = new StringBuffer(
//				" select smcMenu from SmcMenu smcMenu where smcMenu.utiISvr.svrCode=? and upperId=? and menuLevel=? and validInd='1' order by upperId, displayNo");
		// QueryRule queryRule = QueryRule.getInstance();
		// queryRule.addEqual("systemCode", systemCode);
		// queryRule.addEqual("upperId", upperSmcMenu.getId());
		// queryRule.addEqual("menuLevel", new
		// Integer(upperSmcMenu.getMenuLevel()
		// .intValue() + 1));
		// queryRule.addEqual("validInd", "1");
		// queryRule.addAscOrder("upperId");
		// queryRule.addAscOrder("displayNo");
		// List<SmcMenu> smcMenuList = super.findByHql(arg0, arg1);

		int menuLevel = upperSmcMenu.getMenuLevel().intValue() + 1;
		/**直接查询数据库获得smcMenuList*/
//		List<SmcMenu> smcMenuList = this.findByHql(hql.toString(), systemCode,
//				upperSmcMenu.getId(), menuLevel);
		/**通过接口获得smcMenuList*/
		UserMgrAPIService um = new UserMgrAPIServiceImpl();
		List<SmcMenu> MenuList = menuList;
		List<SmcMenu> smcMenuList = new ArrayList();
		for(int i = 0;i<MenuList.size();i++){
			if(MenuList.get(i).getUpperId().intValue()==upperSmcMenu.getId().intValue()&&MenuList.get(i).getMenuLevel().intValue()==menuLevel){
				smcMenuList.add(MenuList.get(i));
			}
		}
		
		buffer
				.append("<table id=\"menu"
						+ upperSmcMenu.getId()
						+ "\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"display:none\">");
		for (SmcMenu smcMenu : smcMenuList) {
			smcMenu.setMenuCName(smcMenu.getMenuCName().trim());
			boolean hasChild = hasValidChildMenu(smcMenu, systemCode);
			buffer.append(" <tr>");
			if (hasChild) {
				buffer.append("    <td class=menu" + smcMenu.getMenuLevel() + " onclick=\"showMenu('menu"
						+ smcMenu.getId() + "');\">");
			} else {
				// 由于目前各系统的menu.css中没有class=menu1样式所以将menu1转成class=menu
				if ("1".equals(smcMenu.getMenuLevel().toString())) {
					buffer.append("    <td  width=\"100%\" class=menu>");
				} else {
					buffer.append("    <td  width=\"100%\" class=menu" + smcMenu.getMenuLevel() + ">");
				}
			}
			buffer.append(processConfigButton(smcMenu, actionType, language));
			// buffer.append(StringUtils.newString("&nbsp;", (smcMenu
			// .getMenuLevel() - 1) * 3));
			/** 图片显示，bocins里在css中使用了背景图片 */
			// if (actionType.equals(CONFIG_MENU)) {
			// buffer.append(" &nbsp;<img src=/platform/"
			// + getImage(smcMenu,systemCode) + " align=\"absmiddle\" >");
			//
			// } else {
			// buffer.append(" &nbsp;<img src=/" + systemCode + "/"
			// + getImage(smcMenu,systemCode) + " align=\"absmiddle\" >");
			// buffer.append(" &nbsp;<img src=/"
			// + getImage(smcMenu,systemCode) + " align=\"absmiddle\" >");
			// }
			buffer.append(processHref(smcMenu, actionType, language));
			buffer.append(" </td>");
			buffer.append(" </tr>");
			if (hasChild) {
				buffer.append(" <tr>");
				buffer.append("    <td>");
				buffer.append(processNextLevelMenu(smcMenu, actionType, comCode, userCode, gradeCodes, systemCode,
						language, powerType, gradesIdString));
				buffer.append(" </td>");
				buffer.append(" </tr>");
			}
		}
		buffer.append("</table>");
		return buffer.toString();
	}

	/**
	 * 处理第0级菜单(通常只有一项，就是配置菜单时需要提示的信息，默认为"菜单配置"）
	 * 
	 * @param buffer
	 * @param systemCode
	 */
	protected StringBuffer processZeroLevelMenu(String actionType,
			String systemCode, String language) {
		StringBuffer buffer = new StringBuffer(1000);
		if (actionType.equals(CONFIG_MENU)) {
			buffer
					.append("<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">");
			buffer.append("  <tr>");
			buffer.append("    <td height=\"25\" class=menu>");
			buffer.append("      &nbsp;<b>菜单配置(" + systemCode + ")</b>");
			buffer
					.append("      <img src='images/btnAddChildMenu.gif' name=btnAddChildMenu alt=添加子菜单 onclick=\"return postAction(fm,'processUtiMenu.do?actionType=prepareInsert&utiMenuMenuID=0','MenuTreeRight')\">");
			buffer.append("     </td>");
			buffer.append("  </tr>");
			buffer.append("</table>");
		}
		return buffer;
	}

	/**
	 * 处理配置按钮(仅适用于配置菜单时）
	 * 
	 * @param smcMenu
	 */
	protected StringBuffer processConfigButton(SmcMenu smcMenu,
			String actionType, String language) {
		StringBuffer buffer = new StringBuffer(3000);
		if (actionType.equals(CONFIG_MENU)) {

			if (language != null && language.equals("C")) {
				buffer
						.append("<img  onmouseover=\"mouseOverMenuTD()\" onmouseout=\"mouseOutMenuTD()\" ");
				buffer
						.append("src='/common/images/btnModifyMenu.gif' name=btnModifyMenu alt= onclick=\"return postAction(fm,'/platform/processGgMenu.do?actionType=prepareUpdate&checkboxSelect=0&ggMenuMenuID="
								+ smcMenu.getId() + "','MenuTreeRight')\">");
				buffer
						.append("<img  onmouseover=\"mouseOverMenuTD()\" onmouseout=\"mouseOutMenuTD()\" ");
				buffer
						.append("src='/common/images/btnDeleteMenu.gif' name=btnDeleteMenu alt=onclick=\"return postActionWithConfirm(fm,'/platform/processGgMenu.do?actionType=delete&checkboxSelect=0&ggMenuMenuID="
								+ smcMenu.getId() + "','','MenuTree')\">");
				buffer
						.append("<img  onmouseover=\"mouseOverMenuTD()\" onmouseout=\"mouseOutMenuTD()\" ");
				buffer
						.append("src='/common/images/btnViewMenu.gif' name=btnViewMenu alt= onclick=\"return postAction(fm,'/platform/processGgMenu.do?actionType=view&ggMenuMenuID="
								+ smcMenu.getId() + "','MenuTreeRight')\">");
			}
		}
		return buffer;
	}

	protected boolean hasExecutePermission(SmcMenu smcMenu, String actionType,
			String comCode, String userCode, String gradeCodes,
			String systemCode, int powerType, String gradesIdString) {
		/** taskCode为空，表示不需要权限 */
		if (smcMenu.getTaskCode() == null) {
			return true;
		}
		if (actionType.equals(CONFIG_MENU)) {
			return true;
		}
		boolean result = false;
		/**调用权限接口*/
		SaaAPIService sa = new SaaAPIServiceImpl();
		
		try {
			result = sa.checkPower("dms",userCode, smcMenu.getTaskCode(), powerType+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 处理超链接
	 * 
	 * @param smcMenu
	 */
	protected StringBuffer processHref(SmcMenu smcMenu, String actionType,
			String language) {
		StringBuffer buffer = new StringBuffer(1000);
		String displayName = getDisplayName(smcMenu, language);
		if (actionType.equals(CONFIG_MENU)) {
			buffer.append("      " + displayName);
			if (smcMenu.getActionURL() == null
					|| null == smcMenu.getActionURL().trim()) {
				buffer
						.append("<img  onmouseover=\"mouseOverMenuTD()\" onmouseout=\"mouseOutMenuTD()\" ");
				if (language != null && language.equals("C")) {
					buffer
							.append("src='/common/images/btnAddChildMenu.gif' name=btnAddChildMenu alt=onclick=\"return postAction(fm,'/platform/processGgMenu.do?actionType=prepareInsert&ggMenuMenuID="
									+ smcMenu.getId() + "','MenuTreeRight')\">");
				} else if (language != null && language.equals("C")) {
					buffer
							.append("src='/common/images/btnAddChildMenu.gif' name=btnAddChildMenu alt= onclick=\"return postAction(fm,'/platform/processGgMenu.do?actionType=prepareInsert&ggMenuMenuID="
									+ smcMenu.getId() + "','MenuTreeRight')\">");
				} else {
					buffer
							.append("src='/common/images/btnAddChildMenu.gif' name=btnAddChildMenu alt='Add Menu' onclick=\"return postAction(fm,'/platform/processGgMenu.do?actionType=prepareInsert&ggMenuMenuID="
									+ smcMenu.getId() + "','MenuTreeRight')\">");
				}
			}
		} else {
			if (smcMenu.getActionURL() == null
					|| smcMenu.getActionURL().trim().length() == 0) {
				buffer.append("      " + smcMenu.getMenuCName());
			} else {
				String url = smcMenu.getActionURL().trim();
				if (null == smcMenu.getTarget()) {
					buffer.append("      <a href=\"" + url + "\" title=\""
							+ getTitle(smcMenu, language)
							+ "\" onclick=\"processMenuClick(this)\">"
							+ smcMenu.getMenuCName() + "</a>");

				} else {
					if (smcMenu.getMenuLevel() == 1) {
						buffer.append("      <a href=\"" + url + "\" target=\""
								+ smcMenu.getTarget() + "\" title=\""
								+ getTitle(smcMenu, language)
								+ "\" onclick=\"processMenuClick(this)\">"
								+ smcMenu.getMenuCName() + "</a>");
					} else if (smcMenu.getMenuLevel() == 2) {
						buffer.append("      <a href=\"" + url + "\" target=\""
								+ smcMenu.getTarget() + "\" title=\""
								+ getTitle(smcMenu, language)
								+ "\" onclick=\"processMenuClick(this)\">"
								+ smcMenu.getMenuCName() + "</a>");
					} else {
						buffer.append("      <a href=\"" + url + "\" target=\""
								+ smcMenu.getTarget() + "\" title=\""
								+ getTitle(smcMenu, language)
								+ "\" onclick=\"processMenuClick(this)\">"
								+ smcMenu.getMenuCName() + "</a>");
					}
				}
			}
		}
		return buffer;
	}

	protected String getMenuClass(SmcMenu smcMenu) {
		String menuClass = "";
		if (smcMenu != null && smcMenu.getMenuLevel() != null) {
			menuClass = "menu" + smcMenu.getMenuLevel().intValue();
		}
		return menuClass;
	}

	/**
	 * 得到title
	 * 
	 * @param smcMenu
	 * @return
	 */
	protected String getTitle(SmcMenu smcMenu, String language) {
		SmcMenu menuTemp = null;
//		SmcMenu menuTemp = new SmcMenu();
//		menuTemp.setUpperId(smcMenu.getUpperId());
		String title = getDisplayName(smcMenu, language);
		while (true) {
			/**直接调用数据库获得菜单*/
//			menuTemp = this.get(menuTemp.getUpperId());
			/**使用接口方式获得菜单*/
			for(int i = 0 ;i<menuList.size();i++){
				if(menuList.get(i).getId().equals(smcMenu.getUpperId())){
					menuTemp = menuList.get(i);
				}
			}
			
			if (menuTemp == null) {
				break;
			}
			title = getDisplayName(menuTemp, language) + " >> " + title;
			if (menuTemp.getMenuLevel().intValue() == 1) {
				break;
			}
		}
		return title;
	}

	/**
	 * 用户在当前菜单节点下是否存在有权限的下级菜单
	 * 
	 * @param smcMenu
	 * @return 存在返回true,否则返回false
	 */
	protected boolean hasValidChildMenu(SmcMenu smcMenu, String systemCode) {
		
		Object value = validChildMap.get(smcMenu.getId());
		if (value != null) {
			if (value.equals("0")) {
				return false;
			} else {
				return true;
			}
		}
//		String hql = "select t from SmcMenu t where t.utiISvr.svrCode =? and  t.upperId =? and t.menuLevel = ? and t.validInd = '1'";
//		List<SmcMenu> smcMenuList = this.findByHql(hql, systemCode, smcMenu
//				.getId(), (new Integer(smcMenu.getMenuLevel().intValue() + 1)));
		/**通过调用接口方式实现*/
		UserMgrAPIService um = new UserMgrAPIServiceImpl();
		List<SmcMenu> MenuList = menuList;
		List smcMenuList = new ArrayList();
//		SmcMenu menu = null;
		for(int i = 0;i<MenuList.size();i++){
//			menu = MenuList.get(i);
//			System.out.println(menu);
//			if (menu.getUpperId() == smcMenu.getId()) {
//			}
			if(MenuList.get(i).getUpperId().intValue()==smcMenu.getId().intValue()&&MenuList.get(i).getMenuLevel().intValue()==(smcMenu.getMenuLevel().intValue()+1)){
				smcMenuList.add(MenuList.get(i));
			}
		}
		
		
		validChildMap.put(smcMenu.getId(), "" + smcMenuList.size());
		if (smcMenuList.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 用户在当前菜单节点下是否存在下级菜单
	 * 
	 * @param smcMenu
	 * @return 存在返回true,否则返回false
	 */
	protected boolean hasChildMenu(SmcMenu smcMenu, String systemCode) {
//		String hql = "select t from SmcMenu t where t.utiISvr.svrCode =? and  t.upperId =? and t.menuLevel = ?";
//		List<SmcMenu> smcMenuList = this.findByHql(hql, systemCode, smcMenu
//				.getId(), (new Integer(smcMenu.getMenuLevel().intValue() + 1)));
		/**使用接口方式获得菜单*/
		UserMgrAPIService um = new UserMgrAPIServiceImpl();
		List<SmcMenu> MenuList = menuList;
		List<SmcMenu> smcMenuList = new ArrayList();
		for(int i = 0 ;i<MenuList.size();i++){
			if(MenuList.get(i).getUpperId().intValue()==smcMenu.getId().intValue()&&MenuList.get(i).getMenuLevel().intValue()==(smcMenu.getMenuLevel().intValue()+1)){
				smcMenuList.add(MenuList.get(i));
			}
		}
		if (smcMenuList.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 返回当前菜单项的Image,优先取菜单本身的Image，如果没有配置则取菜单样式的Image。<br>
	 * 这里采用了缓存技术
	 * 
	 * @param smcMenu
	 * @return 当前菜单项的Image
	 */
	protected String getImage(SmcMenu smcMenu, String systemCode) {
		if (smcMenu.getImage() != null
				&& smcMenu.getImage().trim().length() > 0) {
			return smcMenu.getImage();
		}
		// String image = new
		// StringBuffer("pages/image/arrow_right.gif").toString();
		String image = new StringBuffer("claim/pages/image/arrow_right.gif")
				.toString();
		// .append(
		// smcMenu.getMenuLevel()).append(".gif").toString();
		return image;
	}

	protected String getDisplayName(SmcMenu smcMenu, String language) {
		String displayName = this.getShowName(language, smcMenu, "menu");
		if (displayName == null) {
			displayName = "";
		}
		displayName = displayName.trim();
		return displayName;
	}

	public static String getShowName(String language, Object object,
			String propertyPrefix) {
		if (language == null || language.trim().length() == 0) {
			return "";
		}
		if (object == null) {
			return "";
		}
		if (propertyPrefix == null || propertyPrefix.trim().length() == 0) {
			return "";
		}
		Object value = null;
		String methodName = null;
		Class clazz = object.getClass();
		Method method = null;
		try {
			if (language.equals("C") || language.equals("T")) {
				methodName = "get"
						+ StringUtils.upperCaseFirstChar(propertyPrefix)
						+ language + "Name";
				method = clazz.getMethod(methodName, new Class[] {});
				value = method.invoke(object, new Object[] {});
			}
			if (value == null || value.toString().trim().length() == 0) {
				methodName = "get"
						+ StringUtils.upperCaseFirstChar(propertyPrefix)
						+ "EName";
				method = clazz.getMethod(methodName, new Class[] {});
				value = method.invoke(object, new Object[] {});
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			// System.out.println(ex.toString());
		}
		value = (value == null) ? "" : value;
		return value.toString();
	}

	/**
	 * 执行生成Bocins样式的树(用于显示带险种） *
	 * 
	 * @param upperMenuId
	 * @param comCode
	 *            机构代码
	 * @param userCode
	 *            用户代码
	 * @param gradeCodes
	 *            岗位代码列表
	 * @param systemCode
	 *            系统代码
	 * @param riskCode
	 *            险种代码
	 * @param language
	 *            语言
	 * @return
	 */
	public String showMenu(Integer upperMenuId, String comCode,
			String userCode, String gradeCodes, String systemCode,
			String riskCode, String language, int powerType,
			String gradesIdString) {
		logger.debug("Show Menu");
		this.isShowRiskMenu = true;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("riskCode", riskCode);
		List classList = new ArrayList(0);
		String hql = "select classCode from SaaRisk saaRisk where saaRisk.riskCode =?";
		if (riskCode != "") {
			classList = super.findByHql(hql, riskCode);
			this.riskCode = riskCode;
			this.classCode = (String) classList.get(0);
		} else {
			this.isShowRiskMenu = false;
		}
		return execute(SHOW_MENU, upperMenuId, comCode, userCode, gradeCodes,
				systemCode, language, powerType, gradesIdString);
	}

	// public PowerService getPowerService() {
	// return powerService;
	// }
	//
	// public void setPowerService(PowerService powerService) {
	// this.powerService = powerService;
	// }

}