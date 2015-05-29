package cn.gavinliu.draganddroplistview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.gavinliu.android.lib.dragdrop.DDListView;
import cn.gavinliu.android.lib.dragdrop.DragDropAttacher;
import cn.gavinliu.android.lib.dragdrop.listener.OnDragDropListener;
import cn.gavinliu.android.lib.dragdrop.transformer.DefaultFooterTransformer;
import cn.gavinliu.android.lib.dragdrop.transformer.DefaultHeaderTransformer;
import cn.gavinliu.android.lib.dragdrop.widget.MenuZone;
import cn.gavinliu.android.lib.dragdrop.SelectionMode;

/**
 * Created by GavinLiu on 2015-05-25
 */
public class List2 extends ActionBarActivity {

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
        listView.setSelectionMode(SelectionMode.Custom);
        listView.setOnDragDropListener(onDragDropListener);
        listView.setDragDropAttacher(new DragDropAttacher(new DefaultHeaderTransformer(this), new DefaultFooterTransformer(this)));
        listView.addMenuZone(findViewById(R.id.btn_delete), MenuZone.Type.DELETE);
        listView.setIsSwipChoise(true);
    }

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
            if (menuId == R.id.btn_delete) {
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
