package com.kafka.produce;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

public class ProduceServer 
{
    public static void main( String[] args )
    {
    	String produceKey = "produce_1";//可用来分服务器发送
    	ProduceSendManager.init();
    	ScheduledExecutorService scheduled =Executors.newScheduledThreadPool(2);
    	try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        for(int i=0;i<100;i++){
        	KafkaJsonObj obj = new KafkaJsonObj();
        	obj.id = i;
        	obj.value = "tsingyi"+i;
        	String gson = new Gson().toJson(obj);
            System.out.println("meta");
        	KafkaProduceHandle handle = new KafkaProduceHandle(KafkaTopicConstants.TOPIC_TSINGYI, produceKey, gson);
        	scheduled.schedule(handle, i, TimeUnit.SECONDS);
        	//scheduled.scheduleWithFixedDelay(handle, 0,1, TimeUnit.SECONDS);
        }
    }
}
