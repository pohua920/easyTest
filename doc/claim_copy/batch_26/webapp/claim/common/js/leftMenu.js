	/**
		功能：同maingo，解决了原maingo函数中存在的一个潜在的BUG；
		说明：原maingo的BUG是：若三级菜单的FuncID长度不是1位、2位、4位时，则此脚本会出错，导致导航失败；
		编写人：孟冬冬
		编写时间：2006-1-11
	*/
	function maingo_new(p_Id, doWhat, strName, linkTo, p_station, p_level2Id, p_level1Id) {
	    var linkHtml = "首页";
	    linkHtml = linkHtml + " > " + document.all(p_level1Id + "font").value + " > " + document.all(p_level2Id + "font").value + " > ";
	    linkHtml = linkHtml + strName;

	    findIframe("fraTitle").setCommand(linkHtml);
	    if (linkTo != "") {
	        if (linkTo == "help/main.htm") {
	        } else {
	            findIframe("fraInterface").location = linkTo;
	        }
	    }
	}

	function maingo(p_Id, doWhat, strName, linkTo, p_station) {
	    /**
	    	注意：此函数存在一个潜在的BUG：
	    	现象：若三级菜单的FuncID长度不是1位、2位、4位时，则此脚本会出错，导致导航失败；
	    	原因：在本脚本中是根据p_Id的第一位和前两位来获得上两级父菜单的菜单名称，而若上两级菜单的长度不是1位和2位时，则会出错；
	    	解决方法：详见新的的函数maingo_new。
	    */
	    //alert("p_Id="+p_Id+"|doWhat="+doWhat+"|strName="+strName+"|linkTo="+linkTo+"|p_station="+p_station);
	    var linkHtml = "首页";
	    //linkHtml = document.all(p_Id.substring(0,1)+"font").value+" --"+document.all(p_Id.substring(0,2)+"font").value+" --";
	    //linkHtml = linkHtml+"<font ID='"+p_Id+"font' color='#000000' onClick="+"\""+"maingo('"+p_Id+"','"+p_Id+"','"+strName+"','"+linkTo+"','"+p_Id+"')"+"\""+">"+strName+"</font>";
	    //linkHtml = linkHtml+"<font onClick=alert('sdfsdf')>sdf</font>";
	    //alert("::::::"+linkHtml);
	    //linkHtml = document.all(p_Id.substring(0,1)+"font").value+" --"+document.all(p_Id.substring(0,2)+"font").value+" --"+"<font ID='"+p_Id+"font' color='#000000' onClick="+"\""+"maingo('"+p_Id+"','"+p_Id+"','"+strName+"','"+linkTo+"','"+p_Id+"')"+"\""+">"+strName+"</font>";

	    //document.all(document.all.checkedId.value+"font").color = "#000000";
	    //        document.all.checkedId.value = p_Id;
	    //        document.all.checkedName.value = strName;
	    //document.all(p_Id+"font").color = "#ff0000";


	    linkHtml = linkHtml + " > " + document.all(p_Id.substring(0, 1) + "font").value + " > " + document.all(p_Id.substring(0, 2) + "font").value + " > ";
	    linkHtml = linkHtml + strName;
	    findIframe("fraTitle").setCommand(linkHtml);
	    if (linkTo != "") {
	        if (linkTo == "help/main.htm") {
	        } else {
	            findIframe("fraInterface").location = linkTo;
	        }
	    }
	}

	function clickEvent(p_obj, strOpenUrl, strCloseUrl) {
	    var strAddUrl = "/claim/images/treeAdd.gif";
	    var strSubtractUrl = "/claim/images/treeSubtract.gif";
	    if (document.all(p_obj.id + "p").style.display == "none") {
	        document.all(p_obj.id + "p").style.display = "";
	        //document.all(p_obj.id).src = strSubtractUrl;
	        //document.all(p_obj.id+"II").src = strOpenUrl;
	    } else {
	        document.all(p_obj.id + "p").style.display = "none";
	        //document.all(p_obj.id).src = strAddUrl;
	        //document.all(p_obj.id+"II").src = strCloseUrl;
	    }
	}

	function clickEventtwo(ifEnd) {
	    var strCloseUrl = "/claim/images/tree_folderclass2.gif";
	    var strOpenUrl = "/claim/images/tree_folderclass2open.gif";

	    var strAddUrl = "/claim/images/tree_Tplus.gif";
	    if (ifEnd == "true") {
	        strAddUrl = "/claim/images/tree_Lplus.gif";
	    }
	    var strSubtractUrl = "/claim/images/tree_Tminus.gif";
	    if (ifEnd == "true") {
	        strSubtractUrl = "/claim/images/tree_Lminus.gif";
	    }

	    if (document.all(window.event.srcElement.id + "p").style.display == "none") {
	        document.all(window.event.srcElement.id + "p").style.display = "";
	        //document.all(window.event.srcElement.id).src = strSubtractUrl;
	        //document.all(window.event.srcElement.id+"II").src = strOpenUrl;
	    } else {
	        document.all(window.event.srcElement.id + "p").style.display = "none";
	        //document.all(window.event.srcElement.id).src = strAddUrl;
	        //document.all(window.event.srcElement.id+"II").src = strCloseUrl;
	    }
	}