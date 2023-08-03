package com.microservice.resourceservice.config;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TikaConfig {
    @Bean
    public BodyContentHandler bodyContentHandler() {
        return new BodyContentHandler();
    }

    @Bean
    public Metadata metadata() {
        return new Metadata();
    }

    @Bean
    public Mp3Parser mp3Parser() {
        return new Mp3Parser();
    }

    @Bean
    public ParseContext parseContext() {
        return new ParseContext();
    }
}
