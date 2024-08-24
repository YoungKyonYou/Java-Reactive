package org.youyk.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Mono;

public class Lec09PublisherCreatedVsExecution {
    private static final Logger log = LoggerFactory.getLogger(Lec09PublisherCreatedVsExecution.class);

    public static void main(String[] args) {
        getName().subscribe(Util.subscriber());

        //원래 fromSupplier()는 Lazy하게 만들어주는데 구독하지 않고 getName()만 호출했을 때는 "entered the method" 만 찍히고 끝난다.
        //이건 잘못됐다고 생각할 수 있다. 하지만 잘못되진 않았다. 아래 getName에서 하고자하는 건 Publisher를 생성하는 거지 operation를 하는 게 아니다
       // getName();
    }
    
    private static Mono<String> getName(){
        log.info("entered the method");
        return Mono.fromSupplier(() -> {
            log.info("generating username");
            return Util.faker().name().firstName();
        });
    }
}
