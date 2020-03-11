const template = require('art-template');
const path = require('path');

const views = path.join(__dirname, 'views', '03.art');

const html = template(views, {
	users: [{
		name: '张三',
		age: 20,
		sex: '男'
	},{
		name: '李四',
		age: 30,
		sex: '男'
	},{
		name: '玛丽',
		age: 15,
		sex: '女'
	}]
});

console.log(html);