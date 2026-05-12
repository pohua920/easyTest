<%--
****************************************************************************
* DESC       ：委托公估公司处理赔案评估表
* AUTHOR     : liuwei
* CREATEDATE ：2011-05-21
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@include file="/common/meta_js.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<html>
<head>
<title><s:text name="title.pubBeforeEdit.appointCompanyHandleTable" /></title>
<%--委托公估公司处理赔案评估表--%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
</head>
<body bgcolor="#FFFFFF">
	<form name="fm" method="post" action="/claim/AssessorScore.do" onsubmit="return validateForm(this);">
		<input type="hidden" name="editType" value="insertSave" />
		<!-- 标题部分 -->
		<table width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr height=30>
				<td colspan="3" align=center style="font-family: 宋体; font-size: 14pt;">
					<B>
						<center>
							<span><s:text name="pub.appointCompanyHandleTable" /></span>
							<%--委托公估公司处理赔案评估表--%>
							<center>
								<B>
				</td>
			</tr>
		</table>
		<table border=1 width="100%" align="center" cellspacing="0" cellpadding="0" style="font-family: 宋体; font-size: 11pt; border-collapse: collapse" bordercolor="#111111">
			<tr>
				<td width="50%" valign="top">
					<table width="100%" border=1 align="top" cellspacing="0" cellpadding="0" style="font-family: 宋体; font-size: 11pt; border-collapse: collapse" bordercolor="#111111">
						<tr height="25">
							<td width="15%">
								<s:text name="pub.assessmentCompanyName" />
								：
							</td>
							<%--公估公司名称--%>
							<td colspan="7">${prpLAssessorScoreDto.comCName2}</td>
						</tr>
						<tr height="25">
							<td width="15%">
								<s:text name="pub.assessmentName" />
								：
							</td>
							<%--公估师姓名--%>
							<td colspan="3" width="35%">${prpLAssessorScoreDto.comCName1}</td>
							<td width="15%">
								<s:text name="certify.contactPhoneNo" />
								：
							</td>
							<%--联络电话--%>
							<td colspan="3" width="35%">${prpLAssessorScoreDto.telePhone}</td>
						</tr>
						<tr height="25">
							<td width="15%">
								<s:text name="specialCase.ClaimsNumbers" />
								：
							</td>
							<%--赔案号码--%>
							<td colspan="3" width="35%">${prpLclaimDto.claimNo}</td>
							<td width="15%">
								<s:text name="db.view_larrearage.insuredname" />
								：
							</td>
							<%--被保险人名称--%>
							<td colspan="3" width="35%">${prpLclaimDto.insuredName}</td>
						</tr>
						<tr height="25">
							<td width="15%">
								<s:text name="pub.commissionTime" />
								：
							</td>
							<%--委托时间--%>
							<td colspan="3" width="35%">${prpLAssessorScoreDto.commitDate}</td>
							<td width="15%">
								<s:text name="regist.prpLregist.damageTime" />
								：
							</td>
							<%--出险时间--%>
							<td colspan="3" width="35%">${prpLclaimDto.damageStartDate}</td>
						</tr>
						<tr height="25">
							<td width="15%">
								<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckCase" />
							</td>
							<%--事故原因：--%>
							<td colspan="7">${prpLclaimDto.damageName}</td>
						</tr>
						<tr height="25">
							<td width="15%">
								<s:text name="certainLoss.prpLacciCheck.prpLacciCheckDamageAddress" />
							</td>
							<%--事故地点：--%>
							<td colspan="7">${prpLclaimDto.damageAddress}</td>
						</tr>
						<tr align="center" height="25">
							<td width="15%">
								<b><s:text name="pub.evaluationIndicator" /></b>
								<%--评估指标--%>
							</td>
							<td width="7%">
								<b><s:text name="dangerUnit.Share" /></b>
								<%--占比--%>
							</td>
							<td colspan="2" width="26%">
								<b><s:text name="pub.score100" /></b>
								<%--得分(100~80)--%>
							</td>
							<td colspan="2" width="26%">
								<b><s:text name="pub.score80" /></b>
								<%--得分(80~60)--%>
							</td>
							<td colspan="2" width="26%">
								<b><s:text name="pub.score60" /></b>
								<%--得分(60~30)--%>
							</td>
						</tr>
						<tr align="center" height="25">
							<td width="15%">
								<s:text name="pub.cooperatDegree" />
							</td>
							<%--配合度--%>
							<td width="7%">10%</td>
							<td width="13%">
								<s:text name="pub.fast" />
							</td>
							<%--快--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="100" name="prpLAssessorScoreDto" property="score1">
						            <logic:greaterThan value="80" name="prpLAssessorScoreDto" property="score1">
						                <bean:write name='prpLAssessorScoreDto' property='score1'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score1 <= '100' && prpLAssessorScoreDto.score1 > '80'}">
						    ${prpLAssessorScoreDto.score1}
						    </c:if>
							</td>
							<td width="13%">
								<s:text name="pub.general" />
							</td>
							<%--一般--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="80" name="prpLAssessorScoreDto" property="score1">
						            <logic:greaterThan value="60" name="prpLAssessorScoreDto" property="score1">
						                <bean:write name='prpLAssessorScoreDto' property='score1'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score1 <= '80' && prpLAssessorScoreDto.score1 > '60'}">
						    ${prpLAssessorScoreDto.score1}
						    </c:if>
							</td>
							<td width="13%">
								<s:text name="pub.slow" />
							</td>
							<%--慢--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="60" name="prpLAssessorScoreDto" property="score1">
						            <logic:greaterThan value="30" name="prpLAssessorScoreDto" property="score1">
						                <bean:write name='prpLAssessorScoreDto' property='score1'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score1 <= '60' && prpLAssessorScoreDto.score1 > '30'}">
						    ${prpLAssessorScoreDto.score1}
						    </c:if>
							</td>
						</tr>
						<tr align="center" height="25">
							<td width="15%">
								<s:text name="pub.negotiatSkill" />
							</td>
							<%--谈判技巧--%>
							<td width="7%">10%</td>
							<td width="13%">
								<s:text name="pub.good" />
							</td>
							<%--好--%>
							<td width="13%">
								<%--
                                <logic:lessEqual value="100" name="prpLAssessorScoreDto" property="score2">
						            <logic:greaterThan value="80" name="prpLAssessorScoreDto" property="score2">
						                <bean:write name='prpLAssessorScoreDto' property='score2'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
                            --%>
								<c:if test="${prpLAssessorScoreDto.score2 <= '100' && prpLAssessorScoreDto.score2 > '80'}">
						    ${prpLAssessorScoreDto.score2}
						    </c:if>
							</td>
							<td width="13%">
								<s:text name="pub.general" />
							</td>
							<%--一般--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="80" name="prpLAssessorScoreDto" property="score2">
						            <logic:greaterThan value="60" name="prpLAssessorScoreDto" property="score2">
						                <bean:write name='prpLAssessorScoreDto' property='score2'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score2 <= '80' && prpLAssessorScoreDto.score2 > '60'}">
						    ${prpLAssessorScoreDto.score2}
						    </c:if>
							</td>
							<td width="13%">
								<s:text name="pub.poor" />
							</td>
							<%--差--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="60" name="prpLAssessorScoreDto" property="score2">
						            <logic:greaterThan value="30" name="prpLAssessorScoreDto" property="score2">
						                <bean:write name='prpLAssessorScoreDto' property='score2'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score2 <= '60' && prpLAssessorScoreDto.score2 > '30'}">
						    ${prpLAssessorScoreDto.score2}
						    </c:if>
							</td>
						</tr>
						<tr align="center" height="25">
							<td width="15%">
								<s:text name="pub.professionalLevel" />
							</td>
							<%--专业水平--%>
							<td width="7%">20%</td>
							<td width="13%">
								<s:text name="pub.good" />
							</td>
							<%--好--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="100" name="prpLAssessorScoreDto" property="score3">
						            <logic:greaterThan value="80" name="prpLAssessorScoreDto" property="score3">
						                <bean:write name='prpLAssessorScoreDto' property='score3'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score3 <= '100' && prpLAssessorScoreDto.score3 > '80'}">
						    ${prpLAssessorScoreDto.score3}
						    </c:if>
							</td>
							<td width="13%">
								<s:text name="pub.general" />
							</td>
							<%--一般--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="80" name="prpLAssessorScoreDto" property="score3">
						            <logic:greaterThan value="60" name="prpLAssessorScoreDto" property="score3">
						                <bean:write name='prpLAssessorScoreDto' property='score3'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score3 <= '80' && prpLAssessorScoreDto.score3 > '60'}">
						    ${prpLAssessorScoreDto.score3}
						    </c:if>
							</td>
							<td width="13%">
								<s:text name="pub.poor" />
							</td>
							<%--差--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="60" name="prpLAssessorScoreDto" property="score3">
						            <logic:greaterThan value="30" name="prpLAssessorScoreDto" property="score3">
						                <bean:write name='prpLAssessorScoreDto' property='score3'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score3 <= '60' && prpLAssessorScoreDto.score3 > '30'}">
						    ${prpLAssessorScoreDto.score3}
						    </c:if>
							</td>
						</tr>
						<tr align="center" height="25">
							<td width="15%">
								<s:text name="pub.progressReturnManner" />
							</td>
							<%--处理进度回报是否及时--%>
							<td width="7%">10%</td>
							<td width="13%">
								<s:text name="pub.good" />
							</td>
							<%--好--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="100" name="prpLAssessorScoreDto" property="score4">
						            <logic:greaterThan value="80" name="prpLAssessorScoreDto" property="score4">
						                <bean:write name='prpLAssessorScoreDto' property='score4'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score4 <= '100' && prpLAssessorScoreDto.score4 > '80'}">
						    ${prpLAssessorScoreDto.score4}
						    </c:if>
							</td>
							<td width="13%">
								<s:text name="pub.general" />
							</td>
							<%--一般--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="80" name="prpLAssessorScoreDto" property="score4">
						            <logic:greaterThan value="60" name="prpLAssessorScoreDto" property="score4">
						                <bean:write name='prpLAssessorScoreDto' property='score4'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score4 <= '80' && prpLAssessorScoreDto.score4 > '60'}">
						    ${prpLAssessorScoreDto.score4}
						    </c:if>
							</td>
							<td width="13%">
								<s:text name="pub.poor" />
							</td>
							<%--差--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="60" name="prpLAssessorScoreDto" property="score4">
						            <logic:greaterThan value="30" name="prpLAssessorScoreDto" property="score4">
						                <bean:write name='prpLAssessorScoreDto' property='score4'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score4 <= '60' && prpLAssessorScoreDto.score4 > '30'}">
						    ${prpLAssessorScoreDto.score4}
						    </c:if>
							</td>
						</tr>
						<tr align="center" height="25">
							<td width="15%">
								<s:text name="pub.qualityAssessReport" />
							</td>
							<%--公估报告质量--%>
							<td width="7%">20%</td>
							<td width="13%">
								<s:text name="pub.good" />
							</td>
							<%--好--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="100" name="prpLAssessorScoreDto" property="score5">
						            <logic:greaterThan value="80" name="prpLAssessorScoreDto" property="score5">
						                <bean:write name='prpLAssessorScoreDto' property='score5'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score5 <= '100' && prpLAssessorScoreDto.score5 > '80'}">
						    ${prpLAssessorScoreDto.score5}
						    </c:if>
							</td>
							<td width="13%">
								<s:text name="pub.general" />
							</td>
							<%--一般--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="80" name="prpLAssessorScoreDto" property="score5">
						            <logic:greaterThan value="60" name="prpLAssessorScoreDto" property="score5">
						                <bean:write name='prpLAssessorScoreDto' property='score5'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score5 <= '80' && prpLAssessorScoreDto.score5 > '60'}">
						    ${prpLAssessorScoreDto.score5}
						    </c:if>
							</td>
							<td width="13%">
								<s:text name="pub.poor" />
							</td>
							<%--差--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="60" name="prpLAssessorScoreDto" property="score5">
						            <logic:greaterThan value="30" name="prpLAssessorScoreDto" property="score5">
						                <bean:write name='prpLAssessorScoreDto' property='score5'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score5 <= '60' && prpLAssessorScoreDto.score5 > '30'}">
						    ${prpLAssessorScoreDto.score5}
						    </c:if>
							</td>
						</tr>
						<tr align="center" height="25">
							<td width="15%">
								<s:text name="pub.professionalEthics" />
							</td>
							<%--职业道德操守--%>
							<td width="7%">20%</td>
							<td width="13%">
								<s:text name="pub.good" />
							</td>
							<%--好--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="100" name="prpLAssessorScoreDto" property="score6">
						            <logic:greaterThan value="80" name="prpLAssessorScoreDto" property="score6">
						                <bean:write name='prpLAssessorScoreDto' property='score6'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score6 <= '100' && prpLAssessorScoreDto.score6 > '80'}">
						    ${prpLAssessorScoreDto.score6}
						    </c:if>
							</td>
							<td width="13%">
								<s:text name="pub.general" />
							</td>
							<%--一般--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="80" name="prpLAssessorScoreDto" property="score6">
						            <logic:greaterThan value="60" name="prpLAssessorScoreDto" property="score6">
						                <bean:write name='prpLAssessorScoreDto' property='score6'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score6 <= '80' && prpLAssessorScoreDto.score6 > '60'}">
						    ${prpLAssessorScoreDto.score6}
						    </c:if>
							</td>
							<td width="13%">
								<s:text name="pub.poor" />
							</td>
							<%--差--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="60" name="prpLAssessorScoreDto" property="score6">
						            <logic:greaterThan value="30" name="prpLAssessorScoreDto" property="score6">
						                <bean:write name='prpLAssessorScoreDto' property='score6'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score6 <= '60' && prpLAssessorScoreDto.score6 > '30'}">
						    ${prpLAssessorScoreDto.score6}
						    </c:if>
							</td>
						</tr>
						<tr align="center" height="25">
							<td width="15%">
								<s:text name="pub.price" />
							</td>
							<%--收费价格--%>
							<td width="7%">10%</td>
							<td width="13%">
								<s:text name="pub.reasonable" />
							</td>
							<%--合理--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="100" name="prpLAssessorScoreDto" property="score7">
						            <logic:greaterThan value="80" name="prpLAssessorScoreDto" property="score7">
						                <bean:write name='prpLAssessorScoreDto' property='score7'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score6 <= '100' && prpLAssessorScoreDto.score6 > '80'}">
						    ${prpLAssessorScoreDto.score7}
						    </c:if>
							</td>
							<td width="13%">
								<s:text name="pub.highSide" />
							</td>
							<%--偏高--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="80" name="prpLAssessorScoreDto" property="score7">
						            <logic:greaterThan value="60" name="prpLAssessorScoreDto" property="score7">
						                <bean:write name='prpLAssessorScoreDto' property='score7'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score6 <= '80' && prpLAssessorScoreDto.score6 > '60'}">
						    ${prpLAssessorScoreDto.score7}
						    </c:if>
							</td>
							<td width="13%">
								<s:text name="pub.tooHigh" />
							</td>
							<%--过高--%>
							<td width="13%">
								<%--
						        <logic:lessEqual value="60" name="prpLAssessorScoreDto" property="score7">
						            <logic:greaterThan value="30" name="prpLAssessorScoreDto" property="score7">
						                <bean:write name='prpLAssessorScoreDto' property='score7'/>
						            </logic:greaterThan>
						        </logic:lessEqual>
						    --%>
								<c:if test="${prpLAssessorScoreDto.score6 <= '60' && prpLAssessorScoreDto.score6 > '30'}">
						    ${prpLAssessorScoreDto.score7}
						    </c:if>
							</td>
						</tr>
						<tr align="center" height="25">
							<td width="15%">
								<s:text name="pub.totalScore" />
							</td>
							<%--总得分--%>
							<td colspan="7" id="total">${prpLAssessorScoreDto.totalScore}</td>
						</tr>
						<tr height="100">
							<td width="15%" align="center">
								<s:text name="pub.instructions" />
							</td>
							<%--说明--%>
							<td colspan="7">${prpLAssessorScoreDto.remark}</td>
						</tr>
						<tr height="25">
							<td width="15%" align="center">
								<s:text name="pub.assessmentUnit" />
							</td>
							<%--评估单位--%>
							<td colspan="7">${prpLAssessorScoreDto.company}</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<script language="VBScript">
    // 避免弹出安全警告框的说明：Internet选项=〉安全=〉受信任的站点 
    // 1.将网站加入受信任站点，
    // 2.自定义级别中 启用 对没有标记为安全的ActiveX控件进行初始化和脚本运行    

    dim hkey_root,hkey_path,hkey_key
    hkey_root="HKEY_CURRENT_USER"
    hkey_path="\Software\Microsoft\Internet Explorer\PageSetup"
    
    dim oldheader,oldfooter,oldleft,oldright,oldtop,oldbottom
    
    '//设置网页打印的页眉页脚，上下左右
    function pagesetup_set(header,footer,oldleft,oldright,oldtop,oldbottom)
        on error resume next
        Set RegWsh = CreateObject("WScript.Shell")
        hkey_key="\header"          
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,header
        hkey_key="\footer"
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,footer
        
        
        hkey_key="\margin_left" '左
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,oldleft
        hkey_key="\margin_right" '右
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,oldright
        hkey_key="\margin_top" '上
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,oldtop
        hkey_key="\margin_bottom" '下
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,oldbottom
        
    end function
    '//设置网页打印的页眉页脚,上下左右为默认值
    function pagesetup_default()
        on error resume next
        Set RegWsh = CreateObject("WScript.Shell")
        hkey_key="\header"    
        'RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"&w&b页码，&p/&P"
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,""
        hkey_key="\footer"
        'RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"&u&b&d"
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,""
        
        hkey_key="\margin_left" '左
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.75"     '(对应 19.05毫米)
        'message = message & "左:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        hkey_key="\margin_right" '右
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.75"
        'message = message & "右:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        hkey_key="\margin_top" '上
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.75"
        'message = message & "上:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        hkey_key="\margin_bottom" '下
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.75"  
        'message = message & "下:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        
        'msgbox (message)      
    end function
    
    '//显示页面设置
    function pagesetup_get()
        on error resume next
        Set RegWsh = CreateObject("WScript.Shell")
        hkey_key="\header"    
        oldheader=RegWsh.RegRead(hkey_root+hkey_path+hkey_key)
        hkey_key="\footer"
        oldfooter=RegWsh.RegRead(hkey_root+hkey_path+hkey_key)
        
        hkey_key="\margin_left" '左
        oldleft=RegWsh.RegRead( hkey_root+hkey_path+hkey_key)
        'message = message & "左:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        hkey_key="\margin_right" '右
        oldright=RegWsh.RegRead( hkey_root+hkey_path+hkey_key)
        'message = message & "右:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        hkey_key="\margin_top" '上
        oldtop=RegWsh.RegRead( hkey_root+hkey_path+hkey_key)
        'message = message & "上:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        hkey_key="\margin_bottom" '下
        oldbottom=RegWsh.RegRead( hkey_root+hkey_path+hkey_key)
        'message = message & "下:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        
        'msgbox (message)
    end function    
    
    function printPage()
	    on error resume next
		pagesetup_get()         '读取旧值
		'header=""
		'footer=""		
		'pagesetup_get()
	    pagesetup_default()
		'pagesetup_set header, footer
		divButton.style.display = "none"
        window.print()
        divButton.style.display = ""
		pagesetup_set oldheader, oldfooter, oldleft, oldright, oldtop, oldbottom            '恢复設定

    end function
</script>
		<OBJECT classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0 id=WebBrowser width=0></OBJECT>
		<div align="center" id="divButton" style="display:">
			<p>
			<table cellpadding="0" cellspacing="0" width="80%" style="display:">
				<tr>
					<td class=button align="center">
						<input class="button" type="button" name="buttonSave" value="<s:text name='button.save.value' />" onclick="return submitForm()" disabled="disabled">
					</td>
					<td class=button style="width: 33%" align="center">
						<input class="button" type="button" name="buttonPrint" value="<s:text name='button.print.value' />" onclick="vbscript:printPage()">
					</td>
					<td class=button align="center">
						<input class="button" type="button" name="buttonBack" value="<s:text name='button.return.value' />" onclick="return history.back();">
					</td>
				</tr>
			</table>
			</p>
		</div>
	</form>
</body>
</html>
