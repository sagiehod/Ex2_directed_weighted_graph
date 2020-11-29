package api;

public class GLocation implements geo_location {

    double x;
    double y;
    double z;
   

    public GLocation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public GLocation(geo_location location) {
        this(location.x(), location.y(), location.z());
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

	@Override
	public double distance(geo_location g) {
		// TODO Auto-generated method stub
		return 0;
	}
