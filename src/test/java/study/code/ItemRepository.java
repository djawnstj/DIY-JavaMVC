package study.code;

import java.util.HashMap;
import java.util.Map;

public class ItemRepository {

    private static Map<Long, Item> items = new HashMap<>();

    public void add(Item item) {
        items.put(item.getId(), item);
    }

    public Item findById(long itemId) {
        return items.get(itemId);
    }
}
