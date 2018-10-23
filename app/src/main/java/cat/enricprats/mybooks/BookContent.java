package cat.enricprats.mybooks;

import java.util.List;

import cat.enricprats.mybooks.model.BookItem;

public class BookContent {

    public static List<BookItem> getBooks(){
        return BookItem.listAll(BookItem.class);
    }

    public static boolean exists(BookItem bookItem) {
        // As we don't have IDs, we take into account the fields that define a book: author, title and publication date. Description and Cover image are not used to identify a book
        List<BookItem> list =  BookItem.find(BookItem.class, "AUTHOR = ? AND TITLE = ? AND PUBLICATIONDATE = ?", bookItem.getAuthor(), bookItem.getTitle(), bookItem.getPublication_date());
        return list.size() == 0;
    }
}