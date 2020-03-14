const data = require('./data.json');
const path = require('path');
const fs = require('fs');

// 自动生成图书编号（自增）
let maxBookCode = ()=>{
    let arr = [];
    data.forEach((item)=>{
        arr.push(item.id);
    });
    return Math.max.apply(null,arr);
}
// 把内存数据写入文件
let writeDataToFile = (res) => {
    fs.writeFile(path.join(__dirname,'data.json'),JSON.stringify(data,null,4),(err)=>{
        if(err){
            res.json({
                status: 500
            });
        }
        res.json({
            status: 200
        });
    });
}
// 验证图书名称是否存在
exports.checkName = (req,res) => {
    let name = req.params.name;
    let flag = false;
    data.some(item=>{
        if(name == item.name) {
            flag = true;
            return true;
        }
    })
    if(flag) {
        res.json({
            status: 1
        })
    }else{
        res.json({
            status: 2
        })
    }
}

// 获取图书列表数据
exports.getAllBooks = (req,res) => {
    res.json(data);
}

// 添加图书保存数据
exports.addBook = (req,res) => {
    // 获取表单数据
    let info = req.body;
    let book = {};
    for(let key in info){
        book[key] = info[key];
    }
    book.date = 2525609975000;
    book.id = maxBookCode() + 1;
    data.push(book);
    // 把内存中的数据写入文件
    writeDataToFile(res);
}
// 跳转编辑图书页面
exports.toEditBook = (req,res) => {
    let id = req.params.id;
    let book = {};
    data.some((item)=>{
        if(id == item.id){
            book = item;
            return true;
        }
    });
    res.json(book);
}
// 编辑图书更新数据
exports.editBook = (req,res) => {
    let info = req.body;
    info.id = req.params.id;
    data.some((item)=>{
        if(info.id == item.id){
            for(let key in info){
                item[key] = info[key];
            }
            return true;
        }
    });
    // 把内存中的数据写入文件
    writeDataToFile(res);
}
// 删除图书信息
exports.deleteBook = (req,res) => {
    let id = req.params.id;
    data.some((item,index)=>{
        if(id == item.id){
            // 删除数组的一项数据
            data.splice(index,1);
            return true;
        }
    });
    // 把内存中的数据写入文件
    writeDataToFile(res);
}

