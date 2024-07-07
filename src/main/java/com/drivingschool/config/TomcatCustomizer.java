package com.drivingschool.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class TomcatCustomizer {

    @Bean
    public ConfigurableServletWebServerFactory configurableServletWebServerFactory(){
        log.info("Tomcat新配置");
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.setProtocol("org.apache.coyote.http11.Http11NioProtocol");
        factory.setPort(8850);
        factory.addConnectorCustomizers( connector -> {
            Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
            protocol.setDisableUploadTimeout(false);
            //protocol.setAcceptCount(200);
            //protocol.setMaxConnections(200);
            protocol.setMaxHeaderCount(20000);
            protocol.setConnectionTimeout(20000);
            protocol.setMaxHttpHeaderSize(209715200);
            protocol.setMaxSavePostSize(4194304);
        } );
        return factory;
    }
}