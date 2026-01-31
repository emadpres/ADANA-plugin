public static AccountManager accountManager;
accountManager = AccountManager.get(this);
Account[] accounts = accountManager.getAccountsByType("com.google");;
private void onAccountSelected(final Account account) {
accountManager.getAuthToken(account, AUTH_TOKEN_TYPE, null, this, new AccountManagerCallback<Bundle>() {
public void run(AccountManagerFuture<Bundle> future) {
    try {
        String token = future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
        useToken(account, token);
    } catch (OperationCanceledException e) {
        onAccessDenied();
    } catch (Exception e) {
        handleException(e);
    }
}
}, null);

};
accountManager.invalidateAuthToken("com.google", token);;
String newToken = AccountManager.get(this).getAuthToken(new Account(account,        "com.google"),
             AUTH_TOKEN_TYPE, true, null, null).getResult().getString(AccountManager.KEY_AUTHTOKEN);;
