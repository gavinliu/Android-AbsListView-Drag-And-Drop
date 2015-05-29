package cn.gavinliu.draganddroplistview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void list1(View v) {
        startActivity(new Intent(this, List1.class));
    }

    public void list2(View v) {
        startActivity(new Intent(this, List2.class));
    }

    public void list3(View v) {
        startActivity(new Intent(this, List3.class));
    }

}
