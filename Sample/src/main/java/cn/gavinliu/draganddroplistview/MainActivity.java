package cn.gavinliu.draganddroplistview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.gavinliu.android.lib.dragdrop.DDListView;
import cn.gavinliu.android.lib.dragdrop.MenuType;

public class MainActivity extends ActionBarActivity {

    DDListView listView;
    long selectId;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<String>(Arrays.asList(Cheeses.sCheeseStrings));
        setupListView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                View delete = findViewById(R.id.action_delete);
                if (delete != null) {
                    listView.addMenu(delete, MenuType.DELETE);
                }
            }
        });
        return true;
    }

    private void setupListView() {
        listView = (DDListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnDragDropListener(new DDListView.OnDragDropListener() {

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
            public void onSelect(long id) {
                selectId = id;
            }

            @Override
            public void onDrop(int menuId) {
                if (menuId == R.id.action_delete) {
                    int position = (int) selectId;
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    BaseAdapter adapter = new BaseAdapter() {

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
                convertView = getLayoutInflater().inflate(R.layout.item, null);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setText(list.get(position));

            return convertView;
        }

    };

}
