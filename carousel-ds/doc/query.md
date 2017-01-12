# 检索服务

URI - /discovery/query

参数：
- key 服务key，模糊匹配。
- service 服务URL地址，模糊匹配。
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
            "key": "服务key",
            "service": "服务URL地址",
            "validate": "验证URL地址",
            "success": "验证成功标识",
            "state": "状态：0-正常，>0-异常",
            "register": "注册时间"
        }
    ]
}
```
