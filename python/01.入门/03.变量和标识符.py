# Python中使用变量，不需要声明，直接为变量赋值即可
a = 10

# 不能使用没有进行过赋值的变量
# 如果使用没有赋值过的变量，会报错 NameError: name 'b' is not defined
# print(b)

# Python是一个动态类型的语言，可以为变量赋任意类型的值，也可以任意修改变量的值
a = 'hello'

# print(a)

# 标识符
# 在Python中所有可以自主命名的内容都属于标识符
# 比如：变量名、函数名、类名
# 标识符必须遵循标识符的规范
#   1.标识符中可以含有字母、数字、_，但是不能使用数字开头
#       例子：a_1 _a1 _1a
#   2.标识符不能是Python中的关键字和保留字
#       也不建议使用Python中的函数名作为标识符,因为这样会导致函数被覆盖
#   3.命名规范：
#       在Python中注意遵循两种命名规范：
#           下划线命名法
#               所有字母小写，单词之间使用_分割
#               max_length min_length hello_world xxx_yyy_zzz
#           帕斯卡命名法（大驼峰命名法）  
#               首字母大写，每个单词开头字母大写，其余字母小写
#               MaxLength MinLength HelloWorld XxxYyyZzz  
#       
#   如果使用不符合标准的标识符，将会报错 SyntaxError: invalid syntax    
#   练习：尝试自己定义几个变量（复杂一些，尝试不同的命名法），然后打印一些变量
#           通过搜索引擎搜索还有哪些其他的命名规范
_b123 = 20
# print(_b123)

# print = 123
# print(print)
