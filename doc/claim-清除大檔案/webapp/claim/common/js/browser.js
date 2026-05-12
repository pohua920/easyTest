function ignoreError() {
	return true;
}
window.onerror = ignoreError;

var Browser
if (navigator.appName == "Microsoft Internet Explorer") {
	verStr = navigator.appVersion
	msie = verStr.indexOf("MSIE")
	ver = verStr.substring(msie + 5, msie + 8)
	if (ver >= 5.5) {
		Browser = "IE55up"
	} else {
		Browser = "IE55down"
	}
} else {
	Browser = "other"
}