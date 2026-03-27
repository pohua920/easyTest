/**
 * @author BugLu
 */
if(YAHOO.Claim == undefined) YAHOO.namespace("Claim");
YAHOO.Claim.tabview = [];
function initTabViewByScript(elTabView,elTabs){
	//´´½¨TableView	
	YAHOO.Claim.tabview[elTabView] = new YAHOO.widget.TabView(elTabView);
	if(!YAHOO.lang.isArray(elTabs)) return;
	for(tabIndex=0;tabIndex<elTabs.length;tabIndex++){
		YAHOO.Claim.tabview[elTabView].addTab(new YAHOO.widget.Tab(elTabs[tabIndex]));
	}
	YAHOO.Claim.tabview[elTabView].appendTo(elTabView); 
}