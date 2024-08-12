package org.youyk.sec01.publisher;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.sec01.subscriber.SubscriberImpl;

public class SubscriptionImpl implements Subscription {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionImpl.class);
    private static final int MAX_ITEMS = 10;
    private final Faker faker; //Faker 라이브러리를 사용하여 가짜 데이터를 생성
    //구독의 구독자
    private final Subscriber<? super String> subscriber;
    private boolean isCancelled; //기본 false로 초기화 됨
    private int count =0;
    public SubscriptionImpl(Subscriber<? super String> subscriber){
        this.subscriber = subscriber;
        this.faker = Faker.instance();
    }

    @Override
    public void request(long requested) {
        if(isCancelled){
            return;
        }
        log.info("subscriber has requested {} items", requested);
        if(requested > MAX_ITEMS){
            this.subscriber.onError(new RuntimeException("validation failed"));
            return;
        }
        for (int i = 0; i < requested && count< MAX_ITEMS; i++) {
            count++;
            this.subscriber.onNext(this.faker.internet().emailAddress());
        }
        if(count == MAX_ITEMS){
            log.info("no more data to produce");
            this.subscriber.onComplete();
            this.isCancelled = true;
        }
    }

    @Override
    public void cancel() {
        log.info("subscriber has cancelled");
        this.isCancelled = true;
    }
}
