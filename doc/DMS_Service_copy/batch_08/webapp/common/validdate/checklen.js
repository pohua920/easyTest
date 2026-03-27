function String.prototype.lenB(){return this.replace(/[^\x00-\xff]/g,"**").length;} 

function checkLen(){
	
	 var inputs = document.getElementsByTagName("input");

//     var selects = document.getElementsByTagName("select");
     inputsData = new Array(inputs.length);
    
     //inputsDataCheckBox = new Array(inputs.length);
     inputsDataText = new Array(inputs.length);
    
     //alert(inputs.length);
     for (var i=0;i<inputs.length;i++) {
     //	alert("111111111");
         inputsData[i] = inputs[i].value;
        // 	 alert("22222222222");
         if (inputs[i].type=="text") {
        // 	 alert("33");
        	 var len=inputs[i].maxLength;
        	 // alert("4444444444");
         	inputsDataText[i]=inputs[i].value;
         	if  (check(inputs[i],inputsDataText[i],len)){
         		continue;
         	}else{
         		return false;
         	}
         }
     }
    
	return true;
}
function check(strName,str,len){
	if (str.lenB()>len){
		alert(str+"长度必须小于等于"+len+"位!");
		strName.focus();
		return false;
	}
	return true;
}