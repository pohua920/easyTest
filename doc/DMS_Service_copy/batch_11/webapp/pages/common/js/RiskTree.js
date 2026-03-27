/**
 * selectAllClass
 */
 function selectAllClass() {
 	if (fm.allSelectFlag.value==0) {
		checkChildren(rootNode,true);
		fm.allSelectFlag.value="1";
 	} else {
 		checkChildren(rootNode,false);
 		collapseNode(rootNode);
		fm.allSelectFlag.value="0";
 	}

 	
 }
 
 /**
  * name
  */
  function showSelectValue(){
  	//window.parent.fm.riskCode.value = getCheckValues();
  	//window.parent.closeThisPanel();
  	var arrayList = new Array();
  	arrayList = getCheckValues();
  	var treeCheckBoxList = new Array();
  	var arrayLineList = new Array();
  	var arrayClassList = new Array();
  	var arrayRiskList = new Array();
  	for(var v=0;v<arrayList.length;v++){
  		if (arrayList[v].indexOf('.') == -1) {
					arrayLineList.push(arrayList[v]);

				}else {
					if (arrayList[v].indexOf('.') == arrayList[v].lastIndexOf('.')) {
						arrayClassList.push(arrayList[v]);

					}else {
						arrayRiskList.push(arrayList[v]);
					}

				}
  	}
  	if (arrayLineList.length > 0) {
				for (var i=0;i<arrayLineList.length;i++) {
					treeCheckBoxList.push(arrayLineList[i]);
				}
			}
			if (arrayClassList.length > 0) {
				for (var m=0;m<arrayClassList.length;m++) {
					if (treeCheckBoxList.indexOf(arrayClassList[m].substring(0, arrayClassList[m]
							.indexOf('.')))==-1) {
						treeCheckBoxList.push(arrayClassList[m]);
					}
				}
			}
			if (arrayRiskList.length > 0) {
				for (var k=0;k<arrayRiskList.length;k++) {
					if (!(treeCheckBoxList.indexOf(arrayRiskList[k].substring(0, arrayRiskList[k]
							.indexOf('.')))>-1 || treeCheckBoxList.indexOf(arrayRiskList[k]
							.substring(0, arrayRiskList[k].lastIndexOf('.')))>-1)) {
						treeCheckBoxList.push(arrayRiskList[k]);
					}
				}
			}
	window.parent.fm.riskCode.value = treeCheckBoxList;
  	window.parent.closeThisPanel();
  }