 function modifyMethod(){
        var  chCode;
        var checkbox = document.getElementsByName("checkboxes");
        var num = 0;
        for(var j=0;j<checkbox.length;j++){
			if(checkbox[j].checked){
				num = num + 1;
			}
        }
        if(num == 0){
        	alert("请选择一项进行修改");
        }
        else if(num>1){
			alert("只能选择一项进行修改");
        }else{
	        for(var i=0;i<checkbox.length;i++){
				if(checkbox[i].checked){
					chCode = checkbox[i].value;
				}
	        }
	        fm.action="contextRootPath/utiISvr/prepareSvrModify.do?svrcode="+chCode;
	        fm.submit();
	        return true;
        }
    }