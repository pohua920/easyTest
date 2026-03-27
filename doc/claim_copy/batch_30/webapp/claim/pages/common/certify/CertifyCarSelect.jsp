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
    if(fm.carFlag.length>0){
      for(i=0;i<fm.carFlag.length;i++){
        if(fm.carFlag[i].checked==true){
          ref = ref+fm.carFlag[i].value+",";
        }
      }
    } else {
      if(fm.carFlag.checked==true){
        ref=fm.carFlag.value+",";
      }
    }
    fm.carLossFlag.value = ref;
    fm.submit(); //提交
  }
</script>
</head>
<body  onload="initPage();">
<form name="fm" action="${ctx }/certifyBeforeEdit.do"  method="post" onsubmit="return validateForm(this);">

    <table  border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=2 class="formtitle">
					<s:text name="certify.chooseVehicle" />
				</td>
			</tr>
			<%--选择车辆标的--%>
      <tr>
				<td class='title2'>
					<s:text name="certify.vehicleMark" />:
				</td>
				<%--车辆标的--%>
        <td class='input2' >
          <input type="hidden" name="carLossFlag">
          <c:if test="${registDto.prpLthirdPartyList!=null}">
          	<c:forEach items="${registDto.prpLthirdPartyDtoList}" var="prpLregistTemp" >
          		<input type="checkbox" name="carFlag" value="${prpLregistTemp.licenseNo}">${prpLregistTemp.licenseNo}&nbsp;&nbsp;
          	</c:forEach>
          </c:if>
        </td>
         <input type="hidden" name="caseFlag" value="${param.caseFlag }">
        <input type="hidden" name="RegistNo" class="common" value="${param.RegistNo }">
        </tr>
			<tr>
				<td class='button' align="center" colspan="2">
          <input type="button" class='button' value="<s:text name='button.next.value' />" name="buttonNext" onclick="submitForm();">
        </td>
      </tr>
    </table>
    <input type="hidden" name="editType" value="ADD">
  </form>
</body>
<c:if test="${strCompensateFlag!=null}">
<SCRIPT LANGUAGE="JavaScript">
<!--
  alert("车 "+compensateFlag+" 已出赔款计算书,不能再收集单证");
//-->
</SCRIPT>
</c:if>
</html>



