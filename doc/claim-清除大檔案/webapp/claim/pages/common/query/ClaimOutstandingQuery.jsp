<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>未決賠案明細查詢</title>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<script type="text/javascript">
	function query(field){
		fm.submit();
	}
</script>
</head>
<body class="yui-skin-sam" >
	<form name="fm" action="${ctx }/claimOutstandingQuery.do" method="post" target="_blank">
		<input type="hidden" name="pageNo" value="1">
		<input type="hidden" name="pageSize" value="20">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan="2" class="formtitle">未決賠案明細查詢</td>
			</tr>
			<tr>
				<td class="title" align="right">單位：&nbsp;&nbsp;</td>
				<td class="input" align="left">
					<div >
						<input type="radio" value="0" name="comRadio">
						<label>出單單位</label>
						<input type="hidden" name="MakeCom" value="">
						<input type="text" name="MakeComName" value="" class="codename" style="width: 180px"
							ondblclick="code_CodeSelect(this, 'prpdcompany3','-1,0','Y','N',fm.MakeCom.value);" 
							onchange="code_CodeChange(this, 'prpdcompany3','-1,0','Y','N',fm.MakeCom.value);" 
							onkeyup="code_CodeSelect(this, 'prpdcompany3','-1,0','Y','N',fm.MakeCom.value);">
					</div>
					<div>
						<input type="radio" value="1" name="comRadio">
						<label>理賠單位</label>
						<input type="hidden" name="ComCode" value="" >
						<input type="text" name="ComCodeName" value="" class="codename" style="width: 180px"
							ondblclick="code_CodeSelect(this, 'prpdcompany3','-1,0','Y','N',fm.ComCode.value);" 
							onchange="code_CodeChange(this, 'prpdcompany3','-1,0','Y','N',fm.ComCode.value);" 
							onkeyup="code_CodeSelect(this, 'prpdcompany3','-1,0','Y','N',fm.ComCode.value);">
					</div>
				</td>
			</tr>
			<tr>
				<td class="title" align="right">時間：&nbsp;&nbsp;</td>
				<td class="input" align="left">&nbsp;
					<rc:rcDate name="dateStart" style="width:120px" value="" />
					&nbsp;至&nbsp;
					<rc:rcDate name="dateEnd" style="width:120px" value="" />
					&nbsp;止&nbsp;
				</td>
			</tr>
			<tr>
				<td class='button' colspan="2">
					<input type=button class='button' value="查詢" onClick="query(this)">
				</td>
			</tr>
		</table>
		<table width="98%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" id="tableResullt" style="display: none;">
						<tr>
							<td>
								<div id="content_message" style="display: none;"></div>
								<div id="listShowCont" align="left">
									<div id="listShow">
										<div id="content" class="sort"></div>
										<div id="content_navigation" class="query" style="text-align: center;"></div>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
<script type="text/javascript">
$(function(){
	var $radio = $(":radio[name='comRadio']");
	$radio.change(function(){
		$radio.each(function(i,e){
			$(e).nextAll().prop("disabled",!e.checked);
		});
	}).eq(1).prop("checked",true).trigger("change");
})
</script>
</html>