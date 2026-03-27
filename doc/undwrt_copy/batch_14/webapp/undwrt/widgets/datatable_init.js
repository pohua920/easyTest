/**
 * @author BugLu
 */
if(YAHOO.Claim == undefined) YAHOO.namespace("Claim");
YAHOO.Claim.datatable=[];

STATIC_MSG_EMPTY = i18n.messages.noReports;
YAHOO.widget.DataTable.MSG_EMPTY = STATIC_MSG_EMPTY;
YAHOO.widget.DataTable.MSG_LOADING = i18n.messages.dataLoading;
YAHOO.widget.DataTable.MSG_ERROR = i18n.messages.gainDataError;
/*初始化JSON方式取数的DataTable
 * @param	tbContainer	表格所在的容器element
 * @param	tbColumn	表格的列头,类型YAHOO.widget.ColumnSet
 * @param	tbURL	表格的datasource
 * @param	tbData	JSON对象中数据的位置，如果不使用则为data
 * @return
 */
function initTableJSON(tbContainer,tbColumn,tbURL,tbData){
	var tbColumnSet = new YAHOO.widget.ColumnSet(tbColumn);
	var tbDataSource = new YAHOO.util.DataSource(tbURL);
	var tbFields = [];
	for(sIndex=0;sIndex<tbColumn.length;sIndex++){
		tbFields.push(tbColumn[sIndex].key);
	}
	if(tbData == undefined) tbData = "data";
	tbDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
    tbDataSource.responseSchema = {resultsList: tbData, fields: tbFields};
	YAHOO.Claim.datatable[tbContainer] = new YAHOO.widget.DataTable(
		tbContainer, tbColumnSet, tbDataSource);
}
/*初始化JSArray方式取数的DataTable
 * @param	tbContainer	表格所在的容器element
 * @param	tbColumn	表格的列头,类型YAHOO.widget.ColumnSet
 * @param	tbArray	JSArray
 * @return
 */
function initTableJSArray(tbContainer,tbColumn,tbArray){
	var tbColumnSet = new YAHOO.widget.ColumnSet(tbColumn);
	var tbDataSource = new YAHOO.util.DataSource(tbArray);
	var tbFields = [];
	for(sIndex=0;sIndex<tbColumn.length;sIndex++){
		tbFields.push(tbColumn[sIndex].key);
	}
	//alert(str);
	tbDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;;
    tbDataSource.responseSchema = {fields: tbFields};
	YAHOO.Claim.datatable[tbContainer] = new YAHOO.widget.DataTable(
		tbContainer, tbColumnSet,tbDataSource);
}
