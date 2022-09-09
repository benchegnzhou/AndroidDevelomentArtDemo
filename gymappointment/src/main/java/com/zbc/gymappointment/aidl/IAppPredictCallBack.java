package com.zbc.gymappointment.aidl;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/zbc/micode/MiuiHome/app/src/com/miui/apppredict/IAppPredictCallBack.aidl
 */
public interface IAppPredictCallBack extends android.os.IInterface {
    /**
     * Local-side IPC implementation stub class.
     */
    public static abstract class Stub extends android.os.Binder implements IAppPredictCallBack {
        private static final java.lang.String DESCRIPTOR = "com.zbc.gymappointment.aidl";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an com.zbc.gymappointment.aidl.IAppPredictCallBack interface,
         * generating a proxy if needed.
         */
        public static com.zbc.gymappointment.aidl.IAppPredictCallBack asInterface(android.os.IBinder binder) {
            if (binder == null) {
                return null;
            }
            android.os.IInterface iin = binder.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof com.zbc.gymappointment.aidl.IAppPredictCallBack)) {
                return (com.zbc.gymappointment.aidl.IAppPredictCallBack) iin;
            }
            return new com.zbc.gymappointment.aidl.IAppPredictCallBack.Stub.Proxy(binder);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            java.lang.String descriptor = DESCRIPTOR;
            switch (code) {
                case INTERFACE_TRANSACTION:
                    reply.writeString(descriptor);
                    return true;
                case TRANSACTION_loadAppList:
                    data.enforceInterface(descriptor);
                    java.lang.String json = data.readString();
                    loadAppList(json);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }

        }


        private static class Proxy implements com.zbc.gymappointment.aidl.IAppPredictCallBack {
            private android.os.IBinder mRemoteBinder;

            Proxy(android.os.IBinder remoteBinder) {
                this.mRemoteBinder = remoteBinder;
            }

            @Override
            public IBinder asBinder() {
                return mRemoteBinder;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public void loadAppList(String json) throws RemoteException {
                android.os.Parcel _date = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    mRemoteBinder.transact(Stub.TRANSACTION_loadAppList, _date, _reply, 0);
                } finally {
                    _date.recycle();
                    _reply.recycle();
                }
            }
        }

        static final int TRANSACTION_loadAppList = (IBinder.FIRST_CALL_TRANSACTION + 0);
    }

    public void loadAppList(java.lang.String json) throws android.os.RemoteException;
}
