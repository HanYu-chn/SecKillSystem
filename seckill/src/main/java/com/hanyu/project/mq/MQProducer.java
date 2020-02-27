package com.hanyu.project.mq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Component
public class MQProducer {
    private DefaultMQProducer defaultMQProducer;

    @Value("${mq.nameserver.addr}")
    private String nameServerAddr;

    @Value("${mq.topicname}")
    private String topic;

    @PostConstruct
    public void init() throws MQClientException {
        defaultMQProducer = new DefaultMQProducer("producerGroup");
        defaultMQProducer.setNamesrvAddr(nameServerAddr);
        defaultMQProducer.start();
    }

    public boolean sendMessage(Integer itemId, Integer amount){
        Map<String,Integer> map = new HashMap<>();
        map.put("itemId",itemId);
        map.put("amount",amount);
        Message message = new Message(topic,"increase",
                (JSON.toJSON(map).toString()).getBytes(Charset.forName("UTF-8")));
        try {
            defaultMQProducer.send(message);
            return true;
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
