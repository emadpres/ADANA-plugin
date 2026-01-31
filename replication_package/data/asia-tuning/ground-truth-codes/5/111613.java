final AccountManager accountMgr = AccountManager.get(Main.this);
accountMgr.addAccount(Constants.ACCOUNT_TYPE, Constants.AUTHTOKEN_TYPE, null, null, Main.this, null, null);;
