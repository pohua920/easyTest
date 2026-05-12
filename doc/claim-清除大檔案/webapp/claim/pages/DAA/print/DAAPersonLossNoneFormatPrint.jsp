<%--
****************************************************************************
* DESC       ：机动车辆现场查勘记录打印页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%
	//目前不确定取值，等确认後可以单独做一个取值的INI页面
	String strClaimNo = request.getParameter("ClaimNo"); //赔案号
%>
<html>
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<body bgcolor="#FFFFFF">
	<!-- 标题部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr height=40>
			<td colspan="2" height="40" align=top align=center style="font-family: 宋体; font-size: 14pt;">
				<B>机动车辆保险人员伤亡费用清单<B>
			</td>
		</tr>
		<tr height=25>
			<td align=left style="font-family: 宋体; font-size: 10pt;">
				立案编号：<%=strClaimNo%>
			</td>
			<td align=left style="font-family: 宋体; font-size: 10pt;">金额单位：元（人民币）</td>
			<td align=left style="font-family: 宋体; font-size: 10pt;">伤亡人员编号：</td>
			<td>&nbsp;</td>
		</tr>
	</table>
	<!-- 主体部分 -->
	<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td height=25 rowspan=7 colspan="1" width="2%">
				基<br>本<br>情<br>况<br>
			</td>
			<td height=25 colspan="1" width="10%">姓名</td>
			<td height=25 colspan="1" width="12%"></td>
			<td height=25 colspan="1" width="8%">性别</td>
			<td height=25 colspan="1" width="8%"></td>
			<td height=25 colspan="1" width="10%">年龄</td>
			<td height=25 colspan="1" width="10%">事故责任</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1" width="10%">赔偿比例（%）</td>
			<td height=25 colspan="1" width="10%"></td>
		</tr>
		<tr>
			<td height=25 colspan="1">身份證字號</td>
			<td height=25 colspan="3"></td>
			<td height=25 colspan="1">伤势程度</td>
			<td height=25 colspan="2"></td>
			<td height=25 colspan="1">出险险种</td>
			<td height=25 colspan="1"></td>
		</tr>
		<tr>
			<td height=25 colspan="1">就珍医院</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">诊断结果</td>
			<td height=25 colspan="2"></td>
			<td height=25 colspan="1">伤残鉴定机构</td>
			<td height=25 colspan="3"></td>
		</tr>
		<tr>
			<td height=25 colspan="2">城镇居民年人均可支配收入</td>
			<td height=25 colspan="2"></td>
			<td height=25 colspan="2">职工年平均</td>
			<td height=25 colspan="3"></td>
		</tr>
		<tr>
			<td height=25 colspan="2">城镇居民年人均消费性支出</td>
			<td height=25 colspan="2"></td>
			<td height=25 colspan="2">农村居民年人均纯收入</td>
			<td height=25 colspan="3"></td>
		</tr>
		<tr>
			<td height=25 colspan="2">住院伙食补助费日标准金额</td>
			<td height=25 colspan="2"></td>
			<td height=25 colspan="2">农村居民年人均消费支出</td>
			<td height=25 colspan="3"></td>
		</tr>
		<tr>
			<td height=25 colspan="2">交通费日标准</td>
			<td height=25 colspan="2"></td>
			<td height=25 colspan="2">住宿费日标准</td>
			<td height=25 colspan="3"></td>
		</tr>
		<tr>
			<td height=25 colspan="10"></td>
		</tr>
		<tr>
			<td height=25 colspan="1">
				序<br>号
			</td>
			<td height=25 colspan="9" align=center>赔偿项目及公式</td>
		</tr>
		<tr>
			<td height=25 colspan="1" rowspan=3>1</td>
			<td height=25 colspan="1" rowspan=3>医疗费</td>
			<td height=25 colspan="1">医药费</td>
			<td height=25 colspan="2"></td>
			<td height=25 colspan="2">二次医疗费</td>
			<td height=25 colspan="3"></td>
		</tr>
		<tr>
			<td height=25 colspan="1">治疗费</td>
			<td height=25 colspan="2"></td>
			<td height=25 colspan="2">其它</td>
			<td height=25 colspan="3"></td>
		</tr>
		<tr>
			<td height=25 colspan="8">赔偿计算公式:</td>
		</tr>
		<tr>
			<td height=25 colspan="1" rowspan=3>2</td>
			<td height=25 colspan="1" rowspan=3>
				伤者<br>误工费
			</td>
			<td height=25 colspan="1">月工资收入</td>
			<td height=25 colspan="2"></td>
			<td height=25 colspan="2">最近三年平均年收入</td>
			<td height=25 colspan="3"></td>
		</tr>
		<tr>
			<td height=25 colspan="1">住院天数</td>
			<td height=25 colspan="2"></td>
			<td height=25 colspan="2">继续治疗天数</td>
			<td height=25 colspan="3"></td>
		</tr>
		<tr>
			<td height=25 colspan="8">赔偿计算公式:</td>
		</tr>
		<tr>
			<td height=25 colspan="1" rowspan=8>3</td>
			<td height=25 colspan="1" rowspan=8>护理费</td>
			<td height=25 colspan="1">护理人姓名</td>
			<td height=25 colspan="2"></td>
			<td height=25 colspan="2">月工资收入</td>
			<td height=25 colspan="3"></td>
		</tr>
		<tr>
			<td height=25 colspan="1">护理天数</td>
			<td height=25 colspan="2"></td>
			<td height=25 colspan="2">最近三年平均年收入</td>
			<td height=25 colspan="3"></td>
		</tr>
		<tr>
			<td height=25 colspan="8">赔偿计算公式:</td>
		</tr>
		<tr>
			<td height=25 colspan="1">护理人姓名</td>
			<td height=25 colspan="2"></td>
			<td height=25 colspan="2">月工资收入</td>
			<td height=25 colspan="3"></td>
		</tr>
		<tr>
			<td height=25 colspan="1">护理天数</td>
			<td height=25 colspan="2"></td>
			<td height=25 colspan="2">最近三年平均年收入</td>
			<td height=25 colspan="3"></td>
		</tr>
		<tr>
			<td height=25 colspan="8">赔偿计算公式:</td>
		</tr>
		<tr>
			<td height=25 colspan="1">今後护理年限</td>
			<td height=25 colspan="2"></td>
			<td height=25 colspan="2">今後护理年费用标准</td>
			<td height=25 colspan="3"></td>
		</tr>
		<tr>
			<td height=25 colspan="5">赔偿计算公式:</td>
			<td height=25 colspan="1">护理费小计</td>
			<td height=25 colspan="2"></td>
		</tr>
		<tr>
			<td height=25 colspan="1">4</td>
			<td height=25 colspan="1">残疾补偿费</td>
			<td height=25 colspan="1">伤残等级</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="6">赔偿计算公式：</td>
		</tr>
		<tr>
			<td height=25 colspan="1" rowspan=2>5</td>
			<td height=25 colspan="1" rowspan=2>残疾用具费</td>
			<td height=25 colspan="2">伤残用具单价：</td>
			<td height=25 colspan="1">数量</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">残疾修理单价</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">修理次数</td>
			<td height=25 colspan="1"></td>
		</tr>
		<tr>
			<td height=25 colspan="8">赔偿计算公式:</td>
		</tr>
		<tr>
			<td height=25 colspan="1">6</td>
			<td height=25 colspan="1">死亡补偿费</td>
			<td height=25 colspan="8">赔偿计算公式:</td>
		</tr>
		<tr>
			<td height=25 colspan="1">7</td>
			<td height=25 colspan="1">丧葬费</td>
			<td height=25 colspan="8">赔偿计算公式:</td>
		</tr>
		<tr>
			<td height=25 colspan="1" rowspan=7>8</td>
			<td height=25 colspan="1" rowspan=7>抚养费</td>
			<td height=25 colspan="1">被抚养人姓名</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">年龄</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">被抚养年限</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">总抚养人数</td>
			<td height=25 colspan="1"></td>
		</tr>
		<tr>
			<td height=25 colspan="8">赔偿计算公式:</td>
		</tr>
		<tr>
			<td height=25 colspan="1">被抚养人姓名</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">年龄</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">被抚养年限</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">总抚养人数</td>
			<td height=25 colspan="1"></td>
		</tr>
		<tr>
			<td height=25 colspan="8">赔偿计算公式:</td>
		</tr>
		<tr>
			<td height=25 colspan="1">被抚养人姓名</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">年龄</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">被抚养年限</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">总抚养人数</td>
			<td height=25 colspan="1"></td>
		</tr>
		<tr>
			<td height=25 colspan="8">赔偿计算公式:</td>
		</tr>
		<tr>
			<td height=25 colspan="1">抚养费小计</td>
			<td height=25 colspan="7">抚养费小计</td>
		</tr>
		<tr>
			<td height=25 colspan="1">9</td>
			<td height=25 colspan="1">伙食补助费</td>
			<td height=25 colspan="8">赔偿计算公式:</td>
		</tr>
		<tr>
			<td height=25 colspan="1" rowspan=2>10</td>
			<td height=25 colspan="1" rowspan=2>住宿费</td>
			<td height=25 colspan="1">住宿天数</td>
			<td height=25 colspan="3"></td>
			<td height=25 colspan="2">住宿费实际赔偿金额</td>
			<td height=25 colspan="2"></td>
		</tr>
		<tr>
			<td height=25 colspan="8">住宿费赔偿标准公式:</td>
		</tr>
		<tr>
			<td height=25 colspan="1" rowspan=2>11</td>
			<td height=25 colspan="1" rowspan=2>交通费</td>
			<td height=25 colspan="1">累计天数</td>
			<td height=25 colspan="3"></td>
			<td height=25 colspan="2">交通费实际赔偿金额</td>
			<td height=25 colspan="2"></td>
		</tr>
		<tr>
			<td height=25 colspan="8">交通费赔偿标准公式:</td>
		</tr>
		<tr>
			<td height=25 colspan="1">12</td>
			<td height=25 colspan="1">营养费</td>
			<td height=25 colspan="8"></td>
		</tr>
		<tr>
			<td height=25 colspan="1">13</td>
			<td height=25 colspan="1">精神损害费</td>
			<td height=25 colspan="8"></td>
		</tr>
		<tr>
			<td height=25 colspan="1" rowspan=4>14</td>
			<td height=25 colspan="1" rowspan=4>
				参加丧事处<br>理人员费用
			</td>
			<td height=25 colspan="1">住宿费</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">交通费</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">丧事处理天数</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">人数</td>
			<td height=25 colspan="1"></td>
		</tr>
		<tr>
			<td height=25 colspan="1">丧事处理人姓名</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">月工资</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">误工费公式</td>
			<td height=25 colspan="3"></td>
		</tr>
		<tr>
			<td height=25 colspan="1">丧事处理人姓名</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">月工资</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">误工费公式</td>
			<td height=25 colspan="3"></td>
		</tr>
		<tr>
			<td height=25 colspan="1">丧事处理人姓名</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">月工资</td>
			<td height=25 colspan="1"></td>
			<td height=25 colspan="1">误工费公式</td>
			<td height=25 colspan="3"></td>
		</tr>
		<tr>
			<td height=25 colspan="1">15</td>
			<td height=25 colspan="1">其它费用</td>
			<td height=25 colspan="8"></td>
		</tr>
		<tr>
			<td height=25 colspan="1">16</td>
			<td height=25 colspan="2">人员伤亡各项费用合计</td>
			<td height=25 colspan="7"></td>
		</tr>
	</table>
	<%-- include打印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
