package org.atmarkcafe.otocon.model.parameter;

import android.graphics.Bitmap;

import org.atmarkcafe.otocon.utils.BitmapUtils;

public class UploadImageParams {
    String image_1;
    String image_2;
    String image_3;
    String image_4;
    String image_5;

    public String getImage_1() {
        return image_1;
    }

    public void setImage_1(String image_1) {
        this.image_1 = image_1;
    }

    public String getImage_2() {
        return image_2;
    }

    public void setImage_2(String image_2) {
        this.image_2 = image_2;
    }

    public String getImage_3() {
        return image_3;
    }

    public void setImage_3(String image_3) {
        this.image_3 = image_3;
    }

    public String getImage_4() {
        return image_4;
    }

    public void setImage_4(String image_4) {
        this.image_4 = image_4;
    }

    public String getImage_5() {
        return image_5;
    }

    public void setImage_5(String image_5) {
        this.image_5 = image_5;
    }

    public void setParams(int currentPos,Bitmap bitmap) {
        switch (currentPos) {
            case 1:
                setImage_1(BitmapUtils.toBase64(bitmap));
                break;
            case 2:
                //set Image 2
                setImage_2(BitmapUtils.toBase64(bitmap));
                break;
            case 3:
                //set Image 3
                setImage_3(BitmapUtils.toBase64(bitmap));
                break;
            case 4:
                //set Image 4
                setImage_4(BitmapUtils.toBase64(bitmap));
                break;
            case 5:
                //set Image 5
                setImage_5(BitmapUtils.toBase64(bitmap));
                break;
        }
    }
}
