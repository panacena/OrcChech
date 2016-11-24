package com.cstor.orc.myrecorditem.bean;

/**
 * Created by Administrator on 2016/11/24 0024.
 */
public class MyRecordItem {

    /**
     * code : 0
     * billDetail : {"addr":"北京市宣武区仙居雅苑8幢101室","bill":"6922711079066","billImg":"http://192.168.0.51:8080/Express/billimg/bill6922711079066.jpg","birthday":"1991/2/15","createDate":{"date":23,"day":3,"hours":11,"minutes":7,"month":10,"seconds":38,"time":1479870458000,"timezoneOffset":-480,"year":116},"goodImg":"http://192.168.0.51:8080/Express/goodimg/good6922711079066.jpg","id":0,"idImg":"http://192.168.0.51:8080/Express/idcardimg/idcard6922711079066.jpg","idcard":"320102199102153838","nation":"汉","recieveName":"哈哈","recievePhone":"1333658669","senderName":"张堑","senderPhone":"158958855","sex":"男","updateDate":{"date":23,"day":3,"hours":11,"minutes":7,"month":10,"seconds":38,"time":1479870458000,"timezoneOffset":-480,"year":116}}
     */

    private int code;
    /**
     * addr : 北京市宣武区仙居雅苑8幢101室
     * bill : 6922711079066
     * billImg : http://192.168.0.51:8080/Express/billimg/bill6922711079066.jpg
     * birthday : 1991/2/15
     * createDate : {"date":23,"day":3,"hours":11,"minutes":7,"month":10,"seconds":38,"time":1479870458000,"timezoneOffset":-480,"year":116}
     * goodImg : http://192.168.0.51:8080/Express/goodimg/good6922711079066.jpg
     * id : 0
     * idImg : http://192.168.0.51:8080/Express/idcardimg/idcard6922711079066.jpg
     * idcard : 320102199102153838
     * nation : 汉
     * recieveName : 哈哈
     * recievePhone : 1333658669
     * senderName : 张堑
     * senderPhone : 158958855
     * sex : 男
     * updateDate : {"date":23,"day":3,"hours":11,"minutes":7,"month":10,"seconds":38,"time":1479870458000,"timezoneOffset":-480,"year":116}
     */

    private BillDetailBean billDetail;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public BillDetailBean getBillDetail() {
        return billDetail;
    }

    public void setBillDetail(BillDetailBean billDetail) {
        this.billDetail = billDetail;
    }

    public static class BillDetailBean {
        private String addr;
        private String bill;
        private String billImg;
        private String birthday;
        /**
         * date : 23
         * day : 3
         * hours : 11
         * minutes : 7
         * month : 10
         * seconds : 38
         * time : 1479870458000
         * timezoneOffset : -480
         * year : 116
         */

        private CreateDateBean createDate;
        private String goodImg;
        private int id;
        private String idImg;
        private String idcard;
        private String nation;
        private String recieveName;
        private String recievePhone;
        private String senderName;
        private String senderPhone;
        private String sex;
        private String revieveAddr;
        /**
         * date : 23
         * day : 3
         * hours : 11
         * minutes : 7
         * month : 10
         * seconds : 38
         * time : 1479870458000
         * timezoneOffset : -480
         * year : 116
         */

        private UpdateDateBean updateDate;

        public String getRevieveAddr() {
            return revieveAddr;
        }

        public void setRevieveAddr(String revieveAddr) {
            this.revieveAddr = revieveAddr;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getBill() {
            return bill;
        }

        public void setBill(String bill) {
            this.bill = bill;
        }

        public String getBillImg() {
            return billImg;
        }

        public void setBillImg(String billImg) {
            this.billImg = billImg;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public CreateDateBean getCreateDate() {
            return createDate;
        }

        public void setCreateDate(CreateDateBean createDate) {
            this.createDate = createDate;
        }

        public String getGoodImg() {
            return goodImg;
        }

        public void setGoodImg(String goodImg) {
            this.goodImg = goodImg;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIdImg() {
            return idImg;
        }

        public void setIdImg(String idImg) {
            this.idImg = idImg;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getRecieveName() {
            return recieveName;
        }

        public void setRecieveName(String recieveName) {
            this.recieveName = recieveName;
        }

        public String getRecievePhone() {
            return recievePhone;
        }

        public void setRecievePhone(String recievePhone) {
            this.recievePhone = recievePhone;
        }

        public String getSenderName() {
            return senderName;
        }

        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }

        public String getSenderPhone() {
            return senderPhone;
        }

        public void setSenderPhone(String senderPhone) {
            this.senderPhone = senderPhone;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public UpdateDateBean getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(UpdateDateBean updateDate) {
            this.updateDate = updateDate;
        }

        public static class CreateDateBean {
            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }

        public static class UpdateDateBean {
            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }
    }
}
