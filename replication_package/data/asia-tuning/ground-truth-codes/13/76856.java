private RestAdapter getRestAdapter(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(new TypeToken<HashMap<String, String>>(){}.getType(), new MyHashMapDeserializer());
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setClient(new OkClient());
        builder.setLogLevel(RestAdapter.LogLevel.FULL);
        builder.setExecutors(Executors.newCachedThreadPool(), new MainThreadExecutor());
        builder.setConverter(new GsonConverter(gsonBuilder.create()));
        builder.setEndpoint(API_END_POINT_URL);
        return builder.build();
    };
