KMessageBox = {
	name: "KMessageBox",
	capiton: "消息框",
	content: "提示消息",
	msgbox: null,
	msgcaptioninfo: null,
	msgcontent: null,
	msgContenttxtmsg: null,
	msgbuttonyes: null,
	msbbuttonno: null,
	msgico: null
};

KMessageBox.init = function () {
	var msgNameSpaceURI = "http://www.w3.org/1999/xhtml";

	if (!msgContainerID) {
		var msgContainerID = "KMessageBox";
	}

	if (!msgCaptionID) {
		var msgCaptionID = "KMessageBox_caption";
	}
	if (!msgCaptionInfoID) {
		var msgCaptionInfoID = "KMessageBox_caption_info";
	}
	if (!msgContentID) {
		var msgContentID = "KMessageBox_content";
	}
	if (!msgContentTxtID) {
		var msgContentTxtID = "KMessageBox_content_txt";
	}
	if (!msgContentTxtICOID) {
		var msgContentTxtICOID = "KMessageBox_content_txt_ico"
	};
	if (!msgContentTxtMsgID) {
		var msgContentTxtMsgID = "KMessageBox_content_txt_msg"
	};
	if (!msgButtons) {
		var msgButtonsID = "KMessageBox_buttons"
	};
	if (!msgButtonYes) {
		var msgButtonYesID = "KMessageBox_buttons_yes"
	};
	if (!msgButtonNo) {
		var msgButtonNoID = "KMessageBox_buttons_no"
	};
	if (!msgButtonOK) {
		var msgButtonOKID = "KMessageBox_buttons_ok"
	};

	var msgContainer = $(msgContainerID);
	if (!msgContainer) {
		msgContainer = document.createElementNS ? document.createElementNS(msgNameSpaceURI, "div") : document.createElement("div");
		msgContainer.setAttribute("id", msgContainerID);
		msgContainer.setAttribute("style", "MARGIN: 0px auto; POSITION: absolute; TEXT-ALIGN: center;");


		var msgCaption = $(msgCaptionID);

		if (!msgCaption) {
			msgCaption = document.createElementNS ? document.createElementNS(msgNameSpaceURI, "div") : document.createElement("div");
			msgCaption.setAttribute("id", msgCaptionID);
			Element.addClassName(msgCaption, "caption");


			var msgCaptionInfo = $(msgCaptionInfoID);
			if (!msgCaptionInfo) {
				msgCaptionInfo = document.createElementNS ? document.createElementNS(msgNameSpaceURI, "div") : document.createElement("div");
				msgCaptionInfo.setAttribute("id", msgCaptionInfoID);
				Element.addClassName(msgCaptionInfo, "info");
				msgCaption.appendChild(msgCaptionInfo);
			}
			msgContainer.appendChild(msgCaption);

		}

		var msgContent = $(msgContentID);

		if (!msgContent) {
			msgContent = document.createElementNS ? document.createElementNS(msgNameSpaceURI, "div") : document.createElement("div");
			msgContent.setAttribute("id", msgContentID);
			Element.addClassName(msgContent, "content");

			var msgContentTxt = $(msgContentTxtID);

			if (!msgContentTxt) {
				msgContentTxt = document.createElementNS ? document.createElementNS(msgNameSpaceURI, "div") : document.createElement("div");
				msgContentTxt.setAttribute("id", msgContentTxtID);
				Element.addClassName(msgContentTxt, "txt");

				var msgContentTxtICO = $(msgContentTxtICOID);
				if (!msgContentTxtICO) {
					msgContentTxtICO = document.createElementNS ? document.createElementNS(msgNameSpaceURI, "img") : document.createElement("img");
					msgContentTxtICO.setAttribute("id", msgContentTxtICOID);
					msgContentTxtICO.setAttribute("src", "/claim/undwrt/common/icon_alarm.gif");
					msgContentTxtICO.setAttribute("align", "absMiddle");
					msgContentTxtICO.setAttribute("style", "height:52px;width:64px;background-image:url('/claim/undwrt/common/images/icon_big_info.gif');");
					msgContentTxt.appendChild(msgContentTxtICO);
				}

				var msgContentTxtMsg = $(msgContentTxtMsgID);
				if (!msgContentTxtMsg) {
					msgContentTxtMsg = document.createElementNS ? document.createElementNS(msgNameSpaceURI, "span") : document.createElement("span");
					msgContentTxtMsg.setAttribute("id", msgContentTxtMsgID);

					msgContentTxt.appendChild(msgContentTxtMsg);
				}


				var msgButtons = $(msgButtonsID);
				if (!msgButtons) {
					msgButtons = document.createElementNS ? document.createElementNS(msgNameSpaceURI, "div") : document.createElement("div");
					msgButtons.setAttribute("id", msgButtonsID);
					Element.addClassName(msgButtons, "btnlist");
					var msgButtonYes = $(msgButtonYesID);
					if (!msgButtonYes) {
						msgButtonYes = document.createElementNS ? document.createElementNS(msgNameSpaceURI, "input") : document.createElement("input");
						msgButtonYes.setAttribute("id", msgButtonYesID);
						msgButtonYes.setAttribute("type", "button");
						msgButtonYes.setAttribute("value", "确定");
						Element.addClassName(msgButtonYes, "input_set");

						msgButtons.appendChild(msgButtonYes);
					}

					var msgButtonNo = $(msgButtonNoID);
					if (!msgButtonNo) {
						msgButtonNo = document.createElementNS ? document.createElementNS(msgNameSpaceURI, "input") : document.createElement("input");
						msgButtonNo.setAttribute("id", msgButtonNoID);
						msgButtonNo.setAttribute("type", "button");
						msgButtonNo.setAttribute("value", "取消");
						Element.addClassName(msgButtonNo, "input_set");

						msgButtons.appendChild(msgButtonNo);

					}

					var msgButtonOK = $(msgButtonOKID);
					if (!msgButtonOK) {
						msgButtonOK = document.createElementNS ? document.createElementNS(msgNameSpaceURI, "input") : document.createElement("input");
						msgButtonOK.setAttribute("id", msgButtonOKID);
						msgButtonOK.setAttribute("type", "button");
						msgButtonOK.setAttribute("value", "OK");
						Element.addClassName(msgButtonOK, "input_set");

						msgButtons.appendChild(msgButtonOK);

					}


					msgContentTxt.appendChild(msgButtons);
				}

				msgContent.appendChild(msgContentTxt);
			}


			msgContainer.appendChild(msgContent);
		}

		document.getElementsByTagName("body").item(0).appendChild(msgContainer);
	}

	this.msgbox = $(this.name);
	this.msgcaptioninfo = $(msgCaptionInfoID);
	this.msgContenttxtmsg = $(msgContentTxtMsgID);
	this.msgbuttonyes = $(msgButtonYesID);
	this.msgbuttonno = $(msgButtonNoID);
	this.msgbuttonok = $(msgButtonOKID);
	this.msgico = $(msgContentTxtICOID);
	Element.hide(this.msgbox);

}

KMessageBox.ShowConfirm = function (imgdir, caption, msg, YesClick, NoClick) {

	if (!this.msgbox) return;
	//alert(document.body.style.overflowY);
	DialogModal.Show();

	this.msgcaptioninfo.innerHTML = caption;
	this.msgContenttxtmsg.innerHTML = msg;
	if (imgdir != "") {
		this.msgico.setAttribute("src", imgdir + "/claim/undwrt/common/images/icon_alarm.gif");
	} else {
		this.msgico.setAttribute("src", "/claim/undwrt/common/images/icon_alarm.gif");
	}

	Element.show(this.msgbox);
	Element.show(this.msgbuttonyes);
	Element.show(this.msgbuttonno);
	Element.hide(this.msgbuttonok);

	var x = 0,
		y = 0;
	x = (document.documentElement.scrollLeft || document.body.scrollLeft);
	y = (document.documentElement.scrollTop || document.body.scrollTop);

	var theWidth = 0,
		theHeight = 0;

	if (window.innerWidth) {
		theWidth = window.innerWidth
		theHeight = window.innerHeight
	} else if (document.documentElement && document.documentElement.clientWidth) {
		theWidth = document.documentElement.clientWidth
		theHeight = document.documentElement.clientHeight
	} else if (document.body) {
		theWidth = document.body.clientWidth
		theHeight = document.body.clientHeight
	}

	this.msgbox.style.left = (theWidth - this.msgbox.offsetWidth) / 2 + x;
	this.msgbox.style.top = (theHeight - this.msgbox.offsetHeight) / 2 + y;

	this.msgbuttonyes.onclick = YesClick; //function(){ alert('yes');};
	this.msgbuttonno.onclick = NoClick;

	Event.observe(this.msgbuttonyes, "click", function () {
		KMessageBox.Hide();
	}, true);
	Event.observe(this.msgbuttonno, "click", function () {
		KMessageBox.Hide();
	}, true);
}

KMessageBox.ShowInfo = function (imgdir, caption, msg) {

	if (!this.msgbox) return;
	DialogModal.Show();

	if (imgdir != "") {
		this.msgico.setAttribute("src", imgdir + "/claim/undwrt/common/images/icon_big_info.gif");
	} else {
		this.msgico.setAttribute("src", "/claim/undwrt/common/images/icon_big_info.gif");
	}

	this.msgcaptioninfo.innerHTML = caption;
	this.msgContenttxtmsg.innerHTML = msg;
	Element.show(this.msgbox);
	Element.show(this.msgbuttonok);
	Element.hide(this.msgbuttonyes);
	Element.hide(this.msgbuttonno);

	var x = 0,
		y = 0;
	x = (document.documentElement.scrollLeft || document.body.scrollLeft);
	y = (document.documentElement.scrollTop || document.body.scrollTop);

	var theWidth = 0,
		theHeight = 0;

	if (window.innerWidth) {
		theWidth = window.innerWidth
		theHeight = window.innerHeight
	} else if (document.documentElement && document.documentElement.clientWidth) {
		theWidth = document.documentElement.clientWidth
		theHeight = document.documentElement.clientHeight
	} else if (document.body) {
		theWidth = document.body.clientWidth
		theHeight = document.body.clientHeight
	}

	this.msgbox.style.left = (theWidth - this.msgbox.offsetWidth) / 2 + x;
	this.msgbox.style.top = (theHeight - this.msgbox.offsetHeight) / 2 + y;


	Event.observe(this.msgbuttonok, "click", function () {
		KMessageBox.Hide();
	}, true);
}


KMessageBox.Hide = function () {
	if (!this.msgbox) return;
	Element.hide(this.msgbox);
	DialogModal.Close();
	Event.stopObserving(this.msgbuttonyes, "click", function () {
		KMessageBox.Hide();
	}, true)
	Event.stopObserving(this.msgbuttonno, "click", function () {
		KMessageBox.Hide();
	}, true)
	Event.stopObserving(this.msgbuttonok, "click", function () {
		KMessageBox.Hide();
	}, true)
}


function DialogModal() {
	this.blankImgHandle = null;
	this.tags = new Array("applet", "iframe", "select", "object", "embed");
}


DialogModal.Show = function () {
	debugger;
	var NameSpaceURI = "http://www.w3.org/1999/xhtml";
	this.blankImgHandle = document.createElementNS ? document.createElementNS(NameSpaceURI, "iframe") : document.createElement("iframe"); //iframe
	this.blankImgHandle.setAttribute("id", "blankImgHanldle");
	with(this.blankImgHandle.style) {
		position = "absolute";
		left = 0;
		top = (document.documentElement.scrollTop || document.body.scrollTop);
		height = "100%";
		width = "100%";
		zIndex = "9999";
		filter = "progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=40)";
		opacity = "0.1";
	}

	document.getElementsByTagName("body").item(0).appendChild(this.blankImgHandle);
}

DialogModal.Close = function () {
	if (this.blankImgHandle) {
		Element.remove(this.blankImgHandle);
		this.blankImgHandle = null;
	}
};


Event.observe(window, 'load', function (e) {
	KMessageBox.init();
}, false);
Event.observe(window, 'unload', Event.unloadCache, false);