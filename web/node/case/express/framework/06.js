// 引入express框架
const express = require('express');
const fs = require('fs');
const promisify = require('util').promisify;
const readFile = promisify(fs.readFile);
// 创建网站服务器
const app = express();

app.get('/index', async (req, res, next) => {
	try {
		await readFile('./aaa.js')
	}catch (ex) {
		next(ex);
	}
})

// 错误处理中间
app.use((err, req, res, next) => {
	res.status(500).send(err.message);
})

// 监听端口
app.listen(3000);
console.log('网站服务器启动成功');