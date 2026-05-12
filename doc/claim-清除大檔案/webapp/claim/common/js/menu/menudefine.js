if (!exclude) {
	//菜单定义
	// add main link item ("url","Link name",width,"text-alignment","_target","alt text",top position,left position,"key trigger")
	// define submenu properties (width,"align to edge","text-alignment",v offset,h offset,"filter")
	// add submenu link items ("url","Link name","_target","alt text")
	// define child menu properties (width,"align to edge","text-alignment",v offset,h offset,"filter")
	// add child menu link items ("url","Link name","_target","alt text")


	addMainItem("", "报案处理", 65, "center", "_top", "", 0, 0, "h");
	defineSubmenuProperties(90, "left", "left", -4, -5, "");
	addSubmenuItem("/claim/common/regist/UILRegistBeforeInput.jsp", "报案輸入", "main", "");
	addSubmenuItem("/claim/common/regist/FcoRegistQuery.jsp", "报案查询", "main", "");
	addSubmenuItem("/reins/common/repolicy/fcoRepolicyQuery.jsp", "报案修改", "main", "");
	addSubmenuItem("/reins/common/repolicy/fcoRepolicyQuery.jsp", "报案删除", "main", "");

	addMainItem("", "产品管理", 65, "center", "_top", "", 0, 0, "h");
	defineSubmenuProperties(100, "left", "left", -4, -5, "");
	addSubmenuItem("", "合約管理", "main", "");
	defineChildmenuProperties(80, "left", "left", 12, -6, "");
	addChildmenuItem("/reins/treaty/TreatySelect.jsp", "合約輸入", "main", "");
	addChildmenuItem("", "合約生效", "main", "");
	addChildmenuItem("", "合約续转", "main", "");
	addSubmenuItem("", "单号初始化管理", "main", "");
	addSubmenuItem("", "权限管理", "main", "");
	addSubmenuItem("", "代码管理", "main", "");
	addSubmenuItem("", "直接业务维护", "main", "");
	defineChildmenuProperties(80, "left", "left", 12, -6, "");
	addChildmenuItem("/reins/common/policy/fcoPolicyQuery.jsp", "保单维护", "main", "");
	addChildmenuItem("/reins/common/endor/EndorQuery.jsp", "批单维护", "main", "");
	addChildmenuItem("/reins/common/pay/PayQuery.jsp", "赔案维护", "main", "");
	addMainItem("/reins/index.jsp", "重新登录", 65, "center", "_top", "", 0, 0, "h");
	addMainItem("", "修改密码", 65, "center", "_top", "", 0, 0, "h");
}