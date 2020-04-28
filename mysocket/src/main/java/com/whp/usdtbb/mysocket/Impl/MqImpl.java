package com.whp.usdtbb.mysocket.Impl;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.*;
import com.whp.usdtbb.mysocket.Interface.MqBbOrderInterface;
import com.whp.usdtbb.mysocket.Interface.MqInterface;
import com.whp.usdtbb.mysocket.Task.ConnectionUtils;
import com.whp.usdtbb.mysocket.socket.WebSocketUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : 张吉伟
 * @data : 2019/6/16 14:17
 * @descrpition :
 */
@Service
public class MqImpl implements MqInterface {

    @Autowired
    private MqBbOrderInterface mqBbOrderInterface;

    @Override
    public JSONObject MqTask(JSONObject json) {
        try {
            // 获取连接
            Connection con = ConnectionUtils.getConnection();
            // 从连接中创建通道
            Channel channel = con.createChannel();
            // 声明队列
            channel.queueDeclare(json.getString("queue"), true, false, false, null);
//            channel.exchangeDeclare("usdt", BuiltinExchangeType.FANOUT,
//                    true, false, false, null);
            channel.queueBind(json.getString("queue"), "usdt", json.getString("queue"));
            channel.basicConsume(json.getString("queue"), false, "usdt", new DefaultConsumer(channel) {
                // 获取消息
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String msg = new String(body, "utf-8");
                    JSONObject json = new JSONObject();
                    json.put("message", msg);
                    WebSocketUtils.SocketXunHun(json);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }

            });
            // 监听队列

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject MqRedis(JSONObject json) {
        try {
            // 获取连接
            Connection con = ConnectionUtils.getConnection();
            // 从连接中创建通道
            Channel channel = con.createChannel();
            // 声明队列
            channel.queueDeclare(json.getString("queue"), true, false, false, null);
//            channel.exchangeDeclare("usdt", BuiltinExchangeType.FANOUT,
//                    true, false, false, null);
            channel.queueBind(json.getString("queue"), "usdt", json.getString("queue"));
            channel.basicConsume(json.getString("queue"), false, "usdt", new DefaultConsumer(channel) {
                // 获取消息
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String msg = new String(body, "utf-8");
                    JSONObject json = JSONObject.parseObject(msg);
                    mqBbOrderInterface.MqBbOrder(json);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }

            });
            // 监听队列

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject MqInsert(JSONObject json) {
        JSONObject fjson = new JSONObject();
        Connection con = null;
        Channel channel = null;
        try {
            // 获取连接
            con = ConnectionUtils.getConnection();
            // 从连接中创建通道
            channel = con.createChannel();
            // 声明一个队列
            channel.queueDeclare(json.getString("queue"), true, false, false, null);
            channel.exchangeDeclare("usdt", BuiltinExchangeType.TOPIC,
                    true, false, false, null);
            channel.queueBind(json.getString("queue"), "usdt", json.getString("queue"));
            // 发送消息
            channel.basicPublish("usdt", json.getString("queue"), null, json.getString("message").getBytes());
            System.out.println("send success");
            fjson.put("code", 100);
        } catch (IOException e) {
            e.printStackTrace();
            fjson.put("code", 103);
        } catch (TimeoutException e) {
            e.printStackTrace();
            fjson.put("code", 103);
        } finally {
            // 关闭连接
            System.out.println("关闭连接");
            ConnectionUtils.close(channel, con);
        }
        return fjson;
    }
}
