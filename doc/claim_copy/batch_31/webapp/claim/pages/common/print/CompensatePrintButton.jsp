<%--
****************************************************************************
* DESC       ：打印按钮页面
* Author     : 东安项目组
* CREATEDATE ：2002-11-19
* MODIFYLIST ：   Name       Date            Reason/Contents
*
****************************************************************************/
--%>

  <script language="VBScript">
    // 避免弹出安全警告框的说明：Internet选项=〉安全=〉受信任的站点 
    // 1.将网站加入受信任站点，
    // 2.自定义级别中 启用 对没有标记为安全的ActiveX控件进行初始化和脚本运行    

    dim hkey_root,hkey_path,hkey_key
    hkey_root="HKEY_CURRENT_USER"
    hkey_path="\Software\Microsoft\Internet Explorer\PageSetup"
    
    dim oldheader,oldfooter,oldleft,oldright,oldtop,oldbottom
    
    '//设置网页打印的页眉页脚，上下左右
    function pagesetup_set(header,footer,oldleft,oldright,oldtop,oldbottom)
        on error resume next
        Set RegWsh = CreateObject("WScript.Shell")
        hkey_key="\header"          
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,header
        hkey_key="\footer"
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,footer
        
        
        hkey_key="\margin_left" '左
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,oldleft
        hkey_key="\margin_right" '右
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,oldright
        hkey_key="\margin_top" '上
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,oldtop
        hkey_key="\margin_bottom" '下
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,oldbottom
        
    end function
    '//设置网页打印的页眉页脚,上下左右为默认值
    function pagesetup_default()
        on error resume next
        Set RegWsh = CreateObject("WScript.Shell")
        hkey_key="\header"    
        'RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"&w&b页码，&p/&P"
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,""
        hkey_key="\footer"
        'RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"&u&b&d"
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,""
        
        hkey_key="\margin_left" '左
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.75"     '(对应 19.05毫米)
        'message = message & "左:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        hkey_key="\margin_right" '右
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.75"
        'message = message & "右:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        hkey_key="\margin_top" '上
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.75"
        'message = message & "上:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        hkey_key="\margin_bottom" '下
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.75"  
        'message = message & "下:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        
        'msgbox (message)      
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
        oldleft=RegWsh.RegRead( hkey_root+hkey_path+hkey_key)
        'message = message & "左:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        hkey_key="\margin_right" '右
        oldright=RegWsh.RegRead( hkey_root+hkey_path+hkey_key)
        'message = message & "右:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        hkey_key="\margin_top" '上
        oldtop=RegWsh.RegRead( hkey_root+hkey_path+hkey_key)
        'message = message & "上:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        hkey_key="\margin_bottom" '下
        oldbottom=RegWsh.RegRead( hkey_root+hkey_path+hkey_key)
        'message = message & "下:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        
        'msgbox (message)
    end function    
    
    function printPage()
		on error resume next
		pagesetup_get()         '读取旧值
		'header=""
		'footer=""		
		'pagesetup_get()
		pagesetup_default()
		'pagesetup_set header, footer
		 divButton.style.display = "none"
		'accountButton.style.display="none"
    window.print()
		pagesetup_set oldheader, oldfooter, oldleft, oldright, oldtop, oldbottom            '恢复設定
        divButton.style.display = ""
    end function
</script> 
<script LANGUAGE="JavaScript">

</script> 
 
<OBJECT  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0   id=WebBrowser   width=0></OBJECT>  
  
<div align="center" id="divButton" style="display:">
<p>
  <table cellpadding="0" cellspacing="0" width="80%" style="display:">
    <tr>
      <td class=button style="width:33%" align="center">
        <input class="button" type="button"  name="buttonPrint" value=" 列 印 "  onclick="vbscript:printPage()">
      </td>
      <td class=button style="width:33%" align="center">
        <input class="button" type="button"  name="buttonClose" value=" 关 闭 "  onclick="javascript:window.close();">
      </td>
      <td class=button style="width:33%" align="center">
        <input class="button" type="button"  name="buttonPrintAdd" value=" 列印附页 "  onclick="javascript:printPageAdd();" disabled="disabled">
      </td>
      <%--<td class=button style="width:33%" align="center">
        <input class="button" type="button"  name="buttonSetUp" value="页面設定 "  onclick="document.all.WebBrowser.ExecWB(8,1);">
      </td>
      
    --%></tr>
  </table>
</p>
</div>
