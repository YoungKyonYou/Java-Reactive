package org.youyk.sec09;

import org.youyk.common.Util;
import org.youyk.sec09.applications.PaymentService;
import org.youyk.sec09.applications.UserService;
import reactor.core.publisher.Mono;

/*
    Sequential non-blocking IO calls!
    flatMap is used to flatten the inner publisher / to subscribe to the inner publisher
 */
public class Lec09MonoFlatMap {

    public static void main(String[] args) {

        /*
            We have username.
            Get user account balance
         */
        /**
         * flatMap은 Mono나 Flux와 같은 리액티브 스트림에서 제공하는 연산자 중 하나로,
         * 주어진 함수에 Mono의 요소를 적용하여 새로운 Mono를 반환합니다. 일반적으로 비동기 작업을 연결하고 싶을 때 사용됩니다.
         * flatMap은 주어진 함수가 또 다른 Mono를 반환하는 경우에 유용합니다. 만약 단순히 값을 변환하고 싶다면 map을 사용하고,
         * 비동기적으로 작업을 연결해야 할 경우 flatMap을 사용하는 것이 좋습니다.
         * map 연산자는 데이터를 동기적으로 변환할 때 사용됩니다. 하지만 만약 변환 과정에서 비동기 작업이 필요하다면,
         * flatMap을 사용하여 비동기적인 데이터 변환을 수행할 수 있습니다.
         */
        UserService.getUserId("mike")
                //이렇게는 작동하지 않는다.
                //.map(userId -> Mono.fromSupplier(() -> userId))
                //flatMap은 서비스의 inner publisher를 subscribe한다.
                //publisher가 뭘 주든 가져와서 subscriber에게 준다
                //이건 inner publisher를 subscribe하더라도 mono publisher를 반환한다.
                .flatMap(PaymentService::getUserBalance)
                .subscribe(Util.subscriber());

    }

}