AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
Account[] list = manager.getAccounts();
String gmail = null;

    for(Account account: list)
    {
        if(account.type.equalsIgnoreCase("com.google"))
        {
            gmail = account.name;
            break;
        }
    };
