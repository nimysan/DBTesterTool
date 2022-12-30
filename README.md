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
.es(index=http-logs-*,timefield=timestamp,metric=count,q=response.status:>200).color(#F44336).label(异常请求数)
.es(index=db-info,timefield=timestamp,metric=max:threadCounts).color(#F44336).label(数据库服务器请求数)
,

```


![请求时序图](images/请求时序图.png)

## 常用命令

```bash

select count(*) from user_behavior;
select count(product_id), product_id from user_behavior group by product_id order by  count(product_id) desc;
```