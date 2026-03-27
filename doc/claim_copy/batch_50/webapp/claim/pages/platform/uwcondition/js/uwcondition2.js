 function doUpdate() {
 	if (checkOtherValue() == false) {
 		return;
 	}
 	if (checkSimpleFactorValue() == false) {
 		return;
 	}
 	if (checkComboFactorValue() == false) {
 		return;
 	}
 	if (confirm("確實要儲存嗎？")) {
 		fm.action = "/claim/processUwCondition.do?actionType=updateUtiUwCondition";
 		fm.submit();
 	}
 }

 function checkComboIsNull(comboCount) {
 	if (fm.combofactorCodeCode == null) {
 		return true;
 	}

 	var comboLength = fm.combofactorCodeCode.length;
 	var factorCodeValueCount = comboLength / comboCount;
 	var factorCodeValueLength = fm.utiUwConditionFactorCodeValue.length;
 	var q = comboCount;
 	for (var j = 1; j < factorCodeValueLength; j++) {
 		//alert(q);
 		//alert(fm.utiUwConditionFactorCodeValue[j].value);
 		if (fm.utiUwConditionFactorCodeValue[j].value != "OtherValue") {
 			for (var i = 0; i < comboCount; i++) {
 				var comboValue = fm.combofactorCodeCode[q].value;
 				//alert(otherValue);
 				if (comboValue == "") {
 					alert("因子類型不能爲空");
 					return false;
 				} else {
 					q++;
 				}
 			}
 		} else {
 			//alert("aaaaaaaaaaaa");
 			q = q + comboCount;
 		}
 	}
 	return true;
 }

 function checkOtherValue() {
 	if (fm.combofactorFactorName == null) {
 		return true;
 	}
 	var otherValueLength = fm.utiUwConditionFactorCodeValue.length;
 	var checkLength = 0;
 	for (var i = 0; i < otherValueLength; i++) {
 		var otherValue = fm.utiUwConditionFactorCodeValue[i].value;
 		if (otherValue == "OtherValue") {
 			checkLength += 1;
 		}
 	}
 	if (checkLength > 1) {
 		alert("只能選擇 1 個其它條件！而您選擇了 " + checkLength + " 個其它條件！");
 		return false;
 	}
 	return true;
 }

 function checkSimpleFactorValue() {
 	if (fm.simpleFactorCode == null) {
 		return true;
 	}
 	var simpleLength = fm.simpleFactorCode.length;
 	if (simpleLength > 0) {
 		for (var i = 0; i < simpleLength; i++) {
 			var factorName = trim(fm.simpleFactorName[i].value);
 			var factorAttr = trim(fm.simpleFactorAttr[i].value);
 			var factorValue = trim(fm.simpleFactorValue[i].value);
 			//if(factorValue == "")
 			//{
 			//	alert("简单因子：" + factorName + " 不能为空！");
 			//	return false;
 			//}
 			if (factorValue == "MAX" || factorValue == "max" || factorValue == "MIN" || factorValue == "min") {
 				continue;
 			}
 			if (factorAttr == "I") //整数型
 			{
 				if (isInteger(factorValue) == false) {
 					alert("簡單因子：" + factorName + " 必須是整數！");
 					return false;
 				}
 			} else if (factorAttr == "M" || factorAttr == "R") //金额型
 			{
 				if (isDecimal(factorValue) == false) {
 					alert("簡單因子：" + factorName + " 必須是數字！");
 					return false;
 				}
 			} else if (factorAttr == "B") //布尔型
 			{
 				if (isBoolean(factorValue) == false) {
 					alert("簡單因子：" + factorName + " 的值必須是 N 或 Y 或 N,Y 或 Y,N！");
 					return false;
 				}
 			} else if (factorAttr == "C") //币别型
 			{
 				if (isCurrency(factorValue) == false) {
 					alert("簡單因子：" + factorName + " 的值必須是幣別代碼！");
 					return false;
 				}
 			}
 		}
 	}
 	return true;
 }

 function checkComboFactorValue() {
 	if (fm.combofactorFactorName == null) {
 		return true;
 	}
 	var comboLength = fm.combofactorFactorName.length;
 	if (comboLength > 0) {
 		var frontFactorName = "";
 		for (var i = 0; i < comboLength; i++) {
 			var factorName = fm.combofactorFactorName[i].value;
 			var factorValue = trim(fm.combofactorFactorValue[i].value);
 			var factorAttr = fm.combofactorFactorAttr[i].value;
 			//if(factorName == frontFactorName && factorValue == "")
 			//{
 			//	alert("组合因子：" + factorName + " 不能为空！");
 			//	return false;
 			//}
 			frontFactorName = factorName;
 			if (factorValue == "MAX" || factorValue == "max" || factorValue == "MIN" || factorValue == "min") {
 				continue;
 			}
 			if (factorAttr == "I") //整数型
 			{
 				if (isInteger(factorValue) == false) {
 					alert("組合因子：" + factorName + " 必須是整數！");
 					return false;
 				}
 			} else if (factorAttr == "M" || factorAttr == "R") //金额型
 			{
 				if (isDecimal(factorValue) == false) {
 					alert("組合因子：" + factorName + " 必須是數字！");
 					return false;
 				}
 			} else if (factorAttr == "B") //布尔型
 			{
 				if (isBoolean(factorValue) == false) {
 					alert("組合因子：" + factorName + " 的值必須是 N 或 Y 或 N,Y 或 Y,N！");
 					return false;
 				}
 			} else if (factorAttr == "C") //币别型
 			{
 				if (isCurrency(factorValue) == false) {
 					alert("組合因子：" + factorName + " 的值必須是幣別代碼！");
 					return false;
 				}
 			}
 		}
 	}
 	return true;
 }

 function isInteger(value) {
 	if (isNaN(value)) {
 		return false;
 	}
 	for (var k = 0; k < value.length; k++) {
 		var val = value.charAt(k);
 		if (val < '0' || val > '9') {
 			return false;
 		}
 	}
 	return true;
 }

 function isDecimal(value) {
 	if (isNaN(value)) {
 		return false;
 	}
 	for (var k = 0; k < value.length; k++) {
 		var val = value.charAt(k);
 		//if((val<'0' || val>'9') && val!='.')
 		if (val > '9' && val != '.') {
 			return false;
 		}
 	}
 	return true;
 }

 function isBoolean(value) {
 	if (value != "N" && value != "Y" && value != "") {
 		return false;
 	}
 	return true;
 }

 function isCurrency(value) {
 	if (value != "DEM" && value != "DKK" && value != "ECU" &&
 		value != "ESP" && value != "EUR" && value != "FIM" &&
 		value != "FRF" && value != "GBP" && value != "HKD" &&
 		value != "ITL" && value != "JPY" && value != "MOP" &&
 		value != "MYR" && value != "NLG" && value != "NOK" &&
 		value != "NTD" && value != "NZD" && value != "PHP" &&
 		value != "SDR" && value != "SEK" && value != "SGD" &&
 		value != "THB" && value != "AUD" && value != "BEF" &&
 		value != "ATS" && value != "USD" && value != "ASF" &&
 		value != "CAD" && value != "CHF" && value != "CNY") {
 		return false;
 	}
 	return true;
 }