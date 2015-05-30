package cn.gavinliu.draganddroplistview;

import android.os.Handler;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;

import cn.gavinliu.android.lib.dragdrop.SelectionMode;
import cn.gavinliu.android.lib.dragdrop.widget.MenuZone;

/**
 * Created by GavinLiu on 2015-05-30
 */
public class Grid1 extends BaseGridActivity {

    @Override
    protected void setupListView() {
        super.setupListView();

        gridView.setSelectionMode(SelectionMode.Official);
        gridView.setMultiChoiceModeListener(multiChoiceModeListener);
    }

    private AbsListView.MultiChoiceModeListener multiChoiceModeListener = new AbsListView.MultiChoiceModeListener() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            mode.setTitle("Select Item");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            new Handler().post(new Runnable() {

                @Override
                public void run() {
                    View delete = findViewById(R.id.action_delete);
                    if (delete != null) {
                        gridView.addMenuZone(delete, MenuZone.Type.DELETE);
                    }
                }
            });
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            final int checkedCount = gridView.getCheckedItemCount();
            switch (checkedCount) {
                case 0:
                    mode.setTitle("Select Item");
                    break;
                default:
                    mode.setTitle("" + checkedCount);
                    break;
            }
        }
    };

}
