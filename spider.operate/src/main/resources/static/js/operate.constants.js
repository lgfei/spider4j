/**
 * 
 */
if (typeof (Constants) == 'undefined') {
	Constants = {};
}
(function() {
	var mConstants = 
	{
		pageList : [ 10, 20, 50 ],
		queryParams : {data:'{"pageNum":1,"pageSize":10}'},
		pageSize : 10,
	};

	$.extend(Constants, mConstants, true);
})();
