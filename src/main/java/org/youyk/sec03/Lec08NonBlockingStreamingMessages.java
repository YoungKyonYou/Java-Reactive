package org.youyk.sec03;


import org.youyk.common.Util;
import org.youyk.sec03.client.ExternalServiceClient;

public class Lec08NonBlockingStreamingMessages {
    public static void main(String[] args) {
        ExternalServiceClient client = new ExternalServiceClient();
        client.getNames()
                .subscribe(Util.subscriber("sub1"));

        client.getNames()
                        .subscribe(Util.subscriber("sub2"));

        Util.sleepSeconds(6);
    }
}
