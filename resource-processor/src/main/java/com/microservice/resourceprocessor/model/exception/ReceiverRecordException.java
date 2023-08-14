package com.microservice.resourceprocessor.model.exception;

import reactor.kafka.receiver.ReceiverRecord;

public class ReceiverRecordException extends RuntimeException {
    private ReceiverRecord<?, ?> receiverRecord;

    public <T> ReceiverRecordException(ReceiverRecord<String, T> receiverRecord, Throwable error) {
        super(error);
        this.receiverRecord = receiverRecord;
    }

    public ReceiverRecord<?, ?> getReceiverRecord() {
        return receiverRecord;
    }
}
