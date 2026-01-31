ContentResolver.requestSync(account, authority, extras);;
AccountManager am = AccountManager.get(context);
Account account =  null;
am.getAccountsByType(mytype);
for(Account a : accounts) {
    if (am.getUserData(account, key)) {
        account = a;
        break;
    }
}
Bundle extras = new Bundle();
extras.putString(EXTRA_mystuff, myvalue);
ContentResolver.requestSync(account, authority, extras);
