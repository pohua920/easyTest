<%--

****************************************************************************

* DESC       ：意健险不予立案通知书打印初始化

* AUTHOR     ：dongchengliang

* CREATEDATE ：2005-8-17

* MODIFYLIST ：   Name       Date            Reason/Contents

****************************************************************************

--%>



<%-- 引入bean类部分 --%>

<%@page import="java.text.*"%>

<%@page import="com.sinosoft.claim.ui.control.action.*"%>

<%@page import="com.sinosoft.claim.dto.custom.*"%>

<%@page import="com.sinosoft.claim.dto.domain.*"%> 

<%@page import="com.sinosoft.claim.util.*"%> 

<%@page import="com.sinosoft.sysframework.common.util.*"%> 

<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%> 

<%@page import="com.sinosoft.sysframework.exceptionlog.*"%> 



<%
 	String strContext = "";

 	RegistDto registDto = (RegistDto) request.getAttribute("registDto");

 	java.util.ArrayList prpLltextDtoList = (java.util.ArrayList) registDto.getPrpLregistTextDtoList();

 	PrpLregistTextDto prpLltextDto = null;

 	if (prpLltextDtoList != null) {

 		for (int i = 0; i < prpLltextDtoList.size(); i++) {

 			prpLltextDto = (PrpLregistTextDto) prpLltextDtoList.get(i);

 			if (prpLltextDto.getTextType().equals("2")) { //类型为2的才是不予立案信息

 				strContext = strContext + prpLltextDto.getContext();

 			}

 		}

 	}
 %>



<script language="javascript">

    function loadForm() {

        tdRregistNo.innerHTML          = '案件号:' + '<%=registDto.getPrpLregistDto().getRegistNo()%>';

    }

</script>