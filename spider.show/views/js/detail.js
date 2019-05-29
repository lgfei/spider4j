/**
 * 
 */
$(document).ready(function(){
	var pageParamStr = window.location.search.substr(1);
	var paramArr = pageParamStr.split("&");
	var paramMap = new Map();
	$.each(paramArr,function(i,item){
		var arr = item.split("=");
		paramMap.set(arr[0],arr[1]);
	});
	// 渲染影片详情
	$.post("../spidershowservice/queryDetial",{"mvid" : paramMap.get("mvid")}, function(resp){
		var mvSources = JSON.parse(resp);
		var mvInfo = mvSources[0];
		$("#idPosterImg").attr("src",mvInfo.poster);
		$("#idMvName").text(mvInfo.mvName);
		$("#idBreadcrumbMvName").text(mvInfo.mvName);
		$("#idDirector").text(mvInfo.director);
		$("#idPlayer").text(mvInfo.player);
		$("#idShowDate").text(mvInfo.showDate);
		$("#idDesc").text(mvInfo.details);
		
		var maps = new Map();
		$.each(mvSources,function(i,item){
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

			var li = [];
			var tabC = [];
			if(mapsIndex === 0){
				// 选项卡菜单
				li.push('<li class="active">');
				li.push('<a href="#'+websiteId+'" data-toggle="tab">'+websiteName+'</a>');
				li.push('</li>');
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
				li.push('<li>');
				li.push('<a href="#'+websiteId+'" data-toggle="tab">'+websiteName+'</a>');
				li.push('</li>');
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
			$('#mvSourcesTab').append(li.join(" "));
			$('#mvSourcesTabContent').append(tabC.join(" "));
		  mapsIndex++;
 		});
	});
});