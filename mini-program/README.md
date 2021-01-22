#### 注意事项
- JS引入的时候，使用相对路径，不要使用绝对路径
- 模板字符串不仅支持变量，也支持函数
- 不能在 observer 自身中修改自身的值，会导致死循环
- wx:key：当遍历的对象本身就是字符串，可以使用 *this

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