package com.sinosoft.claim.taglib;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * 生成功能按钮
 */
public class UICommandTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	protected String page = null;
	private String action = "";
	private String path = "";
	private String objectName = "";
	private String LINE_SEPARATOR = System.getProperty("line.separator");
	private StringBuffer results = new StringBuffer(64000);

	/**
	 * Render the beginning of the hyperlink.
	 * @return int
	 * @exception JspException if a JSP exception has occurred
	 */
	public int doStartTag() throws JspException {
		// Generate the URL to be encoded
		String objectName = getObjectName();
		String[] actions = StringUtils.split(action, ",");
		results.setLength(0);
		writeLine("    <table width='100%' border=0 cellspacing=0 cellpadding=0>");
		writeLine("        <tr align=center>");
		for (int i = 0; i < actions.length; i++) {
			String action = actions[i];
			writeLine("          <td >");
			if (action.equals("insert")) {
				writeLine("          	<input class='button' type='button'  name=buttonInsert   value='增 加'  alt=增 加 onclick=\"return insertMethod()\">");
			} else if (action.equals("delete")) {
				writeLine("          	<input class='button' type='button' name=buttonDelete value='" + this.getI18N("prompt.del") + "' alt=" + this.getI18N("prompt.del") + " onclick=\"return deleteMethod()\">");
			} else if (action.equals("update")) {
				writeLine("          	<input class='button' type='button' name=buttonUpdate alt='" + this.getI18N("prompt.update") + "' value='" + this.getI18N("prompt.update") + "' onclick=\"return updateMethod()\">");
				// 增加打印机构信息管理的修改按钮
			} else if (action.equals("updatePrint")) {
				writeLine("          	<input class='button' type='button' name=buttonUpdate alt='" + this.getI18N("prompt.update") + "' value='" + this.getI18N("prompt.update") + "' onclick=\"return updatePrintMethod()\">");
			} else if (action.equals("updateAdditionalRisk")) {
				writeLine("          	<input class='button' type='button' name=buttonUpdate alt='" + this.getI18N("prompt.update") + "' value='" + this.getI18N("prompt.update") + "' onclick=\"return updateAdditionalRiskMethod()\">");
			} else if (action.equals("disableAdditionalRisk")) {
				writeLine("          	<input class='button' type='button' name=buttonUpdate alt=停 用 value='停 用' onclick=\"return disableAdditionalRiskMethod()\">");
			} else if (action.equals("recoveryAdditionalRisk")) {
				writeLine("          	<input class='button' type='button' name=buttonUpdate alt=恢 復 value='恢 復' onclick=\"return recoveryAdditionalRiskMethod()\">");
			} else if (action.equals("confirm")) {
				writeLine("          	<input class='button' type='button' name=buttonConfirm alt=確 認 value='確 認' onclick=\"return confirmMethod()\">");
			} else if (action.equals("query")) {
				writeLine("          	<input class='button' type='button' name=buttonQuery alt='" + this.getI18N("prompt.query") + "' value='" + this.getI18N("prompt.query") + "' onclick=\"return queryMethod()\">");
			} else if (action.equals("return")) {
				writeLine("          	<input class='button' type='button' name=buttonReturn alt='" + this.getI18N("button.return.value") + "' value='" + this.getI18N("button.return.value") + "' onclick=\"javascript:history.back();\">");
			} else if (action.equals("view")) {
				writeLine("             <input class='button' type='button' name=buttonReturn alt=查 看 value='查 看' onclick=\"return viewMethod()\">");
			} else if (action.equals("writeOffOnly")) {
				writeLine("             <input class='button' type='button' name=buttonReturn alt='註銷' value='註銷' onclick=\"return writeOffOnlyMethod()\">");
			} else if (action.equals("writeOff")) {
				writeLine("             <input class='button' type='button' name=buttonReturn alt='註銷/恢復' value='註銷/恢復' onclick=\"return writeOffMethod()\">");
			} else if (action.equals("update/view")) {
				writeLine("             <input class='button' type='button' name=buttonReturn alt='修改/查看' value='修改/查看' onclick=\"return updateMethod()\">");
			} else if (action.equals("copy")) {
				writeLine("             <input class='button' type='button' name=buttoncopy alt=復 制 value='復 制' onclick=\"return copyMethod()\">");
			}
			writeLine("          </td>");
		}
		writeLine("        </tr>");
		writeLine("   </table>");
		writeLine("   <script language=\"javascript\">");
		writeLine("      <!--");
		for (int i = 0; i < actions.length; i++) {
			String action = actions[i];
			if (action.equals("insert")) {
				writeLine("        function insertMethod(){");
				writeLine("            fm.action=\"" + this.getPath() + "/process" + objectName + ".do?actionType=prepareInsert\";");
				writeLine("            fm.submit();");
				writeLine("            return true;");
				writeLine("        }");
			} else if (action.equals("delete")) {
				writeLine("        function deleteMethod(){");
				writeLine("            var count = getElementCount('checkboxSelect');");
				writeLine("            if(count==0){");
				writeLine("                alert('沒有記錄，無法選擇!');");
				writeLine("                return false;");
				writeLine("            }else if(count==1){");
				writeLine("                if(!fm.checkboxSelect.checked==true){");
				writeLine("                    alert('請選擇一條記錄進行刪除!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            else{");
				writeLine("                var n = 0;");
				writeLine("                for(var i=0;i<fm.checkboxSelect.length;i++){");
				writeLine("                    if(fm.checkboxSelect[i].checked==true){");
				writeLine("                        n = n + 1;");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                if(n==0){");
				writeLine("                    alert('請選擇至少一條記錄進行刪除!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            fm.action=\"" + this.getPath() + "/process" + objectName + ".do?actionType=delete\";");
				writeLine("            if(confirm('確實要刪除嗎？')){");
				writeLine("              fm.submit();");
				writeLine("            }");
				writeLine("        }");
			} else if (action.equals("update")) {
				writeLine("        function updateMethod(){");
				writeLine("            var count = getElementCount('checkboxSelect');");
				writeLine("            if(count==0){");
				writeLine("                alert('沒有記錄，無法選擇!');");
				writeLine("                return false;");
				writeLine("            }else if(count==1){");
				writeLine("                if(fm.checkboxSelect.checked==true){");
				writeLine("                    fm.action = '" + this.getPath() + "/process" + objectName + ".do?actionType=prepareUpdate';");
				writeLine("                    fm.submit();");
				writeLine("                    return true;");
				writeLine("                }");
				writeLine("                else{");
				writeLine("                    alert('請選擇一條記錄進行修改!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            else{");
				writeLine("                var n = 0;");
				writeLine("                for(var i=0;i<fm.checkboxSelect.length;i++){");
				writeLine("                    if(fm.checkboxSelect[i].checked==true){");
				writeLine("                        n = n + 1;");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                if(n==0){");
				writeLine("                    alert('請選擇一條記錄進行修改!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("                else if(n==1){");
				writeLine("                    for(var j=0;j<fm.checkboxSelect.length;j++){");
				writeLine("                        if(fm.checkboxSelect[j].checked==true){");
				writeLine("                            var checkboxSelect = fm.checkboxSelect[j].value;");
				writeLine("                            fm.action = '" + this.getPath() + "/process" + objectName + ".do?actionType=prepareUpdate';");
				writeLine("                            fm.submit();");
				writeLine("                            break;");
				writeLine("                        }");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                else{");
				writeLine("                    alert('只能選擇一條記錄進行修改!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            return true;");
				writeLine("        }");
				// modify by yanglibo 20090309 begin reason 增加打印机构信息管理的修改方法
			} else if (action.equals("updatePrint")) {
				writeLine("        function updatePrintMethod(){");
				writeLine("            var count = getElementCount('checkboxSelect');");
				writeLine("            if(count==0){");
				writeLine("                alert('沒有記錄，無法選擇!');");
				writeLine("                return false;");
				writeLine("            }else if(count==1){");
				writeLine("                if(fm.checkboxSelect.checked==true){");
				writeLine("                    fm.action = '" + this.getPath() + "/process" + objectName + ".do?actionType=prepareUpdatePrint';");
				writeLine("                    fm.submit();");
				writeLine("                    return true;");
				writeLine("                }");
				writeLine("                else{");
				writeLine("                    alert('請選擇一條記錄進行修改!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            else{");
				writeLine("                var n = 0;");
				writeLine("                for(var i=0;i<fm.checkboxSelect.length;i++){");
				writeLine("                    if(fm.checkboxSelect[i].checked==true){");
				writeLine("                        n = n + 1;");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                if(n==0){");
				writeLine("                    alert('請選擇一條記錄進行修改!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("                else if(n==1){");
				writeLine("                    for(var j=0;j<fm.checkboxSelect.length;j++){");
				writeLine("                        if(fm.checkboxSelect[j].checked==true){");
				writeLine("                            var checkboxSelect = fm.checkboxSelect[j].value;");
				writeLine("                            fm.action = '" + this.getPath() + "/process" + objectName + ".do?actionType=prepareUpdatePrint';");
				writeLine("                            fm.submit();");
				writeLine("                            break;");
				writeLine("                        }");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                else{");
				writeLine("                    alert('只能選擇一條記錄進行修改!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            return true;");
				writeLine("        }");
			} else if (action.equals("updateAdditionalRisk")) {
				writeLine("        function updateAdditionalRiskMethod(){");
				writeLine("            var count = getElementCount('checkboxSelect');");
				writeLine("            if(count==0){");
				writeLine("                alert('沒有記錄，無法選擇!');");
				writeLine("                return false;");
				writeLine("            }else if(count==1){");
				writeLine("                if(fm.checkboxSelect.checked==true){");
				writeLine("                    fm.action = '" + this.getPath() + "/process" + objectName + ".do?actionType=prepareUpdate';");
				writeLine("                    fm.submit();");
				writeLine("                    return true;");
				writeLine("                }");
				writeLine("                else{");
				writeLine("                    alert('請選擇一條記錄進行修改!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            else{");
				writeLine("                var n = 0;");
				writeLine("                for(var i=0;i<fm.checkboxSelect.length;i++){");
				writeLine("                    if(fm.checkboxSelect[i].checked==true){");
				writeLine("                        n = n + 1;");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                if(n==0){");
				writeLine("                    alert('請選擇一條記錄進行修改!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("                else if(n==1){");
				writeLine("                    for(var j=0;j<fm.checkboxSelect.length;j++){");
				writeLine("                        if(fm.checkboxSelect[j].checked==true){");
				writeLine("                            var checkboxSelect = fm.checkboxSelect[j].value;");
				writeLine("                            fm.action = '" + this.getPath() + "/process" + objectName + ".do?actionType=prepareUpdate';");
				writeLine("                            fm.submit();");
				writeLine("                            break;");
				writeLine("                        }");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                else{");
				writeLine("                    alert('只能選擇一條記錄進行修改!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            return true;");
				writeLine("        }");
			} else if (action.equals("disableAdditionalRisk")) {
				writeLine("        function disableAdditionalRiskMethod(){");
				writeLine("            var count = getElementCount('checkboxSelect');");
				writeLine("            if(count==0){");
				writeLine("                alert('沒有記錄，無法選擇!');");
				writeLine("                return false;");
				writeLine("            }else if(count==1){");
				writeLine("                if(fm.checkboxSelect.checked==true){");
				writeLine("                    fm.action = '" + this.getPath() + "/process" + objectName + ".do?actionType=prepareDisable';");
				writeLine("                    fm.submit();");
				writeLine("                    return true;");
				writeLine("                }");
				writeLine("                else{");
				writeLine("                    alert('請選擇一條記錄進行停用!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            else{");
				writeLine("                var n = 0;");
				writeLine("                for(var i=0;i<fm.checkboxSelect.length;i++){");
				writeLine("                    if(fm.checkboxSelect[i].checked==true){");
				writeLine("                        n = n + 1;");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                if(n==0){");
				writeLine("                    alert('請選擇一條記錄進行停用!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("                else if(n==1){");
				writeLine("                    for(var j=0;j<fm.checkboxSelect.length;j++){");
				writeLine("                        if(fm.checkboxSelect[j].checked==true){");
				writeLine("                            var checkboxSelect = fm.checkboxSelect[j].value;");
				writeLine("                            fm.action = '" + this.getPath() + "/process" + objectName + ".do?actionType=prepareDisable';");
				writeLine("                            fm.submit();");
				writeLine("                            break;");
				writeLine("                        }");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                else{");
				writeLine("                    alert('只能選擇一條記錄進行停用!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            return true;");
				writeLine("        }");
			} else if (action.equals("recoveryAdditionalRisk")) {
				writeLine("        function recoveryAdditionalRiskMethod(){");
				writeLine("            var count = getElementCount('checkboxSelect');");
				writeLine("            if(count==0){");
				writeLine("                alert('沒有記錄，無法選擇!');");
				writeLine("                return false;");
				writeLine("            }else if(count==1){");
				writeLine("                if(fm.checkboxSelect.checked==true){");
				writeLine("                    fm.action = '" + this.getPath() + "/process" + objectName + ".do?actionType=prepareRecovery';");
				writeLine("                    fm.submit();");
				writeLine("                    return true;");
				writeLine("                }");
				writeLine("                else{");
				writeLine("                    alert('請選擇一條記錄進行恢復!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            else{");
				writeLine("                var n = 0;");
				writeLine("                for(var i=0;i<fm.checkboxSelect.length;i++){");
				writeLine("                    if(fm.checkboxSelect[i].checked==true){");
				writeLine("                        n = n + 1;");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                if(n==0){");
				writeLine("                    alert('請選擇一條記錄進行恢復!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("                else if(n==1){");
				writeLine("                    for(var j=0;j<fm.checkboxSelect.length;j++){");
				writeLine("                        if(fm.checkboxSelect[j].checked==true){");
				writeLine("                            var checkboxSelect = fm.checkboxSelect[j].value;");
				writeLine("                            fm.action = '" + this.getPath() + "/process" + objectName + ".do?actionType=prepareRecovery';");
				writeLine("                            fm.submit();");
				writeLine("                            break;");
				writeLine("                        }");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                else{");
				writeLine("                    alert('只能選擇一條記錄進行恢復!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            return true;");
				writeLine("        }");
			} else if (action.equals("writeOff")) {
				writeLine("        function writeOffMethod(){");
				writeLine("            var count = getElementCount('checkboxSelect');");
				writeLine("            if(count==0){");
				writeLine("                alert('沒有記錄，無法選擇!');");
				writeLine("                return false;");
				writeLine("            }else if(count==1){");
				writeLine("                if(!fm.checkboxSelect.checked==true){");
				writeLine("                    alert('請選擇一條記錄進行註銷/恢復!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            else{");
				writeLine("                var n = 0;");
				writeLine("                for(var i=0;i<fm.checkboxSelect.length;i++){");
				writeLine("                    if(fm.checkboxSelect[i].checked==true){");
				writeLine("                        n = n + 1;");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                if(n==0){");
				writeLine("                    alert('請選擇至少一條記錄進行註銷/恢復!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            fm.action=\"" + this.getPath() + "/process" + objectName + ".do?actionType=writeOff\";");
				writeLine("            if(confirm('確實要註銷/恢復嗎？')){");
				writeLine("              fm.submit();");
				writeLine("            }");
				writeLine("            return true;");
				writeLine("        }");
			} else if (action.equals("writeOffOnly")) {
				writeLine("        function writeOffOnlyMethod(){");
				writeLine("            var count = getElementCount('checkboxSelect');");
				writeLine("            if(count==0){");
				writeLine("                alert('沒有記錄，無法選擇!');");
				writeLine("                return false;");
				writeLine("            }else if(count==1){");
				writeLine("                if(!fm.checkboxSelect.checked==true){");
				writeLine("                    alert('請選擇一條記錄進行註銷!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            else{");
				writeLine("                var n = 0;");
				writeLine("                for(var i=0;i<fm.checkboxSelect.length;i++){");
				writeLine("                    if(fm.checkboxSelect[i].checked==true){");
				writeLine("                        n = n + 1;");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                if(n==0){");
				writeLine("                    alert('請選擇至少一條記錄進行註銷!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            fm.action=\"" + this.getPath() + "/process" + objectName + ".do?actionType=writeOff\";");
				writeLine("            if(confirm('確實要註銷嗎？')){");
				writeLine("              fm.submit();");
				writeLine("            }");
				writeLine("            return true;");
				writeLine("        }");
			} else if (action.equals("view")) {
				writeLine("        function viewMethod(){");
				writeLine("            var count = getElementCount('checkboxSelect');");
				writeLine("            if(count==0){");
				writeLine("                alert('沒有記錄，無法選擇!');");
				writeLine("                return false;");
				writeLine("            }else if(count==1){");
				writeLine("                if(fm.checkboxSelect.checked==true){");
				writeLine("                    fm.action = '" + this.getPath() + "/process" + objectName + ".do?actionType=view';");
				writeLine("                    fm.submit();");
				writeLine("                    return true;");
				writeLine("                }");
				writeLine("                else{");
				writeLine("                    alert('請選擇一條記錄進行查看!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            else{");
				writeLine("                var n = 0;");
				writeLine("                for(var i=0;i<fm.checkboxSelect.length;i++){");
				writeLine("                    if(fm.checkboxSelect[i].checked==true){");
				writeLine("                        n = n + 1;");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                if(n==0){");
				writeLine("                    alert('請選擇一條記錄進行查看!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("                else if(n==1){");
				writeLine("                    for(var j=0;j<fm.checkboxSelect.length;j++){");
				writeLine("                        if(fm.checkboxSelect[j].checked==true){");
				writeLine("                            var checkboxSelect = fm.checkboxSelect[j].value;");
				writeLine("                            fm.action = '" + this.getPath() + "/process" + objectName + ".do?actionType=view';");
				writeLine("                            fm.submit();");
				writeLine("                            break;");
				writeLine("                        }");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                else{");
				writeLine("                    alert('只能選擇一條記錄進行查看!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            return true;");
				writeLine("        }");
			} else if (action.equals("confirm")) {
				writeLine("        function confirmMethod(){");
				writeLine("            var count = getElementCount('checkboxSelect');");
				writeLine("            if(count==0){");
				writeLine("                alert('沒有記錄，無法選擇!');");
				writeLine("                return false;");
				writeLine("            }else if(count==1){");
				writeLine("                if(fm.checkboxSelect.checked==true){");
				writeLine("                    fm.action = '" + this.getPath() + "/process" + objectName + ".do?actionType=prepareConfirm';");
				writeLine("                    fm.submit();");
				writeLine("                    return true;");
				writeLine("                }");
				writeLine("                else{");
				writeLine("                    alert('請選擇一條記錄進行確認!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            else{");
				writeLine("                var n = 0;");
				writeLine("                for(var i=0;i<fm.checkboxSelect.length;i++){");
				writeLine("                    if(fm.checkboxSelect[i].checked==true){");
				writeLine("                        n = n + 1;");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                if(n==0){");
				writeLine("                    alert('請選擇一條記錄進行確認!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("                else if(n==1){");
				writeLine("                    for(var j=0;j<fm.checkboxSelect.length;j++){");
				writeLine("                        if(fm.checkboxSelect[j].checked==true){");
				writeLine("                            var checkboxSelect = fm.checkboxSelect[j].value;");
				writeLine("                            fm.action = '" + this.getPath() + "/process" + objectName + ".do?actionType=prepareConfirm';");
				writeLine("                            fm.submit();");
				writeLine("                            break;");
				writeLine("                        }");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                else{");
				writeLine("                    alert('只能選擇一條記錄進行確認!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            return true;");
				writeLine("        }");
			} else if (action.equals("query")) {
				writeLine("        function queryMethod(){");
				writeLine("            fm.action=\"" + this.getPath() + "/process" + objectName + ".do?actionType=prepareQuery\";");
				writeLine("            fm.submit();");
				writeLine("            return true;");
				writeLine("        }");
			} else if (action.equals("copy")) {
				writeLine("        function copyMethod(){");
				writeLine("            var count = getElementCount('checkboxSelect');");
				writeLine("            if(count==0){");
				writeLine("                alert('沒有記錄，無法選擇!');");
				writeLine("                return false;");
				writeLine("            }else if(count==1){");
				writeLine("                if(fm.checkboxSelect.checked==true){");
				writeLine("                    fm.action = '" + this.getPath() + "/process" + objectName + ".do?actionType=prepareCopy';");
				writeLine("                    fm.submit();");
				writeLine("                    return true;");
				writeLine("                }");
				writeLine("                else{");
				writeLine("                    alert('請選擇一條記錄進行復制!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            else{");
				writeLine("                var n = 0;");
				writeLine("                for(var i=0;i<fm.checkboxSelect.length;i++){");
				writeLine("                    if(fm.checkboxSelect[i].checked==true){");
				writeLine("                        n = n + 1;");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                if(n==0){");
				writeLine("                    alert('請選擇一條記錄進行復制!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("                else if(n==1){");
				writeLine("                    for(var j=0;j<fm.checkboxSelect.length;j++){");
				writeLine("                        if(fm.checkboxSelect[j].checked==true){");
				writeLine("                            var checkboxSelect = fm.checkboxSelect[j].value;");
				writeLine("                            fm.action = '" + this.getPath() + "/process" + objectName + ".do?actionType=prepareCopy';");
				writeLine("                            fm.submit();");
				writeLine("                            break;");
				writeLine("                        }");
				writeLine("                    }");
				writeLine("                }");
				writeLine("                else{");
				writeLine("                    alert('只能選擇一條記錄進行復制!');");
				writeLine("                    return false;");
				writeLine("                }");
				writeLine("            }");
				writeLine("            return true;");
				writeLine("        }");
			}
		}
		writeLine("      //-->");
		writeLine("    </script>");
		// Print this element to our output writer
		JspWriter writer = pageContext.getOut();
		try {
			writer.print(results.toString());
		} catch (IOException e) {
			throw new JspException(e.toString());
		}
		// Evaluate the body of this tag
		return (EVAL_BODY_INCLUDE);
	}

	private String getI18N(String name) {
		Locale locale = ActionContext.getContext().getLocale();
		return LocalizedTextUtil.findDefaultText(name, locale);
	}

	/**
	 * Render the end of the hyperlink.
	 * @return int
	 * @exception JspException if a JSP exception has occurred
	 */
	public int doEndTag() throws JspException {
		return (EVAL_PAGE);
	}

	/** Release any acquired resources. */
	public void release() {
		super.release();
		this.page = null;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	private void writeLine(String value) {
		results.append(value);
		results.append(LINE_SEPARATOR);
	}

	/**
	 * @return Returns the action.
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action The action to set.
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return Returns the path.
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path The path to set.
	 */
	public void setPath(String path) {
		this.path = path;
	}
}