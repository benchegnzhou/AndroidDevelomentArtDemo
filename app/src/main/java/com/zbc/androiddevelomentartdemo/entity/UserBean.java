package com.zbc.androiddevelomentartdemo.entity;

import java.io.Serializable;

/**
 * Created by benchengzhou on 2019/4/12  10:58 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 定义用户实体类
 * 类    名： UserBean
 * 备    注：
 */
public class UserBean implements Serializable {
    private static final long serialVersionUID = 2226350951019985L;
    private String userName;
    private int age;
    private String address;
    private HomeTown homeTown;

    public UserBean(String userName, int age, String address, HomeTown homeTown) {
        this.userName = userName;
        this.age = age;
        this.address = address;
        this.homeTown = homeTown;
    }

    public static class HomeTown implements Serializable {
        private static final long serialVersionUID = 22263509510199845L;
        private String townName;
        private String x;
        private String y;


        public HomeTown(String townName, String x, String y) {
            this.townName = townName;
            this.x = x;
            this.y = y;

        }

        public String getTownName() {
            return townName;
        }

        public void setTownName(String townName) {
            this.townName = townName;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "HomeTown{" +
                    "townName='" + townName + '\'' +
                    ", x='" + x + '\'' +
                    ", y='" + y + '\'' +
                    '}';
        }
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public HomeTown getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(HomeTown homeTown) {
        this.homeTown = homeTown;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", homeTown=" + homeTown.toString() +
                '}';
    }
}
