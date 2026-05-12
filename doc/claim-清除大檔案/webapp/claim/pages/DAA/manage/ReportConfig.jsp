<%@page import="java.util.*"%>
<%@page import="java.lang.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>

<%
	MidResultConfigDto midResultConfigDto = null;
	Vector vecConfigs = new Vector();

	final int N = 4; //count of erery row show
	int intCountFlag = 0;
	int intCount = 0; //totol rows
	int i = 0;
	int k = 0;
	boolean isExistSumConfig = false;
%>
<%
	vecConfigs = (Vector) session.getAttribute("Configs");
	ArrayList resultSumConfig = (ArrayList) vecConfigs.get(1);
	session.setAttribute("ConfigsSum", resultSumConfig);
%>
