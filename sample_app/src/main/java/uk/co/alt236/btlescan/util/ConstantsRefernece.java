package uk.co.alt236.btlescan.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by User on 15-03-2018.
 */

public class ConstantsRefernece
{
    public static FirebaseDatabase mFirebaseInstance=FirebaseDatabase.getInstance();

    public static DatabaseReference mFirebaseRef=mFirebaseInstance.getReference("beacons-mobile-distance");
     public static String mMobiles="mobiles";
    public static String mAssets="assets";
    public static String mDist="dist";
    public static String mts="ts";
    public static String mlat="lat";
    public static String mlng="lng";
    public static String mName="name";
    public static String mWorktype="worktype";







}
