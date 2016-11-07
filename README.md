Carousel旨在提供一个轻量级的、易于通过负载来快速增加处理能力的中间服务中间件。

Carousel本身并不实现具体的业务逻辑，而是提供服务的管理与调用，以降低系统间的耦合。

> Carousel基于[Tephra](https://github.com/heisedebaise/tephra)构建。

> Carousel服务通过IP白名单进行授信访问，因此需要将请求方的IP地址添加到IP白名单中。

# 消息服务

消息服务模块主要提供消息（数据）的同步、异步通知，为多个应用系统提供统一的消息处理机制。

[流程配置](carousel-ms/doc/config.md)

[发起流程](carousel-ms/doc/execute.md)

[失败处理](carousel-ms/doc/failure.md)

[设置参数](carousel-ms/doc/ms-config.md)

# 发现服务

发现服务模块主要提供开放服务的注册与调用。

[注册服务](carousel-ds/doc/register.md)

[调用服务](carousel-ds/doc/execute.md)

[自动检测](carousel-ds/doc/validate.md)

