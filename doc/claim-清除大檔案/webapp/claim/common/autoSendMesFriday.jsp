
<jsp:directive.page import="com.sinosoft.app.ewps.activities.web.EwpsDetailExportToExcelAction"/>

<%
EwpsDetailExportToExcelAction ewpsDetailExportToExcelAction = new EwpsDetailExportToExcelAction();
ewpsDetailExportToExcelAction.autoExportToExcel();
%>
