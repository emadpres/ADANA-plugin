var setupSingleton = MvxAndroidSetupSingleton.EnsureSingletonAvailable(context);
setupSingleton.EnsureInitialized();;
protected async override void OnMessage(Context context, Intent intent)
{
    var setupSingleton = MvxAndroidSetupSingleton.EnsureSingletonAvailable(context);
    setupSingleton.EnsureInitialized();

    await MyMobileApp.EnsureDataStoreInitialized();

    var logger = Mvx.Resolve<ILogger>();
    logger.Send(LogLevel.Info, "Notification received");
};
