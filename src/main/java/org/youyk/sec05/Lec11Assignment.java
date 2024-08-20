package org.youyk.sec05;

import org.youyk.common.Util;
import org.youyk.sec05.assignment.ExternalServiceClient;

public class Lec11Assignment {
    public static void main(String[] args) {
        ExternalServiceClient client = new ExternalServiceClient();

        for (int i = 1; i < 5; i++) {
            client.getProductName(i)
                    .subscribe(Util.subscriber());
        }
        Util.sleepSeconds(3);
    }
}
