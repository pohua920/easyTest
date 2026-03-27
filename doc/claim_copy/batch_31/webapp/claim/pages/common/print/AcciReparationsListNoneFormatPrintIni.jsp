<%--

***********************************************************

*DESC       : 意健险理赔批单打印初始化页面

*AUTHOR     : wangwei

*CREATEDATE : 2005-08-18

*MODIFYLIST :    ID          DATE            Reason/contents

*            -----------------------------------------------

************************************************************

--%>



<%@page import="java.util.*,com.sinosoft.claim.dto.custom.CompensateDto" %>

<%@page import="com.sinosoft.claim.dto.domain.PrpLctextDto" %>

<%@page import="com.sinosoft.claim.ui.control.action.UICodeAction" %>



<%
	String strContext = ""; //批文文字信息

	String strHandlerCode = ""; //经办人代码

	String strHandlerName = ""; //经办人

	CompensateDto compensateDto = (CompensateDto) request.getAttribute("compensateDto");

	ArrayList prpLctextDtoList = (ArrayList) compensateDto.getPrpLctextDtoDtoList();

	PrpLctextDto prpLctextDto = null;

	for (int i = 0; i < prpLctextDtoList.size(); i++) {

		prpLctextDto = (PrpLctextDto) prpLctextDtoList.get(i);

		strContext = strContext + prpLctextDto.getContext();

	}

	strHandlerCode = compensateDto.getPrpLcompensateDto().getHandlerCode();

	UICodeAction uiCodeAction = new UICodeAction();

	strHandlerName = uiCodeAction.translateUserCode(strHandlerCode, true);
%>



<script language="javascript">

    function loadForm() {

        tdCompensateNo.innerHTML = '计算书号:' + '<%=compensateDto.getPrpLcompensateDto().getCompensateNo()%>';

        tdClaimNo.innerHTML      = '赔案号:' + '<%=compensateDto.getPrpLcompensateDto().getClaimNo()%>';

    }

</script>