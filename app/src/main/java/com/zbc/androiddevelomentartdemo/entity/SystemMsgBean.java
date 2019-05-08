package com.zbc.androiddevelomentartdemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by benchengzhou on 2019/4/13  10:29 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 系统消息类
 * 类    名： SystemMsgBean
 * 备    注：
 */

public class SystemMsgBean implements Parcelable {
    private String msgId;
    private String msgBody;
    private long time;
    private int number;

    public SystemMsgBean(String msgId, String msgBody, long time, int number) {
        this.msgId = msgId;
        this.msgBody = msgBody;
        this.time = time;
        this.number = number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msgId);
        dest.writeString(msgBody);
        dest.writeLong(time);
        dest.writeInt(number);
    }

    public static final Creator<SystemMsgBean> CREATOR = new Creator<SystemMsgBean>() {
        @Override
        public SystemMsgBean createFromParcel(Parcel in) {
            return new SystemMsgBean(in);
        }

        @Override
        public SystemMsgBean[] newArray(int size) {
            return new SystemMsgBean[size];
        }
    };


    public SystemMsgBean(Parcel source) {
        this.msgId = source.readString();
        this.msgBody = source.readString();
        this.time = source.readLong();
        this.number = source.readInt();
    }

    @Override
    public String toString() {
        return "SystemMsgBean{" +
                "msgId='" + msgId + '\'' +
                ", msgBody='" + msgBody + '\'' +
                ", time=" + time +
                ", number=" + number +
                '}';
    }
}
