package io.ossim.omar.scdf.sqs

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.aws.messaging.listener.Acknowledgment
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.integration.annotation.InboundChannelAdapter
import org.springframework.messaging.handler.annotation.MessageMapping

/**
 * Created by adrake on 5/31/2017
 */

@SpringBootApplication
@EnableBinding(Source.class)
class OmarScdfSqsApplication {

	/**
	 * The application logger
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass())

	BucketFile sqsMessageReceived

	/**
	 * Constructor
	 */
	OmarScdfSqsApplication(){
		sqsMessageReceived = new BucketFile()
	}

	/**
	 * The main entry point of the SCDF Sqs application.
	 * @param args
	 */
	static void main(String[] args) {
		SpringApplication.run OmarScdfSqsApplication, args
	}

	 @MessageMapping('${queue.name}')
	 @SqsListener(value = '${queue.name}', deletionPolicy = SqsMessageDeletionPolicy.NEVER)
	 void receive(String message, Acknowledgment acknowledgment) {

		if(logger.isDebugEnabled()){
			logger.debug("Message received: ${message}")
		}

		def slurper = new groovy.json.JsonSlurper()
		def result = slurper.parseText(message)


		 sqsMessageReceived.bucket = result.Records.s3.bucket.name
		 sqsMessageReceived.filename =  result.Records.s3.object.key

		if(logger.isDebugEnabled()){
			logger.debug("sqsMessageReceived: ${sqsMessageReceived}")
		}
	}

	@InboundChannelAdapter(Source.OUTPUT)
	BucketFile send() {
        sqsMessageReceived
	}

	/**
	 * @String filename output file name
	 * @String bucket output bucket
	 */
	private final class BucketFile {
		String filename
		String bucket
	}
}
