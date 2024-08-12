package org.youyk.sec01.subscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//Subscriber<>에서 <>안에는 Publisher로부터 받을 데이터의 타입을 넣어줘야 한다.
public class SubscriberImpl implements Subscriber<String> {
    private static final Logger log = LoggerFactory.getLogger(SubscriberImpl.class);
    private Subscription subscription;

    public Subscription getSubscription() {
        return subscription;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void onNext(String email) {
        log.info("received: {}", email);
    }

    @Override
    public void onError(Throwable t) {
        log.error("error", t);
    }

    @Override
    public void onComplete() {
        log.info("completed!");
    }
}
