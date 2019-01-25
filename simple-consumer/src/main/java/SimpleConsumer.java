import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by cmccarthy on 1/25/19.
 */
public class SimpleConsumer {
	public static void main(String[] args) throws InterruptedException {
		KafkaConsumer<String,String> simpleConsumer = new KafkaConsumer<>(
				new ImmutableMap.Builder<String, Object>()
						.put("bootstrap.servers", "127.0.0.1:9092")
						.put("group.id", "SimpleConsumer")
						.put("key.deserializer", StringDeserializer.class)
						.put("value.deserializer", StringDeserializer.class)
						.put("auto.commit.interval.ms", "1000")
						.put("enable.auto.commit", "true")
						.build());

		simpleConsumer.subscribe(ImmutableList.of("SimpleTopic"));

		while (true) {
			ConsumerRecords<String, String> records = simpleConsumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String, String> record : records) {
				consumeRecord(record);
			}
		}
	}

	@Trace(dispatcher = true)
	private static void consumeRecord(ConsumerRecord<String, String> record) throws InterruptedException {
		Random r = new Random();
		Thread.sleep(r.nextInt(200));
		record.headers().headers("NR-DT-PAYLOAD").forEach(header -> {
			//System.out.println(Arrays.toString(header.value()));
			NewRelic.getAgent().getTransaction().acceptDistributedTracePayload( new String(header.value(),
					StandardCharsets.UTF_8));
		});
	}
}
