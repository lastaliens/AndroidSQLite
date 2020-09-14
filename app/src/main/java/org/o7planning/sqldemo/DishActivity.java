package org.o7planning.sqldemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DishActivity extends MainActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        String dishImg = getIntent().getStringExtra("img");
        String dishName = getIntent().getStringExtra("name");
        String dishInfo = getIntent().getStringExtra("info");
        String dishPrice = getIntent().getStringExtra("price");
        String dishContent = getIntent().getStringExtra("content");

        ImageView img = (ImageView) findViewById(R.id.ImgViewId);
        TextView name = (TextView) findViewById(R.id.NameViewId);
        TextView info = (TextView) findViewById(R.id.InfoViewId);
        TextView price = (TextView) findViewById(R.id.PriceViewId);
        TextView content = (TextView) findViewById(R.id.ContentViewId);

        img.setImageResource(Integer.parseInt(dishImg));
        name.setText(dishName);
        info.setText(dishInfo);
        price.setText(dishPrice);
        content.setText(dishContent);
    }
}
