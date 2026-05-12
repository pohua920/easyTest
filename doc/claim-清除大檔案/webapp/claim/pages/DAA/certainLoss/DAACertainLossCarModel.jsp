<%--
****************************************************************************
* DESC       ：车型查询页面
* AUTHOR     ：理赔项目组
* CREATEDATE ：2007-03-15
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<script language="JavaScript">
	javascript:window.history.forward(1);
</script>
<%@ include file="/common/meta_js.jsp"%>
<html>
<app:claimCodeInput />
<head>
<title><s:text name="title.certainLossBeforeEdit.modelQuery" /></title>
<!--车型查询-->
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<script language='javascript'>
	//选择车辆品牌
	
	function onLoadBrand(field) {
		var outputObject;
		var inputObject = field;
		var factory = fm.prpdCarModelFactory.value;
		var findType = 'factory';
		var param = {
			factory: factory,
			findType: findType
		};
		dwrInvokeData("getPrpDcarModelBrand", param, "rollbackOnLoadBrand", inputObject, outputObject);
	
	}
	
	function rollbackOnLoadBrand(inputObject, outputObject, returnObject) {
		var prpDcarModel = returnObject;
		if (prpDcarModel.length == 0)
			alert("无此车型品牌!");
		else {
			DWRUtil.removeAllOptions('prpdCarModelBrand');
			DWRUtil.addOptions('prpdCarModelBrand', {
				"0": "＝＝选择品牌＝＝"
			});
			DWRUtil.addOptions('prpdCarModelBrand', prpDcarModel);
		}
		//modify by liping 08-05-03 
		undisablebutton();
	}
	
	
	 //选择车系
	
	function onLoadSeriesName(field) {
		var outputObject;
		var inputObject = field;
		var carBrand = DWRUtil.getText('prpdCarModelBrand');
		var findType = 'carBrand';
		var param = {
			carBrand: carBrand,
			findType: findType
		};
		dwrInvokeData("getPrpDcarModelBrand", param, "rollbackOnLoadSeriesName", inputObject, outputObject);
	}
	
	function rollbackOnLoadSeriesName(inputObject, outputObject, returnObject) {
		var prpDcarModel = returnObject;
		DWRUtil.removeAllOptions('prpdCarModelSeriesName');
		DWRUtil.addOptions('prpdCarModelSeriesName', {
			"0": "＝＝选择车系＝＝"
		});
		DWRUtil.addOptions('prpdCarModelSeriesName', prpDcarModel);
		//modify by liping 08-05-03 
		undisablebutton();
	}
	
	
	function saveForm(findType) {
		fm.findType.value = findType;
		if (findType == '2') {
			if (fm.SuperModelName.value == "" && fm.JPModelName.value == "") {
				alert("请输入车型代码或者车型简拼!");
				fm.SuperModelName.focus();
			} else {
				fm.submit();
			}
		} else {
			fm.carModelBrand.value = DWRUtil.getText('prpdCarModelBrand');
			fm.carModelSeriesName.value = DWRUtil.getText('prpdCarModelSeriesName');
			fm.submit();
		}
	}
	
	function submitForm(modelName, modelCode, seatCount, tonCount, carActualValue, purchasePrice, countryNature, carKind) {
		try {
			window.opener.fm.prpLcarLossBrandName.value = modelName;
			window.opener.fm.prpLcarLossModelCode.value = modelCode;
			window.opener.fm.prpLcarLossCarKindName.value = carKind;
		} catch (e) {}
		window.close();
		return;
	
	}
	
	function document_onkeypress() {
		if (window.event.keyCode == 13) {
			submitQueryForm();
		}
	}
	
	function submitQueryForm() {
		if (fm.SuperModelName.value == "" && fm.JPModelName.value == "") {
			alert("请输入车型代码或者车型简拼!");
			fm.SuperModelName.focus();
		} else {
			fm.submit();
		}
	}
	
	function UpperCase(e) {
		var keycode = window.event.keyCode;
		if (keycode >= 97 && keycode <= 122) {
			window.event.keyCode = keycode - 32;
			return pressCustom(e, /[\dA-Z\.]/);
		}
		if (keycode = 32) {
	
		}
	}
	
	function pressCustom(e, reg) {
		var value = String.fromCharCode(e.keyCode);
		var r = reg.test(value);
		return r;
	}
</script>
<body>
	<form name="fm" action="/claim/certainLoss/certainLossBeforeEdit.do?editType=findCarModel" method="post">
		<table class=common cellpadding="5" cellspacing="1">
			<tr>
				<td class="subformtitle" colspan="6">
					<s:text name="certainLoss.modelQuery" />
				</td>
				<!--车型查询-->
			</tr>
		</table>
		<table class=common cellpadding="5" cellspacing="1">
			<tr class=common>
				<td class="center">
					<s:text name="certainLoss.Manufacturers" />:
				</td>
				<!--厂商-->
				<td class="right">
					<input name="prpdCarModelFactory" class="codecode" ondblclick="code_CodeSelect(this,'factory','0,1','Y');onLoadBrand(this);"
						onchange="code_CodeChange(this,'factory','0,1','Y');onLoadBrand(this);" onkeyup="code_CodeSelect(this,'factory','0,1','Y');onLoadBrand(this);">
				</td>
				<td class="right">
					<select name="prpdCarModelBrand" class="one" onChange="onLoadSeriesName(this);">
						<option value="0">
							＝＝
							<s:text name="certainLoss.selectBrand" />
							＝＝
						</option>
						<!--选择品牌-->
					</select>
					<input type="hidden" name="carModelBrand">
				</td>
				<td class="right">
					<select name="prpdCarModelSeriesName" class="one">
						<option value="0">
							＝＝
							<s:text name="certainLoss.selectCars" />
							＝＝
						</option>
						<!--选择车系-->
					</select>
					<input type="hidden" name="carModelSeriesName">
				</td>
				<td class="center">
					<input type="button" class=button name="findCarModel" value="查询" onclick="saveForm('1');">
				</td>
			</tr>
			<tr class=common>
				<td class=title>
					<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyBrandName" />
				</td>
				<!--厂牌型号：-->
				<td class=right>
					<input onkeypress="document_onkeypress();return UpperCase(event);" name="SuperModelName" class="common" style="" value="" />
				</td>
				<td class=title>
					<s:text name="certainLoss.janeTailorInput" />：
				</td>
				<!--简拼录入-->
				<td class=right>
					<input onkeypress="document_onkeypress();return UpperCase(event);" name="JPModelName" class="common" style="" value="" />
				</td>
				<td class=button>
					<input class="button" align="left" type="button" name="ModelNameSubmit" alt=" 查 询 " value="<s:text name='button.query.value'/>" onclick="saveForm('2')">
					<!--查 询-->
				</td>
			</tr>
		</table>
		<input type="hidden" name="findType">
		<table class=common cellpadding="5" cellspacing="1">
			<tr class=listtitle>
				<td width='12%'>
					<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyBrandName" />
				</td>
				<!--厂牌型号-->
				<td width='12%'>
					<s:text name="db.prpLthirdparty.modelCode" />
				</td>
				<!--车型代码-->
				<td width='12%'>
					<s:text name="certainLoss.thirdCarLoss.carKind" />
				</td>
				<!--车辆种类-->
				<td width='10%'>
					<s:text name="certainLoss.abclass" />
				</td>
				<!--A/B类-->
				<td width='10%'>
					<s:text name="certainLoss.approvedPassenger" />
				</td>
				<!--核定载客-->
				<td width='10%'>
					<s:text name="certainLoss.containedInApprovedQuality" />
				</td>
				<!--核定载质量-->
				<td width='10%'>
					<s:text name="db.prpLCItemCar.purchasePrice" />
				</td>
				<!--新车购置价-->
			</tr>
			<c:forEach items="${requestScope.carModelList}" var="prpDcarModel" varStatus="stat">
				<tr
					<c:choose>
	      <c:when test="${stat.index % 2==0}">
	         class="listodd" 
	      </c:when>
	      <c:otherwise>
	         class="listeven" 
	      </c:otherwise>        
	    </c:choose>
					onclick="submitForm('${pageScope.prpDcarModel.modelName}'
						      ,'${pageScope.prpDcarModel.modelCode}'
						      ,'${pageScope.prpDcarModel.seatCount}'
						      ,'${pageScope.prpDcarModel.tonCount}'
						      ,'${pageScope.prpDcarModel.purchasePrice}'
						      ,'${pageScope.prpDcarModel.purchasePrice}'
						      ,'${pageScope.prpDcarModel.carStyle}'
						      ,'${pageScope.prpDcarModel.carKind}');">
					<td class=itemLink>
						<c:out value="${pageScope.prpDcarModel.modelName}" />
					</td>
					<td>
						<c:out value="${pageScope.prpDcarModel.modelCode}" />
					</td>
					<td>
						<c:out value="${pageScope.prpDcarModel.carKind}" />
					</td>
					<td>
						<c:out value="${pageScope.prpDcarModel.carStyle}" />
					</td>
					<td>
						<c:out value="${pageScope.prpDcarModel.seatCount}" />
					</td>
					<td>
						<c:out value="${pageScope.prpDcarModel.tonCount}" />
					</td>
					<td>
						<c:out value="${pageScope.prpDcarModel.purchasePrice}" />
					</td>
				</tr>
			</c:forEach>
		</table>
	</form>
</body>
</html>