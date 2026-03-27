<%@ page language="java" pageEncoding="GBK"%>
<%@ include file="/common/i18njs.jsp"%>
<%@page import="java.util.Iterator"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String oldPolicyNo = (String)request.getParameter("oldPolicyNo");
int rowsPerPage = 10;   //每页显示行数
int pageNo = Integer.parseInt(request.getParameter("pageNo"));   //跳转的页数
int totalPage = 0;         //总页数
int currentPage = pageNo;       //当前页
int allRows = 0;           //总行数
%>
<%!
	public String checkNull(String str){
		String returnStr = "";
		if(str==null || "null".equals(str) )
			return returnStr;
		else
			return str;
	
	}
%>
<%@page import="java.util.Vector" %>
<html>
  <head>
    <jsp:include page="/common/meta_css.jsp" />
	<jsp:include page="/common/meta_js.jsp" />
    <base href="<%=basePath%>">
    
    <title>上一张保单赔案信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
	
	<script type="text/javascript">
		function skipPage(){
			var page = parseInt(document.getElementById('txtPage').value);
			var totalPage = parseInt(document.getElementById("totalPage").innerHTML);
			if(page>totalPage || page==0){
				alert("请您输入正确的页码！");
				return false;
			}
				
			if(page!=null && page!="" && (page<=totalPage || page!=0)){
				location="/undwrt/common/LastPolicyClaimInfoList.jsp?oldPolicyNo=<%=oldPolicyNo%>&pageNo="+page;
			}
			
		}
		
	</script>
  </head>
  
  <body>
    <table id="mainTable" width="100%" cellpadding="8" cellspacing="1" align="center">
   		<tr class="listtitle">
   			<td width="8%" align="center" colspan='9'>保单<%=oldPolicyNo %>赔案信息列表</td>
   		</tr>
   		<tr class="listtitle">
   			<td width="2%" align="center">序号</td>
   			<td width="10%" align="center">报案案号</td>
   			<td width="10%" align="center">立案案号</td>
   			<td width="6%" align="center">出险时间</td>
   			<td width="28%" align="center">出险原因</td>
   			<td width="4%" align="center">赔案进度</td>
   			<td width="6%" align="center">理赔人员</td>
   			<td width="8%" align="center">已决金额</td>
   			<td width="8%" align="center">未决金额</td>
   		</tr>

<%					
	String sql = "SELECT * FROM ( SELECT row_.*, rownum rownum_ FROM ( "+
			"select r.registno,c.claimno, to_char(r.damagestartdate,'yyyy-mm-dd') damagestartdate,r.damagename,c.endcasedate,u.username, "+      
			"sum(decode(c.endcasedate,null,c.sumclaim -decode(s.underwriteflag, '1', s.sumpaid, '3', s.sumpaid, 0), 0)) as outstanding, "+      
			"sum(decode(s.underwriteflag, '1', s.sumpaid, '3', s.sumpaid, 0)) as sumpaid "+
			"from prplregist r, prplclaim c, prplcompensate s, prpduser u "+
			"where "+
			"r.registno = c.registno(+) "+
			"and (r.canceldate = '' or r.canceldate is null) "+
			"and c.claimno = s.claimno(+)  "+
			"and c.policyno = s.policyno(+)  "+
			"and c.handlercode =  u.usercode(+) "+
			"and r.policyno = '"+oldPolicyNo+"' "+
			"group by r.registno,c.claimno,r.damagestartdate,u.username,r.damagename,c.endcasedate "+
			"order by damagestartdate desc "+
			") row_ WHERE rownum <="+rowsPerPage*currentPage+") WHERE rownum_ >"+rowsPerPage*(currentPage-1); 
					
				  
	System.out.println(sql);
	//modify by xjb for 页面分页错误--begin
	String getCount_sql =  " select count(*) from prplregist r, prplclaim c "+
							  " where r.registno = c.registno(+) "+
							  " and (r.canceldate = '' or r.canceldate is null) "+
							  "  and r.policyno = '"+oldPolicyNo+"' ";
	//modify by xjb for 页面分页错误--end
	com.sinosoft.sysframework.reference.DBManager dbManager = new com.sinosoft.sysframework.reference.DBManager();
	com.sinosoft.claim.resource.dtofactory.domain.DBPrpLclaimBase dbPrpLclaimBase= new com.sinosoft.claim.resource.dtofactory.domain.DBPrpLclaimBase(dbManager);
	java.util.Collection collection = new java.util.ArrayList();
	java.sql.ResultSet rs = null;
	try{
		dbManager.open(com.sinosoft.sysframework.reference.AppConfig.get("sysconst.UNDWRTDATASOURCE"));
		rs = dbManager.executeQuery(getCount_sql);
		if(rs.next())
			allRows = rs.getInt(1);
		rs = dbManager.executeQuery(sql);
		//System.out.println("总记录数===="+allRows);
		if(allRows>0 && allRows<rowsPerPage){
			totalPage = 1;
			currentPage = 1;
		}else if(allRows>=rowsPerPage && allRows%rowsPerPage==0){
			totalPage = allRows/rowsPerPage;
			currentPage = 1;
		}else if(allRows>=rowsPerPage && allRows%rowsPerPage!=0){
			totalPage = allRows/rowsPerPage+1;
		}
		//System.out.println("总页数===="+totalPage);
		
		int i = 0;
	    while(rs.next()){
	    	i++;
%>		
			<tr>
				<td align="center"><%=(currentPage-1)*rowsPerPage+i %></td>
				<td  align="center"><%=checkNull(rs.getString(1)) %></td>
				<td align="center"><%=checkNull(rs.getString(2)) %></td>
				<td align="center"><%=checkNull(rs.getString(3)) %></td>
				<td align="left"><%=checkNull(rs.getString(4)) %></td>
				<%if(!"".equals(checkNull(rs.getString(5)))) {%>
				<td align="center">已结案</td>
				<%}else{ %>
				<td align="center">未结案</td>
				<%} %>
				<td align="center"><%=checkNull(rs.getString(6)) %></td>
				
				<td align="center"><%=rs.getDouble(8) %></td>
				<td align="center"><%=rs.getDouble(7) %></td>
			</tr>
		
		
				
<%	
	    }
	    if(rs!=null)
			rs.close();
%>
   </table>
<%	    
if(i>0){
%>
	<table style="font-size:13;" width="1000" class=common>
		<tr class="common">
   			<td style="font-size:13;">

					<input class="button" id="fistPage" type="button"  <%if(currentPage<=1){ %> disabled='disabled' <%} %> value="首页"
   						onclick="location='/undwrt/common/LastPolicyClaimInfoList.jsp?oldPolicyNo=<%=oldPolicyNo%>&pageNo=1'"/>
   					<input class="button" id="previousPage" type="button" value="上一页" <%if(currentPage<=1){ %> disabled="disabled" <%} %>
   						onclick="location='/undwrt/common/LastPolicyClaimInfoList.jsp?oldPolicyNo=<%=oldPolicyNo%>&pageNo='+<%=currentPage-1 %>"/>




					<input class="button" id="nextPage" type="button" value="下一页" <%if(currentPage>=totalPage){%>disabled='disabled'<%} %>
   						onclick="location='/undwrt/common/LastPolicyClaimInfoList.jsp?oldPolicyNo=<%=oldPolicyNo%>&pageNo='+<%=currentPage+1 %>"/>
   					<input class="button" id="lastPage" type="button" value="最后一页" <%if(currentPage>=totalPage){%>disabled='disabled'<%} %>
   						onclick="location='/undwrt/common/LastPolicyClaimInfoList.jsp?oldPolicyNo=<%=oldPolicyNo%>&pageNo='+<%=totalPage %>"/>

					

<%
				if(totalPage==0){

				}else{
%>
					&nbsp;&nbsp;&nbsp;&nbsp;<span>第<%=currentPage %>页</span>&nbsp;&nbsp;
   					共<span id='totalPage'><%=totalPage %></span>页
   					
   					&nbsp;&nbsp;<span>转到</span>
   					<input style="width:50;" id="txtPage" type="text" value="" onkeyup="this.value=this.value.replace(/\D/g,'')" />
   					<span>页</span>
   					<img src='images/btnGo.gif' align='middle' style='cursor:hand' border='0' alt='跳到' onclick="return skipPage();">
<%					
				}
	    

%>
        </td>
        </tr>
        </table>
<%
	    }
		if(i<=0){
%>
   		<table style="font-size:13;" width="1000" class=common>
   		<tr class="listtitle">
   			<td  align="center"><font color='red'>保单<%=oldPolicyNo %> 无赔案数据</font></td>
   		</tr>
		</table>
<%	    		    
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		if(dbManager!=null)
			dbManager.close();
	}
		
%>		
  </body>
</html>
