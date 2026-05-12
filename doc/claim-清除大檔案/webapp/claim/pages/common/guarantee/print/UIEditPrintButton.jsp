
<%--

****************************************************************************

* DESC       ：打印按钮页面

* Author     : 东安项目组

* CREATEDATE ：2002-11-19

* MODIFYLIST ：   Name       Date            Reason/Contents

*

****************************************************************************/

--%>
<%
  String ClaimNo=request.getParameter("BizNo");
  String PrintType = request.getParameter("PrintType");
  String showOnly = request.getParameter("showOnly");
%>

<script type="text/javascript">
  function edit(PrintType) {
  		var pageWidth=screen.availWidth-10;
  		var pageHeight=screen.availHeight-30;
  		if (pageWidth<100 )
    		pageWidth = 100;
  		if (pageHeight<100 )
    		pageHeight = 100;
    	mesg=	open("/claim/common/guarantee/print/UIEditReport.jsp?BizNo=<%=ClaimNo%>&PrintType="+PrintType,"edit",'width='+pageWidth+',height='+pageHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');


  		//mesg.focus(); 
  		return mesg;
	}
</script>

<%

  String strRiskCode_ForPrint = String.valueOf(session.getValue("RiskCode"));

  String strFlag_Print = request.getParameter("FlagForPrint");
  
  String notEdit = request.getParameter("NotEdit");
  

  System.out.println(">>>>>>>>><<<<<<<<<<<<<<<......RiskCode...." + strRiskCode_ForPrint + "....flag...." + strFlag_Print);

%>



  <script language="VBScript">

    // 避免弹出安全警告框的说明：Internet选项=〉安全=〉受信任的站点 

    // 1.将网站加入受信任站点，

    // 2.自定义级别中 启用 对没有标记为安全的ActiveX控件进行初始化和脚本运行    



    dim hkey_root,hkey_path,hkey_key

    hkey_root="HKEY_CURRENT_USER"

    hkey_path="\Software\Microsoft\Internet Explorer\PageSetup"

    

    dim oldheader,oldfooter   

    

    

    

    'add by meihuidong,20051118, reason:控制页面边距， Begin 注意：这个函数针对“非车险”

    '设置网页打印的页眉页脚,上下左右为默认值

    function pagesetup_print(strRiskCode,strFlag)

    	

        on error resume next

        Set RegWsh = CreateObject("WScript.Shell")

        hkey_key="\header"

        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,""

        hkey_key="\footer"

        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,""

      

                    

        if strRiskCode = 1804 or strRiskCode = 1807  or strRiskCode = 1809  or strRiskCode = 1811 or strRiskCode = 1903 then

          hkey_key="\margin_left" '左

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.7500"

          hkey_key="\margin_right" '右

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.23819"

          hkey_key="\margin_top" '上

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"1.3012"

          hkey_key="\margin_bottom" '下

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.2381"  

        elseif strRiskCode=101 or strRiskCode=102 or strRiskCode=103 or strRiskCode=104 or strRiskCode=105 then    

                   

            hkey_key="\margin_left" '左

            RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.2512"

            hkey_key="\margin_right" '右

            RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.4519"

            hkey_key="\margin_top" '上

            RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"1.2952"

            hkey_key="\margin_bottom" '下

            RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.2381"  

         

        elseif strRiskCode=301 then

          hkey_key="\margin_left" '左

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.2512"

          hkey_key="\margin_right" '右

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.2381"

          hkey_key="\margin_top" '上

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"1.2952"

          hkey_key="\margin_bottom" '下

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.23819"  

        elseif strRiskCode=1001 or strRiskCode=1002 then

          hkey_key="\margin_left" '左

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.1222"

          hkey_key="\margin_right" '右

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.1222"

          hkey_key="\margin_top" '上

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"1.4952"

          hkey_key="\margin_bottom" '下

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.23819" 

        else     '这个else分支涉及到的险种主要包括：27类。     

          hkey_key="\margin_left" '左

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.6512"

          hkey_key="\margin_right" '右

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.4381"

          hkey_key="\margin_top" '上

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"1.2952"

          hkey_key="\margin_bottom" '下

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.13819"

        end if

        

        '批单打印页面边距控制，因为是公用文件，所以都单独的控制分支。strFlag=30：批单套印，strFlag=31：批单全印。

        if strFlag=30 then            

          hkey_key="\margin_left" '左

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.2512"

          hkey_key="\margin_right" '右

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.4519"

          hkey_key="\margin_top" '上

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"1.2952"

          hkey_key="\margin_bottom" '下

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.2381"

        elseif strFlag=31 then

          hkey_key="\margin_left" '左

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.5512"

          hkey_key="\margin_right" '右

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.4519"

          hkey_key="\margin_top" '上

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"1.2952"

          hkey_key="\margin_bottom" '下

          RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.2381"

        end if

             

   end function  

   'add by meihuidong,20051118, reason:控制页面边距， End

   

   

    

    '//设置网页打印的页眉页脚，上下左右

    function pagesetup_set(header,footer)

        on error resume next

        Set RegWsh = CreateObject("WScript.Shell")

        hkey_key="\header"          

        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,header

        hkey_key="\footer"

        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,footer

        

        

        hkey_key="\margin_left" '左

        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.52"

        hkey_key="\margin_right" '右

        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.60"

        hkey_key="\margin_top" '上

        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"1.25"

        hkey_key="\margin_bottom" '下

        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.79"

        

    end function

    '//设置网页打印的页眉页脚,上下左右为默认值

    function pagesetup_default()

        on error resume next

        Set RegWsh = CreateObject("WScript.Shell")

        hkey_key="\header"    

        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,""   '&w&b页码，&p/&P

        hkey_key="\footer"

        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,""   '&u&b&d

        

        hkey_key="\margin_left" '左

        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.75"     '(对应 19.05毫米)

        hkey_key="\margin_right" '右

        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.25"

        hkey_key="\margin_top" '上

        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"1.05"

        hkey_key="\margin_bottom" '下

        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.25"        

    end function

    

    '//显示页面设置

    function pagesetup_get()

        on error resume next

        Set RegWsh = CreateObject("WScript.Shell")

        hkey_key="\header"    

        oldheader=RegWsh.RegRead(hkey_root+hkey_path+hkey_key)

        hkey_key="\footer"

        oldfooter=RegWsh.RegRead(hkey_root+hkey_path+hkey_key)

        

        hkey_key="\margin_left" '左

        message = message & "左:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf

        hkey_key="\margin_right" '右

        message = message & "右:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf

        hkey_key="\margin_top" '上

        message = message & "上:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf

        hkey_key="\margin_bottom" '下

        message = message & "下:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf

        

        msgbox (message)

    end function    

    

    function printPage1()

      'pagesetup_get()         '读取旧值

      'header=""

      'footer=""		

      'pagesetup_get()

      'pagesetup_set header, footer

      'divButton.style.display = "none"

      'pagesetup_print <%=strRiskCode_ForPrint%>,<%=strFlag_Print%>

      window.print()

      'pagesetup_default()            '恢复設定

    end function

</script>

 

<div align="center" id="divButton" style="display:">

<p>

  <table cellpadding="0" cellspacing="0" width="80%" style="display:">

    <tr>
    <%
      	if((showOnly==null||"".equals(showOnly)) && notEdit != null && !notEdit.trim().equals("")){
      %>

      <td class=button style="width:30%" align="center">

        <input class="button" type="button"  name="buttonPrint" alt="列印" value="列印" onclick="printPage()">

      </td>
      <%
        }
       %>
      <%
      	if((showOnly==null||"".equals(showOnly)) && notEdit == null || notEdit.trim().equals("")){
      %>
      <td class=button style="width:20%" align="center">

        <input class="button" type="button"  name="edit" alt="编辑" value="编辑" onclick="edit('<%=PrintType%>')">

      </td>
      <%
      	}
      %>
      <td class=button style="width:20%" align="center">

<%--        <input class="button" type="button"  name="buttonPrintPreview" alt="打印预览" value="打印预览" onclick="printPreview()">--%>

      </td>
      <td class=button style="width:30%" align="center">

        <input class="button" type="button"  name="buttonClose" alt="关闭" value="关闭" onclick="javascript:window.close()">

      </td>

    </tr>
<span>
<OBJECT ID="DS_Printer" border=0
	CLASSID = "CLSID:24DDA709-7162-4CAD-8575-5DB572479D32">
</object>
</span>

  </table>

</p>

</div>

<span id="SpanID"><OBJECT id="WB"   border=0
	CLASSID = "CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height=0 width=0 VIEWASTEXT>
</OBJECT>
</span>

<script language="javascript">
    function printPage(){
    	//add print liudaoping 2013-04-15
        //alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
        return false;
        divButton.style.display = "none";
        try{
            <%if(strRiskCode_ForPrint.startsWith("09")){%>
                //纸张大小
                DS_Printer.SetPaperSize(21500,28000);
                
                //上边距
                DS_Printer.SetTopMargin(3190);//1/100 mm
                
                //下边距
                DS_Printer.SetBottomMargin(1505);
                
                //左边距
                DS_Printer.SetLeftMargin(405);
                
                //右边距
                DS_Printer.SetRightMargin(1905);
            <%}else if(strRiskCode_ForPrint.startsWith("10")){%>
                DS_Printer.SetPaperSize(23500,30500);
                DS_Printer.SetTopMargin(3190);
                DS_Printer.SetBottomMargin(1505);
                DS_Printer.SetLeftMargin(405);
                DS_Printer.SetRightMargin(1905);
            <%}else if(strRiskCode_ForPrint.startsWith("2712")||strRiskCode_ForPrint.startsWith("2716")){%>
                DS_Printer.SetPaperSize(21000,8800);
                DS_Printer.SetTopMargin(405);
                DS_Printer.SetBottomMargin(405);
                DS_Printer.SetLeftMargin(405);
                DS_Printer.SetRightMargin(405);
            <%}else if(strRiskCode_ForPrint.startsWith("2713")){%>
                DS_Printer.SetPaperSize(21000,8800);
                DS_Printer.SetTopMargin(405);
                DS_Printer.SetBottomMargin(405);
                DS_Printer.SetLeftMargin(405);
                DS_Printer.SetRightMargin(405);
            <%}else{%>
            	//DS_Printer.SetPaperSize(34544,10000);
                DS_Printer.SetPaperSize(21500,28000);
                DS_Printer.SetTopMargin(3190);
                DS_Printer.SetBottomMargin(1505);
                DS_Printer.SetLeftMargin(405);
                DS_Printer.SetRightMargin(1905);
            <%}%>
        }catch(e){
            
        }
        window.print();
    }
    
     function printPreview(){
    	//add print liudaoping 2013-04-15
        //alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
         return false;
     		SpanID.innerHTML = "<OBJECT id=\"WB\"   border=0	CLASSID = \"CLSID:8856F961-340A-11D0-A96B-00C04FD705A2\" height=0 width=0 VIEWASTEXT></OBJECT>";
        divButton.style.display = "none";
        try{
            <%if(strRiskCode_ForPrint.startsWith("09")){%>
                //纸张大小
                DS_Printer.SetPaperSize(21500,28000);
                
                //上边距
                DS_Printer.SetTopMargin(3190);//1/100 mm
                
                //下边距
                DS_Printer.SetBottomMargin(1505);
                
                //左边距
                DS_Printer.SetLeftMargin(405);
                
                //右边距
                DS_Printer.SetRightMargin(1905);
            <%}else if(strRiskCode_ForPrint.startsWith("10")){%>
                DS_Printer.SetPaperSize(23500,30500);
                DS_Printer.SetTopMargin(3190);
                DS_Printer.SetBottomMargin(1505);
                DS_Printer.SetLeftMargin(405);
                DS_Printer.SetRightMargin(1905);
            <%}else if(strRiskCode_ForPrint.startsWith("2712")||strRiskCode_ForPrint.startsWith("2716")){%>
                DS_Printer.SetPaperSize(21000,8800);
                DS_Printer.SetTopMargin(405);
                DS_Printer.SetBottomMargin(405);
                DS_Printer.SetLeftMargin(405);
                DS_Printer.SetRightMargin(405);
            <%}else if(strRiskCode_ForPrint.startsWith("2713")){%>
                DS_Printer.SetPaperSize(21000,8800);
                DS_Printer.SetTopMargin(405);
                DS_Printer.SetBottomMargin(405);
                DS_Printer.SetLeftMargin(405);
                DS_Printer.SetRightMargin(405);
            <%}else{%>
                DS_Printer.SetPaperSize(23500,28000);
                //DS_Printer.SetPaperSize(34544,10000);
                DS_Printer.SetTopMargin(3190);
                DS_Printer.SetBottomMargin(1505);
                DS_Printer.SetLeftMargin(405);
                DS_Printer.SetRightMargin(1905);
            <%}%>
        }catch(e){
            
        }
       var ad = document;
       var ada = ad.all;
       var   adaw = ada.WB;
       adaw.ExecWB(7,1);
    }
    
</script>