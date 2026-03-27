function boundCheckBox(controlField, checkBoxField) {
	var count = 0;
	try {
		count = checkBoxField.length;
	} catch (E) {}
	if (isNaN(count)) {
		checkBoxField.checked = controlField.checked;
	} else {
		for (var i = 0; i < count; i++) {
			checkBoxField[i].checked = controlField.checked;
		}
	}
}