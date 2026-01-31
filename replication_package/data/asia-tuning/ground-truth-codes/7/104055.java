interface OnItemClickListener {
    void onItemClicked(int position);
};
private OnItemClickListener listener = new OnItemClickListener() {
    public void onItemClicked(int position) {
        Song song = adapter.get(position);
        //do whatever you want with song
    }
}

//creation of adapter
SongAdapter adapter = new SongAdapter(this, songs, listener);;
private OnItemClickListener onClickListener;

SongAdapter(Context context, ArrayList<Song> songs, OnItemClickListener onClickListener) {
    super(context, 0, songs);
    this.onClickListener = onClickListener;
}

//in getView
addToPlaylistButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onClickListener != null) {
                onClickListener.onItemClicked(position);
            }
        }
    });;
