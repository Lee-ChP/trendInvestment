package org.ksl;

import cn.hutool.core.net.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Keelahselai
 * @date 2021/05/06
 * description ： 服务发现中心启动类
 */
@SpringBootApplication
@EnableEurekaServer
@Slf4j
public class EurekaServerApplication {
    public static void main(String[] args) {
        int port = 8761;
        if (!NetUtil.isUsableLocalPort(port)) {
            log.error("服务发现中心：端口号{}已被占用！",port);
            System.exit(1);
        }
        //Attention: "server.port=" + port 中间不要加空格 {用于覆盖配置文件中误配置的server.port参数}
        new SpringApplicationBuilder(EurekaServerApplication.class).properties("server.port=" + port).run(args);
    }
}
