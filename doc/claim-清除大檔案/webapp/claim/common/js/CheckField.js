/*
 * author Dangze
 * date   2012/11/14
 * description 检查字段是否符合要求，不符合返回false;
 */
/*
    jsp中input title属性值内容介绍
    title属性的内容是一个由逗号隔开的字符串，每一段代表需要校验的内容，最后一段代表校验字段的中文名
"必填(非必填),数字类型(0、1、2)位小数||日期类型||邮箱或msn||手机号||固定电话或传真,数据长度最大值(6、7),fieldName
例1：title="必填,数字类型0位小数,数据长度最大值7,年龄";
例2：title="必填,邮箱或msn,数据长度最大值20,邮箱
 */
function pubCheckField(id){
	var flag=true;
	//selectorHead是jQuery选择器中的一部分(例：$("#duty [title]") 那么selectorHead是"#duty [title]"字符串中" [title]"前边的"#duty"串)
	var selectorHead="";
	typeof(id)=="undefined"||id==""?selectorHead="":selectorHead=("#"+id);
	//拿到具有title属性的input,进行遍历校验（注：如果id为空，那么jQuery取得的就是有title属性的元素，如果不为空将取得对应id下的具有title属性的元素）
	$(selectorHead+" [title]").each(function(){
		//title内的内容是以逗号隔开的数字，最后一个逗号后面的内容是此字段的中文名称，将他们以逗号为界分割后放到数组中
		var titleArray=$(this).attr("title").split(",");
		//数组的长度放到length中
		var length=titleArray.length;
		//取出当前记录的input的val值
		var fieldVal=$(this).val();
		var fieldName="";
		fieldName=titleArray[length-1];
		//遍历数组
		for(var i=0;i<length-1;i++){
			if(i==0){
				//校验是否为空
				if(titleArray[i]=="必填"){
					if($.trim(fieldVal)==""){
						jAlert(fieldName+"爲必填項!","提示!");
						flag=false;
						return false;
					}
				}
			}
			//第二个校验数据类型：数字类型（0、1、2.。）位小数、日期类型、邮箱或msn格式、手机号、固定电话或传真
			if(i==1){
				if(titleArray[i].substring(0,4)=="数字类型"){
					//去得小数位
					var point=titleArray[i].substring(4,5);
					//验证是否是数字的正则表达式
					if(point=='0'){
						var numReg=/^\d$/;
					}else if(point=='1'){
						var numReg=/^\d+(\.\d{1})?$/;
					}else if(point=='2'){
						var numReg=/^\d+(\.\d{2})?$/;
					}else if(point=='3'){
						var numReg=/^\d+(\.\d{3})?$/;
					}else if(point=='4'){
						var numReg=/^\d+(\.\d{4})?$/;
					}else if(point=='5'){
						var numReg=/^\d+(\.\d{5})?$/;
					}else if(point=='6'){
						var numReg=/^\d+(\.\d{6})?$/;
					}
					if(!numReg.test(fieldVal)){
						if(point!='0'){
							jAlert(fieldName+"的值應爲數字(例：0.00),保留"+point+"位小數!","提示!");}
						else{
							jAlert(fieldName+"的值應爲數字(例：0),保留"+point+"位小數!","提示!");
						}
						flag=false;
						return false;
					}
				}else if(titleArray[i]=="日期类型"){
					//验证是否是日期类型
					var dateReg = /((^((1[8-9]\d{2})|([2-9]\d{3}))(-)(10|12|0?[13578])(-)(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(11|0?[469])(-)(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(0?2)(-)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)(-)(0?2)(-)(29)$)|(^([3579][26]00)(-)(0?2)(-)(29)$)|(^([1][89][0][48])(-)(0?2)(-)(29)$)|(^([2-9][0-9][0][48])(-)(0?2)(-)(29)$)|(^([1][89][2468][048])(-)(0?2)(-)(29)$)|(^([2-9][0-9][2468][048])(-)(0?2)(-)(29)$)|(^([1][89][13579][26])(-)(0?2)(-)(29)$)|(^([2-9][0-9][13579][26])(-)(0?2)(-)(29)$))/;
					if(fieldVal.match(dateReg)==null){
						jAlert(fieldName+"的格式不正確，例：2012-01-01","提示!");
						flag=false;
						return false;
					}
				}else if(titleArray[i]=="邮箱或msn"){
					//验证邮箱格式是否正确
					var mailReg=/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
					if(!mailReg.test(fieldVal)){
						jAlert(fieldName+"的格式不正確，例：9876543@qq.com","提示!");
						flag=false;
						return false;
					}
				}else if(titleArray[i]=="手机号"){
					 var mobile=/^((13[0-9]{1})|159|153)+\d{8}$/;
					 if(!mobile.test(fieldVal)){
							jAlert(fieldName+"的格式不正確，例：13888888888","提示!");
							flag=false;
							return false;
						}
				}else if(titleArray[i]=="固定电话或传真"){
					var telephone=/^0\d{2,3}-\d{7,8}$/;
					if(!telephone.test(fieldVal)){
						jAlert(fieldName+"的格式不正確，例：010-3456789","提示!");
						flag=false;
						return false;
					}
				}
			}
			//第三个校验 数据长度最大值
			if(i==2){
				//数据库中规定的字段长度
				var s_len=titleArray[i].substring(7,titleArray[i].length);
				//jsp页面中录入的字段的长度
				var w_len=fieldVal.length;
				//判断汉字的个数，在数据库中一个汉字按两位算，需要知道汉字的个数乘2就是数据库中的存入长度
				var ch_count=cal(fieldVal);
				//将汉字位数乘2以后的字段长度
				 wr_len=w_len+ch_count;
				//如果jsp页面录入数据处理后的长度大于数据规定长度，提示信息
				if(wr_len>s_len){
					jAlert(fieldName+"的長度不能超過"+s_len+"!","提示!");
					flag=false;
					return false;
				}
			}
		}
	});
	return flag;
}

//判断汉字的长度
function cal(str)
{ 
    re=/[\u4E00-\u9FA5]/g;  //测试中文字符的正则
    if(re.test(str)){        //使用正则判断是否存在中文
    	return str.match(re).length;} //返回中文的个数
    else {
    return 0;} 
} 