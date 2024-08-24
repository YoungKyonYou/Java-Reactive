package org.youyk.sec09;

import org.youyk.common.Util;
import org.youyk.sec09.helper.Kayak;

public class Lec06MergeUseCase {

    public static void main(String[] args) {
        Kayak.getFlights()
                .subscribe(Util.subscriber());

        Util.sleepSeconds(3);
    }
}
