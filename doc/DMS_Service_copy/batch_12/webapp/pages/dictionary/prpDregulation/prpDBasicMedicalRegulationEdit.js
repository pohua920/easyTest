
var basicMedicalIndex = 100;
function addBasicMedical(lastLineId) {
		var node = document.getElementById(lastLineId);
		//modify  by duanfa 20110928 start 数字验证
//	var innerHtml = "<select class='table_short' name='prpdBasicMedicals["+basicMedicalIndex+"].itemKind' onchange='otherChoose(this)'><option value='1'>住院</option><option value='2'>特殊疾病门诊</option><option value='3'>门急诊</option><option value='4'>特殊项目</option><option value='other'>其他</option></select>"+
	var innerHtml = "<select class='table_short' name='prpdBasicMedicals["+basicMedicalIndex+"].itemKind' onchange='otherChoose(this)'>"+document.getElementById("MedicalItemKindSelect").innerHTML+"</select>"+
//	"#<select class='table_short' name='prpdBasicMedicals["+basicMedicalIndex+"].insureType' onchange='otherChoose(this)'><option value='1'>城镇职工</option> <option value='2'>城镇居民</option><option value='3'>新农合</option><option value='4'>外来务工人员</option> <option value='5'>综合</option> <option value='other'>其他</option></select>"+
	"#<select class='table_short' name='prpdBasicMedicals["+basicMedicalIndex+"].insureType' onchange='otherChoose(this)'>"+document.getElementById("MedicalInsureTypeKindSelect").innerHTML+"</select>"+
//	"#<select class='table_short' name='prpdBasicMedicals["+basicMedicalIndex+"].personCategory' onchange='otherChoose(this)'><option value='1'>学生儿童</option> <option value='2'>在职</option><option value='3'>退休</option><option value='other'>其他</option></select>"+
	"#<select class='table_short' name='prpdBasicMedicals["+basicMedicalIndex+"].personCategory' onchange='otherChoose(this)'>"+document.getElementById("MedicalPersCategorySelect").innerHTML+"</select>"+
	"#<input class='table_short dt-num' name='prpdBasicMedicals["+basicMedicalIndex+"].baseStandard'></input>"+
	"#<input class='table_short dt-num' name='prpdBasicMedicals["+basicMedicalIndex+"].busiStandard'></input>"+
//	"#<select class='table_short' name='prpdBasicMedicals["+basicMedicalIndex+"].hospitalLevel'><option value='1'>一级</option><option value='2'>二级</option><option value='3'>三级</option><option value='4'>网络医院</option><option value='5'>其他</option></select>"+
	"#<select class='table_short' name='prpdBasicMedicals["+basicMedicalIndex+"].hospitalLevel'>"+document.getElementById("hospitalTypeSelect").innerHTML+"</select>"+
	"#<input class='table_short dt-num' name='prpdBasicMedicals["+basicMedicalIndex+"].payLine'></input>"+
	"#<input class='table_short dt-num' name='prpdBasicMedicals["+basicMedicalIndex+"].baseLimit'></input>"+
	"#<input class='table_short dt-num' name='prpdBasicMedicals["+basicMedicalIndex+"].highSegLimit'></input>"+
	"#<input class='table_short' name='prpdBasicMedicals["+basicMedicalIndex+"].basePayScale' onblur='checkNumber(this)' maxlength='6'></input>"+
	"#<input class='table_short' name='prpdBasicMedicals["+basicMedicalIndex+"].addedPayScale'></input>"+
	"#<input class='table_short' name='prpdBasicMedicals["+basicMedicalIndex+"].highSegPayScale'></input>"+
	"#<input name='submit' type='button' style='width: 50px;' value='删除' class='button_ty' onclick=deleteTrById('basicMedical_tr_"
			+ basicMedicalIndex + "');refreshTagName('prpdBasicMedicals','prpdBasicMedicals_table') ></input>";
	//		modify  by duanfa 20110928 end 数字验证
	var newNode = CreateNode("basicMedical_tr_" + basicMedicalIndex,innerHtml);
	// 如果存在上一级结点
	if (node.parentNode) {
		node.parentNode.insertBefore(newNode, node);
	}
	basicMedicalIndex++;
	refreshTagName("prpdBasicMedicals", "prpdBasicMedicals_table");
}
function refreshTagName(listName, tableId) {
	var inputSize = 0;
	var inputChangeFlag = false;
	var table = document.getElementById(tableId);
	if (table.childNodes) {
		var tbody = table.childNodes;
		for (var h = 0; h < tbody.length; h++) {
			if (tbody[h].childNodes) {
				if (tbody[h].childNodes.length > 2) {
					var trs = tbody[h].childNodes;
					for (var i = 0; i < trs.length; i++) {
						if (trs[i].childNodes) {
							var tds = trs[i].childNodes;
							for (var j = 0; j < tds.length; j++) {
								if (tds[j].childNodes) {
									var tdSubTag = tds[j].childNodes;
									for (var k = 0; k < tdSubTag.length; k++) {
										var inputName = tdSubTag[k].name;
										if (inputName != null) {
											if (inputName.indexOf(listName) > -1) {
												var nameTail = inputName.substring(inputName.indexOf("]"), inputName.length);
												tdSubTag[k].name = listName + "[" + inputSize + nameTail;
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
function otherChoose(slectTag) {
	if (slectTag.value == "other") {
		if (slectTag.parentNode) {
			var newNode = document.createElement("input");
			newNode.name = slectTag.name;
			newNode.style.width="93";
			slectTag.name = "remove";
			slectTag.parentNode.appendChild(newNode);
		}
	} else {
		var nodes = slectTag.parentNode.childNodes;
		for (var i = 0; i < nodes.length; i++) {
			if (nodes[i].tagName == "INPUT") {
				slectTag.name = nodes[i].name;
				nodes[i].parentNode.removeChild(nodes[i]);
			}
		}
	}
}
function CreateNode( tagId, innerHtml) {
	// 创建新div
	/*var NewTr = document.createElement(tagName);
	NewTr.id = tagId;
	NewTr.innerHTML = innerHtml;*/
	var NewTr = document.createElement("tr");
	NewTr.id = tagId;
	var elements = innerHtml.split("#");
	for (var i = 0; i < elements.length; i++) {
		var Newtd = document.createElement("td");
		Newtd.innerHTML = elements[i];
		NewTr.appendChild(Newtd);
	}
	// 对div设置 id属性
	// 返回新创建结点数据
	return NewTr;
}
