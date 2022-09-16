package com.mongcent.bootstrap.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * @author zl
 */
@Configuration
public class MongodbAutoConfig {
    @Value("${spring.data.mongodb.source-uri}")
    private String riskManagerUri;

    @Value("${spring.data.mongodb.statistics-uri}")
    private String tnaotUri;

    @Bean(name = "mongoTemplate")
    @Primary
    public MongoTemplate mongoTemplate() {
        MongoClientURI mongoClientUri = new MongoClientURI(riskManagerUri);
        MongoClient mongoClient = new MongoClient(mongoClientUri);
        return new MongoTemplate(mongoClient, mongoClientUri.getDatabase());
    }

    @Bean(name = "recommendMongoTemplate")
    public MongoTemplate recommendMongoTemplate() {
        MongoClientURI mongoClientUri = new MongoClientURI(tnaotUri);
        MongoClient mongoClient = new MongoClient(mongoClientUri);
        return new MongoTemplate(mongoClient, mongoClientUri.getDatabase());
    }
}
