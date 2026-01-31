mGridView.getViewTreeObserver().addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (mAdapter.getNumColumns() == 0) {
                        final int numColumns = (int) Math.floor(
                                mGridView.getWidth() / (mImageThumbSize + mImageThumbSpacing));
                        if (numColumns > 0) {
                            final int columnWidth =
                                    (mGridView.getWidth() / numColumns) - mImageThumbSpacing;
                            mAdapter.setNumColumns(numColumns);
                            mAdapter.setItemHeight(columnWidth);
                            if (BuildConfig.DEBUG) {
                                Log.d(TAG, "onCreateView - numColumns set to " + numColumns);
                            }
                        }
                    }
                }
            });

    return v;;
