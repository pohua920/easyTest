<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.sinosoft.claim.schema.model.PrpLcertifyDirect"%>
<%@page import="com.sinosoft.claim.schema.model.PrpLthirdParty"%>
<%@page import="com.sinosoft.claim.schema.model.PrpLpersonTrace"%>
<%@page import="com.sinosoft.claim.schema.model.PrpDcode"%>
<%@page import="com.sinosoft.claim.schema.model.PrpLcertifyImg"%>
<%
	String strRiskCode = (String) request.getAttribute("riskCode");
	List thirdPartyList = (List) request.getAttribute("thirdPartyList");
	List prpLpersonTraceList = (List) request.getAttribute("prpLpersonTraceList");
	String relatePolicyFlag = (String) request.getAttribute("relatePolicyFlag");
	String compelPolicyFlag = (String) request.getAttribute("compelPolicyFlag");
	int imageTypeListSize = (Integer) request.getAttribute("imageTypeListSize");
	PrpLcertifyImg prpLcertifyImg = (PrpLcertifyImg) request.getAttribute("prpLcertifyImg");
	List<PrpLcertifyImg> prpLcertifyImgList = (ArrayList<PrpLcertifyImg>) prpLcertifyImg.getCertifyImgList();
	List imageTypeList = (List) request.getAttribute("imageTypeList");
	PrpLcertifyDirect prpLcertifyDirect = (PrpLcertifyDirect) request.getAttribute("prpLcertifyDirect");
	List<PrpLcertifyDirect> prpLcertifyDirectList = (ArrayList<PrpLcertifyDirect>) prpLcertifyDirect.getCertifyDirectList();
	String strTempCount = "";
	for (int k = 1; k <= 12; k++) {
		//取得标题
		String strTitle = "";
		if (k == 1) {
			strTitle = "索賠申請";
		} else if (k == 2) {
			strTitle = "保險單證";
		} else if (k == 3) {
			strTitle = "事故處理單證";
		} else if (k == 4) {
			strTitle = "法院提供單證";
			continue;
		} else if (k == 5) {
			strTitle = "車損資料";
		} else if (k == 6) {
			strTitle = "財産損失資料";
		} else if (k == 7) {
			strTitle = "人傷資料";
		} else if (k == 8) { //0502没有盗抢险
			if (strRiskCode.equals("0502")) {
				continue;
			} else {
				strTitle = "車輛盜搶資料";
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
		}
		List commonList = new ArrayList();
		List commonListTemp = new ArrayList();
		if (k == 5 || k == 12) {
			commonList = thirdPartyList;
		} else if (k == 7) {
			commonList = prpLpersonTraceList;
		} else {
			commonList.add(new Object());
			commonListTemp = thirdPartyList;
		}
		String subName = "";
		int no = 0;
		String name = "";
		//if(imageTypeList !=null) imageTypeListSize = imageTypeList.size();

		boolean flag = false;
		for (int i = 0; i < prpLcertifyDirectList.size(); i++) {
			PrpLcertifyDirect prpLcertifyDirectDtoFlag = (PrpLcertifyDirect) prpLcertifyDirectList.get(i);
			if ((String.valueOf(k)).length() == 1) {
				if (prpLcertifyDirectDtoFlag.getTypeCode().substring(1, 2).equals(String.valueOf(k))) {
					flag = true;
				}
			} else {
				if (prpLcertifyDirectDtoFlag.getTypeCode().substring(0, 2).equals(String.valueOf(k))) {
					flag = true;
				}
			}
		}
		if (!flag) {
			continue;
		}
		//add end by miaowenjun 20060420
		//add by lixiang start 2007-8-29
		//reasion:第二辆三者车，不能上传单证类型为第1个的内容，在applet程序中，不显示此单证上传的框。具体就是没有拼strImageList 
		//        修改方式，把int aa=0这句话从
		int aa = 0; //问题by lixiang ,这个aa是做什么用的？没看懂，而且後面第3辆以上的三者车会有问题，很奇怪：
		//暂时认为1辆车，如果循环到第2辆车的话，aa++,然後判断不同的话，清除上一个标题合並的内容，但是算法上目前有bug
		//比如，index为0时，表示第一辆车，
		//index 为1时，表示第2辆车，但是。。。 
		//index的含义，是三者车的lossitemcode??暂时这样理解，具体不知道写程序人的意思 by lixiang  
		//add by lixiang end 2007-8-29
		for (int index = 0; index < commonList.size(); index++) {
			if (k == 5 || k == 12) {
				//if (k == 5) {
				//subName = "資料";
				//} else {
				//subName = "車損資料";
				//}
				subName = "資料";
				PrpLthirdParty prpLthirdPartyDto = (PrpLthirdParty) commonList.get(index);
				no = prpLthirdPartyDto.getId().getSerialNo();
				name = prpLthirdPartyDto.getLicenseNo();
				if (k == 5 && no != 1) {
					continue;
				}
				if (k == 12 && no == 1) {
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
				no = prplpersontraceDto.getId().getPersonNo();
				name = prplpersontraceDto.getPersonName();
				strTitle = "人傷亡資料";
				strTitle = strTitle + "(" + prplpersontraceDto.getPersonName() + ")";
			} else {
				PrpLthirdParty prpLthirdPartyDto = (PrpLthirdParty) commonListTemp.get(index);
				no = 0;
				name = "claim";
			}
			String strImageList = "";
			//delete by lixiang start 2007-8-29
			//reasion:第二辆三者车，不能上传单证类型为第1个的内容，在applet程序中，不显示此单证上传的框。具体就是没有拼strImageList 
			//        修改方式，把int aa=0这句话从此处移到第一个for循环处。
			//int aa = 0;
			//delete by lixiang start 2007-8-29
%>
<table cellpadding="5" cellspacing="1" border="0" class="common">
	<tr>
		<td class="input" colspan="3">
			<table cellpadding="5" cellspacing="1" border="0" class="common">
				<tr>
					<td class="subformtitle" style="width: 10%">
						<%-- 需要标志--%>
						<s:text name="certainLoss.prpLcertifyCollect.requireSign" />
					</td>
					<td class="centertitle" style="width: 100%" colspan="5"><%=strTitle%></td>
				</tr>
				<tr>
					<%
						if ("1".equals(relatePolicyFlag)) {
					%>
					<td class="subformtitle" style="width: 10%">
						<%-- 商业&nbsp;强三--%>
						<s:text name="certainLoss.prpLcertifyCollect.commerceForce3" />
					</td>
					<%
						} else if ("1".equals(compelPolicyFlag)) {
					%>
					<td class="subformtitle" style="width: 10%">
						<%--强制--%>
						<s:text name="certainLoss.prpLcertifyCollect.force" />
					</td>
					<%
						} else {
					%>
					<td class="subformtitle" style="width: 10%">
						<%-- 商业--%>
						<s:text name="certainLoss.prpLcertifyCollect.commerce" />
					</td>
					<%
						}
					%>
					<td class="subformtitle" style="width: 60%">
						<%-- 清单类型--%>
						<s:text name="certainLoss.prpLcertifyCollect.billType" />
					</td>
					<td class="subformtitle" style="width: 10%">
						<%-- 是否上传--%>
						<s:text name="certainLoss.prpLcertifyCollect.isOnload" />
					</td>
					<td class="subformtitle" style="width: 10%">
						<%-- 查看--%>
						<s:text name="certainLoss.prpLcertifyCollect.read" />
					</td>
					<td class="subformtitle" style="width: 10%">
						<%-- 上传--%>
						<s:text name="certainLoss.prpLcertifyCollect.onload" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="input" style="width: 80%">
			<table cellpadding="5" cellspacing="1" border="0" class="common" style="width: 100%">
				<%
					for (int i = 0; i < imageTypeListSize; i++) {
								PrpDcode prpDcodeDto = (PrpDcode) imageTypeList.get(i);

								strTempCount = String.valueOf(k);
								if (strTempCount.length() < 2) {
									strTempCount = "0" + strTempCount;
								}
								if (prpDcodeDto.getId().getCodeCode().substring(0, 2).equals(strTempCount)) { //第一位为1代表基本资料
									//是否已经上传的标志 从PrpLcertifyImg取得
									String alreadyUploadFlag = "";

									if (prpLcertifyImgList != null && prpLcertifyImgList.size() > 0) {
										for (int j = 0; j < prpLcertifyImgList.size(); j++) {
											PrpLcertifyImg prpLcertifyImgDtoTemp = (PrpLcertifyImg) prpLcertifyImgList.get(j);

											if (prpLcertifyImgDtoTemp.getTypeCode().equals(prpDcodeDto.getId().getCodeCode()) && prpLcertifyImgDtoTemp.getId().getLossItemCode().equals(String.valueOf(no))) {

												alreadyUploadFlag = "checked";
												//System.out.println("----此单证已经上传-------"+prpDcodeDto.getCodeCode());
												break;
											}
										}
									}
									String requireUploadFlag = "";
									String compelRequireUploadFlag = "";
									//System.out.println("aa"+aa+"|index"+index);
									if (aa == index) {
									} else {
										//System.out.println("aa"+aa+"|index清空"+index);
										strImageList = "";
										aa++;
									}
									//是否需要上传的标志 从PrpLcertifyDirect取得
									if (prpLcertifyDirectList != null && prpLcertifyDirectList.size() > 0) {
										for (int j = 0; j < prpLcertifyDirectList.size(); j++) {
											PrpLcertifyDirect prpLcertifyDirectDtoTemp = (PrpLcertifyDirect) prpLcertifyDirectList.get(j);
											if (prpLcertifyDirectDtoTemp.getTypeCode().equals(prpDcodeDto.getId().getCodeCode()) && prpLcertifyDirectDtoTemp.getId().getLossItemCode().equals(String.valueOf(index + 1))) {
												if ("1".equals(prpLcertifyDirectDtoTemp.getBusinessFlag()) && "1".equals(prpLcertifyDirectDtoTemp.getCompelFlag())) {
													requireUploadFlag = "checked";
													compelRequireUploadFlag = "checked";
													strImageList += prpDcodeDto.getId().getCodeCode() + "@@" + prpDcodeDto.getCodeCName() + "|";
												} else {
													if ("1".equals(prpLcertifyDirectDtoTemp.getBusinessFlag())) {
														requireUploadFlag = "checked";
														strImageList += prpDcodeDto.getId().getCodeCode() + "@@" + prpDcodeDto.getCodeCName() + "|";
													}
													if ("1".equals(prpLcertifyDirectDtoTemp.getCompelFlag())) {
														compelRequireUploadFlag = "checked";
														strImageList += prpDcodeDto.getId().getCodeCode() + "@@" + prpDcodeDto.getCodeCName() + "|";
														break;
													}
												}
											}
										}
									}
									if ("checked".equals(requireUploadFlag) || "checked".equals(compelRequireUploadFlag)) {
				%>
				<tr>
					<td class="input" style="width: 5%">
						<input type="${businessType}" name="prpLcertifyDirect" disabled <%=requireUploadFlag%>>
					</td>
					<td class="input" style="width: 5%">
						<input type="${compelType }" name="compelCertifyDirect" disabled <%=compelRequireUploadFlag%>>
					</td>
					<td class="input" style="width: 60%">
						<input type="text" name="prpLcertifyDirectTypeName" class="readonly" readonly="true" value="<%=prpDcodeDto.getCodeCName()%>">
					</td>
					<td class="input" style="width: 10%">
						<input type="checkbox" name="prpLcertifyDirectUploadFlag" disabled <%=alreadyUploadFlag%>>
					</td>
				</tr>
				<%
					}
								}
							}
				%>
				<%
					String isCase = (String) request.getAttribute("isCase");
				%>
			</table>
		</td>
		<td class="input" style="width: 10%">
			<%--
		 <input class=button type="button" name="buttonView" value="查看" onclick="doViewFile('${editType }','<%=k%>','<%=no%>','<%=isCase%>' );return false;" />
	 --%>
			<input class="button" type="button" name="buttonUpload" value="查看" onclick="doUploadFile('show')">
			<!--<input class=button type="button" name="buttonView" value="查看1"-->
			<!--				onclick="doNewUploadFile('99');return false;">-->
		</td>
		<td class="input" style="width: 10%">
			<%--
		 <input class=button type="button" name="buttonUpload" value="上传" onclick="doUploadFile('<%=k%>','<%=no%>','<%=name%>','<%=strTitle%>','<%=strImageList%>');return false;" />
		  --%>
			<input class="button" type="button" name="buttonUpload" value="上傳" onclick="doUploadFile('upload')" />
		</td>
	</tr>
</table>
<%
	}
	}
%>
<%
	boolean isHavedCustomCertify = false;
	for (int j = 0; j < prpLcertifyDirectList.size(); j++) {
		prpLcertifyDirect = (PrpLcertifyDirect) prpLcertifyDirectList.get(j);
		if ("99".equals(prpLcertifyDirect.getTypeCode().substring(0, 2))) {
			isHavedCustomCertify = true;
			break;
		}
	}
	if (isHavedCustomCertify) {
%>
<table cellpadding="5" cellspacing="1" border="0" class="common">
	<tr>
		<td class="input" style="width: 100%" colspan="3">
			<table cellpadding="5" cellspacing="1" border="0" class="common">
				<tr>
					<td class="centertitle" style="width: 100%" colspan="6">
						<%-- 其他资料--%>
						<s:text name="certainLoss.prpLcertifyCollect.otherInfo" />
					</td>
				</tr>
				<tr>
					<td class="subformtitle" style="width: 10%">
						<%-- 需要标志--%>
						<s:text name="certainLoss.prpLcertifyCollect.requireSign" />
					</td>
					<td class="subformtitle" style="width: 60%">
						<%-- 清单类型--%>
						<s:text name="certainLoss.prpLcertifyCollect.billType" />
					</td>
					<td class="subformtitle" style="width: 10%">
						<%-- 是否上传--%>
						<s:text name="certainLoss.prpLcertifyCollect.isOnload" />
					</td>
					<td class="subformtitle" style="width: 10%">
						<%-- 查看--%>
						<s:text name="certainLoss.prpLcertifyCollect.read" />
					</td>
					<td class="subformtitle" style="width: 10%">
						<%-- 上传--%>
						<s:text name="certainLoss.prpLcertifyCollect.onload" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="input" style="width: 80%">
			<table cellpadding="0" cellspacing="1" border="0" class="title" style="width: 100%">
				<%
					String alreadyUploadFlag = "";
						String strImageList = "";
						if (prpLcertifyDirectList != null) {
							for (int j = 0; j < prpLcertifyDirectList.size(); j++) {
								prpLcertifyDirect = (PrpLcertifyDirect) prpLcertifyDirectList.get(j);
								if ("99".equals(prpLcertifyDirect.getTypeCode().substring(0, 2))) {
									alreadyUploadFlag = "";
									strImageList += prpLcertifyDirect.getTypeCode() + "@@" + prpLcertifyDirect.getTypeName() + "|";
									if (prpLcertifyImgList != null && prpLcertifyImgList.size() > 0) {
										for (int k = 0; k < prpLcertifyImgList.size(); k++) {
											PrpLcertifyImg prpLcertifyImgDtoTemp = (PrpLcertifyImg) prpLcertifyImgList.get(k);
											if (prpLcertifyImgDtoTemp.getTypeCode().equals(prpLcertifyDirect.getTypeCode())) {
												alreadyUploadFlag = "checked";
												break;
											}
										}
									}
				%>
				<tr>
					<td class="input" style="width: 10%" align="center" />
					<td class="input" style="width: 60%">
						<input type="text" class="readonly" readonly="true" align="left" style="width: 80%" name="prpLcertifyDirectCustomTypeName" value="<%=prpLcertifyDirect.getTypeName()%>">
						<input type="hidden" name="prpLcertifyDirectCustomTypeCode" value="<%=prpLcertifyDirect.getTypeCode()%>">
					</td>
					<td class="input" style="width: 10%">
						<input type="checkbox" name="prpLcertifyDirectUploadFlag" disabled <%=alreadyUploadFlag%>>
					</td>
				</tr>
				<%
					}
							}
						}
				%>
			</table>
		</td>
		<td class="input" style="width: 10%">
			<input class=button type="button" name="buttonView" value="<s:text name='button.view.value' />" onclick="doUploadFile('show');return false;">
		</td>
		<%-- 查看 --%>
		<td class="input" style="width: 10%">
			<input class=button type="button" name="buttonUpload" value="<s:text name='button.upload.value' />" onclick="doUploadFile('upload')">
		</td>
		<%-- 上传 --%>
	</tr>
</table>
<%
	} //自定义类型显示完毕
%>
<input type="hidden" name="imageTypeListSize" value="${imageTypeListSize }">
<input type="hidden" name="paramString" value="${paramString }">
<input type="hidden" name="remoteUrl" value="${remoteUrl }">
<input type="hidden" name="typeTreeXML" value="${typeTreeXML }">
<input type="hidden" name="paramString_show" value="${paramString_show }">
<input type="hidden" name="remoteUrl_show" value="${remoteUrl_show }">
<script type="text/javascript">
function doUploadFile(uploadType) {
	var url = "";
	if (uploadType != "upload") {
		url = fm.remoteUrl_show.value + "?" + fm.paramString_show.value + "&allowUpload=false&allowModifiedImage=true&bussNo=" + fm.RegistNo.value;
	} else {
		url = fm.remoteUrl.value + "?" + fm.paramString.value + "&allowUpload=true&allowModifiedImage=true&bussNo=" + fm.RegistNo.value;
	}
	var oldAction = fm.action;
	var oldTarget = fm.target;
	fm.action = url;
	fm.target = "fraSubmit";
	fm.submit();
	fm.action = oldAction;
	fm.target = oldTarget;
}
</script>