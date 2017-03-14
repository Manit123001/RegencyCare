package com.example.mcnewz.regencycare.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by MCNEWZ on 24-Jan-17.
 */

public class ItemDao implements Parcelable {

    @SerializedName("id") private int id;
    @SerializedName("subject") private String subject;
    @SerializedName("detail") private String detail;
    @SerializedName("lat") private String lat;
    @SerializedName("lng") private String lng;
    @SerializedName("photo") private String photo;
    @SerializedName("vedio") private String vedio;
    @SerializedName("create_date") private Date create_date;
    @SerializedName("time_submit") private String time_submit;
    @SerializedName("time_incident") private String time_incident;
    @SerializedName("members") private int members;
    @SerializedName("type") private int type;
    @SerializedName("statusAccept") private int statusAccept;
    @SerializedName("member_F") private String memberFrist;
    @SerializedName("member_L") private String memberLast;
    @SerializedName("members_token") private String memberToken;
    @SerializedName("ac_dep_status") private int departStatus;
    @SerializedName("ac_dep_id") private int departId;
    @SerializedName("member_tel") private String memberTel;

    public String getMemberTel() {
        return memberTel;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }

    public int getDepartStatus() {
        return departStatus;
    }

    public void setDepartStatus(int departStatus) {
        this.departStatus = departStatus;
    }

    public int getDepartId() {
        return departId;
    }

    public void setDepartId(int departId) {
        this.departId = departId;
    }

    public String getMemberFrist() {
        return memberFrist;
    }

    public void setMemberFrist(String memberFrist) {
        this.memberFrist = memberFrist;
    }

    public String getMemberLast() {
        return memberLast;
    }

    public void setMemberLast(String memberLast) {
        this.memberLast = memberLast;
    }

    public int getStatusAccept() {
        return statusAccept;
    }

    public void setStatusAccept(int statusAccept) {
        this.statusAccept = statusAccept;
    }

    public String getMemberToken() {
        return memberToken;
    }

    public void setMemberToken(String memberToken) {
        this.memberToken = memberToken;
    }

    protected ItemDao(Parcel in) {
        id = in.readInt();
        subject = in.readString();
        detail = in.readString();
        lat = in.readString();
        lng = in.readString();
        photo = in.readString();
        vedio = in.readString();
        create_date = new Date(in.readLong());
        members = in.readInt();
        type = in.readInt();
        statusAccept = in.readInt();
        memberFrist = in.readString();
        memberLast = in.readString();
        memberToken = in.readString();
        departId = in.readInt();
        departStatus = in.readInt();
        memberTel = in.readString();

    }

    public static final Creator<ItemDao> CREATOR = new Creator<ItemDao>() {
        @Override
        public ItemDao createFromParcel(Parcel in) {
            return new ItemDao(in);
        }

        @Override
        public ItemDao[] newArray(int size) {
            return new ItemDao[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getVedio() {
        return vedio;
    }

    public void setVedio(String vedio) {
        this.vedio = vedio;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getTime_submit() {
        return time_submit;
    }

    public void setTime_submit(String time_submit) {
        this.time_submit = time_submit;
    }

    public String getTime_incident() {
        return time_incident;
    }

    public void setTime_incident(String time_incident) {
        this.time_incident = time_incident;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(subject);
        dest.writeString(detail);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeString(photo);
        dest.writeString(vedio);
        dest.writeLong(create_date.getTime());
        dest.writeInt(members);
        dest.writeInt(type);
        dest.writeInt(statusAccept);
        dest.writeString(memberFrist);
        dest.writeString(memberLast);
        dest.writeString(memberToken);
        dest.writeInt(departId);
        dest.writeInt(departStatus);
        dest.writeString(memberTel);


    }
}
