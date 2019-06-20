package org.darod.elearning.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/20 0020 14:24
 */
@SpringBootApplication(scanBasePackages = {"org.darod"})
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
