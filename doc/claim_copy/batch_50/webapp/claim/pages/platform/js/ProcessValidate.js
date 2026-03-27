/****************************************************************************
 * DESC       ��Validate JavaScript
 ************************************************************************************/
var currentCheckField = null;
var currentMessage = "";
var public_validateFieldList = null;
var public_validateFormObject = null;
var public_validateCancelled = null;
//the next 8 vars can change value in application.js
var public_saveMessage = "Sure save?";
var public_webApplicationName = "platform";
var public_errorIndicatorImgClassName = "_errorImg";
var public_errorIndicatorImgAlt = "Click me to show error message.";
var public_errorIndicatorImgSrc = "/images/bgDoubleClick2.gif";
var public_errorIndicatorImgHeight = 10;
var public_errorIndicatorImgWidth = 10;
var public_errorIndicatorImgStyleCursor = "hand";
/**
 * @param form form
 * @return true/false
 * @since 2004-11-25
 */
function submitFormToSave(form) {
	if (!confirm(public_saveMessage)) {
		return false;
	}
	return validateForm(form);
}
/**
 * @param form form
 * @return true/false
 * @since 2005-01-12
 */
function submitFormWithoutConfirm(form) {
	return validateFm(form);
}
/**
 * Check to see if fields are a valid integer.
 * Fields are not checked if they are disabled.
 * <p>
 * @param form The form validation is taking place on.
 */
function validateInt(form) {
	//===============MODIFY:add var begin
	var testResult = true; //store temp test result
	//---------------add var end
	var isValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	var oInteger = eval("new " + jcv_retrieveFormName(form) + "_integer()");
	for (var x in oInteger) {
		if (!jcv_verifyArrayElement(x, oInteger[x])) {
			continue;
		}

		//================MODIFY: add multiLine support begin
		//the input maybe an array
		var getVar = oInteger[x][2];
		var isMultiLine = false;
		if (getVar("multiLine") == "true") {
			isMultiLine = true;
		}
		var loopFields = form[oInteger[x][0]];
		if (!jcv_isFieldPresent(loopFields)) { //input not present
			continue;
		}
		var fieldsCount = loopFields.length;
		if (isMultiLine) {
			if (fieldsCount == undefined) { //is not array
				fieldsCount = 1;
				var tempLoopField = loopFields;
				loopFields = new Array(tempLoopField);
			}
		} else {
			fieldsCount = 1;
			var tempLoopField = loopFields;
			loopFields = new Array(tempLoopField);
		}
		for (var index = 0; index < fieldsCount; index++) {
			var field = loopFields[index];
			//must set isValid default value true              
			if (index > 0 && isValid == false) {
				isValid = true;
				testResult = false;
			}
			//only check currentCheckField if it's not null     
			if (currentCheckField != null && currentCheckField != field) {
				continue;
			}

			//skip the first input if isMultiLine
			if (isMultiLine && index == 0) {
				continue;
			}
			//var field = form[oInteger[x][0]];
			//---------------add multiLine support end
			if (!jcv_isFieldPresent(field)) {
				continue;
			}
			if ((field.type == "hidden" || field.type == "text" || field.type == "textarea" || field.type == "select-one" || field.type == "radio")) {
				var value = "";
				// get field's value
				if (field.type == "select-one") {
					var si = field.selectedIndex;
					if (si >= 0) {
						value = field.options[si].value;
					}
				} else {
					value = field.value;
				}
				if (value.length > 0) {
					if (!jcv_isDecimalDigits(value)) {
						if (i == 0) {
							focusField = field;
						}
						fields[i++] = oInteger[x][1];
						isValid = false;
					} else {
						var iValue = parseInt(value, 10);
						if (isNaN(iValue) || !(iValue >= -2147483648 && iValue <= 2147483647)) {
							if (i == 0) {
								focusField = field;
							}
							fields[i++] = oInteger[x][1];
							isValid = false;
						}
					}
				}
				//==============MODIFY: add a } begin
			}
			//--------------add a } end

			//==============MODIFY: add to change color begin
			jcv_sustome_processFieldAfterValid(isValid, field, fields, i - 1);
			//--------------add to change color end
		}
	}
	if (fields.length > 0) {
		jcv_handleErrors(fields, focusField);
	}
	//==============MODIFY: add test temp result begin
	if (testResult == false) {
		isValid = false;
	}
	//---------------MODIFY: add test temp result end
	return isValid;
}
/**
 * Check to see if fields are a valid using a regular expression.
 * Fields are not checked if they are disabled.
 * <p>
 * @param form The form validation is taking place on.
 */
function validateMask(form) {
	var testResult = true; //store temp test result
	var isValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	var oMasked = eval("new " + jcv_retrieveFormName(form) + "_mask()");
	for (var x in oMasked) {
		if (!jcv_verifyArrayElement(x, oMasked[x])) {
			continue;
		}
		//the input maybe an array
		var getVar = oMasked[x][2];
		var isMultiLine = false;
		if (getVar("multiLine") == "true") {
			isMultiLine = true;
		}
		var loopFields = form[oMasked[x][0]];
		if (!jcv_isFieldPresent(loopFields)) { //input not present
			continue;
		}
		var fieldsCount = loopFields.length;
		if (isMultiLine) {
			if (fieldsCount == undefined) { //is not array
				fieldsCount = 1;
				var tempLoopField = loopFields;
				loopFields = new Array(tempLoopField);
			}
		} else {
			fieldsCount = 1;
			var tempLoopField = loopFields;
			loopFields = new Array(tempLoopField);
		}
		for (var index = 0; index < fieldsCount; index++) {
			var field = loopFields[index];
			//must set isValid default value true              
			if (index > 0 && isValid == false) {
				isValid = true;
				testResult = false;
			}
			//only check currentCheckField if it's not null     
			if (currentCheckField != null && currentCheckField != field) {
				continue;
			}

			//skip the first input if isMultiLine
			if (isMultiLine && index == 0) {
				continue;
			}
			if (!jcv_isFieldPresent(field)) {
				continue;
			}
			if ((field.type == "hidden" || field.type == "text" || field.type == "textarea" || field.type == "file") && (field.value.length > 0)) {
				if (!jcv_matchPattern(field.value, oMasked[x][2]("mask"))) {
					if (i == 0) {
						focusField = field;
					}
					fields[i++] = oMasked[x][1];
					isValid = false;
				}
			}
			jcv_sustome_processFieldAfterValid(isValid, field, fields, i - 1);
		}
	}
	if (fields.length > 0) {
		jcv_handleErrors(fields, focusField);
	}
	if (testResult == false) {
		isValid = false;
	}
	return isValid;
}

function jcv_matchPattern(value, mask) {
	return mask.exec(value);
}
/**
 *  Check to see if fields are a valid short.
 * Fields are not checked if they are disabled.
 * <p>
 * @param form The form validation is taking place on.
 */
function validateShort(form) {
	var testResult = true; //store temp test result
	var isValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	var oShort = eval("new " + jcv_retrieveFormName(form) + "_short()");
	for (var x in oShort) {
		if (!jcv_verifyArrayElement(x, oShort[x])) {
			continue;
		}
		//the input maybe an array
		var getVar = oShort[x][2];
		var isMultiLine = false;
		if (getVar("multiLine") == "true") {
			isMultiLine = true;
		}
		var loopFields = form[oShort[x][0]];
		if (!jcv_isFieldPresent(loopFields)) { //input not present
			continue;
		}
		var fieldsCount = loopFields.length;
		if (isMultiLine) {
			if (fieldsCount == undefined) { //is not array
				fieldsCount = 1;
				var tempLoopField = loopFields;
				loopFields = new Array(tempLoopField);
			}
		} else {
			fieldsCount = 1;
			var tempLoopField = loopFields;
			loopFields = new Array(tempLoopField);
		}
		for (var index = 0; index < fieldsCount; index++) {
			var field = loopFields[index];
			//must set isValid default value true              
			if (index > 0 && isValid == false) {
				isValid = true;
				testResult = false;
			}
			//only check currentCheckField if it's not null     
			if (currentCheckField != null && currentCheckField != field) {
				continue;
			}

			//skip the first input if isMultiLine
			if (isMultiLine && index == 0) {
				continue;
			}
			if (!jcv_isFieldPresent(field)) {
				continue;
			}
			if ((field.type == "hidden" || field.type == "text" || field.type == "textarea" || field.type == "select-one" || field.type == "radio")) {
				var value = "";
				// get field's value
				if (field.type == "select-one") {
					var si = field.selectedIndex;
					if (si >= 0) {
						value = field.options[si].value;
					}
				} else {
					value = field.value;
				}
				if (value.length > 0) {
					if (!jcv_isDecimalDigits(value)) {
						isValid = false;
						if (i == 0) {
							focusField = field;
						}
						fields[i++] = oShort[x][1];
					} else {
						var iValue = parseInt(value, 10);
						if (isNaN(iValue) || !(iValue >= -32768 && iValue <= 32767)) {
							if (i == 0) {
								focusField = field;
							}
							fields[i++] = oShort[x][1];
							isValid = false;
						}
					}
				}
			}
			jcv_sustome_processFieldAfterValid(isValid, field, fields, i - 1);
		}
	}
	if (fields.length > 0) {
		jcv_handleErrors(fields, focusField);
	}
	if (testResult == false) {
		isValid = false;
	}
	return isValid;
}
/**
 * Check to see if fields are a valid byte.
 * Fields are not checked if they are disabled.
 * <p>
 * @param form The form validation is taking place on.
 */
function validateByte(form) {
	var testResult = true; //store temp test result
	var isValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	var oByte = eval("new " + jcv_retrieveFormName(form) + "_byte()");
	for (var x in oByte) {
		if (!jcv_verifyArrayElement(x, oByte[x])) {
			continue;
		}
		//the input maybe an array
		var getVar = oByte[x][2];
		var isMultiLine = false;
		if (getVar("multiLine") == "true") {
			isMultiLine = true;
		}
		var loopFields = form[oByte[x][0]];
		if (!jcv_isFieldPresent(loopFields)) { //input not present
			continue;
		}
		var fieldsCount = loopFields.length;
		if (isMultiLine) {
			if (fieldsCount == undefined) { //is not array
				fieldsCount = 1;
				var tempLoopField = loopFields;
				loopFields = new Array(tempLoopField);
			}
		} else {
			fieldsCount = 1;
			var tempLoopField = loopFields;
			loopFields = new Array(tempLoopField);
		}
		for (var index = 0; index < fieldsCount; index++) {
			var field = loopFields[index];
			//must set isValid default value true              
			if (index > 0 && isValid == false) {
				isValid = true;
				testResult = false;
			}
			//only check currentCheckField if it's not null     
			if (currentCheckField != null && currentCheckField != field) {
				continue;
			}

			//skip the first input if isMultiLine
			if (isMultiLine && index == 0) {
				continue;
			}
			if (!jcv_isFieldPresent(field)) {
				continue;
			}
			if ((field.type == "hidden" || field.type == "text" || field.type == "textarea" || field.type == "select-one" || field.type == "radio")) {
				var value = "";
				// get field's value
				if (field.type == "select-one") {
					var si = field.selectedIndex;
					if (si >= 0) {
						value = field.options[si].value;
					}
				} else {
					value = field.value;
				}
				if (value.length > 0) {
					if (!jcv_isDecimalDigits(value)) {
						isValid = false;
						if (i == 0) {
							focusField = field;
						}
						fields[i++] = oByte[x][1];
					} else {
						var iValue = parseInt(value, 10);
						if (isNaN(iValue) || !(iValue >= -128 && iValue <= 127)) {
							if (i == 0) {
								focusField = field;
							}
							fields[i++] = oByte[x][1];
							isValid = false;
						}
					}
				}
			}
			jcv_sustome_processFieldAfterValid(isValid, field, fields, i - 1);
		}
	}
	if (fields.length > 0) {
		jcv_handleErrors(fields, focusField);
	}
	if (testResult == false) {
		isValid = false;
	}
	return isValid;
}
/**
 * Check to see if fields are a valid creditcard number based on Luhn checksum.
 * Fields are not checked if they are disabled.
 * <p>
 * @param form The form validation is taking place on.
 */
function validateCreditCard(form) {
	var testResult = true; //store temp test result
	var isValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	var oCreditCard = eval("new " + jcv_retrieveFormName(form) + "_creditCard()");
	for (var x in oCreditCard) {
		if (!jcv_verifyArrayElement(x, oCreditCard[x])) {
			continue;
		} //the input maybe an array
		var getVar = oCreditCard[x][2];
		var isMultiLine = false;
		if (getVar("multiLine") == "true") {
			isMultiLine = true;
		}
		var loopFields = form[oCreditCard[x][0]];
		if (!jcv_isFieldPresent(loopFields)) { //input not present
			continue;
		}
		var fieldsCount = loopFields.length;
		if (isMultiLine) {
			if (fieldsCount == undefined) { //is not array
				fieldsCount = 1;
				var tempLoopField = loopFields;
				loopFields = new Array(tempLoopField);
			}
		} else {
			fieldsCount = 1;
			var tempLoopField = loopFields;
			loopFields = new Array(tempLoopField);
		}
		for (var index = 0; index < fieldsCount; index++) {
			var field = loopFields[index];
			//must set isValid default value true              
			if (index > 0 && isValid == false) {
				isValid = true;
				testResult = false;
			}
			//only check currentCheckField if it's not null     
			if (currentCheckField != null && currentCheckField != field) {
				continue;
			}

			//skip the first input if isMultiLine
			if (isMultiLine && index == 0) {
				continue;
			}
			if (!jcv_isFieldPresent(field)) {
				continue;
			}
			if ((field.type == "text" || field.type == "textarea") && (field.value.length > 0)) {
				if (!jcv_luhnCheck(field.value)) {
					if (i == 0) {
						focusField = field;
					}
					fields[i++] = oCreditCard[x][1];
					isValid = false;
				}
			}
		}
		jcv_sustome_processFieldAfterValid(isValid, field, fields, i - 1);
	}
	if (fields.length > 0) {
		jcv_handleErrors(fields, focusField);
	}
	if (testResult == false) {
		isValid = false;
	}
	return isValid;
}
/**
 * Checks whether a given credit card number has a valid Luhn checksum.
 * This allows you to spot most randomly made-up or garbled credit card numbers immediately.
 * Reference: http://www.speech.cs.cmu.edu/~sburke/pub/luhn_lib.html
 */
function jcv_luhnCheck(cardNumber) {
	if (jcv_isLuhnNum(cardNumber)) {
		var no_digit = cardNumber.length;
		var oddoeven = no_digit & 1;
		var sum = 0;
		for (var count = 0; count < no_digit; count++) {
			var digit = parseInt(cardNumber.charAt(count));
			if (!((count & 1) ^ oddoeven)) {
				digit *= 2;
				if (digit > 9) {
					digit -= 9;
				}
			}
			sum += digit;
		}
		if (sum == 0) {
			return false;
		}
		if (sum % 10 == 0) {
			return true;
		}
	}
	return false;
}

function jcv_isLuhnNum(argvalue) {
	argvalue = argvalue.toString();
	if (argvalue.length == 0) {
		return false;
	}
	for (var n = 0; n < argvalue.length; n++) {
		if ((argvalue.substring(n, n + 1) < "0") || (argvalue.substring(n, n + 1) > "9")) {
			return false;
		}
	}
	return true;
}
/**
 * Check to see if fields are a valid float.
 * Fields are not checked if they are disabled.
 * <p>
 * @param form The form validation is taking place on.
 */
function validateFloat(form) {
	var testResult = true; //store temp test result
	var isValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	var oFloat = eval("new " + jcv_retrieveFormName(form) + "_float()");
	for (var x in oFloat) {
		if (!jcv_verifyArrayElement(x, oFloat[x])) {
			continue;
		} //the input maybe an array
		var getVar = oFloat[x][2];
		var isMultiLine = false;
		if (getVar("multiLine") == "true") {
			isMultiLine = true;
		}
		var loopFields = form[oFloat[x][0]];
		if (!jcv_isFieldPresent(loopFields)) { //input not present
			continue;
		}
		var fieldsCount = loopFields.length;
		if (isMultiLine) {
			if (fieldsCount == undefined) { //is not array
				fieldsCount = 1;
				var tempLoopField = loopFields;
				loopFields = new Array(tempLoopField);
			}
		} else {
			fieldsCount = 1;
			var tempLoopField = loopFields;
			loopFields = new Array(tempLoopField);
		}
		for (var index = 0; index < fieldsCount; index++) {
			var field = loopFields[index];
			//must set isValid default value true              
			if (index > 0 && isValid == false) {
				isValid = true;
				testResult = false;
			}
			//only check currentCheckField if it's not null     
			if (currentCheckField != null && currentCheckField != field) {
				continue;
			}

			//skip the first input if isMultiLine
			if (isMultiLine && index == 0) {
				continue;
			}
			if (!jcv_isFieldPresent(field)) {
				continue;
			}
			if ((field.type == "hidden" || field.type == "text" || field.type == "textarea" || field.type == "select-one" || field.type == "radio")) {
				var value = "";
				// get field's value
				if (field.type == "select-one") {
					var si = field.selectedIndex;
					if (si >= 0) {
						value = field.options[si].value;
					}
				} else {
					value = field.value;
				}
				if (value.length > 0) {
					// remove '.' before checking digits
					var tempArray = value.split(".");
					//Strip off leading '0'
					var zeroIndex = 0;
					var joinedString = tempArray.join("");
					while (joinedString.charAt(zeroIndex) == "0") {
						zeroIndex++;
					}
					var noZeroString = joinedString.substring(zeroIndex, joinedString.length);
					if (!jcv_isAllDigits(noZeroString) || tempArray.length > 2) {
						isValid = false;
						if (i == 0) {
							focusField = field;
						}
						fields[i++] = oFloat[x][1];
					} else {
						var iValue = parseFloat(value);
						if (isNaN(iValue)) {
							if (i == 0) {
								focusField = field;
							}
							fields[i++] = oFloat[x][1];
							isValid = false;
						}
					}
				}
			}
			jcv_sustome_processFieldAfterValid(isValid, field, fields, i - 1);
		}
	}
	if (fields.length > 0) {
		jcv_handleErrors(fields, focusField);
	}
	if (testResult == false) {
		isValid = false;
	}
	return isValid;
}
/**
 * Check to see if fields is in a valid integer range.
 * Fields are not checked if they are disabled.
 * <p>
 * @param form The form validation is taking place on.
 */
function validateIntRange(form) {
	var testResult = true; //store temp test result
	var isValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	var oRange = eval("new " + jcv_retrieveFormName(form) + "_intRange()");
	for (var x in oRange) {
		if (!jcv_verifyArrayElement(x, oRange[x])) {
			continue;
		} //the input maybe an array
		var getVar = oRange[x][2];
		var isMultiLine = false;
		if (getVar("multiLine") == "true") {
			isMultiLine = true;
		}
		var loopFields = form[oRange[x][0]];
		if (!jcv_isFieldPresent(loopFields)) { //input not present
			continue;
		}
		var fieldsCount = loopFields.length;
		if (isMultiLine) {
			if (fieldsCount == undefined) { //is not array
				fieldsCount = 1;
				var tempLoopField = loopFields;
				loopFields = new Array(tempLoopField);
			}
		} else {
			fieldsCount = 1;
			var tempLoopField = loopFields;
			loopFields = new Array(tempLoopField);
		}
		for (var index = 0; index < fieldsCount; index++) {
			var field = loopFields[index];
			//must set isValid default value true              
			if (index > 0 && isValid == false) {
				isValid = true;
				testResult = false;
			}
			//only check currentCheckField if it's not null     
			if (currentCheckField != null && currentCheckField != field) {
				continue;
			}

			//skip the first input if isMultiLine
			if (isMultiLine && index == 0) {
				continue;
			}
			if (jcv_isFieldPresent(field)) {
				var value = "";
				if (field.type == "hidden" || field.type == "text" || field.type == "textarea" || field.type == "radio") {
					value = field.value;
				}
				if (field.type == "select-one") {
					var si = field.selectedIndex;
					if (si >= 0) {
						value = field.options[si].value;
					}
				}
				if (value.length > 0) {
					var iMin = parseInt(oRange[x][2]("min"));
					var iMax = parseInt(oRange[x][2]("max"));
					var iValue = parseInt(value, 10);
					if (!(iValue >= iMin && iValue <= iMax)) {
						if (i == 0) {
							focusField = field;
						}
						fields[i++] = oRange[x][1];
						isValid = false;
					}
				}
			}
			jcv_sustome_processFieldAfterValid(isValid, field, fields, i - 1);
		}
	}
	if (fields.length > 0) {
		jcv_handleErrors(fields, focusField);
	}
	if (testResult == false) {
		isValid = false;
	}
	return isValid;
}
/**
 * This is a place holder for common utilities used across the javascript validation
 *
 **/
/**
 * Retreive the name of the form
 * @param form The form validation is taking place on.
 */
function jcv_retrieveFormName(form) {

	// Please refer to Bugs 31534, 35127, 35294, 37315 & 38159
	// for the history of the following code
	var formName;
	if (form.getAttributeNode) {
		if (form.getAttributeNode("id") && form.getAttributeNode("id").value) {
			formName = form.getAttributeNode("id").value;
		} else {
			formName = form.getAttributeNode("name").value;
		}
	} else {
		if (form.getAttribute) {
			if (form.getAttribute("id")) {
				formName = form.getAttribute("id");
			} else {
				formName = form.attributes["name"];
			}
		} else {
			if (form.id) {
				formName = form.id;
			} else {
				formName = form.name;
			}
		}
	}
	return formName;
}
/**
 * Handle error messages.
 * @param messages Array of error messages.
 * @param focusField Field to set focus on.
 */
function jcv_handleErrors(messages, focusField) {
	if (currentCheckField == null) {
		if (focusField && focusField != null) {
			var doFocus = true;
			if (focusField.disabled || focusField.type == "hidden") {
				doFocus = false;
			}
			if (doFocus && focusField.style && focusField.style.visibility && focusField.style.visibility == "hidden") {
				doFocus = false;
			}
			if (doFocus) {
				setFocus(focusField);
			}
		}
		alert(messages.join("\n"));
	} else {
		currentMessage = messages;
		log(messages);
	}
}
/**
 * Checks that the array element is a valid
 * Commons Validator element and not one inserted by
 * other JavaScript libraries (for example the
 * prototype library inserts an "extends" into
 * all objects, including Arrays).
 * @param name The element name.
 * @param value The element value.
 */
function jcv_verifyArrayElement(name, element) {
	if (element && element.length && element.length == 3) {
		return true;
	} else {
		return false;
	}
}
/**
 * Checks whether the field is present on the form.
 * @param field The form field.
 */
function jcv_isFieldPresent(field) {
	var fieldPresent = true;
	if (field == null || field == undefined) {
		fieldPresent = false;
	} else {
		if (field.disabled) {
			fieldPresent = false;
		}
	}
	return fieldPresent;
}
/**
 * Check a value only contains valid numeric digits
 * @param argvalue The value to check.
 */
function jcv_isAllDigits(argvalue) {
	argvalue = argvalue.toString();
	var validChars = "0123456789";
	var startFrom = 0;
	if (argvalue.substring(0, 2) == "0x") {
		validChars = "0123456789abcdefABCDEF";
		startFrom = 2;
	} else {
		if (argvalue.charAt(0) == "0") {
			validChars = "01234567";
			startFrom = 1;
		} else {
			if (argvalue.charAt(0) == "-") {
				startFrom = 1;
			}
		}
	}
	for (var n = startFrom; n < argvalue.length; n++) {
		if (validChars.indexOf(argvalue.substring(n, n + 1)) == -1) {
			return false;
		}
	}
	return true;
}
/**
 * Check a value only contains valid decimal digits
 * @param argvalue The value to check.
 */
function jcv_isDecimalDigits(argvalue) {
	argvalue = argvalue.toString();
	var validChars = "0123456789";
	var startFrom = 0;
	if (argvalue.charAt(0) == "-") {
		startFrom = 1;
	}
	for (var n = startFrom; n < argvalue.length; n++) {
		if (validChars.indexOf(argvalue.substring(n, n + 1)) == -1) {
			return false;
		}
	}
	return true;
}
/**
 * Check to see if fields are a valid date.
 * Fields are not checked if they are disabled.
 * <p>
 * @param form The form validation is taking place on.
 */
function validateDate(form) {
	var testResult = true; //store temp test result
	var isValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	var oDate = eval("new " + jcv_retrieveFormName(form) + "_date()");
	for (var x in oDate) {
		if (!jcv_verifyArrayElement(x, oDate[x])) {
			continue;
		}
		//the input maybe an array
		var getVar = oDate[x][2];
		var isMultiLine = false;
		if (getVar("multiLine") == "true") {
			isMultiLine = true;
		}
		var loopFields = form[oDate[x][0]];
		if (!jcv_isFieldPresent(loopFields)) { //input not present
			continue;
		}
		var fieldsCount = loopFields.length;
		if (isMultiLine) {
			if (fieldsCount == undefined) { //is not array
				fieldsCount = 1;
				var tempLoopField = loopFields;
				loopFields = new Array(tempLoopField);
			}
		} else {
			fieldsCount = 1;
			var tempLoopField = loopFields;
			loopFields = new Array(tempLoopField);
		}
		for (var index = 0; index < fieldsCount; index++) {
			var field = loopFields[index];
			//must set isValid default value true              
			if (index > 0 && isValid == false) {
				isValid = true;
				testResult = false;
			}
			//only check currentCheckField if it's not null     
			if (currentCheckField != null && currentCheckField != field) {
				continue;
			}

			//skip the first input if isMultiLine
			if (isMultiLine && index == 0) {
				continue;
			}
			if (!jcv_isFieldPresent(field)) {
				continue;
			}
			var value = field.value;
			var isStrict = true;
			var datePattern = oDate[x][2]("datePatternStrict");
			// try loose pattern
			if (datePattern == null) {
				datePattern = oDate[x][2]("datePattern");
				isStrict = false;
			}
			if ((field.type == "hidden" || field.type == "text" || field.type == "textarea") && (value.length > 0) && (datePattern.length > 0)) {
				var MONTH = "MM";
				var DAY = "dd";
				var YEAR = "yyyy";
				var orderMonth = datePattern.indexOf(MONTH);
				var orderDay = datePattern.indexOf(DAY);
				var orderYear = datePattern.indexOf(YEAR);
				if ((orderDay < orderYear && orderDay > orderMonth)) {
					var iDelim1 = orderMonth + MONTH.length;
					var iDelim2 = orderDay + DAY.length;
					var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
					var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
					if (iDelim1 == orderDay && iDelim2 == orderYear) {
						dateRegexp = isStrict ? new RegExp("^(\\d{2})(\\d{2})(\\d{4})$") : new RegExp("^(\\d{1,2})(\\d{1,2})(\\d{4})$");
					} else {
						if (iDelim1 == orderDay) {
							dateRegexp = isStrict ? new RegExp("^(\\d{2})(\\d{2})[" + delim2 + "](\\d{4})$") : new RegExp("^(\\d{1,2})(\\d{1,2})[" + delim2 + "](\\d{4})$");
						} else {
							if (iDelim2 == orderYear) {
								dateRegexp = isStrict ? new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})(\\d{4})$") : new RegExp("^(\\d{1,2})[" + delim1 + "](\\d{1,2})(\\d{4})$");
							} else {
								dateRegexp = isStrict ? new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{4})$") : new RegExp("^(\\d{1,2})[" + delim1 + "](\\d{1,2})[" + delim2 + "](\\d{4})$");
							}
						}
					}
					var matched = dateRegexp.exec(value);
					if (matched != null) {
						if (!jcv_isValidDate(matched[2], matched[1], matched[3])) {
							if (i == 0) {
								focusField = field;
							}
							fields[i++] = oDate[x][1];
							isValid = false;
						}
					} else {
						if (i == 0) {
							focusField = field;
						}
						fields[i++] = oDate[x][1];
						isValid = false;
					}
				} else {
					if ((orderMonth < orderYear && orderMonth > orderDay)) {
						var iDelim1 = orderDay + DAY.length;
						var iDelim2 = orderMonth + MONTH.length;
						var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
						var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
						if (iDelim1 == orderMonth && iDelim2 == orderYear) {
							dateRegexp = isStrict ? new RegExp("^(\\d{2})(\\d{2})(\\d{4})$") : new RegExp("^(\\d{1,2})(\\d{1,2})(\\d{4})$");
						} else {
							if (iDelim1 == orderMonth) {
								dateRegexp = isStrict ? new RegExp("^(\\d{2})(\\d{2})[" + delim2 + "](\\d{4})$") : new RegExp("^(\\d{1,2})(\\d{1,2})[" + delim2 + "](\\d{4})$");
							} else {
								if (iDelim2 == orderYear) {
									dateRegexp = isStrict ? new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})(\\d{4})$") : new RegExp("^(\\d{1,2})[" + delim1 + "](\\d{1,2})(\\d{4})$");
								} else {
									dateRegexp = isStrict ? new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{4})$") : new RegExp("^(\\d{1,2})[" + delim1 + "](\\d{1,2})[" + delim2 + "](\\d{4})$");
								}
							}
						}
						var matched = dateRegexp.exec(value);
						if (matched != null) {
							if (!jcv_isValidDate(matched[1], matched[2], matched[3])) {
								if (i == 0) {
									focusField = field;
								}
								fields[i++] = oDate[x][1];
								isValid = false;
							}
						} else {
							if (i == 0) {
								focusField = field;
							}
							fields[i++] = oDate[x][1];
							isValid = false;
						}
					} else {
						if ((orderMonth > orderYear && orderMonth < orderDay)) {
							var iDelim1 = orderYear + YEAR.length;
							var iDelim2 = orderMonth + MONTH.length;
							var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
							var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
							if (iDelim1 == orderMonth && iDelim2 == orderDay) {
								dateRegexp = isStrict ? new RegExp("^(\\d{4})(\\d{2})(\\d{2})$") : new RegExp("^(\\d{4})(\\d{1,2})(\\d{1,2})$");
							} else {
								if (iDelim1 == orderMonth) {
									dateRegexp = isStrict ? new RegExp("^(\\d{4})(\\d{2})[" + delim2 + "](\\d{2})$") : new RegExp("^(\\d{4})(\\d{1,2})[" + delim2 + "](\\d{1,2})$");
								} else {
									if (iDelim2 == orderDay) {
										dateRegexp = isStrict ? new RegExp("^(\\d{4})[" + delim1 + "](\\d{2})(\\d{2})$") : new RegExp("^(\\d{4})[" + delim1 + "](\\d{1,2})(\\d{1,2})$");
									} else {
										dateRegexp = isStrict ? new RegExp("^(\\d{4})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{2})$") : new RegExp("^(\\d{4})[" + delim1 + "](\\d{1,2})[" + delim2 + "](\\d{1,2})$");
									}
								}
							}
							var matched = dateRegexp.exec(value);
							if (matched != null) {
								if (!jcv_isValidDate(matched[3], matched[2], matched[1])) {
									if (i == 0) {
										focusField = field;
									}
									fields[i++] = oDate[x][1];
									isValid = false;
								}
							} else {
								if (i == 0) {
									focusField = field;
								}
								fields[i++] = oDate[x][1];
								isValid = false;
							}
						} else {
							if (i == 0) {
								focusField = field;
							}
							fields[i++] = oDate[x][1];
							isValid = false;
						}
					}
				}
			}
			jcv_sustome_processFieldAfterValid(isValid, field, fields, i - 1);
		}
	}
	if (fields.length > 0) {
		jcv_handleErrors(fields, focusField);
	}
	if (testResult == false) {
		isValid = false;
	}
	return isValid;
}

function jcv_isValidDate(day, month, year) {
	if (month < 1 || month > 12) {
		return false;
	}
	if (day < 1 || day > 31) {
		return false;
	}
	if ((month == 4 || month == 6 || month == 9 || month == 11) && (day == 31)) {
		return false;
	}
	if (month == 2) {
		var leap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
		if (day > 29 || (day == 29 && !leap)) {
			return false;
		}
	}
	return true;
}
/**
 *  Check to see if fields must contain a value.
 * Fields are not checked if they are disabled.
 * <p>
 * @param form The form validation is taking place on.
 */
function validateRequired(form) {
	var testResult = true;
	var isValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	var oRequired = eval("new " + jcv_retrieveFormName(form) + "_required()");
	for (var x in oRequired) {
		if (!jcv_verifyArrayElement(x, oRequired[x])) {
			continue;
		}
		//the input maybe an array
		var getVar = oRequired[x][2];
		var isMultiLine = false;
		if (getVar("multiLine") == "true") {
			isMultiLine = true;
		}
		var loopFields = form[oRequired[x][0]];
		if (!jcv_isFieldPresent(loopFields)) { //input not present
			continue;
		}
		var fieldsCount = loopFields.length;
		if (isMultiLine) {
			if (fieldsCount == undefined) { //is not array
				fieldsCount = 1;
				var tempLoopField = loopFields;
				loopFields = new Array(tempLoopField);
			}
		} else {
			fieldsCount = 1;
			var tempLoopField = loopFields;
			loopFields = new Array(tempLoopField);
		}
		for (var index = 0; index < fieldsCount; index++) {
			var field = loopFields[index];
			//must set isValid default value true
			if (index > 0 && isValid == false) {
				isValid = true;
				testResult = false;
			}
			//only check currentCheckField if it's not null     
			if (currentCheckField != null && currentCheckField != field) {
				continue;
			}
			//skip the first input if isMultiLine
			if (isMultiLine && index == 0) {
				continue;
			}
			if (!jcv_isFieldPresent(field)) {
				fields[i++] = oRequired[x][1];
				isValid = false;
			} else {
				if ((field.type == "hidden" || field.type == "text" || field.type == "textarea" || field.type == "file" || field.type == "radio" || field.type == "checkbox" || field.type == "select-one" || field.type == "password")) {
					var value = "";
					// get field's value
					if (field.type == "select-one") {
						var si = field.selectedIndex;
						if (si >= 0) {
							value = field.options[si].value;
						}
					} else {
						if (field.type == "radio" || field.type == "checkbox") {
							if (field.checked) {
								value = field.value;
							}
						} else {
							value = field.value;
						}
					}
					if (trim(value).length == 0) {
						if ((i == 0) && (field.type != "hidden")) {
							focusField = field;
						}
						fields[i++] = oRequired[x][1];
						isValid = false;
					}
				} else {
					if (field.type == "select-multiple") {
						var numOptions = field.options.length;
						lastSelected = -1;
						for (loop = numOptions - 1; loop >= 0; loop--) {
							if (field.options[loop].selected) {
								lastSelected = loop;
								value = field.options[loop].value;
								break;
							}
						}
						if (lastSelected < 0 || trim(value).length == 0) {
							if (i == 0) {
								focusField = field;
							}
							fields[i++] = oRequired[x][1];
							isValid = false;
						}
					} else {
						if ((field.length > 0) && (field[0].type == "radio" || field[0].type == "checkbox")) {
							isChecked = -1;
							for (loop = 0; loop < field.length; loop++) {
								if (field[loop].checked) {
									isChecked = loop;
									break; // only one needs to be checked
								}
							}
							if (isChecked < 0) {
								if (i == 0) {
									focusField = field[0];
								}
								fields[i++] = oRequired[x][1];
								isValid = false;
							}
						}
					}
				}
			}
			jcv_sustome_processFieldAfterValid(isValid, field, fields, i - 1);
		}
	}
	if (fields.length > 0) {
		jcv_handleErrors(fields, focusField);
	}
	if (testResult == false) {
		isValid = false;
	}
	return isValid;
}
/**
 * Check to see if fields are in a valid float range.
 * Fields are not checked if they are disabled.
 * <p>
 * @param form The form validation is taking place on.
 */
function validateFloatRange(form) {
	var testResult = true; //store temp test result
	var isValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	var oRange = eval("new " + jcv_retrieveFormName(form) + "_floatRange()");
	for (var x in oRange) {
		if (!jcv_verifyArrayElement(x, oRange[x])) {
			continue;
		}
		//the input maybe an array
		var getVar = oRange[x][2];
		var isMultiLine = false;
		if (getVar("multiLine") == "true") {
			isMultiLine = true;
		}
		var loopFields = form[oRange[x][0]];
		if (!jcv_isFieldPresent(loopFields)) { //input not present
			continue;
		}
		var fieldsCount = loopFields.length;
		if (isMultiLine) {
			if (fieldsCount == undefined) { //is not array
				fieldsCount = 1;
				var tempLoopField = loopFields;
				loopFields = new Array(tempLoopField);
			}
		} else {
			fieldsCount = 1;
			var tempLoopField = loopFields;
			loopFields = new Array(tempLoopField);
		}
		for (var index = 0; index < fieldsCount; index++) {
			var field = loopFields[index];
			//must set isValid default value true              
			if (index > 0 && isValid == false) {
				isValid = true;
				testResult = false;
			}
			//only check currentCheckField if it's not null     
			if (currentCheckField != null && currentCheckField != field) {
				continue;
			}

			//skip the first input if isMultiLine
			if (isMultiLine && index == 0) {
				continue;
			}
			if (!jcv_isFieldPresent(field)) {
				continue;
			}
			if ((field.type == "hidden" || field.type == "text" || field.type == "textarea") && (field.value.length > 0)) {
				var fMin = parseFloat(oRange[x][2]("min"));
				var fMax = parseFloat(oRange[x][2]("max"));
				var fValue = parseFloat(field.value);
				if (!(fValue >= fMin && fValue <= fMax)) {
					if (i == 0) {
						focusField = field;
					}
					fields[i++] = oRange[x][1];
					isValid = false;
				}
			}
			jcv_sustome_processFieldAfterValid(isValid, field, fields, i - 1);
		}
	}
	if (fields.length > 0) {
		jcv_handleErrors(fields, focusField);
	}
	if (testResult == false) {
		isValid = false;
	}
	return isValid;
}
/**
 * A field is considered valid if greater than the specified minimum.
 * Fields are not checked if they are disabled.
 * <p>
 * <strong>Caution:</strong> Using <code>validateMinLength</code> on a password field in a
 *  login page gives unnecessary information away to hackers. While it only slightly
 *  weakens security, we suggest using it only when modifying a password.</p>
 * @param form The form validation is taking place on.
 */
function validateMinLength(form) {
	var testResult = true; //store temp test result
	var isValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	var oMinLength = eval("new " + jcv_retrieveFormName(form) + "_minLength()");
	for (var x in oMinLength) {
		if (!jcv_verifyArrayElement(x, oMinLength[x])) {
			continue;
		}
		//the input maybe an array
		var getVar = oMinLength[x][2];
		var isMultiLine = false;
		if (getVar("multiLine") == "true") {
			isMultiLine = true;
		}
		var loopFields = form[oMinLength[x][0]];
		if (!jcv_isFieldPresent(loopFields)) { //input not present
			continue;
		}
		var fieldsCount = loopFields.length;
		if (isMultiLine) {
			if (fieldsCount == undefined) { //is not array
				fieldsCount = 1;
				var tempLoopField = loopFields;
				loopFields = new Array(tempLoopField);
			}
		} else {
			fieldsCount = 1;
			var tempLoopField = loopFields;
			loopFields = new Array(tempLoopField);
		}
		for (var index = 0; index < fieldsCount; index++) {
			var field = loopFields[index];
			//must set isValid default value true              
			if (index > 0 && isValid == false) {
				isValid = true;
				testResult = false;
			}
			//only check currentCheckField if it's not null     
			if (currentCheckField != null && currentCheckField != field) {
				continue;
			}

			//skip the first input if isMultiLine
			if (isMultiLine && index == 0) {
				continue;
			}
			if (!jcv_isFieldPresent(field)) {
				continue;
			}
			if ((field.type == "hidden" || field.type == "text" || field.type == "password" || field.type == "textarea")) {
				/* Adjust length for carriage returns - see Bug 37962 */
				var lineEndLength = oMinLength[x][2]("lineEndLength");
				var adjustAmount = 0;
				if (lineEndLength) {
					var rCount = 0;
					var nCount = 0;
					var crPos = 0;
					while (crPos < field.value.length) {
						var currChar = field.value.charAt(crPos);
						if (currChar == "\r") {
							rCount++;
						}
						if (currChar == "\n") {
							nCount++;
						}
						crPos++;
					}
					var endLength = parseInt(lineEndLength);
					adjustAmount = (nCount * endLength) - (rCount + nCount);
				}
				var iMin = parseInt(oMinLength[x][2]("minlength"));
				if ((trim(field.value).length > 0) && ((field.value.length + adjustAmount) < iMin)) {
					if (i == 0) {
						focusField = field;
					}
					fields[i++] = oMinLength[x][1];
					isValid = false;
				}
			}
			jcv_sustome_processFieldAfterValid(isValid, field, fields, i - 1);
		}
	}
	if (fields.length > 0) {
		jcv_handleErrors(fields, focusField);
	}
	if (testResult == false) {
		isValid = false;
	}
	return isValid;
}
/**
 * A field is considered valid if less than the specified maximum.
 * Fields are not checked if they are disabled.
 * <p>
 * <strong>Caution:</strong> Using <code>validateMaxLength</code> on a password field in a
 *  login page gives unnecessary information away to hackers. While it only slightly
 *  weakens security, we suggest using it only when modifying a password.</p>
 * @param form The form validation is taking place on.
 */
function validateMaxLength(form) {
	var testResult = true; //store temp test result
	var isValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	var oMaxLength = eval("new " + jcv_retrieveFormName(form) + "_maxLength()");
	for (var x in oMaxLength) {
		if (!jcv_verifyArrayElement(x, oMaxLength[x])) {
			continue;
		} //the input maybe an array
		var getVar = oMaxLength[x][2];
		var isMultiLine = false;
		if (getVar("multiLine") == "true") {
			isMultiLine = true;
		}
		var loopFields = form[oMaxLength[x][0]];
		if (!jcv_isFieldPresent(loopFields)) { //input not present
			continue;
		}
		var fieldsCount = loopFields.length;
		if (isMultiLine) {
			if (fieldsCount == undefined) { //is not array
				fieldsCount = 1;
				var tempLoopField = loopFields;
				loopFields = new Array(tempLoopField);
			}
		} else {
			fieldsCount = 1;
			var tempLoopField = loopFields;
			loopFields = new Array(tempLoopField);
		}
		for (var index = 0; index < fieldsCount; index++) {
			var field = loopFields[index];
			//must set isValid default value true              
			if (index > 0 && isValid == false) {
				isValid = true;
				testResult = false;
			}
			//only check currentCheckField if it's not null     
			if (currentCheckField != null && currentCheckField != field) {
				continue;
			}

			//skip the first input if isMultiLine
			if (isMultiLine && index == 0) {
				continue;
			}
			if (!jcv_isFieldPresent(field)) {
				continue;
			}
			if ((field.type == "hidden" || field.type == "text" || field.type == "password" || field.type == "textarea")) {
				/* Adjust length for carriage returns - see Bug 37962 */
				var lineEndLength = oMaxLength[x][2]("lineEndLength");
				var adjustAmount = 0;
				if (lineEndLength) {
					var rCount = 0;
					var nCount = 0;
					var crPos = 0;
					while (crPos < field.value.length) {
						var currChar = field.value.charAt(crPos);
						if (currChar == "\r") {
							rCount++;
						}
						if (currChar == "\n") {
							nCount++;
						}
						crPos++;
					}
					var endLength = parseInt(lineEndLength);
					adjustAmount = (nCount * endLength) - (rCount + nCount);
				}
				var iMax = parseInt(oMaxLength[x][2]("maxlength"));
				if ((field.value.length + adjustAmount) > iMax) {
					if (i == 0) {
						focusField = field;
					}
					fields[i++] = oMaxLength[x][1];
					isValid = false;
				}
			}
			jcv_sustome_processFieldAfterValid(isValid, field, fields, i - 1);
		}
	}
	if (fields.length > 0) {
		jcv_handleErrors(fields, focusField);
	}
	if (testResult == false) {
		isValid = false;
	}
	return isValid;
}
/**
 * Check to see if fields are a valid email address.
 * Fields are not checked if they are disabled.
 * <p>
 * @param form The form validation is taking place on.
 */
function validateEmail(form) {
	var testResult = true; //store temp test result
	var isValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	var oEmail = eval("new " + jcv_retrieveFormName(form) + "_email()");
	for (var x in oEmail) {
		if (!jcv_verifyArrayElement(x, oEmail[x])) {
			continue;
		} //the input maybe an array
		var getVar = oEmail[x][2];
		var isMultiLine = false;
		if (getVar("multiLine") == "true") {
			isMultiLine = true;
		}
		var loopFields = form[oEmail[x][0]];
		if (!jcv_isFieldPresent(loopFields)) { //input not present
			continue;
		}
		var fieldsCount = loopFields.length;
		if (isMultiLine) {
			if (fieldsCount == undefined) { //is not array
				fieldsCount = 1;
				var tempLoopField = loopFields;
				loopFields = new Array(tempLoopField);
			}
		} else {
			fieldsCount = 1;
			var tempLoopField = loopFields;
			loopFields = new Array(tempLoopField);
		}
		for (var index = 0; index < fieldsCount; index++) {
			var field = loopFields[index];
			//must set isValid default value true              
			if (index > 0 && isValid == false) {
				isValid = true;
				testResult = false;
			}
			//only check currentCheckField if it's not null     
			if (currentCheckField != null && currentCheckField != field) {
				continue;
			}

			//skip the first input if isMultiLine
			if (isMultiLine && index == 0) {
				continue;
			}
			if (!jcv_isFieldPresent(field)) {
				continue;
			}
			if ((field.type == "hidden" || field.type == "text" || field.type == "textarea") && (field.value.length > 0)) {
				if (!jcv_checkEmail(field.value)) {
					if (i == 0) {
						focusField = field;
					}
					fields[i++] = oEmail[x][1];
					isValid = false;
				}
			}
			jcv_sustome_processFieldAfterValid(isValid, field, fields, i - 1);
		}
	}
	if (fields.length > 0) {
		jcv_handleErrors(fields, focusField);
	}
	if (testResult == false) {
		isValid = false;
	}
	return isValid;
}
/**
 * Reference: Sandeep V. Tamhankar (stamhankar@hotmail.com),
 * http://javascript.internet.com
 */
function jcv_checkEmail(emailStr) {
	if (emailStr.length == 0) {
		return true;
	}
	// TLD checking turned off by default
	var checkTLD = 0;
	var knownDomsPat = /^(com|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum)$/;
	var emailPat = /^(.+)@(.+)$/;
	var specialChars = "\\(\\)><@,;:\\\\\\\"\\.\\[\\]";
	var validChars = "[^\\s" + specialChars + "]";
	var quotedUser = "(\"[^\"]*\")";
	var ipDomainPat = /^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/;
	var atom = validChars + "+";
	var word = "(" + atom + "|" + quotedUser + ")";
	var userPat = new RegExp("^" + word + "(\\." + word + ")*$");
	var domainPat = new RegExp("^" + atom + "(\\." + atom + ")*$");
	var matchArray = emailStr.match(emailPat);
	if (matchArray == null) {
		return false;
	}
	var user = matchArray[1];
	var domain = matchArray[2];
	for (i = 0; i < user.length; i++) {
		if (user.charCodeAt(i) > 127) {
			return false;
		}
	}
	for (i = 0; i < domain.length; i++) {
		if (domain.charCodeAt(i) > 127) {
			return false;
		}
	}
	if (user.match(userPat) == null) {
		return false;
	}
	var IPArray = domain.match(ipDomainPat);
	if (IPArray != null) {
		for (var i = 1; i <= 4; i++) {
			if (IPArray[i] > 255) {
				return false;
			}
		}
		return true;
	}
	var atomPat = new RegExp("^" + atom + "$");
	var domArr = domain.split(".");
	var len = domArr.length;
	for (i = 0; i < len; i++) {
		if (domArr[i].search(atomPat) == -1) {
			return false;
		}
	}
	if (checkTLD && domArr[domArr.length - 1].length != 2 && domArr[domArr.length - 1].search(knownDomsPat) == -1) {
		return false;
	}
	if (len < 2) {
		return false;
	}
	return true;
}
/**
 * 1. call beforeInitPage if exist
 * 2. do some init on all present element
 * 3. call afterInitPage if exist
 */
function initPage() {
	var obj;
	//Call beforeInsertRow of pageCode
	obj = eval("window.beforeInitPage");
	if (obj != null) {
		obj.apply();
	}
	var i = 0;
	var j = 0;
	var element;
	var formsCount = document.forms.length;
	for (i = 0; i < formsCount; i++) {
		var form = document.forms[i];
		var elementsCount = form.elements.length;
		for (j = 0; j < elementsCount; j++) {
			element = form.elements[j];
			jcv_custom_initElement(element);
		}
	}
	obj = eval("window.afterInitPage");
	if (obj != null) {
		obj.apply();
	}
}

function jcv_custom_initElement(element) {
	if (element.name == "" || element.type == "hidden" || element.type == "button" || element.type == "submit" || element.type == "reset" || element.type == "img") {
		return;
	}
	//���onblur�����¼�
	var method = null;
	if (element.onblur != null) {
		method = element.onblur;
	}
	element.onblur = jcv_custom_onblurHandler;
	if (method != null) {
		eval("element.onblur.prototype." + element.name + "=" + method);
	}
	if (element.onfocus != null) {
		method = element.onfocus;
	}
	element.onfocus = jcv_custom_onfocusHandler;
	if (method != null) {
		eval("element.onfocus.prototype." + element.name + "=" + method);
	}
}

function jcv_custom_onblurHandler(evt) {
	var testValue = true;
	var obj;
	evt = (evt) ? evt : window.event;
	try {
		obj = eval("this.onblur.prototype." + this.name);
		if (obj != null) {
			if (obj.apply(obj, arguments) == false) {
				return false;
			}
		}
	} catch (E) {
		log(E);
		testValue = false;
	}
	var field = this;
	if (field == null) {
		return testValue;
	}
	if (currentCheckField != null) {
		return true;
	}
	currentCheckField = field;
	var result = validateFm(fm);
	currentCheckField = null;
	if (result) {
		jcv_custom_removeErrorIndicator(field);
	}
	return result;
}

function jcv_custom_onfocusHandler(evt) {
	var testValue = true;
	var obj;
	evt = (evt) ? evt : window.event;
	try {
		obj = eval("this.onfocus.prototype." + this.name);
		if (obj != null) {
			if (obj.apply(obj, arguments) == false) {
				return false;
			}
		}
	} catch (E) {
		log(E);
		testValue = false;
	}
	var field = this;
	if (field == null) {
		return testValue;
	}
	if (currentCheckField != null) {
		return true;
	}
	if (field.nodeName == "SELECT") {
		return true;
	}
	var className = field.className;
	if (className.indexOf("_") != -1) {
		className = className.substring(0, className.lastIndexOf("_"));
	}
	className += "_onfocus";
	field.className = className;
	return true;
}

function jcv_custom_createErrorIndicator(fieldObject, promptResult) {
	var className = fieldObject.className;
	if (className.indexOf("_") != -1) {
		className = className.substring(0, className.lastIndexOf("_"));
	}
	var errorImg = private_getErrorIndicator(fieldObject);
	if (errorImg == null) {
		errorImg = private_createErrorIndicator(fieldObject, promptResult);
	}
	if (fieldObject.nextSibling == null) {
		fieldObject.parentNode.appendChild(errorImg);
	} else {
		fieldObject.parentNode.insertBefore(errorImg, fieldObject.nextSibling);
	}
	className += "_error";
	fieldObject.className = className;
}

function jcv_custom_removeErrorIndicator(fieldObject) {
	var className = fieldObject.className;
	if (className.indexOf("_") != -1) {
		className = className.substring(0, className.lastIndexOf("_"));
	}
	private_removeIndicator(fieldObject);
	log("");
	fieldObject.className = className;
}

function jcv_sustome_processFieldAfterValid(isValid, field, fields, i) {
	jcv_custom_removeErrorIndicator(field);
	if (isValid == false) {
		var key = "{_lineNo_}";
		if (fields[i].indexOf(key) != -1) {
			if (currentCheckField == null) {
				fields[i] = replace(fields[i], key, "(" + (getElementOrder(field) - 1) + ")");
			} else {
				fields[i] = replace(fields[i], key, "");
			}
		}
		jcv_custom_createErrorIndicator(field, fields[i]);
	}
}

//-----------------------------------------------------------------------------------

function private_createErrorIndicator(fieldObject, promptResult) {
	var errorIndicatorImg = document.createElement("IMG");
	document.body.appendChild(errorIndicatorImg);
	errorIndicatorImg.setAttribute("id", fieldObject.name + fieldObject._objectItemCount);
	errorIndicatorImg.className = public_errorIndicatorImgClassName;
	errorIndicatorImg.alt = public_errorIndicatorImgAlt;
	errorIndicatorImg.src = "/" + public_webApplicationName + public_errorIndicatorImgSrc;
	errorIndicatorImg.height = public_errorIndicatorImgHeight;
	errorIndicatorImg.width = public_errorIndicatorImgWidth;
	errorIndicatorImg.style.cursor = public_errorIndicatorImgStyleCursor;
	errorIndicatorImg._promptResult = promptResult;
	errorIndicatorImg.onclick = new Function("private_alertError(this);");
	private_linkIndicator(fieldObject, errorIndicatorImg);
	return errorIndicatorImg;
}

function private_getErrorIndicator(fieldObject) {
	if (!fieldObject._errorIndicator) {
		var c = fieldObject.nextSibling;
		while (null != c && (c.nodeName != "IMG" || c.className != public_errorIndicatorImgClassName)) {
			c = c.nextSibling;
		}
		if (c) {
			private_linkIndicator(fieldObject, c);
		}
		return c;
	}
	return fieldObject._errorIndicator;
}

function private_linkIndicator(fieldObject, err) {
	err._attachedControl = fieldObject;
	fieldObject._errorIndicator = err;
}

function private_removeIndicator(fieldObject) {
	if (fieldObject._errorIndicator != null) {
		fieldObject.parentNode.removeChild(fieldObject._errorIndicator);
		fieldObject._errorIndicator = null;
	}
}

function private_alertError(errorImg) {
	alert(errorImg._promptResult);
}