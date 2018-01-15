package pixboh.mobiyviewtest.realm;

import java.util.List;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import pixboh.mobiyviewtest.model.DataItemModel;

/**
 * Created by pix on 1/14/18.
 */

public class RealmQueries {
    public static RealmConfiguration config = new RealmConfiguration.Builder()
            .name("myrealm3.realm")
            .migration(new RealmMigration() {
                @Override
                public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

                }
            })
            .schemaVersion(6)
            .build();

    public static void addDataOnRealmFromServer(Realm realm, List<DataItemModel> list) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Add a person
                for (DataItemModel item : list) {
                    DataItemRealm dataItem = realm.createObject(DataItemRealm.class);
                    dataItem.setTitle(item.getTitle());
                    dataItem.setType(item.getType());
                    dataItem.setAddress(item.getAddress());
                    dataItem.setLatlong(item.getGeo().getLatlon());
                    dataItem.setDescription(item.getDescription().size() == 0 ? null : item.getDescription().get(0).getValue());
                    if (item.getType().contains("attraction")) {
                        dataItem.setAttractionCategory(item.getAttractionCategory().getName());
                    }
                    dataItem.setImage(item.getImage().size() == 0 ? null : item.getImage().get(0).getFile().getUriFull());
                    dataItem.setLocationArea(item.getLocationArea().getName());
                    dataItem.setOpeningDays(item.getOpeningDays());
                    if(item.getType().contains("attraction")){
                        dataItem.setPrice(item.getPriceRange());
                    }
//                    Log.e("Count realm", realm.where(DataItemRealm.class).count() + "");
                }

            }
        });
    }

    public static void addDataOnRealm(Realm realm, List<DataItemRealm> list) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Add a person

                for (DataItemRealm item : list) {
                    DataItemRealm dataItem = realm.createObject(DataItemRealm.class);
                    dataItem = item;
                }

            }
        });


    }

    public static void destroyAll(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(DataItemRealm.class).findAll().deleteAllFromRealm();
            }
        });

    }
}
