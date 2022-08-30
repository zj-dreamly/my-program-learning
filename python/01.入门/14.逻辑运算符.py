# 逻辑运算符
# 逻辑运算符主要用来做一些逻辑判断
# not 逻辑非
#   not可以对符号右侧的值进行非运算
#       对于布尔值，非运算会对其进行取反操作，True变False，False变True
#       对于非布尔值，非运算会先将其转换为布尔值，然后再取反
#       
# and 逻辑与
#   and可以对符号两侧的值进行与运算
#    只有在符号两侧的值都为True时，才会返回True，只要有一个False就返回False
#    与运算是找False的
#    Python中的与运算是短路的与，如果第一个值为False，则不再看第二个值
#   
# or 逻辑或
#   or 可以对符号两侧的值进行或运算
#    或运算两个值中只要有一个True，就会返回True
#    或运算是找True的
#    Python中的或运算是短路的或，如果第一个值为True，则不再看第二个值
#    
# 练习：
#   尝试一下对布尔值进行三种逻辑运算
#   尝试对非布尔值进行三种逻辑运算，并观察返回的结果   
#   

a = True
a = not a # 对a进行非运算

a = 1
a = ''
a = not a
# print('a =',a)

result = True and True # True
result = True and False # False
result = False and True # False
result = False and False # False

# print(result) 

# True and print('你猜我出来吗？') 第一个值是True，会看第二个值，所以print()会执行
# False and print('你猜我出来吗？')第一个值是False，不会看第二个值，所以print()不会执行


result = True or True # True
result = True or False # True
result = False or True # True
result = False or False # False

# print(result) 
# False or print('你猜我出来吗？') 第一个值为False，继续看第二个，所以打印语句执行
# True or print('你猜我出来吗？') 第一个值为True，不看第二个，所以打印语句不执行

# 非布尔值的与或运算
#   当我们对非布尔值进行与或运算时，Python会将其当做布尔值运算，最终会返回原值
#   与运算的规则
#       与运算是找False的，如果第一个值是False，则不看第二个值
#       如果第一个值是False，则直接返回第一个值，否则返回第二个值
#   或运算的规则
#       或运算是找True的，如果第一个值是True，则不看第二个值
#       如果第一个值是True，则直接返回第一个值，否则返回第二个值    

# True and True
result = 1 and 2 # 2
# True and False
result = 1 and 0 # 0
# False and True
result = 0 and 1 # 0
# False and False
result = 0 and None # 0

# True or True
result = 1 or 2 # 1
# True or False
result = 1 or 0 # 1
# False or True
result = 0 or 1 # 1
# False or False
result = 0 or None # None

print(result)