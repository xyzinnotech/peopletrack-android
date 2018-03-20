package uk.co.alt236.btlescan.ui.main.recyclerview.holder;

import android.view.View;
import android.widget.TextView;

import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder;
import uk.co.alt236.btlescan.ui.main.recyclerview.model.LeDeviceItem;

public class LeDeviceHolder extends BaseViewHolder<LeDeviceItem> implements CommonDeviceHolder {

    private final TextView deviceName;
    private final TextView deviceAddress;
    private final TextView deviceRssi;
    private final TextView deviceLastUpdated;
    private final TextView distance;

    public LeDeviceHolder(View itemView) {
        super(itemView);

        this.deviceAddress = (TextView) itemView.findViewById(R.id.device_address);
        this.deviceName = (TextView) itemView.findViewById(R.id.device_name);
        this.deviceRssi = (TextView) itemView.findViewById(R.id.device_rssi);
        this.deviceLastUpdated = (TextView) itemView.findViewById(R.id.device_last_update);
        this.distance = (TextView) itemView.findViewById(R.id.distance_main);
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
    public TextView getDeviceLastUpdated() {
        return deviceLastUpdated;
    }

    @Override
    public TextView getdistance() {
        return distance;
    }

}
