var injuiDefineIndex = 100;
var injuryRateIndex = 100;
var injuiDutieIndex = 100;
function addInjuryDefine(lastLineId) {
	var node = document.getElementById(lastLineId);
	var innerHtml = "<input type='hidden' name='prpdInjuryDefines["
			+ injuiDefineIndex
			+ "].injuryDefineCode' ><input class='table_long'  name='prpdInjuryDefines["
			+ injuiDefineIndex
			+ "].defineDesc' ></input>#<button type='button' value=''  onclick=deleteTrById('injuryDefine_tr_"
			+ injuiDefineIndex + "');refreshTagName('prpdInjuryDefines','injuryDefine_table')><span><em>删除</em></span></button>";
//			<input type='button' value='删除' class='button_ty' onclick=deleteTrById('injuryDefine_tr_"
//			+ injuiDefineIndex + "');refreshTagName('prpdInjuryDefines','injuryDefine_table')></input>			
	var newNode = CreateNode("injuryDefine_tr_" + injuiDefineIndex,
			innerHtml);
	// 如果存在上一级结点
	if (node.parentNode) {
		node.parentNode.insertBefore(newNode, node);
	}
	injuiDefineIndex++;
	refreshTagName("prpdInjuryDefines","injuryDefine_table");
}


function addInjuryRate(lastLineId) {
	var node = document.getElementById(lastLineId);
	var innerHtml = "<input type='hidden' name='prpdInjuryRates["
			+ injuryRateIndex
			//modify by duanfa
			/*+ "].injuryRateCode' ><select name='prpdInjuryRates["+ injuryRateIndex + "].firstGrade'><option value='1'>一级</option><option value='2'>二级</option><option value='3'>三级</option></select>"+
	"#<select name='prpdInjuryRates["+ injuryRateIndex + "].secondGrade'><option value='1'>一级</option><option value='2'>二级</option><option value='3'>三级</option></select>"+
	"#<select name='prpdInjuryRates["+ injuryRateIndex + "].thirdGrade'><option value='1'>一级</option><option value='2'>二级</option><option value='3'>三级</option></select>"+
	"#<input type='button' value='删除' class='button_ty' onclick=deleteTrById('injuryRate_tr_"*/
			+ "].injuryRateCode' ><select name='prpdInjuryRates["+ injuryRateIndex + "].firstGrade'>"+document.getElementById("injuryRateSelect").innerHTML+"</select>"+
	"#<select name='prpdInjuryRates["+ injuryRateIndex + "].secondGrade'>"+document.getElementById("injuryRateSelect").innerHTML+"</select>"+
	"#<select name='prpdInjuryRates["+ injuryRateIndex + "].thirdGrade'>"+document.getElementById("injuryRateSelect").innerHTML+"</select>"+
	"#<button type='button' value=''  onclick=deleteTrById('injuryRate_tr_"
			+ injuryRateIndex + "');refreshTagName('prpdInjuryRates','injuryRate_table');><span><em>删除</em></span></button>"
//	"# /*<input type='button' value='删除'  onclick=deleteTrById('injuryRate_tr_"
//			+ injuryRateIndex + "');refreshTagName('prpdInjuryRates','injuryRate_table'); />*/";
	var newNode = CreateNode("injuryRate_tr_" + injuryRateIndex,
			innerHtml);
	// 如果存在上一级结点
	if (node.parentNode) {
		node.parentNode.insertBefore(newNode, node);
	}
	injuryRateIndex++;
	refreshTagName("prpdInjuryRates","injuryRate_table");
}

function addInjuryDutie(lastLineId) {
	//modify by duanfa20110804 start
	var node = document.getElementById(lastLineId);
	var innerHtml = "<select name='prpdInjuryDuties["+injuiDutieIndex+"].dutyType'> <option value='1'>主要责任</option></select>"+
	"#<select name='prpdInjuryDuties["+injuiDutieIndex+"].paymentType' onchange='changePaymentType(this,"+injuiDutieIndex+")' >"+document.getElementById("paymentTypeSelect").innerHTML+"</select>"+
	//"#<select name='prpdInjuryDuties["+injuiDutieIndex+"].disabiCategory' onchange='changeDisabiLevel(this,"+injuiDutieIndex+")'> <option value='1'>劳动功能障碍</option> <option value='2'>生活自理障碍</option></select>"+
	"#<select name='prpdInjuryDuties["+injuiDutieIndex+"].disabiCategory' onchange='changeDisabiLevel(this,"+injuiDutieIndex+")'>"+document.getElementById("disabiCategorySelect").innerHTML+"</select>"+
	//"#<select name='prpdInjuryDuties["+injuiDutieIndex+"].disabiLevel' id='disabiLevel_"+injuiDutieIndex+"'> <option value='a'>1</option></select>"+
	"#<select name='prpdInjuryDuties["+injuiDutieIndex+"].disabiLevel' id='disabiLevel_"+injuiDutieIndex+"'>"+document.getElementById("WoundLevelSelect").innerHTML+"</select>"+
	//"#<select name='prpdInjuryDuties["+injuiDutieIndex+"].countType'> <option value='a'>本人工资</option> <option value=''>社平工资</option><option value=''>其他</option></select>"+
	"#<select name='prpdInjuryDuties["+injuiDutieIndex+"].countType' id='countType_"+injuiDutieIndex+"'>"+document.getElementById("injuryCountTypeSelect").innerHTML+"</select>"+
	//"#<select name='prpdInjuryDuties["+injuiDutieIndex+"].payScaleType'> <option value='a'>百分比</option> <option value=''>倍数</option> <option value=''>月</option></select>"+
	"#<select name='prpdInjuryDuties["+injuiDutieIndex+"].payScaleType' id='payScaleType_"+injuiDutieIndex+"'>"+document.getElementById("payScaleTypeSelect").innerHTML+"</select>"+
	"#<input type='hidden' name='prpdInjuryDuties["
			+ injuiDutieIndex
			+ "].injuryDutyCode' ><input name='prpdInjuryDuties["+injuiDutieIndex+"].payScale' onblur='checkNumber(this)' maxlength='6'></input>"+
	"#<button type='button' style='width: 50px;' value=''  onclick=deleteTrById('injuryDutie_tr_"
			+ injuiDutieIndex + "');refreshTagName('prpdInjuryDuties','injuryDutie_table') ><span><em>删除</em></span></button>";
	
//	<input type='button' style='width: 50px;' value='删除' class='button_ty' onclick=deleteTrById('injuryDutie_tr_"
//			+ injuiDutieIndex + "');refreshTagName('prpdInjuryDuties','injuryDutie_table') ></input>
			
	var newNode = CreateNode("injuryDutie_tr_" + injuiDutieIndex,innerHtml);
	// 如果存在上一级结点
	if (node.parentNode) {
		node.parentNode.insertBefore(newNode, node);
	}
	injuiDutieIndex++;
	refreshTagName("prpdInjuryDuties","injuryDutie_table");
}
function refreshTagName(listName,tableId) {
	var inputSize = 0;
	var inputChangeFlag = false;
	var table = document.getElementById(tableId);
	if (table.childNodes) {
		var tbody = table.childNodes;
		for ( var h = 0; h < tbody.length; h++) {
			if (tbody[h].childNodes) {
				if (tbody[h].childNodes.length > 2) {
					var trs = tbody[h].childNodes;
					for ( var i = 0; i < trs.length; i++) {
						if (trs[i].childNodes) {
							var tds = trs[i].childNodes;
							for ( var j = 0; j < tds.length; j++) {
								if (tds[j].childNodes) {
									var tdSubTag = tds[j].childNodes;
									for ( var k = 0; k < tdSubTag.length; k++) {
										var inputName = tdSubTag[k].name;
										if (inputName != null) {
											if (inputName.indexOf(listName) > -1) {
												var nameTail = inputName
														.substring(
																inputName.indexOf("]"),
																inputName.length);
												tdSubTag[k].name = listName
														+ "[" + inputSize
														+ nameTail;
												inputChangeFlag = true;
											}
										}
									}
								}
							}
							if (inputChangeFlag) {
								inputSize++;
								inputChangeFlag = false;
							}
						}
					}
				}
			}
		}
	}
}

function deleteTrById(trId) {
	var parentTd = document.getElementById(trId);
	if (parentTd.parentNode) {
		parentTd.parentNode.removeChild(parentTd);
	}
}
function CreateNode(tagId, innerHtml) {
	// 创建新div
	/*var NewTr = document.createElement(tagName);
	NewTr.id = tagId;
	NewTr.innerHTML = innerHtml;*/
	
	var NewTr = document.createElement("tr");
	NewTr.id = tagId;
	var elements = innerHtml.split("#");
	for(var i=0;i<elements.length;i++){
		var Newtd = document.createElement("td"); 
		Newtd.innerHTML=elements[i];
		NewTr.appendChild(Newtd);
	}
	// 对div设置 id属性
	// 返回新创建结点数据
	return NewTr;
}
function changeDisabiLevel(disabiCategory,index){
	var disabiLevelSelect = document.getElementById("disabiLevel_"+index);
	var selectName = disabiLevelSelect.name;
			// modify by duanfa 20110804
	if(disabiCategory.value=='2'){
		if(disabiLevelSelect.parentNode){
			//delete by duanfa20110805 忘了注掉了
			//disabiLevelSelect.parentNode.innerHTML="<select class='table_short' name='"+selectName+"' id='disabiLevel_"+index+"'> <option value='1'>生活完全不能自理</option><option value='2'>生活大部分不能自理</option><option value='3'>生活部分不能自理</option></select>";
			disabiLevelSelect.parentNode.innerHTML="<select class='table_short' name='"+selectName+"' id='disabiLevel_"+index+"'>"+document.getElementById("disabiLevelSelect").innerHTML+"</select>";
		}
	}else{
		if(disabiLevelSelect.parentNode){
			//disabiLevelSelect.parentNode.innerHTML="<select class='table_short' name='"+selectName+"' id='disabiLevel_"+index+"'>"++"</select>";
		    disabiLevelSelect.parentNode.innerHTML="<select class='table_short' name='"+selectName+"' id='disabiLevel_"+index+"'>"+document.getElementById("WoundLevelSelect").innerHTML+"</select>";
			
		}
		
	}
}
// add by duanfa 20110804
function changePaymentType(paymentType,index){
	var countTypeSelect = document.getElementById("countType_"+index);
	var payScaleTypeSelect = document.getElementById("payScaleType_"+index);
	if(paymentType.value=='3'){
		countTypeSelect.value='2';
		payScaleTypeSelect.value='3';
	}
	if(paymentType.value=='5'){
		countTypeSelect.value='1';
		payScaleTypeSelect.value='2';
	}
	if(paymentType.value=='1'||paymentType.value=='2'||paymentType.value=='4'){
		countTypeSelect.value='1';
	}
	if(paymentType.value=='7'){
		countTypeSelect.value='2';
	}
}