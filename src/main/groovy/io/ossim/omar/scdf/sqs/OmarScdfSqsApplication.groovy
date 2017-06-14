package io.ossim.omar.scdf.sqs

import groovy.util.logging.Slf4j
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo

/**
 * Created by adrake on 5/31/2017
 * Modified by cdowin on 6/5/2017
 *
 * The OmarScdfSqsApplication is a purpose built SQS listener
 * for integration with a full SCDF stack. The SQS listener
 * pops messages off of a configured queue and passes the message
 * along to the next link in the SCDF chain.
 */
@SpringBootApplication
@EnableBinding(Source.class)
@Slf4j
class OmarScdfSqsApplication
{

	/**
	 * Constructor
	 */
	OmarScdfSqsApplication() {}

	/**
	 * The main entry point of the SCDF Sqs application.
	 * @param args
	 */
	static void main(String[] args)
	{
		SpringApplication.run OmarScdfSqsApplication, args
	}

	/**
	 * The callback for when an SQS message is received
	 * @param message the body of the SQS message from the queue
	 */
	@MessageMapping("stephen-queue")
	@SqsListener(value = "stephen-queue", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	@SendTo(Source.OUTPUT)
	final String receive(String message)
	{
		log.debug("Forwarding message from queue: ${message}")
		return message
	}
}
