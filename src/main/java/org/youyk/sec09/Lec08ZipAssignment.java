package org.youyk.sec09;

import org.youyk.common.Util;
import org.youyk.sec09.assignment.ExternalServiceClient;

public class Lec08ZipAssignment {
    public static void main(String[] args) {
        ExternalServiceClient client = new ExternalServiceClient();

        for (int i = 1; i < 10; i++) {
            client.getProduct(i)
                    .subscribe(Util.subscriber());

        }

        Util.sleepSeconds(2);
    }
}
