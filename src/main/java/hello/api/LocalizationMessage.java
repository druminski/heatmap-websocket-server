package hello.api;

public class LocalizationMessage {
    private String name;
    private String province;
    private double lat;
    private double lng;
    private double count;

    public LocalizationMessage(String name, String province, double lat, double lng, double count) {
        this.name = name;
        this.province = province;
        this.lat = lat;
        this.lng = lng;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public String getProvince() {
        return province;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public double getCount() {
        return count;
    }
}
