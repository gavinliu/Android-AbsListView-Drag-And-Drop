package cn.gavinliu.draganddroplistview;

/**
 * Created by GavinLiu on 2015-05-29
 */
public class List3 extends List1 {

    @Override
    protected void setupListView() {
        super.setupListView();

        listView.setIsSwipeChoise(true);
    }
}
