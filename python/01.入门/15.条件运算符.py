# 条件运算符（三元运算符）
# 语法： 语句1 if 条件表达式 else 语句2
# 执行流程：
#   条件运算符在执行时，会先对条件表达式进行求值判断
#       如果判断结果为True，则执行语句1，并返回执行结果
#       如果判断结果为False，则执行语句2，并返回执行结果
# 练习：
#   现在有a b c三个变量，三个变量中分别保存有三个数值，
#       请通过条件运算符获取三个值中的最大值

# print('你好') if False else print('Hello')

a = 30
b = 50

# print('a的值比较大！') if a > b else print('b的值比较大！')
# 获取a和b之间的较大值
max = a if a > b else b

print(max)