function showRelateInfo(){
	 var count = getElementCount('treeCheckBox');
     if(count==0){
        alert('请选择一个菜单!');
        return false;
    }else 
    if(count==1){
        if(fm.treeCheckBox.checked==true){
        	fm.menuId.value=fm.treeCheckBox.value;
            return true;
        }
        else{
            alert('请选择一个菜单');
            return false;
        }
    }else{
    	var n = 0;
    	for(var i=0;i<fm.treeCheckBox.length;i++){
    	    if(fm.treeCheckBox[i].checked==true){
    	        n = n + 1;
    	    }
   	 	}
        if(n==0){
            alert("请选择一个菜单");
            return false;
        }
        else if(n==1){
            for(var j=0;j<fm.treeCheckBox.length;j++){
                if(fm.treeCheckBox[j].checked==true){
                   fm.menuId.value=fm.treeCheckBox[j].value;
                   break;
                 }
            }
        }
        else{
            alert("只能选择一个菜单");
            return false;
        }
        return true;
    	}
    }

function showRelateInfo1(){
	 var count = getElementCount('treeCheckBox');
     if(count==0){
        alert('请选择一个父结点菜单!');
        return false;
    }else 
    if(count==1){
        if(fm.treeCheckBox.checked==true){
        	fm.menuId.value=fm.treeCheckBox.value;
            return true;
        }
        else{
            alert('请选择一个父结点菜单');
            return false;
        }
    }else{
    	var n = 0;
    	for(var i=0;i<fm.treeCheckBox.length;i++){
    	    if(fm.treeCheckBox[i].checked==true){
    	        n = n + 1;
    	    }
   	 	}
        if(n==0){
            alert("请选择一个父结点菜单");
            return false;
        }
        else if(n==1){
            for(var j=0;j<fm.treeCheckBox.length;j++){
                if(fm.treeCheckBox[j].checked==true){
                   fm.menuId.value=fm.treeCheckBox[j].value;
                   break;
                 }
            }
        }
        else{
            alert("只能选择一个父结点菜单");
            return false;
        }
        return true;
    	}
    }


function viewMethod(){
	var chooseflag = showRelateInfo();
	if(chooseflag){
		if (fm.menuId.value!='0'){
			fm.action = '${ctx}/smcMenu/viewMenu.do?editType=view&menuID='+fm.menuId.value;
	        fm.target="menuTreeRight";
	        fm.submit();
	        return true;
 		}else{
 			alert("在此不能查看服务信息！");
 			return false;
 		}   
    }
}

function modifyMethod(){
	var chooseflag = showRelateInfo();
	if(chooseflag){
		if (fm.menuId.value!='0'){
			fm.action = '${ctx}/smcMenu/prepareUpdateMenu.do?editType=update&menuID='+fm.menuId.value;
	        fm.target="menuTreeRight";
	        fm.submit();
        return true;
      	}else {
      		alert("在此不能对服务进行修改！");
      		return false;
      	}
    }
}
function insertMethod(){
	var chooseflag = showRelateInfo1();
	if(chooseflag){
		  fm.action = '${ctx }/smcMenu/prepareInsertMenu.do?editType=insert&menuID='+fm.menuId.value;
        fm.target="menuTreeRight";
        fm.submit();
        return true;
    }
}

