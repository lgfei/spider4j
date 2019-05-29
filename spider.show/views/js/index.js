/**
 * 
 */
$(document).ready(function(){
	// 渲染最新电影数据
	$.get("queryLastests", function(resp){
		var rows = [];
		var index = -1;
		var data = JSON.parse(resp);
	    $.each(data,function(i,item){
	    	if(i % 4 == 0){
	    		index++;
	    		rows[index] = [];
	    	}
	    	rows[index].push(item);
	    });
	    
	    $.each(rows,function(i,row){
	    	var tr = [];
	    	tr.push('<tr>');
	    	$.each(row,function(j,mv){
	    		var td = [];
	    		td.push('<td class="w-20">');
	    		td.push('<a href="/spidershowservice/detail.html?mvid='+mv.mvId+'" style="text-decoration:none;">');
	    		td.push('<div class="card card-mv">');
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
	    	
	    	$('#idLastestMvTable tbody').append(tr.join(" "));
	    });
	});

		// 渲染推荐电影数据
		$.get("queryHots", function(resp){
			var rows = [];
			var index = -1;
			var data = JSON.parse(resp);
				$.each(data,function(i,item){
					if(i % 4 == 0){
						index++;
						rows[index] = [];
					}
					rows[index].push(item);
				});
				
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
					
					$('#idHotMvTable tbody').append(tr.join(" "));
				});
		});
});