package cat.enricprats.mybooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * An activity representing a single BookItem detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link BookItemListActivity}.
 */
public class BookItemDetailActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bookitem_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        webView = (WebView) findViewById(R.id.simpleWebView);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                view.loadUrl(String.valueOf(request.getUrl()));
                String name = request.getUrl().getQueryParameter("name");
                String num = request.getUrl().getQueryParameter("num");
                String date = request.getUrl().getQueryParameter("date");
                if ("".equals(name) || "".equals(num) || "".equals(date)) {
                    Snackbar mySnackbar = Snackbar.make(view, "You must fill all fields", Snackbar.LENGTH_LONG);
                    mySnackbar.show();
                }
                else {
                    Snackbar mySnackbar = Snackbar.make(view, "Payment done", Snackbar.LENGTH_LONG);
//                        .setAction("Action", null).show();
                    mySnackbar.show();
                    webView.setVisibility(View.GONE);
                    final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.buy);
                    fab.show();
                }
                return true;
            }
        });
        webView.loadUrl("file:///android_asset/form.html");
        webView.setVisibility(View.GONE);



        // Floating button to buy the book
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.buy);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.setVisibility(View.VISIBLE);
                fab.hide();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();

            arguments.putLong(BookItemListActivity.ARG_ITEM_ID,
                    getIntent().getLongExtra(BookItemListActivity.ARG_ITEM_ID, 0));
//                    getIntent().getStringExtra(BookItemListActivity.ARG_ITEM_ID));
//                getIntent().getIntExtra(BookItemListActivity.ARG_ITEM_ID, -1));
            BookItemDetailFragment fragment = new BookItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.bookitem_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, BookItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
