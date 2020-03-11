// 引入express框架
const express = require('express');
const bodyParser = require('body-parser');
// 创建网站服务器
const app = express();

app.use(fn ({a: 2}))

function fn (obj) {
	return function (req, res, next) {
		if (obj.a == 1) {
			console.log(req.url)
		}else {
			console.log(req.method)
		}
		next()
	}
}

app.get('/', (req, res) => {
	// 接收post请求参数
	res.send('ok')
})

// 端口监听
app.listen(3000);