package uk.co.alt236.btlescan.ui.main;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconType;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconUtils;
import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.containers.BluetoothLeDeviceStore;
import uk.co.alt236.btlescan.ui.common.Navigation;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewBinderCore;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;
import uk.co.alt236.btlescan.ui.main.recyclerview.binder.GPSTracker;
import uk.co.alt236.btlescan.ui.main.recyclerview.model.IBeaconItem;
import uk.co.alt236.btlescan.ui.main.recyclerview.model.LeDeviceItem;
import uk.co.alt236.btlescan.util.BluetoothLeScanner;
import uk.co.alt236.btlescan.util.BluetoothUtils;
import uk.co.alt236.btlescan.util.Constants;
import uk.co.alt236.btlescan.util.ConstantsRefernece;
import uk.co.alt236.btlescan.util.GetDeviceInfo;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.tvBluetoothLe)
    protected TextView mTvBluetoothLeStatus;
    @Bind(R.id.tvBluetoothStatus)
    protected TextView mTvBluetoothStatus;
    @Bind(R.id.tvItemCount)
    protected TextView mTvItemCount;

    @Bind(android.R.id.list)
    protected RecyclerView mList;
    @Bind(android.R.id.empty)
    protected View mEmpty;
    private Handler mHandler;
    private RecyclerViewBinderCore mCore;
    private BluetoothUtils mBluetoothUtils;
    private BluetoothLeScanner mScanner;
    private BluetoothLeDeviceStore mDeviceStore;
    private DeviceRecyclerAdapter mRecyclerAdapter;
    private GetDeviceInfo getDeviceInfo;
    private final HashMap<String, Double> map = new HashMap<>();
    private DatabaseReference insertMobilesRefernce,insertAssetRef;
    //GPS
    private GPSTracker gpsTracker;
    double latitude;
    double longitude;

    public int i=0;

    private final BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {


            final BluetoothLeDevice deviceLe = new BluetoothLeDevice(device, rssi, scanRecord, System.currentTimeMillis());
            mDeviceStore.addDevice(deviceLe);
            final List<RecyclerViewItem> itemList = new ArrayList<>();

            for (final BluetoothLeDevice leDevice : mDeviceStore.getDeviceList()) {
                if (BeaconUtils.getBeaconType(leDevice) == BeaconType.IBEACON) {
                    itemList.add(new IBeaconItem(new IBeaconDevice(leDevice)));
                    device.getUuids();
                    Log.d("ledvc_s",leDevice.getAddress().toString()+" s: "+leDevice.getName());

                } else {
                  //  itemList.add(new LeDeviceItem(leDevice));
                }




            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mRecyclerAdapter.setData(itemList);
                    updateItemCount(mRecyclerAdapter.getItemCount());
                }
            });
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getDeviceInfo = new GetDeviceInfo() {
            @Override
            public void getDeviceInfo(final IBeaconDevice device) {
                ConstantsRefernece.mFirebaseInstance = FirebaseDatabase.getInstance();
                insertMobilesRefernce=ConstantsRefernece.mFirebaseRef.child(device.getAddress());
                gpsTracker=new GPSTracker(getApplicationContext());
                insertFirebaseData(device.getAccuracy(),device.getAddress());
                //insertAssetdata(device.getAddress());
                gpsTracker.getDeviceCordinates();
                if (gpsTracker.canGetLocation()) {
                    latitude = gpsTracker.getLatitude();
                    longitude = gpsTracker.getLongitude();
                }

            }

            @Override
            public void postFormValues(String name, long ts, String workType, String addressTag) {
                Log.d("tst_name: ",""+name);
                Log.d("tst_ts: ",""+String.valueOf(ts));
                Log.d("tst_work_type: ",""+workType);
                Log.d("tst_adress_: ",""+addressTag);
                if (addressTag.contentEquals("D4:F5:13:7A:15:64")){
                    final HashMap<String, String> assetmap = new HashMap<>();
                    DatabaseReference insertAssetRefOne=ConstantsRefernece.mFirebaseRef.child("D4:F5:13:7A:15:64").child(ConstantsRefernece.mAssets);
                    assetmap.put(ConstantsRefernece.mName,name);
                    assetmap.put(ConstantsRefernece.mts,String.valueOf(ts));
                    assetmap.put(ConstantsRefernece.mWorktype,workType);
                    insertAssetRefOne.setValue(assetmap);
                    Log.d("MAcAddress","One");
                }else {
                    final HashMap<String, String> assetmap = new HashMap<>();
                    DatabaseReference insertAssetRefOne=ConstantsRefernece.mFirebaseRef.child("20:C3:8F:F1:88:4B").child(ConstantsRefernece.mAssets);
                    assetmap.put(ConstantsRefernece.mName,name);
                    assetmap.put(ConstantsRefernece.mts,String.valueOf(ts));
                    assetmap.put(ConstantsRefernece.mWorktype,workType);
                    insertAssetRefOne.setValue(assetmap);
                    Log.d("MAcAddress","Two");
                }
                /*final HashMap<String, String> assetmap = new HashMap<>();
                DatabaseReference insertAssetRefOne=ConstantsRefernece.mFirebaseRef.child(addressTag).child(ConstantsRefernece.mAssets);
                assetmap.put(ConstantsRefernece.mName,name);
                assetmap.put(ConstantsRefernece.mts,String.valueOf(ts));
                assetmap.put(ConstantsRefernece.mWorktype,workType);
                insertAssetRefOne.setValue(assetmap);
                *///reference1 = mFirebaseInstance.getReference("beacons-mobile-distance").child(addressTag).child("assets");
               // reference1.setValue(assetmap);
            }
        };
        mCore = RecyclerViewCoreFactory.create(this, new Navigation(this),getDeviceInfo);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mDeviceStore = new BluetoothLeDeviceStore();
        mBluetoothUtils = new BluetoothUtils(this);
        mScanner = new BluetoothLeScanner(mLeScanCallback, mBluetoothUtils);
        updateItemCount(0);

    }

    private void insertAssetdata(final String address) {
        insertAssetRef=ConstantsRefernece.mFirebaseRef.child(address);

        DatabaseReference rootRef = ConstantsRefernece.mFirebaseRef.child(address);
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("assets")) {
                    // run some code
                    Log.d("ConstantValues","Value here");

                }
                else
                    {
                    HashMap<String, String> assetmap = new HashMap<>();
                    assetmap.put(ConstantsRefernece.mName,"null");
                    assetmap.put(ConstantsRefernece.mts,"null");
                    assetmap.put(ConstantsRefernece.mWorktype,"null");
                    insertAssetRef.child(ConstantsRefernece.mAssets).setValue(assetmap);
                    Log.d("ConstantValues","Value Not here");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


/*
        if(insertAssetRef.child(ConstantsRefernece.mAssets).equals(ConstantsRefernece.mAssets))
        {
         }
*/

    }




    private void insertFirebaseData(final double distance, String address) {


        // reference = mFirebaseInstance.getReference("beacons-mobile-distance").child(address);
        //GettingDetails getdevicedetails=new GettingDetails(device);
       final String android_id = android.provider.Settings.Secure.getString(this.getContentResolver(), "bluetooth_address");

/*        String android_id = Settings.Secure.getString(MainActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);*/
        Long tsLong = System.currentTimeMillis() / 1000;
        // ts = tsLong;
        map.put(ConstantsRefernece.mDist, distance);

        map.put(ConstantsRefernece.mts, Double.valueOf(tsLong));

        map.put(ConstantsRefernece.mlat, latitude);
        map.put(ConstantsRefernece.mlng, longitude);
        Log.d("Checking", " " + distance + " " + tsLong + " " + gpsTracker.getLatitude() + " " + gpsTracker.getLatitude());
        insertMobilesRefernce.child(ConstantsRefernece.mMobiles).child(android_id).setValue(map);


    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (!mScanner.isScanning()) {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(null);
        } else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(R.layout.actionbar_progress_indeterminate);
        }

        if (mRecyclerAdapter != null && mRecyclerAdapter.getItemCount() > 0) {
        } else {
      }

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                startScanPrepare();
                break;
            case R.id.menu_stop:
                mScanner.scanLeDevice(-1, false);
                invalidateOptionsMenu();
                break;
            case R.id.menu_about:
                break;
            case R.id.menu_share:
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScanner.scanLeDevice(-1, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mBluetoothUtils.isBluetoothOn()) {
            mTvBluetoothStatus.setText(R.string.on);

        } else {
            mTvBluetoothStatus.setText(R.string.off);
        }

        if (mBluetoothUtils.isBluetoothLeSupported()) {
           // mTvBluetoothLeStatus.setText(R.string.supported);
        } else {
           // mTvBluetoothLeStatus.setText(R.string.not_supported);
        }

        invalidateOptionsMenu();
    }


    private void startScanPrepare()
    {
        //
        // The COARSE_LOCATION permission is only needed after API 23 to do a BTLE scan
        //
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, new PermissionsResultAction() {

                        @Override
                        public void onGranted() {
                            startScan();
                        }

                        @Override
                        public void onDenied(String permission) {
                            Toast.makeText(MainActivity.this,
                                    R.string.permission_not_granted_coarse_location,
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
        } else {
            startScan();
        }
    }

    private void startScan() {
        final boolean isBluetoothOn = mBluetoothUtils.isBluetoothOn();
        final boolean isBluetoothLePresent = mBluetoothUtils.isBluetoothLeSupported();
        mDeviceStore.clear();
        updateItemCount(0);

        mRecyclerAdapter = new DeviceRecyclerAdapter(mCore);
        mList.setAdapter(mRecyclerAdapter);

        mBluetoothUtils.askUserToEnableBluetoothIfNeeded();
        if (isBluetoothOn && isBluetoothLePresent)
        {
            mScanner.scanLeDevice(-1, true);
            invalidateOptionsMenu();
        }
    }

    private void updateItemCount(final int count) {
        mTvItemCount.setText(
                getString(
                        R.string.formatter_item_count,
                        String.valueOf(count)));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }



    }



