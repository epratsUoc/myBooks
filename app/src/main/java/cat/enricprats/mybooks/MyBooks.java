package cat.enricprats.mybooks;

import com.facebook.stetho.Stetho;
import com.orm.SugarApp;

public class MyBooks extends SugarApp {

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        // This code would force the creation of the table BookItem - an issue related to Instant Run. Only left as reference
//        SugarContext.init(getApplicationContext());
//        SchemaGenerator schemaGenerator = new SchemaGenerator(this);
//        schemaGenerator.createDatabase(new SugarDb(this).getDB());
    }
}
