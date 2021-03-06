# omar-scdf-sqs
The SQS Listener is a Spring Cloud Data Flow (SCDF) Source.
This means it:
1. Listens on an AWS SQS Queue for a message.
2. Forwards the message on a Spring Cloud output stream using Kafka to a listening SCDF Processor.

## Purpose
The SQS Listener receives a message from the SQS Queue and forwards it to any SCDF Processors that need it. For the purposes of our EV-WHS and LongDwell ingest flow, it typically sends it to the S3 Filter.

## Input Example (from an SQS Queue)
```json
{
   "Records":[
      {
         "eventVersion":"2.0",
         "eventSource":"aws:s3",
         "awsRegion":"us-east-1",
         "eventTime":"2017-05-17T15:19:17.054Z",
         "eventName":"ObjectCreated:Put",
         "userIdentity":{
            "principalId":"AWS:AIDAJEZ522MASFRCX33EA"
         },
         "requestParameters":{
            "sourceIPAddress":"72.239.134.33"
         },
         "responseElements":{
            "x-amz-request-id":"B96EEA576F92C7AE",
            "x-amz-id-2":"ziF5E2fAivtJAC7zuieVgHIJeAtNf0zgsx/qiW0C0OUibZQw0OgZs75EEmxYnENGwnlYBAfljyA="
         },
         "s3":{
            "s3SchemaVersion":"1.0",
            "configurationId":"NzlkMjZhOGMtNWUzOS00ZmQzLTkzYzYtMTJiNDY2N2Y2ZjUw",
            "bucket":{
               "name":"omar-dropbox",
               "ownerIdentity":{
                  "principalId":"A15X0AZ24P2BXT"
               },
               "arn":"arn:aws:s3:::omar-dropbox"
            },
            "object":{
               "key":"12345/SCDFTestImages.zip",
               "size":1753,
               "eTag":"e10e1145f9c0c39387b09b584eb5e523",
               "sequencer":"00591C69F4E5D745D6"
            }
         }
      }
   ]
}
```

## Output Example (to a SCDF Processor)
The SQS Listener simply forwards the SQS message on to the SCDF Processor that is listening on the SQS Listener's output Kafka stream. Therefore, the output is the same as the input.
