function addOption(fromSelect,toSelect){
	var i,j;
	for(i=0;i<fm.all(fromSelect).options.length;i++){
		if(fm.all(fromSelect).options[i].selected){
			var existOption = false;
			for(j=0;j<fm.all(toSelect).options.length;j++){
				if( fm.all(fromSelect).options[i].value == fm.all(toSelect).options[j].value ){
					existOption = true;
					break;
				}
			}
			if(existOption==false){
				var option = document.createElement("option");
				option.value = fm.all(fromSelect).options[i].value;
				option.text = fm.all(fromSelect).options[i].text;
				fm.all(toSelect).add(option);
			}
		} 
	}
}

function delOption(targetSelect){
	var i;
	for(i=fm.all(targetSelect).options.length-1;i>=0;i--){
		if(fm.all(targetSelect).options[i].selected){
			fm.all(targetSelect).remove(i);
		} 
	}
}
