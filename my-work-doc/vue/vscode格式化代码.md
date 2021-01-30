### 安装插件

- vetur
- ESlint
- prettier

### 配置

```json
{
  //打开文件不覆盖
  "workbench.editor.enablePreview": false,
  //关闭快速预览
  "editor.minimap.enabled": false,
  //打开自动保存
  "files.autoSave": "afterDelay",
  //每次保存自动格式化
  "editor.formatOnSave": true,
  // 每次保存的时候将代码按eslint格式进行修复
  "editor.codeActionsOnSave": {
    "source.fixAll.eslint": true
  },
  //让函数(名)和后面的括号之间加个空格
  "javascript.format.insertSpaceBeforeFunctionParenthesis": true,
  //格式化.vue中html
  "vetur.format.defaultFormatter.html": "js-beautify-html",
  //让vue中的js按编辑器自带的ts格式进行格式化
  "vetur.format.defaultFormatter.js": "vscode-typescript",
  "vetur.format.defaultFormatterOptions": {
    "js-beautify-html": {
      "wrap_attributes": "force-aligned" //属性强制折行对齐
    }
  },
  "window.zoomLevel": 2,
  "workbench.colorTheme": "Material Theme",
  "workbench.iconTheme": "material-icon-theme",
}
```

### 项目根目录新建.prettierrc

```json
{
   //让prettier使用eslint的代码格式进行校验
  "eslintIntegration": true,  
   //去掉代码结尾的分号
  "semi": false, 
   //使用带引号替代双引号
  "singleQuote": true  
}
```



