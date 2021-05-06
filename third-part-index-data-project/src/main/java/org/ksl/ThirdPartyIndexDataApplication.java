package org.ksl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author GaoHao
 * @date 2021/05/06
 * description 第三方数据服务
 */
@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class ThirdPartyIndexDataApplication {
    public static void main(String[] args) {
        int port = 8090;
        int eurekaServerPort = 8761;
        if (NetUtil.isUsableLocalPort(eurekaServerPort)) {
            log.error("检查到端口{}未启用——eureka服务器未启动，导致第三方数据服务无法使用，故退出！", eurekaServerPort);
            System.exit(1);
        }

        if (null != args && 0!=args.length) {
            for (String arg : args) {
                if (arg.startsWith("port=")) {
                    String startPort = StrUtil.subAfter(arg,"port=",true);
                    if (NumberUtil.isNumber(startPort)) {
                        port = Convert.toInt(startPort);
                    }
                }
            }
        }

        if (!NetUtil.isUsableLocalPort(port)) {
            log.error("端口号{}被占用，无法启动第三方数据服务！", port);
            System.exit(1);
        }

        new SpringApplicationBuilder(ThirdPartyIndexDataApplication.class).properties("server.port="+port).run(args);
    }
}
