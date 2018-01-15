package pixboh.mobiyviewtest.realm;

import java.util.ArrayList;
import java.util.List;

import pixboh.mobiyviewtest.model.DataItemModel;

/**
 * Created by pix on 1/14/18.
 */

public class RealmUtil {
    public static DataItemRealm castToRealmdata(DataItemModel item){
        DataItemRealm dataItem = new DataItemRealm();
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
        return  dataItem;
    }

    public static List<DataItemRealm> castToRealmdata(List<DataItemModel> dataItemModels){
        List<DataItemRealm> dataItemRealms = new ArrayList<>();
        for(DataItemModel dataItemModel : dataItemModels){
            dataItemRealms.add(castToRealmdata(dataItemModel));
        }
        return  dataItemRealms;

    }
}
