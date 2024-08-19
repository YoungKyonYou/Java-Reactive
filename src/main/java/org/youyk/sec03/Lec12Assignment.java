package org.youyk.sec03;

import org.youyk.common.Util;
import org.youyk.sec03.assignment.StockPriceObserver;
import org.youyk.sec03.client.ExternalServiceClient;

public class Lec12Assignment {
    public static void main(String[] args) {
        ExternalServiceClient client = new ExternalServiceClient();
        StockPriceObserver subscriber = new StockPriceObserver();
        client.getPriceChanges()
                .subscribe(subscriber);

        Util.sleepSeconds(20);
    }
}
