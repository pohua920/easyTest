    var FIELD_SEPARATOR = "_FIELD_SEPARATOR_"; //字段之间的分割符
    var GROUP_SEPARATOR = "_GROUP_SEPARATOR_"; //一组代码之间的分割符

     //按键事件

    function fieldOnKeyPress() {
    	var charCode = window.event.keyCode;
    	if (charCode == 13) { //回车
    		setFieldValue();
    	} else if (charCode == 32) { //ESC
    		cancelFieldValue();
    	}
    }

     //取消

    function cancelFieldValue() {
    	parent.fraRight.rows = "100%,0%,0%";
    	try {
    		var index = parseInt(fm.codeindex.value);

    		if (parent.fraInterface.getSign() == fm.fieldsign.value) {
    			fmInput.elements[index].value = "";
    			fmInput.elements[index].focus();
    		}
    	} catch (e) {}

    	parent.fraInterface.setInquery(false);
    	parent.fraInterface.setQueryError(false);
    	window.status = "";
    }

     //确定

    function setFieldValue() {
    	var codecode;
    	var codename;
    	var codevalue;
    	try {
    		codevalue = fm.codeselect.options[fm.codeselect.options.selectedIndex].value;

    	} catch (e1) {
    		errorMessage("请先选择需要的代码");
    		return;
    	}

    	try {
    		parent.fraRight.rows = "100%,0%,0%";
    		var index = parseInt(fm.codeindex.value);
    		var tempIndex = 0;
    		var arrCodeValue;

    		//如果输入域类型为code
    		if (fm.inputtype.value == "code") {
    			var arrRelation = ("0," + fm.relation.value).split(","); //得到所有元素相对於输入域的偏移量
    			arrCodeValue = codevalue.split(FIELD_SEPARATOR); //得到所有元素的值
    			for (var i = 0; i < arrRelation.length; i++) {
    				tempIndex = index + parseInt(arrRelation[i]); //得到元素的ElementIndex
    				if (i > arrCodeValue.length - 1) { //如果相关域的个数大於查询出的元素的值的个数，则用最後一个默认值
    					if (fm.querytype.value == "need") { //inputtype is code and querytype is need
    						if (isEmpty(fmInput.elements[tempIndex])) {
    							fmInput.elements[tempIndex].value = arrCodeValue[arrCodeValue.length - 1];
    							fmInput.elements[tempIndex].tag = arrCodeValue[arrCodeValue.length - 1];

    							try {
    								if (fmInput.elements[tempIndex].onchange != null) {
    									fmInput.elements[tempIndex].onchange();
    								}
    							} catch (E1) {}
    						}
    					} else {
    						fmInput.elements[tempIndex].value = arrCodeValue[arrCodeValue.length - 1];
    						fmInput.elements[tempIndex].tag = arrCodeValue[arrCodeValue.length - 1];
    						try {
    							if (fmInput.elements[tempIndex].onchange != null) {
    								fmInput.elements[tempIndex].onchange();
    							}
    						} catch (E2) {}
    					}
    				} else {
    					if (fm.querytype.value == "need") {
    						if (isEmpty(fmInput.elements[tempIndex]) || i == 0) { //代码域必须重新赋值,代码值域可选赋值
    							fmInput.elements[tempIndex].value = arrCodeValue[i];
    							fmInput.elements[tempIndex].tag = arrCodeValue[i];
    							try {
    								if (fmInput.elements[tempIndex].onchange != null) {
    									fmInput.elements[tempIndex].onchange();
    								}
    							} catch (E3) {}
    						}
    					} else {
    						fmInput.elements[tempIndex].value = arrCodeValue[i];
    						fmInput.elements[tempIndex].tag = arrCodeValue[i];
    						try {
    							if (fmInput.elements[tempIndex].onchange != null) {
    								fmInput.elements[tempIndex].onchange();
    							}
    						} catch (E4) {}
    					}
    				}
    			}
    		} else { //codename
    			var arrRelation = ("0," + fm.relation.value).split(",");
    			arrRelation[0] = arrRelation[1];
    			arrRelation[1] = "0";

    			arrCodeValue = codevalue.split(FIELD_SEPARATOR);
    			for (var i = 0; i < arrRelation.length; i++) {
    				tempIndex = index + parseInt(arrRelation[i]);
    				if (i > arrCodeValue.length - 1) {
    					if (fm.querytype.value == "need") {
    						if (isEmpty(fmInput.elements[tempIndex])) {
    							fmInput.elements[tempIndex].value = arrCodeValue[arrCodeValue.length - 1];
    							fmInput.elements[tempIndex].tag = arrCodeValue[arrCodeValue.length - 1];
    							try {
    								if (fmInput.elements[tempIndex].onchange != null) {
    									fmInput.elements[tempIndex].onchange();
    								}
    							} catch (E5) {}
    						}
    					} else {
    						fmInput.elements[tempIndex].value = arrCodeValue[arrCodeValue.length - 1];
    						fmInput.elements[tempIndex].tag = arrCodeValue[arrCodeValue.length - 1];
    						try {
    							if (fmInput.elements[tempIndex].onchange != null) {
    								fmInput.elements[tempIndex].onchange();
    							}
    						} catch (E6) {}
    					}
    				} else {
    					if (fm.querytype.value == "need") {
    						if (isEmpty(fmInput.elements[tempIndex]) || i == 0) {
    							fmInput.elements[tempIndex].value = arrCodeValue[i];
    							fmInput.elements[tempIndex].tag = arrCodeValue[i];
    							try {
    								if (fmInput.elements[tempIndex].onchange != null) {
    									fmInput.elements[tempIndex].onchange();
    								}
    							} catch (E7) {}
    						}
    					} else {
    						fmInput.elements[tempIndex].value = arrCodeValue[i];
    						fmInput.elements[tempIndex].tag = arrCodeValue[i];
    						try {
    							if (fmInput.elements[tempIndex].onchange != null) {
    								fmInput.elements[tempIndex].onchange();
    							}
    						} catch (E8) {}
    					}
    				}
    			}
    		}
    		fmInput.elements[index].focus();
    	} catch (e2) {}

    	parent.fraInterface.setInquery(false);
    	parent.fraInterface.setQueryError(false);
    	window.status = "";
    }