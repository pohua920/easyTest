YAHOO.namespace("YAHOO.SINOSOFT");
(function() {
	var Event = YAHOO.util.Event, Dom = YAHOO.util.Dom;
	var WorkFlowChart = function(jsonArray,nodeType) {
		WorkFlowChart.prototype.init.call(this, jsonArray,nodeType);
	};
	WorkFlowChart.prototype = {
		height : null,
		width : null,
		nodeType:"history",
		x : null,
		y : null,
		data : null,
		el : null,
		containerId : null,
 		_taskTableDialog : null,
		_dataTable : null,
		_dataSource : null,
		_columnDef : null,
		init : function(jsonArray,nodeType) {
			this.nodeType=nodeType;
			this.judgeConfig("height", jsonArray);
			this.judgeConfig("width", jsonArray);
			this.judgeConfig("x", jsonArray);
			this.judgeConfig("y", jsonArray);
			this.judgeConfig("data", jsonArray);
			function page_formatDateTime(elCell, oRecord, oColumn, oData) {
				if (oData !== null && "time" in oData) {
					var value = oData;
					var date = new Date(oData.time);
					value = date.getFullYear() + "-" + (date.getMonth() + 1)
							+ "-" + date.getDate() + "&nbsp;&nbsp;"
							+ date.getHours() + ":" + date.getMinutes() + ":"
							+ date.getSeconds();
					elCell.innerHTML = value;
				}
			}
			function page_formatLink(elCell,oRecord,oColumn,oData){
				if(oColumn.key === "operator"){
					var id=oRecord._oData.taskId;
					var nodeName=oRecord._oData.nodeName;
					var url=contextRootPath+"/simpleclaim/task/processTask.do?taskId="+id;
					elCell.innerHTML = "<a href=\""+url+"\" >处理"+nodeName+"任务</a>";
				}
			}
			this._dataSource = new YAHOO.util.DataSource(this.data);
			this._dataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
			this._dataSource.responseSchema = {
				fields : [ "taskId","nodeName", "description", "actorId", "creat",
						"start", "end", "dueDate", "priority" ]
			};
			this._columnDef = [ {
				key : "nodeName",
				label : "节点名",
				sortable : true,
				resizeable : true
			}, {
				key : "actorId",
				label : "执行者",
				sortable : true,
				resizeable : true
			}, {
				key : "start",
				label : "开始时间",
				formatter : page_formatDateTime,
				sortable : true,
				resizeable : true
			}, {
				key : "end",
				label : "结束时间",
				formatter : page_formatDateTime,
				resizeable : true
			}, {
				key : "priority",
				label : "优先级",
				resizeable : true
			} ];
			//增加“当前节点”的列显示
			if(this.nodeType==="current"){
				var linkColumn={
					key:"operator",					
					label:"操作",
					formatter:page_formatLink,
					resizeable : true
				};
				this._columnDef.push(linkColumn);
			}
		},
		run : function() {
			this._initDivOverLay();
		},
		_initDivOverLay : function() {
			this.el = document.createElement("DIV");
			this.el.className = this.nodeType+"Node";
			this.el.style.position = "absolute";
			this.el.style.width = (this.width) + "px";
			this.el.style.height = (this.height) + "px";
			this.el.style.left = this.x + "px";
			this.el.style.top = this.y + "px";
			Dom.get("chartMain").appendChild(this.el);
			this._addDivEvent();
		},
		_addDivEvent : function() {
			Event.on(this.el, 'mouseover', this._divMouseOver, this, true);
			Event.on(this.el, 'mouseout', this._divMouseOut, this, true);
			Event.on(this.el, 'click', this._divClick, this, true);
		},
		_divMouseOut : function() {
			this.el.className = this.nodeType+"Node";
			this.el.innerHTML = "";
		},
		_divMouseOver : function() {
			this.el.className = this.nodeType+"_node";
			this.el.innerHTML = "點選查看任务列表";
		},
		_divClick : function() {
			this.el.className = this.nodeType+"_node_phrase";
			
			if (this._taskTableDialog !== null) {
				this._taskTableDialog.hide();
			}
			this._initDateTableDialog();
		},
		_initDateTableDialog : function() {
			this.containerId = this.width + "_" + this.height;
			if (this._taskTabelDialog == null) {
				this._taskTableDialog = new YAHOO.widget.Dialog(
						this.containerId, {
							context : [ this.el, "tl", "tl" ],
							visible : false,
							draggable : false,
							close : true
						});
				this._taskTableDialog.setHeader("TaskList");
				this._taskTableDialog.setBody('<div id="dataTableContainer_'
						+ this.containerId + '"><DIV id=\"dataTable_'
						+ this.containerId
						+ '\"></DIV><br/><DIV id=\"dataTableInfo_'
						+ this.containerId
						+ '\" class=\"dataTableInfo\"></DIV></DIV>');
				var EscKey = new YAHOO.util.KeyListener(document, {
					keys : 27
				}, {
					fn : this._taskTableDialog.hide,
					scope : this._taskTableDialog,
					correctScope : true
				}, "keyup");
				this._taskTableDialog.cfg.queueProperty("keylisteners", EscKey);
				this._taskTableDialog.render(document.body);
				this._initDataTable();
			}
			this._taskTableDialog.show();

		},
		_initDataTable : function() {
			this._dataTable = new YAHOO.widget.DataTable("dataTable_"
					+ this.containerId, this._columnDef, this._dataSource);
			this._addDataTableDialogEvent();
		},
		_addDataTableDialogEvent : function() {
			this._dataTable.subscribe("rowMouseoverEvent",
					this._dataTable.onEventHighlightRow);
			this._dataTable.subscribe("rowMouseoutEvent",
					this._dataTable.onEventUnhighlightRow);
			this._dataTable.subscribe("rowClickEvent", this._onEventSelectRow,
					this, true);
		},
		_onEventSelectRow : function(oArgs) {
			var showInfoId = "dataTableInfo_" + this.containerId;
			var showEl = Dom.get(showInfoId);
			var target = oArgs.target;
			var record = this._dataTable.getRecord(target);
			showEl.innerHTML = this._construtorInfo(record);
		},
		_construtorInfo : function(record) {
			var str = "<table class=\"dataTabelInfo_detailTable\"><thead><tr><td width=\"50%\">名称</td><td width=\"50%\">内容</td></tr></thead>";
			str += this._construtorInfoUnit("节点名", record, "nodeName");
			str += this._construtorInfoUnit("描述", record, "description");
			str += this._construtorInfoUnit("执行者", record, "actorId");
			str += this._construtorInfoUnit("创建时间", record, "creat");
			str += this._construtorInfoUnit("开始时间", record, "start");
			str += this._construtorInfoUnit("结束时间", record, "end");
			str += this._construtorInfoUnit("过期时间", record, "dueDate");
			str += this._construtorInfoUnit("优先级", record, "priority");
			//增加“当前节点”的详细 信息显示
			if(this.nodeType==="current"){
				var id=record.getData("taskId");
				var nodeName=record.getData("nodeName");
				var url=contextRootPath+"/simpleclaim/task/processTask.do?taskId="+id;
				var innerHTMLStr = "<a href=\""+url+"\" >处理"+nodeName+"任务</a>";
				str+="<tr><td>操作</td><td>" + innerHTMLStr
				+ "</td></tr>";
			}
			str += "</table>";
			return str;
		},
		_construtorInfoUnit : function(name, record, field) {
			var data = record.getData(field);
			if (data != null) {
				if (typeof (data) === "object") {
					return "<tr><td>" + name + "</td><td>"
							+ this._parseDate(data) + "</td></tr>";
				} else {
					return "<tr><td>" + name + "</td><td>" + data
							+ "</td></tr>";
				}
			} else {
				return "<tr><td>" + name + "</td><td>" + " " + "</td></tr>";
			}
		},
		_parseDate : function(oDate) {
			if (oDate !== null && "time" in oDate) {
				var date = new Date(oDate.time);
				var value = date.getFullYear() + "-" + (date.getMonth() + 1)
						+ "-" + date.getDate() + "&nbsp;&nbsp;"
						+ date.getHours() + ":" + date.getMinutes() + ":"
						+ date.getSeconds();
				return value;
			} else {
				return "";
			}
		},
		judgeConfig : function(attr, obj) {
			if (attr in obj) {
				this[attr] = obj[attr];
			}
		}

	};
	YAHOO.SINOSOFT.WorkFlowChart = WorkFlowChart;
})();