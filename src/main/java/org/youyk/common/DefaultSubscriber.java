package org.youyk.common;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DefaultSubscriber<T> implements Subscriber<T> {
    private static final Logger log = LoggerFactory.getLogger(DefaultSubscriber.class);
    private final String name;

    public DefaultSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        log.info("onSubscribe call");
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T item) {
        log.info("{} received: {}",this.name, item);
    }

    @Override
    public void onError(Throwable t) {
        log.error("{} error", this.name, t);
    }

    @Override
    public void onComplete() {
        log.info("{} received completed!", this.name);
    }
}
