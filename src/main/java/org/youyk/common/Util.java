package org.youyk.common;

import com.github.javafaker.Faker;
import java.util.function.UnaryOperator;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.sec09.Lec04ConcatError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Util {
    private static final Logger log = LoggerFactory.getLogger(Util.class);
    private static final Faker faker = Faker.instance();
    public static <T> Subscriber<T> subscriber(){
        return new DefaultSubscriber<>("");
    }

    public static <T> Subscriber<T> subscriber(String name){
        return new DefaultSubscriber<>(name);
    }

    public static void main(String[] args) {
        Mono<Integer> mono = Mono.just(1);

        mono.subscribe(subscriber("sub1"));
        mono.subscribe(subscriber("sub2"));
    }

    public static Faker faker(){
        return faker;
    }

    public static void sleepSeconds(int seconds){
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 입력값과 출력값의 타입이 같은 함수를 나타냅니다.
     * 이 인터페이스는 Function<T, T> 인터페이스를 확장하며, apply(T t)라는 메서드를 가지고 있습니다.
     * 이 코드에서 UnaryOperator<Flux<T>>는 Flux<T> 타입의 인자를 받아 Flux<T> 타입을 반환하는 함수를 나타냅니다.
     * 이 함수는 Flux 스트림에 대한 연산을 정의하고 있습니다.
     */
    public static <T>UnaryOperator<Flux<T>> fluxLogger(String name) {
        return flux -> flux
                .doOnSubscribe(s -> log.info("subscribing to {} " + name))
                .doOnCancel(() -> log.info("cancelling{}", name))
                .doOnComplete(() -> log.info("{} completed", name));
    }
    
    public static void sleep(Duration duration){
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
