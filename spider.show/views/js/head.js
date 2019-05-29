/**
 * 
 */
$(document).ready(function(){
	// 调整导航栏高度
	window.top.frames[0].frameElement.height = $(".bg-dark").height();
});

/*
* 绑定搜索按钮事件
*/
$(document).on("click","#idSearchBtn",function(){
	var keyword = $("#idKeyword").val();
	window.open('search.html?keyword='+keyword);
});