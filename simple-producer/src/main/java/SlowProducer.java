import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.StringSerializer;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Created by cmccarthy on 1/25/19.
 */
public class SlowProducer {
	public static void main(String[] args) throws Exception {
		final KafkaProducer<String,String> slowProducer =
				new KafkaProducer<String,String>(ImmutableMap.<String,Object>of(
				"key.serializer", StringSerializer.class,
				"value.serializer", StringSerializer.class,
				"bootstrap.servers", "127.0.0.1:9092",
				"client.id", "slowProducer"));

		while (true) {
			produceSomething(slowProducer);
		}
	}

	@Trace(dispatcher = true)
	private static void produceSomething(KafkaProducer<String, String> producer) throws InterruptedException {
		Random r = new Random();
		Thread.sleep(r.nextInt(200));
		try {
			final String payload = NewRelic.getAgent().getTransaction().createDistributedTracePayload().text();
			ImmutableList<Header> headers = ImmutableList.of((Header)new RecordHeader(
					"NR-DT-PAYLOAD", payload.getBytes(StandardCharsets.UTF_8)));
//			if (!payload.isEmpty()) {
//				System.out.println(payload);
//			}
			producer.send(new ProducerRecord<>("SimpleTopic", 0, "hello", "world", headers));
		} finally {
			producer.flush();
		}
	}
}
