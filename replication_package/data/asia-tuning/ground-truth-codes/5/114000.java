class RequestAccountInfoTask implements Runnable {

    private Account account;

    public RequestAccountInfoTask(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        doRequestAccountInfo(account);
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // you can update fragment UI at here
                }
            });
        }
    }
};
