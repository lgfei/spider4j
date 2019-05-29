/**
 * 各种工具类
 */
(function () {
	if (typeof operate === "undefined") {
		operate = {};
	}
	if (!operate.util) {
		operate.util = {};
	}
	/**
	 * 表单操作工具类
	 */
	(function ($) {
		operate.util.form = function(){
			var formUtil = this;
		    // 获取表单参数
		    formUtil.getParam = function(formId,formData){
		        if(formData == null){
		        	formData = {};
		        }
		        var arr = $('#'+formId).serializeArray();
		        $.each(arr, function() {
		        	formData[this.name] = this.value;
		        });
		        
		        return formData;
		    };
		};
	})(jQuery);
	/**
	 * 表格操作工具类
	 */
	(function ($) {
		operate.util.table = function(){
			var tableUtil = this;
		    // 获取表单参数
			tableUtil.cancelEdit = function(tableId){
				var rows = $("#"+tableId).datagrid("getChecked");
				if(!rows || rows.length == 0){
					return;
				}
				for(var i = 0;i<rows.length;i++){
					var row = rows[i];
					var _rowIndex = $("#"+tableId).datagrid("getRowIndex", row);
					// 取消编辑
					$("#"+tableId).datagrid("cancelEdit",_rowIndex);
					// 取消勾选
					$("#"+tableId).datagrid("uncheckRow",_rowIndex);
					// 取消选中
					$("#"+tableId).datagrid("unselectRow",_rowIndex);
				}
		    };
		};
	})(jQuery);
})();

/**
 * 获取全部的网站
 * @returns
 */
function getWebsites(){
	var data = [];
	$.ajax({
		type:"POST",
		async : false,
		url:"../../mvcrawler/querywebsitelist.do",
		data:JSON.stringify({"data":{}}),
		dataType:"json",
		contentType:"application/json;charset=utf-8;",
		success:function(resp){
			if(!resp){
				return;
			}
			if(resp.retCode == 0){
				if(resp.data && resp.data.length > 0){
					$.each(resp.data,function(i,item){
						data.push(item);
					});
				}
			}else{
				$.messager.alert('错误消息',resp.retMsg,'error');
			}
		}
	});
	return data;
}


/**
 * 日期格式化
 */
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}