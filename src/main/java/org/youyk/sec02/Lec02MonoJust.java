package org.youyk.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.sec01.subscriber.SubscriberImpl;
import reactor.core.publisher.Mono;

import java.util.concurrent.Flow;

public class Lec02MonoJust {
    private static final Logger log = LoggerFactory.getLogger(Lec02MonoJust.class);

    public static void main(String[] args) {
        //Mono.just는 publisher를 생성하는 가장 쉬운 방법이다.
        //Mono.just("vins")는 "vins"라는 단일 값을 가진 Mono를 생성한다.
        Mono<String> mono = Mono.just("vins");
        //이렇게 하면 "MonoJust"라고 밖에 안찍힌다
        //이유는 Mono는 publisher이기 때문에 subscribe를 해야한다.(구독을 먼저 해야된다!)
        //reactive에서 이런 개념이 제일 중요하다
       // System.out.println(mono);

        SubscriberImpl subscriber = new SubscriberImpl();
        mono.subscribe(subscriber);
        //구독자가 Subscription을 통해 10개의 아이템을 요청합니다.
        // 이때 Mono는 단일 아이템만을 가지고 있으므로,
        // "vins"라는 아이템이 구독자의 onNext 메소드로 전달됩니다.
        //아래 처럼 2번 요청해도 1개만 온다(Mono는 1개만 가지고 있으므로)
        subscriber.getSubscription().request(10);
        subscriber.getSubscription().request(10);
        subscriber.getSubscription().cancel();


    }

}
