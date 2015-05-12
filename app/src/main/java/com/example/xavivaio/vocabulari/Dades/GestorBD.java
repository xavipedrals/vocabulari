package com.example.xavivaio.vocabulari.Dades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.xavivaio.vocabulari.Paraula;

/**
 * Created by xavivaio on 02/02/2015.
 */
public class GestorBD extends SQLiteOpenHelper {

    //Declaracion del nombre de la base de datos
    public static final int DATABASE_VERSION = 1;

    //Declaracion global de la version de la base de datos
    public static final String DATABASE_NAME = "Dades";

    //Declaracion del nombre de la tabla
    //public static final String PUNTUACIO_TABLE ="Puntuacio";
    //sentencia global de cracion de la base de datos
    //public static final String PUNTUACIO_TABLE_CREATE = "CREATE TABLE " + PUNTUACIO_TABLE + "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, punts INTEGER);";

    //public static final String PUNTUACIO_TABLE_RESET = "DELETE FROM " + PUNTUACIO_TABLE;

    public static final String IDIOMES_TABLE_NAME = "Idiomes";
    public static final String IDIOMES_COLUMN_NAME = "name";
    public static final String IDIOMES_COLUMN_ID = "id";
    public static final String IDIOMES_COLUMN_NUMTRAD = "numtrad";
    public static final String IDIOMES_COLUMN_NUMPAR = "numpar";

    public static final String IDIOMES_TABLE_CREATE = "CREATE TABLE "+ IDIOMES_TABLE_NAME +"(" +
            IDIOMES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            IDIOMES_COLUMN_NAME + " CHAR(30) UNIQUE, "+
            IDIOMES_COLUMN_NUMPAR + " INTEGER, "+
            IDIOMES_COLUMN_NUMTRAD + " INTEGER);";


    public static final String IDIOMA_COLUMN_PARAULA = "paraula";
    public static final String IDIOMA_COLUMN_ID = "id";
    //public static final String IDIOMA_COLUMN_NUMPAR = "numpar";
    public static final String IDIOMA_COLUMN_NUMTRAD = "numtrad";

    public static final String IDIOMA_TABLE_CREATE1 = "CREATE TABLE IF NOT EXISTS ";
    public static final String IDIOMA_TABLE_CREATE2 = "(id INTEGER PRIMARY KEY AUTOINCREMENT, paraula CHAR(30) UNIQUE, numTrad INTEGER);";


    public GestorBD(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private String getStrnigCreaTaulaIdioma(String idioma){
        final String IDIOMA_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "+ idioma +"(" +
                IDIOMA_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                IDIOMA_COLUMN_PARAULA + " CHAR(30) UNIQUE, "+
                IDIOMA_COLUMN_NUMTRAD + " INTEGER);";
        return IDIOMA_TABLE_CREATE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(PUNTUACIO_TABLE_CREATE);
        db.execSQL(IDIOMES_TABLE_CREATE);
    }

    public void createTableIdioma(String idioma){
        SQLiteDatabase db = this.getWritableDatabase();
        String CREA_TAULA = getStrnigCreaTaulaIdioma(idioma);
        db.execSQL(CREA_TAULA);
        Log.d("DADES", "creo la taula " + idioma);
    }

    public void insertParaula (String idioma, String paraula){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IDIOMA_COLUMN_PARAULA, paraula);
        contentValues.put(IDIOMA_COLUMN_NUMTRAD, 0);
        db.insert(idioma, null, contentValues);
        Log.d("DADES", "Inserto " + idioma + " " + paraula);
    }

    public Cursor getParaulesIdioma(String idioma){
        Log.d("DADES", "Vaig a buscar a la taula "+ idioma);
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {IDIOMA_COLUMN_PARAULA, IDIOMA_COLUMN_NUMTRAD};
        Cursor c = db.query(
                idioma,                                 // The table to query
                columns,                                // The columns to return
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                null                                    // The sort order
        );
        Cursor copia = c;
        if (copia.moveToFirst()) {
            do {
                Log.d("DADES", String.valueOf(copia.getColumnIndex("name")));
            } while (copia.moveToNext());
        }
        return c;
    }

    public void insertIdioma(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IDIOMES_COLUMN_NAME, name);
        contentValues.put(IDIOMES_COLUMN_NUMPAR, 0);
        contentValues.put(IDIOMES_COLUMN_NUMTRAD, 0);
        db.insert(IDIOMES_TABLE_NAME, null, contentValues);
    }

    public Cursor getIdiomes(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {IDIOMES_COLUMN_NAME, IDIOMES_COLUMN_NUMPAR, IDIOMA_COLUMN_NUMTRAD};
        Cursor c = db.query(
                IDIOMES_TABLE_NAME,                        // The table to query
                columns,                                // The columns to return
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                null                                    // The sort order
        );
        return c;
    }

//    public void renameIdioma(String newName, String oldName){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String UPDATE_IDIOMES = "UPDATE " + IDIOMES_TABLE + " SET name=" + newName + " WHERE name=" + oldName + ";";
//        db.execSQL(UPDATE_IDIOMES);
//    }
//
//    public void dropIdioma(String name){
//        SQLiteDatabase db = this.getWritableDatabase();
//        int id_taula = this.getIdentificadorIdioma(name);
//        if (id_taula != -1){
//            String DROP_IDIOMA = "DROP TABLE " + id_taula + ";";
//            String DELETE_IDIOMA = "DELETE FROM " + IDIOMES_TABLE + " WHERE name=" + name + ";";
//        }
//    }

//    private int getIdentificadorIdioma(String idioma){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String[] columns = {"id"};
//        String[] valuesWhere = {idioma};
//        Cursor c = db.query(
//                idioma,                                 // The table to query
//                columns,                                // The columns to return
//                "name = ?",                               // The columns for the WHERE clause
//                valuesWhere,                                 // The values for the WHERE clause
//                null,                                   // don't group the rows
//                null,                                   // don't filter by row groups
//                null                                    // The sort order
//        );
//        if (c.moveToFirst()) {
//            String id = c.getString(c.getColumnIndex("id"));
//            int aux = Integer.parseInt(id);
//            return aux;
//        }
//        return -1;
//    }



//    public void insertPuntuacio (String name, int punts){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put("name", name);
//        contentValues.put("punts", punts);
//
//        db.insert(PUNTUACIO_TABLE, null, contentValues);
//    }
//
//    private void createTaulaTraduccio (String name){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String CREA_TAULA = "CREATE TABLE " + name + "(paraula1 CHAR(30), paraula2 CHAR(30), PRIMARY KEY (paraula1, paraula2));";
//        db.execSQL(CREA_TAULA);
//    }
//
//    public void insertTraduccio (String idioma1, String idioma2, String paraula1, String paraula2){
//        SQLiteDatabase db = this.getWritableDatabase();
//        int id_taula1 = getIdentificadorIdioma(idioma1);
//        int id_taula2 = getIdentificadorIdioma(idioma2);
//
//    }



//    public void renameParaula (String idioma, String paraulaAct, String paraulaNova){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("UPDATE " + idioma + " SET paraula=" + paraulaNova + " WHERE paraula=" + paraulaAct + ";");
//    }
//
//    public void deleteParaula (String idioma, String paraula){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DELETE FROM "+ idioma +" WHERE paraula="+ paraula +";");
//    }
//
//    public void contaTuples(String nomTaula){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("SELECT COUNT(*) FROM " + nomTaula + ";");
//    }

//    public Cursor getPuntuacions() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String[] columns = {"name", "punts"};
//        Cursor c = db.query(
//                PUNTUACIO_TABLE,                        // The table to query
//                columns,                                // The columns to return
//                null,                                   // The columns for the WHERE clause
//                null,                                   // The values for the WHERE clause
//                null,                                   // don't group the rows
//                null,                                   // don't filter by row groups
//                null                                    // The sort order
//        );
//        return c;
//    }
//
//    public void resetTablePuntuacio(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL(PUNTUACIO_TABLE_RESET);
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }
}
