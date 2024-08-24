package org.youyk.sec10;

import java.time.Duration;
import org.youyk.common.Util;
import org.youyk.sec10.assigment.groupby.OrderProcessingService;
import org.youyk.sec10.assigment.groupby.PurchaseOrder;
import reactor.core.publisher.Flux;

public class Lec06GroupByAssignment {

    public static void main(String[] args) {
        orderStream()
                .filter(OrderProcessingService.canProcess())
                .groupBy(PurchaseOrder::category)
                .flatMap(gf -> gf.transform(OrderProcessingService.getProcessor(gf.key())))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);
    }

    private static Flux<PurchaseOrder> orderStream(){
        return Flux.interval(Duration.ofMillis(200))
                .map(i -> PurchaseOrder.create());
    }
}
