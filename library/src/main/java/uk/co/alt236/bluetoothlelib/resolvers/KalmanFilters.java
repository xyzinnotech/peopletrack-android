package uk.co.alt236.bluetoothlelib.resolvers;

/**
 * Created by User on 15-03-2018.
 */
public class KalmanFilters implements RssiFilters {
    private double processNoise;//Process noise
    private double measurementNoise;//Measurement noise
    private double estimatedRSSI;//calculated rssi
    private double errorCovarianceRSSI;//calculated covariance
    private boolean isInitialized = false;//initialization flag

    public KalmanFilters() {
        this.processNoise =0.125;
        this.measurementNoise = 0.8;
    }

    public KalmanFilters(double processNoise, double measurementNoise) {
        this.processNoise = processNoise;
        this.measurementNoise = measurementNoise;
    }

    @Override
    public double applyFilter(double rssi) {
        double priorRSSI;
        double kalmanGain;
        double priorErrorCovarianceRSSI;
        if (!isInitialized) {
            priorRSSI = rssi;
            priorErrorCovarianceRSSI = 1;
            isInitialized = true;
        } else {
            priorRSSI = estimatedRSSI;
            priorErrorCovarianceRSSI = errorCovarianceRSSI + processNoise;
        }

        kalmanGain = priorErrorCovarianceRSSI / (priorErrorCovarianceRSSI + measurementNoise);
        estimatedRSSI = priorRSSI + (kalmanGain * (rssi - priorRSSI));
        errorCovarianceRSSI = (1 - kalmanGain) * priorErrorCovarianceRSSI;

        return estimatedRSSI;
    }
}
