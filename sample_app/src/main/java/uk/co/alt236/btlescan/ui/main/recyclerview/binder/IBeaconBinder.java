package uk.co.alt236.btlescan.ui.main.recyclerview.binder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.IntentService;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.ui.common.Navigation;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewBinder;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;
import uk.co.alt236.btlescan.ui.main.pojo.GettingDetails;
import uk.co.alt236.btlescan.ui.main.recyclerview.holder.IBeaconHolder;
import uk.co.alt236.btlescan.ui.main.recyclerview.model.IBeaconItem;
import uk.co.alt236.btlescan.util.Constants;
import uk.co.alt236.btlescan.util.ConstantsRefernece;
import uk.co.alt236.btlescan.util.GetDeviceInfo;

public class IBeaconBinder extends BaseViewBinder<IBeaconItem> {

    private final Navigation navigation;
    private boolean status;
  //  private FirebaseDatabase firebaseDatabase;
    IBeaconDevice device;
    private ProgressDialog progressDialog;
    List<String> details=new ArrayList<>();
    String checking="null";
    String nameValue;
     IBeaconHolder actualHolder;
     GPSTracker gps;
     double latitude;
     double longitude;
     BackgroundFirbaseService backgroundFirbaseService;
     Intent firebaseService;
     Handler handler = new Handler();
    GetDeviceInfo getDeviceInfo;
    // private DatabaseReference firebaseDatabase,databaseReference;
    //private FirebaseDatabase mFirebaseDataBase;
    static DatabaseReference zonesRef,firebaseDatabase,databaseReference;
    private  String checkingValue=null;
    //   DatabaseReference zone1Ref = zonesRef.child("Beacon Details");
    DatabaseReference personReffernce,gettingAssetref;
    private  AlertDialog.Builder builder;
    private  AlertDialog alertDialog;
    public IBeaconBinder(Context context, Navigation navigation,GetDeviceInfo getDeviceInfo )
    {
        super(context);
        this.navigation = navigation;
        this.getDeviceInfo = getDeviceInfo;
        gps = new GPSTracker(context);
        gps.getDeviceCordinates();

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void bind(BaseViewHolder<IBeaconItem> holder, IBeaconItem item) {

        actualHolder = (IBeaconHolder) holder;
        device = item.getDevice();
        getDeviceInfo.getDeviceInfo(device);
        final String accuracy = Constants.DOUBLE_TWO_DIGIT_ACCURACY.format(device.getAccuracy());
        actualHolder.getIbeaconMajor().setText(String.valueOf(device.getMajor()));
        actualHolder.getIbeaconMinor().setText(String.valueOf(device.getMinor()));
        actualHolder.getIbeaconTxPower().setText(String.valueOf(device.getCalibratedTxPower()));
        actualHolder.getIbeaconUUID().setText(device.getUUID());
        actualHolder.getIbeaconDistance().setText(getContext().getString(R.string.formatter_meters, accuracy));
        actualHolder.getIbeaconDistanceDescriptor().setText(device.getDistanceDescriptor().toString());
        personDetails();
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }

        CommonBinding.bind(getContext(), actualHolder, device);

        actualHolder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigation.openDetailsActivity(device);
            }
        });



       /* actualHolder.getPersonDetailsDeleteButton().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                personReffernce.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        HashMap<String,String> map=new HashMap<String, String>();
                        map.put("name","null");
                        map.put("ts","null");
                        map.put("workingType","null");
                        personReffernce.setValue(map);
                        actualHolder.getPersonDetailsAddButton().setBackgroundResource(R.drawable.add);
                        actualHolder.getPersonDetailsAddButton().setEnabled(true);
                        actualHolder.getPersonDetailsDeleteButton().setEnabled(false);
                        actualHolder.getPersonDetailsDeleteButton().setVisibility(View.INVISIBLE);
                        actualHolder.getPersonName().setVisibility(View.INVISIBLE);
                        CommonBinding.bind(getContext(), actualHolder, device);
                        actualHolder.isRecyclable();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });*/


        actualHolder.getPersonDetailsAddButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getContext());
                View view = View.inflate(getContext(), R.layout.person_details_layout, null);
                builder.setView(view);
                final EditText nameText = (EditText) view.findViewById(R.id.profile_upload_name);
                //     final EditText emailText=(EditText)view.findViewById(R.id.profile_upload_email);

                //  final EditText mobileNumberText=(EditText)view.findViewById(R.id.profile_upload_phone);
                final EditText workingTypeText = (EditText) view.findViewById(R.id.profile_upload_working_type);

                TextView submit = (TextView) view.findViewById(R.id.profile_submit);

                alertDialog = builder.create();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                        long emailPattern = System.currentTimeMillis() / 1000;
                        String name = nameText.getText().toString().trim();
                        String email = String.valueOf(emailPattern);
                        //    String mobile=mobileNumberText.getText().toString().trim();
                        String type = workingTypeText.getText().toString().trim();

                        if (!name.contentEquals("")) {
                            if (email.length() != 0) {
                                if (!type.contentEquals("")) {

                                    getDeviceInfo.postFormValues(name, emailPattern, type, device.getAddress());
                                   /* DatabaseReference personName=gettingAssetref.child("name");
                                    personName.setValue(name);
                                    DatabaseReference email1 = gettingAssetref.child("ts");
                                    email1.setValue(email);
                                    DatabaseReference type1 = gettingAssetref.child("workingType");
                                    type1.setValue(type);*/
                                    alertDialog.dismiss();
                                    //*//**//*
                                   //**//* actualHolder.getPersonDetailsDeleteButton().setBackgroundResource(R.drawable.delete);
             //                       actualHolder.getPersonDetailsDeleteButton().setVisibility(View.VISIBLE);
           //                         actualHolder.getPersonDetailsDeleteButton().setEnabled(true);
                             //       actualHolder.getPersonDetailsAddButton().setVisibility(View.INVISIBLE);
                              //      actualHolder.getPersonDetailsAddButton().setEnabled(false);*//**//*
                                } else {
                                    Toast.makeText(getContext(), "Please enter working type!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Please enter valid email!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Please enter name!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.show();
            }
        });
    }

    @Override
    public boolean canBind(RecyclerViewItem item)
    {
        return item instanceof IBeaconItem;
    }

    public void fireBaseDataStore(final String ibeaconUUID, final String ibeaconMajor, final String ibeaconMinor, final String ibeaconTxPower,final String ibeaconDistance, final String ibeaconDistanceDescriptor, final String deviceName, final String deviceAddress, final String deviceRssi, final String deviceLastUpdated)
    {

       // databaseReference=firebaseDatabase.child(ibeaconUUID.toString());
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

               /* if (dataSnapshot.child("personName").exists())
                {
                   // Log.d("Working"," "+"Here");
                    zone1NameRef = zonesRef.child("personName");
                    zone1NameRef.addValueEventListener(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String name=dataSnapshot.getValue(String.class);
                            if (name.contentEquals("null"))
                            {
                                Log.d("Working"," "+dataSnapshot.getValue(String.class));

                                actualHolder.getDevicesAllocationStatus().setBackgroundResource(R.drawable.add);
                            }
                            else if (!name.contentEquals("null"))
                            {
                                actualHolder.getDevicesAllocationStatus().setBackgroundResource(R.drawable.added);
                                actualHolder.getPersonName().setText(name.toString());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError)
                        {

                        }
                    });

                }*/
              /*  else
                {*/
                /*GettingDetails gettingDetails=new GettingDetails(ibeaconUUID,ibeaconMajor,ibeaconMinor,ibeaconTxPower,ibeaconDistance,ibeaconDistanceDescriptor,deviceName,deviceAddress,deviceRssi,deviceLastUpdated);
                databaseReference.setValue(gettingDetails);
*/
                /*if(!isMyServiceRunning(BackgroundFirbaseService.class)){
                    Log.d("not_","running");
                    backgroundFirbaseService = new BackgroundFirbaseService("MyService");
                    firebaseService = new Intent(getContext(),backgroundFirbaseService.getClass());
                    firebaseService.putExtra("uuid",ibeaconUUID);
                    firebaseService.putExtra("major",ibeaconMajor);
                    firebaseService.putExtra("minor",ibeaconMinor);
                    firebaseService.putExtra("txpower",ibeaconTxPower);
                    firebaseService.putExtra("distance",ibeaconDistance);
                    firebaseService.putExtra("distanceDescriptor",ibeaconDistanceDescriptor);
                    firebaseService.putExtra("name",deviceName);
                    firebaseService.putExtra("address",deviceAddress);
                    firebaseService.putExtra("rssi",deviceRssi);
                    firebaseService.putExtra("updated",deviceLastUpdated);
                    getContext().startService(firebaseService);
                    actualHolder.getDevicesAllocationStatus().setBackgroundResource(R.drawable.add);
                }else {
                    Log.d("yes_","running");
                    getContext().stopService(firebaseService);
                }*/
              //  }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }


    public static class BackgroundFirbaseService extends Service{

        private static final String TAG = "BackgroundFirbaseService";

        public BackgroundFirbaseService(){
        }

        public BackgroundFirbaseService(String name){
        }

        @Override
        public void onCreate() {
            super.onCreate();
        }

        @SuppressLint("StaticFieldLeak")
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {

            final String uuIdVal =  intent.getStringExtra("uuid");
            final String bmajorVal =  intent.getStringExtra("major");
            final String bminorVal =  intent.getStringExtra("minor");
            final String btxPowerVal =  intent.getStringExtra("txpower");
            final String bdistanceVal =  intent.getStringExtra("distance");
            final String bdiatanceSedcriptorVal =  intent.getStringExtra("distanceDescriptor");
            final String bnameVal =  intent.getStringExtra("name");
            final String baddressVal =  intent.getStringExtra("address");
            final String brssiVal =  intent.getStringExtra("rssi");
            final String bupdatedVal =  intent.getStringExtra("updated");

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    GettingDetails gettingDetails=new GettingDetails(uuIdVal,bmajorVal,bminorVal,btxPowerVal,bdistanceVal,bdiatanceSedcriptorVal,bnameVal,baddressVal,brssiVal,bupdatedVal);
                   // databaseReference.setValue(gettingDetails);
                    return null;
                }
            }.execute();

            return START_NOT_STICKY;
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        /*@Override
        protected void onHandleIntent(@Nullable Intent intent) {
            String uuIdVal =  intent.getStringExtra("uuid");
            String bmajorVal =  intent.getStringExtra("major");
            String bminorVal =  intent.getStringExtra("minor");
            String btxPowerVal =  intent.getStringExtra("txpower");
            String bdistanceVal =  intent.getStringExtra("distance");
            String bdiatanceSedcriptorVal =  intent.getStringExtra("distanceDescriptor");
            String bnameVal =  intent.getStringExtra("name");
            String baddressVal =  intent.getStringExtra("address");
            String brssiVal =  intent.getStringExtra("rssi");
            String bupdatedVal =  intent.getStringExtra("updated");

            GettingDetails gettingDetails=new GettingDetails(uuIdVal,bmajorVal,bminorVal,btxPowerVal,bdistanceVal,bdiatanceSedcriptorVal,bnameVal,baddressVal,brssiVal,bupdatedVal);
            databaseReference.setValue(gettingDetails);

        }*/
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    //Person Details Checking With FirebaseDataBase
    public void personDetails()
    {
        ConstantsRefernece.mFirebaseInstance=FirebaseDatabase.getInstance();
        gettingAssetref=ConstantsRefernece.mFirebaseRef.child(ConstantsRefernece.mAssets);

        if (!gettingAssetref.child("name").equals("null"))
        {
            //actualHolder.getPersonDetailsAddButton().setBackgroundResource(R.drawable.add);
           // actualHolder.getPersonDetailsAddButton().setEnabled(true);
           // actualHolder.getPersonDetailsAddButton().setVisibility(View.VISIBLE);
            //actualHolder.getPersonDetailsDeleteButton().setEnabled(false);
            //actualHolder.getPersonDetailsDeleteButton().setVisibility(View.INVISIBLE);*//*
            Log.d("CheckingStatus","Working1");
        }
        else
        {
                               // actualHolder.getPersonDetailsDeleteButton().setBackgroundResource(R.drawable.delete);
           // actualHolder.getPersonDetailsDeleteButton().setEnabled(true);
            //actualHolder.getPersonDetailsDeleteButton().setVisibility(View.VISIBLE);
            //actualHolder.getPersonDetailsAddButton().setEnabled(false);
          //  actualHolder.getPersonDetailsAddButton().setVisibility(View.INVISIBLE);
          //  actualHolder.getPersonName().setVisibility(View.VISIBLE);
            //actualHolder.getPersonName().setText(name.toString());
            Log.d("CheckingStatus","Working12");
        }



        /*gettingAssetref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                   DatabaseReference personName = gettingAssetref.child("name");
                    personName.addValueEventListener(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String name=dataSnapshot.getValue(String.class);
                            if (name.contentEquals("null"))
                            {
                                actualHolder.getPersonDetailsAddButton().setBackgroundResource(R.drawable.add);
                                actualHolder.getPersonDetailsAddButton().setEnabled(true);
                                actualHolder.getPersonDetailsAddButton().setVisibility(View.VISIBLE);
                       *//*         actualHolder.getPersonDetailsDeleteButton().setEnabled(false);
                                actualHolder.getPersonDetailsDeleteButton().setVisibility(View.INVISIBLE);*//*
                                Log.d("CheckingStatus","Working1");
                            }
                            else if (!name.contentEquals("null"))
                            {
                               *//* actualHolder.getPersonDetailsDeleteButton().setBackgroundResource(R.drawable.delete);
                                actualHolder.getPersonDetailsDeleteButton().setEnabled(true);
                                actualHolder.getPersonDetailsDeleteButton().setVisibility(View.VISIBLE);
                                actualHolder.getPersonDetailsAddButton().setEnabled(false);*//*
                                actualHolder.getPersonDetailsAddButton().setVisibility(View.INVISIBLE);
                                actualHolder.getPersonName().setVisibility(View.VISIBLE);
                                actualHolder.getPersonName().setText(name.toString());
                                Log.d("CheckingStatus","Working12");
                            }
                            else
                            {
                                Log.d("CheckingStatus","Working123");
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError)
                        {

                        }
                    });

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }


     private void insertPersonDetails(final String address)
    {
      //  personReffernce = firebaseDatabase.getReference("beacons-mobile-distance").child(address);

       // Log.d("mac_address_",""+address);
        /*HashMap<String,String> map=new HashMap<String, String>();
        map.put("name","null");
        map.put("ts","null");
        map.put("workingType","null");
        personReffernce.child("assets").setValue(map);*/


        personReffernce.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(device.getAddress()).child("assets").exists())
                {
                    Log.d("mac_address_1",""+address);
                }
                else
                {
                    Log.d("mac_address_",""+address);
                    HashMap<String,String> map=new HashMap<String, String>();
                    map.put("name","null");
                    map.put("ts","null");
                    map.put("workingType","null");
                    personReffernce.setValue(map);
                }
            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
