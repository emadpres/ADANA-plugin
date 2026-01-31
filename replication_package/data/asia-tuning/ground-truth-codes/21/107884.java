//I use the OnTabChangeListener solve the question.mTabHost.setOnTabChangedListener(new OnTabChangeListener(){
        @Override
        public void onTabChanged(String tabId) {
            if (DEBUG) Log.d(TAG, "onTabChanged()");
            if("testTabId".equals(tabId)) {
                //destroy earth

                Intent intentTabMe = new Intent(MainActivity.this, UserDetailsActivity.class);
                intentTabMe.setAction(Intent.ACTION_VIEW);

                startActivity(intentTabMe);
            }                
        }
    });;
