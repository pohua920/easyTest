<%--
****************************************************************************
* DESC       ：新单证系统标题页面
* AUTHOR     ：YANGXIAOGANG
* CREATEDATE ：2004-07-19
* MODIFYLIST ：Name          Date            Reason/Contents
*              ------------------------------------------------------
*              ZHANGYING     2004-07-23      按规范整理
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.sinosoft.utiall.blsvr.BLUtiUserGrade" %>
<%@ page import="com.sinosoft.utiall.blsvr.BLPrpDcompany" %>
<%@ page import="com.sinosoft.utiall.schema.UtiUserGradeSchema"%>
<%@ page import="com.sinosoft.utility.error.UserException"%>
<%@ page import="com.sinosoft.utility.database.DbPool"%>

<%-- <%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %> --%>

<script language='JavaScript'>
	    function menuSwitch(menuSwitchInput)
    {
        if(menuSwitchInput.value=="open")
        {
             menuSwitchInput.src="/visa/images/BtnMenuOpen.gif";
             menuSwitchInput.value="open";
             parent.fraSet.cols = "180,*";
             return;
        }
        if(menuSwitchInput.value=="close")
        {
             menuSwitchInput.src="/visa/images/BtnMenuClose.gif";
             menuSwitchInput.value="close";
             parent.fraSet.cols = "0,*";
             return;
        }
    }
    //设置操作员信息
    function setTitleInfo(vOperator,vComCode)
    {
      try
      {
        spanOperator.innerHTML = vOperator;
        spanComCode.innerHTML = vComCode;
        
      }
      catch(re)
      {}
    }
    
     //改变险种,改变险类信息列表
    //function changeComSelected()
    //{
     // var vRiskCode = fm.RiskCodeSelect.value;
     // var vClassCode = "";
     // var i = 0;
//
     // //为了获得险类的位置
      //for(i=0;i<fm.ClassCodeSelect.options.length;i++)
      //{
      //  if(fm.ClassCodeSelect.options[i].value!=vRiskCode.substr(0,2))
      //    continue;
      //  vClassCode = vRiskCode.substr(0,2);
      //  break;
      //}

      //异常处理
      //if(vClassCode=="")
      //{
      //  vClassCode = "01";
      //  i = 0;
      //}

      //fm.ClassCodeSelect.value = vClassCode;
      //fm.ClassCodeSelect.options[i].selected = true;
    //}
    
    //改变机构重新提交
    function changeCom()
    { 
      fm.target = "fraInterface";//在哪个页面打开
      fm.action = "/visa/common/UILogonSubmit.jsp";
      fm.submit();
    }
    
     //给机构显示重新赋值
    function setOptionComCode(vChangeComCode)
    {
      for(var i=0;i<fm.ComCodeSelect.options.length;i++)
      {
        //如果相等则显示check
        if(fm.ComCodeSelect.options[i].value==vChangeComCode)
        {
          fm.ComCodeSelect.options[i].selected = true;
          //return;
        }
      }
    }
    
</script>
 <script type="text/javascript" language="javascript1.2">
    function setTitle(title)
    {
      fm.textfield.value = title;
    }
    function setCommand(text){
	    document.all("command").innerText="　"+text;
	}

    function FindInSelect(vRiskValue)
    {
      var vRiskCodeSelect;
      for(var i=0;i<fm.RiskCodeSelect.length;i++)
      {
        vRiskCodeSelect = fm.RiskCodeSelect.options[i].value;
        if(vRiskCodeSelect.substr(0,4)==vRiskValue)
        {
          fm.RiskCodeSelect.options[i].selected = true;
          spanRiskCode.innerHTML = vRiskValue;
          break;
        }
      }
    }

	
  </script>
<%
  String strUserCode = (String)session.getAttribute("userCode");
  String strUserName = (String)session.getAttribute("userName");
  String strUserPassword = (String)session.getAttribute("password");
  String strComCode = (String)session.getAttribute("comCode");
  String strComName = (String)session.getAttribute("comName");
  String strGradeCode = (String)session.getAttribute("gradeCodes");
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" style="background:url(/visa/images/body_bg.gif) top repeat-x; padding-top:3px;">
<form name="fm">
<input type="hidden" name="LogonFromPage" value="UITitle">
<link href="/visa/css/Standard.css" rel="stylesheet" type="text/css">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td style="padding:8px 0 9px 9px;">
            <img src="/visa/images/logo.png"></td>
            <td align="right" valign="top" width="100%"><table width="90%" border="0" cellspacing="0" cellpadding="0" style="background:url(/visa/images/top_c.gif) repeat-x;">
              <tr>
              	<td align="left" width="5%"><img src="/visa/images/top_l.gif"/></td>
        <td width="15%" class=common align=right>&nbsp;<%out.println(strUserName);%></td>
        <td width="20%" class=common align=center >&nbsp;<%out.println("<s:text name='prompt.dealOrganization'/>:"+strComName);%></td>
        <td width="40%" class=common align=center >&nbsp;<span id="spanComCode"><%=strComCode%></span>
                  <select name="ComCodeSelect" style="width:170px" class="common" 
                    onchange="changeCom();">
<%
  BLUtiUserGrade blUtiUserGrade = new BLUtiUserGrade();
  BLPrpDcompany  blPrpDcompany =new BLPrpDcompany();
  String condition1 = "UserCode ='" + strUserCode + "'"+" "+"And GradeCode In (Select distinct(GradeCode) From UtiGradeTask Where TaskCode Like'visa%')"+"ORDER BY ComCode";//查询有单证权限的登录机构.
  blUtiUserGrade.query(condition1,0);
  int intSize = blUtiUserGrade.getSize();
  for(int i=0;i<intSize;i++)
  {String condition2 = "ComCode ='"+blUtiUserGrade.getArr(i).getComCode()+"'";
  	blPrpDcompany.query(condition2,0);
  	int inSize =blPrpDcompany.getSize();
  		for(int j=0;j<inSize;j++)
  		{
%>
                    <option value='<%=blUtiUserGrade.getArr(i).getComCode()%>'><%=blUtiUserGrade.getArr(i).getComCode()%>-<%=blPrpDcompany.getArr(j).getComCName()%>
<%
  }
  }
%>

                  </select>
                  </td>
        <script>
        parent.document.frames("fraTitle").setOptionComCode("<%=strComCode%>");
        </script>
        <td width="15%" class=common align=center>&nbsp;<%out.println(new SimpleDateFormat("<s:text name='prompt.formatYearMonthDay'/>").format(new Date()));%></td>
        <td align="right" width="5%"><img src="/visa/images/top_r.gif"/></td>
        </tr>
            </table></td>
    </tr>
</table>
<table width=100% border="0" cellspacing="0" cellpadding="0">
    <tr> 
      <td width=17% height=26 align=center>
      </td>
      <td width="10%" class=white><s:text name='undwrt.pages.undwrtDeal.youNowPosition'/><br> 
      </td>
      <td width="53%" class=white align=left id=command></td>
      <td width="20%" class=white align="center">
      	 <s:text name='prompt.luYanYingDate'/> 
      </td>
      
    </tr>
</table>
</form>
</body>