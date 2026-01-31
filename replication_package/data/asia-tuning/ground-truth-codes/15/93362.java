TwitterAuthConfig authConfig =  new TwitterAuthConfig("consumerKey", "consumerSecret");
Fabric.with(this, new Crashlytics(),new Twitter(authConfig));;
compile('com.twitter.sdk.android:twitter:1.11.0@aar') {
        transitive = true;
};
Fabric.Builder builder = new Fabric.Builder(this).kits(new Crashlytics(), new Twitter(authConfig));;
Fabric.with(builder.build);;
