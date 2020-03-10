package com.seckillSystem.mq;

import com.alibaba.fastjson.JSON;
import com.seckillSystem.error.BusinessException;
import com.seckillSystem.error.CommonError;
import com.seckillSystem.mapper.StockMapper;
import com.seckillSystem.service.StockService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
public class MQConsumer {

    private DefaultMQPushConsumer consumer;

    @Autowired
    private StockService stockService;

    @Value("${mq.nameserver.addr}")
    private String nameServerAddr;

    @Value("${mq.topicname}")
    private String topic;

    @Autowired
    RedisTemplate redisTemplate;

    @PostConstruct
    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer("consumerGroup");
        consumer.setNamesrvAddr(nameServerAddr);
        consumer.subscribe(topic, "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                //todo 这里需要实现消息去重

                //实现库存真正到数据库内扣减的逻辑
                Message msg = msgs.get(0);
                String jsonString  = new String(msg.getBody());
                Map<String,Object> map = JSON.parseObject(jsonString, Map.class);
                Integer itemId = (Integer)map.get("itemId");
                Integer amount = (Integer)map.get("amount");

                //消息去重
                String msgUUID = (String)map.get("msgUUID");
                Boolean isExist = redisTemplate.opsForSet().isMember("message_UUID_Set", msgUUID);
                if(isExist == true) {
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                Long row = redisTemplate.opsForSet().add("message_UUID_Set", msgUUID);
                if(row != 1) {
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                boolean result = stockService.decreaseStockFromDB(itemId, amount);
                if(result == false) {
                    redisTemplate.opsForValue().increment("promo_item_stock_" + itemId, amount);
                    //todo 往消息队列中投递取消对应订单的信息
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
