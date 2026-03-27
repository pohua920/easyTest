
  <!--****************************************************************************************
 * DESC       ：首页,初始化配置
* Author     : 东安项目组
 * CREATEDATE ：2002-07-12
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *
 *****************************************************************************************-->
<%@ page import="com.sinosoft.platform.bl.action.custom.*"%>

<%@ page errorPage="/UIErrorPage"%>

<%
  
  BLPowerAction blPowerAction = new BLPowerAction();

	blPowerAction.clearCacheMap();
	System.out.println("清空CacheMap成功");
	out.println("清空CacheMap成功");
%>

