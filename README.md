# JavaWebCRM
JavaWeb project bases on Servvlet

one project from bjpowernode;

多对多类型的数据   
不应该在任何一张表中建立外键   
而是应该再建立一张关系表来描述两张表之间的关系
主键      表一的外键       表二的外键


Cache 缓存机制 解决数据字典存储问题     
缓存机制就是将数据存储在服务器的内存中，只要服务器没有关闭或者销毁该部分内存，数据就一直存在    
application(全局作用域，也叫上下文作用域)   
在服务器启动阶段，加数据保存到服务器缓存中，application.setAttribute()，取出getAttribute()


数据字典：程序中做表单元素选择内容用的相关的数据，例如下拉框，单选框，复选框等  
数据字典旁边应用在下拉框中。