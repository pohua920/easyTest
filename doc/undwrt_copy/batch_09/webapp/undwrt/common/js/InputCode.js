
/****************************************************************************

 * DESC       ：代码录入

 * AUTHOR     ：liuyang

 * CREATEDATE ：2003-12-23

 * MODIFYLIST ：Name       Date            Reason/Contents

 *          ------------------------------------------------------

 *              ZHANGYING  2004-07-29      类似以往的查询，整理，增加注释

 ****************************************************************************/
var DEBUG_MODE = true;
var blnInQuery = false;    //正在查询代码标志
var blnQueryError = false;    //查询代码错误标志
var TIME_SIGN; //时间戳
/**

 @author      ZHANGYING 整理

 @description 判断是否在查询过程中

 @param       无

 @return      布尔值

 @see         无

*/
function isInQuery() {
	return blnInQuery;
}
/**

 @author      ZHANGYING 整理

 @description 判断是否有查询错误

 @param       无

 @return      布尔值

*/
function isQueryError() {
	return blnQueryError;
}
/**

 @author      ZHANGYING 整理

 @description 设置是否正在查询标志

 @param       varInQuery:正在查询标志

 @return      无

*/
function setInQuery(varInQuery) {
	blnInQuery = varInQuery;
}
/**

 @author      ZHANGYING 整理

 @description 设置是否有查询错误标志

 @param       varInQuery:查询错误标志

 @return      无

*/
function setQueryError(varQueryError) {
	blnQueryError = varQueryError;
}
/**

 @author      ZHANGYING 整理

 @description 获取时间戳

 @param       无

 @return      时间戳

*/
function getTIME_SIGN() {
	return TIME_SIGN;
}
/**

 @author      ZHANGYING 整理

 @description 设置时间戳

 @param       varTIME_SIGN:有查询错误标志

 @return      无

*/
function setTIME_SIGN(varTIME_SIGN) {
	TIME_SIGN = varTIME_SIGN;
}
/**

 @author      ZHANGYING 整理

 @description 查找元素在Form中的顺序，没有则返回-1

 @param       field：域

 @return      无

*/
function getElementIndex(field) {
	var intElementIndex = -1;

    

    //查找fm里的元素
	for (var i = 0; i < fm.elements.length; i++) {
		if (fm.elements[i] == field) {
			intElementIndex = i;
			break;
		}
	}
	return intElementIndex;
}
/**

 @author      ZHANGYING 整理

 @description 初始化所有代码输入域

 @param       无

 @return      无

*/
function initAllCodeInput() {
	for (var i = 0; i < fm.elements.length; i++) {
		if (fm.elements[i].className == "codecode" || fm.elements[i].className == "codename") {
			fm.elements[i].tag = fm.elements[i].value;
		}
	}
}
/**

 @author      ZHANGYING 整理

 @description 调用服务（私有函数）

 @param       无

 @return      无

*/
function private_Code_CallService(field, codetype, relation, inputtype, querytype, limit, method, FrameInput) {
	var fmcode = parent.parent.fraCode.fm;
	var index = getElementOrder(field) - 1;
	if (isInQuery() == true) {
		window.status = "\u6b63\u5728\u67e5\u8be2\u4ee3\u7801......";
		return;
	}
	setTIME_SIGN(new Date().getTime());
	setInQuery(true);
	setQueryError(false);

    

    //请求服务器
	try {
		if (relation == null) {
			relation = 1;
		}
		if (inputtype == null) {
			inputtype = "code";
		}
		if (querytype == null) {
			querytype = "always";
		}
		if (limit == null) {
			limit = "must";
		}
		if (method == null) {
			method = "select";
		}
		if (FrameInput == null) {
			FrameInput = "fraInterface";
		}
		fmcode.codeindex.value = getElementIndex(field);
		fmcode.codevalue.value = field.value;
		fmcode.codetype.value = codetype;
		fmcode.relation.value = relation;
		fmcode.inputtype.value = inputtype;
		fmcode.querytype.value = querytype;
		fmcode.limit.value = limit;
		fmcode.method.value = method;
		fmcode.fieldsign.value = getTIME_SIGN();
		fmcode.fminput.value = FrameInput;
		fmcode.other.value = "";
		if(codetype=="RationType"){
			fmcode.vsCodeSetRiskCode.value  = fm.vsCodeSetRiskCode.value;
			fmcode.vsCodeSetComCode.value = fm.vsCodeSetComCode.value;
		}
		
		try {
			fmcode.riskcode.value = fm.riskcode.value;
		}
		catch (ex) {
			fmcode.riskcode.value = "PUB";
		}
		try {
			fmcode.fieldext.value = field.fieldext;
		}
		catch (ex) {
			fmcode.fieldext.value = "";
		}
		fmcode.submit(); 

                                                                       //提交
	}
	catch (E) {
		if (DEBUG_MODE == true) {
			alert("===============" + E);
		}
	}
}
/**

 @author      ZHANGYING 整理

 @description 提供给jsp界面直接调用的js方法

 @param       field：域

 @param       codetype：代码类型

 @param       relation：相关域，默认1

 @param       inputtype：code还是name，默认code

 @param       querytype：need，always，默认always

 @param       limit：有clear、none和must，默认must

 @param       method：默认select

 @return      无

*/
function code_CodeSelect(field, codetype, relation, inputtype, querytype, limit, method, FrameInput) {
	if (!isInQuery() && !isQueryError()) {
		window.status = "";
	}
	if (event.type == "keyup") {
		var charCode = window.event.keyCode;
		if (!(charCode == 13 & window.event.ctrlKey)) {
			return;
		}
	}
	window.status = window.status + "select";
	private_Code_CallService(field, codetype, relation, inputtype, querytype, limit, "select", FrameInput);
}
function code_CodeSelectVisa(field, codetype, relation, inputtype, querytype, limit, method, FrameInput) {
	if (!isInQuery() && !isQueryError()) {
		window.status = "";
	}
	if (event.type == "keyup") {
		var charCode = window.event.keyCode;
		if (!(charCode == 13 & window.event.ctrlKey)) {
			return;
		}
	}
	window.status = window.status + "select";
	private_Code_CallServiceVisa(field, codetype, relation, inputtype, querytype, limit, "select", FrameInput);
}
function private_Code_CallServiceVisa(field, codetype, relation, inputtype, querytype, limit, method, FrameInput) {
	var fmcode = parent.parent.fraCode.fm;
	var index = getElementOrder(field) - 1;
	if (isInQuery() == true) {
		window.status = "\u6b63\u5728\u67e5\u8be2\u4ee3\u7801......";
		return;
	}
	setTIME_SIGN(new Date().getTime());
	setInQuery(true);
	setQueryError(false);

    

    //请求服务器
	try {
		if (relation == null) {
			relation = 1;
		}
		if (inputtype == null) {
			inputtype = "code";
		}
		if (querytype == null) {
			querytype = "always";
		}
		if (limit == null) {
			limit = "must";
		}
		if (method == null) {
			method = "select";
		}
		if (FrameInput == null) {
			FrameInput = "fraInterface";
		}
		fmcode.codeindex.value = getElementIndex(field);
		fmcode.codevalue.value = field.value;
		fmcode.codetype.value = codetype;
		fmcode.relation.value = relation;
		fmcode.inputtype.value = inputtype;
		fmcode.querytype.value = querytype;
		fmcode.limit.value = limit;
		fmcode.method.value = method;
		fmcode.fieldsign.value = getTIME_SIGN();
		fmcode.fminput.value = FrameInput;
        
		//取页面选择的申领机构代码
		if (index == 0) {
			var accepterCode = fm.vsUnUsedMarkUserComeCode.value;
			fmcode.accepterCode.value = accepterCode;
		} else {
			var accepterCode = fm.vsProvideAccepterCode[index].value;
			fmcode.accepterCode.value = accepterCode;
		}
		fmcode.other.value = "";
		try {
			fmcode.riskcode.value = fm.riskcode.value;
		}
		catch (ex) {
			fmcode.riskcode.value = "PUB";
		}
		try {
			fmcode.fieldext.value = field.fieldext;
		}
		catch (ex) {
			fmcode.fieldext.value = "";
		}
		fmcode.submit(); 

                                                                       //提交
	}
	catch (E) {
		if (DEBUG_MODE == true) {
			alert("===============" + E);
		}
	}
}
function code_CodeChange(field, codetype, relation, inputtype, querytype, limit, method, FrameInput) {
	if (!isInQuery() && !isQueryError()) {
		window.status = "";
	}



//    if(field.tag==field.value){

//        return;

//    }
	window.status = window.status + "change";
	private_Code_CallService(field, codetype, relation, inputtype, querytype, limit, "change", FrameInput);
}

