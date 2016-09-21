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