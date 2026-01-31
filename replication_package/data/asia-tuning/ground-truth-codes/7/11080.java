import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewTreeObserver;

/**
 * Allows RecyclerView work like a FlowLayout.*
 * Created by douglas on 16/09/15.*/
public class OnFlowLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

    private final RecyclerView recyclerView;
    private final float cardViewWidth;

    public OnFlowLayoutListener(RecyclerView recyclerView, float cardViewWidth) {
        this.recyclerView = recyclerView;
        this.cardViewWidth = cardViewWidth;
    }

    @Override
    public void onGlobalLayout() {
      //  recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        int viewWidth = recyclerView.getMeasuredWidth();
        int newSpanCount = (int) Math.floor(viewWidth / cardViewWidth);
        if (newSpanCount > 0 && newSpanCount != ((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount()) {
            ((GridLayoutManager) recyclerView.getLayoutManager()).setSpanCount(newSpanCount);
            recyclerView.requestLayout();
            //recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }
    }
}
