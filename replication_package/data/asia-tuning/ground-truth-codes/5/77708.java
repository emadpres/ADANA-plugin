AccountManager accountManager = AccountManager.get(context/*activity*/);
    Account[] accounts = accountManager.getAccountsByType("com.google");
    for (Account acc : accounts) {
        userEmail = acc.name.trim();
    // do whatever you need with account
    };
