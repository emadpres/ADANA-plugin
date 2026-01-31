AccountManager accountManager=new AccountManager(connection);
try {
    accountManager.createAccount("username", "password");
} catch (XMPPException e1) {
    Log.d(e1.getMessage(), e1);
};
