package com.kafka.consume;
public class ConsumeServer 
{
    public static void main( String[] args )
    {
        KafkaConsumerHandle handle = new KafkaConsumerHandle("consume_1");
        handle.start();
    }
}
