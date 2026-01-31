package com.example.android.cardview;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

public class Emad {
    public int emad;
    private int foo, boo;
    private String baz;

    public void foo()
    {
        int a,b;
        a = 10;
        b=10;
        int c=10;
        c++;
        c--;
        int d = b+a;
    }

    public void foo(int a)
    {
        int b;
        a = 10;
        b=10;
        int c=10;
        c++;
        c--;
        int d = b+a;
    }

    public Emad()
    {

        emad++;
        emad++;
        int age = 0;
        emad = 90;
        foo = age;
        System.out.println("simple:website");
        for (int i=0; i<100; i++)
        {
            emad++;
            emad++;
            emad++;
            while(foo++<1000)
            {
                foo--;
                int a,b;
                a = 10;
                a = 10;
                b=10;
                while(foo)
                {

                    ConnectivityManager mgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    Method dataMtd = ConnectivityManager.class.getDeclaredMethod(setMobileDataEnabled, boolean.class);
                    dataMtd.setAccessible(true);
                    while(foo)
                    {
                        ConnectivityManager mgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                        Method dataMtd = ConnectivityManager.class.getDeclaredMethod(setMobileDataEnabled, boolean.class);
                        dataMtd.setAccessible(true);
                        dataMtd.invoke(mgr, true);
                    }
                    dataMtd.invoke(mgr, true);

                }

                if (mFileDescriptor != null)
                {

                    mPdfRenderer = new PdfRenderer(mFileDescriptor);
                    int a = 0;
                    a--;
                    mCurrentPage = mPdfRenderer.openPage(a++);
                    Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(), mCurrentPage.getHeight(), Bitmap.Config.ARGB_8888);
                    mCurrentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                    mImageView.setImageBitmap(bitmap);
                }

                int c=10;
                c++;
                int d = b+a;

            }



        }
        age++;
        foo = emad + foo; 
        foo = boo = emad+10;
        baz = "test";
    }




    public ApplicationComponent getComponent() {

        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
            int a=3;
        }
        return mApplicationComponent;
    }

    public ApplicationComponent getComponent() {

        int a=3;
    }

    public class CardViewActivity extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_card_view);
            if (savedInstanceState == null) {
                getFragmentManager().beginTransaction()
                        .add(R.id.container, com.example.android.cardview.CardViewFragment.newInstance())
                        .commit();
            }
        }
    }

    public int sum2(int a, int b)
    {
        return a+b;
    }
    public int sum3(int a, int b);

    public int sum4(int a, int b)
    {
        return a+b;
    }

    void sample4()
    {
        gestureView.setClickable(true);
        gestureView.setFocusable(true);

        GestureDetector.SimpleOnGestureListener gestureListener = new GestureListener();
        final GestureDetector gd = new GestureDetector(getActivity(), gestureListener);

        gestureView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gd.onTouchEvent(motionEvent);
                gd.onTouchEvent(motionEvent);
                gd.onTouchEvent(motionEvent);
                return false;
            }
        });
    }


    void sample7()
    {

        mGridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @TargetApi(VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        if (mAdapter.getNumColumns() == 0) {
                            final int numColumns = (int) Math.floor(mGridView.getWidth() / (mImageThumbSize + mImageThumbSpacing));
                            if (numColumns > 0) {
                                final int columnWidth = (mGridView.getWidth() / numColumns) - mImageThumbSpacing;
                                mAdapter.setNumColumns(numColumns);
                                mAdapter.setItemHeight(columnWidth);
                                if (BuildConfig.DEBUG) {
                                    Log.d(TAG, "onCreateView - numColumns set to " + numColumns);
                                    Log.d(TAG, "onCreateView - numColumns set to " + numColumns);
                                    Log.d(TAG, "onCreateView - numColumns set to " + numColumns);
                                }
                                if (Utils.hasJellyBean()) {
                                    mGridView.getViewTreeObserver()
                                            .removeOnGlobalLayoutListener(this);
                                } else {
                                    mGridView.getViewTreeObserver()
                                            .removeGlobalOnLayoutListener(this);
                                }
                            }
                        }
                    }
                });
    }

    void sample16()
    {
        File file = new File(context.getCacheDir(), FILENAME);
        if (!file.exists()) {
            InputStream asset = context.getAssets().open(FILENAME);
            FileOutputStream output = new FileOutputStream(file);
            final byte[] buffer = new byte[1024];
            int size;
            while ((size = asset.read(buffer)) != -1) {
                output.write(buffer, 0, size);
            }
            asset.close();
            output.close();
        }
        mFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);


        if (mFileDescriptor != null) {

            mPdfRenderer = new PdfRenderer(mFileDescriptor);
            int a = 0;
            a--;
            mCurrentPage = mPdfRenderer.openPage(a++);
            Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(), mCurrentPage.getHeight(), Bitmap.Config.ARGB_8888);
            mCurrentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            mImageView.setImageBitmap(bitmap);
        }
    }


    public String getConnectivityStatusString() {
        int conn = getConnectivityStatus();
        String status = null;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }

    void sasad()
    {
        File file = new File(context.getCacheDir(), FILENAME);
        if (!file.exists()) {
            InputStream asset = context.getAssets().open(FILENAME);
            FileOutputStream output = new FileOutputStream(file);
            final byte[] buffer = new byte[1024];
            int size;
            while ((size = asset.read(buffer)) != -1)
            {
                output.write(buffer, 0, size);
            }
            asset.close();
            output.close();
        }
        mFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        if (mFileDescriptor != null)
        {
            mPdfRenderer = new PdfRenderer(mFileDescriptor);
            mCurrentPage = mPdfRenderer.openPage(0);
            Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(), mCurrentPage.getHeight(), Bitmap.Config.ARGB_8888);
            mCurrentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            mImageView.setImageBitmap(bitmap);
        }


        while(true)
        {

            ConnectivityManager mgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            Method dataMtd = ConnectivityManager.class.getDeclaredMethod(setMobileDataEnabled, boolean.class);
            dataMtd.setAccessible(true);
            dataMtd.invoke(mgr, true);
        }

        ConnectivityManager mgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        Method dataMtd = ConnectivityManager.class.getDeclaredMethod(setMobileDataEnabled, boolean.class);
        dataMtd.setAccessible(true);
        dataMtd.invoke(mgr, true);
    }

    private void StartSplashScreenAnim() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.splashLinLay);
        layout.clearAnimation();
        layout.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView imageView = (ImageView) findViewById(R.id.logo);
        imageView.setImageResource(R.drawable.appicon);
        imageView.clearAnimation();
        imageView.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser != null && (ParseFacebookUtils.isLinked(currentUser))) {
                    if (NetworkStatusReceiver.isOnline) {
                        Intent i = new Intent(splash.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Unable to Login! Check Network Connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent i = new Intent(splash.this, login.class);
                    startActivity(i);
                    finish();
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportFragmentManager().findFragmentByTag(FRAGTAG) == null ) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BasicGestureDetectFragment fragment = new BasicGestureDetectFragment();
            transaction.add(fragment, FRAGTAG);
            transaction.commit();
        }
    }

    private void checkNetworkConnection() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if(wifiConnected) {
                Log.i(TAG, getString(R.string.wifi_connection));
            } else if (mobileConnected){
                Log.i(TAG, getString(R.string.mobile_connection));
            }
        } else {
            Log.i(TAG, getString(R.string.no_wifi_or_mobile));
        }
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View gestureView = getActivity().findViewById(R.id.sample_output);
        gestureView.setClickable(true);
        gestureView.setFocusable(true);

        GestureDetector.SimpleOnGestureListener gestureListener = new GestureListener();
        final GestureDetector gd = new GestureDetector(getActivity(), gestureListener);

        gestureView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gd.onTouchEvent(motionEvent);
                return false;
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sample_action) {

            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setAction(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), REQUEST_CODE,
                    intent, 0);

            int alarmType = AlarmManager.ELAPSED_REALTIME;
            final int FIFTEEN_SEC_MILLIS = 15000;

            AlarmManager alarmManager = (AlarmManager)
                    getActivity().getSystemService(getActivity().ALARM_SERVICE);

            alarmManager.setRepeating(alarmType, SystemClock.elapsedRealtime() + FIFTEEN_SEC_MILLIS,
                    FIFTEEN_SEC_MILLIS, pendingIntent);
            Log.i("RepeatingAlarmFragment", "Alarm set.");
        }
        return true;
    }

    private void handleResponse(Page page) {
        if (page != null && page.getText() != null && !page.getText().isEmpty()) {
            setContentVisible(true);
            pageTitle.setText(page.getTitle());
            String data = page.getText();
            String header = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">"
                    + "<html>  <head>  <meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">"
                    + "</head>  <body>";
            String footer = "</body></html>";
            pageContent.loadData(header + data + footer, "text/html; charset=UTF-8", null);
        } else {
            setContentVisible(false);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null) progressDialog.cancel();
            }
        }, 200);
    }

    private void openRenderer(Context context) throws IOException {


        File file = new File(context.getCacheDir(), FILENAME);
        if (!file.exists()) {
            InputStream asset = context.getAssets().open(FILENAME);
            FileOutputStream output = new FileOutputStream(file);
            final byte[] buffer = new byte[1024];
            while ((size = asset.read(buffer)) != -1) {
                output.write(buffer, 0, size);
            }
            asset.close();
            output.close();
        }


        mFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        if (mFileDescriptor != null) {
            mPdfRenderer = new PdfRenderer(mFileDescriptor);
        }

        if (mPdfRenderer.getPageCount() <= index) {
            return;
        }
        if (null != mCurrentPage) {
            mCurrentPage.close();
        }

        mCurrentPage = mPdfRenderer.openPage(index);
        Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(), mCurrentPage.getHeight(),
                Bitmap.Config.ARGB_8888);
        mCurrentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        mImageView.setImageBitmap(bitmap);
        updateUi();

    }






    public void openPdfFileFromAsset(String filename, int pageNo)
    {
        File pdfFile = new File(context.getCacheDir(), filename);
        if (!file.exists()) {
            try {
                InputStream in = context.getAssets().open(filename);
                OutputStream out = new FileOutputStream(pdfFile);
                IOUtils.copy(in, out);
            } catch (IOException ioe) {
                Log.e(LOGTAG, "IOException occurred.", ioe);
            } finally {
                IOUtils.closeQuietly(out);
                IOUtils.closeQuietly(in);
            }
        }
        mFileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY);
        if (mFileDescriptor != null) {
            mPdfRenderer = new PdfRenderer(mFileDescriptor);
            mCurrentPage = mPdfRenderer.openPage(pageNo);
            Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(), mCurrentPage.getHeight(), Bitmap.Config.ARGB_8888);
            mCurrentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            mImageView.setImageBitmap(bitmap);
        }
    }


    // Result Sample OF ADANA ICSE
//    public void openPdfFileFromAsset(String filename, int pageNo)
//    {
//        File pdfFile = new File(context.getCacheDir(), filename);
//        if (!file.exists()) {
//            // Makes a copy of a file.
//            try {
//                // Loads a file from assets folder of my android test project.
//                InputStream in = context.getAssets().open(filename);
//                OutputStream out = new FileOutputStream(pdfFile);
//                IOUtils.copy(in, out);
//            } catch (IOException ioe) {
//                Log.e(LOGTAG, "IOException occurred.", ioe);
//            } finally {
//                IOUtils.closeQuietly(out);
//                IOUtils.closeQuietly(in);
//            }
//        }
//        mFileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY);
//        if (mFileDescriptor != null) {
//            // Renders PDF.
//            mPdfRenderer = new PdfRenderer(mFileDescriptor);
//            mCurrentPage = mPdfRenderer.openPage(pageNo);
//            Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(), mCurrentPage.getHeight(), Bitmap.Config.ARGB_8888);
//            mCurrentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
//            mImageView.setImageBitmap(bitmap);
//        }
//    }


    public void openPdfFileFromAsset(String filename, int pageNo)
    {
        File pdfFile = new File(context.getCacheDir(), filename);
        if (!file.exists()) {

            try {
                InputStream in = context.getAssets().open(filename);
                OutputStream out = new FileOutputStream(pdfFile);
                IOUtils.copy(in, out);
            } catch (IOException ioe) {
                Log.e(LOGTAG, "IOException occurred.", ioe);
            } finally {
                IOUtils.closeQuietly(out);
                IOUtils.closeQuietly(in);
            }
        }

        mFileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY);
        if (mFileDescriptor != null) {
            mPdfRenderer = new PdfRenderer(mFileDescriptor);
            mCurrentPage = mPdfRenderer.openPage(pageNo);
            Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(), mCurrentPage.getHeight(), Bitmap.Config.ARGB_8888);
            mCurrentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            mImageView.setImageBitmap(bitmap);
        }




        final Cursor cursor = db.rawQuery("SELECT SUM(odometer) as odometer FROM tripmileagetable where date like '2012-07%';", null);
        int sum = 0;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    sum = cursor.getInt(0);
                }
            } finally {
                cursor.close();
            }
        }



    }


    public void copyFile(File src, File dst)  {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dst);
            IOUtils.copy(in, out);
        } catch (IOException ioe) {
            Log.e(LOGTAG, "IOException occurred.", ioe);
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
    };



}

}