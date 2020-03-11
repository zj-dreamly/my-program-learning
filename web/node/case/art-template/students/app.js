// 引入http模块
const http = require('http');
// 引入模板引擎
const template = require('art-template');
// 引入path模块
const path = require('path');
// 引入静态资源访问模块
const serveStatic = require('serve-static');
// 引入处理日期的第三方模块
const dateformat = require('dateformat');

const router = require('./route/index');
// 实现静态资源访问服务
const serve = serveStatic(path.join(__dirname, 'public'))

// 配置模板的根目录
template.defaults.root = path.join(__dirname, 'views');
// 处理日期格式的方法
template.defaults.imports.dateformat = dateformat;

// 数据库连接
require('./model/connect');

// 创建网站服务器
const app = http.createServer();
// 当客户端访问服务器端的时候
app.on('request', (req, res) => {
	// 启用路由功能
	router(req, res, () => {})
	// 启用静态资源访问服务功能
	serve(req, res, () => {})
});
// 端口监听
app.listen(80);
console.log('服务器启动成功');