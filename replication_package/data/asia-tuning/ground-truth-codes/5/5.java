Account account = GenericAccountService.GetAccount(ACCOUNT_TYPE);
AccountManager accountManager =
        (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
if (accountManager.addAccountExplicitly(account, null, null)) {
    ContentResolver.setIsSyncable(account, CONTENT_AUTHORITY, 1);
    ContentResolver.setSyncAutomatically(account, CONTENT_AUTHORITY, true);
    ContentResolver.addPeriodicSync(
            account, CONTENT_AUTHORITY, new Bundle(),SYNC_FREQUENCY);
    newAccount = true;
}