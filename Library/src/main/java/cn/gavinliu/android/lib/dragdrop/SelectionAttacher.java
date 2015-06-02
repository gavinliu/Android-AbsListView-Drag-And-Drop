package cn.gavinliu.android.lib.dragdrop;

import cn.gavinliu.android.lib.dragdrop.transformer.FooterTransformer;
import cn.gavinliu.android.lib.dragdrop.transformer.HeaderTransformer;

/**
 * Created by GavinLiu on 2015-05-27
 */
public class SelectionAttacher {

    private HeaderTransformer mHeaderTransformer;
    private FooterTransformer mFooterTransformer;

    public SelectionAttacher(HeaderTransformer headerTransformer, FooterTransformer footerTransformer) {
        setTransformer(headerTransformer, footerTransformer);
    }

    public void show() {
        mHeaderTransformer.showContentView();
        mFooterTransformer.showContentView();
    }

    public void hide(){
        mHeaderTransformer.hideContentView();
        mFooterTransformer.hideContentView();
    }

    public void updateChooseCount(int count) {
        mHeaderTransformer.updateChooseCount(count);
    }

    public void setMultiChoosable(MultiChoosable multiChoosable) {
        mHeaderTransformer.setMultiChoosable(multiChoosable);
    }

    public FooterTransformer getFooterTransformer() {
        return mFooterTransformer;
    }

    public void setTransformer(HeaderTransformer header, FooterTransformer footer) {
        mHeaderTransformer = header;
        mFooterTransformer = footer;

        mHeaderTransformer.onViewCreated();
        mFooterTransformer.onViewCreated();

        mHeaderTransformer.setDragDropAttacher(this);
        mFooterTransformer.setDragDropAttacher(this);
    }

}
