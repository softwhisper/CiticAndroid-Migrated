package and25.completo.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import and25.completo.bbdd.ActiveRecord;
import android.os.Parcel;
import android.os.Parcelable;

@DatabaseTable
public class Student extends ActiveRecord implements Parcelable {
	
	public final String TAG = "Student";
	
	@DatabaseField(id = true)
	private int id;
	
	@DatabaseField
	private String name;
	
	@DatabaseField
	private String lastname;
	
	@DatabaseField
	private String email;
	
	@DatabaseField
	private String city;
	
	public Student() { }
	
	public String getFullname() {
		return name + " " + lastname;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
    public Student(Parcel in) {
        name = in.readString();
        lastname = in.readString();
        email = in.readString();
        city = in.readString();
    }	
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(lastname);
		dest.writeString(email);
		dest.writeString(city);		
	}
		
	
}
