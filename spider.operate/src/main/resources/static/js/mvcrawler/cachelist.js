/**
 * 
 */
$(function() {
	loadDataGrid();
});

function loadDataGrid(){
	// 加载表格数据
	$("#tableMvCache").datagrid(dataGridOp);
}

/**
 * 页面加载默认查询的参数
 */
var queryParams = Constants.queryParams;

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
	url:"../../mvcrawler/querymvcachepagelist.do",
	queryParams:queryParams,
	columns : [ [
	    {field:"ck", checkbox:true},
		{field:"mvId", title:"ID", hidden:true, align:"center"},
		{field:"mvInfo", title:"影片信息", width: 10, align:"center",
			formatter:function(value,rowData,rowIndex){ 
				var div = [];
				div.push('<div style="float: left;text-align: left;">');
				if(rowData.poster){
					div.push('<img style="width:50px;height:70px;border: 0px;float: left;" src="'+ rowData.poster +'">');
				}
				div.push('<b>'+ rowData.name +'</b>');
				div.push('<p class="title h6 text-gray" title ="'+rowData.mvId+'">ID:'+ rowData.mvId +'</p>');
				div.push('</div>');
				return div.join(" ");
            }
		},
		{field:"name", title:"名称", width: 10, align:"center", editor:"text"},
		{field:"player", title:"主演", width: 10, align:"center", editor:"text"},
		{field:"director", title:"导演", width: 10, align:"center", editor:"text"},
		{field:"showDate", title:"上映日期", width: 10, align:"center", editor:"text"},
		{field:"brief", title:"简介", width: 10, align:"center", editor:"text"},
		{field:"details", title:"剧情", width: 10, align:"center", editor:"text"}
	] ],
	onClickRow: function (rowIndex, rowData) {
        $(this).datagrid('unselectRow', rowIndex);
    },
	onLoadSuccess:function(data){    
        // 自适应宽高
        $('#tableMvCache').datagrid('resize', {
            width: $(window).width(),
            height: $(window).height()
        });
	}
};

function search(){
	var mvId = $("#inputMvId").val() ? $("#inputMvId").val().trim() : null;
	var name = $("#inputMvName").val() ? $("#inputMvName").val().trim() : null;
	var data = JSON.parse(queryParams["data"]);
	data["mvId"] = mvId;
	data["name"] = name;
	queryParams["data"] = JSON.stringify(data);
	$("#tableMvCache").datagrid("load",queryParams);
}

function clear(){
	$('#formQueryMvCache').form("reset");
}

function sync(flag){
	$.messager.confirm('Confirm', '确认同步吗？', function(yes){
		if (!yes){
			return;
		}
		var mvIds = [];
		if(flag != 'all'){
			var rows = $("#tableMvCache").datagrid("getChecked");
			if(!rows || rows.length == 0){
				$.messager.alert('提示消息','您还未选择要同步的数据','info');
				return;
			}
			$.each(rows,function(i,item){
				mvIds.push(item["mvId"]);
			});
		}
		var data = {"mvIds":mvIds};
		// 同步
		$.ajax({
			type:"POST",
			url:"../../mvcrawler/syncmvcache.do",
			data:JSON.stringify({"data":data}),
			dataType:"json",
			contentType:"application/json;charset=utf-8;",
			success:function(resp){
				if(!resp){
					return;
				}
				if(resp.retCode == 0){
					loadDataGrid();
				}else{
					$.messager.alert('错误消息',resp.retMsg,'error');
				}
			}
		});
	});
}

function del(flag){
	$.messager.confirm('Confirm', '确认删除吗？', function(yes){
		if (!yes){
			return;
		}
		var mvIds = [];
		if(flag != 'all'){
			var rows = $("#tableMvCache").datagrid("getChecked");
			if(!rows || rows.length == 0){
				$.messager.alert('提示消息',"您还未选择要删除的数据",'info');
				return;
			}
			$.each(rows,function(i,item){
				mvIds.push(item["mvId"]);
			});
		}
		var data = {"mvIds":mvIds};
		// 删除
		$.ajax({
			type:"POST",
			url:"../../mvcrawler/deletemvcache.do",
			data:JSON.stringify({"data":data}),
			dataType:"json",
			contentType:"application/json;charset=utf-8;",
			success:function(resp){
				if(!resp){
					return;
				}
				if(resp.retCode == 0){
					loadDataGrid();
				}else{
					$.messager.alert('错误消息',resp.retMsg,'error');
				}
			}
		});
	});
}
