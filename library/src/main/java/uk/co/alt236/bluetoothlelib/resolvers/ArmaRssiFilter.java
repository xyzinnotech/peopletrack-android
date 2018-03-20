package uk.co.alt236.bluetoothlelib.resolvers;

/**
 * Created by User on 20-03-2018.
 */

/**
 * Interface that can be implemented to overwrite measurement and filtering
 * of RSSI values
 */
public interface ArmaRssiFilter {

    public int addMeasurement(Integer rssi);
    public boolean noMeasurementsAvailable();
    public double calculateRssi();
    public int getMeasurementCount();

}