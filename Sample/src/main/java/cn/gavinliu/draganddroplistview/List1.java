package cn.gavinliu.draganddroplistview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.gavinliu.android.lib.dragdrop.DDListView;
import cn.gavinliu.android.lib.dragdrop.listener.OnDragDropListener;
import cn.gavinliu.android.lib.dragdrop.widget.MenuZone;
import cn.gavinliu.android.lib.dragdrop.SelectionMode;

/**
 * Created by GavinLiu on 2015-05-25
 */
public class List1 extends ActionBarActivity {

    DDListView listView;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        list = new ArrayList<String>(Arrays.asList(Cheeses.sCheeseStrings));
        setupListView();
    }

    private void setupListView() {
        listView = (DDListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnDragDropListener(onDragDropListener);
        listView.setSelectionMode(SelectionMode.Official);
        listView.setMultiChoiceModeListener(multiChoiceModeListener);
        listView.setIsSwipChoise(true);
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
                        listView.addMenuZone(delete, MenuZone.Type.DELETE);
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
            final int checkedCount = listView.getCheckedItemCount();
            switch (checkedCount) {
                case 0:
                    mode.setSubtitle("Select Item");
                    break;
                default:
                    mode.setSubtitle("" + checkedCount);
                    break;
            }
        }
    };

    private OnDragDropListener onDragDropListener = new OnDragDropListener() {

        @Override
        public void onDragStart() {

        }

        @Override
        public void onDragEnter() {

        }

        @Override
        public void onDragExit() {

        }

        @Override
        public void onDragEnd() {

        }

        @Override
        public void onDrop(int menuId, int itemPosition, long itemId) {
            if (menuId == R.id.action_delete) {
                list.remove(itemPosition);
                adapter.notifyDataSetChanged();
            }
        }
    };

    private BaseAdapter adapter = new BaseAdapter() {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_checked, null);
            }

            CheckedTextView tx = (CheckedTextView) convertView;
            tx.setText(list.get(position));

            return convertView;
        }

    };
}
