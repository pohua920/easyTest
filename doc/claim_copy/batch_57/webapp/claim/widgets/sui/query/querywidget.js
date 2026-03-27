YAHOO.namespace("SINOSOFT.widget");
(function(){
	var QueryWidget=function(formInfo,requestURL,container,page_responseSchema,page_contentColumnHeaders){
		this.formInfo=formInfo;
		this.requestURL=requestURL;
		this.container=container;
		this.page_responseSchema=page_responseSchema;
		this.page_contentColumnHeaders=page_contentColumnHeaders;
	}

	QueryWidget.prototype.executeQuery=function () {
		var CONTIANER_PREFIX="paginator_";
		var CONTIANER_SUFFIX_TOP="_top";
		var CONTIANER_SUFFIX_BOTTOM="_bottom";
		var myDataSource = new YAHOO.util.DataSource(this.requestURL);
		var me=this;
		myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.connMgr.setForm(this.formInfo);
		myDataSource.responseSchema = this.page_responseSchema;
		myDataSource.connMethodPost = true;
		var myColumnSet = new YAHOO.widget.ColumnSet(this.page_contentColumnHeaders);
		var paginatorContainers=[CONTIANER_PREFIX+this.container+CONTIANER_SUFFIX_TOP,CONTIANER_PREFIX+this.container+CONTIANER_SUFFIX_BOTTOM];
		var myConfig = {
			//dynamicData : true,
			paginator : new YAHOO.widget.Paginator( {
				rowsPerPage : 10,
				rowsPerPageOptions: [10,25,50,100], 
				containers:paginatorContainers,
				firstPageLinkLabel : "<<",
				lastPageLinkLabel : ">>", 
				template:"{FirstPageLink}{PreviousPageLink} <strong>{CurrentPageReport}</strong> {NextPageLink}{LastPageLink}"
			})
		};

		contentDataTable = new YAHOO.widget.DataTable(this.container, myColumnSet,myDataSource, myConfig);
		contentDataTable.handleDataReturnPayload = function(oRequest, oResponse,oPayload) {
			if(oResponse.meta.totalRecords)
			oPayload.totalRecords = oResponse.meta.totalRecords;
			return oPayload;
		};
	};
	YAHOO.SINOSOFT.widget.QueryWidget=QueryWidget;
	
})();
YAHOO.register("QueryWidget", YAHOO.SINOSOFT.widget.QueryWidget, {version: "2.8.0r4", build: "2449"});
