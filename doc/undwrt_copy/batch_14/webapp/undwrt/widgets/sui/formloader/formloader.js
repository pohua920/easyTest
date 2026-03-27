
YAHOO.namespace('SINOSOFT.util'); 
YAHOO.SINOSOFT.util.YUILoader=function(o){
	YAHOO.SINOSOFT.util.YUILoader.superclass.constructor.call(this,o);
	this.charset="UTF-8";
	this.addModule = function(o) {
        if (!o || !o.name || !o.type || (!o.path && !o.fullpath)) {
            return false;
        }
        o.requires = o.requires || [];
        this.moduleInfo[o.name] = o;
        this.dirty = true;
        return true;
    };
	this.base=contextRootPath+'/widgets/yui2/';
	this.addModule( {
		name : 'calendarwidget',
		type : 'js',
		requires:['calendar','container','datasource'],
		path : '../sui/calendar/calendarwidget.js',
		fullpath : contextRootPath+'/widgets/sui/calendar/calendarwidget.js',
		optional:['dragdrop', 'animation']
	});
	this.addModule( {
		name : 'autocompletewidget',
		type : 'js',
		requires:['datasource','container','autocomplete'],
		path : '../sui/autocomplete/autocompletewidget.js',
		fullpath : contextRootPath+'/widgets/sui/autocomplete/autocompletewidget.js',
		optional:['dragdrop', 'animation']
	});
	this.addModule({
	    name:'sinosoft',
	    type:'js',
	    path:'../sui/common/sinosoft.js',
	    fullpath:contextRootPath+'/widgets/sui/common/sinosoft.js'
	  }); 
	this.addModule({
	    name:'common',
	    type:'js',
	    path:'../sui/common/common.js',
	    fullpath:contextRootPath+'/widgets/sui/common/common.js',
	    requires:['sinosoft']
	  }); 
	this.addModule({
		 name:'querywidget',
		 type:'js',
		 path:'../sui/query/querywidget.js',
		 fullpath:contextRootPath+'/widgets/sui/query/querywidget.js',
		 requires:['connection', 'json', 'datasource', 'datatable',
					'container', 'paginator', 'common'],
	     optional:['dragdrop', 'animation'],
	     skinnable:true,
	     pkg : '../sui/query'
	});
	this.addModule({
		name:'submitdialogwidget',
		type:'js',
		path:'../sui/dialog/submitdialogwidget.js',
		fullpath:contextRootPath+'/widgets/sui/dialog/submitdialogwidget.js',
		requires:['container']
	});
	this.addModule({
		name:'mullinecss',
		type:'css',
		path:'../sui/mulline/mulinewidget.css',
		fullpath:contextRootPath+'/widgets/sui/mulline/mulinewidget.css'
	});	
	this.addModule({
		name:'mullinewidget',
		type:'js',
		path:'../sui/mulline/mullinewidget.js',
		fullpath:contextRootPath+'/widgets/sui/mulline/mullinewidget.js',
		requires:['mullinecss']
	});	
	this.addModule({
		name:'spinwidgetcss',
		type:'css',
		path:'../sui/spin/spinwidget.css',
		fullpath:contextRootPath+'/widgets/sui/spin/spinwidget.css'  
	});
	this.addModule({
		name:'spinwidget',
		type:'js',
		path:'../sui/spin/spinwidget.js',
		fullpath:contextRootPath+'/widgets/sui/spin/spinwidget.js',
		requires:['spinwidgetcss']
	});
	this.addModule({
		name:'timewidget',
		type:'js',
		path:'../sui/time/timewidget.js',
		fullpath:contextRootPath+'/widgets/sui/time/timewidget.js',
		requires:['spinwidget'],
		after:['spinwidget']
	});
	this.addModule({
		name:'calendardatetime',
		type:'js',
		path:'../sui/calendardatetime/calendardatetime.js',
		fullpath:contextRootPath+'/widgets/sui/calendardatetime/calendardatetime.js',
		requires:['calendar','container','datasource','timewidget']
	});
	this.addModule({
		name:'calendartimewidget',
		type:'js',
		path:'../sui/calendartime/calendartimewidget.js',
		fullpath:contextRootPath+'/widgets/sui/calendartime/calendartimewidget.js',
		requires:['calendardatetime','container','datasource']
	});
	this.addModule({
		name:'showhidecss',
		type:'css',
		path:'../sui/showhide/showhide.css',
		fullpath:contextRootPath+'/widgets/sui/showhide/showhide.css'
	});
	this.addModule({
		name:'showhidewidget',
		type:'js',
		path:'../sui/showhide/showhide.js',
		fullpath:contextRootPath+'/widgets/sui/showhide/showhide.js',
		requires:['showhidecss','animation']
	});
	this.addModule({
		name:'basiccss',
		type:'css',
		path:'../sui/basic/basic.css',
		fullpath:contextRootPath+'/widgets/sui/basic/basic.css',
		after:['reset-fonts-grids']
	});
	this.addModule({
		name:'showdialogwidget',
		type:'js',
		path:'../sui/dialog/showdialogwidget.js',
		fullpath:contextRootPath+'/widgets/sui/dialog/showdialogwidget.js',
		requires:['container','dragdrop']
	});	
	this.skin.overrides = {
		 querywidget:['skin']
    };
	this.comboBase = contextRootPath + "/requestCombo?";   
	this.root = '/widgets/yui2/';
	this.combine=true;
};
YAHOO.lang.extend(YAHOO.SINOSOFT.util.YUILoader,YAHOO.util.YUILoader);