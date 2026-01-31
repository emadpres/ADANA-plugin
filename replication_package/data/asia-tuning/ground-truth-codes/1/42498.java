import android.hardware.camera2.CameraManager;

    // within constructor
    // Figure out if Camera is Available or Not
    CameraManager cam_manager = (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);
    cam_manager.registerAvailabilityCallback(camAvailCallback, mHandler);


    CameraManager.AvailabilityCallback camAvailCallback = new CameraManager.AvailabilityCallback() {
    public void onCameraAvailable(String cameraId) {

        cameraInUse=false;
        Log.d(TAG, "notified that camera is not in use.");

    }

    public void onCameraUnavailable(String cameraId) {

        cameraInUse=true;
        Log.d(TAG, "notified that camera is in use.");

    }
};;
