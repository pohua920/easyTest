<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
    <head>
        <link href="${ctx}/pages/platform/css/Standard.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <table class="common" cellpadding="5" cellspacing="1" align="center">
            <tr>
                <td class="top">
                    <s:text name="uwlevel.selectLevel" /><%--选择某级别，设置该级别的双核条件。--%>
                </td>
            </tr>
        </table>
        <form name="fm" action="" method="POST">
        </form>
        <script language="javascript">
         function prepareUpdate(actionType, nodeNo){
            fm.action = "/claim/processUwLevel.do?actionType=" + actionType +"&nodeNo=" + nodeNo;
            fm.submit();
         }
        </script>
    </body>
</html>
