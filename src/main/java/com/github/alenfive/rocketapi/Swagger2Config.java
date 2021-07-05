//package com.github.alenfive.rocketapi;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.bind.annotation.RestController;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * swagger-ui 配置
// */
//@Configuration
//@EnableSwagger2
//public class Swagger2Config {
//    @Bean public Docket createRestApi() {
//        List pars = new ArrayList();
//        ParameterBuilder ticketPar = new ParameterBuilder();
//        ticketPar.name("token")
//                .description("token")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .required(false)
//                .build();
//        pars.add(ticketPar.build());
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.any()) //所有
//                .paths(PathSelectors.any())            //所有
//                .build()
//                .globalOperationParameters(pars)
//                .apiInfo(apiInfo());
//    }
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Rocket Api")
//                .description("Rocket Api swagger-ui")
//                .termsOfServiceUrl("https://blog.csdn.net/lsqtzj")
//                .version("1.0")
//                .build();
//    }
//}