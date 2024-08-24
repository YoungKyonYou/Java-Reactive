package org.youyk.sec09;

import org.youyk.common.Util;
import reactor.core.publisher.Flux;

/*
    To collect the items received via Flux. Assuming we will have finite items!
 */
public class Lec14CollectList {

    public static void main(String[] args) {

        // collectList은 non blocking 이다.
        Flux.range(1, 10)
                .collectList()
                .subscribe(Util.subscriber());

    }

}
