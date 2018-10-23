package cat.enricprats.mybooks;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import cat.enricprats.mybooks.model.BookItem;
import cat.enricprats.mybooks.dummy.DummyContent;

/**
 * A fragment representing a single BookItem detail screen.
 * This fragment is either contained in a {@link BookItemListActivity}
 * in two-pane mode (on tablets) or a {@link BookItemDetailActivity}
 * on handsets.
 */
public class BookItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
//    public static final String ARG_ITEM_ID = "item_id";
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");


    private BookItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(BookItemListActivity.ARG_ITEM_ID)) {

            Long key = getArguments().getLong(BookItemListActivity.ARG_ITEM_ID);
            mItem = BookItem.findById(BookItem.class, key);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getTitle());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bookitem_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.author)).setText(mItem.getAuthor());
            ((TextView) rootView.findViewById(R.id.description)).setText(mItem.getDescription());

//            ((TextView) rootView.findViewById(R.id.publishDate)).setText(dateFormatter.format(mItem.getPublication_date()));
            ((TextView) rootView.findViewById(R.id.publishDate)).setText(mItem.getPublication_date());
        }

        return rootView;
    }
}
