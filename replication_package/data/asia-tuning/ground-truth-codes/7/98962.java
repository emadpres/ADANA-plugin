if (BuildConfig.DEBUG) {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
            Log.wtf("Alert", paramThrowable.getMessage(), paramThrowable);
            System.exit(2); //Prevents the service/app from freezing
        }
    });
};
final UncaughtExceptionHandler oldHandler = Thread.getDefaultUncaughtExceptionHandler();;
