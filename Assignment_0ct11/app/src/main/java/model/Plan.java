package model;

import java.io.Serializable;

public class Plan implements Serializable {
        private int clinetNumber;
        private String provider;
        private int nbMonths;

        public Plan(int clinetNumber, String provider, int nbMonths) {
            this.clinetNumber = clinetNumber;
            this.provider = provider;
            this.nbMonths = nbMonths;
        }

        public Plan(int clinetNumber, String provider) {
            this.clinetNumber = clinetNumber;
            this.provider = provider;
            this.nbMonths = 1;
        }

        public int getClinetNumber() {
            return clinetNumber;
        }

        public void setClinetNumber(int clinetNumber) {
            this.clinetNumber = clinetNumber;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public int getNbMonths() {
            return nbMonths;
        }

        public void setNbMonths(int nbMonths) {
            this.nbMonths = nbMonths;
        }

        @Override
        public String toString() {
            return "Plan{" +
                    "clinetNumber=" + clinetNumber +
                    ", provider='" + provider + '\'' +
                    ", nbMonths=" + nbMonths +
                    "total: "+getTotal(provider,nbMonths)+
                    '}';
        }

        public static float getSubTotal(String provider, int nbMonths) throws Exception {
            float price = 0.0f;
            if (provider.equals("Videotron")) {
                 if (nbMonths == 6) {
                    price = 350.0f;
                } else if (nbMonths == 12) {
                    float normalPrice = 70.0f * 12;
                    price = normalPrice - ((normalPrice * 30) / 100);
                }else{
                    price = 70.0f * nbMonths;
                }
            } else if (provider.equals("Bell")) {
                if (nbMonths < 12) {
                    price = 60.0f * nbMonths;
                } else if (nbMonths == 12) {
                    price = 600.0f;
                }

            } else if (provider.equals("Acanac")) {
                if (nbMonths < 12) {
                    price = 45.0f * nbMonths;
                } else if (nbMonths == 12) {
                    price = 45.0f * 11;

                }
            } else {
                throw new Exception("please select the provider ");
            }
            return price;
        }
        public static float getTps(String provider,int nbMonths){
            try{
                float tps=0.06f;
                return getSubTotal(provider,nbMonths)*tps;
            }catch (Exception e){
                return -1;
            }
        }
        public static float getTvq(String provider,int nbMonths){
            try{
                float tvq=0.095f;
                return getSubTotal(provider,nbMonths)*tvq;
            }catch (Exception e){
                return -1;
            }
        }
        public static float getTotal(String provider,int nbMonths) {
            try {
                float tsp = getTps(provider, nbMonths);
                float tvq = getTvq(provider, nbMonths);
                return getSubTotal(provider, nbMonths) + tsp + tvq;

            } catch (Exception e) {
                return -1;
            }
        }
    }

