/*****************************************************************************
 * DESC       ：单证管理系统级别设置脚本
 * AUTHOR     ：YANGXIAOGANG
 * CREATEDATE ：2004-08-14
 * MODIFYLIST ：  Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ******************************************************************************/
/**
 @author 中科软      
 @description 删除
 @param       无
 @return      无
 */
function deleteForm(Type) {
	var value;
	var returnNum = -1;
	var UnitCode = "";
	var UnitName = "";
	var UnitType = "";
	var i;

	if (Type == "P") {
		if (!confirm("是否要删除选定的人员?"))
			return false;

		for (i = 0; i < fm.vecPerson.value; i++) {
			if (fm.all("checkPerson")[i].checked == true && 　fm.all("checkPerson")[i].disabled == false) {
				returnNum = 0;
				UnitType = "1";
				break;
			}
		}

	} else if (Type == "D") {
		if (!confirm("是否要删除选定的机构？是则其下属机构人员也将被删除"))
			return false;
		UnitType = "D";
		for (i = 0; i < fm.vecDepartment.value; i++) {
			if (fm.all("checkDepartment")[i].checked == true && 　fm.all("checkDepartment")[i].disabled == false) {
				returnNum = 0;
				UnitType = "0";
				break;
			}
		}
	}
	if (returnNum == 0 && UnitType != "") {
		fm.Flag.value = "delete";
		fm.target = "fraNext";
		fm.action = "/platform/levelsetdelete.do?unittype=" + UnitType;
		fm.submit();
	} else
		errorMessage("没有选中的信息!");
}

/**
 @author 中科软      
 @description 新增
 @param       无
 @return      无
 */
function saveForm(Type) {
	var value;
	var error = "";
	var returnNum = 0;
	var UnitCode = "";
	var UnitName = "";
	var UnitType = "";

	if (Type == "P") {
		UnitType = "1";
		value = fm.vsLevelUnitCode.value;
		if (value == "") {
			returnNum = -1;
			error = "人员代码不能为空！";
		}

		value = fm.vsLevelUnitName.value;
		if (value == "" && returnNum == 0) {
			returnNum = -1;
			error = "人员名称不能为空！";
		}
	} else if (Type == "D") {
		UnitType = "0";
		value = fm.DepartmentCode.value;
		if (value == "") {
			returnNum = -1;
			error = "部门代码不能为空！";
		}

		value = fm.DepartmentName.value;
		if (value == "" && returnNum == 0) {
			returnNum = -1;
			error = "部门名称不能为空！";
		}
	}
	if (returnNum == 0 && UnitType != "") {
		fm.target = "fraNext";
		fm.action = "/platform/levelsetsave.do?unittype=" + UnitType;
		fm.submit();
	} else if (returnNum == -1)
		errorMessage(error);
}

//人员的全部选择click事件

function onclickPersonAll() {
	var i;
	for (i = 0; i < fm.vecPerson.value; i++) {
		if (fm.all("checkPerson")[i].disabled == false)
			fm.all("checkPerson")[i].checked = fm.SelectAllPerson.checked;
	}
}


//部门的全部选择click事件

function onclickDepartmentAll() {
	var i;
	for (i = 0; i < fm.vecDepartment.value; i++) {
		if (fm.all("checkDepartment")[i].disabled == false)
			fm.all("checkDepartment")[i].checked = fm.SelectAllDepartment.checked;
	}
}


//删除成功

function success(UnitType) {
	var i;

	alert("删除成功!");

	if (UnitType == "P") {
		for (i = 0; i < fm.all("checkPerson").length; i++) {
			if (fm.all("checkPerson")[i].checked == true)
				fm.all("checkPerson")[i].disabled = true;
		}
	} else if (UnitType == "D") {
		for (i = 0; i < fm.all("checkDepartment").length; i++) {
			if (fm.all("checkDepartment")[i].checked == true)
				fm.all("checkDepartment")[i].disabled = true;
		}
	}

}

//调整加减号

function divproT(divid) {
	thedivid = 'div' + divid;
	theimgid = 'img' + divid;
	if (document.all(thedivid).style.display == 'block') {
		document.all(thedivid).style.display = 'none';
		document.all(theimgid).src = "/platform/images/treeTExpand.gif";
	} else {
		document.all(thedivid).style.display = 'block';
		document.all(theimgid).src = "/platform/images/treeTCollapse.gif";
	}
}
//调整加减号，最後一项

function divproL(divid) {
	thedivid = 'div' + divid;
	theimgid = 'img' + divid;
	try {
		if (document.all(thedivid).style.display == 'block') {
			document.all(thedivid).style.display = 'none';
			document.all(theimgid).src = "/platform/images/treeLExpand.gif";
		} else {
			document.all(thedivid).style.display = 'block';
			document.all(theimgid).src = "/platform/images/treeLCollapse.gif";
		}
	} catch (E) {}
}