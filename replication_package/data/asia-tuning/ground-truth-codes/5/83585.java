AccountManager manager = AccountManager.get(context);
Account[] accounts = manager.getAccountsByType("com.google");
List<String> username = new LinkedList<String>();

for (Account account : accounts) {
    username.add(account.name);
};
