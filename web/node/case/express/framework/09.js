// 引入express框架
const express = require('express');
// 创建网站服务器
const app = express();

app.get('/index', (req, res) => {
	// 获取get请求参数
	res.send(req.query)
})

// 端口监听
app.listen(3000);