package io.ossim.omar.scdf.sqs

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Output
import org.springframework.cloud.stream.messaging.Source
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.support.MessageBuilder

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
     * Output channel
     */
    @Autowired
    @Output(Source.OUTPUT)
    private MessageChannel outputChannel

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
    @MessageMapping('${queue.name}')
	@SqsListener(value = '${queue.name}', deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	void receiveFromSqsQueue(final String message)
	{
		log.debug("Forwarding message from queue: ${message}")
        log.debug("Message sent: ${sendMessageOnOutputStream(message)}")
	}

    /**
     * Sends message to an Output stream
     */
    boolean sendMessageOnOutputStream(String message)
    {
        Message<String> messageToSend = MessageBuilder.withPayload(message)
                .setHeader(MessageHeaders.CONTENT_TYPE, '${spring.cloud.stream.bindings.output.content.type}')
                .build()

        outputChannel.send(messageToSend)
    }
}
