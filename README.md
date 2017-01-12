Carousel旨在提供一个轻量级的、易于通过负载来快速增加处理能力的中间服务中间件。

Carousel本身并不实现具体的业务逻辑，而是提供服务的管理与调用，以降低系统间的耦合。

> Carousel基于[Tephra](https://github.com/heisedebaise/tephra)构建。

> Carousel服务通过IP白名单进行授信访问，因此需要将请求方的IP地址添加到IP白名单中。


- [消息服务](carousel-ms/)
- [发现服务](carousel-ds/)

