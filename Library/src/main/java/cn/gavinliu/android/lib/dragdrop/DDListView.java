package cn.gavinliu.android.lib.dragdrop;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by gavin on 15-5-13.
 */
public class DDListView extends ListView {

    public DDListView(Context context) {
        this(context, null);
    }

    public DDListView(Context context, AttributeSet attrs) {
        this(context, attrs, com.android.internal.R.attr.listViewStyle);
    }

    public DDListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
