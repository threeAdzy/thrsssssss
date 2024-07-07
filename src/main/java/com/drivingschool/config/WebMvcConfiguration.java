package com.drivingschool.config;


import com.drivingschool.common.json.JacksonObjectMapper;
import com.drivingschool.interceptor.JwtTokenAdminInterceptor;
import com.drivingschool.interceptor.JwtTokenCoachInterceptor;
import com.drivingschool.interceptor.JwtTokenUserInterceptor;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * 配置类，注册web层相关组件
 * 生成swagger接口文档网页
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;
    @Autowired
    private JwtTokenCoachInterceptor jwtTokenCoachInterceptor;
    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {

        log.info("开始注册教练自定义拦截器...");
        registry.addInterceptor(jwtTokenCoachInterceptor)
                //todo 改拦截器路径
                .addPathPatterns("/coach/**")
                .excludePathPatterns("/admin/login", "/coach/login", "/user/login","/common/upload","/register","/user/Autologin");

        log.info("开始注册管理员自定义拦截器...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                //todo 改拦截器路径
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login", "/coach/login", "/user/login","/common/upload","/register","/user/Autologin");

        log.info("开始注册用户自定义拦截器...");
        registry.addInterceptor(jwtTokenUserInterceptor)
                //todo 改拦截器路径
                .addPathPatterns("/user/**")
                .excludePathPatterns("/admin/login", "/coach/login", "/user/login","/common/upload","/register","/user/Autologin");


    }


    /**
     * 通过knife4j生成接口文档
     *
     * @return
     */
//    @Bean
//    public OpenAPI docket() {
//        log.info("准备生成接口文档。。。。。");
//        ApiInfo apiInfo = new ApiInfoBuilder()
//                .title("驾校管理系统接口文档")
//                .version("2.0")
//                .description("驾校管理系统的接口文档（未完成）")//简介
//                .build();
//        OpenAPI docket = new OpenAPI(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo)
//                .select()
////                指定扫描的包
//                .apis(RequestHandlerSelectors.basePackage("com.drivingschool.controller"))
//                .paths(PathSelectors.any())
//                .build();
//        return docket;
//    }
    @Bean
    public OpenAPI springShopOpenAPI() {

        return new OpenAPI()
                // 接口文档标题
                .info(new Info().title("驾校管理系统")
                        // 接口文档简介
                        .description("这是基于Knife4j OpenApi3的接口文档")
                        // 接口文档版本
                        .version("v1.0")

                        .contact(new Contact()))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringBoot基础框架")
                        .url("http://127.0.0.1:8080"));
    }

    //    /**
//     * 设置静态资源映射
//     * 重写了WebMvcConfigurationSupport中的addResourceHandlers方法
//     * @param registry
//     */
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        log.info("开始设置静态资源。。。。");
//        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开启静态资源映射...");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }


    /**
     * 扩展spring mvc框架的消息转换器
     *
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器");

//        创建一个消息转换器
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        需要为消息转换器设置一个对象转换器，对象转换器可以将java对象序列化成json数据
        converter.setObjectMapper(new JacksonObjectMapper());
//        将自己的消息转化器加入容器中  converters.size() - 1
        converters.add(0, converter);

        super.extendMessageConverters(converters);
    }
}
