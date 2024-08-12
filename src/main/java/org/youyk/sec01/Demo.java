package org.youyk.sec01;


import org.youyk.sec01.publisher.PublisherImpl;
import org.youyk.sec01.subscriber.SubscriberImpl;

import java.time.Duration;

/*
   1. publisher does not produce data unless subscriber requests for it.
   2. publisher will produce only <= subscriber requested items. publisher can also produce 0 items!
   3. subscriber can cancel the subscription. producer should stop at that moment as subscriber is no longer interested in consuming the data
   4. producer can send the error signal
 */
public class Demo {
    public static void main(String[] args) throws InterruptedException {
        demo4();
    }

    private static void demo1(){
        PublisherImpl publisher = new PublisherImpl();
        SubscriberImpl subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
    }

    private static void demo2() throws InterruptedException {
        PublisherImpl publisher = new PublisherImpl();
        SubscriberImpl subscriber = new SubscriberImpl();
        // 구독자의 구독 신청
        publisher.subscribe(subscriber);
        // 구독자의 데이터 요청
        // 구독자는 데이터를 요청할 때 "구독 object"로 요청을 하게 된다 (getSubscription() 메소드로 구독 object를 가져온다)
        subscriber.getSubscription().request(3);

        Thread.sleep(Duration.ofSeconds(2).toMillis());
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2).toMillis());
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2).toMillis());
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2).toMillis());
        subscriber.getSubscription().request(3);

    }

    private static void demo3() throws InterruptedException {
        PublisherImpl publisher = new PublisherImpl();
        SubscriberImpl subscriber = new SubscriberImpl();
        // 구독자의 구독 신청
        publisher.subscribe(subscriber);
        // 구독자의 데이터 요청
        // 구독자는 데이터를 요청할 때 "구독 object"로 요청을 하게 된다 (getSubscription() 메소드로 구독 object를 가져온다)
        subscriber.getSubscription().request(3);

        Thread.sleep(Duration.ofSeconds(2).toMillis());
        subscriber.getSubscription().cancel();
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2).toMillis());
    }

    private static void demo4() throws InterruptedException {
        PublisherImpl publisher = new PublisherImpl();
        SubscriberImpl subscriber = new SubscriberImpl();
        // 구독자의 구독 신청
        publisher.subscribe(subscriber);
        // 구독자의 데이터 요청
        // 구독자는 데이터를 요청할 때 "구독 object"로 요청을 하게 된다 (getSubscription() 메소드로 구독 object를 가져온다)
        subscriber.getSubscription().request(3);

        Thread.sleep(Duration.ofSeconds(2).toMillis());
        subscriber.getSubscription().request(11);
        Thread.sleep(Duration.ofSeconds(2).toMillis());

        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2).toMillis());
    }
}
