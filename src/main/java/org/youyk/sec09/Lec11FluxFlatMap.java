package org.youyk.sec09;

import org.youyk.common.Util;
import org.youyk.sec09.applications.OrderService;
import org.youyk.sec09.applications.UserService;
import org.youyk.sec09.applications.User;

/*
    Sequential non-blocking IO calls!
    flatMap is used to flatten the inner publisher / to subscribe to the inner publisher
 */
public class Lec11FluxFlatMap {

    public static void main(String[] args) {

        /*
            Get all the orders from order service!
         */

        UserService.getAllUsers()
                .map(User::id)
                .flatMap(OrderService::getUserOrders, 1)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(5);

    }

}
