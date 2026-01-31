
import android.os.Build;

import android.support.v4.app.ListFragment;

public class  HeadListFragment extends ListFragment {

	@Override
	public void setListAdapter(ListAdapter adapter) {
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			View fixFooter = new View(getActivity());
			getListView().addFooterView(fixFooter);
			super.setListAdapter(adapter);
			getListView().removeFooterView(fixFooter);
		} else {
			super.setListAdapter(adapter);
		}
	}

}