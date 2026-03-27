<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
    <%@include file="/common/i18njs.jsp"%>
    <%@include file="/common/meta_js.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <link rel="stylesheet" type="text/css" href="${ctx }/pages/undwrt/css/Standard.css">
    <title><s:text name="title.hepeiBeforeEdit.hePeiTaskQuery" /></title>
    <script src="${ctx}/pages/undwrt/common/js/Common.js"></script>
    <script src="${ctx}/pages/undwrt/common/js/CommonTaskDeal.js"></script>
    <script src="${ctx}/pages/undwrt/common/js/WfLogQuery.js"></script>
    <script language="javascript"> 
        function locate(pageNo){
            if(pageNo<1){
                alert("已經到第首頁");
                return false;
            }
            if(pageNo>parseInt(fm.pagesCount.value)){
                alert("已經到最尾頁");
                return false;
            }
            if(pageNo==${pageRecord.pageNo}){
                alert("無法跳轉到該頁");
                return false;  
            }
            fm.pageNo.value=pageNo;
            fm.submit();
            return true;
        }
        function goPage(){
            var pageNo=parseInt(fm.newPageNo.value);
            if(isNaN(pageNo)){
                pageNo=1;
            }
            if(pageNo>parseInt(fm.pagesCount.value)){
                alert("無法跳轉到" + pageNo + "頁");
                return false;
            }
            return locate(pageNo);
        }
    </script>
</head>
<body>
    <form name="fm" action="${ctx}/hepeiTaskDeal.do">
        <input type="hidden" name="actionType" value="queryContinue" />
        <input type="hidden" name="pageNo" value="${pageRecord.pageNo}" />
        <input type="hidden" name="rowsCount" value="${pageRecord.count}" />
        <input type="hidden" name="rowsPerPage" value="${pageRecord.rowsPerPage}" />
        <input type="hidden" name="riskCategory" value='${param.riskCategory}'>
        <input type="hidden" name="HandType" value="22">
        <input type="hidden" name="EditType" value='${param.EditType}'>
        <input type="hidden" name="checkboxSelect" value="0">
        <table class="common" cellpadding="5" cellspacing="1" align="center">
            <tr class="listtitle">
                <td colspan="12">
                    <b><s:text name="undwrt.hepei.hePeiTaskQuery" /></b><%--核赔任务查询结果--%>
                </td>
            </tr>
            <tr class="listtitle">
                <td>
                    <c:if test="${param.EditType!='query'}">
                        <input type="checkbox" name="selectButton" value="v" onpropertychange="boundCheckBox(this, fm.checkboxSelect);">
                    </c:if>
                    <c:if test="${param.EditType=='query'}">
                        <input type="checkbox" name="selectButton" value="v" disabled>
                    </c:if>
                </td>
                <td><s:text name="sendUndwrt.BusinessNumber" /><%--业务号--%></td>
                <td><s:text name="db.view_larrearage.policyNo" /><%--保单号--%></td>
                <td><s:text name="db.prpLclaim.claimNo" /><%--立案号--%></td>
                <td>
	                <c:choose>
	                    <c:when test="${param.riskCategory=='D'}"><s:text name="db.prpLdriver.licenseNo"/><%-- 車牌號 --%></c:when>
	                    <c:when test="${param.riskCategory=='Y'}"><s:text name="undwrt.ReservationAgreement"/><%-- 預約協議號 --%></c:when>
	                    <c:when test="${param.riskCategory=='E'}"><s:text name="db.prpLdriver.identifyNumber"/><%-- 證件號 --%></c:when>
	                    <c:otherwise><s:text name="db.prpLdriver.licenseNo"/>/<s:text name="db.prpLdriver.identifyNumber"/>/<s:text name="undwrt.ReservationAgreement"/></c:otherwise>
	                </c:choose>
                </td>
                <td><s:text name="db.view_larrearage.insuredname" /><%--被保险人名称--%></td>
                <td><s:text name="regist.prpLregist.riskCodeName" /><%--险种--%></td>
                <td><s:text name="regist.prpLregist.comName" /><%--归属机构--%></td>
                <td><s:text name="undwrt.SubmissionTime" /><%--提交时间--%></td>
                <td><s:text name="archive.level" /><%--级别--%></td>
                <td><s:text name="regist.prpLregist.status" /><%--状态--%></td>
                <td><s:text name="taskActorId" /><%--提交人--%></td>
            </tr>
        <c:forEach items="${requestScope.UndwrtTaskList}" var="tempWfLog" varStatus="status">
            <tr class=common>
                <td>
                    <input type="checkbox" name="checkboxSelect" value="${status.index}" <c:if test="${param.EditType=='query'}">disabled</c:if> >
                </td>
                <td>
                    <a class="check" href="${ctx}/CommonCheckTask.do?iFlowID=${tempWfLog.id.flowId}&iLogNo=${tempWfLog.id.logNo}&iRiskCode=${tempWfLog.riskCode}&HandType=22&EditType=${param.EditType}">${tempWfLog.businessNo}</a></td>
                <td>${tempWfLog.policyNo}</td>
                <td>${tempWfLog.claimNo}</td>
                <td>
                    <c:choose>
                        <c:when test="${param.riskCategory=='D'}">${tempWfLog.licenseNo}</c:when>
                        <c:when test="${param.riskCategory=='Y'}">${tempWfLog.relateContractNo}</c:when>
                        <c:when test="${param.riskCategory=='E'}">${tempWfLog.identifyNumber}</c:when>
                        <c:otherwise>&nbsp;</c:otherwise>
                    </c:choose>
                </td>
                <td>${tempWfLog.insuredName}</td>
                <td>${tempWfLog.riskCode}</td>
                <td>${tempWfLog.comName}</td>
                <td>
                    <rc:rcDate name="flowInTime" class="readonly" readonly="true" wdatePicker="false" style="width:120px" value="${tempWfLog.flowInTime}" />
                </td>
                <td>${tempWfLog.nodeName}</td>
                <td>${tempWfLog.nodeStatusName}
                    <c:choose>
                        <c:when test="${tempWfLog.reinsStatus=='2'}"><s:text name="undwrt.hepei.submitReinsurance" /><%--（提交再保）--%></c:when>
                        <c:when test="${tempWfLog.reinsStatus=='3'}"><s:text name="undwrt.hepei.submitReinsurance" /><%--（提交再保）--%></c:when>
                        <c:when test="${tempWfLog.reinsStatus=='4'}"><s:text name="undwrt.hepei.reinsuranFeedback" /><%--（再保反馈）--%></c:when>
                        <c:when test="${tempWfLog.reinsStatus=='5'}"><s:text name="undwrt.hepei.reinsuranInquiry" /><%--（再保询价）--%></c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose>
                </td>
                <td>${tempWfLog.operatorName}</td>
            </tr>
        </c:forEach>
        </table>
        <table class=menu align="center">
            <tr>
                <td>
                    <table width="100%" border=0 cellspacing=0 cellpadding=0>
                        <tr>
                            <c:set var="firstRow" value="0" scope="page" />
                            <c:if test="${pageRecord.count>0}">
                                <c:set var="firstRow" value="${pageRecord.rowsPerPage * (pageRecord.pageNo - 1) + 1 }" scope="page" />
                            </c:if>
                            <c:if test="${pageRecord.pageNo<pageRecord.totalPageCount}">
                                <c:set var="lastRow" value="${pageRecord.rowsPerPage * (pageRecord.pageNo - 1) + pageRecord.rowsPerPage}" scope="page" />
                            </c:if>
                            <c:if test="${pageRecord.pageNo>=pageRecord.totalPageCount}">
                                <c:set var="lastRow" value="${pageRecord.count}" scope="page" />
                            </c:if>
                            <td width="33%" class="page">&nbsp;
                                <s:text name="manage.total" /><%--共--%>${pageRecord.count}
                                <s:text name="manage.article" /><%--条--%>，
                                <s:text name="navigator.list" /><%--列出--%>${firstRow}
                                <s:text name="navigator.firstArticle" /><%--条到第--%>${lastRow }
                                <s:text name="manage.article" /><%--条--%>
                            </td>
                            <td width="34%" align="center" class="page">
                                <input type=hidden name=pagesCount value="${pageRecord.totalPageCount }">
                                <img src='${ctx}/pages/undwrt/common/images/btnFirstPage.gif' align=middle style='cursor: hand' border=0 alt='首页' onclick="return locate(1);">&nbsp;&nbsp; 
                                <img src='${ctx}/pages/undwrt/common/images/btnUp.gif' align=middle style='cursor: hand' border=0 alt='上页' onclick="return locate(${pageRecord.pageNo-1 })">&nbsp;&nbsp; 
                                <img src='${ctx}/pages/undwrt/common/images/btnNext.gif' align='middle' style='cursor: hand' border=0 alt='下页' onclick="return locate(${pageRecord.pageNo+1 })">&nbsp;&nbsp;
                                <img src='${ctx}/pages/undwrt/common/images/btnLastPage.gif' align=middle style='cursor: hand' border=0 alt='末页' onclick="return locate(${pageRecord.totalPageCount })">
                            </td>
                            <td width="33%" align="right" class="page">
                                <s:text name="manage.total" /><%--共--%>${pageRecord.totalPageCount}
                                <s:text name="navigator.page" /><%--页--%>，
                                <s:text name="navigator.listFirst" /><%--列出第--%>${pageRecord.pageNo}
                                <s:text name="navigator.page" /><%--页--%>&nbsp;
                                <s:text name="navigator.goTo" /><%--转到--%>
                                <input type='text' name=newPageNo class='smallGo'>
                                <s:text name="navigator.page" /><%--页--%>
                                <img src='${ctx }/pages/undwrt/common/images/btnGo.gif' align='middle' style='cursor: hand' border='0' alt='转到' onclick="return goPage()">&nbsp;
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        &nbsp;
        <c:if test="${param.EditType!='query'}">
            <table class="two">
                <tr>
                    <td align="center">
                        &nbsp;&nbsp;
                        <input type="button" class="button" name="btn3" value="撤回" <%--撤回--%>
                           onclick="prepareBatchSubmit('hepei', 'prepareBatchUndo');" ${batchUndoButton}>
                    </td>
                </tr>
            </table>
        </c:if>
    </form>
</body>
</html>