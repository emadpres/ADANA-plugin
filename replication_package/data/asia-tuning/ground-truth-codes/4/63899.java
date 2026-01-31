eventTitleView.setFocusable(false); 
eventTitleView.setFocusableInTouchMode(false);
eventTitleView.setLongClickable(true);

eventDescView.setFocusable(false); 
eventDescView.setFocusableInTouchMode(false);
eventDescView.setLongClickable(true);

eventDateView.setFocusable(false); 
eventDateView.setFocusableInTouchMode(false);
eventDateView.setLongClickable(true);;
eventTitleView.setOnItemLongClickListener(new OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            //do your sorting stuff here
        }
    });

eventDescView.setOnItemLongClickListener(new OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            //do your sorting stuff here
        }
    });

eventDateView.setOnItemLongClickListener(new OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            //do your sorting stuff here
        }
    });;
