package uk.co.alt236.btlescan.util;

import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice;

/**
 * Created by User on 3/13/2018.
 */

public interface GetDeviceInfo {
    public void getDeviceInfo(IBeaconDevice device);
    public void postFormValues(String name, long ts, String workType, String trim);
}
