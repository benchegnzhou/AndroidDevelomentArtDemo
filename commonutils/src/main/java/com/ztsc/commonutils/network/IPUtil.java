package com.ztsc.commonutils.network;

import com.ztsc.commonutils.logcat.LogUtil;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * 获取IP的工具类
 * 
 * @author zbc
 */
public class IPUtil {

   /* private static final Logger log = LoggerFactory.getLogger(IPUtil.class);

    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }*/
        
        // 解析IP, 第一个为真ip
     /*   String[] ips = null;
        if (ip != null) {
            ips = ip.split(",");
            ip = ips[0];
        }
        return ip;
    }*/

    // linux取ip
    private static String getLinuxLocalIP() {
        String ip = "";

        try {
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress().toString();
            if (!ip.equals("127.0.0.1")) {
                return ip;
            }
        } catch (UnknownHostException e) {
            LogUtil.e(e.getMessage(), e);
        }

        try {
            Enumeration<?> e1 = (Enumeration<?>) NetworkInterface.getNetworkInterfaces();
            while (e1.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) e1.nextElement();
                if (!ni.getName().equals("eth0")) {
                    continue;
                } else {
                    Enumeration<?> e2 = ni.getInetAddresses();
                    while (e2.hasMoreElements()) {
                        InetAddress ia = (InetAddress) e2.nextElement();
                        if (ia instanceof Inet6Address) {
                            continue;
                        }
                        ip = ia.getHostAddress();
                    }
                    break;
                }
            }
        } catch (SocketException e) {
            LogUtil.e(e.getMessage(), e);
        }
        return ip;
    }

    private static String getWindowsLocalIP() {
        Enumeration<NetworkInterface> netInterfaces = null;
        String ip = "";
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    InetAddress ia = ips.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;
                    }

                    ip = ia.getHostAddress();
                }
            }
        } catch (Exception e) {
            LogUtil.e(e.getMessage(), e);
        }
        return ip;
    }

    public static String getLocalIP() {
        String ip = getLinuxLocalIP();
        if (ip == null || ip.equals("")) {
            ip = getWindowsLocalIP();
        }
        return ip;
    }
    
    public static boolean matchingIP(String ip) {
        boolean match = false;
        String localIp = IPUtil.getLocalIP();
        if (localIp.equals(ip)) {
            match = true;
        }
        return match;
    }

    public String getMacAddr() {
        String MacAddr = "";
        String str = "";
        try {
            NetworkInterface NIC = NetworkInterface.getByName("eth0");
            byte[] buf = NIC.getHardwareAddress();
            for (int i = 0; i < buf.length; i++) {
                str = str + byteHEX(buf[i]);
            }
            MacAddr = str.toUpperCase();
        } catch (SocketException e) {
            LogUtil.e(e.getMessage(), e);
            System.exit(-1);
        }
        return MacAddr;
    }

    /* 一个将字节转化为十六进制ASSIC码的函数 */
    private static String byteHEX(byte ib) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    public static boolean allow(String ip) {
        boolean b = false;
        long ipNum = ipToLong(ip);
        long aBegin = ipToLong("10.0.0.0");
        long aEnd = ipToLong("10.255.255.255");
        long bBegin = ipToLong("172.16.0.0");
        long bEnd = ipToLong("172.31.255.255");
        long cBegin = ipToLong("192.168.0.0");
        long cEnd = ipToLong("192.168.255.255");
        boolean isInnerIp = isInner(ipNum, aBegin, aEnd) || isInner(ipNum, bBegin, bEnd)
                || isInner(ipNum, cBegin, cEnd) || ip.equals("127.0.0.1");
        if (isInnerIp) {
            b = true;
        }

        return b;
    }

    public static boolean isInner(long userIp, long begin, long end) {
        return (userIp >= begin) && (userIp <= end);
    }

    /**
     * 将127.0.0.1形式的IP地址转换成十进制整数，这里没有进行任何错误处理
     * 通过左移位操作（<<）给每一段的数字加权，第一段的权为2的24次方，第二段的权为2的16次方，第三段的权为2的8次方，最后一段的权为1
     */
    public static long ipToLong(String ipaddress) {
        long[] ip = new long[4];
        // 先找到IP地址字符串中.的位置
        int position1 = ipaddress.indexOf(".");
        int position2 = ipaddress.indexOf(".", position1 + 1);
        int position3 = ipaddress.indexOf(".", position2 + 1);
        // 将每个.之间的字符串转换成整型
        ip[0] = Long.parseLong(ipaddress.substring(0, position1));
        ip[1] = Long.parseLong(ipaddress.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(ipaddress.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(ipaddress.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    /**
     * 将十进制整数形式转换成127.0.0.1形式的ip地址 将整数值进行右移位操作（>>>），右移24位，右移时高位补0，得到的数字即为第一段IP。
     * 通过与操作符（&）将整数值的高8位设为0，再右移16位，得到的数字即为第二段IP。
     * 通过与操作符吧整数值的高16位设为0，再右移8位，得到的数字即为第三段IP。 通过与操作符吧整数值的高24位设为0，得到的数字即为第四段IP。
     */
    public static String longToIP(long ipaddress) {
        StringBuffer sb = new StringBuffer("");
        // 直接右移24位
        sb.append(String.valueOf((ipaddress >>> 24)));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((ipaddress & 0x00FFFFFF) >>> 16));
        sb.append(".");
        // 将高16位置0，然后右移8位
        sb.append(String.valueOf((ipaddress & 0x0000FFFF) >>> 8));
        sb.append(".");
        // 将高24位置0
        sb.append(String.valueOf((ipaddress & 0x000000FF)));
        return sb.toString();
    }

}
