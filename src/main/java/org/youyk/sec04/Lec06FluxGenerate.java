package org.youyk.sec04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;

/*
    Flux generate
    - invokes the given lambda expression again and again based on downstream demand.
    - We can emit only one value at a time
    - will stop when complete method is invoked
    - will stop when error method is invoked
    - will stop downstream cancels
 */
public class Lec06FluxGenerate {
    private static final Logger log = LoggerFactory.getLogger(Lec06FluxGenerate.class);
    public static void main(String[] args) {
        //synchronousSink는 emit을 한 번만 할 수 있다 mono는 아니다
        Flux.generate(synchronousSink ->{
            log.info("invoked");
            synchronousSink.next(1);
           // synchronousSink.next(2);
            //complete()가 없으면 무한으로 received 된다 그래서 take로 막아줌
            //complete() 주석해제하면 take가 있어도 1개만 receive 되고 멈춤
            //synchronousSink.complete();
        }).take(4)
        .subscribe(Util.subscriber());
        //fluxSink는 emit을 여러 번 할 수 있다 그리고 이건 모든 제어를 할 수 있다.
        //generate는 반대다. emit을 한 번만 할 수 있다.
    }
}
