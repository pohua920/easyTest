<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%
	String sex = (String)request.getAttribute("sex");
	String flag = (String)request.getAttribute("flag");
%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type="text/javascript" src="${ctx}/common/js/sinosoft.js"></script>
<script type="text/javascript" src="${ctx}/common/js/MulLine.js"></script>
<script type="text/javascript" src="${ctx}/common/js/Common.js"></script>
<script type="text/javascript" src="${ctx }/common/dtree/dtree.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>

</head>
<body id="all_title">
<div id="wrapper">
<div id="container">

<s:form name="fm" action=""	>
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="comCode" id="comCode" value="${comCode}"></s:hidden>
	<table width="100%" class="fix_table">
		<tr class="top">
            <div id="crash_menu">
            <h2 align="center">
              <s:if test="${editType=='insert' }">用户增加</s:if>
              <s:if test="${editType=='update' }">用户修改</s:if>
              <s:if test="${editType=='view' }">用户查看</s:if>
            </h2>
            </div>	
		</tr>				
<!-------- 查看------------ -->		
		<s:if test="${editType=='view' }">
			<s:hidden name="utiIUser.userCode "/>
			<tr>
				<td class="bgc_tt short">用户代码</td>
				<td class="long"><s:textfield name="utiIUser.userCode" value="${utiIUser.userCode}"
					id="userCode" cssClass='input_w w_30' maxlength="40" readonly="true"/></td>
				<td class="bgc_tt short">用户名称</td>
				<td class="long"><s:textfield name="utiIUser.userName" value="${utiIUser.userName}"
					id="userName" cssClass='input_w w_30' maxlength="30" readonly="true"/></td>				
				
			</tr>
			<tr>
				<td class="bgc_tt short">用户类型</td>
				<td class="long">
					    <div id="userTypeDiv1" class="selectui-indiv">
					        <div class="selectConfig">
					        <div class="codeType">StaticSelect</div>
					        </div>
					        <c:set var="checked" value="${utiIUser.userType}" />
					        <ce:select name="utiIUser.userType"  cssClass="selectui-input" disabled="true"  value="${checked}" 
					        list="#@java.util.HashMap@{'01':'员工用户','02':'业务员用户','03':'虚拟用户','04':'合作伙伴用户','06':'企业用户','07':'个人用户','98':'临时用户','99':'其他用户'}" />
					    </div>
			    </td>			
				<td class="bgc_tt short">归属机构</td>
				<td class="long"><s:textfield name="utiIUser.comCode" value="${utiIUser.comCode}"
					id="comcode" cssClass='input_w w_30' maxlength="10" disabled="true"/></td>	
			</tr>
            
<!-- 用户信息查看 -->
            <tr>
			<td class="bgc_tt short">年龄</td>
				<td class="long"><s:textfield name="utiIUserIdv.age" value="${utiIUserIdv.age}"
						id="utiIUserIdv.age" cssClass='input_w w_30' maxlength="10" readonly="true"/></td>
				<td class="bgc_tt short">出生日期</td>
				<td class="long"><input readonly="true" name="utiIUserIdv.birthDate" 
						id="utiIUserIdv.birthDate" class='input_w w_30' maxlength="10" value="${utiIUserIdv.birthDate}" />
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">性别</td>
				<td class="long">
					<s:textfield name="utiIUserIdv.sex" value="<%=sex %>" cssClass='input_w w_30' id="utiIUserIdv.sex"  maxlength="1" readonly="true"/>
				</td>
				<td class="bgc_tt short">健康状况</td>
				<td class="long"><s:textfield name="utiIUserIdv.health" 
						id="utiIUserIdv.health" cssClass='input_w w_30' maxlength="1000" value="${utiIUserIdv.health }" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">家庭电话</td>
				<td class="long"><s:textfield name="utiIUserIdv.homePhone" value="${utiIUserIdv.homePhone}"
						id="utiIUserIdv.homePhone" cssClass='input_w w_30' maxlength="30" readonly="true"/></td>
				<td class="bgc_tt short">办公电话</td>
				<td class="long"><s:textfield name="utiIUserIdv.officePhone" 
						id="utiIUserIdv.officePhone" cssClass='input_w w_30' maxlength="30" value="${utiIUserIdv.officePhone }" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">电子邮件</td>
				<td class="long"><s:textfield name="utiIUserIdv.email" value="${utiIUserIdv.email}"
						id="utiIUserIdv.email" cssClass='input_w w_30' maxlength="100" readonly="true"/></td>
				<td class="bgc_tt short">手机号码</td>
				<td class="long"><s:textfield name="utiIUserIdv.mobile" value="${utiIUserIdv.mobile}"
						id="utiIUserIdv.mobile" cssClass='input_w w_30' maxlength="30" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">邮件地址</td>
				<td class="long"><s:textfield name="utiIUserIdv.postAddress" value="${utiIUserIdv.postAddress}"
						id="utiIUserIdv.addressCName" cssClass='input_w w_30' maxlength="1000" readonly="true"/></td>
				<td class="bgc_tt short">邮政编码</td>
				<td class="long"><s:textfield name="utiIUserIdv.postCode" value="${utiIUserIdv.postCode}"
						id="utiIUserIdv.postCode" cssClass='input_w w_30' maxlength="10" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">传真号码</td>
				<td class="long"><s:textfield name="utiIUserIdv.faxNumber" value="${utiIUserIdv.faxNumber}"
						id="utiIUserIdv.faxNumber" cssClass='input_w w_30' maxlength="30" readonly="true"/></td>
				<td class="bgc_tt short">身份证</td>
				<td class="long"><s:textfield name="utiIUserIdv.identifyNumber" value="${utiIUserIdv.identifyNumber}"
						id="utiIUserIdv.identifyNumber" cssClass='input_w w_30' maxlength="30" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">职业</td>
				<td class="long"><s:textfield name="utiIUserIdv.occupationCode" value="${utiIUserIdv.occupationCode}"
						id="utiIUserIdv.occupationCode" cssClass='input_w w_30' maxlength="1000" readonly="true"/></td>
				<td class="bgc_tt short">学历</td>
				<td class="long"><s:textfield name="utiIUserIdv.educationCode" value="${utiIUserIdv.educationCode}"
						id="utiIUserIdv.educationCode" cssClass='input_w w_30' maxlength="1000" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">工作单位</td>
				<td class="long"><s:textfield name="utiIUserIdv.unit" value="${utiIUserIdv.unit}"
						id="utiIUserIdv.unit" cssClass='input_w w_30' maxlength="1000" readonly="true"/></td>
				<td class="bgc_tt short">单位地址</td>
				<td class="long"><s:textfield name="utiIUserIdv.unitAddress" value="${utiIUserIdv.unitAddress}"
						id="utiIUserIdv.unitAddress" cssClass='input_w w_30' maxlength="1000" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">开户银行</td>
				<td class="long"><s:textfield name="utiIUserIdv.bank" value="${utiIUserIdv.bank}"
						id="utiIUserIdv.bank" cssClass='input_w w_30' maxlength="1000" readonly="true"/></td>
				<td class="bgc_tt short">账号</td>
				<td class="long"><s:textfield name="utiIUserIdv.account" 
						id="utiIUserIdv.account" cssClass='input_w w_30' maxlength="1000" value="${utiIUserIdv.account }" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">建立机构</td>
				<td class="long"><s:textfield name="utiIUserIdv.terminalCode" value="${utiIUserIdv.terminalCode}"
						id="utiIUserIdv.terminalCode" cssClass='input_w w_30' maxlength="10" readonly="true"/></td>
				<td class="bgc_tt short">用户来源</td>
				<td class="long"><s:textfield name="utiIUserIdv.source" 
						id="utiIUserIdv.source" cssClass='input_w w_30' maxlength="1000" value="${utiIUserIdv.source }" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">客户标识</td>
				<td class="long">
		      		<s:textfield name="utiIUserIdv.customerFlag" value="<%=flag %>" cssClass='input_w w_30' id="utiIUserIdv.customerFlag"  maxlength="1" readonly="true"/>
				</td>
				<td class="bgc_tt short">印章</td>
				<td class="long"><s:textfield name="utiIUserIdv.seal" value="${utiIUserIdv.seal}"
						id="utiIUserIdv.seal" cssClass='input_w w_30' maxlength="1000" readonly="true"/></td>
			</tr>
			<tr>
				<td class="bgc_tt short">信息创建人</td>
				<td class="long">
					<s:textfield name="utiIUserIdv.creatorCode" 
						id="utiIUserIdv.creatorCode" cssClass='input_w w_30' maxlength="40" value="${utiIUserIdv.creatorCode}" readonly="true"/>
				</td>
				<td class="bgc_tt short">信息创建日期</td>
				<td class="long"><s:textfield name="utiIUserIdv.createDate" 
						id="utiIUserIdv.createDate" cssClass='input_w w_30' maxlength="10" value="${utiIUserIdv.createDate}" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">信息修改人</td>
				<td class="long">
					<s:textfield name="utiIUserIdv.updaterCode" 
						id="utiIUserIdv.updaterCode" cssClass='input_w w_30' maxlength="40" value="${utiIUserIdv.updaterCode}" readonly="true"/>
				</td>
				<td class="bgc_tt short">信息修改日期</td>
				<td class="long"><s:textfield name="utiIUserIdv.updateDate" 
						id="utiIUserIdv.updateDate" cssClass='input_w w_30' maxlength="10" value="${utiIUserIdv.updateDate}" readonly="true"/>
				</td>
			</tr>
			<tr>
                <td class="bgc_tt short">备注</td>
            	<td class="long" colspan='3'>
                <s:textarea rows="4" cols="40" cssStyle="wwctrl dc-chk" name="utiIUserIdv.remark" value="${utiIUserIdv.remark}" readonly="true"></s:textarea>		
				</td>
			</tr>
            <tr>
					
		   	    </tr>
			</s:if>
<!-------- 修改------------ -->	
			<s:elseif test="${editType=='update' }">
				<s:hidden name="utiIUser.userCode "/>
	            <s:hidden name="utiIUser.userSort " value="${utiIUser.userSort}"/>
	            <s:hidden name="utiIUserIdv.userCode " value="${utiIUserIdv.userCode}"/>
	            <s:hidden name="utiIUserIdv.comCode " value="${utiIUserIdv.comCode}"/>
	            <s:hidden name="utiIUser.auditStatus" value="${utiIUser.auditStatus}"></s:hidden>
	            <s:hidden name="utiIUser.validStatus" value="${utiIUser.validStatus}"></s:hidden>
				<tr>
					<td class="bgc_tt short">用户代码</td>
					<td class="long"><s:textfield name="utiIUser.userCode" value="${utiIUser.userCode}"
						id="userCode" cssClass='input_w w_30' maxlength="40" readonly="true"/></td>
					<td class="bgc_tt short">用户名称<font color="red">*</font></td>
					<td class="long">				
					<s:textfield name="utiIUser.userName" maxlength="30"  id="utiIUser.userName"  cssClass='input_w w_30 dc-chk' value="${utiIUser.userName }"/>
			</td>
				</tr>
	            
				<tr>
					
					<td class="bgc_tt short">归属机构</td>
					<td class="long"><s:textfield name="utiIUser.comCode" value="${utiIUser.comCode}"
					id="comcode" cssClass='input_w w_30' maxlength="8" disabled="true"/></td>
					<td class="bgc_tt short">性别</td>
					<td class="long">
<!--					modify start-->
<!--						<input type="radio" name="utiIUserIdv.sex" value="${ utiIUserIdv.sex}" id="utiIUserIdv.sex"  maxlength="1" >男</input>-->
<!--			      		<input type="radio" name="utiIUserIdv.sex" value="${ utiIUserIdv.sex}" id="utiIUserIdv.sex" maxlength="1" >女</input>-->
							<ce:radio name="utiIUserIdv.sex" value="${utiIUserIdv.sex}" list="#{'0':'男','1':'女'}"/>
<!--					modify end-->
					</td>
			    </tr>
	                <!-------- 个人客户信息 -->
	            
	            <tr>
					<td class="bgc_tt short">年龄</td>
					<td class="long"><s:textfield name="utiIUserIdv.age" value="${utiIUserIdv.age}"
							id="utiIUserIdv.age" cssClass='input_w w_30 dt-num' maxlength="10"/></td>
					<td class="bgc_tt short">出生日期</td>
					<td class="long"><input type="text" readonly="true" name="utiIUserIdv.birthDate" 
							id="utiIUserIdv.birthDate" class="input_w w_30 Wdate" onFocus="WdatePicker()" maxlength="20" value="${utiIUserIdv.birthDate}" />
<!--						<img src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板" id="imgBtn1" width="14" height="14" /> -->
<!--						<span class="calender-panel">-->
<!--							<div id="calContainer1" style="position: absolute;"></div>-->
<!--						</span>-->
					</td>
				</tr>
				<tr>
					<td class="bgc_tt short">健康状况</td>
					<td class="long" colspan='3'><s:textfield name="utiIUserIdv.health" 
							id="utiIUserIdv.health" cssClass='input_w w_p93' maxlength="1000" value="${utiIUserIdv.health }" />
					</td>
				</tr>
				<tr>
					<td class="bgc_tt short">家庭电话</td>
					<td class="long"><s:textfield name="utiIUserIdv.homePhone" value="${utiIUserIdv.homePhone}"
							id="utiIUserIdv.homePhone" cssClass='input_w w_30 ' maxlength="30"/></td>
					<td class="bgc_tt short">办公电话</td>
					<td class="long"><s:textfield name="utiIUserIdv.officePhone" 
							id="utiIUserIdv.officePhone" cssClass='input_w w_30 ' maxlength="30" value="${utiIUserIdv.officePhone }" />
					</td>
				</tr>
				<tr>
					<td class="bgc_tt short">电子邮件</td>
					<td class="long"><s:textfield name="utiIUserIdv.email" value="${utiIUserIdv.email}"
							id="utiIUserIdv.email" cssClass='input_w w_30' maxlength="100"/></td>
					<td class="bgc_tt short">手机号码</td>
					<td class="long"><s:textfield name="utiIUserIdv.mobile" value="${utiIUserIdv.mobile}"
							id="utiIUserIdv.mobile" cssClass='input_w w_30 dt-num' maxlength="30"/>
					</td>
				</tr>
				<tr>
					<td class="bgc_tt short">邮寄地址</td>
					<td class="long" colspan='3'><s:textfield name="utiIUserIdv.postAddress" value="${utiIUserIdv.postAddress}"
							id="utiIUserIdv.postAddress" cssClass='input_w w_p93' maxlength="1000"/></td>
							</tr>
				<tr>
					<td class="bgc_tt short">开户银行</td>
					<td class="long" colspan='3'><s:textfield name="utiIUserIdv.bank" value="${utiIUserIdv.bank}"
							id="utiIUserIdv.bank" cssClass='input_w w_p93' maxlength="1000"/></td>
					
				</tr>
				<tr>
					<td class="bgc_tt short">传真号码</td>
					<td class="long"><s:textfield name="utiIUserIdv.faxNumber" value="${utiIUserIdv.faxNumber}"
							id="utiIUserIdv.faxNumber" cssClass='input_w w_30' maxlength="30"/></td>
					<td class="bgc_tt short">身份证</td>
					<td class="long"><s:textfield name="utiIUserIdv.identifyNumber" value="${utiIUserIdv.identifyNumber}"
							id="utiIUserIdv.identifyNumber" cssClass='input_w w_30 dt-num' maxlength="30"/>
					</td>
				</tr>
				<tr>
					<td class="bgc_tt short">职业</td>
					<td class="long"><s:textfield name="utiIUserIdv.occupationCode" value="${utiIUserIdv.occupationCode}"
							id="utiIUserIdv.occupationCode" cssClass='input_w w_30' maxlength="1000"/></td>
					<td class="bgc_tt short">学历</td>
					<td class="long"><s:textfield name="utiIUserIdv.educationCode" value="${utiIUserIdv.educationCode}"
							id="utiIUserIdv.educationCode" cssClass='input_w w_30' maxlength="1000"/>
					</td>
				</tr>
				<tr>
					<td class="bgc_tt short">工作单位</td>
					<td class="long" colspan='3'><s:textfield name="utiIUserIdv.unit" value="${utiIUserIdv.unit}"
							id="utiIUserIdv.unit" cssClass='input_w w_p93' maxlength="1000"/></td>
					</tr>
				<tr>
					<td class="bgc_tt short">单位地址</td>
					<td class="long" colspan='3'><s:textfield name="utiIUserIdv.unitAddress" value="${utiIUserIdv.unitAddress}"
							id="utiIUserIdv.unitAddress" cssClass='input_w w_p93' maxlength="1000"/>
					</td>
				</tr>
				<tr>
					<td class="bgc_tt short">邮政编码</td>
					<td class="long"><s:textfield name="utiIUserIdv.postCode" value="${utiIUserIdv.postCode}"
							id="utiIUserIdv.postCode" cssClass='input_w w_30 dt-num' maxlength="10"/>
					</td>
					<td class="bgc_tt short">账号</td>
					<td class="long"><s:textfield name="utiIUserIdv.account" 
							id="utiIUserIdv.account" cssClass='input_w w_30' maxlength="1000" value="${utiIUserIdv.account }" />
					</td>
				</tr>
				<tr>
					<td class="bgc_tt short">客户标识</td>
					<td class="long">
						<s:if test="${utiIUserIdv.customerFlag == 0}">
							<input type="radio" name="utiIUserIdv.customerFlag" value="0" id="utiIUserIdv.customerFlag"  maxlength="1" checked="checked">临时
							<input type="radio" name="utiIUserIdv.customerFlag" value="1" id="utiIUserIdv.customerFlag"  maxlength="1">正式
						</s:if>
						<s:if test="${utiIUserIdv.customerFlag == 1}">
							<input type="radio" name="utiIUserIdv.customerFlag" value="0" id="utiIUserIdv.customerFlag"  maxlength="1">临时
							<input type="radio" name="utiIUserIdv.customerFlag" value="1" id="utiIUserIdv.customerFlag"  maxlength="1" checked="checked">正式
						</s:if>
					</td>
					<td class="bgc_tt short">印章</td>
					<td class="long"><s:textfield name="utiIUserIdv.seal" value="${utiIUserIdv.seal}"
							id="utiIUserIdv.seal" cssClass='input_w w_30' maxlength="1000"/></td>
				</tr>
				<tr>
					<td class="bgc_tt short">信息创建人</td>
					<td class="long">
						<s:textfield name="utiIUserIdv.creatorCode" 
							id="utiIUserIdv.creatorCode" cssClass='input_w w_30' maxlength="40" value="${utiIUserIdv.creatorCode}" readonly="true"/>
					</td>
					<td class="bgc_tt short">信息创建日期</td>
					<td class="long"><s:textfield name="utiIUserIdv.createDate" 
							id="utiIUserIdv.createDate" cssClass='input_w w_30' maxlength="10" value="${utiIUserIdv.createDate}" readonly="true"/>
					</td>
				</tr>
                <tr>
				<td class="bgc_tt short">备注</td>
				<td class="long" colspan='3'>
                <s:textarea rows="4" cols="25" cssStyle="wwctrl dc-chk" name="utiIUserIdv.remark" value="${utiIUserIdv.remark}"></s:textarea>		
				</td>
		    </tr>
		</s:elseif>
<!-------- 增加------------ -->	
		<s:else>
			<s:hidden name="utiIUser.userSort" value="${userSort}"/>
            <s:hidden name="utiIUser.userType" value="${userType}"/>
            <tr>
				<td class="bgc_tt short">用户代码</td>
				<td class="long""><s:textfield name="utiIUser.userCode"  value="${userCode}"
					id="userCode" cssClass='input_w w_30' maxlength="40" readonly="true" /></td>
				<td class="bgc_tt short">用户名称<font color="red">*</font></td>
                <td class="long"><input type="text" name="utiIUser.userName" maxlength="30"  id="utiIUser.userName"  class='input_w w_30 dc-chk' value="${utiIUser.userName }""/>
			</td>
				
			</tr>
			<tr>
				<td class="bgc_tt short">用户类型</td>
                <td class="long">
					    <div id="userTypeDiv2" class="selectui-indiv">
					        <div class="selectConfig">
					        <div class="codeType">StaticSelect</div>
					        </div>
                            <c:set var="checked" value="${userType}" />
			                <ce:select name="utiIUser.userType" id="userType" cssClass="selectui-input" disabled="true" value="${checked}" 
					        list="#@java.util.HashMap@{'01':'员工用户','02':'业务员用户','03':'虚拟用户','04':'合作伙伴用户','06':'企业用户','07':'个人用户','98':'临时用户','99':'其他用户'}" />
					    </div>
			    </td>
						
				<td class="bgc_tt short">归属机构</td>
				<td class="long""><s:textfield name="utiIUser.comCode" value="${comCode}"
					id="userName" cssClass='input_w w_30' maxlength="10" readonly="true"/></td>		
			</tr>
    <!-------- 个人客户信息 -->
            <s:hidden name="utiIUserIdv.comCode" value="${comCode}"></s:hidden>
            <tr>
				<td class="bgc_tt short">年龄</td>
				<td class="long"><s:textfield name="utiIUserIdv.age" value="${utiIUserIdv.age}"
						id="utiIUserIdv.age" cssClass='input_w w_30 dt-num' maxlength="10"/></td>
				<td class="bgc_tt short">出生日期</td>
				<td class="long">
					<input type="text" readonly="true" name="utiIUserIdv.birthDate" 
						id="utiIUserIdv.birthDate" class="input_w w_30 Wdate" onFocus="WdatePicker()" maxlength="20" value="${utiIUserIdv.birthDate}" />
<!--						<img src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板" id="imgBtn1" width="14" height="14" /> -->
<!--						<span class="calender-panel">-->
<!--							<div id="calContainer1" style="position: absolute;"></div>-->
<!--						</span>-->
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">家庭电话</td>
				<td class="long"><s:textfield name="utiIUserIdv.homePhone" value="${utiIUserIdv.homePhone}"
						id="utiIUserIdv.homePhone" cssClass='input_w w_30'  maxlength="30"/></td>
				<td class="bgc_tt short">办公电话</td>
				<td class="long"><s:textfield name="utiIUserIdv.officePhone" 
						id="utiIUserIdv.officePhone" cssClass='input_w w_30 ' maxlength="30" value="${utiIUserIdv.officePhone }" />
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">电子邮件</td>
				<td class="long"><s:textfield name="utiIUserIdv.email" value="${utiIUserIdv.email}"
						id="utiIUserIdv.email" cssClass='input_w w_30' maxlength="100"/></td>
				<td class="bgc_tt short">手机号码</td>
				<td class="long"><s:textfield name="utiIUserIdv.mobile" value="${utiIUserIdv.mobile}"
						id="utiIUserIdv.mobile" cssClass='input_w w_30 dt-num' maxlength="30"/>
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">邮寄地址</td>
				<td class="long" colspan='3'><s:textfield name="utiIUserIdv.postAddress" value="${utiIUserIdv.postAddress}"
						id="utiIUserIdv.postAddress" cssClass='input_w w_p93' maxlength="1000"/></td>
						</tr>
			<tr>
			
				<td class="bgc_tt short">邮政编码</td>
				<td class="long"><s:textfield name="utiIUserIdv.postCode" value="${utiIUserIdv.postCode}"
						id="utiIUserIdv.postCode" cssClass='input_w w_30 dt-num' maxlength="10"/>
				</td>
				<td class="bgc_tt short">性别</td>
				<td class="long">
					<input type="radio" name="utiIUserIdv.sex" value="0" id="utiIUserIdv.sex"  maxlength="1" checked="checked">男
		      		<input type="radio" name="utiIUserIdv.sex" value="1" id="utiIUserIdv.sex" maxlength="1" >女
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">传真号码</td>
				<td class="long"><s:textfield name="utiIUserIdv.faxNumber" value="${utiIUserIdv.faxNumber}"
						id="utiIUserIdv.faxNumber" cssClass='input_w w_30' maxlength="30"/></td>
				<td class="bgc_tt short">身份证</td>
				<td class="long"><s:textfield name="utiIUserIdv.identifyNumber" value="${utiIUserIdv.identifyNumber}"
						id="utiIUserIdv.identifyNumber" cssClass='input_w w_30 dt-num' maxlength="30"/>
				</td>
			</tr>
		
			<tr>
			<td class="bgc_tt short">健康状况</td>
				<td class="long" colspan='3'><s:textfield name="utiIUserIdv.health" 
						id="utiIUserIdv.health"  cssClass='input_w w_p93' maxlength="1000" value="${utiIUserIdv.health }" />
				</td>
					</tr>
			<tr>
				<td class="bgc_tt short">职业</td>
				<td class="long"><s:textfield name="utiIUserIdv.occupationCode" value="${utiIUserIdv.occupationCode}"
						id="utiIUserIdv.occupationCode" cssClass='input_w w_30' maxlength="1000"/></td>
				<td class="bgc_tt short">学历</td>
				<td class="long"><s:textfield name="utiIUserIdv.educationCode" value="${utiIUserIdv.educationCode}"
						id="utiIUserIdv.educationCode" cssClass='input_w w_30' maxlength="1000"/>
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">工作单位</td>
				<td class="long" colspan='3'><s:textfield name="utiIUserIdv.unit" value="${utiIUserIdv.unit}"
						id="utiIUserIdv.unit" cssClass='input_w w_p93' maxlength="1000"/></td>
			</tr>
			<tr>
			<td class="bgc_tt short">单位地址</td>
				<td class="long" colspan='3'><s:textfield name="utiIUserIdv.unitAddress" value="${utiIUserIdv.unitAddress}"
						id="utiIUserIdv.unitAddress" cssClass='input_w w_p93' maxlength="1000"/>
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">开户银行</td>
				<td class="long" colspan='3'><s:textfield name="utiIUserIdv.bank" value="${utiIUserIdv.bank}"
						id="utiIUserIdv.bank" cssClass='input_w w_p93' maxlength="1000"/></td>
				</tr>
				<tr>
				<td class="bgc_tt short">账号</td>
				<td class="long" colspan='3'><s:textfield name="utiIUserIdv.account" 
						id="utiIUserIdv.account" cssClass='input_w w_p93' maxlength="1000" value="${utiIUserIdv.account }" />
				</td>
			</tr>
			<tr>
				<td class="bgc_tt short">客户标识</td>
				<td class="long">
					<input type="radio" name="utiIUserIdv.customerFlag" value="0" id="utiIUserIdv.customerFlag"  maxlength="20" checked="checked">临时
		      		<input type="radio" name="utiIUserIdv.customerFlag" value="1" id="utiIUserIdv.customerFlag"  maxlength="20">正式
				</td>
					<td class="bgc_tt short">印章</td>
				<td class="long"><s:textfield name="utiIUserIdv.seal" value="${utiIUserIdv.seal}"
						id="utiIUserIdv.seal" cssClass='input_w w_30' maxlength="1000"/></td>
			</tr>
            <tr>
				<td class="bgc_tt short">备注</td>
				<td class="long" colspan='3'>
                <s:textarea rows="4" cols="30" cssStyle="wwctrl dc-chk" name="utiIUserIdv.remark" value="${utiIUserIdv.remark}"></s:textarea>		
				
				</td>
		    </tr>
		</s:else>
        
	</table>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr align="center" class="top">
			<c:if test="${editType=='insert' }">
				<td><input type="button" value="保存" class="button_ty"
				onclick="return addMethod()"></td>
            </c:if>
            <c:if test="${editType=='update' }">
                <td><input type="button" value="保存" class="button_ty"
				onclick="return updateMethod()"></td>
            </c:if>
			<c:if test="${editType=='view' }">	
				<td><input type="button" value="确定" class="button_ty"
				onclick="OKButton()"></td>
			</c:if>
		</tr>
	</table>
</s:form>
</div>
</div>
</body>
</html>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type='text/javascript' src='/ims/dwr/interface/Ims.js'></script>
<script language="javascript">
var tabView = new YAHOO.widget.TabView('tabdemo');
var tabFlag = new Array();
var exName = "yes";
var exOName = "yes";
tabFlag.push("taskIframe1");


  function updateMethod(){
	  if (YAHOO.quote.data.datacheck('fm')) {
	  	if(checkLen()){
		    fm.action="${ctx}/utiIUser/updateUtiIUser.do";
		    target="pages";
		    fm.submit();
		    return true;
	  	} 

 	 }else{
 		alert("界面输入有误，请核实!");
 	 }
  }
  
  function addMethod(){
	  if (YAHOO.quote.data.datacheck('fm')) {
	  //var userType1 = document.getElementById("userType1").value;
	  //alert(userType1);
	 if(checkLen()){
		  if(exName=="yes"){
				fm.action="${ctx}/utiIUser/insertUser.do";
				fm.submit();
				return true;
		  }else{
			  alert("界面输入有误，请核实!");
		  }
		}
	  }else{
		  alert("界面输入有误，请核实!");
	  }
  }
  function checkName(){
	this.userMsg = "";
	var userName = document.getElementById("utiIUser.userName").value;
	Ims.nameIsExist(userName,callBackName);
  }
  function callBackName(data){
	if(!data){
		DWRUtil.setValue("userMsg", "用户名称已经存在");
		exName = "no";
	}else{
		DWRUtil.setValue("userMsg", null);
		exName = "yes";
	}
  }
	function OKButton(){
		fm.action="${ctx}/utiIUser/prepareQueryUser.do";
		fm.submit();
	}
 
  var editType=document.getElementById("editType").value;
  if(editType=="insert" || editType=="update"){
	  //init_calendar("calContainer1","imgBtn1","utiIUserIdv.birthDate");
  }

  
  
  
</script>