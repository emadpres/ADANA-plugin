ContentResolver.setIsSyncable(account, ContactsContract.AUTHORITY, 0);;
Account account = new Account("John Doe", "com.myapp.account");
ContentResolver.setIsSyncable(account, ContactsContract.AUTHORITY, 0);
AccountManager am = AccountManager.get(LoginActivity.this);
boolean accountCreated = am.addAccountExplicitly(account, "Password", null);
Bundle extras = LoginActivity.this.getIntent().getExtras();
if(extras != null){
    if (accountCreated) { 
        AccountAuthenticatorResponse response = extras.getParcelable(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);
        Bundle result = new Bundle();
        result.putString(AccountManager.KEY_ACCOUNT_NAME, "John Doe");
        result.putString(AccountManager.KEY_ACCOUNT_TYPE, "com.myapp.account");
        response.onResult(result);
    }
};
