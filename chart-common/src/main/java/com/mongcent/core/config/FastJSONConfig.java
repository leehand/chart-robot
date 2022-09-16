package com.mongcent.core.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置fastjson替换jackson
 * 
 */
@Configuration
public class FastJSONConfig {

	@Bean
	public HttpMessageConverters customConverters() {
		FastJsonHttpMessageConverter fastConvert = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		// SerializerFeature.BrowserCompatible 解决浏览器出现解析bigint类型参数的错误  
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,SerializerFeature.DisableCircularReferenceDetect);
        //处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConvert.setSupportedMediaTypes(fastMediaTypes);
		fastConvert.setFastJsonConfig(fastJsonConfig);
		return new HttpMessageConverters(fastConvert);
	}

}