/**
 * 
 */
var curTaskId = undefined;
function ruleConfig(rowIndex){
	var rows = $("#tableTask").datagrid('getRows');
	var rowData = rows[rowIndex];
	// 打开窗口
	$('#divEditDialog').dialog({
	    title: "编辑",
	    width: 1000,
	    height: 500,
	    closable: true,
	    resizable : true,
	    maximizable : true,
	    cache: false,
	    buttons:[{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){
				// 表单保存成功，再保存表格数据
				saveData();
			}
	    },{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#divEditDialog').dialog('close');
			}
	    }],
	    onResize :function(width, height){
	    	$('#divContent').accordion('resize');
	    	$('#tableRule').datagrid('resize', {
	            width: width - 30,
	            height: height - 50
	        });
	    	$('#tableEdit').css("width",$('#formEdit').css("width"));
	    },
	    onClose:function(){
	    	$("#formEdit").form('clear');
	    	$('#tableTask').datagrid('reload');
	    }
	});
	curTaskId = rowData.taskId;
	// 填充表单数据
	$('#formEdit').form("load",rowData);
	// 加载表格数据
	loadWbrDataGrid();
}

function loadWbrDataGrid(){
	// 加载表格数据
	$("#tableRule").datagrid(wbrDataGridOp);
	var data = {"taskId":curTaskId};
	$.ajax({
		type:"POST",
		url:"../../mvcrawler/querytaskrulelist.do",
		data:JSON.stringify({"data":data}),
		dataType:"json",
		contentType:"application/json;charset=utf-8;",
		success:function(resp){
			if(!resp){
				return;
			}
			if(resp.retCode == 0){
				$('#tableRule').datagrid('loadData',resp.data);
			}else{
				$.messager.alert('错误消息',resp.retMsg,'error');
			}
		}
	});
}

var types = [{'value':1,'text':'Text'},{'value':2,'text':'List'}];
var matchConditions = 
	[{'value':null,'text':'ALL'},
	 {'value':1,'text':'IN'},
	 {'value':2,'text':'NOT IN'},
	 {'value':3,'text':'>'},
	 {'value':4,'text':'<'},
	 {'value':5,'text':'LIKE'}
    ];

var wbrDataGridOp = {
	iconCls : 'icon-search',
	title : "",
	striped : true,
	autoRowHeight : true,
	loadMsg : "正在加载中...",
	fitColumns : true,
	columns : [ [
	    {field:"ck", checkbox:true},
		{field:"id", title:"ID", hidden:true, align:"center"},
		{field:"taskId", title:"任务ID", hidden:true, align:"center"},
		{field:"fieldName", title:"字段名", width: 6, align:"center", editor:"text"},
		{field:"description", title:"描述", width: 6, align:"center", editor:"text"},
		{field:"rule", title:"提取规则", width: 20, align:"center", editor:"text"},
		{field:"type", title:"数据类型", width: 5, align:"center", 
			editor:{
				type: 'combobox',  
                options: {  
                    data: types,  
                    valueField: "value",  
                    textField: "text",  
                    editable: false,
                    panelHeight:50
                }
			},
			formatter:function(value,rowData,rowIndex){
				if(value == 1){
					return "Text";
				}else if(value == 2){
					return "List";
				}else{
					return "";
				}
			}
		},
		{field:"itemRule", title:"单个值规则", width: 10, align:"center", editor:"text"},
		{field:"allowBlank", title:"是否允许为空", width: 5, align:"center", 
			editor:{
				type: 'checkbox',  
				options:{  
		            on: "1",  
		            off: "0"  
		        }
			},
			formatter:function(value,rowData,rowIndex){
				if(value == 1){
					return "Yes";
				}else if(value == 0){
					return "No";
				}else{
					return "";
				}
			}
		},
		{field:"condition", title:"条件", width: 5, align:"center",
			editor:{
				type: 'combobox',  
                options: {  
                    data: matchConditions,  
                    valueField: "value",  
                    textField: "text",  
                    editable: false,
                    panelHeight:125
                }
			},
			formatter:function(value,rowData,rowIndex){
				if(value == 1){
					return "IN";
				}else if(value == 2){
					return "NOT IN";
				}else if(value == 3){
					return ">";
				}else if(value == 4){
					return "<";
				}else if(value == 5){
					return "LIKE";
				}else{
					return "All";
				}
			}
		},
		{field:"matchValue", title:"值", width: 20, align:"center", editor:"text"},
		{field:"enableFlag", title:"是否开启", width: 10, align:"center",
			formatter:function(value,rowData,rowIndex){
				var iconName = "switch_open";
				if(rowData.enableFlag == 0){
					iconName = "switch_close";
				}
				var opt = '<div style="padding:5px;">';
				opt += "<a id='optBtn_"+rowData.id+"' href='javascript:updateEnableFlag("+rowIndex+","+rowData.id+")' name='"+iconName+"' class='easyui-linkbutton'></a>";
				opt += '</div>';
				return opt;
			}
		}
	] ],
	onDblClickCell:function(index,field,value){
		$(this).datagrid('beginEdit', index);
		var ed = $(this).datagrid('getEditor', {index:index,field:field});
		$(ed.target).focus();
		$("#optBtn_"+index).linkbutton('disable');
	},
	onClickRow:function (rowIndex, rowData) {
        $(this).datagrid('unselectRow', rowIndex);
    },
    onCancelEdit:function(rowIndex,rowData){
    	$("a[name='switch_close']").linkbutton({text:'',plain:true,iconCls:'icon-switch-close'}); 
        $("a[name='switch_open']").linkbutton({text:'',plain:true,iconCls:'icon-switch-open'});
    },
	onLoadSuccess:function(data){ 
		$("a[name='switch_close']").linkbutton({text:'',plain:true,iconCls:'icon-switch-close'}); 
        $("a[name='switch_open']").linkbutton({text:'',plain:true,iconCls:'icon-switch-open'});
        // 自适应宽高
        $('#tableRule').datagrid('resize', {
            width: $("#divEditDialog").width() - 5,
            height: $("#divEditDialog").height() - 10
        });
	}
}

function addRule(){
	$('#tableRule').datagrid('appendRow', {"taskId":curTaskId,"type":1});
	// 新增的行默认开启编辑模式
	var addIndex = $('#tableRule').datagrid('getRows').length - 1
	$('#tableRule').datagrid('beginEdit',addIndex);
}

function saveData(){
	/*$('#formEdit').form({
	    url:"../../mvcrawler/task/update",
	    onSubmit: function(){
			return $('#formEdit').form('validate');
	    },
	    success:function(resp){
	    	if(!resp){
				return;
			}
	    	resp = JSON.parse(resp);
			if(resp.retCode == 0){
				saveRule();
				$('#tableTask').datagrid('reload');
			}else{
				$.messager.alert('错误消息',resp.retMsg,'error');
			}
	    }
	});
	$('#formEdit').form('submit');*/
	var isPass = $('#formEdit').form('validate');
	if(!isPass){
		return;
	}
	var formData = new operate.util.form().getParam("formEdit");
	var data = {"taskConfig":formData};
	$.ajax({
		type:"POST",
		url:"../../mvcrawler/updatetaskconfig.do",
		data:JSON.stringify({"data":data}),
		dataType:"json",
		contentType:"application/json;charset=utf-8;",
		success:function(resp){
			if(!resp){
				return;
			}
			if(resp.retCode == 0){
				saveRule();
				$('#tableTask').datagrid('reload');
				$.messager.alert('提示消息','保存成功','info');
			}else{
				$.messager.alert('错误消息',resp.retMsg,'error');
			}
		}
	});
}

function saveRule(){
	// 结束所有行编辑
	var rows = $("#tableRule").datagrid("getRows");
	for(var i = 0;i<rows.length;i++){
		var row = rows[i];
		var _rowIndex = $("#tableRule").datagrid("getRowIndex", row);
		$("#tableRule").datagrid("endEdit",_rowIndex);
	}
	
	var inserted = $('#tableRule').datagrid('getChanges','inserted');
	var updated = $('#tableRule').datagrid('getChanges','updated');
	if(inserted.length == 0 && updated.length == 0){
		$('#divEditDialog').dialog('close');
		return;
	}
	var data = {"inserted":inserted, "updated":updated};
	// 保存
	$.ajax({
		type:"POST",
		async:false,
		url:"../../mvcrawler/savetaskrule.do",
		data:JSON.stringify({"data":data}),
		dataType:"json",
		contentType:"application/json;charset=utf-8;",
		success:function(resp){
			if(!resp){
				return;
			}
			if(resp.retCode == 0){
				$('#divEditDialog').dialog('close');
			}else{
				$.messager.alert('错误消息',resp.retMsg,'error');
			}
		}
	});
}

function delRule(){
	var rows = $("#tableRule").datagrid("getChecked");
	if(!rows || rows.length == 0){
		return;
	}
	$.messager.confirm('Confirm', '确认删除吗？', function(yes){
		if (!yes){
			return;
		}
		var data = {"taskRules":rows};
		// 删除
		$.ajax({
			type:"POST",
			url:"../../mvcrawler/deletetaskrule.do",
			data:JSON.stringify({"data":data}),
			dataType:"json",
			contentType:"application/json;charset=utf-8;",
			success:function(resp){
				if(!resp){
					return;
				}
				if(resp.retCode == 0){
					loadWbrDataGrid();
				}else{
					$.messager.alert('错误消息',resp.retMsg,'error');
				}
			}
		});
	});
}

function cancelEdit(){
	new operate.util.table().cancelEdit("tableRule");
}

function updateEnableFlag(rowIndex,id){
	var rows = $('#tableRule').datagrid('getRows');
	var rowData = rows[rowIndex];
	// 切换状态
	var iconType = "";
	var flag = rowData.enableFlag;
	if(flag == 0){
		rowData.enableFlag = 1;
		iconType = 'icon-switch-open';
	}else{
		rowData.enableFlag = 0;
		iconType = 'icon-switch-close';
	}
    /*$('#tableRule').datagrid('updateRow',{
    	index: rowIndex,
    	row: rowData
    });*/
	var data = {"id":rowData.id,"enableFlag":rowData.enableFlag};
	$.ajax({
		type:"POST",
		url:"../../mvcrawler/updatetaskruleenableflag.do",
		data:JSON.stringify({"data":data}),
		dataType:"json",
		contentType:"application/json;charset=utf-8;",
		success:function(resp){
			if(!resp){
				return;
			}
			if(resp.retCode != 0){
				$.messager.alert('错误消息',resp.retMsg,'error');
			}
		}
	});
    $("#optBtn_"+id).linkbutton({text:'',plain:true,iconCls:iconType});
}