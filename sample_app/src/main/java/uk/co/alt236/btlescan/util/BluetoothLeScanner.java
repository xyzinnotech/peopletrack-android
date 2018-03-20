package uk.co.alt236.btlescan.util;

import android.bluetooth.BluetoothAdapter;
import android.os.Handler;
import android.util.Log;

public class BluetoothLeScanner {
    private final Handler mHandler;
    private final BluetoothAdapter.LeScanCallback mLeScanCallback;
    private final BluetoothUtils mBluetoothUtils;
    private boolean mScanning;

    public BluetoothLeScanner(final BluetoothAdapter.LeScanCallback leScanCallback, final BluetoothUtils bluetoothUtils) {
        mHandler = new Handler();
        mLeScanCallback = leScanCallback;
        mBluetoothUtils = bluetoothUtils;
    }

    public boolean isScanning() {
        return mScanning;
    }

    public void scanLeDevice(final int duration, final boolean enable) {

        if (enable) {
            if (mScanning) {
                return;
            }
            Log.d("TAG", "~ Starting Scan");
            //duration=20000;
            // Stops scanning after a pre-defined scan period.
            if (duration > 0) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TAG", "~ Stopping Scan (timeout)");
                        mScanning = false;
                        mBluetoothUtils.getBluetoothAdapter().stopLeScan(mLeScanCallback);
                    }
                }, 2000);
            }

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("TAG", "~ Stopping Scan (timeout)");
                    mScanning = true;
                    mBluetoothUtils.getBluetoothAdapter().startLeScan(mLeScanCallback);
                }
            }, 5000);

        }
        else {
            Log.d("TAG", "~ Stopping Scan");
            mScanning = false;
            mBluetoothUtils.getBluetoothAdapter().stopLeScan(mLeScanCallback);
        }
    }
}
