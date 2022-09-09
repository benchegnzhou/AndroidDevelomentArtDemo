package com.zbc.gymappointment.aidl;
/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/zbc/micode/MiuiHome/app/src/com/miui/apppredict/IAppPredict.aidl
 */

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface IAppPredict extends IInterface {
    /**
     * Local-side IPC implementation stub class.
     */
    public static abstract class Stub extends android.os.Binder implements com.zbc.gymappointment.aidl.IAppPredict {
        private static final java.lang.String DESCRIPTOR = "com.zbc.gymappointment.aidl.IAppPredict";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an com.miui.apppredict.IAppPredict interface,
         * generating a proxy if needed.
         */
        public static com.zbc.gymappointment.aidl.IAppPredict asInterface(android.os.IBinder binder) {
            if (binder == null) {
                return null;
            }
            android.os.IInterface iin = binder.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAppPredict)) {
                return (IAppPredict) binder;
            }
            return new com.zbc.gymappointment.aidl.IAppPredict.Stub.Proxy(binder);
        }

        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            String descriptor = DESCRIPTOR;
            switch (code) {
                case INTERFACE_TRANSACTION:
                    reply.writeString(descriptor);
                    return true;
                case TRANSACTION_getAppPredictList:
                    data.enforceInterface(descriptor);
                    java.lang.String appId = data.readString();
                    com.zbc.gymappointment.aidl.IAppPredictCallBack callback = com.zbc.gymappointment.aidl.IAppPredictCallBack.Stub.asInterface(data.readStrongBinder());
                    this.getAppPredictList(appId, callback);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_register:
                    data.enforceInterface(descriptor);
                    java.lang.String appId_ = data.readString();
                    this.register(appId_);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_unregister:
                    data.enforceInterface(descriptor);
                    java.lang.String appId__ = data.readString();
                    this.unRegister(appId__);
                    reply.writeNoException();
                    return true;

                default:
                    return super.onTransact(code, data, reply, flags);
            }

        }

        public static class Proxy implements com.zbc.gymappointment.aidl.IAppPredict {
            private static android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                this.mRemote = remote;
            }

            @Override
            public android.os.IBinder asBinder() {
                return mRemote;
            }

            private java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }


            @Override
            public void getAppPredictList(String appId, com.zbc.gymappointment.aidl.IAppPredictCallBack callback) throws RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(appId);
                    _data.writeStrongBinder(callback == null ? null : (IBinder) callback);
                    mRemote.transact(TRANSACTION_getAppPredictList, _data, _reply, 0);
                    _reply.writeNoException();
                } finally {
                    _data.recycle();
                    _reply.recycle();
                }
            }

            @Override
            public void register(String appId) throws RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(appId);
                    mRemote.transact(TRANSACTION_register, _data, _reply, 0);
                    _reply.writeNoException();
                } finally {
                    _data.recycle();
                    _reply.recycle();
                }
            }

            @Override
            public void unRegister(String appId) throws RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(appId);
                    mRemote.transact(TRANSACTION_unregister, _data, _reply, 0);
                    _reply.writeNoException();
                } finally {
                    _data.recycle();
                    _reply.recycle();
                }
            }
        }

        public static final int TRANSACTION_getAppPredictList = FIRST_CALL_TRANSACTION + 0;
        public static final int TRANSACTION_register = FIRST_CALL_TRANSACTION + 1;
        public static final int TRANSACTION_unregister = FIRST_CALL_TRANSACTION + 2;
    }

    public void getAppPredictList(java.lang.String appId, com.zbc.gymappointment.aidl.IAppPredictCallBack callback) throws android.os.RemoteException;

    public void register(java.lang.String appId) throws RemoteException;

    public void unRegister(java.lang.String appId) throws RemoteException;
}
