package org.youyk.sec01.publisher;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublisherImpl implements Publisher<String> {
    private static final Logger log = LoggerFactory.getLogger(PublisherImpl.class);

    @Override
    public void subscribe(Subscriber<? super String> subscriber) {
        SubscriptionImpl subscription = new SubscriptionImpl(subscriber);
        //구독자는 subscription 오브젝트를 통해 구독을 요청한다.
        subscriber.onSubscribe(subscription);
    }
}
