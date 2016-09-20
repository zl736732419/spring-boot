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