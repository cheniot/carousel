# 调用服务

调用服务时，只需发送HTTP(S)请求到/discovery/execute即可，如：
```shell
curl -H "carousel-ds-key: service-key" -d "key=value" http://localhost:8080/discovery/execute
{"code":0,"data":{"code":0,"data":{"concurrent":2,"timestamp":1478502309963,"version":{"tephra":"1.0.0-RELEASE","carousel":"0.3.0-RELEASE"}}}}
```
参数说明
- carousel-ds-key 服务key，添加到header中。

其他header、参数等将直接转发到目标服务中；并返回目标服务的执行结果。
