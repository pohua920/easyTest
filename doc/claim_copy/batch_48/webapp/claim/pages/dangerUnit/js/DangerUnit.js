//显示危险单位划分信息

function viewDangerUnit(field) {
	//var policyno   = fm.policyno.value;
	//var damageDate = fm.damageDate.value;
	var submitStr = "/claim/getDangerUnit.do?policyNo=1011105072006000061&damageDate=2006-12-17";
	window.open(submitStr, '查看危险单位信息', 'width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
}
