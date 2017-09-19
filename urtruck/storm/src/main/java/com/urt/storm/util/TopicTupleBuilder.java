package com.urt.storm.util;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.storm.kafka.spout.KafkaSpoutTupleBuilder;
import org.apache.storm.tuple.Values;

public class TopicTupleBuilder <K, V> extends KafkaSpoutTupleBuilder<K,V> {
    /**
     * @param topics list of topics that use this implementation to build tuples
     */
    public TopicTupleBuilder(String... topics) {
        super(topics);
    }

    @Override
    public List<Object> buildTuple(ConsumerRecord<K, V> consumerRecord) {
        return new Values(consumerRecord.topic(),
                consumerRecord.partition(),
                consumerRecord.offset(),
                consumerRecord.key(),
                consumerRecord.value());
    }
}
