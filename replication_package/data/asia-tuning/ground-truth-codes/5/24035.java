AccountManager accountManager = AccountManager.get(this); 
            Log.e(TAG,"Error"+AccountManager.get(this));
            Account account = getAccount(accountManager);

            if (account != null) {
              Log.d(TAG,"Account"+account.name);

            }

            Cursor c = null; Uri CONTENT_URI = Uri.parse("content://com.android.email.provider/account");
            String RECORD_ID = "_id";
            String[] ID_PROJECTION = new String[] {RECORD_ID };
            c = getContentResolver().query(CONTENT_URI,ID_PROJECTION,null, null, null);
            Log.d(TAG,"Response"+c);;
