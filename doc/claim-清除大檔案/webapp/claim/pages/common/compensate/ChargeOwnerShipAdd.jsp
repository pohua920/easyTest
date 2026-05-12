<%@ include file="/common/taglibs.jsp"%>
<script>
	function ownerShip_change(id, field) {
		var i = getElementOrder(field) - 1;
		if (id == "B") {
			document.getElementsByName("cutBack1")[i].style.display = "none";
			document.getElementsByName("cutBack2")[i].style.display = "none";
			document.getElementsByName("prpLchargebank1")[i].style.display = "";
			document.getElementsByName("prpLchargebank2")[i].style.display = "";
			document.getElementsByName("prpLchargeButtonAddAcc")[i].style.display = "";
		} else if (id == "Q") {
			document.getElementsByName("cutBack1")[i].style.display = "";
			document.getElementsByName("cutBack2")[i].style.display = "";
			document.getElementsByName("prpLchargebank1")[i].style.display = "none";
			document.getElementsByName("prpLchargebank2")[i].style.display = "none";
			document.getElementsByName("prpLchargeButtonAddAcc")[i].style.display = "none";
		}

	}
</script>
<tr>
	<td colspan="12">
		<table class="common" style="width: 100%">
			<tr>
				<td class="input" style="width: 12%">费用支付方式：</td>
				<td class="input" style="width: 18%">
					<select name="prpLchargeOwnerShip" style="width: 50%" onchange="ownerShip_change(this.options[this.selectedIndex].value,this)">
						<option value="B" selected="selected">匯款</option>
						<option value="Q">支票</option>
					</select>
				</td>
				<td class="input" style="width: 8%"></td>
				<td class="input" style="width: 18%"></td>
				<td class="input" style="width: 12%"></td>
				<td class="input" style="width: 18%"></td>
			</tr>
		</table>
	</td>
</tr>