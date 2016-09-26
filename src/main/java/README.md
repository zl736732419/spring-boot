###运行springboot的几种方式
	1.直接在java文件中生成main方法，调用SpringApplication.run
	2.创建app.groovy脚本，通过springboot cli运行  spring run app.groovy
	3.通过maven运行
		3.1.进入到项目根路径下，运行mvn spring-boot:run
		3.2.将项目打包成Jar,通过java -jar 运行，这里需要特别说明一下，在pom.xml中没有指定package的类型，默认引用的是parent中的jar,所以打包时是生成的jar包
通过jar打包引用了spring-boot-maven-plugin插件
  查看jar包内部文件：jar tvf target/myproject-0.0.1-SNAPSHOT.jar
  
###静态资源访问顺序
    spring-boot默认提供的静态资源访问路径可以是classpath下的META-INF/resources, /resources, /static, public/
    这些个目录,访问顺序依次排开
    
####自定义静态资源访问路径
    需要自定义子类实现WebMvcConfigurerAdapter,并重写其中方法addResourceHandlers
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/images/");
        registry.addResourceHandler("/app/**").addResourceLocations("classpath:/app/");
        super.addResourceHandlers(registry);
    }
    
    
###定时任务
    @Configuration
    @EnableScheduling
    public class MyScheduleTask {
        @Scheduled(cron = "0/5 * * * * ?")
        public void scheduler() {
            System.out.println("hello task execute...");
        }
    }

    
###使用alibaba druid
#### 方式1，通过原生Servlet,Filter进行配置

    1.Application.java启动类上添加@ServletComponentScan
    2.配置Servlet
    @WebServlet(urlPatterns="/druid/*",
               initParams={
                       @WebInitParam(name="allow",value="192.168.1.72,127.0.0.1"),// IP白名单 (没有配置或者为空，则允许所有访问)
                        @WebInitParam(name="deny",value="192.168.1.73"),// IP黑名单 (存在共同时，deny优先于allow)
                        @WebInitParam(name="loginUsername",value="admin"),// 用户名
                        @WebInitParam(name="loginPassword",value="123456"),// 密码
                        @WebInitParam(name="resetEnable",value="false")// 禁用HTML页面上的“Reset All”功能
               }
    )
    public class DruidStatViewServlet extends StatViewServlet{
        privatestaticfinallongserialVersionUID = 1L;
       
    }
    3.配置Filter
    @WebFilter(filterName="druidWebStatFilter",urlPatterns="/*",
        initParams={
                 @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源
         }
    )
    public class DruidStatFilter extends WebStatFilter{
    }
####方式2 代码配置
    通过代码配置的方式，就不需要添加@ServletComponentScan
    @Configuration
    public class DruidConfiguration {
    
        @Bean
        public ServletRegistrationBean druidServletRegistryBean() {
            ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid2/*");
            //添加初始化参数
    
            //白名单
            bean.addInitParameter("allow", "127.0.0.1");
            bean.addInitParameter("deny", "192.168.1.116");
            bean.addInitParameter("loginUsername", "admin");
            bean.addInitParameter("loginPassword", "123456");
            bean.addInitParameter("resetEnable", "false");
    
            return bean;
        }
    
        @Bean
        public FilterRegistrationBean druidFilterRegistryBean() {
            FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());
    
            //添加过滤规则
            bean.addUrlPatterns("/*");
            bean.addInitParameter("exclusions", "*.js,*.css,*.bmp,*.png,*.gif,*.jpg,*.ico,/druid2/*");
    
            return bean;
        }
    }
    
### 获取ApplicationContext容器对象
####通过实现ApplicationContextAware进行注入，使用@Component注解加入到容器
    @Component
    public class SpringUtils implements ApplicationContextAware {
    
        private static ApplicationContext ctx = null;
    
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            if(SpringUtils.ctx == null) {
                SpringUtils.ctx = applicationContext;
            }
    
            System.out.println("applicationContext已经注册，可以在普通类中获取容器中的bean了...");
        }
    
        public static ApplicationContext getApplicationContext() {
            return ctx;
        }
    
        public Object getBean(String beanName) {
            return ctx.getBean(beanName);
        }
    
        public Object getBean(Class beanCls) {
            return ctx.getBean(beanCls);
        }
    
        public Object getBean(String name, Class beanCls) {
            return ctx.getBean(name, beanCls);
        }
    }
    如果不使用@component注解注入，还可以使用@Bean注解注入SpringUtils
    Application.java
    public class Application {
        ...
        @Bean 
        public SpringUtils getSpringUtils() {
            return new SpringUtils();
        }
    }
    还可以使用@Import来加载SpringUtils
    @SpringBootApplication
    @ServletComponentScan
    @Import(value={SpringUtil.class})
    public class Application {
    }
    
###自定义servlet处理用户请求
####代码注册方式
    需要在Application.java中通过ServletRegistrationBean进行注册
    /**
     * 注册自定义Servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new MyServlet(), "/myServlet/*");
    }
    
    MyServlet.java直接编写Servlet类，不添加@WebServlet
    
    /**
         * 注册自定义Servlet
         * @return
         */
        @Bean
        public ServletRegistrationBean servletRegistrationBean() {
            return new ServletRegistrationBean(new MyServlet(), "/myServlet/*");
        }
####通过注解注册
    注解注册的方式，需要在Application.java启动类上添加@ServletComponentScan扫描自定义的Servlet,Filter,Listener
    同时自定义Servlet需要添加@WebServlet(urlPatterns = "/myServlet2/*", description = "通过注解注册")
    
    定义Filter,Listener与上面定义Servlet方式相同，可以参考上面Servlet的配置，注解方式Filter对应的是@WebFilter, 
    Listener对应的是@WebListener
###自定义拦截器
    自定义拦截器不会处理自定义的Servlet请求，但是Filter会处理任何请求
    1.定义拦截器类，实现HanlderInterceptor
    2.定义MyWebMvcConfigurer,注册自定义的拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加自定义拦截器
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");

        super.addInterceptors(registry);
    }
###获取参数
    实现EnvironmentAware,可以获取到环境变量中的参数，和application.properties中配置的参数
    可以在controller，service中实现EnvironmentAware
    还可以通过@ConfigurationProperties(prefix = "spring.datasource")获取,请查看ValuePropertySourceBean.java
    如果没有指定@Component注解注入，那么需要在Application.java中添加@EnableConfigurationProperties(value={ValuePropertySourceBean.class})
    将类加入到spring容器中管理
####加载自定义配置文件
    依然通过@ConfigurationProperties来配置，但是需要指定locations参数
    @ConfigurationProperties(prefix = "wisely",locations = "classpath:config/wisely.properties")
###springboot 监控管理生产环境
    1.添加maven依赖
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-actuator</artifactId>
      </dependency>
    2.直接启动项目访问指定路径获取监控数据
    /autoconfig
    查看自动配置的使用情况，该报告展示所有auto-configuration候选者及它们被应用或未被应用的原因
    
    /configprops
    显示一个所有@ConfigurationProperties的整理列表
    
    /beans
    显示一个应用中所有Spring Beans的完整列表
    
    /dump
    打印线程栈
    
    /env
    查看所有环境变量
    
    /env/{name}
    查看具体变量值
    
    /health
    查看应用健康指标
    
    /info
    查看应用信息
    
    /mappings
    查看所有url映射
    
    /metrics
    查看应用基本指标
    
    /metrics/{name}
    查看具体指标
    
    POST
    /shutdown
    允许应用以优雅的方式关闭（默认情况下不启用）
    
    /trace
    查看基本追踪信息
    以上路径访问方式：非POST方式，直接使用http://localhost:8080/health的方式进行访问
    
    http://localhost:8080/spring-boot/health用于检测springboot中的健康指标
    默认情况下，springboot已经将下面bean注册到spring中
    
    DiskSpaceHealthIndicator     低磁盘空间检测
    DataSourceHealthIndicator  检查是否能从DataSource获取连接
    MongoHealthIndicator   检查一个Mongo数据库是否可用（up）
    RabbitHealthIndicator   检查一个Rabbit服务器是否可用（up）
    RedisHealthIndicator      检查一个Redis服务器是否可用（up）
    SolrHealthIndicator  检查一个Solr服务器是否可用（up）
    
    也可以自定义指标信息，自定义要求你需要实现HealthIndicator接口，
    响应一个包含status状态和可选用于显示的信息,代码结构如下
    @Component
    public class MyHealthIndicator implements HealthIndicator {
        @Override
        public Health health() {
            int statusCode = check();
            if(statusCode > 0) {
                return Health.down().withDetail("Error Code", statusCode).build();
            }else {
                return Health.up().build();
            }
        }
    
        /**
         * 实现一些健康状态检查
         * @return >0标识错误，=0标识正常
         */
        private int check() {
            //TODO add check logical code ...
            return 500;
        }
    }
    
    http://localhost:8080/spring-boot/info用于查看info信息，
    默认情况下是没有任何信息提示的，需要在application.properties中配置以info为前缀的参数信息
    对于info配置参数，如果要访问maven中的一些变量，可以使用@varname@的方式引用，比如
    app.build.version=@project.version@
    
### springboot测试
    在spring中提供了spring-test模块，springboot提供了在spring中测试的模块依赖
    1.引入依赖
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
    </dependency>
    
    2.在test/java中编写测试基类
    
    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringApplicationConfiguration(classes = Application.class)
    //由于是WEB项目，所以Junit需要模拟ServletContext, 因此加上WEB环境
    @WebAppConfiguration
    public class BaseServiceTest {
    }
    
    3.针对不同Service编写不同的测试类这里为UserServiceImplTest
    public class UserServiceImplTest extends BaseServiceTest {
    
        @Autowired
        private UserService userService;
    
        @Test
        public void getUser() {
            User user = userService.getUser(1L);
            System.out.println(user);
        }
    }
    除了用注解配置外，写法上与springtest相同
    
### springboot提供的依赖模块
    1）spring-boot-starter
    这是Spring Boot的核心启动器，包含了自动配置、日志和YAML。
    
    2）spring-boot-starter-actuator
    帮助监控和管理应用。
    
    3）spring-boot-starter-amqp
    通过spring-rabbit来支持AMQP协议（Advanced Message Queuing Protocol）。
    
    4）spring-boot-starter-aop
    支持面向方面的编程即AOP，包括spring-aop和AspectJ。
    
    5）spring-boot-starter-artemis
    通过Apache Artemis支持JMS的API（Java Message Service API）。
    
    6）spring-boot-starter-batch
    支持Spring Batch，包括HSQLDB数据库。
    
    7）spring-boot-starter-cache
    支持Spring的Cache抽象。
    
     
    
    8）spring-boot-starter-cloud-connectors
    支持Spring Cloud Connectors，简化了在像Cloud Foundry或Heroku这样的云平台上连接服务。
    
    9）spring-boot-starter-data-elasticsearch
    支持ElasticSearch搜索和分析引擎，包括spring-data-elasticsearch。
    
    10）spring-boot-starter-data-gemfire
    支持GemFire分布式数据存储，包括spring-data-gemfire。
    
    11）spring-boot-starter-data-jpa
    支持JPA（Java Persistence API），包括spring-data-jpa、spring-orm、Hibernate。
     
    12）spring-boot-starter-data-mongodb
    支持MongoDB数据，包括spring-data-mongodb。
     
    13）spring-boot-starter-data-rest
    通过spring-data-rest-webmvc，支持通过REST暴露Spring Data数据仓库。
     
    14）spring-boot-starter-data-solr
    支持Apache Solr搜索平台，包括spring-data-solr。
     
    15）spring-boot-starter-freemarker
    支持FreeMarker模板引擎。
     
    16）spring-boot-starter-groovy-templates
    支持Groovy模板引擎。
     
    17）spring-boot-starter-hateoas
    通过spring-hateoas支持基于HATEOAS的RESTful Web服务。
     
    18）spring-boot-starter-hornetq
    通过HornetQ支持JMS。
     
    19）spring-boot-starter-integration
    支持通用的spring-integration模块。
     
    20）spring-boot-starter-jdbc
    支持JDBC数据库。
     
    21）spring-boot-starter-jersey
    支持Jersey RESTful Web服务框架。
     
    22）spring-boot-starter-jta-atomikos
    通过Atomikos支持JTA分布式事务处理。
     
    23）spring-boot-starter-jta-bitronix
    通过Bitronix支持JTA分布式事务处理。
     
    24）spring-boot-starter-mail
    支持javax.mail模块。
     
    25）spring-boot-starter-mobile
    支持spring-mobile。
     
    26）spring-boot-starter-mustache
    支持Mustache模板引擎。
     
    27）spring-boot-starter-redis
    支持Redis键值存储数据库，包括spring-redis。
     
    28）spring-boot-starter-security
    支持spring-security。
     
    29）spring-boot-starter-social-facebook
    支持spring-social-facebook
     
    30）spring-boot-starter-social-linkedin
    支持pring-social-linkedin
     
    31）spring-boot-starter-social-twitter
    支持pring-social-twitter
     
    32）spring-boot-starter-test
    支持常规的测试依赖，包括JUnit、Hamcrest、Mockito以及spring-test模块。
     
    33）spring-boot-starter-thymeleaf
    支持Thymeleaf模板引擎，包括与Spring的集成。
     
    34）spring-boot-starter-velocity
    支持Velocity模板引擎。
     
    35）spring-boot-starter-web
    S支持全栈式Web开发，包括Tomcat和spring-webmvc。
     
    36）spring-boot-starter-websocket
    支持WebSocket开发。
     
    37）spring-boot-starter-ws
    支持Spring Web Services。
     
    Spring Boot应用启动器面向生产环境的还有2种，具体如下：
     
    1）spring-boot-starter-actuator
    增加了面向产品上线相关的功能，比如测量和监控。
     
    2）spring-boot-starter-remote-shell
    增加了远程ssh shell的支持。
     
    最后，Spring Boot应用启动器还有一些替换技术的启动器，具体如下：
     
    1）spring-boot-starter-jetty
    引入了Jetty HTTP引擎（用于替换Tomcat）。
     
    2）spring-boot-starter-log4j
    支持Log4J日志框架。
     
    3）spring-boot-starter-logging
    引入了Spring Boot默认的日志框架Logback。
     
    4）spring-boot-starter-tomcat
    引入了Spring Boot默认的HTTP引擎Tomcat。
     
    5）spring-boot-starter-undertow
    引入了Undertow HTTP引擎（用于替换Tomcat）。
