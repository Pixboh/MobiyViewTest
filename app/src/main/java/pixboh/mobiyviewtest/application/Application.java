package pixboh.mobiyviewtest.application;

import io.realm.Realm;

/**
 * Created by pix on 1/14/18.
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
