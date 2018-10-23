package cat.enricprats.mybooks;

import com.facebook.stetho.Stetho;
import com.orm.SchemaGenerator;
import com.orm.SugarApp;
import com.orm.SugarContext;
import com.orm.SugarDb;

import cat.enricprats.mybooks.model.BookItem;

public class MyBooks extends SugarApp {

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        // Code added to force the creation of the table BookItem
        SugarContext.init(getApplicationContext());
        SchemaGenerator schemaGenerator = new SchemaGenerator(this);
        schemaGenerator.createDatabase(new SugarDb(this).getDB());
    }
}
