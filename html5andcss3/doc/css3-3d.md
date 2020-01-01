### `cHTML5 `  第三天

###### 一、 认识 `3D` 转换

1. `3D` 的特点
   - 近大远小
   - 物体和面遮挡不可见
2. 三维坐标系
   - x 轴：水平向右  -- **注意：x 轴右边是正值，左边是负值**

   - y 轴：垂直向下  -- **注意：y 轴下面是正值，上面是负值**

   - z 轴：垂直屏幕  --  **注意：往外边的是正值，往里面的是负值**

     <img src="images\sanwei.png">

​          

###### 二、`3D` 转换

1. `3D` 转换知识要点
   - `3D` 位移：`translate3d(x, y, z)`
   - `3D` 旋转：`rotate3d(x, y, z)`
   - 透视：`perspctive`
   - `3D`呈现 `transfrom-style`

2.  `3D` 移动 `translate3d`

   - `3D` 移动就是在 `2D` 移动的基础上多加了一个可以移动的方向，就是 z 轴方向
   - `transform: translateX(100px)`：仅仅是在 x 轴上移动
   - `transform: translateY(100px)`：仅仅是在 y 轴上移动
   - `transform: translateZ(100px)`：仅仅是在 z 轴上移动
   - `transform: translate3d(x, y, z)`：其中x、y、z 分别指要移动的轴的方向的距离
   - **注意：x, y, z 对应的值不能省略，不需要填写用 0 进行填充**

3.  语法

   ```html
    transform: translate3d(x, y, z)
   ```

4.  代码演示

   ```html
   transform: translate3d(100px, 100px, 100px)
   /* 注意：x, y, z 对应的值不能省略，不需要填写用 0 进行填充 */
   transform: translate3d(100px, 100px, 0)
   ```

###### 三、透视 `perspective`

1. 知识点讲解

   - 如果想要网页产生 `3D` 效果需要透视(理解成 `3D` 物体投影的 `2D` 平面上)
   - 实际上模仿人类的视觉位置，可视为安排一直眼睛去看
   - 透视也称为视距，所谓的视距就是人的眼睛到屏幕的距离
   - 距离视觉点越近的在电脑平面成像越大，越远成像越小
   - 透视的单位是像素

2. 知识要点

   - **透视需要写在被视察元素的父盒子上面**
   - 注意下方图片
     - d：就是视距，视距就是指人的眼睛到屏幕的距离

     - z：就是 z 轴，z 轴越大(正值)，我们看到的物体就越大

       <img src="images\perspective.png">

   ​            

3. 代码演示

   ```css
   body {
     perspective: 1000px;
   }
   ```

###### 四、 `translateZ`

1.  `translateZ` 与 `perspecitve` 的区别
   - `perspecitve` 给父级进行设置，`translateZ` 给 子元素进行设置不同的大小

###### 五、`3D` 旋转`rotateX`

> 3D 旋转指可以让元素在三维平面内沿着 x 轴、y 轴、z 轴 或者自定义轴进行旋转

1. 语法
   - `transform: rotateX(45deg)` -- 沿着 x 轴正方向旋转 45 度
   -  `transform: rotateY(45deg)` -- 沿着 y 轴正方向旋转 45 度
   -  `transform: rotateZ(45deg)` -- 沿着 z 轴正方向旋转 45 度
   - `transform: rotate3d(x, y, z, 45deg)` -- 沿着自定义轴旋转 45 deg 为角度

2.  代码案例

   ```css
   div {
     perspective: 300px;
   }
   
   img {
     display: block;
     margin: 100px auto;
     transition: all 1s;
   }
   
   img:hover {
     transform: rotateX(-45deg)
   }
   ```


3. 左手准则

   - 左手的手拇指指向 x 轴的正方向

   - 其余手指的弯曲方向就是该元素沿着 x 轴旋转的方向

     <img src="images\rotateX.png">

​              

###### 六、`3D` 旋转 `rotateY`

1.  代码演示

   ```css
   div {
     perspective: 500px;
   }
   
   img {
     display: block;
     margin: 100px auto;
     transition: all 1s;
   }
   
   img:hover {
     transform: rotateY(180deg)
   }
   ```

2. 左手准则

   - 左手的拇指指向 y 轴的正方向

   - 其余的手指弯曲方向就是该元素沿着 y 轴旋转的方向(正值)

     <img src="images\rotateY.png">

     ​

###### 七、 `3D` 旋转 `rotateZ`

1.  代码演示

   ```css
   div {
     perspective: 500px;
   }
   
   img {
     display: block;
     margin: 100px auto;
     transition: all 1s;
   }
   
   img:hover {
     transform: rotateZ(180deg)
   }
   ```

2.   `rotate3d`
   - `transform: rotate3d(x, y, z, deg)` -- 沿着自定义轴旋转 deg 为角度
   - x, y, z 表示旋转轴的矢量，是标识你是否希望沿着该轴进行旋转，最后一个标识旋转的角度
     - `transform: rotate3d(1, 1, 0, 180deg)` -- 沿着对角线旋转 45deg
     - `transform: rotate3d(1, 0, 0, 180deg)` -- 沿着 x 轴旋转 45deg

3. 代码演示

   ```css
   div {
     perspective: 500px;
   }
   
   img {
     display: block;
     margin: 100px auto;
     transition: all 1s;
   }
   
   img:hover {
     transform: rotate3d(1, 1, 0, 180deg)
   }
   ```

   ###### 八、`3D` 呈现 `transform-style`

   1.  `transform-style`

      - ☆☆☆☆☆

      -  控制子元素是否开启三维立体环境
      - `transform-style: flat`  代表子元素不开启 `3D` 立体空间，默认的
      - `transform-style: preserve-3d` 子元素开启立体空间
      -  代码写给父级，但是影响的是子盒子

   2.  代码演示




