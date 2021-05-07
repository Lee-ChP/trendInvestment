package org.ksl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Keelahselai
 * @date 2021/05/07
 * description 启动类
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@EnableCaching
@Slf4j
public class IndexCollectionStoreApplication {
    public static void main(String[] args) {
        int port;
        int defaultPort = 8001;
        int eurekaServerPort = 8761;
        int redisPort = 6379;
        port = defaultPort;

        if (NetUtil.isUsableLocalPort(eurekaServerPort)) {
            log.error("检查到端口{} 未启用，判断 eureka 服务器没有启动，本服务无法使用，故退出!", eurekaServerPort);
            System.exit(1);
        }

        if(NetUtil.isUsableLocalPort(redisPort)) {
            log.error("检查到端口{} 未启用，判断 redis 服务器没有启动，本服务无法使用，故退出!", redisPort );
            System.exit(1);
        }

        if (null != args && 0 != args.length) {
            for (String arg : args) {
                if (arg.startsWith("port=")) {
                    String strPort = StrUtil.subAfter(arg, "port=", true);
                    if (NumberUtil.isNumber(strPort)) {
                        port = Convert.toInt(strPort);
                    }
                }
            }
        }

        if (!NetUtil.isUsableLocalPort(port)) {
            log.error("端口{}被占用了，无法启动!", port);
            System.exit(1);
        }
        new SpringApplicationBuilder(IndexCollectionStoreApplication.class).properties("server.port=" + port).run(args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
