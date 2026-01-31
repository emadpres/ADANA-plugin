final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
feedsRecyclerView.setLayoutManager(layoutManager);

feedsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (dy > 0) {   
            if ((layoutManager.getChildCount() + layoutManager.findFirstVisibleItemPosition()) >= layoutManager.getItemCount()) {
                Log.d("TAG", "End of list");
                //loadMore();
            }
        }
    }
});;
