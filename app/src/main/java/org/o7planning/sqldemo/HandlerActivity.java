package org.o7planning.sqldemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class HandlerActivity extends SQLiteOpenHelper {
    private static final int db_ver = 1;
    private static final String db_name = "menuDb";
    private static final String menu = "menu";
    private static final String Id = "Id";
    private static final String img = "img";
    private static final String name = "name";
    private static final String info = "info";
    private static final String price = "price";
    private static final String content = "content";

    public HandlerActivity(Context context) {
        super(context, db_name, null, db_ver);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_menu_table = "create table " + menu + "(" + Id + " integer primary key,"
                + img + " int," + name + " text," + info + " text," + price + " int," + content + " text" + ")";
        db.execSQL(create_menu_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("drop table if exists " + menu);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new dish
    void addDish(Menu dish) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(img, dish.getImg());
        values.put(name, dish.getName());
        values.put(info, dish.getInfo());
        values.put(price, dish.getPrice());
        values.put(content, dish.getContent());

        db.insert(menu, null, values);
        db.close();
    }

    public Menu getDish(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(menu, new String[] { Id, img, name, info, price, content },
                        Id + "=?", new String[] { String.valueOf(id) },
                        null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Menu dish = new Menu(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getInt(4),
                            cursor.getString(5));
        // return dish
        return dish;
    }

    // Getting the entire menu
    public List<Menu> getMenu() {
        List<Menu> dishList = new ArrayList<Menu>();
        // Select All Query
        String selectQuery = "select * from " + menu;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Menu dish = new Menu();
                dish.setId(Integer.parseInt(cursor.getString(0)));
                dish.setImg(cursor.getString(1));
                dish.setName(cursor.getString(2));
                dish.setInfo(cursor.getString(3));
                dish.setPrice(cursor.getInt(4));
                dish.setContent(cursor.getString(5));
                dishList.add(dish);
            } while (cursor.moveToNext());
        }
        return dishList;
    }

    public int updateDish(Menu dish) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(img, dish.getImg());
        values.put(name, dish.getName());
        values.put(info, dish.getInfo());
        values.put(price, dish.getPrice());
        values.put(content, dish.getPrice());

        return db.update(menu, values, Id + " = ?", new String[] { String.valueOf(dish.getId()) });
    }

    public void deleteDish(Menu dish) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(menu, Id + " = ?", new String[] { String.valueOf(dish.getId()) });
        db.close();
    }

    // Deleting all countries
    public void deleteMenu() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(menu,null,null);
        db.close();
    }

    // Getting countries Count
    public int getDishCount() {
        String countQuery = "select  * from " + menu;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}
