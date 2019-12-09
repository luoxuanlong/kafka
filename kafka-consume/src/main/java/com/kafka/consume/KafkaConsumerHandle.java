package com.kafka.consume;


import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.google.gson.Gson;


public class KafkaConsumerHandle extends Thread {

	String brokeServers = "49.233.142.42:9092,49.233.142.115:9092,49.233.92.75:9092";
	//String brokeServers = "192.160.2.113:9092";
	String produceKey = "produce_1";
	KafkaConsumer<String, String> consumer = null;

	public KafkaConsumerHandle(String groupId) {
		Properties props = new Properties();
		props.put("bootstrap.servers", brokeServers);
		props.put("group.id", groupId);
		props.put("enable.auto.commit", "false");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer = new KafkaConsumer<String, String>(props);
		consumer.subscribe(Arrays.asList(KafkaTopicConstants.TOPIC_TSINGYI));
	}

	public void run() {
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			if(records.count() > 0)
				consumer.commitAsync();
			for (ConsumerRecord<String, String> record : records){
				if(produceKey.equals(record.key())){
					KafkaJsonObj obj = new Gson().fromJson(record.value(), KafkaJsonObj.class);
					System.out.println("收到kafka消息："+obj.value+" time="+System.currentTimeMillis());
				}
				
			}
		}
	}

}
