AccountManager accountManager = AccountManager.get(context);
Account[] facebookAccounts = accountManager.getAccountsByType("com.facebook.auth.login");
if (facebookAccounts.length > 0) {
    facebook.logout(getApplicationContext());...
};
