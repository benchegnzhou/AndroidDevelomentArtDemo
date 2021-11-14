package com.ztsc.commonutils.deviceutil

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.util.Log
import java.lang.Exception
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by benchengzhou on 2021/11/14  15:35 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 获取IP
 * 类    名：
 * 备    注：
 */

class IPUtils {

    companion object {

        /**
         * 获取网络IP
         */
       /* fun getNetConnectIP(context: Context): String {
            val connectType: Int = NetWorkUtil.netWorkType(context)
            if (connectType > NetWorkUtil.TYPE_NOT_CONNECTION) {
                val ip = getLocalIpAddress()
                System.out.println("本地ip-----" + ip)
                return ip
            } else if (connectType > NetWorkUtil.TYPE_NOT_CONNECTION) {
                val wifiManager: WifiManager =
                    context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val wifiInfo: WifiInfo = wifiManager.getConnectionInfo()
                val ipAddress = wifiInfo.ipAddress
                val ip = intToIp(ipAddress)
                System.out.println("wifi_ip地址为------" + ip)
                return ip
            }
        }*/

        /**
         * 获取IP
         *
         * @param context
         * @return
         */
        fun getIP(context: Context): String {
            var ip: String = "0.0.0.0"
            val connectivityManager: ConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info: NetworkInfo = connectivityManager.activeNetworkInfo ?: return ip
            val type: Int = info.type
            if (type == ConnectivityManager.TYPE_ETHERNET) {
                ip = getEtherNetIP()
            } else if (type == ConnectivityManager.TYPE_WIFI) {
                ip = getWifiIP(context)
            }
            return ip
        }

        /**
         * 获取有线地址
         *
         * @return
         */
        fun getEtherNetIP(): String {
            try {
                val en = NetworkInterface
                    .getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val intf = en.nextElement()
                    val enumIpAddr = intf
                        .inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress = enumIpAddr.nextElement()
                        if (!inetAddress.isLoopbackAddress
                            && inetAddress is Inet4Address
                        ) {
                            return inetAddress.hostAddress.toString()
                        }
                    }
                }
            } catch (ex: SocketException) {
                Log.e("Wifi IpAddress", ex.toString())
            }
            return "0.0.0.0"
        }

        /**
         * 获取wifiIP地址
         *
         * @param context
         * @return
         */
        fun getWifiIP(context: Context): String {
            val wifi = context
                .getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiinfo = wifi.connectionInfo
            val intaddr = wifiinfo.ipAddress
            val byteaddr = byteArrayOf(
                (intaddr and 0xff).toByte(),
                (intaddr shr 8 and 0xff).toByte(), (intaddr shr 16 and 0xff).toByte(),
                (intaddr shr 24 and 0xff).toByte()
            )
            var addr: InetAddress? = null
            try {
                addr = InetAddress.getByAddress(byteaddr)
            } catch (e1: Exception) {
                e1.printStackTrace()
            }
            return addr!!.hostAddress
        }

        fun getLocalIpAddress(): String {
            var ipv4: String = ""
            try {
                var nilist: ArrayList<NetworkInterface> =
                    Collections.list(NetworkInterface.getNetworkInterfaces())
                nilist.forEach { ni ->
                    var ialist: ArrayList<InetAddress> = Collections.list(ni.getInetAddresses())
                    ialist.forEach { address ->
                        if (!address.isLoopbackAddress /*&& InetAddressUtils.isIPv4Address(ipv4 = address.hostAddress)*/) {
                            return ipv4
                        }
                    }
                }
            } catch (ex: SocketException) {
                Log.e("localip", ex.toString())
            }
            return ipv4
        }




        /**
         * 连接上一个没有外网连接的WiFi
         * 或者有线就会出现极端的情况
         *
         * @return
         */
        /*   public static final boolean ping() {
               String result = null;
               try {
                   String ip = NETIP;// ping 的地址，可以换成任何一种可靠的外网
                   Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
                   // 读取ping的内容，可以不加
                   InputStream input = p.getInputStream();
                   BufferedReader in = new BufferedReader(new InputStreamReader(input));
                   StringBuffer stringBuffer = new StringBuffer();
                   String content = "";
                   while ((content = in.readLine()) != null) {
                       stringBuffer.append(content);
                   }
                   Log.d("------ping-----", "result content : " + stringBuffer.toString());
                   // ping的状态
                   int status = p.waitFor();
                   if (status == 0) {
                       result = "success";
                       return true;
                   } else {
                       result = "failed";
                       return false;
                   }
               } catch (IOException e) {
                   result = "IOException";
                   return false;
               } catch (InterruptedException e) {
                   result = "InterruptedException";
                   return false;
               } finally {
                   Log.d("----result---", "result = " + result);
               }
           }*/


    }


}