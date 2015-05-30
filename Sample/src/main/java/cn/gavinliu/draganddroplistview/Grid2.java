package cn.gavinliu.draganddroplistview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by GavinLiu on 2015-05-30
 */
public class Grid2 extends Grid1 {

    @Override
    protected void setupListView() {
        super.setupListView();
        gridView.setIsSwipeChoise(true);
        gridView.setAdapter(adapter);
    }

    protected BaseAdapter adapter = new BaseAdapter() {

        int[] colors = {0xFFE51C23, 0xFFE91E63, 0xFF9C27B0, 0xFF009688, 0xFF0D5302, 0xFF03A9F4, 0xFFCDDC93, 0xFF259B24, 0xFF8BC34A, 0xFFFF9800, 0xFF795548, 0xFFFF5722};

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
                convertView = getLayoutInflater().inflate(R.layout.item_grid_animate, null);
            }

            TextView tx = (TextView) convertView.findViewById(R.id.text);

            int i = position % colors.length;
            tx.setBackgroundColor(colors[i]);

            return convertView;
        }

    };
}
