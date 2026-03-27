
YAHOO.namespace("calendar");

/*one candeer=============================================================*/
			function handleSelect(type,args,obj) {
				var dates = args[0]; 
				var date = dates[0];
				var year = date[0], month = date[1], day = date[2];
				
				var txtDate1 = document.getElementById("date");
				txtDate1.value =year + "/" + month + "/" + day;
			}

			function updateCal() {
				var txtDate1 = document.getElementById("date");

				if (txtDate1.value != "") {
					YAHOO.calendar.cal.select(txtDate1.value);
					
					var firstDate = YAHOO.calendar.cal.getSelectedDates()[0];
					YAHOO.calendar.cal.cfg.setProperty("pagedate", (firstDate.getMonth()+1) + "/" + firstDate.getFullYear());
					
					YAHOO.calendar.cal.render();
				}
			}

			// For this example page, stop the Form from being submitted, and update the cal instead
			function handleSubmit(e) {
				updateCal();
				YAHOO.util.Event.preventDefault(e);
			}
			function init() {
					YAHOO.calendar.cal = new YAHOO.widget.Calendar("cal","calContainer", { title:"请选择日期:", close:true } );
					
					YAHOO.calendar.cal.selectEvent.subscribe(handleSelect, YAHOO.calendar.cal, true);


					YAHOO.calendar.cal.render();

					// Listener to show the 1-up Calendar when the button is clicked
					YAHOO.util.Event.addListener("showup", "click", YAHOO.calendar.cal.show, YAHOO.calendar.cal, true);
				}

			YAHOO.util.Event.addListener(window, "load", init);

/*one candeer=============================================================*/




/*two candeer=============================================================*/
function handleSelect1(type,args,obj) {
				var dates = args[0]; 
				var date = dates[0];
				var year = date[0], month = date[1], day = date[2];
				
				var txtDate1 = document.getElementById("date1");
				txtDate1.value =year + "/" + month + "/" + day;
			}

			function updateCal1() {
				var txtDate1 = document.getElementById("date1");

				if (txtDate1.value != "") {
					YAHOO.calendar.cal.select(txtDate1.value);
					
					var firstDate = YAHOO.calendar.cal.getSelectedDates()[0];
					YAHOO.calendar.cal.cfg.setProperty("pagedate", (firstDate.getMonth()+1) + "/" + firstDate.getFullYear());
					
					YAHOO.calendar.cal.render();
				}
			}

			// For this example page, stop the Form from being submitted, and update the cal instead
			function handleSubmi1t1(e) {
				updateCal11();
				YAHOO.util.Event.preventDefault(e);
			}
function init1() {
					YAHOO.calendar.cal = new YAHOO.widget.Calendar("cal1","calContainer1", { title:"请选择日期:", close:true } );
					
					YAHOO.calendar.cal.selectEvent.subscribe(handleSelect1, YAHOO.calendar.cal, true);


					YAHOO.calendar.cal.render();

					// Listener to show the 1-up Calendar when the button is clicked
					YAHOO.util.Event.addListener("showup1", "click", YAHOO.calendar.cal.show, YAHOO.calendar.cal, true);
				}

			YAHOO.util.Event.addListener(window, "load", init1);

/*two candeer=============================================================*/


/*three candeer=============================================================*/
function handleSelect2(type,args,obj) {
				var dates = args[0]; 
				var date = dates[0];
				var year = date[0], month = date[1], day = date[2];
				
				var txtDate2 = document.getElementById("date2");
				txtDate2.value =year + "/" + month + "/" + day;
			}

			function updateCal2() {
				var txtDate1 = document.getElementById("date2");

				if (txtDate1.value != "") {
					YAHOO.calendar.cal.select(txtDate1.value);
					
					var firstDate = YAHOO.calendar.cal.getSelectedDates()[0];
					YAHOO.calendar.cal.cfg.setProperty("pagedate", (firstDate.getMonth()+1) + "/" + firstDate.getFullYear());
					
					YAHOO.calendar.cal.render();
				}
			}

			// For this example page, stop the Form from being submitted, and update the cal instead
			function handleSubmi1t2(e) {
				updateCal12();
				YAHOO.util.Event.preventDefault(e);
			}
function init2() {
					YAHOO.calendar.cal = new YAHOO.widget.Calendar("cal2","calContainer2", { title:"请选择日期:", close:true } );
					
					YAHOO.calendar.cal.selectEvent.subscribe(handleSelect2, YAHOO.calendar.cal, true);


					YAHOO.calendar.cal.render();

					// Listener to show the 1-up Calendar when the button is clicked
					YAHOO.util.Event.addListener("showup2", "click", YAHOO.calendar.cal.show, YAHOO.calendar.cal, true);
				}

			YAHOO.util.Event.addListener(window, "load", init2);
/*three candeer=============================================================*/

