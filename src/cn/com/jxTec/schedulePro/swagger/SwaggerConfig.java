package cn.com.jxTec.schedulePro.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@EnableSwagger  
public class SwaggerConfig {  
  
    private SpringSwaggerConfig springSwaggerConfig;  
  
    /** 
     * Required to autowire SpringSwaggerConfig 
     */  
    @Autowired  
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig)  
    {  
        this.springSwaggerConfig = springSwaggerConfig;  
    }  
  
    /** 
     * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc 
     * framework - allowing for multiple swagger groups i.e. same code base 
     * multiple swagger resource listings. 
     */  
    @Bean  
    public SwaggerSpringMvcPlugin customImplementation()  
    {  
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)  
                .apiInfo(apiInfo())  
                .includePatterns(".*?");  
    }  
  
    private ApiInfo apiInfo()  
    {  
        ApiInfo apiInfo = new ApiInfo(  
                "Restful API",  
                "API Document管理",  
                "My Apps API terms of service",  
                "",  
                "web app",  
                "My Apps API License URL");  
        return apiInfo;  
    }  
}  