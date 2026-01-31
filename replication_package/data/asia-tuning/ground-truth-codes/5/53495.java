// Global Variables 
    public static final String AUTHORITY = "com.example.package";
    public static final String ACCOUNT_TYPE = "com.example.package";
    public static final String ACCOUNT = "my_custom_account_name";

    // Account Manager definition
    AccountManager accountManager = (AccountManager) this.getSystemService(ACCOUNT_SERVICE);

    // loop through all accounts to remove them
    Account[] accounts = accountManager.getAccounts();
    for (int index = 0; index < accounts.length; index++) {
    if (accounts[index].type.intern() == AUTHORITY)
        accountManager.removeAccount(accounts[index], null, null);
    };
