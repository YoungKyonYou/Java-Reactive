package org.youyk.sec04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import org.youyk.sec01.subscriber.SubscriberImpl;
import reactor.core.publisher.Flux;

public class Lec04FluxCreateDownstreamDemand {
    private static final Logger log = LoggerFactory.getLogger(Lec04FluxCreateDownstreamDemand.class);

    public static void main(String[] args) {
        //요청이 있을 때만 produce한다 back pressure 방지
        produceOnDemand();
    }
    public void produceEarly() {
        SubscriberImpl subscriber = new SubscriberImpl();
        //큐에 결과가 쌓인다
        Flux.<String>create(fluxSink -> {
            for (int i = 0; i <10; i++) {
                String name = Util.faker().name().firstName();
                log.info("generated: {}", name);
                fluxSink.next(name);
            }
            fluxSink.complete();
        }).subscribe(subscriber);

        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriber.getSubscription().cancel();
        subscriber.getSubscription().request(2);



    }
    public static void produceOnDemand() {
        SubscriberImpl subscriber = new SubscriberImpl();
        //큐에 결과가 쌓인다
        Flux.<String>create(fluxSink -> {
            //subscriber가 request를 할 시
            fluxSink.onRequest(request -> {
                for (int i = 0; i < request && !fluxSink.isCancelled(); i++) {
                    String name = Util.faker().name().firstName();
                    log.info("generated: {}", name);
                    fluxSink.next(name);
                }
            });


        }).subscribe(subscriber);

        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriber.getSubscription().cancel();
        subscriber.getSubscription().request(2);



    }
}
