package pixboh.mobiyviewtest.utils;

import java.util.Comparator;

import pixboh.mobiyviewtest.model.DataItemModel;

/**
 * Created by pix on 1/12/18.
 */

public class CustomItemComparatorForPhoto implements Comparator<DataItemModel> {


    @Override
    public int compare(DataItemModel dataItemModel, DataItemModel t1) {
        return dataItemModel.getTitle().compareTo(t1.getTitle());

    }
}