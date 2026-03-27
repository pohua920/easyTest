<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.sync.SyncConstants"%>
<%
	String deployCom = (String)session.getAttribute("deployCom");
%>
<html>
<head>
<title>分保接受人代码</title>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>

</head>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<!--  onkeydown方法禁用BackSpace onload方法光标定位在首个输入框 -->
<body id="all_title" onkeydown="keyDown()" onload="fm.reinsCode.focus()">
<div id="wrapper">
<div id="container">
<s:form name="fm" action="${ctx}/dictionary/insertPrpDTreatyReten.do" method="post">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
<!--  
<s:hidden name="prpDtreatyReten.flag" id="flag" value="${prpDtreatyReten.flag}"></s:hidden>
-->
<table width="100%" class="fix_table">
<tr class="top">
	<s:if test="${editType=='insert' }">
		<div id="crash_menu">
			<h2 align="center">增加分保接受人代码</h2>
		</div>
	</s:if>
	<s:if test="${editType=='update' }">
		<div id="crash_menu">
			<h2 align="center">修改分保接受人代码</h2>
		</div>
	</s:if>
	<s:if test="${editType=='view' }">
		<div id="crash_menu">
			<h2 align="center">查看分保接受人代码</h2>
		</div>
	</s:if>
</tr>       
<s:if test="${editType=='insert' }">
	<tr class="top">
	<td class="bgc_tt short">接受人代码<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDreinsurer.reinsCode" id="reinsCode" cssClass="input_w w_15 dc-chk dt-nzhs" maxlength="40"/>
	</td>
	<td class="bgc_tt short">接受人全称</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.longName" id="longName" cssClass="input_w w_15" maxlength="40" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">接受人简称</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.shortName" id="shortName" cssClass="input_w w_15" maxlength="40"/>
	</td>
	<td class="bgc_tt short">所在城市／地区</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.regionCode" id="regionCode" cssClass="input_w w_15 dt-nzhs" maxlength="6" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">所属国家</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.countryName" id="countryName" cssClass="input_w w_15" maxlength="40"/>
	</td>
	<td class="bgc_tt short">所在地区分类</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.locationFlag" id="locationFlag" cssClass="input_w w_15 dt-nzhs" maxlength="1" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">中文地址</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.caddr" id="CAddr" cssClass="input_w w_15" maxlength="40"/>
	</td>
	<td class="bgc_tt short">英文地址</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.eaddr" id="EAddr" cssClass="input_w w_15" maxlength="40" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">评定等级</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.level" id="level" cssClass="input_w w_15" maxlength="40"/>
	</td>
	<td class="bgc_tt short">合同业务联系人</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.ttyLinker" id="ttyLinker" cssClass="input_w w_15" maxlength="40" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">合同业务电话</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.ttyPhone" id="ttyPhone" cssClass="input_w w_15 dt-mobile" maxlength="40"/>
	</td>
	<td class="bgc_tt short">合同业务传真</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.ttyFax" id="ttyFax" cssClass="input_w w_15" maxlength="40" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">合同业务EMAIL</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.ttyEmail" id="ttyEmail" cssClass="input_w w_15 dt-email" maxlength="40"/>
	</td>
	<td class="bgc_tt short">临分业务联系人</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.facLinker" id="facLinker" cssClass="input_w w_15" maxlength="40" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short"> 临分业务电话</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.facPhone" id="facPhone" cssClass="input_w w_15 dt-mobile" maxlength="40"/>
	</td>
	<td class="bgc_tt short">临分业务传真</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.facFax" id="facFax" cssClass="input_w w_15" maxlength="40" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">临分业务EMAIL</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.facEmail" id="facEmail" cssClass="input_w w_15 dt-email" maxlength="40"/>
	</td>
	<td class="bgc_tt short">备注</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.remarks" id="remarks" cssClass="input_w w_15" maxlength="40" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">财务专项代码</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.accCode" id="accCode" cssClass="input_w w_15" maxlength="40"/>
	</td>
	<td class="bgc_tt short">变更日期</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.chgDate" id="chgDate" cssClass="input_w w_15 Wdate" 
			value="${prpDreinsurer.chgDate}"  onfocus="WdatePicker()" maxlength="40" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">新的分保接受人</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.newReinsCode" id="newReinsCode" cssClass="input_w w_15" maxlength="40"/>
	</td>
	<td class="bgc_tt short">有效标志</td>
	<td class="long">
		<ct:select name="prpDreinsurer.validStatus" value="${prpDreinsurer.validStatus}" id="validStatus" sysCode="DMS" codeType="ValidStatus"></ct:select>
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">操作员编码</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.operatorCode" id="operatorCode" cssClass="input_w w_15 dt-nzhs" maxlength="10"/>
	</td>
	<td class="bgc_tt short">操作时间</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.operatedTime" id="operatedTime" cssClass="input_w w_15 Wdate" 
			value="${prpDreinsurer.operatedTime}"  onfocus="WdatePicker()" maxlength="40" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">数据下发标志</td>
	<td class="long">
		 <ce:select cssClass='input_w w_15' name="prpDreinsurer.flag" id="flag" value="${prpDreinsurer.flag}" list="#{'0':'未下发','1':'待下发','2':'已下发','7':'下发失败'}"/>
	</td>
	</tr>
</s:if>
<s:elseif test="${editType=='update' }">
	<tr class="top">
	<td class="bgc_tt short">接受人代码<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDreinsurer.reinsCode" id="reinsCode" cssClass="input_w w_15" readonly="true" maxlength="40"/>
	</td>
	<td class="bgc_tt short">接受人全称</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.longName" id="longName" cssClass="input_w w_15" maxlength="40" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">接受人简称</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.shortName" id="shortName" cssClass="input_w w_15" maxlength="40"/>
	</td>
	<td class="bgc_tt short">所在城市／地区</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.regionCode" id="regionCode" cssClass="input_w w_15 dt-nzhs" maxlength="6"/>
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">所属国家</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.countryName" id="countryName" cssClass="input_w w_15" maxlength="40"/>
	</td>
	<td class="bgc_tt short">所在地区分类</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.locationFlag" id="locationFlag" cssClass="input_w w_15 dt-nzhs" maxlength="1" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">中文地址</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.caddr" id="CAddr" cssClass="input_w w_15" maxlength="40"/>
	</td>
	<td class="bgc_tt short">英文地址</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.eaddr" id="EAddr" cssClass="input_w w_15" maxlength="40" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">评定等级</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.level" id="level" cssClass="input_w w_15" maxlength="40"/>
	</td>
	<td class="bgc_tt short">合同业务联系人</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.ttyLinker" id="ttyLinker" cssClass="input_w w_15" maxlength="40" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">合同业务电话</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.ttyPhone" id="ttyPhone" cssClass="input_w w_15 dt-mobile" maxlength="40"/>
	</td>
	<td class="bgc_tt short">合同业务传真</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.ttyFax" id="ttyFax" cssClass="input_w w_15" maxlength="40" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">合同业务EMAIL</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.ttyEmail" id="ttyEmail" cssClass="input_w w_15 dt-email" maxlength="40"/>
	</td>
	<td class="bgc_tt short">临分业务联系人</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.facLinker" id="facLinker" cssClass="input_w w_15" maxlength="40" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short"> 临分业务电话</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.facPhone" id="facPhone" cssClass="input_w w_15 dt-mobile" maxlength="40"/>
	</td>
	<td class="bgc_tt short">临分业务传真</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.facFax" id="facFax" cssClass="input_w w_15" maxlength="40" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">临分业务EMAIL</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.facEmail" id="facEmail" cssClass="input_w w_15" maxlength="40"/>
	</td>
	<td class="bgc_tt short">备注</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.remarks" id="remarks" cssClass="input_w w_15" maxlength="40" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">财务专项代码</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.accCode" id="accCode" cssClass="input_w w_15" maxlength="40"/>
	</td>
	<td class="bgc_tt short">变更日期</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.chgDate" id="chgDate" cssClass="input_w w_15 Wdate" 
			value="${prpDreinsurer.chgDate}"  onfocus="WdatePicker()" maxlength="40" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">新的分保接受人</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.newReinsCode" id="newReinsCode" cssClass="input_w w_15" maxlength="40"/>
	</td>
	<td class="bgc_tt short">有效标志</td>
	<td class="long">
		<ct:select name="prpDreinsurer.validStatus" value="${prpDreinsurer.validStatus}" id="validStatus" sysCode="DMS" codeType="ValidStatus"></ct:select>
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">操作员编码</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.operatorCode" id="operatorCode" cssClass="input_w w_15 dt-nzhs" maxlength="10"/>
	</td>
	<td class="bgc_tt short">操作时间</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.operatedTime" id="operatedTime" cssClass="input_w w_15 Wdate" 
			value="${prpDreinsurer.operatedTime}"  onfocus="WdatePicker()" maxlength="40" />
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">数据下发标志</td>
	<td class="long">
		<ce:select cssClass='input_w w_15' name="prpDreinsurer.flag" id="flag" value="${prpDreinsurer.flag}" list="#{'0':'未下发','1':'待下发','2':'已下发','7':'下发失败'}"/>
	</td>
	</tr>
</s:elseif>
<s:elseif test="${editType=='view'}">
	<tr class="top">
	<td class="bgc_tt short">接受人代码<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDreinsurer.reinsCode" id="reinsCode" cssClass="input_w w_15" 
			maxlength="40" readonly="true"/>
	</td>
	<td class="bgc_tt short">接受人全称</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.longName" id="longName" cssClass="input_w w_15" 
			maxlength="40" readonly="true" disabled="true"/>
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">接受人简称</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.shortName" id="shortName" cssClass="input_w w_15" 
			maxlength="40" readonly="true" disabled="true"/>
	</td>
	<td class="bgc_tt short">所在城市／地区</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.regionCode" id="regionCode" cssClass="input_w w_15" maxlength="40" readonly="true" disabled="true"/>
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">所属国家</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.countryName" id="countryName" cssClass="input_w w_15" maxlength="40" readonly="true" disabled="true"/>
	</td>
	<td class="bgc_tt short">所在地区分类</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.locationFlag" id="locationFlag" cssClass="input_w w_15" maxlength="1" readonly="true" disabled="true"/>
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">中文地址</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.caddr" id="CAddr" cssClass="input_w w_15" maxlength="40" readonly="true" disabled="true"/>
	</td>
	<td class="bgc_tt short">英文地址</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.eaddr" id="EAddr" cssClass="input_w w_15" maxlength="40" readonly="true" disabled="true"/>
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">评定等级</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.level" id="level" cssClass="input_w w_15" maxlength="40" readonly="true" disabled="true"/>
	</td>
	<td class="bgc_tt short">合同业务联系人</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.ttyLinker" id="ttyLinker" cssClass="input_w w_15" maxlength="40" readonly="true" disabled="true"/>
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">合同业务电话</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.ttyPhone" id="ttyPhone" cssClass="input_w w_15" maxlength="40" readonly="true" disabled="true"/>
	</td>
	<td class="bgc_tt short">合同业务传真</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.ttyFax" id="ttyFax" cssClass="input_w w_15" maxlength="40" readonly="true" disabled="true"/>
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">合同业务EMAIL</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.ttyEmail" id="ttyEmail" cssClass="input_w w_15" maxlength="40" readonly="true" disabled="true"/>
	</td>
	<td class="bgc_tt short">临分业务联系人</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.facLinker" id="facLinker" cssClass="input_w w_15" maxlength="40" readonly="true" disabled="true"/>
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short"> 临分业务电话</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.facPhone" id="facPhone" cssClass="input_w w_15" maxlength="40" readonly="true" disabled="true"/>
	</td>
	<td class="bgc_tt short">临分业务传真</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.facFax" id="facFax" cssClass="input_w w_15" maxlength="40" readonly="true" disabled="true"/>
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">临分业务EMAIL</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.facEmail" id="facEmail" cssClass="input_w w_15" maxlength="40" readonly="true" disabled="true"/>
	</td>
	<td class="bgc_tt short">备注</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.remarks" id="remarks" cssClass="input_w w_15" maxlength="40" readonly="true" disabled="true"/>
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">财务专项代码</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.accCode" id="accCode" cssClass="input_w w_15" maxlength="40" readonly="true" disabled="true"/>
	</td>
	<td class="bgc_tt short">变更日期</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.chgDate" id="chgDate" cssClass="input_w w_15" maxlength="40" readonly="true" disabled="true"/>
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">新的分保接受人</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.newReinsCode" id="newReinsCode" cssClass="input_w w_15" maxlength="40" readonly="true" disabled="true"/>
	</td>
	<td class="bgc_tt short">有效标志</td>
	<td class="long">
		<ct:select name="prpDreinsurer.validStatus" value="${prpDreinsurer.validStatus}" id="validStatus" sysCode="DMS" codeType="ValidStatus" disabled="true"></ct:select>
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">操作员编码</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.operatorCode" id="operatorCode" cssClass="input_w w_15" maxlength="40" readonly="true" disabled="true"/>
	</td>
	<td class="bgc_tt short">操作时间</td>
	<td class="long">
		<s:textfield name="prpDreinsurer.operatedTime"  id="operatedTime" cssClass="input_w w_15 " maxlength="40" readonly="true" disabled="true"/>
	</td> 	
	</tr>
	<tr class="top">
	<td class="bgc_tt short">数据下发标志</td>
	<td class="long">
		<ce:select cssClass='input_w w_15' disabled='true' name="prpDreinsurer.flag" id="flag" value="${prpDreinsurer.flag}" list="#{'0':'未下发','1':'待下发','2':'已下发','7':'下发失败'}"/>
	</td>
	</tr>
</s:elseif>
</table> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr align="center" class="top">
		<c:if test="${editType=='view' }">
			<td>
			<% if(SyncConstants.ComCode_Head.equals(deployCom)){%>
			<button type="button" value=""  onclick="prepareUpdate()"><span><em>修改</em></span></button>
<!--			<input type="button" value="修改" class="button_ty" onclick="prepareUpdate()">-->
			<%}%>
			</td>
		</c:if>
		<c:if test="${editType=='insert' }">
			<td>
			<button type="button" value=""  onclick="return addMethod()"><span><em>保存</em></span></button>
<!--			<input type="button" value="保存" class="button_ty" onclick="return addMethod()">-->
			</td>
		</c:if>
		<c:if test="${editType=='update' }">
			<td>
			<button type="button" value=""  onclick="updateMethod()"><span><em>保存</em></span></button>
<!--			<input type="button" value="保存" class="button_ty" onclick="updateMethod()">-->
			</td>
		</c:if>
	</tr>
</table>
</s:form>
</div>
</div>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/js/newCalendar.js"></script>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/widgets/yui/autocomplete/autocomplete-min.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script language="javascript" src="${ctx}/common/js/StaticJavascript.jsp"></script>
<script type="text/javascript">
	function updateMethod(){
		if(YAHOO.quote.data.datacheck('fm') ){
		    fm.action="${ctx}/dictionary/updatePrpDreinsurer.do";
		    fm.submit();
		}else{
			 alert("界面输入有误，请核实！");
		}
	}
	function addMethod(){
		if(YAHOO.quote.data.datacheck('fm') ){
			hasSameKey();
		}else{
			 alert("界面输入有误，请核实！");
		}
	}
	function prepareUpdate(){//客户需求，查看页面点击修改要进入修改页面。2009-10-21
		var reinsCode    = document.getElementById("reinsCode").value;
		editRecord("${ctx}/dictionary/prepareUpdatePrpDreinsurer.do?prpDreinsurer.reinsCode="+reinsCode+"&editType=update");
		window.close();
	}
	function hasSameKey(){//多主键校验！
		var key1 = document.getElementById("reinsCode").value;
		var url="${ctx}/dictionary/isSameKey.do?tableName=PrpDreinsurer&values=reinsCode\='"+key1+"'";
		var handleSuccess = function(o){
			if(o.responseText=="sameKey"){
				alert("该预算单位代码已存在！");
			}else{
				fm.action="${ctx}/dictionary/insertPrpDreinsurer.do";
				fm.submit();
			}
		};
		var handleFailure = function(o){
			if(o.responseText !== undefined){
				var msg = i18n.errors.updatefail+"!\n"+ o.status +" " + o.statusText;
				alert(msg);
				return true;
			}
		};	
		var callback =
		{
		  success:handleSuccess,
		  failure:handleFailure
		};
		var req = YAHOO.util.Connect.asyncRequest('POST', url, callback, "");
	}
	function keyDown(){
            // 禁止使用backspace键
            if(window.event.keyCode == 8){
             event.keyCode = 0; 
       		 event.cancelBubble = true; 
             return false; 
            }
        }
</script>




