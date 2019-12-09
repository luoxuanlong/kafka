package com.kafka.produce;


public class KafkaProduceHandle extends Thread {

	private String key;
	private String value;
	private String topic;

	public KafkaProduceHandle(String topic, String key, String value) {
		this.key = key;
		this.value = value;
		this.topic = topic;
	}

	@Override
	public void run() {
		ProduceSendManager.getInstance().send(topic, key, value);
		System.out.println("sendmessage topic="+topic +" key="+key+" message="+value+" time="+System.currentTimeMillis());
	}

}
