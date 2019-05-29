var time = '0';
var source = '0';
/**
 * 
 */
$(document).ready(function(){
	// 初始加载第一页
	loadMvList(1);
	// 初始查询条件为全部
	loadTimeCond(0);
	loadSourceCond(0);
});

/**
 * 渲染电影列表数据 
 */
function loadMvList(pageNum){
	// 先清空
	$('#idMvListTable tbody').empty();
	$('#idMvListTablePager').empty();
	var url = 'queryList';
	var param = {"pageNum" : pageNum};
	if(time != '0' || source != '0'){
		url = 'queryListByCond';
		$.extend(param,{'time':time,'source':source});
	}
	// 重新渲染
	$.post(url,param, function(resp){
		var respJson = JSON.parse(resp);
		var rows = [];
		var index = -1;
	    $.each(respJson.data,function(i,item){
	    	if(i % 4 == 0){
	    		index++;
	    		rows[index] = [];
	    	}
	    	rows[index].push(item);
	    });
	    // 渲染表格数据
	    $.each(rows,function(i,row){
	    	var tr = [];
	    	tr.push('<tr>');
	    	$.each(row,function(j,mv){
	    		var td = [];
	    		td.push('<td class="w-20">');
	    		td.push('<a href="/spidershowservice/detail.html?mvid='+mv.mvId+'" style="text-decoration:none;">');
	    		td.push('<div class="card poster-list">');
	    		td.push('<img class="card-img-top w-100" src="'+mv.poster+'" title="'+mv.mvName+'" style="max-height:320px;">');
	    		td.push('<div class="card-body">');
	    		td.push('<div class="mvname ellipsis-text">'+mv.mvName+'</div>');
	    		td.push('<div class="player ellipsis-text" title="'+mv.player+'">主演：'+ mv.player +'</div>');
	    		td.push('</div>');
	    		td.push('</div>');
	    		td.push('</a>');
	    		td.push('</td>');
	    		
	    		tr.push(td.join(" "));
	    	});
	    	tr.push('</tr>');
	    	
	    	$('#idMvListTable tbody').append(tr.join(" "));
	    });
	    // 渲染分页
	    var total = respJson.total;
	    if(total){
	    	var pageSize = respJson.pageSize ? respJson.pageSize : 40;
		    // 计算一共多少页
	    	var pageCount = total % pageSize == 0 ? total/pageSize : parseInt(total/pageSize) +1;
	    	var pageNum = respJson.pageNum ? respJson.pageNum : 1;
	    	var li = [];
	    	if(pageNum == 1){
	    		li.push('<li class="disabled"><a href="#">&laquo;</a></li>');
	    		li.push('<li class="active"><a href="javascript:void(0)">'+1+'</a></li>');
	    	}else{
	    		li.push('<li><a href="javascript:loadMvList('+(pageNum - 1)+')">&laquo;</a></li>');
	    		li.push('<li><a href="javascript:loadMvList('+1+')">'+1+'</a></li>');
	    	}
	    	// 构造隐藏的页码
	    	if(pageCount > 1){
		    	if(pageCount <= 7){
		    		buildPager(li,pageNum,2,pageCount - 1);
		    	}else{
		    		if(pageNum < 6){
			    		buildPager(li,pageNum,2,6);
			    		li.push('<li class="disabled"><a>......</a></li>');
		    		}else if((pageNum + 5) > pageCount){
		    			li.push('<li class="disabled"><a>......</a></li>');
		    			buildPager(li,pageNum,pageCount - 5,pageCount - 1);
		    		}else{
		    			li.push('<li class="disabled"><a>...</a></li>');
		    			buildPager(li,pageNum,pageNum - 3,pageNum + 1);
		    			li.push('<li class="disabled"><a>...</a></li>');
		    		}
		    	}
		    	
	    		if(pageNum == pageCount){
		    		li.push('<li class="active"><a href="javascript:void(0)">'+pageCount+'</a></li>');
		    		li.push('<li class="disabled"><a href="#">&raquo;</a></li>');
		    	}else{
		    		li.push('<li><a href="javascript:loadMvList('+pageCount+')">'+pageCount+'</a></li>');
		    		li.push('<li><a href="javascript:loadMvList('+(pageNum + 1)+')">&raquo;</a></li>');
		    	}
	    	}
	    	
	    	$('#idMvListTablePager').append(li.join(" "));
	    }
	});	
}

/**
 * 根据当前页面构造分页条
 * @param li
 * @param pageNum
 * @param start
 * @param end
 * @returns
 */
function buildPager(li,pageNum,start,end){
	for(var i = start; i <= end; i++){
		if(i == pageNum){
			li.push('<li class="active"><a href="javascript:void(0)">'+i+'</a></li>');
		}else{
			li.push('<li><a href="javascript:loadMvList('+i+')">'+i+'</a></li>');
		}
	}
}

/**
 * 加载时间查询条件
 * @returns
 */
function loadTimeCond(curr){
	// 先清空
	$("#idTimeCondDiv").empty();
	// 在重新渲染
	var year = new Date().getFullYear();
	var btn = [];
	if(curr == 0){
		btn.push('<button type="button" class="btn btn-link" cond-type="time" cond-val="0"><span class="label label-primary">全部</span></button>');
	}else{
		btn.push('<button type="button" class="btn btn-link" cond-type="time" cond-val="0"><span>全部</span></button>');
	}
	for(var y = year; y >= 2005; y--){
		if(curr == y){
			btn.push('<button type="button" class="btn btn-link" cond-type="time" cond-val="'+y+'"><span class="label label-primary">'+y+'</span></button>');
		}else{
			btn.push('<button type="button" class="btn btn-link" cond-type="time" cond-val="'+y+'"><span>'+y+'</span></button>');
		}
	}
	if(curr == '2004-0000'){
		btn.push('<button type="button" class="btn btn-link" cond-type="time" cond-val="2004-0000"><span class="label label-primary">更早</span></button>');
	}else{
		btn.push('<button type="button" class="btn btn-link" cond-type="time" cond-val="2004-0000"><span>更早</span></button>');
	}
	$("#idTimeCondDiv").append(btn.join(" "));
	time = curr;
}

/**
 * 时间条件点击监听
 */
$(document).on("click","button[cond-type='time']",function(){
	var curr = $(this).attr("cond-val");
	loadTimeCond(curr);
	// 带条件的第一页
	loadMvList(1);
});

/**
 * 加载片源查询条件
 * @returns
 */
function loadSourceCond(curr){
	// 先清空
	$("#idSourceCondDiv").empty();
	var btn = [];
	if(curr == 0){
		btn.push('<button type="button" class="btn btn-link" cond-type="sour" cond-val="0"><span class="label label-primary">全部</span></button>');
	}else{
		btn.push('<button type="button" class="btn btn-link" cond-type="sour" cond-val="0"><span>全部</span></button>');
	}
	// 再重新渲染
	$.ajax({
	   type: "GET",
	   async: false,
	   url: "querySourceGroup",
	   success: function(resp){
		   var data = JSON.parse(resp);
			$.each(data,function(i,item){
				if(curr == item.wids){
					btn.push('<button type="button" class="btn btn-link" cond-type="sour" cond-val="'+item.wids+'"><span class="label label-primary">'+item.wnames+'</span></button>');
				}else{
					btn.push('<button type="button" class="btn btn-link" cond-type="sour" cond-val="'+item.wids+'"><span>'+item.wnames+'</span></button>');
				}
			});
	   }
	});
	$("#idSourceCondDiv").append(btn.join(" "));
	source = curr;
}

/**
 * 片源条件点击监听
 */
$(document).on("click","button[cond-type='sour']",function(){
	var curr = $(this).attr("cond-val");
	loadSourceCond(curr);
	// 带条件的第一页
	loadMvList(1);
});