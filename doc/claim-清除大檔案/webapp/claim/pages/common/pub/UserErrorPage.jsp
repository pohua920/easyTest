<%--
****************************************************************************
* DESC       ：错误处理页
* AUTHOR     ：
* CREATEDATE ：
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>

<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.io.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.lang.reflect.*"%>
<%@ page import="com.sinosoft.sysframework.exceptionlog.*"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%@ page isErrorPage="true"%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
	String strCaption = ""; //标题
	String strTitle = ""; //信息
	String strContent = ""; //详细信息
	StringWriter stringWriter = new StringWriter();
	if (request.getAttribute("EXCEPTION") instanceof UserException) //用户异常
	{
		strTitle += ((UserException) request.getAttribute("EXCEPTION")).getErrorMessage() + "<br>";
		strContent += "<table>";
		strContent += "  <tr>";
		strContent += "    <td>";
		strContent += "      ErrorCatalog:";
		strContent += "    </td>";
		strContent += "    <td>";
		strContent += ((UserException) request.getAttribute("EXCEPTION")).getErrorCatalog();
		strContent += "    </td>";
		strContent += "  </tr>";
		strContent += "  <tr>";
		strContent += "    <td>";
		strContent += "      ErrorNo:";
		strContent += "    </td>";
		strContent += "    <td>";
		strContent += ((UserException) request.getAttribute("EXCEPTION")).getErrorNo();
		strContent += "    </td>";
		strContent += "  </tr>";
		strContent += "  <tr>";
		strContent += "    <td>";
		strContent += "      ErrorModule:";
		strContent += "    </td>";
		strContent += "    <td>";
		strContent += ((UserException) request.getAttribute("EXCEPTION")).getErrorModule();
		strContent += "    </td>";
		strContent += "  </tr>";
		strContent += "</table>";
	} else {
		Throwable systemException = (Exception) request.getAttribute("EXCEPTION");
		if (systemException != null) {
			String vers = System.getProperty("java.version");
			if (vers.compareTo("1.4") > -1) { //JDK1.4以上
				Class c = Exception.class;
				Method method = c.getMethod("getCause", new Class[0]);
				Throwable tempException = (Throwable) method.invoke(systemException, new Object[0]);
				if (tempException != null) {
					systemException = tempException;
				}
			} else {
				systemException.printStackTrace(new PrintWriter(stringWriter));
				String tempContent = stringWriter.toString();
				if (tempContent != null) { //分析异常堆栈，模拟实现JDK1.4的getClause()
					int start = tempContent.indexOf(": ");
					if (start > -1) {
						int start2 = tempContent.indexOf(":", start + 2);
						if (start2 > -1) {
							try {
								Class cl = Class.forName(tempContent.substring(start + 2, start2));
								int end = tempContent.indexOf("\n", start + 2);
								if (end > -1) {
									tempContent = tempContent.substring(start2 + 2, end);
								} else {
									tempContent = tempContent.substring(start2 + 2);
								}
								Constructor constructor = cl.getDeclaredConstructor(new Class[] { String.class });
								if (constructor != null) {
									Throwable tempException = (Throwable) constructor.newInstance(new Object[] { tempContent });
									if (tempException != null) {
										systemException = tempException;
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

			String message = systemException.getMessage();
			if (message == null || message.trim().equals("")) {
				message = "未知错误";
			}

			//处理strCaption和strTitle
			if (systemException instanceof SQLException) {
				strCaption = "系统提示--資料庫错误";
				if (message.indexOf("is still being referenced") > -1) {
					strTitle = "该数据存在外键引用,不允许删除<br>";
				}
			}

			//处理默认strCaption
			if (strCaption == null || strCaption.trim().equals("")) {
				strCaption = "系统提示--内部错误";
			}
			//处理默认strTitle
			if (strTitle == null || strTitle.trim().equals("")) {
				strTitle = message + "<br>";
			}

			//重新得到异常信息
			stringWriter = new StringWriter();
			systemException.printStackTrace(new PrintWriter(stringWriter));
			strContent = stringWriter.toString();
		}
	}
%>
<html>
<head>
<title><s:text name="error.regist.required" /></title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script language=javascript>
  /**
   * 显示/隐藏异常详细信息
   */
  function showContent()
  {
    if(trContent.style.display=='')
      trContent.style.display = 'none';
    else
      trContent.style.display = '';
  }
  </script>
</head>
<body class="interface">
	<table cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" align="center">
		<tr>
			<td class=formtitle colspan="2"><%=strTitle%></td>
		</tr>
		<tr>
			<td class="common">
				<img src='/claim/images/bgClaimFailure.gif' style='cursor: hand' alt='详细信息' onclick="showContent()">
			</td>
			<td id="trContent" align=left colspan="2" class="common" style="display:">
				<pre><%=strContent%></pre>
			</td>
		</tr>
	</table>
</body>
</html>