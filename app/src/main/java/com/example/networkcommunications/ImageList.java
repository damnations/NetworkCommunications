package com.example.networkcommunications;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class ImageList {
    public Bitmap image;

    public ImageList(Bitmap image) {this.image = image;}

    @NonNull
    @Override
    public  String toString() {
        return String.format("ModalList{image=%s}", image);
    }
}
