 <%--
****************************************************************************
* DESC       ：模板路径
* AUTHOR     ：weishixin
* CREATEDATE ：2004-08-10
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
    <%--多行输入自定义JavaScript方法域--%>
 
    <script language='javascript'>
      //在下面加入本页自定义的JavaScript方法

     /*
        插入一条新的WorkFlowPath之後的处理（可选方法）
      */
	function afterInsertWorkFlowPath() {
        setWorkFlowPathNo();
      }
    
      /*
        删除本条之後的处理（可选方法）
      */
	function afterDeleteWorkFlowPath(field) {

        
        //setWorkFlowPathNo();
      }
    
        /**
         * 设置setWorkFlowPathNo
         */
	function setWorkFlowPathNo() {
            var count=getElementCount("swfPathPathNo");
            var i = count -1;

           if(count!=2){
               fm.swfPathPathNo[i].value=parseInt(fm.swfPathPathNo[i-1].value)+1;
		} else {
             fm.swfPathPathNo[1].value = 1;
           }
            
        }
        
        /**
         * 打开路径编辑窗口
         */
	function openWinWorkFlowPathEdit(field) {
           var win ;
           setCurrRecordOrder(field);//设置当前输入的域的顺序
		win = window.showModalDialog(
				"/claim/workflow/model/WorkFlowPathAddEdit.jsp", window,
				'status:no;resizable:yes');
         }
         /**
          * 设置序号
          */
	function setCurrRecordOrder(field) {
            var i = 0;
            i = getElementOrder(field);
            fm.WorkFlowPathRecordOrder.value = i;
            return true;
          } 
    </script> 
   <br>
  
   <!--建立显示的录入条，可以收缩显示的-->
      <span style="display:none"> 
          <table class="common" style="display:none" id="WorkFlowPath_Data" cellspacing="1" cellpadding="0">
           <tbody>
           <tr class=common>                                             
				<td><input name="swfPathPathNo" class="readonly" readonly style='width: 95%' title="路徑號"></td>
				<td><input name=swfPathPathName class="readonly" readonly style="width: 75%" title="路徑名稱"></td>
				<td><input name=swfPathStartNodeNo class="readonly" readonly style="width: 95%" title="起始節點"></td>
				<td><input name=swfPathStartNodeName class="readonly" readonly style="width: 95%" title="起始節點名稱"></td>
				<td><input name=swfPathEndNodeNo class="readonly" readonly style="width: 95%" title="終止節點"></td>
				<td><input name=swfPathEndNodeName class="readonly" readonly style="width: 95%" title="終止節點名稱"></td>
				<td><input name=swfPathPriority class="readonly" readonly style="width: 95%" title="優先級"></td>
				<td><input name=swfPathDefaultFlag class="readonly" readonly style="width: 95%" title="缺省路徑"></td>
				<td><input type=button name=buttonWorkFlowPathEdit value="<s:text name="referlaw.edit"/>" style="cursor: hand" onclick="openWinWorkFlowPathEdit(this);">
				<%-- 编辑--%></td>
          <td >
              <div align="center">
                <input type=button name="buttonWorkFlowPathDelete"  onclick="deleteRow(this,'WorkFlowPath');" value="-" style="cursor: hand">
              </div>
              
           </td>
             
         </tr>
       </tbody>

    </table>
    </span>
        
<span id="spanWorkFlowPath"> <%-- 多行输入展现域 --%>
        <table id="WorkFlowPath" cellpadding="5" cellspacing="1"  class="common">
          <thead>
             <tr>
				<td class="subsubformtitle" colspan=9><s:text name="manage.endTime" />
					<s:text name="workflow.wayInformation" />
					<%--路径信息 --%></td>
             </tr>
             <tr>
				<td class="title" style="width: 8%"><s:text name="workflow.wayNumber" />
					<%--路径号 --%></td>
				<td class="title"><s:text name="workflow.wayName" />
					<%--路径名称 --%></td>
				<td class="title"><s:text name="workflow.startNode" />
					<%--起始节点 --%></td>
				<td class="title"><s:text name="workflow.startNodeName" />
					<%--起始节点名称 --%></td>
				<td class="title"><s:text name="workflow.endNode" />
					<%--终止节点 --%></td>
				<td class="title"><s:text name="workflow.endNodeName" />
					<%--终止节点名称 --%></td>
				<td class="title" style="width: 5%"><s:text name="db.prpDIdentifier.identifierOrder" />
					<%--优先级 --%></td>
				<td class="title" style="width: 5%"><s:text name="workflow.shortWay" />
					<%--缺省路径 --%></td>
				<td class="title" style="width: 8%"><s:text name="workflow.editWay" />
					<%--编辑路径 --%></td>
         <td class="title" style="width:3%">  </td>    
         <input type=hidden name="WorkFlowPathPageID" value="WorkFlowPath">   
         <input type=hidden name="WorkFlowPathRecordOrder" value="">                                    
       </tr> 
          </thead>
          <tfoot>
              <tr>
                <td class="title" colspan="9" style="width:97%"></td>
                 <td class="title" align="right" style="width:4%">
                 <div align="center">
                 <input type="button" class=smallbutton value="+" onclick="insertRow('WorkFlowPath');" name="buttonWorkFlowPathInsert" style="cursor: hand">
                 </div>
                 </td>
              </tr>
           </tfoot> 
           
          <tbody>
			<%
				int indexPath = 0;
			%>
		<logic:notEmpty  name="swfPathDto"  property="pathList"> 
        <logic:iterate id="swfPath" name="swfPathDto" property="pathList">
           <tr class=common>                                             
						<td><input name="swfPathPathNo" class="readonly" readonly style='width: 95%' title="路徑號" value="<bean:write name='swfPath' property='pathNo'/>"></td>
						<td><input name=swfPathPathName class="readonly" readonly style="width: 75%" title="路徑名稱" value="<bean:write name='swfPath' property='pathName'/>"></td>
						<td><input name=swfPathStartNodeNo class="readonly" readonly style="width: 95%" title="起始節點" value="<bean:write name='swfPath' property='startNodeNo'/>"></td>
						<td><input name=swfPathStartNodeName class="readonly" readonly style="width: 95%" title="起始節點名稱" value="<bean:write name='swfPath' property='startNodeName'/>"></td>
						<td><input name=swfPathEndNodeNo class="readonly" readonly style="width: 95%" title="終止節點" value="<bean:write name='swfPath' property='endNodeNo'/>"></td>
						<td><input name=swfPathEndNodeName class="readonly" readonly style="width: 95%" title="終止節點名稱" value="<bean:write name='swfPath' property='endNodeName'/>"></td>
						<td><input name=swfPathPriority class="readonly" readonly style="width: 95%" title="優先級" value="<bean:write name='swfPath' property='priority'/>"></td>
						<td><input name=swfPathDefaultFlag class="readonly" readonly style="width: 95%" title="缺省路徑" value="<bean:write name='swfPath' property='defaultFlag'/>"></td>
						<td><input type=button name=buttonWorkFlowPathEdit class=button value="<s:text name="referlaw.edit"/>" style="cursor: hand" onclick="openWinWorkFlowPathEdit(this);">
						<%--编辑 --%></td>
          <td >
              <div align="center">
                <input type=button name="buttonWorkFlowPathDelete"  class=smallbutton onclick="deleteRow(this,'WorkFlowPath');" value="-" style="cursor: hand">
              </div>
              
           </td>
             
         </tr>       
					<%
						indexPath++;
					%>
        </logic:iterate>
        </logic:notEmpty>  
          </tbody>
        </table>  
        </span>    
  


