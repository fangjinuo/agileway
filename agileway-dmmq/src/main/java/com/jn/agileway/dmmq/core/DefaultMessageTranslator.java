package com.jn.agileway.dmmq.core;

public class DefaultMessageTranslator<M> implements MessageTranslator<M> {
    private M message;
    private String topicName;

    @Override
    public String getTopicName() {
        return topicName;
    }

    @Override
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public void setMessage(M message) {
        this.message = message;
    }

    @Override
    public M getMessage() {
        return message;
    }

    @Override
    public void translateTo(MessageHolder<M> event, long sequence) {
        event.setTopicName(getTopicName());
        event.set(message);
    }
}
