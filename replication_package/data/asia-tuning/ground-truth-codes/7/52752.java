listItem.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

  public void onGlobalLayout() {
    int listItemHeight = listItem.getHeight();
  }
});;
listView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

  public void onGlobalLayout() {
    int listViewHeight = listView.getHeight();
  }
});;
