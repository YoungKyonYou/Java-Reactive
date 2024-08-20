package org.youyk.sec05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.youyk.common.Util;
import reactor.core.publisher.Flux;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Lec10Transform {
    private static final Logger log = LoggerFactory.getLogger(Lec10Transform.class);
    record Customer(
            int id,
            String name
    ){}
    record PurchaseOrder(
            String productName,
            int price,
            int quantity
    ){}

    public static void main(String[] args) {
        boolean isDebugEnabled = true;

        //반복되는 코드를 줄이기 위해 transform을 사용할 수 있다.
        getCustomer()
                .transform(isDebugEnabled ? addDebugger() : Function.identity())
                .subscribe();

        getPurchaseOrder()
                .transform(addDebugger())
                .subscribe();
    }

    private static Flux<Customer> getCustomer(){
        return Flux.range(1, 3)
                .map(i -> new Customer(i, Util.faker().name().firstName()));
    }

    private static Flux<PurchaseOrder> getPurchaseOrder(){
        return Flux.range(1, 5)
                .map(i -> new PurchaseOrder(Util.faker().commerce().productName(), i, i*10));
    }

    private static <T> UnaryOperator<Flux<T>> addDebugger(){
        return flux -> flux
                .doOnNext(i -> log.info("received: {}", i))
                .doOnComplete(() -> log.info("completed"))
                .doOnError(err -> log.error("error", err));
    }
}
