package pixboh.mobiyviewtest.utils;

import java.util.Comparator;

import pixboh.mobiyviewtest.model.DataItemModel;

/**
 * Created by pix on 1/12/18.
 */

public class CustomItemComparator implements Comparator<DataItemModel> {


    @Override
    public int compare(DataItemModel dataItemModel, DataItemModel t1) {
        if(dataItemModel.getImage().size() != 0 && t1.getImage().size() != 0){
            return dataItemModel.getImage().get(0).getFile().getUriFull().compareTo(t1.getImage().get(0).getFile().getUriFull());
        }else {
            return 0;
        }

    }
}