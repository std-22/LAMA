package io.github.studio22.lama;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Operation implements Parcelable {

    private final String name;
    private final String nameOfClass;

    public Operation(String name, String nameOfClass){
        this.name = name;
        this.nameOfClass = nameOfClass;
    }

    protected Operation(Parcel in) {
        this.name = in.readString();
        this.nameOfClass = in.readString();
    }

    public static final Creator<Operation> CREATOR = new Creator<Operation>() {
        @Override
        public Operation createFromParcel(Parcel in) {
            return new Operation(in);
        }

        @Override
        public Operation[] newArray(int size) {
            return new Operation[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getNameOfClass() { return nameOfClass; }

    @NonNull
    @Override
    public String toString() {
        return "Operation{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(nameOfClass);
    }
}
