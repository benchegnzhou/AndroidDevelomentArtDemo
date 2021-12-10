package com.example.custom_round_view.service;

import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * 编写一个定位的Service。
 * 代表服务端
 */
public class LocationManagerService extends Binder {
    private static final String TAG = "LocationService";

    /**
     * @param code  用于标识客户端期望调用服务端的哪个函数
     * @param data  客户端传递的参数
     * @param reply 返回给客户端的数据
     * @param flags 执行IPC的模式，分为两种一种是双向的，用常量0表示，其含义是服务端执行指定服务后返回一定的数据，另一种是单向的，用常量1表示，其含义不返回任何数据
     * @return
     * @throws RemoteException
     */
    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {


        switch (code) {
            case 101:
                //校验与客户端token data.writeInterfaceToken("LocationManagerService");相对应。
                data.enforceInterface(TAG);

                double lat = data.readDouble();
                double lng = data.readDouble();

                setLocation(lat, lng);
                reply.writeString("Successful");
                break;
            default:
                break;
        }
        return super.onTransact(code, data, reply, flags);
    }

    public void getMyLocation() {

    }

    public void setLocation(double lat, double lng) {

    }
}
