<%--
****************************************************************************
* DESC       ：4.3.10  查勘/代查勘扩展页面
* AUTHOR     ： 中科软
* CREATEDATE ： 2004-06-03
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<script type="text/javascript">
	function checkExt() {
		var CheckExt19 = document.getElementsByName("CheckExt19");
		var CheckExt191 = document.getElementsByName("CheckExt191");
		if (CheckExt19[1].checked == true) {
			CheckExt191[0].checked = false;
			CheckExt191[1].checked = false;
		}
	}
	function SetAvailable20(flag) {
		if (flag == "true") {
			fm.CheckExtText201.readOnly = false;
			fm.CheckExtText202.readOnly = false;
		} else if (flag == "false") {
			fm.CheckExtText201.readOnly = true;
			fm.CheckExtText201.value = "";
			fm.CheckExtText202.readOnly = true;
			fm.CheckExtText202.value = "";
		}
	}
	function SetAvailable22(flag) {
		if (flag == "true") {
			fm.CheckExtText221.readOnly = false;
			fm.CheckExtText222.readOnly = false;
		} else if (flag == "false") {
			fm.CheckExtText221.readOnly = true;
			fm.CheckExtText221.value = "";
			fm.CheckExtText222.readOnly = true;
			fm.CheckExtText222.value = "";
		}
	}
	function checknum(field) {
		var re = /^[0-9]*$/;//匹配正整数
		if (field.value != "" && !re.test(field.value)) {//
			alert("請輸入正確數字");
			return false;
		}
	}
</script>
<table class="common" align="center" width="100%">
	<tr>
		<td class="subformtitle" style="text-align: left;">
			<table class="common" align="center" id="CheckLossText">
				<tbody>
					<%
						//reason:查勘扩展信息用颜色间隔区分，增加一个不确定选项,不能用-1,因为判断如果为-1，js不写值
					%>
					<tr class=listeven>
						<td align="left" colspan=4>1、出險原因： <input type="radio" name="CheckExt01" value="0">碰撞 <input type="radio" name="CheckExt01" value="1">傾覆 <input type="radio"
							name="CheckExt01" value="2">火災 <input type="radio" name="CheckExt01" value="3">閃電、雷擊 <input type="radio" name="CheckExt01" value="4">爆炸 <input type="radio"
							name="CheckExt01" value="5">拋擲物或墜落物 <input type="radio" name="CheckExt01" value="6">第三者非善意行為 <input type="radio" name="CheckExt01" value="7">颱風 <input type="radio"
							name="CheckExt01" value="8">地震 <input type="radio" name="CheckExt01" value="9">海嘯 <input type="radio" name="CheckExt01" value="10">冰雹 <input type="radio"
							name="CheckExt01" value="11">洪水或因雨積水 <input type="radio" name="CheckExt01" value="12">竊盜 <input type="radio" name="CheckExt01" value="13">其它
						</td>
					</tr>
					<tr class="listodd">
						<td align="left" colspan=4>2、事故原因： <input type="radio" name="CheckExt02" value="0">超速行駛 <input type="radio" name="CheckExt02" value="1">未注意車前狀況 <input type="radio"
							name="CheckExt02" value="2">轉彎（變換車道）不當 <input type="radio" name="CheckExt02" value="3">未依號誌、標誌、標線指示行駛 <input type="radio" name="CheckExt02" value="4">酒後（吸毒、嗑藥）駕駛 <input
							type="radio" name="CheckExt02" value="5">未保安全間距 <input type="radio" name="CheckExt02" value="6">未保安全間隔 <input type="radio" name="CheckExt02" value="7">違規停車 <input
							type="radio" name="CheckExt02" value="8">逆向行駛 <input type="radio" name="CheckExt02" value="9">倒車不慎 <input type="radio" name="CheckExt02" value="10">開啟車門不當 <input
							type="radio" name="CheckExt02" value="11">無照駕駛 <input type="radio" name="CheckExt02" value="12">機械故障 <input type="radio" name="CheckExt02" value="13">疲勞駕駛 <input
							type="radio" name="CheckExt02" value="14">駕駛疏忽 <input type="radio" name="CheckExt02" value="15">其他
						</td>
					</tr>
					<tr class="listodd">
						<td align="left" colspan=4>
							<%-- 是否在本公司投保了強制險 --%>3、<s:text name="certainLoss.thirdCarLoss.info3" />: <input type="radio" name="CheckExt03" value="0"> <%-- 是 --%> <s:text name="certainLoss.thirdCarLoss.yes" /> <input
							type="radio" name="CheckExt03" value="1"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" />
						</td>
					</tr>
					<tr class=listodd>
						<td align="left" colspan=4>
							<%-- 事故所涉及的商業保險 --%>4、<s:text name="certainLoss.thirdCarLoss.info4" />： <input type="checkbox" name="CheckExt04" value="0"> <%-- 車損險 --%> <s:text
								name="certainLoss.thirdCarLoss.info.cheSunxian" /> <input type="checkbox" name="CheckExt04" value="1"> <%-- 三責險 --%> <s:text name="certainLoss.thirdCarLoss.info.threeInsurance" /> <input
							type="checkbox" name="CheckExt04" value="2"> <%-- 竊盜險 --%> <s:text name="certainLoss.thirdCarLoss.info.theftInsurance" /> <input type="checkbox" name="CheckExt04" value="3"> <%-- 駕駛人(僱主責任)傷害險 --%>
							<s:text name="certainLoss.thirdCarLoss.info.driverInjuryInsurance" /> <input type="checkbox" name="CheckExt04" value="4"> <%-- 乘客(旅客)責任險 --%> <s:text
								name="certainLoss.thirdCarLoss.info.passengerLiabilityInsurance" /> <input type="checkbox" name="CheckExt04" value="5"> <%-- 貨物運送人責任險 --%> <s:text
								name="certainLoss.thirdCarLoss.info.goodsInsurance" /> <input type="checkbox" name="CheckExt04" value="6"> <%-- 其它（ ）--%> <s:text name="certainLoss.thirdCarLoss.info.other" />
						</td>
					</tr>
					<tr class="listodd">
						<td align="left" colspan=4>
							<%-- 保險車輛的牌照號碼、引擎號碼、車身號碼與保險單上所載明的是否相符 --%>5、<s:text name="certainLoss.thirdCarLoss.info5" />: <input type="radio" name="CheckExt05" value="0"> <%-- 是 --%> <s:text
								name="certainLoss.thirdCarLoss.yes" /> <input type="radio" name="CheckExt05" value="1"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" />
						</td>
					</tr>
					<tr class=listeven>
						<td align="left" colspan=4>
							<%-- 出險時間是否在保險有效期限內 --%>6、<s:text name="certainLoss.thirdCarLoss.info6" />: <input type="radio" name="CheckExt06" value="0"> <%-- 是 --%> <s:text name="certainLoss.thirdCarLoss.yes" />
							<input type="radio" name="CheckExt06" value="1"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" />
						</td>
					</tr>
					<tr class="listodd">
						<td align="left" colspan=4>
							<%-- 是否屬於追償案件 --%>7、<s:text name="certainLoss.thirdCarLoss.info7" />: <input type="radio" name="CheckExt07" value="0"> <%-- 是 --%> <s:text name="certainLoss.thirdCarLoss.yes" /> <input
							type="radio" name="CheckExt07" value="1"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" />
						</td>
					</tr>
					<tr class=listeven>
						<td align="left" colspan=4>
							<%-- 出險時間接近保險起訖期的，有無相應時間證明 --%>8、<s:text name="certainLoss.thirdCarLoss.info8" />: <input type="radio" name="CheckExt08" value="0"> <%-- 有 --%> <s:text
								name="certainLoss.thirdCarLoss.info.have" /> <input type="radio" name="CheckExt08" value="1"> <%-- 无 --%> <s:text name="certainLoss.thirdCarLoss.info.nohave" />
						</td>
					</tr>
					<tr class="listodd">
						<td align="left" colspan=4>
							<%-- 出險地點 --%>9、<s:text name="certainLoss.thirdCarLoss.info9" />: (1)<%--分類 --%> <s:text name="certainLoss.thirdCarLoss.info.classification" />: <input type="radio" name="CheckExt09"
							value="0"> <%-- 高速或快速公路 --%> <s:text name="certainLoss.thirdCarLoss.info.highSpeed" /> <input type="radio" name="CheckExt09" value="1"> <%-- 普通公路 --%> <s:text
								name="certainLoss.thirdCarLoss.info.ordinary" /> <input type="radio" name="CheckExt09" value="2"> <%-- 市區或郊區道路 --%> <s:text name="certainLoss.thirdCarLoss.info.urbanDistrict" /> <input
							type="radio" name="CheckExt09" value="3"> <%-- 鄉村便道或產業道路 --%> <s:text name="certainLoss.thirdCarLoss.info.country" /> <input type="radio" name="CheckExt09" value="4"> <%-- 停車場或私人土地 --%>
							<s:text name="certainLoss.thirdCarLoss.info.parkingLot" /> (2)<%--與備案人所報是否一致 --%> <s:text name="certainLoss.thirdCarLoss.info.consistent" />: <input type="radio" name="CheckExt091" value="0">
							<%-- 是 --%> <s:text name="certainLoss.thirdCarLoss.yes" /> <input type="radio" name="CheckExt091" value="1"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" />
						</td>
					</tr>
					<tr class=listeven>
						<td align="left" colspan=4>
							<%-- 實際使用性質與保險單上所載明的是否一致 --%>10、<s:text name="certainLoss.thirdCarLoss.info10" />: <input type="radio" name="CheckExt10" value="0"> <%-- 是 --%> <s:text
								name="certainLoss.thirdCarLoss.yes" /> <input type="radio" name="CheckExt10" value="1"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" />
						</td>
					</tr>
					<tr class="listodd">
						<td align="left" colspan=4>
							<%-- 保險車輛駕駛人員情況與備案人所述是否一致 --%>11、<s:text name="certainLoss.thirdCarLoss.info11" />: <input type="radio" name="CheckExt11" value="0"> <%-- 是 --%> <s:text
								name="certainLoss.thirdCarLoss.yes" /> <input type="radio" name="CheckExt11" value="1"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" />
					</tr>
					<tr class=listeven>
						<td align="left" colspan=4>
							<%-- 保險車輛駕駛人員的駕照是否有效 --%>12、<s:text name="certainLoss.thirdCarLoss.info12" />: <input type="radio" name="CheckExt12" value="0"> <%-- 是 --%> <s:text name="certainLoss.thirdCarLoss.yes" />
							<input type="radio" name="CheckExt12" value="1"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" />
						</td>
					</tr>
					<tr class="listodd">
						<td align="left" colspan=4>
							<%-- 保險車輛駕駛人員持照條件及駕照種類與實際駕駛車輛是否相符 --%>13、<s:text name="certainLoss.thirdCarLoss.info13" />: <input type="radio" name="CheckExt13" value="0"> <%-- 是 --%> <s:text
								name="certainLoss.thirdCarLoss.yes" /> <input type="radio" name="CheckExt13" value="1"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" />
						</td>
					</tr>
					<tr class=listeven>
						<td align="left" colspan=4>
							<%-- 保險車輛駕駛人員是否為被保險人允許的駕駛人員 --%>14、<s:text name="certainLoss.thirdCarLoss.info14" />: <input type="radio" name="CheckExt14" value="0"> <%-- 是 --%> <s:text
								name="certainLoss.thirdCarLoss.yes" /> <input type="radio" name="CheckExt14" value="1"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" />
						</td>
					</tr>
					<tr class="listodd">
						<td align="left" colspan=4>
							<%-- 保險車輛駕駛人員是否為車體險約定的被保險人範圍 --%>15、<s:text name="certainLoss.thirdCarLoss.info15" />: <input type="radio" name="CheckExt15" value="0"> <%-- 是 --%> <s:text
								name="certainLoss.thirdCarLoss.yes" /> <input type="radio" name="CheckExt15" value="1"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" />
						</td>
					</tr>
					<tr class=listeven>
						<td align="left" colspan=4>
							<%-- 保險車輛駕駛人員是否為酒後駕車 --%>16、<s:text name="certainLoss.thirdCarLoss.info16" />: <input type="radio" name="CheckExt16" value="0"> <%-- 是 --%> <s:text name="certainLoss.thirdCarLoss.yes" />
							<input type="radio" name="CheckExt16" value="1"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" />
						</td>
					</tr>
					<tr class="listodd">
						<td align="left" colspan=4>
							<%-- 事故車輛損失痕跡與事故現場痕跡是否吻合 --%>17、<s:text name="certainLoss.thirdCarLoss.info17" />: <input type="radio" name="CheckExt17" value="0"> <%-- 是 --%> <s:text
								name="certainLoss.thirdCarLoss.yes" /> <input type="radio" name="CheckExt17" value="1"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" />
						</td>
					</tr>
					<tr class=listeven>
						<td align="left" colspan=4>
							<%-- 保險車輛安全配置情況 --%>18、<s:text name="certainLoss.thirdCarLoss.info18" />: <input type="checkbox" name="CheckExt18" value="0"> <%-- 安全氣囊 --%> <s:text
								name="certainLoss.thirdCarLoss.info.airbag" /> <input type="checkbox" name="CheckExt18" value="1"> <%-- ABS --%> <s:text name="certainLoss.thirdCarLoss.info.ABS" /> <input
							type="checkbox" name="CheckExt18" value="2"> <%-- 倒車雷達 --%> <s:text name="certainLoss.thirdCarLoss.info.reversingRadar" /> <input type="checkbox" name="CheckExt18" value="3"> <%-- 衛星定位 --%>
							<s:text name="certainLoss.thirdCarLoss.info.satellitePositioning" /> <input type="checkbox" name="CheckExt18" value="4"> <%-- 其它防盜裝置（ ） --%> <s:text
								name="certainLoss.thirdCarLoss.info.antitheftDevice" />
						</td>
					</tr>
					<tr class="listodd">
						<td align="left" colspan=4>
							<%-- 對造車輛是否在其他保險公司投保任意險 --%>19、<s:text name="certainLoss.thirdCarLoss.info19" /> <input type="radio" name="CheckExt19" value="0"> <%-- 是 --%> <s:text
								name="certainLoss.thirdCarLoss.yes" /> (<%--是否已向其承保公司辦理理賠申請 --%> <s:text name="certainLoss.thirdCarLoss.info.application" /> <input type="radio" name="CheckExt191" value="0"
							onclick="checkExt()"> <%-- 是 --%> <s:text name="certainLoss.thirdCarLoss.yes" /> <input type="radio" name="CheckExt191" value="1" onclick="checkExt()"> <%-- 否 --%> <s:text
								name="certainLoss.thirdCarLoss.no" />) <input type="radio" name="CheckExt19" value="1" onclick="checkExt()"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" />
						</td>
					</tr>
					<tr class=listeven>
						<td align="left" colspan=4>
							<%-- 事故是否涉及財車人身傷亡--%>20、<s:text name="certainLoss.thirdCarLoss.info20" />: <input type="radio" name="CheckExt20" onclick="SetAvailable20('true')" value="0"> <%-- 是 --%> <s:text
								name="certainLoss.thirdCarLoss.yes" /> <%--（傷 人，亡 人） --%>（傷 <input style="width: 10%;" class="readonly" id="underline" type="text" name="CheckExtText201" onblur="checknum(this)" readonly="readonly" value="">人，亡
							<input type="text" style="width: 10%;" class="readonly" id="underline" name="CheckExtText202" onblur="checknum(this)" readonly="readonly" value="">人） <input type="radio" name="CheckExt20"
							onclick="SetAvailable20('false')" value="1"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" />
						</td>
					</tr>
					<tr class=listeven>
						<td align="left" colspan=4>
							<%-- 事故是否涉及財車財產損失 --%>21、<s:text name="certainLoss.thirdCarLoss.info21" />： <input type="radio" name="CheckExt21" value="0"> <%-- 是 --%> <s:text name="certainLoss.thirdCarLoss.yes" />
							<input type="radio" name="CheckExt21" value="1"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" />
						</td>
					</tr>
					<tr class=listeven>
						<td align="left" colspan=4>
							<%-- 事故是否涉及本車上人員傷亡 --%>22、<s:text name="certainLoss.thirdCarLoss.info22" />： <input type="radio" name="CheckExt22" onclick="SetAvailable22('true')" value="0"> <%-- 是 --%> <s:text
								name="certainLoss.thirdCarLoss.yes" /> <%--（傷 人，亡 人） --%>（傷 <input style="width: 10%;" class="readonly" id="underline" type="text" name="CheckExtText221" onblur="checknum(this)" readonly="readonly" value="">人，亡
							<input style="width: 10%;" class="readonly" id="underline" type="text" name="CheckExtText222" onblur="checknum(this)" readonly="readonly" value="">人） <input type="radio" name="CheckExt22"
							onclick="SetAvailable22('false')" value="1"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" />
						</td>
					</tr>
					<tr class=listeven>
						<td align="left" colspan=4>
							<%-- 確定或預計責任劃分 --%>23、<s:text name="certainLoss.thirdCarLoss.info23" />：<input type="text" class="readonly" id="underline" name="CheckExtText23" value="">
						</td>
					</tr>
					<tr class=listeven>
						<td align="left" colspan=4>
							<%-- 保險車輛損失程度 --%>24、<s:text name="certainLoss.thirdCarLoss.info24" />： <input type="radio" name="CheckExt24" value="0"> <%-- 全部損失 --%> <s:text name="certainLoss.thirdCarLoss.info.all" />
							<input type="radio" name="CheckExt24" value="1"> <%-- 部分損失 --%> <s:text name="certainLoss.thirdCarLoss.info.part" />
						</td>
					</tr>
					<tr class=listeven>
						<td align="left" colspan=4>
							<%-- 是否屬於強制險的保險責任 --%>25、<s:text name="certainLoss.thirdCarLoss.info25" />： <input type="radio" name="CheckExt25" value="0"> <%-- 是 --%> <s:text name="certainLoss.thirdCarLoss.yes" />
							<input type="radio" name="CheckExt25" value="1"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" /> <input type="radio" name="CheckExt25" value="2"> <%-- 待确定 --%> <s:text
								name="certainLoss.thirdCarLoss.info.determined" /> <%--（ 原因是 ） --%> <s:text name="certainLoss.thirdCarLoss.info.reason" /> <input type="text" sytle="width: 10%;" class="readonly" id="underline" name="CheckExtText25" value="">
						</td>
					</tr>
					<tr class=listeven>
						<td align="left" colspan=4>
							<%-- 是否屬於任意險的保險責任 --%>26、<s:text name="certainLoss.thirdCarLoss.info26" />： <input type="radio" name="CheckExt26" value="0"> <%-- 是 --%> <s:text name="certainLoss.thirdCarLoss.yes" />
							<input type="radio" name="CheckExt26" value="1"> <%-- 否 --%> <s:text name="certainLoss.thirdCarLoss.no" /> <input type="radio" name="CheckExt26" value="2"> <%-- 待确定 --%> <s:text
								name="certainLoss.thirdCarLoss.info.determined" /> <%--（ 原因是 ） --%> <s:text name="certainLoss.thirdCarLoss.info.reason" /> <input type="text" class="readonly" id="underline" name="CheckExtText26" value="">
						</td>
					</tr>
					<tr class=listeven>
						<td align="left" colspan=4>
							<%-- 其它需要說明的內容 --%>27、<s:text name="certainLoss.thirdCarLoss.info27" />： <%--（ 原因是 ） --%> <input type="text" class="readonly" id="underline" name="CheckExtText27" value="">
						</td>
					</tr>
					<%--扩展说明部分--%>
					<input name="CheckExt01Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info1"/>">
					<input name="CheckExt02Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info2"/>">
					<input name="CheckExt03Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info3"/>">
					<input name="CheckExt04Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info4"/>">
					<input name="CheckExt05Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info5"/>">
					<input name="CheckExt06Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info6"/>">
					<input name="CheckExt07Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info7"/>">
					<input name="CheckExt08Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info8"/>">
					<input name="CheckExt09Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info9"/>">
					<input name="CheckExt10Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info10"/>">
					<input name="CheckExt11Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info11"/>">
					<input name="CheckExt12Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info12"/>">
					<input name="CheckExt13Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info13"/>">
					<input name="CheckExt14Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info14"/>">
					<input name="CheckExt15Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info15"/>">
					<input name="CheckExt16Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info16"/>">
					<input name="CheckExt17Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info17"/>">
					<input name="CheckExt18Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info18"/>">
					<input name="CheckExt19Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info19"/>">
					<input name="CheckExt20Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info20"/>">
					<input name="CheckExt21Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info21"/>">
					<input name="CheckExt22Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info22"/>">
					<input name="CheckExt23Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info23"/>">
					<input name="CheckExt24Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info24"/>">
					<input name="CheckExt25Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info25"/>">
					<input name="CheckExt26Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info26"/>">
					<input name="CheckExt27Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info27"/>">
					<input name="CheckExt091Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info.consistent"/>">
					<input name="CheckExt191Dis" type="hidden" value="<s:text name="certainLoss.thirdCarLoss.info.application"/>">
					<input name="CheckExt01Serial" type="hidden" value="1">
					<input name="CheckExt02Serial" type="hidden" value="2">
					<input name="CheckExt03Serial" type="hidden" value="3">
					<input name="CheckExt04Serial" type="hidden" value="4">
					<input name="CheckExt05Serial" type="hidden" value="5">
					<input name="CheckExt06Serial" type="hidden" value="6">
					<input name="CheckExt07Serial" type="hidden" value="7">
					<input name="CheckExt08Serial" type="hidden" value="8">
					<input name="CheckExt09Serial" type="hidden" value="9">
					<input name="CheckExt10Serial" type="hidden" value="10">
					<input name="CheckExt11Serial" type="hidden" value="11">
					<input name="CheckExt12Serial" type="hidden" value="12">
					<input name="CheckExt13Serial" type="hidden" value="13">
					<input name="CheckExt14Serial" type="hidden" value="14">
					<input name="CheckExt15Serial" type="hidden" value="15">
					<input name="CheckExt16Serial" type="hidden" value="16">
					<input name="CheckExt17Serial" type="hidden" value="17">
					<input name="CheckExt18Serial" type="hidden" value="18">
					<input name="CheckExt19Serial" type="hidden" value="19">
					<input name="CheckExt20Serial" type="hidden" value="20">
					<input name="CheckExt21Serial" type="hidden" value="21">
					<input name="CheckExt22Serial" type="hidden" value="22">
					<input name="CheckExt23Serial" type="hidden" value="23">
					<input name="CheckExt24Serial" type="hidden" value="24">
					<input name="CheckExt25Serial" type="hidden" value="25">
					<input name="CheckExt26Serial" type="hidden" value="26">
					<input name="CheckExt27Serial" type="hidden" value="27">
					<input name="CheckExt091Serial" type="hidden" value="091">
					<input name="CheckExt191Serial" type="hidden" value="191">
				</tbody>
			</table>
		</td>
	</tr>
</table>
