# enhanced.mybaits.generator
增强的Mybaits Generator的插件,将会自动生成指定表的标准实体类、表单类、返回结果类、Mapper、SqlMapper、Service和Service的实现类。并生成标准的注释和版权声明。

## 实体类、表单类、返回类
- 使用Lombok处理GET、SET、ToSting、HashCode、Equals
- 会根据配置的表的描述，生成相应的注释类注释。
- 所有字段会根据获取到的数据库表字段的描述，生成相应的注释。
- 日期类型使用LocalDateTime

## Mapper 接口
Mapper接口会根据配置的表的描述生成相应的接口描述

- 方法名替换
selectByPrimaryKey -> getByPrimaryKey  获取指定主键对应的记录
selectAll -> listAll  获取所有记录

- 新增的方法
getByPrimaryKeyAndLocked  获取指定主键对应的记录并锁定

调整了方法生成顺序，并每个方法增加了对应的注释。

## SQLMapper
增加了版权声明的注释，Mapper下的每个节点都增加了相应的注释，对应的Java对象使用短类名格式，对于全字段的操作通过Include标签做代码复用。

sqlId 替换：
Base_Column_List -> all_column_list 全字段列表
selectByPrimaryKey -> getByPrimaryKey  获取指定主键对应的记录
selectAll -> listAll  获取所有记录

## Service
默认生成增删改查方法

方法如下：

- insert 新增记录
- updateBy${PrimaryKey} 更新指定记录
- deleteBy${PrimaryKey} 删除指定记录
- getBy${PrimaryKey} 获取主键对应的记录
- getResultBy${PrimaryKey} 获取主键对应的记录的返回结果对象

${PrimaryKey} 替换为表的主键名称列表以`And`拼接的字符串，并首字母大写。

## Service的实现类

Service类的所有方法的简单实现。


