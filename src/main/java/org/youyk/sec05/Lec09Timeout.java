package org.youyk.sec05;

import org.youyk.common.Util;
import reactor.core.publisher.Mono;

import java.time.Duration;
/*
    timeout - will produce timeout error.
        - We can handle as part of onError methods
    there is also an overloaded method to accept a publisher
    we can have multiple timeouts. the closest one to the subscriber will take effect for the subscriber.
 */
public class Lec09Timeout {
    public static void main(String[] args) {
        //1초만에 아이템이나 터미널 시그널을 줘야 한다.
        //timeout은 시간이 지나면 에러를 발생시킨다.
        Mono<String> mono = getProductName().timeout(Duration.ofSeconds(1), fallback())
                .onErrorReturn("fallback");

        mono
                //timeout은 여러 개 있을 수 있다.
                //subscriber에 가까운 timeout이 적용된다. 하지만
                //.timeout(Duration.ofMillis(5000) 은 안된다 앞에 1초였기 때문에 reducing time은 괜찮지만 increasing time은 안된다.
                .timeout(Duration.ofMillis(200)).subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }
    private static Mono<String> getProductName(){
        return Mono.fromSupplier(() -> Util.faker().commerce().productName())
                .delayElement(Duration.ofMillis(1900));
    }

    private static Mono<String> fallback(){
        return Mono.fromSupplier(() -> "fallback -"+Util.faker().commerce().productName())
                .delayElement(Duration.ofMillis(300))
                .doFirst(()-> System.out.println("do first"));
    }
}
