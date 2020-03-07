package cn.edu.cqu.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

//当Spring容器内没有TomcatEmbeddedServletContainerFactroy这个bean时，会把此bean加载进Spring
@Component
public class WebServerConfiguration implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        ((TomcatServletWebServerFactory)factory).addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                Http11NioProtocol protocol = (Http11NioProtocol)connector.getProtocolHandler();

                //定制化keppalivetimeout，设置30秒内没有请求则服务器端自动断开keppalive连接
                protocol.setKeepAliveTimeout(30000);
                //当客户端发送超过10000个请求则自动断开keppalive连接
                protocol.setMaxKeepAliveRequests(10000);
            }
        });
    }
}
