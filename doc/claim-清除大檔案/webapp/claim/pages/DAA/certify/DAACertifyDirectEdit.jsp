<%--
****************************************************************************
* DESC       ：索赔资料清单修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2005-03-25
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.claim.schema.model.PrpLcertifyImg"%>
<%@page import="com.sinosoft.claim.schema.model.PrpLcertifyDirect"%>
<%@page import="com.sinosoft.claim.schema.model.PrpLcertifyCollect"%>
<%@page import="com.sinosoft.claim.schema.model.PrpLthirdParty"%>
<%@page import="com.sinosoft.claim.schema.model.PrpLpersonTrace"%>
<%@page import="com.sinosoft.claim.schema.model.PrpDcode"%>
<%@ page import="java.util.*"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<!--对title处理-->
<title><s:text name="title.certifyBeforeEdit.claimInformationList" /></title>
<%-- 索赔资料清单 --%>
<%@include file="/common/meta_js.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script src="${ctx }/pages/DAA/certify/js/DAACertifyEdit.js"></script>
<SCRIPT>
	function exit() {
		window.opener.location.reload();
	}

	/*
	 插入一条新的ThirdParty之後的处理（可选方法）
	 */

	function afterInsertCustomCertify() {
		setPrpLcertifyDirectCustomTypeSerialNo();
	}

	/*
	 删除本条WarnRegion之後的处理（可选方法）
	 */

	function afterDeleteCustomCertify(field) {
		setPrpLcertifyDirectCustomTypeSerialNo();
	}
	/**
	 设置setPrpLthirdPartySerialNo
	 */

	function setPrpLcertifyDirectCustomTypeSerialNo() {
		var count = getElementCount("prpLcertifyDirectCustomTypeSerialNo");
		if (count != 1) {
			for ( var i = 0; i < count; i++) {
				fm.prpLcertifyDirectCustomTypeSerialNo[i].value = i;
			}
		}
	}
</SCRIPT>
</head>
<body <c:if test="${nodeType=='certi' }"> onunload="exit();"</c:if> >
	<form name=fm action="${ctx }/certify/certifySavePost.do" method="post">
		<input type="hidden" name="riskCode" value="${riskCode }">
		<%--自定义的单证类型--%>
		<span style="display: none">
			<table class="common" style="display: none" id="CustomCertify_Data" cellspacing="1" cellpadding="5">
				<tbody>
					<tr>
						<td class="common" style="width: 10%" align="center">
							<input type="hidden" name="prpLcertifyDirectCustomTypeCheck">
						</td>
						<td class="common" style="width: 86%">
							<input type="text" name="prpLcertifyDirectCustomTypeSerialNo" class="readonly" style="width: 15px">.&nbsp&nbsp
							<input type="text" name="prpLcertifyDirectCustomTypeName"  class="common" style="width: 80%" align="left" value="">
							<input type="hidden" name="prpLcertifyDirectCustomTypeCode" value="">
						</td>
						<td class="common" style='width: 4%' align="center">
							<div>
								<input type="button" name="buttonCustomTypeDelete" class=smallbutton onClick="deleteRow(this,'CustomCertify')" value="-" style="cursor: hand">
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</span>
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="title.certifyBeforeEdit.claimInformationList" />
				</td>
			</tr>
			<%-- 索赔资料清单 --%>
			<tr>
				<td class="title">
					<s:text name="db.prpLcheckExt.registNo" />:
				</td>
				<%-- 报案号码 --%>
				<td class="input">
					<input type="text" name="prpLcertifyCollectBusinessNo" class="readonly" readonly="true" value="${prpLcertifyCollect.id.businessNo}">
				</td>
				<td class="title">
					<s:text name="db.prpLcheckExt.policyNo" />:
				</td>
				<%-- 保单号码 --%>
				<td class="input">
					<input type="text" name="prpLcertifyCollectPolicyNo" class="readonly" readonly="true" value="${prpLcertifyCollect.policyNo}">
				</td>
			</tr>
		</table>
		<%
			PrpLcertifyImg prpLcertifyImgDto = (PrpLcertifyImg) request.getAttribute("prpLcertifyImg");
			List<PrpLcertifyImg> prpLcertifyImgDtoList = prpLcertifyImgDto.getCertifyImgList();

			PrpLcertifyDirect prpLcertifyDirectDto = (PrpLcertifyDirect) request.getAttribute("prpLcertifyDirect");
			List<PrpLcertifyDirect> prpLcertifyDirectDtoList = prpLcertifyDirectDto.getCertifyDirectList();
			List prpLpersonTraceList = (ArrayList) request.getAttribute("prpLpersonTraceList"); //add by miaowenjun 20060419
			List imageTypeList = (ArrayList) request.getAttribute("imageTypeList");
			// add by liping 20070105 start
			int imageTypeListSize = 0;

			// add by liping 20070105 end
			List thirdPartyList = (List) request.getAttribute("thirdPartyList");
			PrpLcertifyCollect prpLcertifyCollectDto = (PrpLcertifyCollect) request.getAttribute("prpLcertifyCollect");
			int thirdPartyCount = thirdPartyList.size();// 车的数量
			int personCount = prpLpersonTraceList.size(); //人伤的数量 add by miaowenjun 20060491
			int k = 0;
			String strRiskCode = prpLcertifyCollectDto.getRiskCode();
			String relatePolicyFlag = (String) request.getAttribute("relatePolicyFlag");
			String compelPolicyFlag = (String) request.getAttribute("compelPolicyFlag");
			String compelType = "hidden";
			String businessType = "hidden";

			if ("1".equals(relatePolicyFlag)) {
				businessType = "checkbox";
				compelType = "checkbox";
			} else if ("1".equals(compelPolicyFlag)) {
				compelType = "checkbox";
			} else {
				businessType = "checkbox";
			}
		%>
		<%
			String strTempCount = "";
			//单证类别进行循环
			for (k = 1; k <= 12; k++) {
				//取得标题
				String strTitle = "";
				if (k == 1) {
					strTitle = "理賠申請";
				} else if (k == 2) {
					strTitle = "保險證(單)";
				} else if (k == 3) {
					strTitle = "事故處理證明";
				} else if (k == 4) {
					strTitle = "法院提供單證";
					continue;
				} else if (k == 5) {
					strTitle = "車損資料";
				} else if (k == 6) {
					strTitle = "第三人財物損失資料";
				} else if (k == 7) {
					strTitle = "人員傷亡資料";
				} else if (k == 8) { //0502没有盗抢险
					if (strRiskCode.equals("0502")) {
						continue;
					} else {
						strTitle = "車輛失竊資料";
					}
				} else if (k == 9) {
					strTitle = "車輛自燃資料";
					continue;
				} else if (k == 10) {
					strTitle = "駕駛證件";
				} else if (k == 11) {
					strTitle = "領取賠款證件";
					continue;
				} else if (k == 12) {
					strTitle = "車損資料";
				} else if (k == 13) {
					strTitle = "其他資料";
				}

				List commonList = new ArrayList();
				if (k == 5 || k == 12) {
					commonList = thirdPartyList;
				} else if (k == 7) {
					commonList = prpLpersonTraceList;
				} else {
					commonList.add(new Object());
				}
				String subName = "";
				for (int index = 0; index < commonList.size(); index++) {
					if (k == 5 || k == 12) {
						//if(k==5){
						//subName = "資料";
						//strTitle = "保車資料";
						//}else{
						//subName = "車損資料";
						//strTitle = "財車資料";
						//}
						subName = "資料";
						PrpLthirdParty prpLthirdPartyDto = (PrpLthirdParty) commonList.get(index);
						if (k == 5 && prpLthirdPartyDto.getId().getSerialNo() != 1) {
							continue;
						}
						if (k == 12 && prpLthirdPartyDto.getId().getSerialNo() == 1) {
							continue;
						}
						if (prpLthirdPartyDto.getId().getSerialNo() == 1) {
							strTitle = "保車" + subName;
						} else {
							strTitle = "財車" + subName;
						}
						strTitle = strTitle + "(" + prpLthirdPartyDto.getLicenseNo() + ")";
					} else if (k == 7) {
						PrpLpersonTrace prplpersontraceDto = (PrpLpersonTrace) commonList.get(index);
						strTitle = "人員傷亡資料";
						strTitle = strTitle + "(" + prplpersontraceDto.getPersonName() + ")";
					}
		%>
		<table border="0" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class="subformtitle" style="width: 10%" colspan="1">
					<s:text name="certainLoss.prpLcertifyCollect.requireSign" />
				</td>
				<%-- 需要标志 --%>
				<td class="centertitle" style="width: 100%" colspan="5"><%=strTitle%></td>
			</tr>
			<tr>
				<%
					if ("1".equals(relatePolicyFlag)) {
				%>
				<td class="subformtitle" style="width: 10%">
					<s:text name="certainLoss.prpLcertifyCollect.commerceForce3" />
				</td>
				<%-- 商业&nbsp;强三 --%>
				<%
					} else if ("1".equals(compelPolicyFlag)) {
				%>
				<td class="subformtitle" style="width: 10%">
					<s:text name="certainLoss.prpLcertifyCollect.force" />
				</td>
				<%-- 强  制 --%>
				<%
					} else {
				%>
				<td class="subformtitle" style="width: 10%">
					<s:text name="certainLoss.prpLcertifyCollect.commerce" />
				</td>
				<%-- 商  业 --%>
				<%
					}
				%>
				<td class="subformtitle" style="width: 90%">
					<s:text name="certainLoss.prpLcertifyCollect.billType" />
				</td>
				<%-- 清单类型 --%>
			</tr>
			<input type="hidden" name="certifyType">
			<%
				if (imageTypeList != null)
							imageTypeListSize = imageTypeList.size();
						for (int i = 0; i < imageTypeListSize; i++) {
							PrpDcode prpDcodeDto = (PrpDcode) imageTypeList.get(i);
							strTempCount = String.valueOf(k);
							if (strTempCount.length() < 2) {
								strTempCount = "0" + strTempCount;
							}
							if (prpDcodeDto.getId().getCodeCode().substring(0, 2).equals(strTempCount)) {
								//是否需要上传CheckBox
								String requireUploadFlag = "";
								String compelRequireUploadFlag = "";
								//是否把checkbox变成不可用
								String requireDisabledFlag = "";
								//取得隐含域的值，需要上传时为1，否则为0
								String requireTxt = "0";
								//取得code的值
								String codeCode = prpDcodeDto.getId().getCodeCode();
								//商三和强三的标志
								String compleChoiceFlag = "0";
								String certifyDirectFlag = "0";
								//是否需要上传的标志 从PrpLcertifyDirect取得
								if (prpLcertifyDirectDtoList != null && prpLcertifyDirectDtoList.size() > 0) {
									for (int j = 0; j < prpLcertifyDirectDtoList.size(); j++) {
										PrpLcertifyDirect prpLcertifyDirectDtoTemp = (PrpLcertifyDirect) prpLcertifyDirectDtoList.get(j);
										if ("1".equals(prpLcertifyDirectDtoTemp.getBusinessFlag()) && prpDcodeDto.getId().getCodeCode().equals(prpLcertifyDirectDtoTemp.getTypeCode()) && String.valueOf(index + 1).equals(prpLcertifyDirectDtoTemp.getId().getLossItemCode())) {
											requireUploadFlag = "checked";
											requireTxt = prpDcodeDto.getId().getCodeCode();
											certifyDirectFlag = "1";
										}
										if ("1".equals(prpLcertifyDirectDtoTemp.getCompelFlag()) && prpDcodeDto.getId().getCodeCode().equals(prpLcertifyDirectDtoTemp.getTypeCode()) && String.valueOf(index + 1).equals(prpLcertifyDirectDtoTemp.getId().getLossItemCode())) {
											compelRequireUploadFlag = "checked";
											requireTxt = prpDcodeDto.getId().getCodeCode();
											compleChoiceFlag = "1";
											break;
										}
									}
								}
								if (prpLcertifyImgDtoList != null && prpLcertifyImgDtoList.size() > 0) {
									for (int x = 0; x < prpLcertifyImgDtoList.size(); x++) {
										PrpLcertifyImg prpLcertifyImgDtoTemp = (PrpLcertifyImg) prpLcertifyImgDtoList.get(x);
										if (prpDcodeDto.getId().getCodeCode().equals(prpLcertifyImgDtoTemp.getTypeCode()) && String.valueOf(index + 1).equals(prpLcertifyImgDtoTemp.getId().getLossItemCode())) {
											requireDisabledFlag = "disabled";
											break;
										}
									}
								}
			%>
			<tr>
				<td class="input" style="width: 10%" align="center">
					<input type="<%=businessType%>" name="prpLcertifyDirect" <%=requireDisabledFlag%> <%=requireUploadFlag%> onClick="return directCodeChange(this);">
					<input type="<%=compelType%>" name="compelCertifyDirect" <%=requireDisabledFlag%> <%=compelRequireUploadFlag%> onClick="return compelDirectFlag(this);">
					<input type="hidden" name="compleChoiceFlag" value="<%=compleChoiceFlag%>">
					<input type="hidden" name="certifyDirectFlag" value="<%=certifyDirectFlag%>">
					<input type="hidden" name="prpLcertifyDirectLossItemCode" value="<%=index + 1%>">
					<input type="hidden" name="prpLcertifyDirectCode" value="<%=requireTxt%>">
					<input type="hidden" name="code" value="<%=codeCode%>">
				</td>
				<td class="input" style="width: 90%">
					<input type="text" name="prpLcertifyDirectTypeName" class="readonly" readonly="true" value="<%=prpDcodeDto.getCodeCName()%>">
				</td>
			</tr>
			<%
				}
						}
			%>
			<%
				}// 三者车
			%>
		</table>
		<%
			}
		%>
		<table border="0" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class="centertitle" style="width: 100%" colspan="3">
					<s:text name="certainLoss.prpLcertifyCollect.otherInfo" />
				</td>
				<%-- 其他资料 --%>
				</td>
			</tr>
		</table>
		<table id="CustomCertify" border="0" cellpadding="5" cellspacing="1" class="common">
			<thead>
				<tr>
					<td class="subformtitle" style="width: 10%">
						<s:text name="certainLoss.prpLcertifyCollect.requireSign" />
						<%-- 需要标志 --%>
					</td>
					<td class="subformtitle" style="width: 90%" colspan="2">
						<s:text name="certainLoss.prpLcertifyCollect.billType" />
						<%-- 清单类型 --%>
					</td>
				</tr>
			</thead>
			<tfoot>
				<tr class="common">
					<td colspan=2 align="left">
						<s:text name="prompt.certify.addRemove" />
						<%-- (按"+"號鍵增加訊息，按"-"號鍵刪除訊息) --%>
					</td>
					<td class="title" align="right" style="width: 4%">
						<div align="center">
							<input type="button" value="+" class=smallbutton onClick="insertRow('CustomCertify')" name="buttonCustomTypeInsert" style="cursor: hand">
						</div>
					</td>
				</tr>
			</tfoot>
			<tbody>
				<%
					//是否需要上传的标志 从PrpLcertifyDirect取得
					int x = 0;
					String deleteDisabledFlag = "";
					if (prpLcertifyDirectDtoList != null && prpLcertifyDirectDtoList.size() > 0) {
						for (int j = 0; j < prpLcertifyDirectDtoList.size(); j++) {
							prpLcertifyDirectDto = (PrpLcertifyDirect) prpLcertifyDirectDtoList.get(j);
							if ("99".equals(prpLcertifyDirectDto.getTypeCode().substring(0, 2))) {
								x++;
								deleteDisabledFlag = "";
								for (int m = 0; m < prpLcertifyImgDtoList.size(); m++) {
									prpLcertifyImgDto = (PrpLcertifyImg) prpLcertifyImgDtoList.get(m);
									if (prpLcertifyImgDto.getTypeCode().equals(prpLcertifyDirectDto.getTypeCode())) {
										deleteDisabledFlag = "disabled";
										break;
									}
								}
				%>
				<tr>
					<td class="input" style="width: 10%" align="center" />
					<td class="input" style="width: 86%">
						<input type="text" name="prpLcertifyDirectCustomTypeSerialNo" class="readonly" value="<%=x%>" style="width: 15px">
						.&nbsp&nbsp
						<input type="text" align="left"  style="width: 80%" name="prpLcertifyDirectCustomTypeName" value="<%=prpLcertifyDirectDto.getTypeName()%>" class="common" >
						<input type="hidden" name="prpLcertifyDirectCustomTypeCode" value="<%=prpLcertifyDirectDto.getTypeCode()%>">
					</td>
					<td class="common" style='width: 4%' align="center">
						<div>
							<input type="button" <%=deleteDisabledFlag%> name="buttonCustomTypeDelete" class=smallbutton onClick="deleteRow(this,'CustomCertify')" value="-" style="cursor: hand">
						</div>
					</td>
				</tr>
				<%
					}
						}
					}
				%>
			</tbody>
		</table>
		<table cellpadding="0" cellspacing="0" class="common">
			<tr>
				<td class="button" align="center">
					<!--提交-->
					<input type="button" name=buttonSave class='button' value="保存" onClick="return saveCertifyDirect();">
					&nbsp;&nbsp;
					<!--關閉-->
					<input type="button" name=buttonClose class='button' value="<s:text name='button.close.value' />" onClick="javascript:window.close();">
				</td>
			</tr>
		</table>
		<input type="hidden" name="nodeType" value="CertifDirect">
		<input type="hidden" name="thirdPartyCount" value="<%=thirdPartyCount%>">
		<input type="hidden" name="personCount" value="<%=personCount%>">
		<input type="hidden" name="classCount" value="<%=k - 1%>">
		<input type="hidden" name="imageTypeListSize" value="<%=imageTypeListSize%>">
	</form>
</body>
</html>
