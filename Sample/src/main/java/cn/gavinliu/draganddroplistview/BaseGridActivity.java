package cn.gavinliu.draganddroplistview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.gavinliu.android.lib.dragdrop.DDGridView;
import cn.gavinliu.android.lib.dragdrop.listener.OnDragDropListener;

/**
 * Created by GavinLiu on 2015-05-30
 */
public class BaseGridActivity extends ActionBarActivity {

    protected DDGridView gridView;
    protected List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        list = new ArrayList<String>(Arrays.asList(Cheeses.sCheeseStrings));

        setupListView();
    }

    protected void setupListView() {
        gridView = (DDGridView) findViewById(R.id.grid);
        gridView.setAdapter(adapter);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setOnDragDropListener(onDragDropListener);
    }

    protected BaseAdapter adapter = new BaseAdapter() {

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
                convertView = getLayoutInflater().inflate(R.layout.item_grid, null);
            }

            TextView tx = (TextView) convertView.findViewById(R.id.text);
            tx.setText(list.get(position));

            return convertView;
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

}
