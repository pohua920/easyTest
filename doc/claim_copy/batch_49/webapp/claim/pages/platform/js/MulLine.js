/****************************************************************************
 * DESC       ：JavaScript多记录操纵之FrameWork(兼容IE5/NN6)--Common Project控制，Application Project不能修改
 * AUTHOR     ：zhouxianli
 * CREATEDATE ：2003-05-02
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 *
 ****************************************************************************/
/**
<!--
注意：
    需要Common.js中的函数getElementOrder，getTableElements
    ============================================================================
    非本模块的JavaScript只允许调用以direct开头的方法，其它方法均为私有，不可使用
    ============================================================================
工作原理:

模式
    <table style="display:none" id="<pageCode>_Data">
      <tbody>
        中间放入一组数据(即实际展现给用户使用的一组tr)
      </tbody>
    </table>

    <table id="<pageCode>">
      <thead>
        中间显示标题
      </thead>
      <tfoot>
        放入加号
      </tfoot>
      <tbody>
        空的，中间操纵多组数据区域
      </tbody>
    </table>

    <script language='javascript'>
      function beforeInsert<pageCode>(){
        ...
      }
      function insert<pageCode>(){
        ...
      }
      function afterInsert<pageCode>(){
        ...
      }
      function beforeDelete<pageCode>(field){
        ...
      }
      function delete<pageCode>(field){
        ...
      }
      function afterDelete<pageCode>(field){
        ...
      }
    </script>
    标题域也要求写上输入域的名字，即实际数据从第一行开始。

    对每一个添加控制按钮，
    调用顺序为：beforeInsert<pageCode>  ==>  insert<pageCode>  ==>  afterInsert<pageCode>
    即：如果存在自定义方法beforeInsert<pageCode>,MulLine自动调用，
        如果存在自定义方法insert<pageCode>,MulLine自动调用，如果不存在则调用默认的添加方法
        如果存在自定义方法afterInsert<pageCode>,MulLine自动调用

    对每一个删除控制按钮，
    调用顺序为：beforeDelete<pageCode>  ==>  delete<pageCode>  ==>  afterDelete<pageCode>
    即：如果存在自定义方法beforeDelete<pageCode>,MulLine自动调用，
        如果存在自定义方法delete<pageCode>,MulLine自动调用，如果不存在则调用默认的删除方法
        如果存在自定义方法afterDelete<pageCode>,MulLine自动调用


    注意删除後应该调用getRecentDeletedRowNo()来查询删除的是那一行(如果是一次删除多行，则为删除的第一行)

    talbe的ID建议为PageCode + "_Data",不能与其他tablei的id相同
    thead必须包含和表格主体一样的列数
    tfoot必须出现在tbody前
    -号按钮命名建议为"button_" + pageCode + "_Delete",确保不与其他元素重名即可
    +号按钮命名建议为"button_" + pageCode + "_Insert",确保不与其他元素重名即可
-->
*/
var recentDeletedRowNo = 0;

/**
 返回最近被删除的行的序号,如果是一次删除多行，则为删除的第一行的序号
 */
function getRecentDeletedRowNo() {
	return recentDeletedRowNo;
}

/**
 直接调用插入函数,仅供高级用户使用
 */
function directInsertRow(pageCode, dataPageCode) {
	return private_insertRow(pageCode, dataPageCode);
}

/**
 直接调用删除函数,仅供高级用户使用
 */
function directDeleteRow(field, pageCode, pageDataRowsCount, controlRowsCount) {
	return private_deleteRow(field, pageCode, pageDataRowsCount, controlRowsCount);
}

/**
 直接调用取页面所有行总数的函数,仅供高级用户使用
 */
function directGetRowsCount(pageCode) {
	return private_getRowsCount(pageCode);
}

/**
 直接调用设置颜色函数,仅供高级用户使用
 */
function directSetRowColor(pageCode, dataPageCode, index, color) {
	private_setRowColor(pageCode, dataPageCode, index, color);
}
/**
 插入函数,Framework使用
 */
function insertRow(pageCode) {
	var obj;
	var index;
	//Call beforeInsertRow of pageCode
	obj = eval("window.beforeInsert" + pageCode);
	if (obj != null) {
		obj.apply();
	}

	//call realy insertRow of pageCode
	obj = eval("window.insert" + pageCode);
	if (obj != null) {
		index = obj.apply();
	} else { //如果没有自定义添加方法则调用默认的添加方法
		index = directInsertRow(pageCode, pageCode + "_Data");
	}

	//Call afterInsertRow of pageCode
	obj = eval("window.afterInsert" + pageCode);
	if (obj != null) {
		obj.apply();
	}


	//  directSetRowColor(pageCode,pageCode+"_Data",index,"#EEECFA")
}

/**
 删除函数,Framework使用
 */
function deleteRow(field, pageCode) {

	var obj;
	var index;

	//Call beforeDeleteRow of pageCode
	obj = eval("window.beforeDelete" + pageCode);
	if (obj != null) {
		obj.apply(obj, arguments);
	}

	//call realy insertRow of pageCode
	obj = eval("window.delete" + pageCode);
	if (obj != null) {
		index = obj.apply(obj, arguments);
	} else { //如果没有自定义删除方法则调用默认的删除方法
		index = directDeleteRow(field, pageCode, 1, 1);
	}

	//Call afterDeleteRow of pageCode
	obj = eval("window.afterDelete" + pageCode);
	if (obj != null) {
		obj.apply(obj, arguments);
	}
}

/**
  在表格下方添加一组数据，禁止非本模块调用
  参数为页代码名称和页原始数据代码名称
  例:insertRow("Engage","Engage_Data");
  返回插入行的序号（从1开始）
  */
function private_insertRow(pageCode, dataPageCode) {

	var oTBODY = document.getElementById(pageCode).tBodies.item(0);
	var oTBODYData = document.getElementById(dataPageCode).tBodies.item(0);
	var rowsCount = oTBODYData.rows.length;
	for (var i = 0; i < rowsCount; i++) {
		oTBODY.appendChild(oTBODYData.rows[i].cloneNode(true));
	}
	return private_getRowsCount(pageCode);
}

/**
  删除控制按钮控制的行，禁止非本模块调用
  字段，页名称，数据页中控制按钮的个数，数据页中每个控制按钮的控制的TR的个数
  返回删除行的序号（从1开始）
 */
function private_deleteRow(field, pageCode, pageDataRowsCount, controlRowsCount) {
	recentDeletedRowNo = parseInt(getElementOrder(field));
	var order = recentDeletedRowNo - 1; //顺序改为以0开始
	var oTBODY = document.getElementById(pageCode).tBodies.item(0);
	order = order - pageDataRowsCount; //去掉隐含域中的控制按钮的个数
	for (var i = 0; i < controlRowsCount; i++) {
		oTBODY.removeChild(oTBODY.rows[order * controlRowsCount]);
	}
	return recentDeletedRowNo - 1;
}
/**
  得到一页的多行纪录的记录数
*/
function private_getRowsCount(pageCode) {
	var oTBODY = document.getElementById(pageCode).tBodies.item(0);
	var intCount = oTBODY.rows.length;
	return intCount;
}

/**
 * 设置一行的颜色
 */
function private_setRowColor(pageCode, dataPageCode, index, color) {
	var i = 0;
	var command = "";
	var elements = getTableElements(dataPageCode);
	var elementsCount = elements.length;
	for (i = 0; i < elementsCount; i++) {
		command = "document.getElementsByName('" + elements[i].name + "')[" + index + "].style.backgroundColor = color;"
		eval(command);
	}
}


////清除一页的所有多行纪录
////页名称
//function deleteAllRows(pageCode)
//{
//  var oTBODY   = document.all(pageCode).tBodies.item(0);
//  var intCount = getRowsCount(pageCode);
//
//  for(var i=0;i<intCount;i++)
//  {
//   oTBODY.deleteRow(0);
//  }
//}
//
////按↓（向下）键时调用页面的"insert" + pageCode + "()"方法
//function fieldInsertRow(pageCode)
//{
//  if( window.event.keyCode==40)
//  {
//    eval("insert" + pageCode + "()");
//  }
//}