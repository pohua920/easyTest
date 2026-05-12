<%--
****************************************************************************
* DESC       ：索赔资料清单打印页面
* AUTHOR     ：理赔组
* CREATEDATE ：2005-03-25
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.sinosoft.claim.dto.custom.*" %>
<%@ page import="com.sinosoft.claim.dto.domain.*" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
  <head>
    <!--对title处理-->
<title><s:text name="title.certifyBeforeEdit.claimInformationList" /></title>
<%--索赔资料清单--%>
    <%-- 页面样式  --%>
    <%@include file="/common/meta_js.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
    <script src="${ctx }/pages/common/certify/js/CertifyEdit.js"></script>
  </head>


<body >
  <form name=fm action="${ctx }/certifySave.do" method="post">
  <table border="0" align="center" cellpadding="5" cellspacing="1"  class="common" >
			<tr>
				<td class="formtitle" colspan=4>
					<s:text name="certify.claimInformationList" />
				</td>
			</tr>
			<%--索赔资料清单--%>
    <tr>
				<td class="title">
					<s:text name="db.prpLcheckExt.registNo" />:
				</td>
				<%--报案号码--%>
      <td class="input"  >
        <input type="text" name="prpLcertifyCollectBusinessNo" class="readonly" readonly="true"  value="${prpLcertifyCollect.id.businessNo}">
      </td>
				<td class="title">
					<s:text name="db.prpLcheckExt.policyNo" />:
				</td>
				<%--保单号码--%>
      <td class="input"  >
        <input type="text" name="prpLcertifyCollectPolicyNo" class="readonly" readonly="true"  value="${prpLcertifyCollect.policyNo}">
      </td>
    </tr>
  </table>
    <%

      PrpLcertifyImgDto prpLcertifyImgDto = (PrpLcertifyImgDto)request.getAttribute("prpLcertifyImgDto");
      List prpLcertifyImgDtoList =  (ArrayList)prpLcertifyImgDto.getCertifyImgList();

      PrpLcertifyDirectDto prpLcertifyDirectDto = (PrpLcertifyDirectDto)request.getAttribute("prpLcertifyDirectDto");
      List prpLcertifyDirectDtoList =  (ArrayList)prpLcertifyDirectDto.getCertifyDirectList();

      List imageTypeList = (ArrayList)request.getAttribute("imageTypeList");
      ArrayList thirdPartyList = (ArrayList)request.getAttribute("thirdPartyList");
      PrpLcertifyCollectDto prpLcertifyCollectDto = (PrpLcertifyCollectDto)request.getAttribute("prpLcertifyCollectDto");
      int thirdPartyCount = thirdPartyList.size();// 车的数量
%>

  <table cellpadding="5" cellspacing="1" border="0"  class="common" >

    <%
          //是否需要上传的标志 从PrpLcertifyDirect取得
          UICodeAction uiCodeAction = new UICodeAction();
          if(prpLcertifyDirectDtoList!=null&&prpLcertifyDirectDtoList.size()>0){
            for(int j=0;j < prpLcertifyDirectDtoList.size(); j++){
              PrpLcertifyDirectDto prpLcertifyDirectDtoTemp = (PrpLcertifyDirectDto)prpLcertifyDirectDtoList.get(j);
              String lossItemName = "";
              for(int i=0;i < thirdPartyList.size(); i++){
                PrpLthirdPartyDto prpLthirdPartyDto = (PrpLthirdPartyDto)thirdPartyList.get(i);
                if(String.valueOf(prpLthirdPartyDto.getSerialNo()).equals(prpLcertifyDirectDtoTemp.getLossItemCode())){
                  lossItemName = "  车牌号码: "+ prpLthirdPartyDto.getLicenseNo();
                }
              }
              String typeName= uiCodeAction.translateCodeCode("ImageType",prpLcertifyDirectDtoTemp.getTypeCode(),true);

    %>

        <tr>
				<td class="input">
					<input type="text" name="prpLcertifyDirectTypeName" class="readonly" readonly="true" value="<%=typeName%><%=lossItemName%>">
				</td>
        </tr>
    <%
            }
          }
    %>


  </table>

  <table cellpadding="0" cellspacing="0" width="100%" class="common">
    <tr>
      <td class=button style="width:33%" align="center">
        <!--保存按钮-->
        <input type="button" name=buttonSave class='button' value="<s:text name='button.save.value' />" onclick="return saveCertifyDirect();">
      </td>
      <td class=button style="width:34%" align="center">
        <!--保存按钮-->
        <input type="button" name=buttonSave class='button' value="<s:text name='button.print.value' />" onclick="javascript:window.print();">
      </td>
      <td class=button style="width:33%" align="center">
        <!--保存按钮-->
        <input type="button" name=buttonSave class='button' value="<s:text name='button.close.value' />" onclick="javascript:window.close();">
      </td>
    </tr>
  </table>

  <input type="hidden" name="nodeType" value="CertifDirect">
  <input type="hidden" name="thirdPartyCount" value="<%= thirdPartyCount %>">
  <input type="hidden" name="classCount" value="<%= k-1 %>">
  </form>
</body>
</html>
