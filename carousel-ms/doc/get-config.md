# 获取配置

URI - /config/get

参数：
- id 配置ID。

返回：
```json
{
    "id": "ID值。",
    "name": "名称",
    "description": "描述",
    "delay": "延迟执行时间",
    "interval": "重复执行间隔",
    "times": "重复执行次数",
    "wait": "是否等待执行结果：0-否；1-是",
    "value": "配置值",
    "time": "更新时间"
}
```
