<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String title = "住火稽核議題檢核作業";
	String image = path + "/" + "images/";
	String GAMID = "APS004E0";
	String mDescription = "住火稽核議題檢核作業";
	String nameSpace = "/aps/004";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
mantis：FIR0225，處理人員：BJ085，需求單編號：FIR0225 稽核議題調整-過往資料檢核 start 
-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">

	/* mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 start */
	$(document).ready(function(){
		//加上小日曆
		$('#tiiUndate').datepicker({dateFormat:"yyyy/mm/dd"});
		/* mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  */
		$('#rerunUndate').datepicker({dateFormat:"yyyy/mm/dd"});
	});
	/* mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 end */
	
	/* mantis：FIR0505，處理人員：CC009，需求單編號：FIR0505 手動排程執行功能調整(無規格) start */
	function form_submit(type){
		if("query" == type){
			 $("#mainForm").attr("action","/APS/aps/004/btnQuery.action");
			 $("#mainForm").submit();
		}
		/* mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 start */
		else if("btnExecuteOthPatchPassbook" == type){
 			 $("#mainForm").attr("action","/APS/aps/004/btnExecuteOthPatchPassbook.action");
			 $("#mainForm").submit();
		}
		/* mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 end */
		/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 start */
		else if ("btnExecuteFirPanhsinBackFile" == type){
			 $("#mainForm").attr("action","/APS/aps/004/btnExecuteFirPanhsinBackFile.action");
			 $("#mainForm").submit();
		}
		/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 end */

		/* mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格 start */
		else if ("btnExecuteFirYcbBackFile" == type){
			 $("#mainForm").attr("action","/APS/aps/004/btnExecuteFirYcbBackFile.action");
			 $("#mainForm").submit();
		}
		/* mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格 start */

		/*mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 start*/
		else if ("btnExecuteUbBackFile" == type && confirm("是否確定執行聯邦回饋檔產生作業?") == true){
			 $("#mainForm").attr("action","/APS/aps/004/btnExecuteUbBackFile.action");
			 $("#mainForm").submit();
		}
		/*mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 end*/
		
		/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 start*/
		else if ("btnExecuteFirRenewList" == type && confirm("是否確定執行住火應續件清單產生作業?") == true){
			 $("#mainForm").attr("action","/APS/aps/004/btnExecuteFirRenewList.action");
			 $("#mainForm").submit();
		}
		/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 end*/
		
		/* mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 start */
		else if ("btnExecuteFirTiiSp" == type && confirm("是否確定執行保發中心-住火保批資料產生(SP)作業?") == true){
			 $("#mainForm").attr("action","/APS/aps/004/btnExecuteFirTiiSp.action");
			 $("#mainForm").submit();
		}
		else if ("btnExecuteGenTiiFile" == type && confirm("是否確定執行保發中心-住火保批資料傳輸作業?") == true){
			 $("#mainForm").attr("action","/APS/aps/004/btnExecuteGenTiiFile.action");
			 $("#mainForm").submit();
		}
		/* mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 end */
		/* mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業  start */
		else if ("btnExecuteBotrn" == type && confirm("是否確定執行臺銀續保資料產生作業?") == true){
			$.blockUI({
				border: 'none',
				padding: '15px',//padding：同時設定四個邊留白的捷徑屬性
				backgroundColor: '#000',//backgroundColor：訊息背景顏色
				color: '#fff',//color：訊息字樣顏色
				'-webkit-border-radius': '10px',//WebKit設定四邊的圓角為10px
				'-moz-border-radius': '10px',//Mozilla設定四邊的圓角為10px
				opacity: .5//opacity：指定表單和其控制項的透明度等級
			});
			 $("#mainForm").attr("action","/APS/aps/004/btnExecuteBotrn.action");
			 $("#mainForm").submit();
		}
		/* mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業  end */
		
		/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  start**/
		else if ("btnExecuteFpolicyRerun" == type && confirm("是否確定執行保單轉入中介異常執行作業?") == true){
			var flag=true;
			if($("#rerunUndate").val()!=='' && $("#rerunUndate").val()!=null){
				flag=validateDateFormat();
			}
			if(flag){
				$("#mainForm").attr("action","/APS/aps/004/btnExecuteFpolicyRerun.action");
				$("#mainForm").submit();
			}
		}
		/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  end **/
		/* mantis：FIR0667，處理人員：DP0706，需求單編號：FIR0667 住火_元大續保作業_續件資料產生排程START */
		else if ("btnExecuteYcbrn" == type && confirm("是否確定執行元大續保資料產生作業?") == true){
			$.blockUI({
				border: 'none',
				padding: '15px',//padding：同時設定四個邊留白的捷徑屬性
				backgroundColor: '#000',//backgroundColor：訊息背景顏色
				color: '#fff',//color：訊息字樣顏色
				'-webkit-border-radius': '10px',//WebKit設定四邊的圓角為10px
				'-moz-border-radius': '10px',//Mozilla設定四邊的圓角為10px
				opacity: .5//opacity：指定表單和其控制項的透明度等級
			});
			 $("#mainForm").attr("action","/APS/aps/004/btnExecuteYcbrn.action");
			 $("#mainForm").submit();
		}
		/* mantis：FIR0667，處理人員：DP0706，需求單編號：FIR0667 住火_元大續保作業_續件資料產生排程 END */
	}
	/* mantis：FIR0505，處理人員：CC009，需求單編號：FIR0505 手動排程執行功能調整(無規格) end */
	
	/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  start**/
	function validateDateFormat(){
		var rerunUndate=$("#rerunUndate").val(); 
		var pattern =/^\d{4}\/\d{2}\/\d{2}$/;
		if(!pattern.test(rerunUndate)){
			alert("簽單日期不符合格式");
			return false;
			}
		return true;
		}
	/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  end **/
	
	function showMsg(msg){
		alert(msg);
	}
	
	var msg = "<%=request.getAttribute("message")%>";
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}
</script>
</head>
<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tbody>
		   <tr>
			  <td width="485px">			      
				  <img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0">
			  </td>
			  <td align="right" width="485px">PGMID：<%=GAMID%></td>
		   </tr>
		   <tr>
			   <td colspan="2" align="center" width="970px"><h3><%=title%></h3></td>
		   </tr>
	</tbody>		
</table>
<s:form theme="simple" namespace="/aps/004" id="mainForm" name="mainForm">
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<!-- mantis：FIR0265，處理人員：BJ085，需求單編號：FIR0265 板信受理檔產生排程 -->
				<span id="lbSearch"><b>手動執行排程作業</b></span></td>
			<td colspan="5" class="image"></td>
		</tr>
	</tbody>
</table>
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
	<tbody>
		<!-- mantis：FIR0265，處理人員：BJ085，需求單編號：FIR0265 板信受理檔產生排程 start -->
		<tr>
			<td width="500px" align="left">
			1.稽核檢核：<input type="button" value="執行" onclick="javascript:form_submit('query');"/>
			</td>
		</tr>
		<tr>
			<td width="500px" align="left">
			作業說明：本作業執行前請先與資訊人員確認欲檢核資料是否已匯入FIR_TMP_DATACHECK
			</td>
		</tr>
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			<!-- mantis：FIR0271，處理人員：BJ085，需求單編號：FIR0271 板信保單檔產生作業-排程作業 -->
			2.板信要保受理檔產生作業：<a href="${pageContext.request.contextPath}/aps/009/btnExecuteBop01.action" onclick="return confirm('是否確定執行板信要保受理檔產生作業?')"><input type="button" value="執行"/></a>
			</td>
		</tr>
		<!-- mantis：FIR0265，處理人員：BJ085，需求單編號：FIR0265 板信受理檔產生排程 end -->
		<!-- mantis：FIR0271，處理人員：BJ085，需求單編號：FIR0271 板信保單檔產生作業-排程作業 start -->
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			3.板信保單檔產生作業：<a href="${pageContext.request.contextPath}/aps/009/btnExecuteBop02.action" onclick="return confirm('是否確定執行板信保單檔產生作業?')"><input type="button" value="執行"/></a>
			</td>
		</tr>
		<!-- mantis：FIR0271，處理人員：BJ085，需求單編號：FIR0271 板信保單檔產生作業-排程作業 end -->
		<!-- mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start -->
			<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			4.板信續保檔資料接收作業：<a href="${pageContext.request.contextPath}/aps/016/btnExecuteBoprn.action" onclick="return confirm('是否確定執行板信續保檔資料接收作業?')"><input type="button" value="執行"/></a>
			</td>
		</tr>
		<!-- mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 end -->
		<!-- mantis：FIR0312，處理人員：BJ016，需求單編號：FIR0312_APS 呼叫核心外銀批次匯入 start -->
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			5.呼叫核心承保外銀匯入批次功能(原定排成每日上午九點，下午一點，下午五點會自動執行)：<a href="${pageContext.request.contextPath}/aps/009/btnCallBankToCoreService.action" onclick="return confirm('是否確定執行?')"><input type="button" value="執行"/></a>
			</td>
		</tr>
		<!-- mantis：FIR0312，處理人員：BJ016，需求單編號：FIR0312_APS 呼叫核心外銀批次匯入 end -->
		
		<!-- mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入 start -->
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			6.火險地址匯入作業：<a href="${pageContext.request.contextPath}/aps/005/btnDataImport.action" onclick="return confirm('是否確定執行火險地址匯入作業?')"><input type="button" value="執行"/></a>
			</td>
		</tr>
		<!-- mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入 end-->
		
		<!-- mantis：FIR0314，處理人員：BJ085，需求單編號：FIR0314 台銀保經-APS新件要保檔產生排程 start -->
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			7.臺銀新件要保受理檔產生作業 ：<a href="${pageContext.request.contextPath}/aps/022/btnExcuteGenBotApfile.action" onclick="return confirm('是否確定執行臺銀新件要保受理檔產生作業?')"><input type="button" value="執行"/></a>
			</td>
		</tr>
		<!-- mantis：FIR0314，處理人員：BJ085，需求單編號：FIR0314 台銀保經-APS新件要保檔產生排程 end -->
		
		<!-- mantis：FIR0315，處理人員：BJ085，需求單編號：FIR0315 台銀保經-APS保單檔產生排程 start -->
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			8.臺銀新件保單檔產生作業 ：<a href="${pageContext.request.contextPath}/aps/022/btnExcuteGenBotFhfile.action" onclick="return confirm('是否確定執行臺銀新件保單檔產生作業 ?')"><input type="button" value="執行"/></a>
			</td>
		</tr>
		<!-- mantis：FIR0315，處理人員：BJ085，需求單編號：FIR0315 台銀保經-APS保單檔產生排程 end -->
		
		<!-- mantis：FIR0458，處理人員：CC009，需求單編號：FIR0458 住火-APS中信續件要保書-資料接收排程 start -->
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			9.中信續件要保書接收作業 ：<a href="${pageContext.request.contextPath}/aps/035/btnExecuteRenewalFile.action" onclick="return confirm('是否確定執行中信續件要保書接收作業 ?')"><input type="button" value="執行"/></a>
			</td>
		</tr>
		<!-- mantis：FIR0458，處理人員：CC009，需求單編號：FIR0458 住火-APS中信續件要保書-資料接收排程 end -->
		
		<!-- mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程 start-->
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			10.富邦續保檔資料接收作業：<a href="${pageContext.request.contextPath}/aps/034/btnExecuteFbrn.action" onclick="return confirm('是否確定執行富邦續保檔資料接收作業?')"><input type="button" value="執行"/></a>
			</td>
		</tr>
		<!-- mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程 end-->
		
		<!-- mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 start -->
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			11.保發中心-保單存摺傳送作業(若有輸入批號則只取得該批號之資料產生txt送保發，若未輸入批號則正常執行。)：
			<input type="button" value="執行" onclick="javascript:form_submit('btnExecuteOthPatchPassbook');"/>
			&emsp;批次號碼：<s:textfield key="filter.batchNo" id="batchNo" theme="simple" />
			</td>
		</tr>
		<!-- mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 end -->
		
		<!-- mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 START -->
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			12.板信回饋檔產生作業(未輸入批次號則完整執行，若有輸入批次號則依據批次號重新產生TXT拋送至板信SFTP)：
			<input type="button" value="執行" onclick="javascript:form_submit('btnExecuteFirPanhsinBackFile');"/>
			</br>回饋檔類型：<s:select key="filter.backFileType" id="backFileType" list="#{'1':'受理檔','2':'保單檔'}"/>
			&emsp;批次號碼：<s:textfield key="filter.backFileBatchNo" id="backFileBatchNo" theme="simple" />
			</td>
		</tr>
		<!-- mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 END -->
		
		<!-- mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 start -->
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			13.聯邦回饋檔產生作業(未輸入批次號則完整執行，若有輸入批次號則依據批次號重新產生TXT拋送至內部SFTP)：
			<input type="button" value="執行" onclick="javascript:form_submit('btnExecuteUbBackFile');"/>
			</br>批次號碼：<s:textfield key="filter.ubBackFileBatchNo" id="ubBackFileBatchNo" theme="simple" />
			</td>
		</tr>
		<!-- mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 end -->
		
		<!-- mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程 start -->
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			14.聯邦要保進度檔接收作業:<a href="${pageContext.request.contextPath}/aps/004/btnExecuteUbProposal.action" 
			onclick="return confirm('是否確定執行聯邦要保進度檔接收作業?')"><input type="button" value="執行" /></a>
			</td>
		</tr>
		<!-- mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程 end -->
		
		<!-- mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 start-->
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			15.住火應續件清單產生作業:
			<input type="button" value="執行" onclick="javascript:form_submit('btnExecuteFirRenewList');"/>
			續保年月(YYYYMM)：<s:textfield key="filter.rnYymm" id="rnYymm" theme="simple" />
			</td>
		</tr>
		<!-- mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 end-->
		
		<!-- mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 start -->
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			16.保發中心-住火保批資料產生(SP):
			<input type="button" value="執行" onclick="javascript:form_submit('btnExecuteFirTiiSp');"/>
			</br>SP類型：<s:select key="filter.spType" id="spType" list="#{'P':'P.保單','E':'E.批單','D':'D.刪除','AS400':'AS400'}"/>
			&emsp;保批單號：<s:textfield key="filter.tiiBusinessNo" id="tiiBusinessNo" theme="simple" />
			&emsp;簽單日期：<s:textfield key="filter.tiiUndate" id="tiiUndate" theme="simple" />
			</td>
		</tr>
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			17.保發中心-住火保批資料傳輸(若輸入批號則只取得該批號之資料產生TXT，若未輸入批號則正常執行)：
			<input type="button" value="執行" onclick="javascript:form_submit('btnExecuteGenTiiFile');"/>
			批次號碼：<s:textfield key="filter.firTiiBatchNo" id="firTiiBatchNo" theme="simple" />
			</td>
		</tr>
		<!-- mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 end -->
		<!-- mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業  start -->
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			18.臺銀續保資料產生作業(若有輸入續保年月則依續保年月產生資料，若未輸入則正常執行)：
			<input type="button" value="執行" onclick="javascript:form_submit('btnExecuteBotrn');"/>
			續保年月(YYYYMM)：<s:textfield key="filter.rnDate" id="rnDate" theme="simple" />
			</td>
		</tr>
		<!-- mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業  end -->
		
		<!-- mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  start-->
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			19.保單轉入中介異常執行作業(依簽單日重新執行保單轉AS400中介SP):
			<input type="button" value="執行" onclick="javascript:form_submit('btnExecuteFpolicyRerun');"/>
			簽單日期(YYYY/MM/DD)：<s:textfield key="filter.rerunUndate" id="rerunUndate" theme="simple" />
			</td>
		</tr>
		<!-- mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  end-->
		
		<!--mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程  start-->
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			20.臺銀簽署檔FD資料接收作業：<a href="${pageContext.request.contextPath}/aps/004/btnExecuteBotFdreceive.action" onclick="return confirm('是否確定執行臺銀簽署檔FD資料接收作業?')"><input type="button" value="執行"/></a>
			</td>
		</tr>
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			21.臺銀續保資料比對簽署檔作業：<a href="${pageContext.request.contextPath}/aps/004/btnExecuteBotFdCompare.action" onclick="return confirm('是否確定執行臺銀續保資料比對簽署檔作業?')"><input type="button" value="執行"/></a>
			</td>
		</tr>
		<!--mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程  end-->
		<!-- mantis：FIR0667，處理人員：DP0706，需求單編號：FIR0667 住火_元大續保作業_續件資料產生排程START -->
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			22.元大續保資料產生作業(若有輸入續保年月則依續保年月產生資料，若未輸入則正常執行)：
			<input type="button" value="執行" onclick="javascript:form_submit('btnExecuteYcbrn');"/>
			續保年月(YYYYMM)：<s:textfield key="filter.ycbrnDate" id="ycbrnDate" theme="simple" />
			</td>
		</tr>
		<!-- mantis：FIR0667，處理人員：DP0706，需求單編號：FIR0667 住火_元大續保作業_續件資料產生排程END -->


		<!-- mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格 start -->
		<tr>
			<td align="left"><p></p></td>
		</tr>
		<tr>
			<td align="left">
			23.元大回饋檔產生作業(未輸入批次號則完整執行，若有輸入批次號則依據批次號重新產生TXT拋送至元大SFTP)：
			<input type="button" value="執行" onclick="javascript:form_submit('btnExecuteFirYcbBackFile');"/>
			&nbsp;批次號碼：<s:textfield key="filter.backFileBatchNo" id="backFileBatchNo" theme="simple" />
			</td>
		</tr>
		<!-- mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格 end -->

	</tbody>
</table>
</s:form>
</body>
</html>