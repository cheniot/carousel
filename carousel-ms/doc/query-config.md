# 检索配置

URI - /config/query

参数：
- name 配置名称，模糊匹配。
- pageSize 每页显示记录数。
- pageNum 当前显示页数。

返回：
```json
{
    "count": "总记录数",
    "size": "每页显示记录数",
    "number": "当前显示页数",
    "list": [
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
    ]
}
```
