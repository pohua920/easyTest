<%--
****************************************************************************
* DESC       ：录入单证前输入报案号页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-07-05
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<title><s:text name="title.certainLossBeforeEdit.entryDocument" /></title>
<%--录入单证--%>
  <%-- 页面样式  --%>
  <link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
  
<script language="javascript">
	function submitForm() {
    fm.buttonNext.disabled = true;
    var ref="";
    for(i=0;i<fm.certifyFlag.length;i++){
      if(fm.certifyFlag[i].checked==true){
        ref = ref+"1";
      } else {
        ref = ref+"0";
      }
    }
    fm.caseFlag.value = ref;
    fm.submit(); //提交
  }
</script>
</head>

<body  onload="initPage();">
<form name="fm" action="${ctx }/certifyBeforeEdit.do"  method="post"  onsubmit="return validateForm(this);">

    <table  border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=2 class="formtitle">
					<s:text name="certify.inputReportNo(registration documents)" />
				</td>
			</tr>
			<%--输入报案号（单证登记）--%>
      <tr>
				<td class='title2' align="center">
					<s:text name="prpLregist.registNo" />：
				</td>
				<%--报案号--%>
        <td class='input2' >
          <input type=text name="RegistNo" class="readonly" readonly value="${param.RegistNo }">
        </td>

      </tr>

      <tr>
				<td class='title2' align="center">
					<s:text name="db.prpLcaseno.certiType" />：
				</td>
				<%--单证类型--%>
        <td class='input2' >
          <input type="hidden" name="caseFlag">
					<input type="checkbox" name="certifyFlag" value="0">
					<s:text name="certify.chesunAccident" />
					<%--车损事故--%>
					<input type="checkbox" name="certifyFlag" value="1">
					<s:text name="certify.injuryAccident" />
					<%--人伤事故--%>
					<input type="checkbox" name="certifyFlag" value="2">
					<s:text name="certify.damageIncident" />
					<%--物损事故--%>
					<input type="checkbox" name="certifyFlag" value="3">
					<s:text name="certify.wholeVehicle" />
					<%--全车盗抢--%>
        </td>
      </tr>
      <tr>
        <td class='button'   align="center" colspan="2">
          <input type="button" class='button' value="<s:text name='button.next.value' />" name="buttonNext" onclick="submitForm();">
        </td>
      </tr>
      <%--
      <tr>
        <td class='common'  align="center">车辆标的:</td>
        <td class='input' >
          <input type="hidden" name="carLossFlag">
          <logic:notEmpty  name="prpLregistDto"  property="registList">
          <logic:iterate id="registList1"  name="prpLregistDto"  property="registList">
            <input type="checkbox" name="carFlag" value="<bean:write name='registList1' property='licenseNo'/>"><bean:write name="registList1" property="licenseNo"/>
          </logic:iterate>
          </logic:notEmpty>
        </td>
      </tr>
      --%>
    </table>
    <input type="hidden" name="editType" value="SELECTCAR">
  </form>
</body>
</html>



