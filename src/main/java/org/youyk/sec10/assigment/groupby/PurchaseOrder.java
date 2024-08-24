package org.youyk.sec10.assigment.groupby;

import com.github.javafaker.Commerce;
import org.youyk.common.Util;

public record PurchaseOrder(
        String item,
        String category,
        Integer price
) {

    public static PurchaseOrder create(){
        Commerce commerce = Util.faker().commerce();
        return new PurchaseOrder(
                commerce.productName(),
                commerce.department(),
                Util.faker().random().nextInt(10,100)
        );
    }
}
