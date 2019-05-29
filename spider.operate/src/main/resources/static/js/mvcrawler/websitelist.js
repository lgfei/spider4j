/**
 * 
 */
$(function() {
	loadDataGrid();
});

function loadDataGrid(){
	// 加载表格数据
	$("#tableWbs").datagrid(dataGridOp);
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
	url:"../../mvcrawler/querywebsitepagelist.do",
	queryParams:queryParams,
	columns : [ [
	    {field:"ck", checkbox:true},
		{field:"id", title:"ID", hidden:true, align:"center", editor:"text"},
		{field:"websiteId", title:"ID", width: 20, align:"center"},
		{field:"name", title:"名称", width: 10, align:"center", editor:"text"},
		{field:"address", title:"地址", width: 30, align:"center", editor:"text"},
		{field:"description", title:"描述", width: 20, align:"center", editor:"text"}
	] ],
	onClickRow: function (rowIndex, rowData) {
        $(this).datagrid('unselectRow', rowIndex);
    },
	onDblClickCell:function(index,field,value){
		$(this).datagrid('beginEdit', index);
		var ed = $(this).datagrid('getEditor', {index:index,field:field});
		$(ed.target).focus();
	},
	onLoadSuccess:function(data){    
        // 自适应宽高
        $('#tableWbs').datagrid('resize', {
            width: $(window).width(),
            height: $(window).height()
        });
	}
};

function add(){
	$('#tableWbs').datagrid('appendRow', {});
	// 新增的行默认开启编辑模式
	var addIndex = $('#tableWbs').datagrid('getRows').length - 1
	$('#tableWbs').datagrid('beginEdit',addIndex);
}

function save(){
	// 结束所有行编辑
	var rows = $("#tableWbs").datagrid("getRows");
	for(var i = 0;i<rows.length;i++){
		var row = rows[i];
		var _rowIndex = $("#tableWbs").datagrid("getRowIndex", row);
		$("#tableWbs").datagrid("endEdit",_rowIndex);
	}
	
	var inserted = $('#tableWbs').datagrid('getChanges','inserted');
	var updated = $('#tableWbs').datagrid('getChanges','updated');
	if(inserted.length == 0 && updated.length == 0){
		$.messager.alert('提示消息','保存成功','info');
		return;
	}
	var data = {"inserted":inserted, "updated":updated};
	// 保存
	$.ajax({
		type:"POST",
		url:"../../mvcrawler/savewebsite.do",
		data:JSON.stringify({"data":data}),
		dataType:"json",
		contentType:"application/json;charset=utf-8;",
		success:function(resp){
			if(!resp){
				return;
			}
			if(resp.retCode == 0){
				$('#tableWbs').datagrid('reload');
				$.messager.alert('提示消息','保存成功','info');
			}else{
				$.messager.alert('错误消息',resp.retMsg,'error');
			}
		}
	});
}

function del(){
	var checked = $("#tableWbs").datagrid("getChecked");
	if(!checked || checked.length == 0){
		return;
	}
	$.messager.confirm('Confirm', '确认删除吗？', function(yes){
		if (!yes){
			return;
		}
		var data = {"websites":checked};
		// 删除
		$.ajax({
			type:"POST",
			url:"../../mvcrawler/deletewebsite.do",
			data:JSON.stringify({"data":data}),
			dataType:"json",
			contentType:"application/json;charset=utf-8;",
			success:function(resp){
				if(!resp){
					return;
				}
				if(resp.retCode == 0){
					$('#tableWbs').datagrid('reload');
				}else{
					$.messager.alert('错误消息',resp.retMsg,'error');
				}
			}
		});
	});
}

function search(){
	var name = $("#inputName").val();
	var data = JSON.parse(queryParams["data"]);
	data["name"] = name;
	queryParams["data"] = JSON.stringify(data);
	$("#tableWbs").datagrid("load",queryParams);
}

function clear(){
	$('#formQueryWbs').form("reset");
}

function cancelEdit(){
	var rows = $("#tableWbs").datagrid("getChecked");
	if(!rows || rows.length == 0){
		return;
	}
	for(var i = 0;i<rows.length;i++){
		var row = rows[i];
		var _rowIndex = $("#tableWbs").datagrid("getRowIndex", row);
		// 取消编辑
		$("#tableWbs").datagrid("cancelEdit",_rowIndex);
		// 取消勾选
		$("#tableWbs").datagrid("uncheckRow",_rowIndex);
		// 取消选中
		$("#tableWbs").datagrid("unselectRow",_rowIndex);
	}
}
