package com.wangx.eshop.inventory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * 库存府库项目入口类
 * @author wangx
 * @date 2019/03/01
 */
@SpringBootApplication
@ConfigurationProperties
@EnableTransactionManagement
@EnableAutoConfiguration
@ComponentScan
@MapperScan("com.wangx.eshop.inventory.mapper")
public class EshopInventoryApplication {

    @Value("${spring.redis.cluster.nodes}")
    private String nodes;

    /**
     * 初始化JedisCluster bean
     * @return
     */
    @Bean
    @ConditionalOnClass(JedisCluster.class)
    public JedisCluster jedisCluster() {
        Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
        String[] jedisClusters = nodes.split(",");

        for (String node : jedisClusters) {
            String[] ipAndPost = node.split(",");
            String host = node.split(":")[0];
            Integer port = Integer.parseInt(node.split(":")[1]);
            hostAndPorts.add(new HostAndPort(host,port));
        }
        JedisCluster jedisCluster = new JedisCluster(hostAndPorts);
        return jedisCluster;
    }
    /**
     * spring boot application 项目启动方法
     * @author wangx
     * @date 2019/03/01
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(EshopInventoryApplication.class, args);
    }

}
