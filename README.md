# enhanced.mybaits.generator
增强的Mybaits Generator的插件,将会自动生成指定表的标准实体类、表单类、返回结果类、Mapper、SqlMapper、Service和Service的实现类。并生成标准的注释和版权声明。

该代码生成器主要用于生成一套项目组内部标准的代码模版，基于生成的代码做后续开发，便于统一项目中的代码样式，提升开发效率，提高代码质量。

项目github URL：
[https://github.com/Feiyizhan/enhanced.mybaits.generator](https://github.com/Feiyizhan/enhanced.mybaits.generator)

项目已经提交到Maven中央仓库，可以在各Maven库中搜到到该插件的发布的最新包。
[https://search.maven.org/artifact/io.github.Feiyizhan/enhanced.mybaits.generator/1.0.2/jar](https://search.maven.org/artifact/io.github.Feiyizhan/enhanced.mybaits.generator/1.0.2/jar)



## 使用

增加以下的`dependency`：

```xml
<dependency>
  <groupId>io.github.Feiyizhan</groupId>
  <artifactId>enhanced.mybaits.generator</artifactId>
  <version>${version}</version>
</dependency>
```

使用Mybaits Generator启动方法启动
参考：[http://www.mybatis.org/generator/running/running.html](http://www.mybatis.org/generator/running/running.html)


## 1.0.X版本

### 实体类、表单类、返回类
- 使用Lombok处理`GET`、`SET`、`ToSting`、`HashCode`、`Equals`
- 会根据配置的表的描述，生成相应的注释类注释。
- 所有字段会根据获取到的数据库表字段的描述，生成相应的注释。
- 日期类型使用LocalDateTime

### Mapper 接口
Mapper接口会根据配置的表的描述生成相应的接口描述

- 方法名替换
`selectByPrimaryKey` -> `getByPrimaryKey`  获取指定主键对应的记录
`selectAll` -> `listAll`  获取所有记录

- 新增的方法
`getByPrimaryKeyAndLocked`  获取指定主键对应的记录并锁定

调整了方法生成顺序，并每个方法增加了对应的注释。

### SQLMapper
增加了版权声明的注释，Mapper下的每个节点都增加了相应的注释，对应的Java对象使用短类名格式，对于全字段的操作通过Include标签做代码复用。

sqlId 替换：
`Base_Column_List` -> `all_column_list` 全字段列表
`selectByPrimaryKey` -> `getByPrimaryKey`  获取指定主键对应的记录
`selectAll` -> `listAll`  获取所有记录

### Service
默认生成增删改查方法

方法如下：

- `insert` 新增记录
- `updateBy${PrimaryKey}` 更新指定记录
- `deleteBy${PrimaryKey}` 删除指定记录
- `getBy${PrimaryKey}` 获取主键对应的记录
- `getResultBy${PrimaryKey}` 获取主键对应的记录的返回结果对象

`${PrimaryKey}` 替换为表的主键名称列表以`And`拼接的字符串，并首字母大写。

### Service的实现类

Service类的所有方法的简单实现。

### 相关的配置变动

配置例子参考：
[generatorConfig.xml](https://github.com/Feiyizhan/enhanced.mybaits.generator/blob/master/src/main/resources/generatorConfig.xml)

#### Context 配置
修改`Context`节点的`targetRuntime`属性为`enhanced.mybaits.generator.EnhanceIntrospectedTableMyBatis3SimpleImpl`。


#### 插件配置
增加了新的插件,新增的代码文件通过重写插件的`contextGenerateAdditionalJavaFiles` 的方法实现，配置如下：
`<plugin type="enhanced.mybaits.generator.plugin.EnhancePlugin"/>`

#### 注释生成器配置
只有配置了注释生成器为`EnhanceCommentGenerator`才会有相应的注释生成。`EnhanceCommentGenerator` 增加了一个新的配置`author`,用于生成注释中的`作者`信息，注释生成器配置如下：

```xml
<commentGenerator type="enhanced.mybaits.generator.codegen.EnhanceCommentGenerator">
                        <!-- 是否去除自动生成的注释 true：是 ： false:否 -->
                        <property name="suppressAllComments" value="false" />
                        <property name="addRemarkComments" value="true" />
                        <property name="dateFormat" value="yyyy-MM-dd" />
                        <property name="author" value="徐明龙 XuMingLong" />
</commentGenerator>
```

#### Java类型解析器配置

配置Java类型解析器为`EnhanceJavaTypeResolverImpl`，用于所有的日期类型由`Date`替换为`LocalDateTime`,配置的如下：

```xml
<javaTypeResolver type="enhanced.mybaits.generator.EnhanceJavaTypeResolverImpl">
</javaTypeResolver>
```

#### Java Client生成器配置
由于新增的代码文件主要基于Java Client生成的，因此涉及的新增的配置都放在Java Client生成器配置中，新增了一些配置:

 - `testClientTargetPackage` 测试Dao类的测试类包名;
 - `formTargetPackage` 生成的Form类的包名;
 - `formIgnoreFieldList` 生成的Form类忽略的字段列表。不是所有的实体类字段都需要在生成到Form类，比如审计信息字段，默认主键字段不会生成到Form类，可以在这里配置需要忽略的字段列表，以`,`分隔;
 - `resultTargetPackage` 生成的Result类的包名;
 - `serviceTargetPackage` 生成的Service类的包名;
 - `serviceImplTargetPackage` 生成的Service实现类的包名;
 - `userClassName` 生成的代码在填充审计信息时需要用到的用户信息类名，该类必须提供`getId`,`getName`的方法。
 - `standardCheckAndHandleDtoClassName` 生成的Service接口的新增方法返回的参数对象类名，该类必须包括一个`FormValidError`字段和一个泛型的`Result`字段代码参考：[StandardCheckAndHandleDTO.java](https://github.com/Feiyizhan/enhanced.mybaits.generator/blob/master/src/main/java/enhanced/mybaits/generator/example/StandardCheckAndHandleDTO.java)
 - `formValidErrorClassName`  生成的Service接口增删改方法返回的校验结果信息，该类的代码参考:[FormValidError.java](https://github.com/Feiyizhan/enhanced.mybaits.generator/blob/master/src/main/java/enhanced/mybaits/generator/example/FormValidError.java)
 - `testTargetProject` 测试类存放的项目目录
 - `testSpringBootMainClass`Junit测试类`@SpringBootTest`注解的`classes`属性需要指定的测试启动类，设置为项目对应的Spring Boot启动类即可.


```xml
<javaClientGenerator targetPackage="com.xxx.manager.dao" type="XMLMAPPER" targetProject="src/main/java">
                        <!-- 在targetPackage的基础上，根据数据库的schema再生成一层package，最终生成的类放在这个package下，默认为false -->
                        <property name="enableSubPackages" value="false" />

                        <!-- 可以为所有生成的接口添加一个父接口，但是MBG只负责生成，不负责检查 <property name="rootInterface" value=""/> -->
                        <!-- 测试Client类目存放的包名 -->
                        <property name="testClientTargetPackage" value="com.xxx.mapper"/>
                        <!-- Form类存放目的包名 -->
                        <property name="formTargetPackage" value="com.xxx.form"/>
                        <!-- Form类忽略的字段列表 -->
                        <property name="formIgnoreFieldList" value="createDate,creator,creatorId,updateDate,modifier,modifierId"/>

                        <!-- Result类存放目的包名 -->
                        <property name="resultTargetPackage" value="com.xxx.result"/>
                        <!-- Service接口存放目的包名 -->
                        <property name="serviceTargetPackage" value="com.xxx.service"/>
                        <!-- Service接口实现类存放目的包名 -->
                        <property name="serviceImplTargetPackage" value="com.xxx.service.impl"/>
                        <!-- 用户信息类名 -->
                        <property name="userClassName" value="com.xxx.entity.User"/>
                        <!-- 标准的校验和处理结果类名 -->
                        <property name="standardCheckAndHandleDtoClassName" value="com.xxx.pojo.StandardCheckAndHandleDTO"/>
                        <!-- 表单验证失败内容参数类名 -->
                        <property name="formValidErrorClassName" value="com.xxx.pojo.FormValidError"/>
                        <!-- 静态的生成当前日期公共方法 -->
                        <property name="nowUtils" value="com.xxx.utils.CommonsUtils.now"/>
                        <!-- 测试类存放目的项目目录 -->
                        <property name="testTargetProject" value="src/test/java"/>
                        <!-- 测试类需要的Spring Boot启动类 -->
                        <property name="testSpringBootMainClass" value="com.xxx.Application"/>

</javaClientGenerator>
```

#### table的配置
由于Mybaits Generator获取表的描述信息处理代码获取不到Mysql表的描述信息，因此增加了一个`table`节点的配置`table_comment`用于补充表的描述,如果Mybaits Generator默认能获取到表的描述，将会优先使用Mybaits Generator获取到的表的描述。配置如下:

```xml
<table tableName="test_table" delimitAllColumns="true">
     <property name="table_comment" value="测试表的描述"/>
</table>
```

