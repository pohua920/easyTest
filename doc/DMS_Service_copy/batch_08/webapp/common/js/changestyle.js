// JavaScript Document


function ExChgClsName(Btn,Obj){
	var obj=document.getElementById(Obj);
		obj.style.display =obj.style.display == "none" ? "" : "none";
if(obj.style.display==""){
		Btn.className='default';
   }else{
   Btn.className='down'
}		
}

function addLoadEvent(func) {
			var oldonload = window.onload;
			
			if (typeof window.onload != "function") {
				window.onload = func;
			} else {
				window.onload = function () {
					oldonload();
					func();
				}
			}
		}
		
		/*------------------------------------+
		 | Functions to run when window loads |
		 +------------------------------------*/
		addLoadEvent(function () {
			initChecklist();
			diffent();
		});
		
		/*----------------------------------------------------------+
		 | initChecklist: Add :hover functionality on labels for IE |
		 +----------------------------------------------------------*/
		function initChecklist() {
			if (document.all && document.getElementById) {
				// Get all unordered lists
				var lists = document.getElementsByTagName("input");
				
				for (i = 0; i < lists.length; i++) {
					var theList = lists[i];
					
					// Only work with those having the class "checklist"
					if (theList.className.indexOf("upload") > -1 ) {

							theList.onmouseover = function() { this.className="upload_over"; };
							theList.onmouseout = function() { this.className="upload"; };

					}
					if (theList.className.indexOf("download") > -1 ) {

							theList.onmouseover = function() { this.className="download_over"; };
							theList.onmouseout = function() { this.className="download"; };

					}
					if (theList.className.indexOf("btn_refresh") > -1 ) {

							theList.onmouseover = function() { this.className="btn_refresh_over"; };
							theList.onmouseout = function() { this.className="btn_refresh"; };

					}
					if (theList.className.indexOf("btn_zoom") > -1 ) {

							theList.onmouseover = function() { this.className="btn_zoom_over"; };
							theList.onmouseout = function() { this.className="btn_zoom"; };

					}
					if (theList.className.indexOf("btn_print") > -1 ) {

							theList.onmouseover = function() { this.className="btn_print_over"; };
							theList.onmouseout = function() { this.className="btn_print"; };

					}
					if (theList.className.indexOf("button_ty") > -1 ) {

							theList.onmouseover = function() { this.className="button_ty_over"; };
							theList.onmouseout = function() { this.className="button_ty"; };

					}
				}
			}
		}
		
function diffent(){
			var Ps = document.getElementsByTagName("p");
			for (i = 0; i < Ps.length; i++) {
			var theP = Ps[i];
            if (theP.className.indexOf("bd_out") > -1 ) {
							theP.onmouseover = function() { this.className="bd_over"; };
							theP.onmouseout = function() { this.className="bd_out"; };
					}
}
}
