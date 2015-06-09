package cn.gavinliu.draganddroplistview;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import cn.gavinliu.android.lib.dragdrop.SelectionAttacher;
import cn.gavinliu.android.lib.dragdrop.SelectionMode;
import cn.gavinliu.android.lib.dragdrop.listener.OnDragDropListener;
import cn.gavinliu.android.lib.dragdrop.transformer.DefaultFooterTransformer;
import cn.gavinliu.android.lib.dragdrop.transformer.DefaultHeaderTransformer;
import cn.gavinliu.android.lib.dragdrop.widget.MenuZone;

/**
 * Created by GavinLiu on 2015-05-25
 */
public class List2 extends BaseListActivity {

    @Override
    protected void setupListView() {
        super.setupListView();

        listView.setSelectionMode(SelectionMode.Custom);
        listView.setOnDragDropListener(onDragDropListener);

        listView.setDragDropAttacher(new SelectionAttacher(new DefaultHeaderTransformer(this, 56 * 3), new MyDefaultFooterTransformer(this, 56 * 3)));
        listView.addMenuZone(findViewById(R.id.btn_delete), MenuZone.Type.DELETE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), position + " onItemClick Click", Toast.LENGTH_SHORT).show();
            }
        });

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

    public class MyDefaultFooterTransformer extends DefaultFooterTransformer {

        public MyDefaultFooterTransformer(Activity activity, int height) {
            super(activity, height);

            activity.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Delete Click", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
