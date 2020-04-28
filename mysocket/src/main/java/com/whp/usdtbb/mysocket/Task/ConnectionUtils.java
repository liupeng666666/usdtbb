package com.whp.usdtbb.mysocket.Task;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {
    /**
     * 获取连接
     *
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        // 设置服务地址
        factory.setHost("47.75.124.131");
//		factory.setHost("192.168.0.111");
        // 端口
        factory.setPort(5672);
//		// vhost
        factory.setVirtualHost("/");
        // 用户名
        factory.setUsername("admin");
//		factory.setUsername("root");
        // 密码
        factory.setPassword("1234567a?");
        return factory.newConnection();
    }

    /**
     * 关闭连接
     *
     * @param channel
     * @param con
     */
    public static void close(Channel channel, Connection con) {
        if (channel != null) {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
