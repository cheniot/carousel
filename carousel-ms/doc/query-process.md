# 检索流程

URI - /process/query

参数：
- config 配置名称。
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
            "config": "配置ID值",
            "step": "执行步骤",
            "in": "输入数据",
            "out": "输出数据",
            "start": "开始时间",
            "end": "结束时间",
            "times": "重复执行次数",
            "state": "状态：0-未开始；1-已完成；2-已取消",
            "failure": "失败次数"
        }
    ]
}
```

> 如果配置信息不存在则返回空JSON“{}”对象。
