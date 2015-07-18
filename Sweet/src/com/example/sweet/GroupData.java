package com.example.sweet;

import android.os.Parcel;
import android.os.Parcelable;

public class GroupData implements Parcelable{
	
		String name;
		String info;
		String location;
		String number;

		public GroupData(String groupName, String groupInfo, String groupLocation, String groupNumber) {
			name = groupName;
			info = groupInfo;
			location = groupLocation;
			number = groupNumber;
		}
		
		/**
		 * �ٸ� Parcel ��ü�� �̿��� �ʱ�ȭ�ϴ� ������
		 * 
		 * @param src
		 */
		public GroupData(Parcel src) {
			name = src.readString();
			info = src.readString();
			location = src.readString();
			number = src.readString();
			
		}
		
		/**
		 * ������ CREATOR ��ü ����
		 */
		@SuppressWarnings("unchecked")
		public static final Creator CREATOR = new Creator() {
			
			public GroupData createFromParcel(Parcel in) {
				return new GroupData(in);
			}
			
			public GroupData[] newArray(int size) {
				return new GroupData[size];
			}
			
		};
		 
		
		public int describeContents() {
			return 0;
		}

		/**
		 * �����͸� Parcel ��ü�� ����
		 */
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeString(name);
			dest.writeString(info);
			dest.writeString(location);
			dest.writeString(number);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public String getInfo() {
			return info;
		}

		public void setInfo(String info) {
			this.info = info;
		}
		
		

	}
