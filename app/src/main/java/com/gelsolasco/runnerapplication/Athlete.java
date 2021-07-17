
package com.gelsolasco.runnerapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**Le istanze della classe Athlete permettono l'immagazinazione dei dati scaricati in formato JSON.
 *Tale classe implementa Parcelable che permette il passaggio di un oggetto da un activity ad un'altra.
 */

public class Athlete implements Parcelable{
    Athlete(Integer idathlete, String name, String birth, String sex, Integer cardNo,
            Object note, String nationality, String expcard, String expcert, String username,
            String password, String picture, String email) {
        this.idathlete = idathlete;
        this.name = name;
        this.birth = birth;
        this.sex = sex;
        this.cardNo = cardNo;
        this.note = note;
        this.nationality = nationality;
        this.expcard = expcard;
        this.expcert = expcert;
        this.username = username;
        this.password = password;
        this.picture = picture;
        this.email = email;
    }


    private Integer idathlete;
    private String name;
    private String birth;
    private String sex;
    private Integer cardNo;
    private Object note;
    private String nationality;
    private String expcard;
    private String expcert;
    private String username;
    private String password;
    private String picture;
    private String email;


    private Athlete(Parcel in) {
        if (in.readByte() == 0) {
            idathlete = null;
        } else {
            idathlete = in.readInt();
        }
        name = in.readString();
        birth = in.readString();
        sex = in.readString();
        if (in.readByte() == 0) {
            cardNo = null;
        } else {
            cardNo = in.readInt();
        }
        nationality = in.readString();
        expcard = in.readString();
        expcert = in.readString();
        username = in.readString();
        password = in.readString();
        picture = in.readString();
        email = in.readString();
    }

    public static final Creator<Athlete> CREATOR = new Creator<Athlete>() {
        @Override
        public Athlete createFromParcel(Parcel in) {
            return new Athlete(in);
        }

        @Override
        public Athlete[] newArray(int size) {
            return new Athlete[size];
        }
    };

    public Integer getIdathlete() {
        return idathlete;
    }

    public void setIdathlete(Integer idathlete) {
        this.idathlete = idathlete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getCardNo() {
        return cardNo;
    }

    public void setCardNo(Integer cardNo) {
        this.cardNo = cardNo;
    }

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        this.note = note;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getExpcard() {
        return expcard;
    }

    public void setExpcard(String expcard) {
        this.expcard = expcard;
    }

    public String getExpcert() {
        return expcert;
    }

    public void setExpcert(String expcert) {
        this.expcert = expcert;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (idathlete == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(idathlete);
        }
        dest.writeString(name);
        dest.writeString(birth);
        dest.writeString(sex);
        if (cardNo == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(cardNo);
        }
        dest.writeString(nationality);
        dest.writeString(expcard);
        dest.writeString(expcert);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(picture);
        dest.writeString(email);
    }

}
