package com.image.swipe.lib;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.image.swipe.beforeafterslider.BeforeAfterSlider;

public class MainActivity extends AppCompatActivity {
    private BeforeAfterSlider beforeAfterSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        beforeAfterSlider = findViewById(R.id.before_after_slider_id);

        String imgUrl1 = "https://cdn.pixabay.com/photo/2024/12/27/21/57/venice-9294935_960_720.jpg";
        String imgUrl2 = "https://cdn.pixabay.com/photo/2019/12/18/16/34/venice-4704342_1280.jpg";

        beforeAfterSlider.setBeforeImage(imgUrl1).setAfterImage(imgUrl2);
    }
}