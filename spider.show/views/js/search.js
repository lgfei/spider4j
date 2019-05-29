/**
 * 
 */
var keyWord = "";
$(document).ready(function(){
	var pageParamStr = window.location.search.substr(1);
	var paramArr = pageParamStr.split("&");
	var paramMap = new Map();
	$.each(paramArr,function(i,item){
		var arr = item.split("=");
		paramMap.set(arr[0],arr[1]);
	});
	keyWord = paramMap.get("keyword");
	// 初始加载第一页
	loadMvList(1);
});

function loadMvList(pageNum){
	// 先清空
	$('#idMvSearchDiv').empty();
	$('#idMvSearchDivPager').empty();
	// 重新渲染
	$.post('searchList',{'pageNum':pageNum,'keyWord':keyWord}, function(resp){
		var respJson = JSON.parse(resp);
		var map = new Map();
		$.each(respJson.data,function(i,item){
			var mvId = item.mvId;
			if(map.has(mvId)){
				map.get(mvId).push(item);
			}else{
				map.set(mvId,[item]);
			}
		});
		map.forEach(function(arr,key){
			var mvInfo = arr[0];
			var div = [];
			div.push('<div class="panel">');
			div.push('<div class="panel-body">');
			div.push('<table class="table table-responsive-md">');
			div.push('<tbody>');
			// 基本信息
			div.push('<tr>');
			div.push('<td class="w-25">');
			div.push('<img class="poster-detail" id="idPosterImg" src="'+mvInfo.poster+'">');
			div.push('</td>');
			div.push('<td>');
			div.push('<h3>'+mvInfo.mvName+'</h3>');
			div.push('<p class="text-left">导演：<span>'+mvInfo.director+'</span></p>');
			div.push('<p class="text-left">主演：<span>'+mvInfo.player+'</span></p>');
			div.push('<p class="text-left">日期：<span>'+mvInfo.showDate+'</span></p>');
			div.push('<p class="text-left">剧情：<span>'+mvInfo.details+'</span></p>');
			div.push('</td>');
			div.push('</tr>');
      // 播放源
			div.push('<tr>');
			div.push('<td colspan="2">');
			var ul = [];
			var tabC = [];
			ul.push('<ul class="nav nav-tabs">');
			tabC.push('<div class="tab-content">');

			var maps = new Map();
			$.each(arr,function(i,item){
				var key = item.websiteId + "-" + item.websiteName;
				if(maps.has(key)){
					maps.get(key).push(item);
				}else{
					maps.set(key,[item]);
				}
			});
			
			var mapsIndex = 0;
			maps.forEach(function (arr, key) {
				var keyArr = key.split("-");
				var websiteId = keyArr[0];
				var websiteName = keyArr[1];

				if(mapsIndex === 0){
					// 选项卡菜单
					ul.push('<li class="active">');
					ul.push('<a href="#'+websiteId+'" data-toggle="tab">'+websiteName+'</a>');
					ul.push('</li>');
					// 选项卡内容
					tabC.push('<div class="tab-pane fade active in" id="'+websiteId+'">');
					$.each(arr,function(i,item){
						var tabCId = websiteId + "-" + i;
						var tabCName = "立即播放";
						if(arr.length > 1){
							tabCName = "播放-" + i;
						}
						tabC.push('<div class="btn-play float-l"><a class="btn btn-primary" href="'+item.sourceUrl+'" role="button" target="_blank">'+tabCName+'</a></div>');
					});
					tabC.push('</div>');
				}else{
					// 选项卡菜单
					ul.push('<li>');
					ul.push('<a href="#'+websiteId+'" data-toggle="tab">'+websiteName+'</a>');
					ul.push('</li>');
					// 选项卡内容
					tabC.push('<div class="tab-pane fade" id="'+websiteId+'">');
					$.each(arr,function(i,item){
						var tabCName = "立即播放";
						if(arr.length > 1){
							tabCName = "播放-" + i;
						}
						tabC.push('<div class="btn-play float-l"><a class="btn btn-primary" href="'+item.sourceUrl+'" role="button" target="_blank">'+tabCName+'</a></div>');
					});
					tabC.push('</div>');
				}
				mapsIndex++;
			});
			ul.push('</ul>');
			tabC.push('</div');
			div.push(ul.join(" "));
			div.push(tabC.join(" "));
			div.push('</td>');
			div.push('</tr>');

			div.push('</tbody>');
			div.push('</table>');
			div.push('</div>');
			div.push('</div>');

			$('#idMvSearchDiv').append(div.join(" "));
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
	    	
	    	$('#idMvSearchDivPager').append(li.join(" "));
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