package uk.co.alt236.bluetoothlelib.resolvers;

/**
 * Created by User on 20-03-2018.
 */

public class ArmaFillters implements ArmaRssiFilter {

    private static double DEFAULT_ARMA_SPEED = 0.1;     //How likely is it that the RSSI value changes?
    //Note: the more unlikely, the higher can that value be
    //      also, the lower the (expected) sending frequency,
    //      the higher should that value be

    private static final String TAG = "ArmaRssiFilter";
    //initially set to min value
    private int armaMeasurement;
    private double armaSpeed = 0.1;
    private boolean isInitialized = false;

    public ArmaFillters() {
        this.armaSpeed = DEFAULT_ARMA_SPEED;
    }

    public int addMeasurement(Integer rssi) {
        //use first measurement as initialization
        if (!isInitialized) {
            armaMeasurement = rssi;
            isInitialized = true;
        };
        armaMeasurement = Double.valueOf(armaMeasurement - armaSpeed * (armaMeasurement - rssi)).intValue();
        return addMeasurement(rssi);
    }

    @Override
    public int getMeasurementCount() { return 0; }

    public boolean noMeasurementsAvailable() {
        return false;
    }

    public double calculateRssi() {
        return armaMeasurement;

    }

    public static void setDEFAULT_ARMA_SPEED(double default_arma_speed) {
        DEFAULT_ARMA_SPEED = default_arma_speed;
    }

}