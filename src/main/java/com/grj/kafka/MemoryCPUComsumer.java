package com.grj.kafka;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * @author Administrator 拉取kafka中的数据
 */
public class MemoryCPUComsumer {
	private KafkaConsumer<String, String> consumer;
	private Properties properties;

	/**
	 * kafka consumer 参数设置
	 */
	public MemoryCPUComsumer() {
		properties = new Properties();
		properties.put("bootstrap.servers", "hadoop01:9092");
		properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		// auto.offset.reset值含义解释
		// earliest
		// 客户端读取30条信息，且各分区的offset从0开始消费。
		// latest
		// 客户端读取0条信息。
		// none
		// 抛出NoOffsetForPartitionException异常。
		properties.put("auto.offset.reset", "latest");
		// 是否自动提交已拉取消息的offset。提交offset即视为该消息已经成功被消费
		properties.put("enable.auto.commit", "true");
		//一个消费者只能同时消费一个分区的数据
		properties.put("group.id", "memorycpu");

		consumer = new KafkaConsumer<String, String>(properties);
	}

	// 订阅
	public void subscribeTopic() {
		ArrayList<String> topics = new ArrayList<String>();
		topics.add("grj");
		consumer.subscribe(topics);
		// 始终拉取
		while (true) {
			// 从kafka中拉取数据
			ConsumerRecords<String, String> records = consumer.poll(1000);
			for (ConsumerRecord<String, String> record : records) {
				// System.out.println("接收到的消息partition: " + record.partition());
				// System.out.println("接收到的消息offset: " + record.offset());
				// System.out.println("接收到的消息key: " + record.key());
				System.out.println("接收到的消息value: " + record.value());
			}
		}

	}

	public static void main(String[] args) {
		MemoryCPUComsumer comsumer = new MemoryCPUComsumer();
		comsumer.subscribeTopic();
	}
}
