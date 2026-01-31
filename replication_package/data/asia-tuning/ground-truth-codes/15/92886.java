@Module
public class MainActivityModule {

    @Provides
    public Gson getGson(){
        return new Gson();
    }
};
@Component(
    modules = MainActivityModule.class) //The module you created 
public interface IAppModule {
    void inject(MainActivity activity);
};
IAppModule appComponent;

@Inject
Gson gson;

public void setupDaggerGraph(){ //call this method in your onCreate()
    appComponent = DaggerIAppModule.builder().mainActivityModule(new MainActivityModule()).build();
    appComponent.inject(this);
};
