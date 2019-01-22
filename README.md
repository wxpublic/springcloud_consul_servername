# springcloud_consul_servername
使用consul作为注册中心代替SpringCloud中的Eureka，
本项目类似springcloud_consul项目，不同之处在于本项目采用spring.application.name配置属性获取远端生产者服务实例，并手工定义变量轮询多生产者服务。

# 获取服务生产者实例：
@Autowired DiscoveryClient discoveryClient;

@Autowired private RestTemplate restTemplate;

List instances = discoveryClient.getInstances("consul-member");
