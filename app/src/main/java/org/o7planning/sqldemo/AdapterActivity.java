package org.o7planning.sqldemo;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterActivity extends BaseAdapter {
    private Activity context;
    private PopupWindow window;

    ArrayList<Menu> menu;
    HandlerActivity handler;
    BaseAdapter ba;

    public AdapterActivity(Activity context, ArrayList<Menu> menu, HandlerActivity handler) {
        this.context = context;
        this.menu = menu;
        this.handler = handler;
    }

    public static class ViewHolder
    {
        ImageView ImgViewId;
        TextView NameViewId;
        TextView InfoViewId;
        TextView PriceViewId;
        TextView ContentViewId;

        Button editButton;
        Button deleteButton;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            row = inflater.inflate(R.layout.activity_menu, null, true);

            vh.ImgViewId = (ImageView) row.findViewById(R.id.ImgViewId);
            vh.NameViewId = (TextView) row.findViewById(R.id.NameViewId);
            vh.InfoViewId = (TextView) row.findViewById(R.id.InfoViewId);
            vh.PriceViewId = (TextView) row.findViewById(R.id.PriceViewId);
            vh.ContentViewId = (TextView) row.findViewById(R.id.ContentViewId);

            vh.editButton = (Button) row.findViewById(R.id.editBtn);
            vh.deleteButton = (Button) row.findViewById(R.id.deleteBtn);

            // store the holder with the view.
            row.setTag(vh);
        } else vh = (ViewHolder) convertView.getTag();


        vh.NameViewId.setText(menu.get(position).getName());
        vh.ImgViewId.setImageResource(Integer.parseInt(menu.get(position).getImg()));
        vh.NameViewId.setText(menu.get(position).getName());
        vh.InfoViewId.setText(menu.get(position).getInfo());
        vh.PriceViewId.setText(menu.get(position).getPrice());

        final int positionPopup = position;
        vh.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Save: ", "" + positionPopup);
                editPopup(positionPopup);
            }
        });
        vh.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("Last Index", "" + positionPopup);
                handler.deleteDish(menu.get(positionPopup));

                menu = (ArrayList) handler.getMenu();
                //Log.d("Menu size", "" + menu.size());
                notifyDataSetChanged();
            }
        });
        return row;
    }

    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
        return position;
    }

    public int getCount() {
        return menu.size();
    }

    public void editPopup(final int positionPopup)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.activity_popup, (ViewGroup) context.findViewById(R.id.popup_element));

        window = new PopupWindow(layout, 600, 800, true);
        window.showAtLocation(layout, Gravity.CENTER, 0, 0);

        //final EditText imgEdit = (EditText) layout.findViewById(R.id.editTextImg);
        final EditText nameEdit = (EditText) layout.findViewById(R.id.editTextName);
        final EditText infoEdit = (EditText) layout.findViewById(R.id.editTextInfo);
        final EditText priceEdit = (EditText) layout.findViewById(R.id.editTextPrice);
        final EditText contentEdit = (EditText) layout.findViewById(R.id.editTextContent);

        //imgEdit.setText(menu.get(positionPopup).getImg());
        nameEdit.setText(menu.get(positionPopup).getName());
        infoEdit.setText(menu.get(positionPopup).getInfo());
        priceEdit.setText(menu.get(positionPopup).getPrice());
        contentEdit.setText(menu.get(positionPopup).getContent());
        //Log.d("Name: ", "" + menu.get(positionPopup).getInfo());

        Button save = (Button) layout.findViewById(R.id.save_popup);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //String img = imgEdit.getText().toString();
            String name = nameEdit.getText().toString();
            String info = infoEdit.getText().toString();
            String price = priceEdit.getText().toString();
            String content = contentEdit.getText().toString();

            Menu dish = menu.get(positionPopup);
            //dish.setImg(Integer.valueOf(img));
            dish.setName(name);
            dish.setInfo(info);
            dish.setPrice(Integer.valueOf(price));
            dish.setContent(content);

            handler.updateDish(dish);
            menu = (ArrayList) handler.getMenu();
            notifyDataSetChanged();
            for (Menu dish_detail : menu) {
                String log = "Id: " + dish_detail.getId() + ", Name: " + dish_detail.getName()
                        + ", Info: " + dish_detail.getInfo();
                //Log.d("Name: ", log);
            }
            window.dismiss();
            }
        });
    }
}
