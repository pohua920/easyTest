<%--
***************************************************************************
* Description: 摊赔意向确认页面
* Author     : Luyang
* CreateDate:  2005-2-18 16:56
* UpdateLog：  Name       Date            Reason/Contents
****************************************************************************
--%>

<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
  <head>
<title><s:text name="title.hepeiBeforeEdit.intentionConfirmBooth" /></title>
<%--摊赔意向确认--%>
    <!-- 公用函数 -->
    <script src="${ctx }/pages/undwrt/common/js/Common.js"></script>
    <script src="${ctx }/pages/undwrt/common/js/CommonTaskDeal.js"></script>
    <!-- 页面样式 -->
    <link rel="stylesheet" type="text/css" href="${ctx }/pages/undwrt/css/Standard.css">
  </head>
<s:set var="certiType" value="#parameters.CertiType" scope="page"/>
<s:if test="#parameters[0].CertiType=='T'">
	<s:set var="certiName" value="要保書" scope="page"/>
</s:if>
<s:elseif test="#parameters[0].CertiType=='P'">
	<s:set var="certiName" value="保单" scope="page"/>
</s:elseif>
<s:elseif test="#parameters[0].CertiType=='E'">
	<s:set var="certiName" value="批单" scope="page"/>
</s:elseif>
<s:elseif test="#parameters[0].CertiType=='Y'">
	<s:set var="certiName" value="预赔" scope="page"/>
</s:elseif>
<s:elseif test="#parameters.CertiType[0]=='C'||#parameters[0].CertiType=='J'">
	<s:set var="certiName" value="实赔" scope="page"/>
	<s:set var="certiType" value="J" scope="page"/>
</s:elseif>
  <body class="interface">
    <form name="fm" method="post" action="CommonReinsSave.jsp">
      <table class="common" cellpadding="5" cellspacing="1" align="center">
        <tr>
				<td colspan="4" class="formtitle">
					<s:text name="undwrt.hepei.intentionConfirmBooth" />
				</td>
			</tr>
			<%--摊赔意向确认--%>
        <tr>
				<td class="title">
					<s:text name="sendUndwrt.BusinessNumber" />：
				</td>
				<%--业务号--%>
				<td class="input">
					<s:property value="#parameters.CertiNo" />
				</td>
				<td class="title">
					<s:text name="db.prpLarrearageNew.businessType" />：
          </td>
				<%--业务类型--%>
				<td class="input">${certiName }</td>
        </tr>
			<tr>
				<td colspan=4 class=input>
					<s:text name="undwrt.hepei.standCompenIntention" />：
				</td>
				<%--摊赔意向--%>
          <tr>
            <td class=input colspan=4 align="center">
              <input type="hidden" name=CertiType value='${certiType }'>
              <input type="hidden" name=CertiNo value='<s:property value="#parameters.CertiNo"/>'>
              <textarea class=big wrap="hard" name="ReinsIntent" rows="5" cols="80" maxLength="255" description="摊赔意向" onblur=""></textarea>
            </td>
          </tr>
      </table>

      <!--条件新增的空DATA表格-->
      <!--条件UI显示表格-->
      <span style="display:none">
        <table id="reinsReceiveDATA" name="reinsReceiveDATA">
          <tbody>
             <tr>
						<td class=text>
							<input class="codecode1" name=ReinsCode maxlength=10 ONDBLCLICK="openreinspage(this)">
						</td>
						<td class=text>
							<input class="codecode1" name=FinalReinsCode maxlength=10 ONDBLCLICK="openreinspage(this)">
						</td>
						<td class=text>
							<input class="codecode1" name=PayCode maxlength=10 ONDBLCLICK="openreinspage(this)">
						</td>
						<td class=text>
							<input class=common1 name=ShareRate>
						</td>
						<td class=text>
							<input class=common1 name=CommmRate>
						</td>
						<td class=text>
							<input class=common1 name=TaxRate>
						</td>
						<td class=text>
							<input class=common1 name=OthRate>
						</td>
               <td class=text>
                 <!-- luyang: 当下面元素为IMG时，fm.elements不认为IMG是表单里的元素。可能和浏览器版本有关，延後处理-->
                 <input type="button" value='-' name="deleteReinsReceiveButton" class="button" alt="删除" src="${ctx }/pages/undwrt/common/images/butDeleteBlue.gif" onclick="deleteRow('reinsReceiveUI',this,1,1);">
               </td>
            </tr>
          </tbody>
        </table>
      </span>
      
      <!--条件UI显示表格-->
      <table class="common" cellpadding="5" cellspacing="1" align="center" id="reinsReceiveUI" name="reinsReceiveUI">
      <!--选择条件标题-->
        <thead>
          <tr class=listtitle>
					<td width="13%">
						<s:text name="undwrt.hepei.acceptPeople" />
					</td>
					<%--接受人--%>
            <!--	  <td class=title><input class=common name=ReinsName></td> -->
					<td width="14%">
						<s:text name="undwrt.hepei.eventuallyAcceptPeople" />
					</td>
					<%--最终接受人--%>
            <!--	  <td class=title><input class=common name=FinalReinsName></td> -->
					<td width="13%">
						<s:text name="undwrt.hepei.jieFuPeople" />
					</td>
					<%--结付人--%>
					<td width="14%">
						<s:text name="undwrt.hepei.boothCostShare" />
					</td>
					<%--摊赔份额﹪--%>
					<td width="14%">
						<s:text name="undwrt.hepei.commissionRatio" />
					</td>
					<%--手续费比例﹪--%>
					<td width="12%">
						<s:text name="undwrt.hepei.taxDeductionRatio" />
					</td>
					<%--扣税比例﹪--%>
					<td width="16%">
						<s:text name="undwrt.hepei.otherCostRatio" />
					</td>
					<%--其他费用比例﹪--%>
            <td width="4%"> &nbsp;</td>
          </tr>
        </thead>
        <tfoot>
          <tr>
            <td class="text" colspan="8">
              <IMG name="addReinsReceive" class="button" type="button" alt="新增接收人" src="${ctx }/pages/undwrt/common/images/butAddReinsReceive.gif" onclick="insertRow('reinsReceiveUI','reinsReceiveDATA')">
            </td>
          </tr>
        </tfoot>
     </table>
     </table>
     
     <table align="center" class="common" cellpadding="5" cellspacing="1">
       <tfoot>
         <tr>
					<td class=title>
						<s:text name="undwrt.hepei.reinsuranConfirmStatus" />
					</td>
					<%--再保确认状态--%>
					<td class="input" colspan="7" align=left>
						&nbsp;
						<input type="radio" disabled name="reinsState" value="0">
						<s:text name="undwrt.hepei.uncommitReinsurance" />
						<%--未提交再保--%>
						<input type="radio" disabled name="reinsState" value="1">
						<s:text name="undwrt.hepei.submitAgainConfirm" />
						<%--已提交再保--%>
						<input type="radio" disabled name="reinsState" value="2">
						<s:text name="undwrt.hepei.reinsuranThrough" />
						<%--再保通过--%>
						<input type="radio" disabled name="reinsState" value="3">
						<s:text name="undwrt.hepei.reinsuranNotThrough" />
						<%--再保不通过--%>
          </td>
           <!--<td class="righttitle" colspan="5"></td>-->
        </tr>
      </tfoot>
    </table>
      <table align="center" class="common" cellpadding="5" cellspacing="1">
         <tr>
				<td class=title>
					<s:text name="undwrt.hepei.againConfirmOpinion" />
				</td>
				<%--再保确认意见--%>
           <td class="input">
             <textarea name=ReinsHandleText rows=3 cols=56 class=readonly readonly ></textarea>
           </td>
         </tr>
      </table>
      <table class=sub >
        <tr>
          <td class=button width=33%>
            <IMG name="butSave" class="button" type="submit" alt="儲存"  src="${ctx }/pages/undwrt/common/images/butSave.gif" onclick="saveReins()">
          </td>
          <td class=button width=34%>
            <IMG name="buttonTransmitReins" class="button" type="submit" alt="提交再保" src="${ctx }/pages/undwrt/common/images/butTransmitReins.gif" onclick="transmitReins()">
          </td>
          <td class=button width=33%>
            <IMG name="buttonCancel" class="button" type="button" alt="取消"  src="${ctx }/pages/undwrt/common/images/butCancel.gif" onclick="window.close()">
          </td>
      </tr>
    </table>
  </form>
</body>
	<%-- 初始化 
	<jsp:include page="/common/CommonReinsIni.jsp"/>
	--%>
</html>