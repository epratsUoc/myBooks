package cat.enricprats.mybooks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.FirebaseDatabase;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import cat.enricprats.mybooks.model.BookItem;
import cat.enricprats.mybooks.dummy.DummyContent;

import java.util.List;

/**
 * An activity representing a list of BookList. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BookItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class BookItemListActivity extends AppCompatActivity {

    private static String TAG = "#####";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    private SwipeRefreshLayout swipeContainer;
    private View recyclerView;
    private SimpleItemRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bookitem_list);

        // Initialize Firebase Connection
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.bookitem_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        // Prepare the swipe to refresh widget

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
// Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // ============ INICIO CODIGO A COMPLETAR ===============
                Log.w(TAG, "refresh asked");
                adapter.clear();
                // ...the data has come back, add new items to your adapter...
                adapter.setItems(BookContent.getBooks());
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);

                // ============ FIN CODIGO A COMPLETAR ===============
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null) {
            mAuth.signInWithEmailAndPassword("test1@enricprats.cat", "patata")
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(); //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(BookItemListActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                            updateUI(); //updateUI(null);
                            }

                            // ...
                        }
                    });
        }
        else {
            updateUI();
        }
    }

    private void updateUI() {
        recyclerView = findViewById(R.id.bookitem_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(final @NonNull RecyclerView recyclerView) {
        DatabaseReference myRef = database.getReference("books");
        final BookItemListActivity _this = this;
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<List<BookItem>> type = new GenericTypeIndicator<List<BookItem>>(){};
                List<BookItem> books = dataSnapshot.getValue(type);
                BookContent.rebuildList(books);
                if (adapter == null) {
                    adapter = new SimpleItemRecyclerViewAdapter(_this, BookContent.getBooks(), mTwoPane);
                    recyclerView.setAdapter(adapter);
                }
                else {
                    adapter.clear();
                    adapter.setItems(BookContent.getBooks());
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                // We launch the view with the values of our database
                if (adapter == null) {
                    adapter = new SimpleItemRecyclerViewAdapter(_this, BookContent.getBooks(), mTwoPane);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }



    public static final int ITEM_TYPE_ODD = 0;
    public static final int ITEM_TYPE_EVEN = 1;
    public static final String ARG_ITEM_ID = "item_id";

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final BookItemListActivity mParentActivity;
        private List<BookItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookItem item = (BookItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putLong(BookItemListActivity.ARG_ITEM_ID, item.getId());
                    BookItemDetailFragment fragment = new BookItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.bookitem_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, BookItemDetailActivity.class);
                    intent.putExtra(BookItemListActivity.ARG_ITEM_ID, item.getId());
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(BookItemListActivity parent,
                                      List<BookItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(viewType == ITEM_TYPE_ODD ? R.layout.bookitem_list_content_odd : R.layout.bookitem_list_content_even, parent, false);
//                    .inflate(R.layout.bookitem_list_content_odd, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(String.valueOf(mValues.get(position).getTitle()));
            holder.mContentView.setText(mValues.get(position).getAuthor());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemViewType(int position) {
            if (position%2 == 0) {
                return ITEM_TYPE_ODD;
            } else {
                return ITEM_TYPE_EVEN;
            }
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public void clear() {
            mValues.clear();
            notifyDataSetChanged();
        }

        public void setItems(List<BookItem> booksList) {
            this.mValues = booksList;
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.title);
                mContentView = (TextView) view.findViewById(R.id.author);
            }
        }
    }
}
