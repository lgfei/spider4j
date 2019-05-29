
var fs = require('fs');
var express = require('express');
var app = express();
var mysql  = require('mysql');
//导入querystring模块（解析post请求数据）
var querystring = require('querystring');
// 加解密
var aesutil = require('./aesutil.js');

/**
 * 新建数据库连接
 */
var newConnection = function(){
	var decipherPwd = aesutil.decryption('+RpcQNiAdCoAojx6ua70IA==');
	//var decipherPwd = aesutil.decryption('mdXeBosGjIbH/LdSMBAO0A==');
	return mysql.createConnection({     
	    host : '127.0.0.1',       
	    user : 'root',              
	    password : decipherPwd,       
	    port: '3306',                   
	    database : 'mvcrawler', 
	});
}

// 项目根路径
var contentRoot = "/spidershowservice";
// 配置静态资源
app.use(contentRoot, express.static(__dirname + '/views'));

/**
 * 查询最新影片列表
 */
app.get(contentRoot+'/queryLastests', function (req, res) {
	console.log('begin queryLastests');
	var connection = newConnection();
	connection.connect();
	//查
	var sql = fs.readFileSync('sql/queryLastests.sql').toString();
	console.log('sql:',sql);
	connection.query(sql,function (err, result) {
	        if(err){
	          console.log('[SELECT ERROR] - ',err.message);
	          return;
	        }
	        res.end(JSON.stringify(result));
	});
	connection.end();
	console.log('end queryLastests');
});

/**
 * 查询推荐影片列表
 */
app.get(contentRoot+'/queryHots', function (req, res) {
	console.log('begin queryHots');
	var connection = newConnection();
	connection.connect();
	//查
	var sql = fs.readFileSync('sql/queryHots.sql').toString();
	console.log('sql:',sql);
	connection.query(sql,function (err, result) {
	        if(err){
	          console.log('[SELECT ERROR] - ',err.message);
	          return;
	        }
	        res.end(JSON.stringify(result));
	});
	connection.end();
	console.log('end queryHots');
});

/**
 * 查询影片详情
 */
app.post(contentRoot+'/queryDetial', function (req, res) {
	console.log('begin queryDetial');
	 var data = '';
    // 注册data事件接收数据（每当收到一段表单提交的数据，该方法会执行一次）
    req.on('data', function (chunk) {
        // chunk 默认是一个二进制数据，和 data 拼接会自动 toString
        data += chunk;
    });
    // 当接收表单提交的数据完毕之后，就可以进一步处理了
    //注册end事件，所有数据接收完成会执行一次该方法
    req.on('end', function () {
        data = decodeURI(data);
        var dataObject = querystring.parse(data);
        var mvId = dataObject.mvid;
    	var modSql = fs.readFileSync('sql/queryDetial.sql').toString();
    	var modSqlParams = [mvId];
    	console.log('sql:',modSql);
    	console.log('param:',modSqlParams.join(","));
    	
    	var connection = newConnection();
    	connection.connect();
    	//查
    	connection.query(modSql, modSqlParams, function (err, result) {
    	        if(err){
    	          console.log('[SELECT ERROR] - ',err.message);
    	          return;
    	        }
    	        res.end(JSON.stringify(result));
    	});
    	connection.end();
    });
    console.log('end queryDetial');
});

/**
 * 分页查询影片
 */
app.post(contentRoot+'/queryList', function (req, res) {
	console.log('begin queryList');
	 var data = '';
    // 注册data事件接收数据（每当收到一段表单提交的数据，该方法会执行一次）
    req.on('data', function (chunk) {
        // chunk 默认是一个二进制数据，和 data 拼接会自动 toString
        data += chunk;
    });
    // 当接收表单提交的数据完毕之后，就可以进一步处理了
    //注册end事件，所有数据接收完成会执行一次该方法
    req.on('end', function () {
        data = decodeURI(data);
        var dataObject = querystring.parse(data);
        var pageSize = 40;
        var pageNum = parseInt(dataObject.pageNum);
        var offset = (pageNum - 1) * pageSize;
        // 分页查询语句
    	var modSql = fs.readFileSync('sql/queryList.sql').toString();
    	var modSqlParams = [offset,pageSize];
    	console.log('sql:',modSql);
    	console.log('param:',modSqlParams.join(","));
    	// 查询总数语句
    	var modCountSql = 'SELECT  COUNT(1) AS total  FROM   t_mv_info';
    	
    	// 分页查询响应对象
    	var pageResult = {data:"", pageNum:pageNum, pageSize:pageSize, total:0};
    	
    	var connection = newConnection();
    	connection.connect();
    	// 查总数
    	connection.query(modCountSql,function (err, result) {
	        if(err){
	          console.log('[SELECT ERROR] - ',err.message);
	          return;
	        }
	        var total = result[0].total;
	        if(total > 0){
	        	 pageResult.total = total;
	        	 // 分页查
	 	    	 connection.query(modSql, modSqlParams, function (err, result) {
	 	    	        if(err){
	 	    	          console.log('[SELECT ERROR] - ',err.message);
	 	    	          return;
	 	    	        }
	 	    	        pageResult.data = result;
	 	    	        res.end(JSON.stringify(pageResult));
	 	    	 });
	 	    	connection.end();
	        }
	    });
    });
    console.log('end queryList');
});

/**
 * 查询片源种类
 */
app.get(contentRoot+'/querySourceGroup', function (req, res) {
	console.log('begin querySourceGroup');
	var connection = newConnection();
	connection.connect();
	//查
	var sql = fs.readFileSync('sql/querySourceGroup.sql').toString();
	console.log('sql:',sql);
	connection.query(sql,function (err, result) {
	        if(err){
	          console.log('[SELECT ERROR] - ',err.message);
	          return;
	        }
	        res.end(JSON.stringify(result));
	});
	connection.end();
	console.log('end querySourceGroup');
});

/**
 * 分页查询影片
 */
app.post(contentRoot+'/queryListByCond', function (req, res) {
	console.log('begin queryListByCond');
	 var data = '';
    // 注册data事件接收数据（每当收到一段表单提交的数据，该方法会执行一次）
    req.on('data', function (chunk) {
        // chunk 默认是一个二进制数据，和 data 拼接会自动 toString
        data += chunk;
    });
    // 当接收表单提交的数据完毕之后，就可以进一步处理了
    //注册end事件，所有数据接收完成会执行一次该方法
    req.on('end', function () {
        data = decodeURI(data);
        var dataObject = querystring.parse(data);
        var pageSize = 40;
        var pageNum = parseInt(dataObject.pageNum);
        var offset = (pageNum - 1) * pageSize;
        
        // 分页查询语句
    	var modSql = fs.readFileSync('sql/queryListByCond.sql').toString();
    	// 时间
    	var time = dataObject.time;
    	var begin = '0';
    	var end = '0';
        if(time == '2004-0000'){
        	begin = '0000-00-00';
        	end = '2004-12-31';
        }else if(time != '0'){
        	begin = time + '-00-00';
        	end = time + '-12-31';
        }
        // 片源
        var source = dataObject.source;
    	var modSqlParams = [begin,end,source,offset,pageSize];
    	var modCountSqlParams = [begin,end,source];
    	if(time == '0'){
    		modSql = modSql.replace('WHERE mi.`show_date` BETWEEN  ? AND ?','');
    		modSqlParams.remove(begin);
    		modSqlParams.remove(end);
    		modCountSqlParams.remove(begin);
    		modCountSqlParams.remove(end);
    	}
    	if(source == '0'){
    		modSql = modSql.replace('WHERE t.wids = ?','');
    		modSqlParams.remove(source);
    		modCountSqlParams.remove(source);
    	}
    	console.log('sql:',modSql);
    	console.log('param:',modSqlParams.join(","));
    	// 查询总数语句
    	var modCountSql = modSql.replace('*','COUNT(1) AS total').replace('LIMIT ?,?','');
    	console.log('sql:',modCountSql);
    	console.log('param:',modCountSqlParams.join(","));
    	
    	// 分页查询响应对象
    	var pageResult = {data:"", pageNum:pageNum, pageSize:pageSize, total:0};
    	
    	var connection = newConnection();
    	connection.connect();
    	// 查总数
    	connection.query(modCountSql,modCountSqlParams,function (err, result) {
	        if(err){
	          console.log('[SELECT ERROR] - ',err.message);
	          return;
	        }
	        var total = result[0].total;
	        if(total > 0){
	        	 pageResult.total = total;
	        	 // 分页查
	 	    	 connection.query(modSql, modSqlParams, function (err, result) {
	 	    	        if(err){
	 	    	          console.log('[SELECT ERROR] - ',err.message);
	 	    	          return;
	 	    	        }
	 	    	        pageResult.data = result;
	 	    	        res.end(JSON.stringify(pageResult));
	 	    	 });
	        }
	        connection.end();
	    });
    });
    console.log('end queryListByCond');
});

/**
 * 分页查询影片
 */
app.post(contentRoot+'/searchList', function (req, res) {
	console.log('begin searchList');
	 var data = '';
    // 注册data事件接收数据（每当收到一段表单提交的数据，该方法会执行一次）
    req.on('data', function (chunk) {
        // chunk 默认是一个二进制数据，和 data 拼接会自动 toString
        data += chunk;
    });
    // 当接收表单提交的数据完毕之后，就可以进一步处理了
    //注册end事件，所有数据接收完成会执行一次该方法
    req.on('end', function () {
        data = decodeURI(data);
        var dataObject = querystring.parse(data);
        var keyWord = dataObject.keyWord;
        if(!keyWord || !keyWord.trim()){
        	// 没有输入值则不处理
        	res.end("");
        	return;
        }
        var pageSize = 10;
        var pageNum = parseInt(dataObject.pageNum);
        var offset = (pageNum - 1) * pageSize;
        // 分页查询语句
    	var modSql = fs.readFileSync('sql/searchList.sql').toString();
    	var modSqlParams = [keyWord,offset,pageSize];
    	console.log('sql:',modSql);
    	console.log('param:',modSqlParams.join(","));
    	// 查询总数语句
    	var modCountSql = fs.readFileSync('sql/searchCount.sql').toString();
    	var modCountSqlParams = [keyWord];
    	console.log('sql:',modCountSql);
    	console.log('param:',modCountSqlParams.join(","));
    	
    	// 分页查询响应对象
    	var pageResult = {data:"", pageNum:pageNum, pageSize:pageSize, total:0};
    	
    	var connection = newConnection();
    	connection.connect();
    	// 查总数
    	connection.query(modCountSql,modCountSqlParams,function (err, result) {
	        if(err){
	          console.log('[SELECT ERROR] - ',err.message);
	          return;
	        }
	        var total = result[0].total;
	        if(total > 0){
	        	 pageResult.total = total;
	        	 // 分页查
	 	    	 connection.query(modSql, modSqlParams, function (err, result) {
	 	    	        if(err){
	 	    	          console.log('[SELECT ERROR] - ',err.message);
	 	    	          return;
	 	    	        }
	 	    	        pageResult.data = result;
	 	    	        res.end(JSON.stringify(pageResult));
	 	    	 });
	 	    	connection.end();
	        }
	    });
    });
    console.log('end searchList');
});
	
var server = app.listen(process.env.npm_package_config_port, function () {
	var host = server.address().address
    var port = server.address().port
	console.log("应用实例，访问地址为 http://%s:%s", host, port);
});

/**
 * 数组删除指定元素
 */
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};
