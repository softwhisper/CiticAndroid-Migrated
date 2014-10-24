package curso.and07;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class Quake implements Parcelable {
  private Date date;
  private String details;
  private Location location;
  private double magnitude;
  private String link;


  	
  public Quake(Date _d, String _det, Location _loc, double _mag, String _link) {
    date = _d;
    details = _det;
    location = _loc;
    magnitude = _mag;
    link = _link;
  }

  public Quake(Parcel in) {
	  this.date = (Date)in.readSerializable();  
	  this.details = in.readString();
	  this.location = Location.CREATOR.createFromParcel(in);
	  this.magnitude = in.readDouble();
	  this.link = in.readString();
  }  
  
  @Override
  public String toString() {
    SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");
    String dateString = sdf.format(date);
    return dateString + ": " + magnitude + " " + details;
  }
  
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeSerializable(date);
		out.writeString(details);
		location.writeToParcel(out, flags);
		out.writeString(link);
	}
	
	public Date getDate() { return date; }
	public String getDetails() { return details; }
	public Location getLocation() { return location; }
	public double getMagnitude() { return magnitude; }
	public String getLink() { return link; }	
}