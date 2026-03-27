/*
 ****************************************************************************
 * DESC       ：时间JS
 * Author     ： 张亮
 * CREATEDATE ：2011-03-01
 * MODIFYLIST ：  Name          Date       Reason/Contents
                                          张亮                    2011-03-01       创建页面
 *          --------------------------------------------------
 ****************************************************************************
 */

// 时间转换YYYY-MM-DD
function formatDate(date, format) {
	if (!date) {
		return;
	}
	
	if (!format){
		format = "yyyy-MM-dd";
	}
	
	switch (typeof date) {
	case "string":
		date = new Date(date.replace(/-/, "/"));
		break;
	case "number":
		date = new Date(date);
		break;
	}
	
	if (isNaN(date)||!date instanceof Date){
		return "";
	}
	var dict = {
		"yyyy" : date.getFullYear(),
		"yyy" : date.getFullYear()-1911,
		"M" : date.getMonth() + 1,
		"d" : date.getDate(),
		"H" : date.getHours(),
		"m" : date.getMinutes(),
		"s" : date.getSeconds(),
		"MM" : ("" + (date.getMonth() + 101)).substr(1),
		"dd" : ("" + (date.getDate() + 100)).substr(1),
		"HH" : ("" + (date.getHours() + 100)).substr(1),
		"mm" : ("" + (date.getMinutes() + 100)).substr(1),
		"ss" : ("" + (date.getSeconds() + 100)).substr(1)
	};
	return format.replace(/(yyyy|yyy?|MM?|dd?|HH?|ss?|mm?)/g, function() {
		return dict[arguments[0]];
	});
}
//时间转换YYYY-MM-DD
//明国年，转换成西元年
function rcFormatDate(date, format) {
	if (!date) {
		return;
	}
	
	if (!format){
		format = "yyyy-MM-dd";
	}
	
	switch (typeof date) {
	case "string":
		date = new Date(date.replace(/-/, "/"));
		break;
	case "number":
		date = new Date(date);
		break;
	}
	if (isNaN(date)||!date instanceof Date){
		return "";
	}
	//只支持IE浏览器，咱不支持IE以外的浏览器
	var dict = {
		"yyyy" : date.getYear()+1911,
		"yyy" : date.getYear(),
		"M" : date.getMonth() + 1,
		"d" : date.getDate(),
		"H" : date.getHours(),
		"m" : date.getMinutes(),
		"s" : date.getSeconds(),
		"MM" : ("" + (date.getMonth() + 101)).substr(1),
		"dd" : ("" + (date.getDate() + 100)).substr(1),
		"HH" : ("" + (date.getHours() + 100)).substr(1),
		"mm" : ("" + (date.getMinutes() + 100)).substr(1),
		"ss" : ("" + (date.getSeconds() + 100)).substr(1)
	};
	return format.replace(/(yyyy|yyy?|MM?|dd?|HH?|ss?|mm?)/g, function() {
		return dict[arguments[0]];
	});
}

// 相差天数
function DateDiff(sDate1, sDate2) { // sDate1和sDate2是2006-12-18格式
	var aDate, oDate1, oDate2, iDays
	aDate = sDate1.split("-")
	oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]) // 转换为12-18-2006格式
	aDate = sDate2.split("-")
	oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])
	iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24) // 把相差的毫秒数转换为天数
	return iDays
}
