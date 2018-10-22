package cat.enricprats.mybooks.dummy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cat.enricprats.mybooks.model.BookItem;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<BookItem> ITEMS = new ArrayList<BookItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Long, BookItem> ITEM_MAP = new HashMap<Long, BookItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(BookItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    private static BookItem createDummyItem(int position) {
//        return new BookItem(position, "Title "+position, "Author "+position, "11/11/2005", "Description " + position+"\nSecond line", "");
        return new BookItem("Title "+position, "Author "+position, "11/11/2005", "Description " + position+"\nSecond line", "");
    }

//    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
//    }
}
