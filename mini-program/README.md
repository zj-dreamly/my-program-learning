#### 注意事项
- JS引入的时候，使用相对路径，不要使用绝对路径
- 模板字符串不仅支持变量，也支持函数
- 不能在 observer 自身中修改自身的值，会导致死循环
- wx:key：当遍历的对象本身就是字符串，可以使用 *this
- 参数转对象加{}
- 自定义组件不支持 class 样式设置，只能通过外部样式类设置.
-  async 保证返回结果一定是 promise ，await 需要在操作数据的时候加，不操作数据的时候可以不加
- 去除图片的边距：display: flex
- 去除块边距：box-sizing: border-box

#### 使用插槽

1. 组件开启多插槽配置

   ```javascript
   Component({
     options: {
       multipleSlots: true,
     },
   })
   ```

2. 组件配置插槽 `name` 属性

   ```css
   <view bind:tap="onTap" class="container tag-class ">
       <slot name="before"></slot>
       <text >{{text}}</text>
       <slot name="after"></slot>
   </view>
   ```

3. 外部调用

   ```css
   <v-tag tag-class="{{tool.highlight(index)}}" text="{{item.content}}">
       <text class="num" slot="after">{{'+'+item.nums}}</text>
   </v-tag>
   ```

> css 样式可写在调用方

setData为更新数据，如果是绑定在wxml里面，一定需要调用

