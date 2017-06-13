package io.ossim.omar.scdf.sqs
/**
 * Created by adrake on 5/24/17.
 */
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

import javax.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Configuration
@EnableConfigurationProperties(AwsCredentialsProperties)
class AwsCredentialsConfiguration {

    @Autowired
    private AwsCredentialsProperties awsCredentialsProperties

    private Logger logger = LoggerFactory.getLogger(this.getClass())


    @PostConstruct
    void init() {

        System.setProperty("aws.accessKeyId", awsCredentialsProperties.accessKey)
        System.setProperty("aws.secretKey", awsCredentialsProperties.secretKey)
        logger.debug("accesskey = " + awsCredentialsProperties.accessKey + "\nsecretkey " + awsCredentialsProperties.secretKey + "\n" )
    }

}
