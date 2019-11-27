package com.usama.facedetectionmobilevision;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;


public class MainActivity extends AppCompatActivity {

    // hi everyOne
    // first put this in gradle
    // now need an imageView and button in layout
    Button btn;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnProcess);
        imageView = findViewById(R.id.image_view);

        // create a new bitmap with a decodeResource from BitmapFactory
        final Bitmap myBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.img); // make sure you have an image in drawable
        imageView.setImageBitmap(myBitmap);

        // now create a paint object in set style stroke
        final Paint rectPain = new Paint();
        rectPain.setStrokeWidth(5);
        rectPain.setColor(Color.WHITE);
        rectPain.setStyle(Paint.Style.STROKE);


        // We need a canvas to display the bitmap
        final Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas((tempBitmap));
        canvas.drawBitmap(myBitmap, 0, 0, null);

        //now we detect button handle to detect face

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext())
                        .setTrackingEnabled(false)
                        .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                        .setMode(FaceDetector.FAST_MODE)
                        .build();

                //now check if face is operation already
                if (!faceDetector.isOperational()) {
                    Toast.makeText(MainActivity.this, "Face Detected could not be set up on your device !", Toast.LENGTH_SHORT).show();
                    return;
                }

                //next we create the frame using the default bitmap in call in face detected (object)
                Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                SparseArray<Face> sparseArray = faceDetector.detect(frame);

                // now get position left on top
                for (int i = 0; i < sparseArray.size(); i++) {
                    Face face = sparseArray.valueAt(i);
                    float x1 = face.getPosition().x;
                    float y1 = face.getPosition().y;
                    float x2 = x1 + face.getWidth();
                    float y2 = y1 + face.getHeight();

                    RectF rectf = new RectF(x1, y1, x2, y2);
                    canvas.drawRoundRect(rectf, 2, 2, rectPain);

                    // i put some notes in GitHub please check it


                }
                imageView.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));

                // now run
                // wrong
                // that is good thanks for watch 

            }
        });
    }
}
