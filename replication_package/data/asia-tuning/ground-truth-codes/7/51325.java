protected void onCreate(Bundle bundle) {
     super.onCreate(bundle);

     setContentView(R.layout.settings);

     final ListView list = getListView();
     list.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            // make sure it is not called anymore
            list.getViewTreeObserver().removeGlobalOnLayoutListener(this);

            View view = list.getChildAt(1);

            ExpandableHeightGridView grid = (ExpandableHeightGridView) view.findViewById(R.id.characters_gridview);
            grid.setExpanded(true);
            grid.setAdapter(new CharacterAdapter(getCharacters()));
        }
    });
};
