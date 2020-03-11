const express = require('express');
const path = require('path');
const app = express();

// 实现静态资源访问功能
app.use('/static',express.static(path.join(__dirname, 'public')))

// 端口监听
app.listen(3000);