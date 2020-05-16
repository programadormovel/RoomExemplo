package br.com.domtecpro.roomexemplo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Util {
    /**
     * método de conversão de um objeto byte[] (array de bytes) em Bitmap
     * @param foto
     * @return
     */
    public static Bitmap converterByteToBipmap(byte[] foto) {
        Bitmap bmp = null;
        Bitmap bitmapReduzido = null;
        byte[] x = foto;

        try {
            bmp = BitmapFactory.decodeByteArray(x, 0, x.length);

            bitmapReduzido = Bitmap.createScaledBitmap(bmp, 1080, 1000, true);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmapReduzido;
    }
}
