package org.o7planning.sqldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Menu> menu;
    HandlerActivity handler;
    AdapterActivity adapter;
    Activity activity;

    Button SubmitBtn;
    PopupWindow window;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        handler = new HandlerActivity(this);
        listView = (ListView) findViewById(R.id.ListViewId);
        SubmitBtn = (Button) findViewById(R.id.SubmitBtn);
        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPopUp();
            }
        });
        //Log.d("MainActivity: ", "Before reading MainActivity");
        menu = (ArrayList) handler.getMenu();
        for (Menu dish : menu) {
            String log = "Id: " + dish.getId() + ", Name: " + dish.getName() + ", Info: " + dish.getInfo();
            //Log.d("Name: ", log);
        }

        AdapterActivity adapter = new AdapterActivity(this, menu, handler);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ImageView imgView = (ImageView) view.findViewById(R.id.ImgViewId);
                TextView nameView = (TextView) view.findViewById(R.id.NameViewId);
                TextView infoView = (TextView) view.findViewById(R.id.InfoViewId);
                TextView priceView = (TextView) view.findViewById(R.id.PriceViewId);
                TextView contentView = (TextView) view.findViewById(R.id.ContentViewId);

                String img = imgView.getDrawable().toString();
                String name = nameView.getText().toString();
                String info = infoView.getText().toString();
                Integer price = Integer.valueOf(priceView.getText().toString());
                String content = contentView.getText().toString();

                Intent intent = new Intent(MainActivity.this, DishActivity.class);
                intent.putExtra("img", img);
                intent.putExtra("name", name);
                intent.putExtra("info", info);
                intent.putExtra("price", price);
                intent.putExtra("content", content);

                startActivity(intent);
                Toast.makeText(getApplicationContext(), "You have selected "
                        + menu.get(position).getName() + " as your dish", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addPopUp() {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.activity_popup, (ViewGroup) activity.findViewById(R.id.popup_element));

        window = new PopupWindow(layout, 600, 670, true);
        window.showAtLocation(layout, Gravity.CENTER, 0, 0);

        //final EditText editImg = (EditText) layout.findViewById(R.id.editTextImg);
        final EditText editName = (EditText) layout.findViewById(R.id.editTextName);
        final EditText editInfo = (EditText) layout.findViewById(R.id.editTextInfo);
        final EditText editPrice = (EditText) layout.findViewById(R.id.editTextPrice);
        final EditText editContent = (EditText) layout.findViewById(R.id.editTextContent);

        Button save = (Button) layout.findViewById(R.id.save_popup);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Integer img = Integer.valueOf(editImg.getText().toString());
                String name = editName.getText().toString();
                String info = editInfo.getText().toString();
                int price = Integer.parseInt(editPrice.getText().toString());
                String content = editContent.getText().toString();

                Menu dish = new Menu(name, info, price, content);
                handler.addDish(dish);

                if(adapter == null) {
                    adapter = new AdapterActivity(activity, menu, handler);
                    listView.setAdapter(adapter);
                }
                adapter.menu = (ArrayList) handler.getMenu();
                ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
                for (Menu dish_detail : menu) {
                    String log = "Id: " + dish_detail.getId() + " ,Name: " + dish_detail.getName()
                            + " , Population: " + dish_detail.getInfo();
                    //Log.d("Name: ", log);
                }
                window.dismiss();
            }
        });
    }
}