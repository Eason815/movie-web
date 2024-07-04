## 基于Spring Boot + MyBatis plus + Redis的电影网站的设计与实现.
  运用Spring boot + MyBatis plus框架，设计与实现一个电影网站。 功能包括：

- 1）会员注册、登录和退出；会员账号分为vip账号和普通账号。
- 2）影片展示：按热播排行展示、按类型展示、按地区展示；
- 3）电影排行：本周排行，本月排行，全部排行，按好评排行等
- 4）主创作品：根据电影主创（演员），检索该演员主演的电影，或根据导演搜索。
- 5）权限控制：设置播放权限，区分vip账号和普通账号，vip可观看vip影片，普通账号不能观看vip影片。



## 功能

| 功能                                              |     |
|:------------------------------------------------|-----|
| 数据库设计及数据搜集                                      |     |
| 会员注册、会员登录、退出、会员账号分为vip账号和普通账号                   |     |
| 主页、影片展示、分页展示                                    |     | 
| 电影排行                                            |     |    
| 主创作品                                            |     |     
| 设置播放权限 区分vip账号和普通账号，vip可观看vip影片，普通账号不能观看 vip 影片 |     |  
| 其他功能(加分)                                        |     |
|                                                 |     |                                               
| (1)POI报表技术，生成EXCEL报表，打印电影播放榜单                   |     |
| (2)echarts中文api图表展现，至少生成两种样式图表;运用到系统            |     |  
| (3)使用支付宝沙箱支付                                    |     |

## 技术栈

- Spring Boot
- MyBatis/MyBatis plus
- Redis
- MySQL
- jQuery
- POI
- echarts
- Alipay

## 项目结构

```
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── cn.edu.scnu
│   │   │       ├── common
│   │   │       │   └── MD5Util.java
│   │   │       ├── config
│   │   │       │   ├── AliPayConfig.java
│   │   │       │   ├── MybatisPlusConfig.java
│   │   │       │   ├── MyLocalResovel.java
│   │   │       │   └── RedisConfig.java
│   │   │       ├── controller
│   │   │       │   ├── AliPayController.java
│   │   │       │   ├── ApiController.java
│   │   │       │   ├── IndexController.java
│   │   │       │   ├── MovieController.java
│   │   │       │   ├── MovieLikeController.java
│   │   │       │   ├── RankController.java
│   │   │       │   ├── SearchController.java
│   │   │       │   └── UserController.java
│   │   │       ├── dao
│   │   │       │   ├── GoodsMapper.java
│   │   │       │   └── OrdersMapper.java
│   │   │       ├── entity
│   │   │       │   ├── Goods.java
│   │   │       │   ├── Movie.java
│   │   │       │   ├── MovieLike.java
│   │   │       │   ├── MovieStaff.java
│   │   │       │   ├── Orders.java
│   │   │       │   ├── Staff.java
│   │   │       │   └── TbUser.java
│   │   │       ├── mapper
│   │   │       │   ├── MovieLikeMapper.java
│   │   │       │   ├── MovieMapper.java
│   │   │       │   └── UserMapper.java
│   │   │       ├── service
│   │   │       │   ├── MovieLikeService.java
│   │   │       │   ├── MovieService.java
│   │   │       │   ├── OrdersService.java
│   │   │       │   └── UserService.java
│   │   │       └── MovieWebApplication.java
│   │   └── resources
│   │       ├── application.properties

```

## 解决方案

### 1. 数据库设计及数据搜集

通过爬虫技术，在豆瓣网站收集电影数据。

(爬虫仅为自用学习研究,未有商业用途)
```
├── database
│   ├── oldDatabase
│   │   ├── dump-moviedb-202406011127.sql
│   │   └── moviedb.sql
│   ├──———— 爬取电影图片.py
│   ├──———— 爬取电影相关数据.py
│   ├──———— 补充图片数据.py
│   ├──———— 补充导演数据.py
│   └──———— 随机生成点赞数据.py


```

### 数据库设计如下

`movie`表：存放电影

`movie_like`表：存放电影的点赞(好评)数据

`movie_staff`表：存放电影的演员/导演数据

`staff`表：存放演员/导演数据

`tb_user`表：存放用户数据

`goods`表：存放商品数据

`orders`表：存放订单数据



### 2. 会员注册、会员登录、退出、会员账号分为vip账号和普通账号

参照课程第八周作业完成

- 会员注册/登录实现重定向功能
- 通过Redis存取用户的session信息
- 使用数据库数据区分vip和普通账号
- 实现国际化
- MD5加密密码


### 3. 主页、影片展示、分页展示

- 主页实现轮播图效果


- Model传参实现影片展示
- Thymeleaf实现电影详情信息展示
- 按类型、按地区(筛选操作)

- 求余计算页码实现分页展示


- HTML/CSS美化前端页面布局

### 4. 电影排行

- 简化电影排行榜功能(在一页实现)
- 热播排行、本周排行、本月排行、全部排行(排序操作)

按播放量排序：
热播排行

按好评排序：
本周排行 本月排行 全部排行

《思路是筛选操作和排序操作可以串行完成的 将其放在同一页中实现》

### 5. 主创作品

- Mybatis (plus)实现查询演员/导演的电影
- 分页

### 6. 设置播放权限

- 从Session获取用户信息 
- JS判断VIP权限，实现播放权限控制
- VIP账号可观看VIP影片，普通账号不可观看VIP影片(可以试看3秒)



### 1. POI报表技术

生成EXCEL报表，打印电影播放榜单

- 电影好评榜单
- 电影播放榜单

### 2. echarts中文api图表展现

至少生成两种样式图表;运用到系统

示例使用参考：
https://echarts.apache.org/examples/zh/index.html


- 电影好评数(动态排序柱状图)
- 电影类型(饼图扇区间隙)

### 3. 使用支付宝沙箱支付


支付宝集成参考：
https://gitee.com/xqnode/alipay-demo/tree/master/

- 内网穿透实现支付宝回调
- 实现购买VIP后，用户能变成VIP