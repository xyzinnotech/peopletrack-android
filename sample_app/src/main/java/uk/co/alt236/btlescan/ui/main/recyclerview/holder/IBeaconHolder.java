package uk.co.alt236.btlescan.ui.main.recyclerview.holder;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder;
import uk.co.alt236.btlescan.ui.main.recyclerview.model.IBeaconItem;

public class IBeaconHolder extends BaseViewHolder<IBeaconItem> implements CommonDeviceHolder {

    private final TextView ibeaconUUID;
    private final TextView ibeaconMajor;
    private final TextView ibeaconMinor;
    private final TextView ibeaconTxPower;
    private final TextView ibeaconDistance;
    private final TextView ibeaconDistanceDescriptor;
    private final TextView deviceName;
    private final TextView deviceAddress;
    private final TextView deviceRssi;
    private final TextView deviceLastUpdated;
    private final TextView distance;
    private TextView personName;

    private final ImageView personDetailsAddButton;
  //  private final ImageView personDetailsDeleteButton;

    public IBeaconHolder(View itemView) {
        super(itemView);

        this.deviceAddress = (TextView) itemView.findViewById(R.id.device_address);
        this.deviceName = (TextView) itemView.findViewById(R.id.device_name);
        this.deviceRssi = (TextView) itemView.findViewById(R.id.device_rssi);
        this.deviceLastUpdated = (TextView) itemView.findViewById(R.id.device_last_update);
        this.distance = (TextView) itemView.findViewById(R.id.distance_main);
        this.ibeaconMajor = (TextView) itemView.findViewById(R.id.ibeacon_major);
        this.ibeaconMinor = (TextView) itemView.findViewById(R.id.ibeacon_minor);
        this.ibeaconDistance = (TextView) itemView.findViewById(R.id.ibeacon_distance);
        this.ibeaconUUID = (TextView) itemView.findViewById(R.id.ibeacon_uuid);
        this.ibeaconTxPower = (TextView) itemView.findViewById(R.id.ibeacon_tx_power);
        this.ibeaconDistanceDescriptor = (TextView) itemView.findViewById(R.id.ibeacon_distance_descriptor);
        this.personDetailsAddButton=(ImageView)itemView.findViewById(R.id.profile_image_add);
       // this.personDetailsDeleteButton=(ImageView)itemView.findViewById(R.id.profile_image_delete);
        this.personName=(TextView) itemView.findViewById(R.id.person_name);
    }

    @Override
    public TextView getDeviceName() {
        return deviceName;
    }

    @Override
    public TextView getDeviceAddress() {
        return deviceAddress;
    }

    @Override
    public TextView getDeviceRssi() {
        return deviceRssi;
    }

    @Override
    public TextView getdistance() {
        return distance;
    }

    @Override
    public TextView getDeviceLastUpdated() {
        return deviceLastUpdated;
    }

    public TextView getIbeaconUUID() {
        return ibeaconUUID;
    }

    public TextView getIbeaconMajor() {
        return ibeaconMajor;
    }

    public TextView getIbeaconMinor() {
        return ibeaconMinor;
    }

    public TextView getIbeaconTxPower() {
        return ibeaconTxPower;
    }

    public TextView getIbeaconDistance() {
        return ibeaconDistance;
    }

    public TextView getIbeaconDistanceDescriptor() {
        return ibeaconDistanceDescriptor;
    }

    public ImageView getPersonDetailsAddButton()
    {
        return personDetailsAddButton;
    }
   /* public ImageView getPersonDetailsDeleteButton()
    {
        return personDetailsDeleteButton;
    }*/

    public TextView getPersonName() {
        return personName;
    }

    public void setPersonName(TextView personName) {
        this.personName = personName;
    }
}
