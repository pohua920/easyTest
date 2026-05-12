//打開鏈接窗口

function selectPublicCheckbox(selectClassCode) {
	url = '/claim/archive/archiveQuery.do?' + 'editType=' + selectClassCode
	window.open(url, '', 'width=350,height=600,top=30,left=300,scrollbars=1');
}

//關閉窗口

function closeWin() {
	window.close();
}
//選中全部

function checkAll() {
	var isChecked = (fm.classCodeAll.checked == true);
	var elements = document.fm.elements;
	var counter = elements.length;
	for (var i = 0; i < counter; i++) {
		var element = elements[i];
		if (element.type == "checkbox") {
			element.checked = isChecked;
		}
	}
}
//有一個沒選中則全選為沒有選中狀態 ,全選中下面的則上面的全選框也選中

function checkPart(obj) {
	var elements = document.fm.classCode;
	var counter = elements.length;
	if (obj.checked == false) {
		fm.classCodeAll.checked = false;
	} else {
		for (var i = 0; i < counter; i++) {
			var element = elements[i];
			if (element.type == "checkbox") {
				if (element.checked == true) {
					if ((i + 1) == counter) {
						fm.classCodeAll.checked = true;
					}
				} else {
					break;
				}
			}
		}
	}
}

//確定提交

function selectChecked() {
	var elements = document.fm.classCode;
	var classCodes = "";
	var counter = elements.length;
	for (var i = 0; i < counter; i++) {
		var element = elements[i];
		if (element.type == "checkbox") {
			if (element.checked == true) {
				classCodes += element.value;
				classCodes += ",";
			}
		}
	}
	classCodes = classCodes.substring(0, classCodes.length - 1);
	window.opener.fm.strClassCode.value = classCodes;
	closeWin(); //選完之後關閉窗口
}