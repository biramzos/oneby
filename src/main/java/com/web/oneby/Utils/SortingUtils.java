package com.web.oneby.Utils;

import org.springframework.data.domain.Sort.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SortingUtils {

    public static List<Order> getSoringOrders(Map<String, String> sorts){
        List<Order> orders = new ArrayList<>();
        if (!sorts.isEmpty()) {
            for (String key: sorts.keySet()) {
                String value = sorts.get(key);
                if (value.equals("ASC")) {
                    orders.add(Order.asc(key));
                } else {
                    orders.add(Order.desc(key));
                }
            }
        }
        return orders;
    }

}
