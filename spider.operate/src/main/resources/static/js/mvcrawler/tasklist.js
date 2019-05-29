/**
 * 
 */
$(function() {
	loadDataGrid();
});

function loadDataGrid(){
	// 加载表格数据
	$("#tableTask").datagrid(dataGridOp);
}

/**
 * 页面加载默认查询的参数
 */
var queryParams = Constants.queryParams;
/**
 * 网站信息表格参数
 */
var dataGridOp = {
	iconCls : 'icon-search',
	title : "",
	pagination : true,
	pageSize : Constants.pageSize,
	pageList : Constants.pageList,
	striped : true,
	autoRowHeight : true,
	loadMsg : "正在加载中...",
	fitColumns : true,
	url:"../../mvcrawler/querytaskconfiglist.do",
	queryParams:queryParams,
	columns : [ [
	    {field:"ck", checkbox:true},
		{field:"id", title:"ID", hidden:true, align:"center"},
		{field:"websiteName", title:"网站", width: 10, align:"center"},
		{field:"taskName", title:"任务名称", width: 20, align:"center"},
		{field:"description", title:"描述", width: 20, align:"center"},
		/*{field:"target", title:"目标", width: 20, align:"center"},*/
		/*{field:"regex", title:"正则", width: 20, align:"center"},*/
		{field:"mode", title:"模式", width: 10, align:"center",
			formatter:function(value,rowData,rowIndex){
				if(value == 1){
					return "详情";
				}else if(value == 2){
					return "列表";
				}else{
					return "";
				}
			}
		},
		/*{field:"targetArea", title:"目标区域", width: 10, align:"center"},*/
		{field:"minPagenum", title:"最小页码", width: 8, align:"center"},
		{field:"maxPagenum", title:"最大页码", width: 8, align:"center"},
		{field:"pageSize", title:"每页内容数", width: 8, align:"center"},
		/*{field:"specials", title:"特例", width: 20, align:"center"},*/
		/*{field:"rules", title:"规则", width: 10, align:"center",
			formatter:function(value,rowData,rowIndex){
				return "<a href='javascript:ruleConfig("+rowIndex+")'>爬取规则配置</a>";
			}
		},*/
		{field:"runStatus", title:"运行状态", width: 10, align:"center",
			formatter:function(value,rowData,rowIndex){
				if(value == 1){
					return "待启动";
				}else if(value == 2){
					return "已启动";
				}else if(value == 3){
					return "正在运行";
				}else if(value == 4){
					return "正在停止";
				}else if(value == 5){
					return "已停止";
				}else{
					return "";
				}
			}
		},
		{field:"opt", title:"操作", width: 20, align:"center",
			formatter:function(value,rowData,rowIndex){
				var opt = '<div style="padding:5px;">';
				opt += "<a href='javascript:clone("+JSON.stringify(rowData.taskId)+")' name='clone' class='easyui-linkbutton'></a>";
				if(rowData.runStatus == 1 || rowData.runStatus == 5){
					opt += "<a href='javascript:start("+JSON.stringify(rowData.taskId)+")' name='start' class='easyui-linkbutton'></a>";
					opt += "<a href='javascript:ruleConfig("+rowIndex+")' name='setting' class='easyui-linkbutton'></a>";
				}else if(rowData.runStatus == 2 || rowData.runStatus == 3){
					opt += "<a href='javascript:stop("+JSON.stringify(rowData.taskId)+")' name='stop' class='easyui-linkbutton'></a>";
				}
				opt += '</div>';
				return opt;
			}
		}
	] ],
	onBeforeLoad:function(param){
		$("#divAddDialog").hide();
		$("#divEditDialog").hide();
	},
	onClickRow: function (rowIndex, rowData) {
        $(this).datagrid('unselectRow', rowIndex);
    },
	onLoadSuccess:function(data){    
        $("a[name='start']").linkbutton({text:'启动',plain:true,iconCls:'icon-start'}); 
        $("a[name='stop']").linkbutton({text:'停止',plain:true,iconCls:'icon-stop'});
        $("a[name='setting']").linkbutton({text:'规则配置',plain:true,iconCls:'icon-setting'});
        $("a[name='clone']").linkbutton({text:'克隆',plain:true,iconCls:'icon-clone'});
        // 自适应宽高
        $('#tableTask').datagrid('resize', {
            width: $(window).width(),
            height: $(window).height()
        });
	}
};

function add(){
	$('#divAddDialog').dialog({
	    title: "新增",
	    width: 500,
	    height: 500,
	    closable: true,
	    cache: false,
	    buttons:[{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){
				save();
			}
	    },{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#divAddDialog').dialog('close');
			}
	    }],
	    onClose:function(){
	    	$("#formAdd").form('clear');
	    }
	});	
}

function save(){
	/*$('#formAdd').form({
	    url:"../../mvcrawler/task/add",
	    onSubmit: function(){
			return $('#formAdd').form('validate');
	    },
	    success:function(resp){
	    	if(!resp){
				return;
			}
	    	resp = JSON.parse(resp);
			if(resp.retCode == 0){
				$('#divAddDialog').dialog('close');
		    	$('#tableTask').datagrid('reload');
			}else{
				$.messager.alert('错误消息',resp.retMsg,'error');
			}
	    }
	});
	$('#formAdd').form('submit');*/
	var isPass = $('#formAdd').form('validate');
	if(!isPass){
		return;
	}
	var formData = new operate.util.form().getParam("formAdd");
	var data = {"taskConfig":formData};
	$.ajax({
		type:"POST",
		url:"../../mvcrawler/createtaskconfig.do",
		data:JSON.stringify({"data":data}),
		dataType:"json",
		contentType:"application/json;charset=utf-8;",
		success:function(resp){
			if(!resp){
				return;
			}
			if(resp.retCode == 0){
				$('#divAddDialog').dialog('close');
		    	$('#tableTask').datagrid('reload');
			}else{
				$.messager.alert('错误消息',resp.retMsg,'error');
			}
		}
	});
}

function del(){
	var checked = $("#tableTask").datagrid("getChecked");
	if(!checked || checked.length == 0){
		return;
	}
	$.messager.confirm('Confirm', '确认删除吗？', function(yes){
		if (!yes){
			return;
		}
		var data = {"taskConfigs":checked};
		// 删除
		$.ajax({
			type:"POST",
			url:"../../mvcrawler/deletetaskconfig.do",
			data:JSON.stringify({"data":data}),
			dataType:"json",
			contentType:"application/json;charset=utf-8;",
			success:function(resp){
				if(!resp){
					return;
				}
				if(resp.retCode == 0){
					$('#tableTask').datagrid('reload');
				}else{
					$.messager.alert('错误消息',resp.retMsg,'error');
				}
			}
		});
	});
}

function search(){
	var taskName = $("#inputTaskName").val();
	var data = JSON.parse(queryParams["data"]);
	data["taskName"] = taskName;
	queryParams["data"] = JSON.stringify(data);
	$("#tableTask").datagrid("load",queryParams);
	
}

function clear(){
	$('#formQueryTask').form("reset");
}

function start(taskId){
	if(!taskId){
		return;
	}
	var data = {"taskId":taskId};
	$.ajax({
		type:"POST",
		url:"../../mvcrawler/starttask.do",
		data:JSON.stringify({"data":data}),
		dataType:"json",
		contentType:"application/json;charset=utf-8;",
		success:function(resp){
			if(!resp){
				return;
			}
			if(resp.retCode == 0){
				$('#tableTask').datagrid('reload');
			}else{
				$.messager.alert('错误消息',resp.retMsg,'error');
			}
		}
	});
}

function stop(taskId){
	if(!taskId){
		return;
	}
	var data = {"taskId":taskId};
	$.ajax({
		type:"POST",
		url:"../../mvcrawler/stoptask.do",
		data:JSON.stringify({"data":data}),
		dataType:"json",
		contentType:"application/json;charset=utf-8;",
		success:function(resp){
			if(!resp){
				return;
			}
			if(resp.retCode == 0){
				$('#tableTask').datagrid('reload');
			}else{
				$.messager.alert('错误消息',resp.retMsg,'error');
			}
		}
	});
}

function clone(taskId){
	if(!taskId){
		return;
	}
	var data = {"taskId":taskId};
	$.ajax({
		type:"POST",
		url:"../../mvcrawler/clonetask.do",
		data:JSON.stringify({"data":data}),
		dataType:"json",
		contentType:"application/json;charset=utf-8;",
		success:function(resp){
			if(!resp){
				return;
			}
			if(resp.retCode == 0){
				$('#tableTask').datagrid('reload');
			}else{
				$.messager.alert('错误消息',resp.retMsg,'error');
			}
		}
	});
}
