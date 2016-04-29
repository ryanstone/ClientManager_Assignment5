package com.stoneryan.android.clientmanager;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Ryan on 4/24/2016.
 */


//TODO: Use date/time objects to store data instead of strings.
//TODO: Remove hasPaid attribute.
public class Session {

    private Long mSessionId;
    private Long mCustomerId;
    private String mDate;
    private String mTime;
    private String mSignatureAddress;
    private String mHasPaid;


    public String printSession() {
        String mSession;
        mSession = "Date: " + mDate + "\n" +
                    "Time: " + mTime + "\n";
        return mSession;
    }

    public String toString() {
        return mDate + ", " + mTime;
    }

    public Long getSessionId() {
        return mSessionId;
    }

    public Long getCustomerId() {
        return mCustomerId;
    }

    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }

    public String getSignatureAddress() {
        return mSignatureAddress;
    }

    public String getHasPaid() {
        return mHasPaid;
    }

    public void setSessionId(Long SessionId){
        mSessionId = SessionId;
    }

    public void setCustomerId(Long CustomerId){
        mCustomerId = CustomerId;
    }

    public void setDate(String date){
        mDate = date;
    }

    public void setTime(String time){
        mTime = time;
    }

    public void setSignatureAddress(String signatureAddress){
        mSignatureAddress = signatureAddress;
    }

    public void setHasPaid(String hasPaid){
        mHasPaid = hasPaid;
    }

}
