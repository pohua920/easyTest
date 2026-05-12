function showPage(img, spanID) {
	if (spanID.style.display == "") {
		// ¹Ø±Õ
		spanID.style.display = "none";
		img.src = "/claim/images/butCollapseBlue.gif";
	} else {
		// Õ¹¿ª
		spanID.style.display = "";
		img.src = "/claim/images/butExpandBlue.gif";
	}
}
