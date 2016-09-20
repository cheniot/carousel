# 发起流程
发起流程只需提交HTTP请求到部署地址：
```
http://ip:port/carousel/process/execute
```
即可，并提交配置名称参数与处理数据，如：
```
http://localhost:8080/carousel/process/execute?name=hello&delay=10&data=carousel
```
或：
```
curl -l -H "Content-type: application/json" -X POST -d 'name=hello&delay=10&data=carousel' http://localhost:8080/carousel/process/execute
{"code":0,"data":{"code":0,"data":"hello carousel"}}
```
参数说明如下：
- name：流程配置名称。
- delay：延迟执行时间，单位：秒。如果未设置或小于等于零则使用流程配置定义的延迟时间；如果流程配置亦未定义延迟时间，则立即执行。
- data：要处理的数据，如果是GET请求需进行URL encode。
