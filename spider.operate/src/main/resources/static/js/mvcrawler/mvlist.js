/**
 * 
 */
// 是否有新增行的标识
var adding = false;

$(function() {
	loadDataGrid();
});

function loadDataGrid(){
	// 加载表格数据
	$("#tableMv").datagrid(dataGridOp);
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
	url:"../../mvcrawler/querymvpagelist.do",
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
		{field:"details", title:"剧情", width: 10, align:"center", editor:"text"},
		{field:"createdTime", title:"入库时间", width: 10, align:"center",
			formatter:function(value){ 
				if(value){
					return  new Date(value).format('yyyy-MM-dd hh:mm:ss');
				}
            } 
		},
		{field:"updatedTime", title:"最后修改时间", width: 10, align:"center",
			formatter:function(value){  
				if(value){
					return  new Date(value).format('yyyy-MM-dd hh:mm:ss');
				}
            }
		}
	] ],
	view: detailview,  
    detailFormatter: function(value, row){  
        return  '<table id="tableMvSource_'+row.mvId+'" class="easyui-datagrid"></table>';         
    },  
    onExpandRow: function(pindex,prow){
    	var tableMvSource = $(this).datagrid('getRowDetail',pindex).find('#tableMvSource_'+prow.mvId);  
    	tableMvSource.datagrid({  
            url:"../../mvcrawler/querymvsourcelist.do",  
            fitColumns:false,  
            singleSelect:true,  
            rownumbers:false,  
            height:'auto',
            queryParams:{data:'{"mvId":"'+prow.mvId+'"}'},
            columns:[[  
                {field: 'sourceId', title:"源ID", width: '20%', align: 'center'},
        		{field : "websiteId",title : "网站",width : '20%',align : "center",
                	formatter: function (value, rowData, rowIndex) {
                		if(!rowData.editing){
                			return rowData.websiteName;
                		}
                	},
                	editor : {
    					type:'combobox',
    					options:{
    						valueField:'websiteId',textField:'name',required:true,
    						method:'post',editable:false,multiple:false,data:getWebsites()
    					}
    				}
        		},
                {field: 'sourceUrl', title:"地址", width: '40%', align: 'center', editor:"text",
                	formatter:function(value){  
                		return '<a href="'+value+'" target="_blank">'+value+'</a>';
                    }
                },
                {field: 'operate', title:"操作", width: '20%', align: 'center',
                	formatter: function (value, rowData, rowIndex) { 
        				var websiteId = rowData.websiteId;
        				if(!websiteId){
        					adding = true;
        				}
        				if(rowData.editing){
        					var save = "<a href='javascript:saveRow("+JSON.stringify(prow.mvId)+","+JSON.stringify(rowData)+","+rowIndex+")'>保存</a> | ";
        					var cacel = "<a href='javascript:cacelEditSource("+JSON.stringify(prow.mvId)+","+rowIndex+")'>取消</a>";
        					return save+cacel;
        				}else{
        					var edit = "<a href='javascript:editRow("+JSON.stringify(prow.mvId)+","+rowIndex+")'>编辑</a> | ";
        					var del = "<a href='javascript:delRow("+JSON.stringify(prow.mvId)+","+JSON.stringify(rowData.sourceId)+","+rowIndex+")'>删除</a> | ";
        					var add = "<a href='javascript:addRow("+JSON.stringify(prow.mvId)+")'>加行</a>";
        					return edit+del+add;
        				}
                    }
                }
            ]],
            onLoad:function(){  
            	$("#tableMv").datagrid('fixDetailRowHeight',pindex);  
                $("#tableMv").datagrid('selectRow',pindex);  
                $("#tableMv").datagrid('getRowDetail',pindex).find('form').form('load',prow);  
                $("#tableMv").datagrid('fixDetailRowHeight',pindex);  
                $("#tableMv").datagrid('fixRowHeight', pindex);    
            },  
	        onResize:function(){  
	            $("#tableMv").datagrid('fixDetailRowHeight',pindex);
	        },  
	        onLoadSuccess:function(){  
	            setTimeout(function(){  
	                $("#tableMv").datagrid('fixDetailRowHeight',pindex);  
	                $("#tableMv").datagrid('fixRowHeight',pindex);//防止出现滑动条   
	            },0);
	        },
	        onClickRow: function (rowIndex, rowData) {
                $(this).datagrid('unselectRow', rowIndex);
            },
            onBeforeEdit:function(index,rowData){rowData.editing = true;$("#tableMvSource_" + prow.mvId).datagrid("refreshRow", index);},
        	onAfterEdit:function(index,rowData){rowData.editing = false;$("#tableMvSource_" + prow.mvId).datagrid("refreshRow", index);},
        	onCancelEdit:function(index,rowData){rowData.editing = false;$("#tableMvSource_" + prow.mvId).datagrid("refreshRow", index);}
        });
    },
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
        $('#tableMv').datagrid('resize', {
            width: $(window).width(),
            height: $(window).height()
        });
	}
};

function save(){
	// 结束所有行编辑
	var rows = $("#tableMv").datagrid("getRows");
	for(var i = 0;i<rows.length;i++){
		var row = rows[i];
		var _rowIndex = $("#tableMv").datagrid("getRowIndex", row);
		$("#tableMv").datagrid("endEdit",_rowIndex);
	}
	
	var updated = $('#tableMv').datagrid('getChanges','updated');
	if(updated.length == 0){
		//$.messager.alert('提示消息','保存成功','info');
		return;
	}
	var data = {"mvInfos":updated};
	// 保存
	$.ajax({
		type:"POST",
		url:"../../mvcrawler/updatemv.do",
		data:JSON.stringify({"data":data}),
		dataType:"json",
		contentType:"application/json;charset=utf-8;",
		success:function(resp){
			if(!resp){
				return;
			}
			if(resp.retCode == 0){
				$.messager.alert('提示消息','保存成功','info');
				loadDataGrid();
			}else{
				$.messager.alert('错误消息',resp.retMsg,'error');
			}
		}
	});
	
}

function del(){
	var rows = $("#tableMv").datagrid("getChecked");
	if(!rows || rows.length == 0){
		return;
	}
	$.messager.confirm('Confirm', '确认删除吗？', function(yes){
		if (!yes){
			return;
		}
		var data = {"mvInfos":rows};
		// 删除
		$.ajax({
			type:"POST",
			url:"../../mvcrawler/deletemv.do",
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

function cancelEdit(){
	new operate.util.table().cancelEdit("tableMv");
}

function search(){
	var mvId = $("#inputMvId").val() ? $("#inputMvId").val().trim() : null;
	var name = $("#inputMvName").val() ? $("#inputMvName").val().trim() : null;
	var data = JSON.parse(queryParams["data"]);
	data["mvId"] = mvId;
	data["name"] = name;
	queryParams["data"] = JSON.stringify(data);
	$("#tableMv").datagrid("load",queryParams);
}

function clear(){
	$('#formQueryMv').form("reset");
}

function cacelEditSource(mvId,rowIndex){
	if(adding){
		$("#tableMvSource_"+mvId).datagrid("deleteRow", rowIndex);
		adding = false;
	}else{
		$("#tableMvSource_"+mvId).datagrid("cancelEdit", rowIndex);
	}
}

function delRow(mvId,sourceId,rowIndex){
	$.messager.confirm('确认提示','确认删除吗?',function(ok){
		if (!ok){
			return;
		}
		var data = {"sourceId":sourceId};
		// 删除
		$.ajax({
			type:"POST",
			url:"../../mvcrawler/deletemvsource.do",
			data:JSON.stringify({"data":data}),
			dataType:"json",
			contentType:"application/json;charset=utf-8;",
			success:function(resp){
				if(!resp){
					return;
				}
				if(resp.retCode == 0){
					$("#tableMvSource_"+mvId).datagrid("deleteRow", rowIndex);
				}else{
					$.messager.alert('错误消息',resp.retMsg,'error');
				}
			}
		});
	});
	// 删除新增行后，新增标识置为false
	adding = false;
}

function editRow(mvId,rowIndex){
	// 取消其他正在编辑的行
	var rows = $("#tableMvSource_"+mvId).datagrid("getRows");
	for(var i = 0;i<rows.length;i++){
		var row = rows[i];
		var _rowIndex = $("#tableMvSource_"+mvId).datagrid("getRowIndex", row);
		$("#tableMvSource_"+mvId).datagrid("cancelEdit",_rowIndex);
	}
	// 开启行编辑
	$("#tableMvSource_"+mvId).datagrid("beginEdit", rowIndex);
}

function addRow(mvId){
	// 一次只允许新增一行
	if(adding){
		return;
	}
	// 取消其他正在编辑的行
	var rows = $("#tableMvSource_"+mvId).datagrid("getRows");
	for(var i = 0;i<rows.length;i++){
		var row = rows[i];
		var _rowIndex = $("#tableMvSource_"+mvId).datagrid("getRowIndex", row);
		$("#tableMvSource_"+mvId).datagrid("cancelEdit",_rowIndex);
	}
	// 开启行编辑
	$("#tableMvSource_"+mvId).datagrid("appendRow", {'mvId':mvId,'sourceUrl':""});
	// 新增的行默认开启编辑模式
	var addIndex = $("#tableMvSource_"+mvId).datagrid('getRows').length - 1
	$("#tableMvSource_"+mvId).datagrid('beginEdit',addIndex);
}

function saveRow(mvId,rowData,rowIndex){
	var sourceId = rowData.sourceId;
	rowData.mvId = mvId;
	var websiteIdEditor = $("#tableMvSource_"+mvId).datagrid('getEditor', {index:rowIndex,field:'websiteId'});
	rowData.websiteId = $(websiteIdEditor.target).combobox('getValue');
	rowData.websiteName = $(websiteIdEditor.target).combobox('getText');
	var sourceUrlEditor = $("#tableMvSource_"+mvId).datagrid('getEditor', {index:rowIndex,field:'sourceUrl'});
	rowData.sourceUrl = $(sourceUrlEditor.target).val();
	var data = {"mvSource":rowData};

	var reqUrl = "../../mvcrawler/createmvsource.do";
	if(sourceId){
		reqUrl = "../../mvcrawler/updatemvsource.do";
	}
	// 保存
	$.ajax({
		type:"POST",
		url:reqUrl,
		data:JSON.stringify({"data":data}),
		dataType:"json",
		contentType:"application/json;charset=utf-8;",
		success:function(resp){
			if(!resp){
				return;
			}
			if(resp.retCode == 0){
				$.messager.alert('提示消息','保存成功','info');
				rowData.editing = false;
				if(resp.sourceId){
					rowData.sourceId = resp.sourceId;
				}
				$("#tableMvSource_"+mvId).datagrid('updateRow',{index: rowIndex,row: rowData});
			}else{
				$.messager.alert('错误消息',resp.retMsg,'error');
			}
		}
	});	
}
