var selectedItem = null;

var targetWin;

document.onclick = handleClick;
document.onmouseover = handleOver;
document.onmouseout = handleOut;
//document.onmousedown = handleDown;
//document.onmouseup = handleUp;

document.write(writeSubPadding(10)); //write the stylesheet for the sub. Getting the indention right

function handleClick() {
	el = getReal(window.event.srcElement, "tagName", "DIV");

	if ((el.className == "topFolder") || (el.className == "subFolder")) {
		//		if (el.sub == null) el.sub = eval(el.id + "Sub");

		el.sub = eval(el.id + "Sub");
		if (el.sub.style.display == null) el.sub.style.display = "none";
		if (el.sub.style.display != "block") { //hidden
			//any other sub open?
			if (el.parentElement.openedSub != null) {
				var opener = eval(el.parentElement.openedSub + ".opener");
				hide(el.parentElement.openedSub);
				if (opener.className == "topFolder")
					outTopItem(opener);
				iconTopFolder(opener);
			}
			el.sub.style.display = "block";
			el.sub.parentElement.openedSub = el.sub.id;
			el.sub.opener = el;
		} else {
			if (el.sub.openedSub != null) hide(el.sub.openedSub);
			else hide(el.sub.id);
		}
	}

	if ((el.className == "subItem") || (el.className == "subFolder")) {
		if (selectedItem != null)
			restoreSubItem(selectedItem);
	}

	if ((el.className == "topItem") || (el.className == "topFolder")) {
		if (selectedItem != null)
			restoreSubItem(selectedItem);
	}

	if (el.className == "topFolder") {
		//		if (selectedTopFolder != null)
		iconTopFolder(el);
	}

	if ((el.className == "topItem") || (el.className == "subItem")) {
		if ((el.href != null) && (el.href != "")) {
			if ((el.target == null) || (el.target == "")) {
				if (window.opener == null) {
					//					alert(document.all.tags("BASE").item(0));
					if (document.all.tags("BASE").item(0) != null)
					//						eval(document.all.tags("BASE").item(0).target + ".location = el.href");
						window.open(el.href, document.all.tags("BASE").item(0).target);
					else
						window.location = el.href; // HERE IS THE LOADING!!!
				} else {
					window.opener.location = el.href;
				}
			} else {
				window.open(el.href, el.target);
				//				eval(el.target + ".location = el.href");
			}
		}
	}

	var tmp = getReal(el, "className", "favMenu");
	if (tmp.className == "favMenu") fixScroll(tmp);

}

function handleOver() {
	var fromEl = getReal(window.event.fromElement, "tagName", "DIV");
	var toEl = getReal(window.event.toElement, "tagName", "DIV");
	if (fromEl == toEl) return;

	el = toEl;

	if ((el.className == "topFolder") || (el.className == "topItem")) overTopItem(el);
	if ((el.className == "subFolder") || (el.className == "subItem")) overSubItem(el);

	if ((el.className == "topItem") || (el.className == "subItem")) {
		if (el.href != null) {
			if (el.oldtitle == null) el.oldtitle = el.title;
			if (el.oldtitle != "")
			//				el.title = el.oldtitle + "\n" + el.href;
				el.title = el.oldtitle;
			else
			//				el.title = el.oldtitle + el.href;
				el.title = el.oldtitle;
		}
	}

	if (el.className == "scrollButton") overscrollButton(el);
}

function handleOut() {
	var fromEl = getReal(window.event.fromElement, "tagName", "DIV");
	var toEl = getReal(window.event.toElement, "tagName", "DIV");
	if (fromEl == toEl) return;

	el = fromEl;

	if ((el.className == "topFolder") || (el.className == "topItem")) outTopItem(el);
	if ((el.className == "subFolder") || (el.className == "subItem")) outSubItem(el);
	if (el.className == "scrollButton") outscrollButton(el);
}

function handleDown() {
	el = getReal(window.event.srcElement, "tagName", "DIV");

	if (el.className == "scrollButton") {
		downscrollButton(el);
		var mark = Math.max(el.id.indexOf("Up"), el.id.indexOf("Down"));
		var type = el.id.substr(mark);
		var menuID = el.id.substring(0, mark);
		eval("scroll" + type + "(" + menuID + ")");
	}
}

function handleUp() {
	el = getReal(window.event.srcElement, "tagName", "DIV");

	if (el.className == "scrollButton") {
		upscrollButton(el);
		window.clearTimeout(scrolltimer);
	}
}

////////////////////// EVERYTHING IS HANDLED ////////////////////////////

function hide(elID) {
	var el = eval(elID);
	el.style.display = "none";
	el.parentElement.openedSub = null;
	if (el.openedSub != null) hide(el.openedSub);
}

function writeSubPadding(depth) {
	var str, str2, val;

	var str = "<style type='text/css'>\n";

	for (var i = 0; i < depth; i++) {
		str2 = "";
		val = 0;
		for (var j = 0; j < i; j++) {
			str2 += ".sub "
			val += 22;
		}
		str += str2 + ".subFolder {padding-left: " + val + "px;}\n";
		str += str2 + ".subItem   {padding-left: " + val + "px;}\n";
	}

	str += "</style>\n";
	return str;
}

//If you wan't to change colors do so below

function overTopItem(el) {
	with(el.style) {
		background = "#E97013";
		borderLeft = "1px solid buttonhighlight";
		borderRight = "1px solid #CECFCE";
		borderTop = "1px solid buttonhighlight";
		borderBottom = "1px solid #CECFCE";
		paddingBottom = "2px";
	}
}

function outTopItem(el) {
	if ((el.sub != null) && (el.parentElement.openedSub == el.sub.id)) { //opened
		with(el.style) {
			borderTop = "1px solid #CECFCE";
			borderLeft = "1px solid #CECFCE";
			borderRight = "1px solid buttonhighlight";
			borderBottom = "0px";
			paddingBottom = "3px";
			background = "#FEE4A2";
		}
	} else {
		with(el.style) {
			border = "1px solid #FEE4A2";
			background = "#FEE4A2";
			padding = "2px";
		}
	}
}

function overSubItem(el) {
	el.style.textDecoration = "underline";
	el.style.background = "#FFF9E9";
}

function outSubItem(el) {
	el.style.textDecoration = "none";
	el.style.background = "#FEE4A2";
}

function iconTopFolder(xxx) {
	if (xxx.innerHTML.indexOf("butCollapseYellow.gif") != -1) {
		xxx.innerHTML = xxx.innerHTML.replace("butCollapseYellow.gif", "butExpandYellow.gif")
	} else {
		xxx.innerHTML = xxx.innerHTML.replace("butExpandYellow.gif", "butCollapseYellow.gif")
	};
}

function restoreSubItem(el) {
	el.style.background = "#FEE4A2";
	el.style.color = "menutext";
	selectedItem = null;
}

function overscrollButton(el) {
	overTopItem(el);
	el.style.padding = "0px";
}

function outscrollButton(el) {
	outTopItem(el);
	el.style.padding = "0px";
}

function downscrollButton(el) {
	with(el.style) {
		borderRight = "1px solid buttonhighlight";
		borderLeft = "1px solid #CECFCE";
		borderBottom = "1px solid buttonhighlight";
		borderTop = "1px solid #CECFCE";
	}
}

function upscrollButton(el) {
	overTopItem(el);
	el.style.padding = "0px";
}

// ...till here

function getReal(el, type, value) {
	var temp = el;
	while ((temp != null) && (temp.tagName != "BODY")) {
		if (eval("temp." + type) == value) {
			el = temp;
			return el;
		}
		temp = temp.parentElement;
	}
	return el;
}


////////////////////////////////////////////////////////////////////////////////////////
// Fix the scrollbars

var globalScrollContainer; // Needed because the object is called through windwow.setTimeout
var overflowTimeout = 1;

function fixScroll(el) {
	globalScrollContainer = el;
	window.setTimeout('changeOverflow(globalScrollContainer)', overflowTimeout);
}


function changeOverflow(el) {
	if (el.offsetHeight > el.parentElement.clientHeight)
		window.setTimeout('globalScrollContainer.parentElement.style.overflow = "auto";', overflowTimeout);
	else
		window.setTimeout('globalScrollContainer.parentElement.style.overflow = "hidden";', overflowTimeout);
}