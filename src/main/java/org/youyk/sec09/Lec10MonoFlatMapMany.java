package org.youyk.sec09;

import org.youyk.common.Util;
import org.youyk.sec09.applications.OrderService;
import org.youyk.sec09.applications.UserService;

/*
    Sequential non-blocking IO calls!
    flatMap is used to flatten the inner publisher / to subscribe to the inner publisher
    Mono is supposed to be 1 item - what if the flatMap returns multiple items!?
 */
public class Lec10MonoFlatMapMany {

    public static void main(String[] args) {

        /*
            We have username
            get all user orders!
         */

        UserService.getUserId("sam")
                //Lec09에서의 flatMap은 inner publisher를 subscribe하더라도 mono publisher를 반환한다.
                //이번에는 flatMapMany를 사용해서 inner publisher를 subscribe하고, 여러개의 item을 반환한다.
                .flatMapMany(OrderService::getUserOrders)
                .subscribe(Util.subscriber());


        Util.sleepSeconds(3);
    }

}
