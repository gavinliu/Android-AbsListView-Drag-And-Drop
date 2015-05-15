package cn.gavinliu.draganddroplistview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import cn.gavinliu.android.lib.dragdrop.DDListView;
import cn.gavinliu.android.lib.dragdrop.MenuType;

public class MainActivity extends ActionBarActivity {

    DDListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupListView();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                View delete = findViewById(R.id.x);
                if (delete != null) {
                    listView.addMenu(delete, MenuType.DELETE);
                }
            }
        });
        return true;
    }

    private void setupListView() {
        listView = (DDListView) findViewById(R.id.list);
        listView.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return 50;
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
                return getLayoutInflater().inflate(R.layout.item, null);
            }

        });
    }

}
