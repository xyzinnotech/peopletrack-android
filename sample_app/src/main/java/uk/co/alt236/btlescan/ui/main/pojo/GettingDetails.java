package uk.co.alt236.btlescan.ui.main.pojo;

import android.widget.TextView;

/**
 * Created by Kumar on 3/12/2018.
 */

public class GettingDetails
{
    private  String ibeaconUUID;
    private  String ibeaconMajor;
    private  String ibeaconMinor;
    private  String ibeaconTxPower;
    private  String ibeaconDistance;
    private  String ibeaconDistanceDescriptor;

    private  String deviceName;
    private  String deviceAddress;
    private  String deviceRssi;
    private  String deviceLastUpdated;
    private  String latitude;
    private  String longtitude;
    private  String deviceId;

    private String personName;
    private String personEmail;
    private String personWorkingType;
    private String personMobileNumber;

    public GettingDetails(String ibeaconUUID, String ibeaconMajor, String ibeaconMinor, String ibeaconTxPower, String ibeaconDistance, String ibeaconDistanceDescriptor,
                          String deviceName, String deviceAddress, String deviceRssi, String deviceLastUpdated, String lattitude, String longtitude, String deviceId) {
        this.ibeaconUUID = ibeaconUUID;
        this.ibeaconMajor = ibeaconMajor;
        this.ibeaconMinor = ibeaconMinor;
        this.ibeaconTxPower = ibeaconTxPower;
        this.ibeaconDistance = ibeaconDistance;
        this.ibeaconDistanceDescriptor = ibeaconDistanceDescriptor;
        this.deviceName = deviceName;
        this.deviceAddress = deviceAddress;
        this.deviceRssi = deviceRssi;
        this.deviceLastUpdated = deviceLastUpdated;
        this.latitude = lattitude;
        this.longtitude = longtitude;
        this.deviceId = deviceId;
    }

    public GettingDetails(String ibeaconUUID, String ibeaconMajor, String ibeaconMinor, String ibeaconTxPower, String ibeaconDistance, String ibeaconDistanceDescriptor, String deviceName, String deviceAddress, String deviceRssi, String deviceLastUpdated) {
        this.ibeaconUUID = ibeaconUUID;
        this.ibeaconMajor = ibeaconMajor;
        this.ibeaconMinor = ibeaconMinor;
        this.ibeaconTxPower = ibeaconTxPower;
        this.ibeaconDistance = ibeaconDistance;
        this.ibeaconDistanceDescriptor = ibeaconDistanceDescriptor;
        this.deviceName = deviceName;
        this.deviceAddress = deviceAddress;
        this.deviceRssi = deviceRssi;
        this.deviceLastUpdated = deviceLastUpdated;
    }

    public GettingDetails(String lattitude, String longtitude, String deviceId) {
        this.latitude = lattitude;
        this.longtitude = longtitude;
        this.deviceId = deviceId;
    }

    public GettingDetails(String personName, String personEmail, String personWorkingType, String personMobileNumber) {
        this.personName = personName;
        this.personEmail = personEmail;
        this.personWorkingType = personWorkingType;
        this.personMobileNumber = personMobileNumber;
    }

    public GettingDetails(String ibeaconUUID, String ibeaconMajor, String ibeaconMinor, String ibeaconTxPower, String ibeaconDistance, String ibeaconDistanceDescriptor,
                          String deviceName, String deviceAddress, String deviceRssi, String deviceLastUpdated, String lattitude, String longtitude, String deviceId,
                          String personName, String personEmail, String personWorkingType, String personMobileNumber)
    {
        this.ibeaconUUID = ibeaconUUID;
        this.ibeaconMajor = ibeaconMajor;
        this.ibeaconMinor = ibeaconMinor;
        this.ibeaconTxPower = ibeaconTxPower;
        this.ibeaconDistance = ibeaconDistance;
        this.ibeaconDistanceDescriptor = ibeaconDistanceDescriptor;
        this.deviceName = deviceName;
        this.deviceAddress = deviceAddress;
        this.deviceRssi = deviceRssi;
        this.deviceLastUpdated = deviceLastUpdated;
        this.latitude = lattitude;
        this.longtitude = longtitude;
        this.deviceId = deviceId;
        this.personName = personName;
        this.personEmail = personEmail;
        this.personWorkingType = personWorkingType;
        this.personMobileNumber = personMobileNumber;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getPersonWorkingType() {
        return personWorkingType;
    }

    public void setPersonWorkingType(String personWorkingType) {
        this.personWorkingType = personWorkingType;
    }

    public String getPersonMobileNumber() {
        return personMobileNumber;
    }

    public void setPersonMobileNumber(String personMobileNumber) {
        this.personMobileNumber = personMobileNumber;
    }

    /* public GettingDetails(String ibeaconUUID, String ibeaconMajor, String ibeaconMinor, String ibeaconTxPower, String ibeaconDistance, String ibeaconDistanceDescriptor, String deviceName, String deviceAddress, String deviceRssi, String deviceLastUpdated) {
        this.ibeaconUUID = ibeaconUUID;
        this.ibeaconMajor = ibeaconMajor;
        this.ibeaconMinor = ibeaconMinor;
        this.ibeaconTxPower = ibeaconTxPower;
        this.ibeaconDistance = ibeaconDistance;
        this.ibeaconDistanceDescriptor = ibeaconDistanceDescriptor;
        this.deviceName = deviceName;
        this.deviceAddress = deviceAddress;
        this.deviceRssi = deviceRssi;
        this.deviceLastUpdated = deviceLastUpdated;
    }

    public GettingDetails(String lattitude, String longtitude, String deviceId) {
        this.lattitude = lattitude;
        this.longtitude = longtitude;
        this.deviceId = deviceId;
    }

    public GettingDetails(String personName, String personEmail, String personWorkingType, String personMobileNumber) {
        this.personName = personName;
        this.personEmail = personEmail;
        this.personWorkingType = personWorkingType;
        this.personMobileNumber = personMobileNumber;
    }*/

    public String getIbeaconUUID() {
        return ibeaconUUID;
    }

    public String getIbeaconMajor() {
        return ibeaconMajor;
    }

    public String getIbeaconMinor() {
        return ibeaconMinor;
    }

    public String getIbeaconTxPower() {
        return ibeaconTxPower;
    }

    public String getIbeaconDistance() {
        return ibeaconDistance;
    }

    public String getIbeaconDistanceDescriptor() {
        return ibeaconDistanceDescriptor;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public String getDeviceRssi() {
        return deviceRssi;
    }

    public String getDeviceLastUpdated() {
        return deviceLastUpdated;
    }

    public String getLattitude() {
        return latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public String getDeviceId() {
        return deviceId;
    }
}
