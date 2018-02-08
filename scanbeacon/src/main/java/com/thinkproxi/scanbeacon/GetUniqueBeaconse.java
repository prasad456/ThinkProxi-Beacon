package com.thinkproxi.scanbeacon;

import java.util.List;


public class GetUniqueBeaconse {


    /**
     * status : beacon_success
     * data : [{"UUIDMajorMinor":"B9407F30-F5F8-466E-AFF9-25556B57FE6D3407511316","uuid":"B9407F30-F5F8-466E-AFF9-25556B57FE6D","major":"34075","minor":"11316","sensor_id":"1","company_id":"0","exhibit_name":""},{"UUIDMajorMinor":"DFSAG24-25432-54523FGG4-3FGDH56476578987","uuid":"DFSAG24-25432-54523FGG4-3FGDH","major":"564765","minor":"78987","sensor_id":"2","company_id":"7","exhibit_name":null},{"UUIDMajorMinor":"B9407F30-F5F8-466E-AFF9-25556B57FE6D581876601","uuid":"B9407F30-F5F8-466E-AFF9-25556B57FE6D","major":"58187","minor":"6601","sensor_id":"3","company_id":"7","exhibit_name":null}]
     */

    private String status;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * UUIDMajorMinor : B9407F30-F5F8-466E-AFF9-25556B57FE6D3407511316
         * uuid : B9407F30-F5F8-466E-AFF9-25556B57FE6D
         * major : 34075
         * minor : 11316
         * sensor_id : 1
         * company_id : 0
         * exhibit_name :
         */

        private String UUIDMajorMinor;
        private String uuid;
        private String major;
        private String minor;
        private String sensor_id;
        private String company_id;
        private String exhibit_name;

        public String getUUIDMajorMinor() {
            return UUIDMajorMinor;
        }

        public void setUUIDMajorMinor(String UUIDMajorMinor) {
            this.UUIDMajorMinor = UUIDMajorMinor;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public String getMinor() {
            return minor;
        }

        public void setMinor(String minor) {
            this.minor = minor;
        }

        public String getSensor_id() {
            return sensor_id;
        }

        public void setSensor_id(String sensor_id) {
            this.sensor_id = sensor_id;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getExhibit_name() {
            return exhibit_name;
        }

        public void setExhibit_name(String exhibit_name) {
            this.exhibit_name = exhibit_name;
        }
    }
}
