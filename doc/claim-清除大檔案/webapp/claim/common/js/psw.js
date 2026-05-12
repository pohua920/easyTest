var password = {
	checkPassword: function (oldPasswd, password, confirmPassword) {
		var errorMsg = "";
		if (password == '' || confirmPassword == '') {
			errorMsg = i18n.js.fillPassword; //请填写密码
		}
		if (password != confirmPassword) {
			errorMsg = i18n.js.passwordDifferent; //您输入的两次密码不一致\n
		}
		if (password.length < 6) {
			errorMsg = errorMsg + i18n.js.passwordGreater; //密码长度必须大於6位\n
		}
		if (password == oldPasswd) {
			errorMsg = errorMsg + i18n.js.cannotOriginalPassword; //您的新密码不能与原始密码一样\n
		}

		var pattern1 = /[a-z]+/;
		var pattern2 = /[A-Z]+/;
		var pattern3 = /[0-9]+/;
		//        var pattern4 = /[!@#$%^&*()\-\_\+=?{}`~\\,<>.\[;:'"\/|\]]+/;

		var test1 = pattern1.test(password);
		var test2 = pattern2.test(password);
		var test3 = pattern3.test(password);
		//        var test4 = pattern4.test(password);
		if (test1 == false || test2 == false || test3 == false) {
			errorMsg = errorMsg + i18n.js.passwordContain; //密码必须包含大写字母,小写字母,数字\n
		}

		if (errorMsg != "") {
			alert(errorMsg);
			return false;
		}
	}
}