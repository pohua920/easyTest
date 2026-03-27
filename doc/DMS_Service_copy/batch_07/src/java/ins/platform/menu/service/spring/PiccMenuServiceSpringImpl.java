package ins.platform.menu.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.power.PowerService;
import ins.framework.utils.StringUtils;
import ins.platform.menu.model.SmcMenu;
import ins.platform.menu.service.facade.PiccMenuService;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.log4j.Logger;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 * PICC样式的菜单生成器
 */
public class PiccMenuServiceSpringImpl extends
		GenericDaoHibernate<SmcMenu, Integer> implements PiccMenuService {

	protected final Logger logger = Logger
    .getLogger(PiccMenuServiceSpringImpl.class);

	private static final int DEFAULT_SIZE = 50000;

	protected PowerService powerService;

	protected static Map<String, String> imageMap = new TreeMap<String, String>();

	/**
	 * 执行生成PICC样式的树(用于显示） *
	 * 
	 * @param upperIdId
	 *            上级菜单ID 为0时表示顶级菜单
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
	public String showMenu(Integer upperId, String comCode, String userCode,
			String gradeCodes, String systemCode, String language) {
		StringBuilder builder = new StringBuilder(DEFAULT_SIZE);// 存放生成的树的内容
		processShowMenu(builder, comCode, userCode, gradeCodes, systemCode,
				language);
		return builder.toString();
	}

	/**
	 * 处理左侧菜单和右侧第一级菜单，内部包含其子菜单的处理
	 * 
	 * @param upperId
	 *            上级菜单ID 为0时表示顶级菜单
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
	 */
	protected void processShowMenu(StringBuilder builder, String comCode,
			String userCode, String gradeCodes, String systemCode,
			String language) {
		StringBuilder divBuilder = new StringBuilder(DEFAULT_SIZE);
		StringBuilder jsBuilder = new StringBuilder(DEFAULT_SIZE);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("systemCode", systemCode);
		// 只显示有效状态为1的菜单
		queryRule.addEqual("validInd", "1");
		queryRule.addAscOrder("upperId");
		queryRule.addAscOrder("displayNo");

		List<SmcMenu> menus = super.find(queryRule);
		divBuilder.append("<div class=\"column\">");

		builder.append("<div class=\"menu_d1\">");
		builder.append("  <ul>");
		for (int i = 0; i < menus.size(); i++) {
			SmcMenu menu = menus.get(i);
			Integer menuId = menu.getId();
			Integer upperId = menu.getUpperId();
			// menuId=upperId表示顶级菜单
			if (!menuId.equals(upperId)) {
				continue;
			}
			menus.remove(menu);
			i--;
			if (!hasExecutePermission(menu, comCode, userCode, gradeCodes,
					systemCode)) {
				continue;
			}
			String displayName = getDisplayName(menu, language);
			// 处理li
			int length = displayName.length();
			if (length < 4) {
				length = 4;
			}
			builder.append("<li class=\"m1_").append(length).append(
					"\" onClick=\"return clickreturnvalue()\"").append(
					" onMouseover=\"dropdownmenu(this, event, 'menumain")
					.append(menuId).append("')\">");
			builder.append("<a>").append(displayName).append("</a>");
			builder.append("</li>");

			// 处理div
			divBuilder.append("<div id=\"menumain").append(menuId).append(
					"\" class=\"menustyle\">");
			divBuilder.append("  <div class=\"tree_off F_000\"").append(
					" id=\"treediv").append(menuId).append("\">");
			divBuilder.append("  </div>");
			divBuilder
					.append("<iframe src=\"about:blank\" scrolling=\"no\" frameborder=\"0\" style=\"z-index:-1;position:absolute;top:0px;left:0px;width:160px;height:100%;\">");
			divBuilder.append("</iframe>");
			divBuilder.append("</div>");

			// 处理javascript
			jsBuilder.append("var init").append(menuId).append("=function(){");
			jsBuilder.append("var tree=new YAHOO.widget.TreeView(\"treediv")
					.append(menuId).append("\");");
			jsBuilder.append("var menu").append(menuId).append(
					"=tree.getRoot();");
			for (int j = 0; j < menus.size(); j++) {
				SmcMenu subMenu = menus.get(j);
				if (!subMenu.getUpperId().equals(menu.getId())) {
					continue;
				}
				menus.remove(subMenu);
				j--;
				if (!hasExecutePermission(subMenu, comCode, userCode,
						gradeCodes, systemCode)) {
					continue;
				}
				addMenuObject(jsBuilder, subMenu, menu, language);
				processShowSubMenu(jsBuilder, menus, subMenu, comCode,
						userCode, gradeCodes, systemCode, language);
			}
			jsBuilder.append("tree.draw();");
			jsBuilder.append("};");
			jsBuilder.append("YAHOO.util.Event.on(window, \"load\", init")
					.append(menuId).append(");");

		}
		builder.append("</ul>").append("</div>");
		divBuilder.append("</div>");
		divBuilder.append("<script language='javascript'>").append("\r\n");
		divBuilder.append(jsBuilder);
		divBuilder.append("</script>");
		builder.insert(0, divBuilder);
	}

	protected void processShowSubMenu(StringBuilder builder,
			List<SmcMenu> menus, SmcMenu menu, String comCode, String userCode,
			String gradeCodes, String systemCode, String language) {

		for (int j = 0; j < menus.size(); j++) {
			SmcMenu subMenu = menus.get(j);
			if (!subMenu.getUpperId().equals(menu.getId())) {
				continue;
			}
			if (!hasExecutePermission(subMenu, comCode, userCode, gradeCodes,
					systemCode)) {
				continue;
			}
			menus.remove(subMenu);
			j--;
			addMenuObject(builder, subMenu, menu, language);
			processShowSubMenu(builder, menus, subMenu, comCode, userCode,
					gradeCodes, systemCode, language);
		}

	}

	protected void addMenuObject(StringBuilder builder, SmcMenu subMenu,
			SmcMenu upperMenu, String language) {
		Integer menuId = subMenu.getId();
		String actionURL = subMenu.getActionURL();
		String target = subMenu.getTarget();
		builder.append("var menuObj").append(menuId).append(" = {label:\"")
				.append(getDisplayName(subMenu, language)).append("\"");
		if (StringUtils.isNotEmpty(actionURL)) {
			builder.append(",href:\"").append(actionURL).append("\"");
		}
		if (StringUtils.isNotEmpty(target)) {
			builder.append(",target:\"").append(target).append("\"");
		}
		builder.append("};");
		builder.append("var menu").append(menuId).append(
				" = new YAHOO.widget.TextNode(menuObj").append(menuId).append(
				", menu").append(subMenu.getUpperId()).append(", false);");

	}

	/**
	 * 用户对当前菜单是否存在操作权限(如果是用户为超级用户，则有所有执行权限）
	 * 
	 * @param smcMenu
	 * @return 存在返回true,否则返回false
	 */
	protected boolean hasExecutePermission(SmcMenu smcMenu, String comCode,
			String userCode, String gradeCodes, String systemCode) {

		// 没有配置功能代码则有权显示
		if (smcMenu.getTaskCode() == null
				|| smcMenu.getTaskCode().trim().equals("")) {
			logger.debug("No taskcode,default has execute permission.");
			return true;
		} else if (smcMenu.getTaskCode().trim().equals("0")) {
			return true;
		}
		if (powerService != null) {
			boolean result = powerService.checkPower(userCode, smcMenu
					.getTaskCode());
			if (result) {
				logger.debug("PowerService return has execute permission.");
			} else {
				logger.debug("PowerService return hasn't execute permission.");
			}
			return result;
		}
		logger.debug("No PowerService,default has execute permission.");
		return true;
	}

	protected String getDisplayName(SmcMenu smcMenu, String language) {
		String displayName = smcMenu.getMenuCName();
		// TODO:暂时只支持简体中文
		// getShowName(language, smcMenu, "menu");
		if (displayName == null) {
			displayName = "";
		}
		displayName = displayName.trim();
		return displayName;
	}

	/**
	 * 根据语种返回代码的名称。
	 * 
	 * @param language
	 *            语种，C-简体中文，T-繁体中文，E-英文。
	 * @param object
	 *            DTO对象或PO对象。
	 * @param propertyPrefix
	 *            代码名称属性的前缀。例如userCName,userTName,userEName的前缀就是user。
	 * @return 对应语种的名称属性值。如果简体中文/繁体中文名称属性值为null或""，则返回英文名称属性值。
	 */
	@SuppressWarnings("unchecked")
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
		}
		value = (value == null) ? "" : value;
		return value.toString();
	}

	public void setPowerService(PowerService powerService) {
		this.powerService = powerService;
	}

}