var myColumnHeaders = [
    {key:"序号", sortable:true, resizeable:true},
    {key:"零部件名称", type:"html", sortable:true, resizeable:true},
    {key:"人保编码", type:"html", sortable:true, resizeable:true},
    {key:"原厂编码", type:"html", sortable:true, resizeable:true},
    {key:"数量", type:"currency", sortable:true, resizeable:true},
	{key:"价格类别", type:"currency", sortable:true, resizeable:true},
	{key:"初报价", type:"html", sortable:true, resizeable:true},
	{key:"折残金额", type:"html", sortable:true, resizeable:true},
	{key:"定损价格", type:"html", sortable:true, resizeable:true},
	{key:"回收", type:"html", sortable:true, resizeable:true},
	{key:"操作", type:"html", sortable:true, resizeable:true}
];
var myColumnSet = new YAHOO.widget.ColumnSet(myColumnHeaders);
var myDataSource = new YAHOO.util.DataSource(Data2.tableContent);
myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
myDataSource.responseSchema = {
    fields: ["序号","零部件名称","人保编码","原厂编码","数量","价格类别","初报价","折残金额","定损价格","回收","操作"]
};

var myDataTable = new YAHOO.widget.DataTable("tabber1",myColumnSet,myDataSource,{caption:"",sortedBy:{colKey:"序号",dir:"asc"}});
var onColumnSort = function(oArgs) {
    var column = oArgs.column;
    var dir = oArgs.dir;
};
myDataTable.subscribe("columnSortEvent",onColumnSort);