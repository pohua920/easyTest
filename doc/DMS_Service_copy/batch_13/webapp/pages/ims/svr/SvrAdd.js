//插入数据
	function prepareInsertMethod(){
//      fm.action="contextRootPath/utiISvr/prepareSvrInsert.do";
		fm.action = "contextRootPath/utiISvr/selectPost.do";
        fm.submit();
        return true;
    }	