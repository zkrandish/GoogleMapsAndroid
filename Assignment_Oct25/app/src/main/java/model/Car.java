package model;
import androidx.annotation.NonNull;


    public class Car {
        private int id;
        private String brand;
        private String status;

        public Car() {
        }

        public Car(int id, String brand, String status) {
            this.id = id;
            this.brand = brand;
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

//        @Override
//        public String toString() {
//            return "Car{" +
//                    "id=" + id +
//                    ", brand='" + brand + '\'' +
//                    ", status='" + status + '\'' +
//                    '}';
//        }


        @NonNull
        @Override
        public String toString() {
            return
                    "Id: " + id + "\tbrand: " + brand + "\tstatus: " + status;}

    }


