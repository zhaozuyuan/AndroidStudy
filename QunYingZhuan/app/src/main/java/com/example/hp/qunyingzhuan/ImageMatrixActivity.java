package com.example.hp.qunyingzhuan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

public class ImageMatrixActivity extends AppCompatActivity {

    private Bitmap bitmap;

    private ImageView imageView;

    private GridLayout gridLayout;

    private int mEtWidth, mEtHeight;

    private EditText[] editTexts = new EditText[20];

    private float[] mColorMatrix = new float[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_matrix);

        imageView = (ImageView)findViewById(R.id.iv_image_matrix);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.qunying_image);
        imageView.setImageBitmap(bitmap);
        gridLayout = (GridLayout)findViewById(R.id.grid_image_matrix);

        gridLayout.post(new Runnable() {
            @Override
            public void run() {
                mEtWidth = gridLayout.getWidth()/5;
                mEtHeight = gridLayout.getHeight()/4;
                addEdits();
                initMatrix();

            }
        });
    }

    public void btnChange(View view) {
        getMatrix();
        setImageMatrix();
    }

    public void btnReset(View view) {
        initMatrix();
        getMatrix();
        setImageMatrix();

    }

    private void addEdits(){
        for(int i = 0;i < 20;i++){
            EditText editText = new EditText(this);
            editTexts[i] = editText;
            gridLayout.addView(editText,mEtWidth,mEtHeight);
        }

    }

    private void initMatrix(){
        for(int i = 0;i < 20;i++){
            if(i%6 == 0){
                editTexts[i].setText(String.valueOf(1));
            }else {
                editTexts[i].setText(String.valueOf(0));
            }
        }
    }

    private void getMatrix(){
        for(int i = 0;i < 20;i++){
            mColorMatrix[i] = Float.valueOf(editTexts[i].getText().toString());
        }
    }

    private void setImageMatrix(){
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),Bitmap.Config.ARGB_8888);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(mColorMatrix);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap,0,0,paint);
        imageView.setImageBitmap(bmp);
    }
}
