<script>
	function ownerShip_change(field) {
		var $ownerShip = $(field);
		var $payFeeTD = $ownerShip.parents("td[name='payFeeTD']");
		if ($ownerShip.val() == "B") { //汇款
			$payFeeTD.find("span[name='spanCutBack']").hide(); //隐藏禁背
			$payFeeTD.find("tr[name='bankInfo']").show(); //开放银行帳户录入
		} else {
			$payFeeTD.find("tr[name='bankInfo']").hide(); //关闭银行帳户录入
		}
		if ($ownerShip.val() == "Q") { //支票
			$payFeeTD.find("span[name='spanCutBack']").show(); //显示禁背
			$payFeeTD.find("tr[name='bankInfo']").hide(); //隐藏银行帳户录入
		} else {
			$payFeeTD.find("span[name='spanCutBack']").hide(); //隐藏禁背
		}
	}
</script>