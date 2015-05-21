package hello.api;

public class LocalizationMessage {
    private String name;
    private String lat;
    private String lng;
    private double count;

    public LocalizationMessage(){}
    
    public LocalizationMessage(String name, String province, String lat, String lng, double count) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    @Override
    public String toString() {
        return "LocalizationMessage{" +
                "name='" + name + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", count=" + count +
                '}';
    }

    public double getCount() {
        return count;
    }
    
    
}
