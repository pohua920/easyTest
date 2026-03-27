//
// global variables
// 
var tbody = null;
var theadrow = null;
var colCount = null;
var reverse = false;
var lastclick = -1; // stores the object of our last used object
var oTR = null;
var oStatus = null;
var none = 0;

function initResultTable(element) {
	// get TBODY - take the first TBODY for the table to sort
	tbody = element.tBodies(0);
	if (!tbody) {
		return;
	}
	//Get THEAD
	var thead = element.tHead;
	if (!thead) {
		return;
	}
	theadrow = thead.children[0]; //Assume just one Head row
	if (theadrow.tagName != "TR") {
		return;
	}
	theadrow.runtimeStyle.cursor = "hand";
	colCount = theadrow.children.length;
	var l, clickCell;
	for (var i = 0; i < colCount; i++) {
		// Create our blank gif
		l = document.createElement("IMG");
		if (i == 0) {
			l.src = "/claim/platform/dtable/sortUp.gif";
			lastclick = 0;
		} else {
			l.src = "/claim/platform/dtable/sortBlank.gif";
		}
		l.id = "srtImg";
		l.width = 25;
		l.height = 11;
		clickCell = theadrow.children[i];
		clickCell.selectIndex = i;
		clickCell.insertAdjacentElement("beforeEnd", l);
		clickCell.attachEvent("onclick", doClick);
	}
	setTableRowClass(tbody, tbody.rows.length - 1, reverse, 0);
}

//
// doClick handler
//
//

function doClick(e) {
	var clickObject = e.srcElement;
	tbody.parentElement.scrollIntoView(true);
	while (clickObject.tagName != "TD") {
		clickObject = clickObject.parentElement;
	}
	if (e.srcElement.tagName == "INPUT") {
		return;
	}
	// clear the sort images in the head
	var imgcol = theadrow.all("srtimg");
	for (var x = 0; x < imgcol.length; x++) {
		imgcol[x].src = "/claim/platform/dtable/sortBlank.gif";
	}
	var obj = clickObject.children[0];
	for (var i = 0; i < clickObject.children.length; i++) {
		if (clickObject.children[i].tagName != "INPUT") {
			obj = clickObject.children[i];
			break;
		}
	}
	if (lastclick == clickObject.selectIndex) {
		if (reverse == false) {
			obj.src = "/claim/platform/dtable/sortDown.gif";
			reverse = true;
		} else {
			obj.src = "/claim/platform/dtable/sortUp.gif";
			reverse = false;
		}
	} else {
		reverse = false;
		lastclick = clickObject.selectIndex;
		obj.src = "/claim/platform/dtable/sortUp.gif";
	}
	sortTable(tbody, tbody.rows.length - 1, reverse, clickObject.selectIndex);
}

function sortTable(t, iRowEnd, fReverse, iColumn) {
	insertionSort(t, iRowEnd, fReverse, iColumn);
	setTableRowClass(t, iRowEnd, fReverse, iColumn);
	try {
		var count = fm.checkboxSelect.length;
		if (count > 1) {
			for (var i = 0; i < count; i++) {
				fm.checkboxSelect[i].value = i;
			}
		}
	} catch (E) {
		//alert(E);
	}
}

function setTableRowClass(t, iRowEnd, fReverse, iColumn) {
	var rowClass = "odd";
	for (iRowInsert = 0; iRowInsert <= iRowEnd; iRowInsert++) {
		if (iRowInsert % 2 == 0) {
			rowClass = "odd";
		} else {
			rowClass = "even";
		}
		t.children[iRowInsert].className = rowClass;
	}
}

function insertionSort(t, iRowEnd, fReverse, iColumn) {
	var iRowInsertRow, iRowWalkRow, current, insert;
	var childrens = t.children;
	for (iRowInsert = 1; iRowInsert <= iRowEnd; iRowInsert++) {
		if (iColumn) {
			if (typeof (childrens[iRowInsert].children[iColumn]) != "undefined") {
				textRowInsert = childrens[iRowInsert].children[iColumn].innerText;
			} else {
				textRowInsert = "";
			}
		} else {
			textRowInsert = childrens[iRowInsert].innerText;
			var pos = textRowInsert.indexOf(" ");
			if (pos > -1) {
				textRowInsert = textRowInsert.substring(0, pos);
			}
		}
		for (iRowWalk = 0; iRowWalk <= iRowInsert; iRowWalk++) {
			if (iColumn) {
				if (typeof (childrens[iRowWalk].children[iColumn]) != "undefined") {
					textRowCurrent = childrens[iRowWalk].children[iColumn].innerText;
				} else {
					textRowCurrent = "";
				}
			} else {
				textRowCurrent = childrens[iRowWalk].innerText;
				var pos = textRowCurrent.indexOf(" ");
				if (pos > -1) {
					textRowCurrent = textRowCurrent.substring(0, pos);
				}
			}

			//
			// We save our values so we can manipulate the numbers for
			// comparison
			//
			current = textRowCurrent;
			insert = textRowInsert;

			//  If the value is not a number, we sort normally, else we evaluate
			//  the value to get a numeric representation
			//
			if (!isNaN(current) && !isNaN(insert)) {
				try {
					current = parseFloat(current, 10);
					insert = parseFloat(insert, 10);
				} catch (E) {
					current = eval(current);
					insert = eval(insert);
				}
			} else {
				current = current.toLowerCase();
				insert = insert.toLowerCase();
			}
			if (((!fReverse && insert < current) || (fReverse && insert > current)) && (iRowInsert != iRowWalk)) {
				t.insertBefore(childrens[iRowInsert], childrens[iRowWalk]);
				iRowWalk = iRowInsert; // done
			}
		}
	}
}

function locate(pageNo) {
	if (pageNo < 1) {
		alert("\u5df2\u5230\u7b2c\u4e00\u9875");
		return false;
	}
	if (pageNo > parseInt(getFirstElementValue("pagesCount"), 10)) {
		alert("\u5df2\u5230\u6700\u540e\u4e00\u9875");
		return false;
	}
	if (pageNo == 1 && parseInt(fm.pageNo.value, 10) == 1) {
		alert("\u5df2\u5230\u7b2c\u4e00\u9875");
		return false;
	}
	if (pageNo == 1 && pageNo == parseInt(getFirstElementValue("pagesCount"), 10)) {
		alert("\u5df2\u5230\u6700\u540e\u4e00\u9875");
		return false;
	}
	fm.pageNo.value = pageNo;
	fm.submit();
	return true;
}

function goPage() {
	var pageNo = parseInt(getFirstElementValue("newPageNo"), 10);
	if (isNaN(pageNo)) {
		pageNo = 1;
	}
	if (pageNo > parseInt(getFirstElementValue("pagesCount"), 10)) {
		alert("\u65e0\u6cd5\u8f6c\u5230\u7b2c" + pageNo + "\u9875");
		return false;
	}
	return locate(pageNo);
}

function changeRowsPerPage(field) {
	var rows = parseInt(field.value, 10);
	if (isNaN(rows)) {
		return false;
	}
	if (rows > 500) {
		alert("\u6bcf\u9875\u4e0d\u5141\u8bb8\u8d85\u8fc7500\u6761");
		return false;
	}
	fm.rowsPerPage.value = rows;
	fm.pageNo.value = 1;
	fm.submit();
	return true;
}