package ins.platform.menu.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.power.PowerService;
import ins.framework.utils.StringUtils;
import ins.platform.menu.model.SmcMenu;
import ins.platform.menu.service.facade.MinganMenuService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * ����ʽ�Ĳ˵������ ��Ҫ��Application.js��ʵ�ַ���processMenuClick
 */
public class MinganMenuServiceSpringImpl extends
		GenericDaoHibernate<SmcMenu, Integer> implements MinganMenuService {
	private static Logger logger = Logger
			.getLogger(MinganMenuServiceSpringImpl.class);

	public static final String SHOW_MENU = "showMenu";

	public static final String CONFIG_MENU = "configMenu";

	protected PowerService powerService;

	protected static Map validChildMap = new HashMap();

	protected static Map powerMap = new HashMap();

	/**
	 * ִ���������ʽ����(������ʾ��.��Ҫ��Application.js���ṩshowMenu(elementId)����
	 * 
	 * @param upperMenuId
	 *            �ϼ��˵�ID Ϊ0ʱ��ʾ�����˵�
	 * @param comCode
	 *            �����
	 * @param userCode
	 *            �û�����
	 * @param gradeCodes
	 *            ��λ�����б�
	 * @param systemCode
	 *            ϵͳ����
	 * @param language
	 *            ����
	 * @return
	 */
	public String showMenu(Integer upperMenuId, String comCode,
			String userCode, String gradeCodes, String systemCode,
			String language) {
		logger.debug("Show Menu");
		return execute(SHOW_MENU, upperMenuId, comCode, userCode, gradeCodes,
				systemCode, language);
	}

	/**
	 * ִ���������ʽ�����������ã�.��Ҫ��Application.js���ṩshowMenu(elementId)����
	 * 
	 * @param upperMenuId
	 *            �ϼ��˵�ID Ϊ0ʱ��ʾ�����˵�
	 * @param userCode
	 *            �û�����
	 * @param systemCode
	 *            ϵͳ����
	 * @param language
	 *            ����
	 * @return
	 */
	public String configMenu(Integer upperMenuId, String userCode,
			String systemCode, String language) {
		logger.debug("Config Menu");
		return execute(CONFIG_MENU, upperMenuId, "", userCode, "", systemCode,
				language);
	}

	/**
	 * ִ���������ʽ����.��Ҫ��Application.js���ṩshowMenu(elementId)����
	 * 
	 * @param actionType
	 *            ��������
	 * @param upperMenuId
	 *            �ϼ��˵�ID Ϊ0ʱ��ʾ�����˵�
	 * @param comCode
	 *            �����
	 * @param userCode
	 *            �û�����
	 * @param gradeCodes
	 *            ��λ�����б�
	 * @param systemCode
	 *            ϵͳ����
	 * @param language
	 *            ����
	 * @return
	 */
	protected String execute(String actionType, Integer upperMenuId,
			String comCode, String userCode, String gradeCodes,
			String systemCode, String language) {

		StringBuffer buffer = new StringBuffer(3000);// �����ɵ��������

		if (systemCode.startsWith("reins")) {
			systemCode = "reins";
		}
		if (upperMenuId == null) {
			return "";
		}
		if (upperMenuId.intValue() == 0) {
			buffer.append(processMenu(actionType, comCode, userCode,
					gradeCodes, systemCode, language));
		} else {
			buffer.append(processSubMenu(actionType, upperMenuId, comCode,
					userCode, gradeCodes, systemCode, language));
		}
		return buffer.toString();
	}

	/**
	 * ʵ�ʴ���˵���ͳһ���
	 * 
	 * @param actionType
	 *            ��������
	 * @param upperMenuId
	 *            �ϼ��˵�ID Ϊ0ʱ��ʾ�����˵�
	 * @param comCode
	 *            �����
	 * @param userCode
	 *            �û�����
	 * @param gradeCodes
	 *            ��λ�����б�
	 * @param systemCode
	 *            ϵͳ����
	 * @param language
	 *            ����
	 */
	protected StringBuffer processMenu(String actionType, String comCode,
			String userCode, String gradeCodes, String systemCode,
			String language) {
		StringBuffer buffer = new StringBuffer(3000);
		buffer.append("<html>");
		buffer.append("<head>");
		buffer
				.append("    <link href=\"/common/css/Standard.css\" rel=\"stylesheet\" type=\"text/css\">");
		buffer
				.append("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		buffer.append("    <script language=JavaScript>");
		buffer.append("function showMenu(elementId){");
		buffer.append("  var obj = document.getElementById(elementId);");
		buffer.append("  if (obj.style.display == \"\"){");
		buffer.append("    obj.style.display=\"none\";");
		buffer.append("  }else{");
		/**
		 * ��contentEditable���Ե�ֵΪinheritʱ,Ϊ�Ѿ�����
		 */
		buffer.append("    if('" + actionType
				+ "'=='configMenu') obj.contentEditable='inherit';");
		buffer.append("    if (obj.contentEditable == \"inherit\"){");
		buffer
				.append("      var baseURL = \"/platform/processGgMenu.do?actionType="
						+ actionType
						+ "&systemCode=application&menuStyle=mingan&menuId=\" + elementId.substring(4);");
		buffer.append("      syncRequest(baseURL);");
		buffer.append("      obj.innerHTML = xmlRequest.responseText;");
		buffer.append("      obj.contentEditable != \"inherit\";");
		buffer.append("    }");
		buffer.append("    obj.style.display=\"\";");
		buffer.append("  }");
		buffer.append("} ");
		buffer.append("function mouseOverMenuTD(){");
		buffer.append("  var evt = (evt) ? evt : window.event;");
		buffer.append("  var selectRowObject = evt.srcElement;");
		buffer.append("  while (selectRowObject.tagName != \"TD\") {");
		buffer
				.append("        selectRowObject = selectRowObject.parentElement;");
		buffer.append("  }");
		buffer.append("  var selectRowClassName=selectRowObject.className;");
		buffer.append("  if(selectRowClassName.lastIndexOf(\"Select\")==-1){");
		buffer
				.append("    selectRowObject.className=selectRowClassName+\"Select\";");
		buffer.append("  }");
		buffer.append("}");
		buffer.append("function mouseOutMenuTD(){");
		buffer.append("    var evt = (evt) ? evt : window.event;");
		buffer.append("   var selectRowObject = evt.srcElement;");
		buffer.append("   while (selectRowObject.tagName != \"TD\") {");
		buffer
				.append("        selectRowObject = selectRowObject.parentElement;");
		buffer.append("    }");
		buffer.append("   var selectRowClassName=selectRowObject.className;");
		buffer.append("   if(selectRowClassName.lastIndexOf(\"Select\")!=-1){");
		buffer
				.append("       selectRowObject.className=selectRowClassName.substring(0,selectRowClassName.lastIndexOf(\"Select\"));");
		buffer.append("   }");
		buffer.append("}");
		buffer.append("function processMenuClick(theHREF){");
		buffer.append("}");
		buffer.append("</script>");
		buffer.append("</head>");
		buffer
				.append("<body bgcolor=\"f7ffef\" leftmargin=0 topmargin=0 marginwidth=0 marginheight=0>");
		buffer.append("    <form name=\"fm\" method=\"post\">");
		// �����0���˵�
		buffer.append(processZeroLevelMenu(actionType, systemCode, language));
		// �����1���˵����ڲ������Ӳ˵��Ĵ���
		buffer.append(processFirstLevelMenu(actionType, comCode, userCode,
				gradeCodes, systemCode, language));
		buffer.append("    </form>");
		buffer
				.append("    <script language=\"Javascript\" src=\"/common/js/Common.js\"></script>");
		buffer
				.append("    <script language=\"Javascript\" src=\"/common/js/Application.js\"></script>");
		buffer.append("</body>");
		buffer.append("</html>");
		return buffer;
	}

	/**
	 * �����0���˵�(ͨ��ֻ��һ��������ò˵�ʱ��Ҫ��ʾ����Ϣ��Ĭ��Ϊ"�˵�����"��
	 * 
	 * @param actionType
	 *            ��������
	 * @param upperMenuId
	 *            �ϼ��˵�ID Ϊ0ʱ��ʾ�����˵�
	 * @param comCode
	 *            �����
	 * @param userCode
	 *            �û�����
	 * @param gradeCodes
	 *            ��λ�����б�
	 * @param systemCode
	 *            ϵͳ����
	 * @param language
	 *            ����
	 */
	protected StringBuffer processZeroLevelMenu(String actionType,
			String systemCode, String language) {
		StringBuffer buffer = new StringBuffer(1000);
		if (actionType.equals(CONFIG_MENU)) {
			buffer
					.append("<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">");
			buffer.append("  <tr>");
			buffer.append("    <td height=\"26\" class=menu nowrap>");
			if (language != null && language.equals("C")) {
				buffer.append("  &nbsp;<b>�˵�����(" + systemCode + ")</b>");
				buffer
						.append("      <img src='/common/images/btnAddChildMenu.gif' name=btnAddChildMenu alt=����Ӳ˵� onclick=\"return postAction(fm,'/platform/processGgMenu.do?actionType=prepareInsert&ggMenuMenuID=0','MenuTreeRight')\">");
			} else if (language != null && language.equals("T")) {
				buffer.append("  &nbsp;<b>�ˆ�����(" + systemCode + ")</b>");
				buffer
						.append("      <img src='/common/images/btnAddChildMenu.gif' name=btnAddChildMenu alt=����Ӳˆ� onclick=\"return postAction(fm,'/platform/processGgMenu.do?actionType=prepareInsert&ggMenuMenuID=0','MenuTreeRight')\">");
			} else {
				buffer.append("  &nbsp;<b>Config Menu(" + systemCode + ")</b>");
				buffer
						.append("      <img src='/common/images/btnAddChildMenu.gif' name=btnAddChildMenu alt=\"Add Sub Menu\" onclick=\"return postAction(fm,'/platform/processGgMenu.do?actionType=prepareInsert&ggMenuMenuID=0','MenuTreeRight')\">");
			}

			buffer.append("     </td>");
			buffer.append("  </tr>");
			buffer.append("</table>");
		}
		return buffer;
	}

	/**
	 * �����һ���˵����ڲ������Ӳ˵��Ĵ���
	 * 
	 * @param actionType
	 *            ��������
	 * @param upperMenuId
	 *            �ϼ��˵�ID Ϊ0ʱ��ʾ�����˵�
	 * @param comCode
	 *            �����
	 * @param userCode
	 *            �û�����
	 * @param gradeCodes
	 *            ��λ�����б�
	 * @param systemCode
	 *            ϵͳ����
	 * @param language
	 *            ����
	 */
	protected StringBuffer processFirstLevelMenu(String actionType,
			String comCode, String userCode, String gradeCodes,
			String systemCode, String language) {
		StringBuffer buffer = new StringBuffer(3000);

		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("systemCode", systemCode);
		queryRule.addEqual("menuLevel", Integer.valueOf("1"));
		// ֻ��ʾ��Ч״̬Ϊ1�Ĳ˵�
		if (actionType.equals(CONFIG_MENU) == false) {
			queryRule.addEqual("validInd", "1");
		}
		queryRule.addAscOrder("upperId");
		queryRule.addAscOrder("displayNo");

		List<SmcMenu> menus = super.find(queryRule);

		for (Iterator iter = menus.iterator(); iter.hasNext();) {
			SmcMenu ggMenu = (SmcMenu) iter.next();
			buffer.append(processMenu(ggMenu, actionType, comCode, userCode,
					gradeCodes, systemCode, language));
		}
		return buffer;
	}

	protected StringBuffer processMenu(SmcMenu smcMenu, String actionType,
			String comCode, String userCode, String gradeCodes,
			String systemCode, String language) {
		StringBuffer buffer = new StringBuffer(3000);
		int indent = 0;
		if (smcMenu.getMenuLevel().intValue() > 1) {
			indent = 3 * (smcMenu.getMenuLevel().intValue() - 1);
		}

		if (hasExecutePermission(smcMenu, actionType, comCode, userCode,
				gradeCodes, systemCode)) {
			boolean hasChild = false;// �Ƿ����Ӳ˵�
			if (actionType.equals(CONFIG_MENU)) {
				hasChild = hasChildMenu(smcMenu, systemCode);
			} else {
				hasChild = hasValidChildMenu(smcMenu, systemCode);
			}
			buffer
					.append("<table width=100% border=0 align=center cellpadding=0 cellspacing=0>");
			buffer.append("  <tr><td class=class=\"menu"
					+ smcMenu.getMenuLevel()
					+ "\" nowrap border=0 width=100% height=100%>");
			if (smcMenu.getMenuLevel().intValue() == 1) {
				buffer
						.append("<div style='height:26px;'><table border=0 cellpadding=0 cellspacing=0 width=100%><tr>");
			} else {
				buffer
						.append("<div><table style='height:22px;' border=0 cellpadding=0 cellspacing=0 width=100%><tr>");
			}
			buffer
					.append("<td valign=middle class=\"menu"
							+ smcMenu.getMenuLevel()
							+ "\" nowrap onmouseover=\"mouseOverMenuTD()\" onmouseout=\"mouseOutMenuTD()\"");
			if (hasChild) {
				buffer.append(" onclick=\"showMenu('menu" + smcMenu.getId()
						+ "');\">");
			} else {
				buffer.append(">");
			}
			buffer.append(StringUtils.newString("&nbsp;", 1));
			buffer.append(processConfigButton(smcMenu, actionType, language));
			buffer.append(StringUtils.newString("&nbsp;", indent));
			buffer.append("<img src=" + getImage(smcMenu, systemCode)
					+ " align=\"absmiddle\"");
			buffer
					.append(" onmouseover=\"mouseOverMenuTD()\" onmouseout=\"mouseOutMenuTD()\">");
			buffer.append(processHref(smcMenu, actionType, language));
			buffer.append("</td></tr></table></div>");
			if (hasChild) {
				buffer.append("<div id='menu" + smcMenu.getId()
						+ "' style=\"display:none\" contentEditable=inherit>");
				buffer.append("</div>");
			}
			buffer.append("     </td>");
			buffer.append("  </tr>");
			buffer.append("</table>");
		}
		return buffer;
	}

	/**
	 * �����¼��˵�
	 * 
	 * @param upperMenuId
	 *            �ϼ��˵�ID
	 */
	protected StringBuffer processSubMenu(String actionType,
			Integer upperMenuId, String comCode, String userCode,
			String gradeCodes, String systemCode, String language) {
		StringBuffer buffer = new StringBuffer(3000);
		SmcMenu smcMenu = this.get(upperMenuId);
		if (smcMenu == null) {
			return buffer;
		}

		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("systemCode", systemCode);
		queryRule.addEqual("upperId", smcMenu.getId());
		queryRule.addEqual("menuLevel", new Integer(smcMenu.getMenuLevel()
				.intValue() + 1));
		// ֻ��ʾ��Ч״̬Ϊ1�Ĳ˵�
		if (actionType.equals(CONFIG_MENU) == false) {
			queryRule.addEqual("validInd", "1");
		}
		queryRule.addAscOrder("displayNo");
		queryRule.addAscOrder("createTime");

		List<SmcMenu> menus = super.find(queryRule);

		for (Iterator iter = menus.iterator(); iter.hasNext();) {
			SmcMenu smcMenuNew = (SmcMenu) iter.next();
			buffer.append(processMenu(smcMenuNew, actionType, comCode,
					userCode, gradeCodes, systemCode, language));
		}
		return buffer;
	}

	/**
	 * �������ð�ť(�����������ò˵�ʱ��
	 * 
	 * @param ggMenu
	 */
	protected StringBuffer processConfigButton(SmcMenu smcMenu,
			String actionType, String language) {
		StringBuffer buffer = new StringBuffer(3000);
		if (actionType.equals(CONFIG_MENU)) {

			if (language != null && language.equals("C")) {
				buffer
						.append("<img  onmouseover=\"mouseOverMenuTD()\" onmouseout=\"mouseOutMenuTD()\" ");
				buffer
						.append("src='/common/images/btnModifyMenu.gif' name=btnModifyMenu alt=�޸� onclick=\"return postAction(fm,'/platform/processGgMenu.do?actionType=prepareUpdate&checkboxSelect=0&ggMenuMenuID="
								+ smcMenu.getId() + "','MenuTreeRight')\">");
				buffer
						.append("<img  onmouseover=\"mouseOverMenuTD()\" onmouseout=\"mouseOutMenuTD()\" ");
				buffer
						.append("src='/common/images/btnDeleteMenu.gif' name=btnDeleteMenu alt=ɾ�� onclick=\"return postActionWithConfirm(fm,'/platform/processGgMenu.do?actionType=delete&checkboxSelect=0&ggMenuMenuID="
								+ smcMenu.getId()
								+ "','ɾ���ò˵��������ٻָ���ȷ��ɾ��ô��','MenuTree')\">");
				buffer
						.append("<img  onmouseover=\"mouseOverMenuTD()\" onmouseout=\"mouseOutMenuTD()\" ");
				buffer
						.append("src='/common/images/btnViewMenu.gif' name=btnViewMenu alt=�鿴 onclick=\"return postAction(fm,'/platform/processGgMenu.do?actionType=view&ggMenuMenuID="
								+ smcMenu.getId() + "','MenuTreeRight')\">");
			} else if (language != null && language.equals("T")) {
				buffer
						.append("<img  onmouseover=\"mouseOverMenuTD()\" onmouseout=\"mouseOutMenuTD()\" ");
				buffer
						.append("src='/common/images/btnModifyMenu.gif' name=btnModifyMenu alt=�޸� onclick=\"return postAction(fm,'/platform/processGgMenu.do?actionType=prepareUpdate&checkboxSelect=0&ggMenuMenuID="
								+ smcMenu.getId() + "','MenuTreeRight')\">");
				buffer
						.append("<img  onmouseover=\"mouseOverMenuTD()\" onmouseout=\"mouseOutMenuTD()\" ");
				buffer
						.append("src='/common/images/btnDeleteMenu.gif' name=btnDeleteMenu alt=�h�� onclick=\"return postActionWithConfirm(fm,'/platform/processGgMenu.do?actionType=delete&checkboxSelect=0&ggMenuMenuID="
								+ smcMenu.getId()
								+ "','�h���ԓ�ˆΌ������ٻ֏ͣ��_�J�h���᣿','MenuTree')\">");
				buffer
						.append("<img  onmouseover=\"mouseOverMenuTD()\" onmouseout=\"mouseOutMenuTD()\" ");
				buffer
						.append("src='/common/images/btnViewMenu.gif' name=btnViewMenu alt=�鿴 onclick=\"return postAction(fm,'/platform/processGgMenu.do?actionType=view&ggMenuMenuID="
								+ smcMenu.getId() + "','MenuTreeRight')\">");

			} else {
				buffer
						.append("<img  onmouseover=\"mouseOverMenuTD()\" onmouseout=\"mouseOutMenuTD()\" ");
				buffer
						.append("src='/common/images/btnModifyMenu.gif' name=btnModifyMenu alt=Update onclick=\"return postAction(fm,'/platform/processGgMenu.do?actionType=prepareUpdate&checkboxSelect=0&ggMenuMenuID="
								+ smcMenu.getId() + "','MenuTreeRight')\">");
				buffer
						.append("<img  onmouseover=\"mouseOverMenuTD()\" onmouseout=\"mouseOutMenuTD()\" ");
				buffer
						.append("src='/common/images/btnDeleteMenu.gif' name=btnDeleteMenu alt=Delete onclick=\"return postActionWithConfirm(fm,'/platform/processGgMenu.do?actionType=delete&checkboxSelect=0&ggMenuMenuID="
								+ smcMenu.getId()
								+ "','Sure Delete?','MenuTree')\">");
				buffer
						.append("<img  onmouseover=\"mouseOverMenuTD()\" onmouseout=\"mouseOutMenuTD()\" ");
				buffer
						.append("src='/common/images/btnViewMenu.gif' name=btnViewMenu alt=View onclick=\"return postAction(fm,'/platform/processGgMenu.do?actionType=view&ggMenuMenuID="
								+ smcMenu.getId() + "','MenuTreeRight')\">");

			}
		}
		return buffer;
	}

	/**
	 * ���?t��
	 * 
	 * @param ggMenu
	 */
	protected StringBuffer processHref(SmcMenu smcMenu, String actionType,
			String language) {
		StringBuffer buffer = new StringBuffer(1000);
		String displayName = getDisplayName(smcMenu, language);
		if (actionType.equals(CONFIG_MENU)) {
			buffer.append("      " + displayName);
			if (smcMenu.getActionURL() == null
					|| smcMenu.getActionURL().trim().equals("")) {
				buffer
						.append("<img  onmouseover=\"mouseOverMenuTD()\" onmouseout=\"mouseOutMenuTD()\" ");
				if (language != null && language.equals("C")) {
					buffer
							.append("src='/common/images/btnAddChildMenu.gif' name=btnAddChildMenu alt=����Ӳ˵� onclick=\"return postAction(fm,'/platform/processGgMenu.do?actionType=prepareInsert&ggMenuMenuID="
									+ smcMenu.getId() + "','MenuTreeRight')\">");
				} else if (language != null && language.equals("C")) {
					buffer
							.append("src='/common/images/btnAddChildMenu.gif' name=btnAddChildMenu alt=����Ӳˆ� onclick=\"return postAction(fm,'/platform/processGgMenu.do?actionType=prepareInsert&ggMenuMenuID="
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
				buffer
						.append("&nbsp;<a href=\"#\" onclick=\"return false\" class=\""
								+ getMenuClass(smcMenu)
								+ "\" title=\""
								+ displayName + "\">" + displayName + "</a>");
			} else {
				buffer.append("&nbsp;<a href=\"" + smcMenu.getActionURL()
						+ "\" class=\"" + getMenuClass(smcMenu) + "\" title=\""
						+ getTitle(smcMenu, language) + "\"");
				if (smcMenu.getTarget() != null
						&& smcMenu.getTarget().trim().length() >= 0) {
					buffer.append(" target=\"" + smcMenu.getTarget() + "\"");
				}
				buffer.append(" onclick=\"processMenuClick(this)\">"
						+ displayName + "</a>");
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
	 * �õ�title
	 * 
	 * @param ggMenu
	 * @return
	 */
	protected String getTitle(SmcMenu smcMenu, String language) {
		SmcMenu menuTemp = new SmcMenu();
		menuTemp.setUpperId(smcMenu.getUpperId());
		String title = getDisplayName(smcMenu, language);
		while (true) {
			menuTemp = this.get(menuTemp.getUpperId());
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
	 * �û��ڵ�ǰ�˵��ڵ����Ƿ������Ȩ�޵��¼��˵�
	 * 
	 * @param ggMenu
	 *            ggMenuDto
	 * @param systemCode
	 *            ϵͳ����
	 * @return ���ڷ���true,���򷵻�false
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
		String hql = "select t from SmcMenu t where t.systemCode =? and  t.upperId =? and t.menuLevel = ? and t.validInd = '1'";
		List<SmcMenu> smcMenuList = this.findByHql(hql, systemCode, smcMenu
				.getId(), (new Integer(smcMenu.getMenuLevel().intValue() + 1)));

		validChildMap.put(smcMenu.getId(), "" + smcMenuList.size());
		if (smcMenuList.size() > 0) {
			return true;
		}
		return false;
	}

	public static void clearMap() {
		validChildMap.clear();
		powerMap.clear();
	}

	public synchronized void clear(String userCode) {
		System.out.println("clear user " + userCode + "'s menu power cache.");
		String powerKey = null;
		Object[] powerKeys = powerMap.keySet().toArray();
		for (int i = 0; i < powerKeys.length; i++) {
			powerKey = (String) powerKeys[i];
			if (powerKey != null
					&& powerKey.indexOf("|" + userCode + "|") != -1)
				powerMap.remove(powerKey);
		}
		validChildMap.clear();
	}

	/**
	 * �û��ڵ�ǰ�˵��ڵ����Ƿ�����¼��˵�
	 * 
	 * @param ggMenu
	 * @param systemCode
	 *            ϵͳ����
	 * @return ���ڷ���true,���򷵻�false
	 */
	protected boolean hasChildMenu(SmcMenu smcMenu, String systemCode) {
		String hql = "select t from SmcMenu t where t.systemCode =? and  t.upperId =? and t.menuLevel = ?";
		List<SmcMenu> smcMenuList = this.findByHql(hql, systemCode, smcMenu
				.getId(), (new Integer(smcMenu.getMenuLevel().intValue() + 1)));
		if (smcMenuList.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * ���ص�ǰ�˵����Image,����ȡ�˵������Image�����û��������ȡ�˵���ʽ��Image��<br>
	 * ��������˻��漼��
	 * 
	 * @param ggMenu
	 * @param systemCode
	 *            ϵͳ����
	 * @return ��ǰ�˵����Image
	 */
	protected String getImage(SmcMenu smcMenu, String systemCode) {
		if (smcMenu.getImage() != null
				&& smcMenu.getImage().trim().length() > 0) {
			return smcMenu.getImage();
		}
		String image = new StringBuffer("/"+systemCode+"/pages/image/imgIcon-").append(
				smcMenu.getMenuLevel()).append(".gif").toString();
		return image;
	}

	/**
	 * �û��Ե�ǰ�˵��Ƿ���ڲ���Ȩ��(������û�Ϊ�����û�����������ִ��Ȩ�ޣ�
	 * 
	 * @param ggMenu
	 * @return ���ڷ���true,���򷵻�false
	 */
	protected boolean hasExecutePermission(SmcMenu smcMenu, String actionType,
			String comCode, String userCode, String gradeCodes,
			String systemCode) {
		String key = new StringBuffer(smcMenu.getTaskCode()).append("|")
				.append(comCode).append("|").append(userCode).append("|")
				.append(gradeCodes).append("|").append(systemCode).toString();
		Object value = powerMap.get(key);
		if (value != null) {
			if (value.equals("true")) {
				return true;
			} else {
				return false;
			}
		}
		// ���ò˵�ʱ���ɼ�
		if (actionType.equals(CONFIG_MENU)) {
			return true;
		}
		// // ϵͳ����Ϊplatform��Ա��Ϊ�����û�ʱ��Ȩ�鿴���в˵�
		// if(systemCode.equals("application") &&
		// powerService.isSuperUser(comCode,
		// userCode)) {
		// return true;
		// }
		// û�����ù��ܴ�������Ȩ��ʾ
		if (smcMenu.getTaskCode() == null
				|| smcMenu.getTaskCode().trim().equals("")) {
			return false;
		}
		boolean result = false;
		result = powerService.checkPower(userCode, smcMenu.getTaskCode());
		powerMap.put(key, "" + result);
		if (result == false) {
			return false;
		}

		return true;
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

	public void setPowerService(PowerService powerService) {
		this.powerService = powerService;
	}

}