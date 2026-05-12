<script type="text/javascript">
	var i18n;
	//向上递归查询iframe，iFrameName名称，object页面对象
	function findIframe(iFrameName,object){
		if(object==undefined){
			object = window;
		}
		try{
			var frames = object.frames;
			for(var i=0;i<frames.length;i++){
				if(frames[i].name==iFrameName){
					return frames[i];
				}
			}
		}catch(e){}
		if(object==object.parent){
			return object;
		}
		return findIframe(iFrameName,object.parent);
	}
	
	if(findIframe("fraTitle").parent.i18n!=undefined) {
		i18n = findIframe("fraTitle").parent.i18n;//top.fraTitle.i18n
	}else {
		i18n = new Object();
		i18n.navigator = new Object();
		i18n.navigator.page="<s:text name="navigator.page"/>";
		i18n.navigator.first="<s:text name="navigator.first"/>";
		i18n.navigator.last="<s:text name="navigator.last"/>";
		i18n.navigator.prev="<s:text name="navigator.prev"/>";
		i18n.navigator.next="<s:text name="navigator.next"/>";
		i18n.navigator.records="<s:text name="navigator.records"/>";
		i18n.navigator.page="<s:text name="navigator.page"/>";
		i18n.navigator.more="<s:text name="navigator.more"/>";
	
		i18n.prompt = new Object();
		i18n.prompt.ok="<s:text name="prompt.ok"/>";
		i18n.prompt.cancel="<s:text name="prompt.cancel"/>";
		
		i18n.errors = new Object();	
		i18n.errors.deletefail="<s:text name="errors.delete"/>";
	}
</script>
