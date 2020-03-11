const template = require('art-template');
const path = require('path');

const views = path.join(__dirname, 'views', '05.art');

const html = template(views, {
	msg: '首页模板'
});

console.log(html);