package org.youyk.sec03;

import org.youyk.common.Util;
import org.youyk.sec01.subscriber.SubscriberImpl;
import org.youyk.sec03.helper.NameGenerator;

import java.util.List;

public class Lec07FluxVsList {
    public static void main(String[] args) {
/*        List<String> list = NameGenerator.getNamesList(10);
        System.out.println(list);*/ //10초 블러킹 뒤 10개가 다 표출됨

        SubscriberImpl subscriber = new SubscriberImpl();
        NameGenerator.getNameFlux(10)
                .subscribe(subscriber); // 1초마다 한개씩 표출됨

        subscriber.getSubscription().request(3);
        subscriber.getSubscription().cancel();
    }
}
