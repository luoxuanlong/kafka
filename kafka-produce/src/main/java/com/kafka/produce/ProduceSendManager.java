package com.kafka.produce;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProduceSendManager {

	public static void init(){
		if(instance == null){
			instance = new ProduceSendManager();
		}
	}
	String brokeServers = "49.233.142.42:9092,49.233.142.115:9092,49.233.92.75:9092";
	//String brokeServers = "192.160.2.113:9092";
	private static ProduceSendManager instance;

	public static ProduceSendManager getInstance(){
		return instance;
	}
	Producer<String, String> producer = null;

	private ProduceSendManager() {
		Properties props = new Properties();
		props.put("bootstrap.servers", brokeServers);
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		try {
			producer = new KafkaProducer<>(props);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send(String topic, String key, String value) {
		try {
			producer.send(new ProducerRecord<String, String>(topic, key, value));
		} catch (Exception e) {
			if (producer != null) {
				producer.close();
				// 重连
				Properties props = new Properties();
				props.put("bootstrap.servers", brokeServers);
				props.put("acks", "all");
				props.put("retries", 0);
				props.put("batch.size", 16384);
				props.put("linger.ms", 1);
				props.put("buffer.memory", 33554432);
				props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
				props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
				try {
					producer = new KafkaProducer<>(props);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}finally {
			//producer.close();
		}
	}

}
