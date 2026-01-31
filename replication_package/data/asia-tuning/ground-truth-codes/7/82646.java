mListView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

  @Override
public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
    mListView.removeOnLayoutChangeListener(this);
    Log.e(TAG, "updated");
  }
});

mAdapter.notifyDataSetChanged();;
