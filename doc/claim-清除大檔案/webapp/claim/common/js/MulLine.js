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
      function beforeInsert<pageCode>()
      {
        ...
      }
      function insert<pageCode>()
      {
        ...
      }
      function afterInsert<pageCode>()
      {
        ...
      }
      function beforeDelete<pageCode>(field)
      {
        ...
      }
      function delete<pageCode>(field)
      {
        ...
      }
      function afterDelete<pageCode>(field)
      {
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
function getRecentDeletedRowNo()
{
  return recentDeletedRowNo;
}

/**
 直接调用插入函数,仅供高级用户使用
 */
function directInsertRow(pageCode,dataPageCode)
{
   return private_insertRow(pageCode,dataPageCode);
}

/**
 直接调用删除函数,仅供高级用户使用
 */
function directDeleteRow(field,pageCode,pageDataRowsCount,controlRowsCount)
{
   return private_deleteRow(field,pageCode,pageDataRowsCount,controlRowsCount);
}

/**
 直接调用取页面所有行总数的函数,仅供高级用户使用
 */
function directGetRowsCount(pageCode)
{
  return private_getRowsCount(pageCode);
}

/**
 直接调用设置颜色函数,仅供高级用户使用
 */
function directSetRowColor(pageCode,dataPageCode,index,color)
{
  private_setRowColor(pageCode,dataPageCode,index,color);
}

/***
 * 添加数据块动作，复制pageCode_Data块下tr并加到table id为pageCode的tbody元素下。
 * 并给当前元素的序号赋值，归属父类赋值
 * @param pageCode 当前被插入的数据块的位置，table所在id。函数会选择其下的tbody插入
 * @param btnField 当前点击的"＋"按钮
 * @param csFieldName 当前被插入元素的序号存储input域
 * @param psFieldName 当前被插入元素所属父块的序号input域
 * 
 */
function insertRow(pageCode,btnField,csFieldName,psFieldName){
	insertRowTableNew(pageCode,pageCode+"_Data",btnField,csFieldName,psFieldName);
}

/***
 * 添加数据块动作，复制pageCode_Data块下tr并加到table id为pageCode的tbody元素下。
 * 并给当前元素的序号赋值，归属父类赋值
 * @param pageCode 当前被插入的数据块的位置，table所在id。函数会选择其下的tbody插入
 * @param pageCode_Data 被插入的数据块所在table位置
 * @param btnField 当前点击的"＋"按钮
 * @param csFieldName 当前被插入元素的序号存储input域
 * @param psFieldName 当前被插入元素所属父块的序号input域
 * 
 */
function insertRowTableNew(pageCode,pageCode_Data,btnField,csFieldName,psFieldName){
  var obj;
  obj = eval("window.beforeInsert" + pageCode);
  var continueflag = true;
  if(obj != null){
	  continueflag = obj.apply(this,arguments);
  }
  if(typeof(continueflag) == 'boolean' && continueflag){
	  obj = eval("window.insert" + pageCode);
	  if(obj != null){//如果有自定义的特殊添加
	      obj.apply();
	  } else { //如果没有自定义添加方法则调用默认的添加方法
		  var $data_to;
		  if(btnField != undefined ){
			  $data_to = $(btnField).closest("tfoot").siblings("tbody");
		  }else{
			  $data_to = $("#"+pageCode).children("tbody");
		  }
		  var $tbody = $("#"+pageCode_Data).children("tbody");
		  var $cloneObject = $tbody.children().clone(true);
		  var $serialNo = document.getElementsByName(csFieldName);
		  if(csFieldName != undefined && csFieldName != ""){//设置被添加元素的序号 
			  var $serialNo = document.getElementsByName(csFieldName);
			  if($serialNo.length-1 == 0){
				  var serialNo = parseInt(($data_to.children().length)/($tbody.children().length)) + 1;
			  }else{
				  var serialNo = parseInt($serialNo[$serialNo.length-1].value) + 1;
			  }
			  $cloneObject.find(":input[name='"+csFieldName+"']").val(serialNo);
		  }
		  if(psFieldName != undefined  && psFieldName != ""){//所属父块的序号即+按钮所在的索引
			  $cloneObject.find(":input[name='"+psFieldName+"']").val($(":input[name='"+btnField.name+"']").index(btnField));
		  }
		  $cloneObject.appendTo($data_to);
		  obj = eval("window.afterInsert" + pageCode);//调用添加后的方法，自定义的特殊处理
		  if(obj != null){
		    obj.call(this,$cloneObject[0],pageCode,btnField,csFieldName,psFieldName);
		  }
	  }
  }
}

/***
 * 删除数据块动作，点击当前“-”按钮field，删除该“-”按钮所在的行，且将其后续元素的序号进行变更，后续元素序号-1。
 * @param btnField 当前点击的"-"按钮
 * @param pageCode 删除的数据块标识
 * @param csFieldName 当前被删除元素的序号存储input域 
 * @param psFieldName 当前被删除元素所属父块的序号input域 （嵌套块，删除父块时，存在该项的话，必输；）
 * 
 */
function deleteRow(btnField,pageCode,csFieldName,psFieldName){
  var obj;
  var continueflag = true;
  obj = eval("window.beforeDelete" + pageCode);
  if(obj != null){
	  continueflag = obj.apply(obj,arguments);
  }
  if(typeof(continueflag) == 'boolean' && continueflag){
	  obj = eval("window.delete" + pageCode);
	  if(obj != null){
		  obj.apply(obj,arguments);
	  } else { //如果没有自定义删除方法则调用默认的删除方法
		  var $p = $(btnField).closest("tbody").children();
		  var index = $p.index($(btnField).closest("tr"));//当前被删除行的索引
		  var clength = $("#"+pageCode+"_Data").children("tbody").children().length;
		  var start = parseInt(index/clength)*clength;//开始行
		  var $deletObject = $p.slice(start,start+clength);//取得被删除的那几行
		  var $nextAll = $deletObject.nextAll();
		  $nextAll.find(":input[name='"+csFieldName+"'],:input[name='"+psFieldName+"']").each(function(){
			  this.value = parseInt(this.value)-1; 
		  });
          $deletObject.remove();
          obj = eval("window.afterDelete" + pageCode);
          if(obj != null){
              obj.call(this,$deletObject[0],btnField,pageCode,csFieldName,psFieldName);
          }
	  }
  }
}
/***
 * 还得继续改造。。。
 * 查找当前currField的父元素下含有fieldName文本域的table
 * @param currField 
 * @param fieldName
 */
function findParents(currField,fieldName){
	var $p = $(currField).parents("table:first");
	if($p.length != 0){
		if($p.find(":input[name='"+fieldName+"']").length != 0){
			return $p.get(0);
		}else{
			return findParents($p.get(0),fieldName);
		}
	}
	return null;
}

/***
 * 查找当前元素所在的根据pagecode复制的数据块对象
 * @param field 所在tr必须是pageCode_Data模板复制出的数据块的一行，
 *              所在tbody必须是以pageCode_Data为模板复制出的全部。否则找不到。
 * @param pageCode
 * @returns jquery 对象
 */
function findPageCodeObject(field,pageCode){
    var $cuur_tr = $(field).closest("tr");//当前行
    var $all_tr = $cuur_tr.closest("tbody").children();//与当前所，所有以pageCode复制出的数据块
    var index = $all_tr.index($cuur_tr);//当前被删除行的索引
    var clength = $("#"+pageCode+"_Data").children("tbody").children().length;
    var start = parseInt(index/clength)*clength;//开始行
    var $obj = $all_tr.slice(start,start+clength);//取得被删除的那几行
    if($obj.length != 0){
        return $obj;
    }
    return null;
}
/**
  在表格下方添加一组数据，禁止非本模块调用
  参数为页代码名称和页原始数据代码名称
  例:insertRow("Engage","Engage_Data");
  返回插入行的序号（从1开始）
  */
function private_insertRow(pageCode,dataPageCode)
{

  var oTBODY     = document.getElementById(pageCode).tBodies.item(0);
  var oTBODYData = document.getElementById(dataPageCode).tBodies.item(0);
  for(var i=0;i<oTBODYData.rows.length;i++)
  {
    oTBODY.appendChild(oTBODYData.rows[i].cloneNode(true));
  }

  return private_getRowsCount(pageCode);
}

/**
  删除控制按钮控制的行，禁止非本模块调用
  字段，页名称，数据页中控制按钮的个数，数据页中每个控制按钮的控制的TR的个数
  返回删除行的序号（从1开始）
 */
function private_deleteRow(field,pageCode,pageDataRowsCount,controlRowsCount)
{
  recentDeletedRowNo = parseInt(getElementOrder(field));
  var order = recentDeletedRowNo - 1;  //顺序改为以0开始
  var oTBODY   = document.getElementById(pageCode).tBodies.item(0);
  order = order - pageDataRowsCount;  //去掉隐含域中的控制按钮的个数
  for(var i=0;i<controlRowsCount;i++)
  {
    oTBODY.removeChild(oTBODY.rows[order*controlRowsCount]);
  }
  return recentDeletedRowNo-1;
}
/**
  得到一页的多行纪录的记录数
*/
function private_getRowsCount(pageCode)
{
  var oTBODY   = document.getElementById(pageCode).tBodies.item(0);
  var intCount = oTBODY.rows.length;
  return intCount;
}

/**
 * 设置一行的颜色
 */
function private_setRowColor(pageCode,dataPageCode,index,color)
{
	var i = 0;
	var command = "";
  var elements = getTableElements(dataPageCode);

  for(i=0;i<elements.length;i++)
  {
  	command = "document.getElementsByName('" + elements[i].name + "')["+index+"].style.backgroundColor = color;"
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
