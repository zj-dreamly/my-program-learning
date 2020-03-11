// 引入express框架
const express = require('express');
const fs = require('fs');
// 创建网站服务器
const app = express();

app.get('/index', (req, res, next) => {
	// throw new Error('程序发生了未知错误')
	fs.readFile('./01.js', 'utf8', (err, result) => {
		if (err != null) {
			next(err)
		}else {
			res.send(result)
		}
	})

	// res.send('程序正常执行')
})

// 错误处理中间
app.use((err, req, res, next) => {
	res.status(500).send(err.message);
})

// 监听端口
app.listen(3000);
console.log('网站服务器启动成功');