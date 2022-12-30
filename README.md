# 说明

[Kibana画图绘制指引](https://www.elastic.co/guide/cn/kibana/current/xy-chart.html)

## 读

```bash
curl http://localhost:8080/report/product/top10
```

## 写

```bash
curl  http://localhost:8080/uba/datagen\?size\=300\

curl http://localhost:8080/uba/datagen\?size\=1

大批量请求
ab -n 500 -c 5 http://localhost:8080/uba/datagen\?size\=1
```

## 请求效果

![img.png](img.png)

## DevTools测试

```bash
PUT my_index
{
  "mappings": {
    "properties": {
      "date": {
        "type": "date" 
      }
    }
  }
}

DELETE http-logs-1

PUT my_index/_doc/1
{ "date": "2015-01-01" } 

PUT my_index/_doc/2
{ "date": "2015-01-01T12:10:30Z" } 

PUT my_index/_doc/3
{ "date": 1420070400001 } 

GET my_index/_search
{
  "sort": { "date": "asc"} 
}

GET http-logs-2/_search 
{
  "sort": { "timestamp": "asc"} 
}


PUT http-logs-2
{
  "mappings": {
    "properties": {
      "timestamp": {
        "type": "date" 
      }
    }
  }
}


PUT ip-parse-java
{
  "mappings": {
    "properties": {
      "timestamp": {
        "type": "date" 
      }
    }
  }
}

PUT db-info
{
  "mappings": {
    "properties": {
      "timestamp": {
        "type": "date" 
      }
    }
  }
}
```

## 观察

URL

http://localhost:5601/app/dashboards#/view/fd00b980-8814-11ed-87ff-fd23a312ba6c?_g=(filters:!(),query:(language:kuery,query:''),refreshInterval:(pause:!t,value:0),time:(from:now-30m,to:now))&_a=(description:'',filters:!(),fullScreenMode:!f,options:(hidePanelTitles:!f,useMargins:!t),query:(language:kuery,query:''),tags:!(),timeRestore:!f,title:UpgradeMonitor,viewMode:view)

```bash


```

### kibana绘图

```bash
.es(index=http-logs-*,timefield=timestamp,metric=count).label("总请求数"),
.es(index=http-logs-*,timefield=timestamp,metric=count,q=response.status:200).label(正常请求数),
.es(index=http-logs-*,timefield=timestamp,metric=count,q=response.status:>200).bars(stack=false).color(#F44336).label(异常请求数)

```

Timelion 2:

```bash
.es(index=http-logs-*,timefield=timestamp,metric=count).label("总请求数"),
.es(index=http-logs-*,timefield=timestamp,metric=count,q=response.status:200).label(正常请求数).legend().color(#00FF00),
.es(index=http-logs-*,timefield=timestamp,metric=count,q=response.status:>200&request.uri:"http://localhost:8080/report/product/top10").color(#F44336).label(异常请求数)
.es(index=db-info,timefield=timestamp,metric=max:threadCounts).color(#F44336).label(数据库服务器请求数)
,

```

### 请求耗时的Timelion

```bash
#耗时
.es(index=http-logs-*,timefield=timestamp,metric=max:timeTaken,q=response.status:200).label(最高请求耗时),
.es(index=http-logs-*,timefield=timestamp,metric=min:timeTaken,q=response.status:200).label(最低请求耗时),
.es(index=http-logs-*,timefield=timestamp,metric=avg:timeTaken,q=response.status:200).label(平均请求耗时)

```

### 错误率 Timelion

```bash
#耗时
.es(index=http-logs-*,timefield=timestamp,metric=max:timeTaken,q=response.status:200).label(最高请求耗时),
.es(index=http-logs-*,timefield=timestamp,metric=min:timeTaken,q=response.status:200).label(最低请求耗时),
.es(index=http-logs-*,timefield=timestamp,metric=avg:timeTaken,q=response.status:200).label(平均请求耗时),


```

![请求时序图](images/请求时序图.png)

PostgreSQL RDS 10 reboot和failover的时候的切换图
![]![切换](ip_switch.png)

## 常用命令

```bash

select count(*) from user_behavior;
select count(product_id), product_id from user_behavior group by product_id order by  count(product_id) desc;

#postgresql
psql -h for-upgrade-test.cypjqpec31mg.ap-southeast-1.rds.amazonaws.com -p 5432 -U postgres runoobdb
```

## 结论

HikariCP各参数配置说明: https://github.com/brettwooldridge/HikariCP

> Java程序侧需要30s左右识别新的DNS IP变更

![30s](images/30s-java.png)

> 应用程序无法自行恢复，新的请求一直会进不来

![application_is_stuck.png](images/application_is_stuck.png)

## 各种错误

---

1. org.postgresql.util.PSQLException: 尝试连线已失败。

---

2. java.sql.SQLTransientConnectionException: HikariPool-1 - Connection is not available, request timed out after
   30006ms. at com.zaxxer.hikari.pool.HikariPool.createTimeoutException(HikariPool.java:696) ~[HikariCP-4.0.3.jar:na]
   at com.zaxxer.hikari.pool.HikariPool.getConnection(HikariPool.java:197) ~[HikariCP-4.0.3.jar:na]
   at com.zaxxer.hikari.pool.HikariPool.getConnection(HikariPool.java:162) ~[HikariCP-4.0.3.jar:na]

---

3. org.postgresql.util.PSQLException: An I/O error occurred while sending to the backend. at
   org.postgresql.core.v3.QueryExecutorImpl.execute(QueryExecutorImpl.java:382) ~[postgresql-42.3.8.jar:42.3.8]
   at org.postgresql.jdbc.PgStatement.executeInternal(PgStatement.java:490) ~[postgresql-42.3.8.jar:42.3.8]