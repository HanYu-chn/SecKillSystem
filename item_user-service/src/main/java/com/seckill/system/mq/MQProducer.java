package com.seckill.system.mq;

import com.alibaba.fastjson.JSON;
import com.seckillSystem.error.BusinessException;
import com.seckillSystem.service.OrderService;
import com.seckillSystem.service.StockWaterService;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Component
public class MQProducer {

    private TransactionMQProducer producer;

    @Autowired
    private OrderService orderService;

    @Autowired
    private StockWaterService stockWaterService;

    @Value("${mq.nameserver.addr}")
    private String nameServerAddr;

    @Value("${mq.topicname}")
    private String topic;

    @PostConstruct
    public void init() throws MQClientException {
        producer = new TransactionMQProducer("producerGroup");
        producer.setNamesrvAddr(nameServerAddr);
        producer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                Map<String, Integer> argsMap = (Map<String, Integer>)o;
                Integer itemId = argsMap.get("itemId");
                Integer userId = argsMap.get("userId");
                Integer promoId = argsMap.get("promoId");
                Integer amount = argsMap.get("amount");
                Integer orderWaterId = argsMap.get("orderWaterId");
                try{
                    orderService.creatOrder(userId,itemId,amount,promoId,orderWaterId);
                }catch (BusinessException ex) {
                    ex.printStackTrace();
                    stockWaterService.updateStockWater(2,orderWaterId);
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                return LocalTransactionState.COMMIT_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("我来检查啦---------------------------------");
                String jsonString  = new String(messageExt.getBody());
                Map<String,Integer>map = JSON.parseObject(jsonString, Map.class);
                Integer orderWaterId = map.get("orderWaterId");
                Integer status = stockWaterService.getStatus(orderWaterId);
                if(status == 0) {
                    return LocalTransactionState.UNKNOW;
                } else if(status == 1) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else  {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
            }
        });
        producer.start();
    }

    public boolean sendMessage(Integer userId,Integer itemId, Integer amount, Integer promoId,Integer orderWaterId){
        Map<String,Integer> map = new HashMap<>();
        map.put("itemId",itemId);
        map.put("amount",amount);
        map.put("orderWaterId",orderWaterId);
        Map<String,Integer> argsMap = new HashMap<>();
        argsMap.put("itemId",itemId);
        argsMap.put("amount",amount);
        argsMap.put("userId",userId);
        argsMap.put("promoId",promoId);
        argsMap.put("orderWaterId",orderWaterId);
        Message message = new Message(topic,"increase",
                (JSON.toJSON(map).toString()).getBytes(Charset.forName("UTF-8")));
        TransactionSendResult sendResult = null;
        try {
            sendResult = producer.sendMessageInTransaction(message, argsMap);
        } catch (MQClientException e) {
            e.printStackTrace();
            return false;
        }
        if(sendResult.getLocalTransactionState() == LocalTransactionState.COMMIT_MESSAGE) {
            return true;
        } else {
            return false;
        }
    }
}
