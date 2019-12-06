package com.bilgeadam.mobilefoodapp.data;

import com.bilgeadam.mobilefoodapp.R;
import java.util.ArrayList;
import java.util.List;

public class DataModel {

    private int ImageID;

    private String baslik;

    private String url;

    private String price;

    private String aciklama;

    private String imageItemId;

    public DataModel(int imageID, String baslik, String url,String price,String aciklama, String imageItemId){

        setImageID(imageID);
        setBaslik(baslik);
        setUrl(url);
        setPrice(price);
        setAciklama(aciklama);
        setimageItemId(imageItemId);


    }

    public int getImageID() {
        return ImageID;
    }

    public String getBaslik() {
        return baslik;
    }

    public String getUrl() {
        return url;
    }

    public String getPrice() {
        return price;
    }


    public String getAciklama() {
        return aciklama;
    }

    public String getimageItemId() {
        return imageItemId;
    }




    public void setImageID(int imageID) {
        ImageID = imageID;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public void setimageItemId(String imageItemId) {
        this.imageItemId = imageItemId;
    }





    public static List<DataModel> getDataList(){

        List<DataModel> itemList=new ArrayList<>();

        int[] imageIDs=new int[]{
                R.drawable.no_image, R.drawable.no_image, R.drawable.no_image
        };

        String[] imageBasliklari=new String[]{
                "Adana Ayran ", "Urfa Cola", "Ezogelin Adana Cola"
        };

        String[] imagePrice=new String[]{
                "22", "22", "24"
        };

        String[] imageAciklama=new String[]{
                "Kısa Süreliğine Adana Kebap + Ayran sadece 22TL",
                "Kısa Süreliğine Urfa Kebap + Cola 22TL",
                "Kısa Süreliğine Ezogelin Adana Cola Sadece 24TL"
        };

        String[] imageItemId=new String[]{
                "LuqDf-Xhqr7vSwTIfTX",
                "LuqFYPN8ZHU3BK5at2c",
                "LuqHyPNJR7VP94qO8G8"
        };



        String[] imageUrl=new String[]{
                "https://firebasestorage.googleapis.com/v0/b/online-yemek-android.appspot.com/o/adana_ayran.PNG?alt=media&token=5068985e-3f87-4c05-ba5e-d3c13c1c2356",
                "https://firebasestorage.googleapis.com/v0/b/online-yemek-android.appspot.com/o/urfa_cola.jpg?alt=media&token=09c20794-569f-4c82-8dae-52a70a9f5c98",
                "https://firebasestorage.googleapis.com/v0/b/online-yemek-android.appspot.com/o/ezo_adana_cola.jpg?alt=media&token=3677c824-7b54-4266-adfc-6a17304c24bc"
        };

        for (int i=0; i<imageIDs.length; i++){
            itemList.add(new DataModel(imageIDs[i], imageBasliklari[i],imageUrl[i],imagePrice[i],imageAciklama[i],imageItemId[i]));

        }

        return itemList;

    }

}