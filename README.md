## 基于Spring Boot + MyBatis plus + Redis的电影网站的设计与实现.
  运用Spring boot + MyBatis plus框架，设计与实现一个电影网站。 功能包括：

- 1）会员注册、登录和退出；会员账号分为vip账号和普通账号。
- 2）影片展示：按热播排行展示、按类型展示、按地区展示；
- 3）电影排行：本周排行，本月排行，全部排行，按好评排行等
- 4）主创作品：根据电影主创（演员），检索该演员主演的电影，或根据导演搜索。
- 5）权限控制：设置播放权限，区分vip账号和普通账号，vip可观看vip影片，普通账号不能观看vip影片。



## 功能

| 功能                                                                                                          |     |
|:-------------------------------------------------------------------------------------------------------------|-----|
| 数据库设计及数据搜集                                                                                             |     |
| 会员注册、会员登录、退出、会员账号分为vip账号和普通账号                                                                 |     |
| 主页、影片展示、分页展示                                                                                          |     | 
| 电影排行                                                                                                       |     |    
| 主创作品                                                                                                       |     |     
| 设置播放<span style="color:orange;">权限</span>，区分vip账号和普通账号，vip可观看vip影片，普通账号不权限控制:能观看 vip 影片 |     |  
| 其他功能(加分)                                                                                                  |     |
|                                                                                                               |     |                                               
|(1)POI报表技术，生成EXCEL报表，打印电影播放榜单                                                                       |     |
|(2)echarts中文api图表展现，至少生成两种样式图表;运用到系统                                                              |     |  
|(3)使用支付宝沙箱支付                                                                                              |     |

