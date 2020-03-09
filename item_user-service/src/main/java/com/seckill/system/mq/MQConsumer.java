package com.seckill.system.mq;

import com.alibaba.fastjson.JSON;
import com.seckill.system.mapper.StockMapper;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
public class MQConsumer {

    private DefaultMQPushConsumer consumer;

    @Autowired
    private StockMapper stockMapper;

    @Value("${mq.nameserver.addr}")
    private String nameServerAddr;

    @Value("${mq.topicname}")
    private String topic;

    @PostConstruct
    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer("consumerGroup");
        consumer.setNamesrvAddr(nameServerAddr);
        consumer.subscribe(topic, "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                //实现库存真正到数据库内扣减的逻辑
                Message msg = msgs.get(0);
                String jsonString  = new String(msg.getBody());
                Map<String,Integer> map = JSON.parseObject(jsonString, Map.class);
                Integer itemId = map.get("itemId");
                Integer amount = map.get("amount");

                stockMapper.decreaseStock(itemId,amount);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
