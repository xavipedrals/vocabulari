package com.example.xavivaio.vocabulari.Dades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by xavivaio on 02/02/2015.
 */
public class GestorBD extends SQLiteOpenHelper {

    //Declaracion del nombre de la base de datos
    public static final int DATABASE_VERSION = 1;

    //Declaracion global de la version de la base de datos
    public static final String DATABASE_NAME = "Dades";

    //Declaracion del nombre de la tabla
    public static final String PUNTUACIO_TABLE ="Puntuacio";
    //sentencia global de cracion de la base de datos
    public static final String PUNTUACIO_TABLE_CREATE = "CREATE TABLE " + PUNTUACIO_TABLE + "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, punts INTEGER);";

    public static final String PUNTUACIO_TABLE_RESET = "DELETE FROM " + PUNTUACIO_TABLE;

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
    public static final String IDIOMA_COLUMN_NUMTRAD = "numtrad";


    public static final String TRADUCCIO_COLUMN_PARAULA1 = "paraula1";
    public static final String TRADUCCIO_COLUMN_PARAULA2 = "paraula2";


    public static final String TRADUCCIONS_TABLE_NAME = "traduccions";
    public static final String TRADUCCIONS_COLUMN_IDIOMA1 = "idioma1";
    public static final String TRADUCCIONS_COLUMN_IDIOMA2 = "idioma2";

    public static final String TRADUCCIONS_TABLE_CREATE = "CREATE TABLE "+ TRADUCCIONS_TABLE_NAME +"(" +
            TRADUCCIONS_COLUMN_IDIOMA1 + " CHAR(30), "+
            TRADUCCIONS_COLUMN_IDIOMA2 + " CHAR(30), "+
            "PRIMARY KEY("+TRADUCCIONS_COLUMN_IDIOMA1+","+TRADUCCIONS_COLUMN_IDIOMA2+"));";


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

    private String getStringCreaTraduccio(String idioma1, String idioma2){
        String nomTaula;
        if (idioma1.compareTo(idioma2) < 0){
            nomTaula = idioma1 + idioma2;
        } else {
            nomTaula = idioma2 + idioma1;
        }
        final String TRADUCCIO_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+ nomTaula +"(" +
                TRADUCCIO_COLUMN_PARAULA1 + " CHAR(30), "+
                TRADUCCIO_COLUMN_PARAULA2 + " CHAR(30) UNIQUE," +
                "PRIMARY KEY("+TRADUCCIO_COLUMN_PARAULA1+"));";
        return TRADUCCIO_CREATE_TABLE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PUNTUACIO_TABLE_CREATE);
        db.execSQL(IDIOMES_TABLE_CREATE);
        db.execSQL(TRADUCCIONS_TABLE_CREATE);
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
        //updateIdiomes(idioma);
    }

    public void updateIdiomes (String idioma, int numPar){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IDIOMES_COLUMN_NUMPAR, numPar);
        Log.d("DADES", "Vaig a fer un update");
        db.update(IDIOMES_TABLE_NAME, contentValues, IDIOMES_COLUMN_NAME + "=?", new String[]{idioma});
    }

    public int getNumParIdioma(String idioma){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {IDIOMES_COLUMN_NUMPAR};
        String[] valuesWhere = {idioma};
        Cursor c = db.query(
                IDIOMES_TABLE_NAME,                     // The table to query
                columns,                                // The columns to return
                IDIOMES_COLUMN_NAME + "=?",             // The columns for the WHERE clause
                valuesWhere,                            // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                null                                    // The sort order
        );
        if (c.moveToFirst()) {
            int i = c.getInt(c.getColumnIndex(GestorBD.IDIOMES_COLUMN_NUMPAR));
            return i;
        } return -1;
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

    public void createTableTraduccio (String idioma1, String idioma2){
        SQLiteDatabase db = this.getWritableDatabase();
        String CREA_TAULA_TRAD = getStringCreaTraduccio(idioma1, idioma2);
        db.execSQL(CREA_TAULA_TRAD);
    }

    public void insertTraduccioControl(String idioma1, String idioma2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (idioma1.compareTo(idioma2) < 0){
            contentValues.put(TRADUCCIONS_COLUMN_IDIOMA1, idioma1);
            contentValues.put(TRADUCCIONS_COLUMN_IDIOMA2, idioma2);
        } else {
            contentValues.put(TRADUCCIONS_COLUMN_IDIOMA1, idioma2);
            contentValues.put(TRADUCCIONS_COLUMN_IDIOMA2, idioma1);
        }
        db.insert(TRADUCCIONS_TABLE_NAME, null, contentValues);
    }

    public Cursor getTraduccioControl(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {TRADUCCIONS_COLUMN_IDIOMA1, TRADUCCIONS_COLUMN_IDIOMA2};
        Cursor c = db.query(
                TRADUCCIONS_TABLE_NAME,                           // The table to query
                columns,                                // The columns to return
                null,                           // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                null                                    // The sort order
        );
        return c;
    }


    public Cursor getAllTaulaTraduccio(String idioma1, String idioma2){
        SQLiteDatabase db = this.getReadableDatabase();
        String nomTaula;
        if (idioma1.compareTo(idioma2) < 0){
            nomTaula = idioma1+idioma2;
        } else {
            nomTaula = idioma2+idioma1;
        }
        String[] columns = {TRADUCCIO_COLUMN_PARAULA1, TRADUCCIO_COLUMN_PARAULA2};
        Cursor c = db.query(
                nomTaula,                 // The table to query
                columns,                                // The columns to return
                null,                           // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                null                                    // The sort order
        );
        return c;
    }

    public void insertTraduccio (String idioma1, String idioma2, String paraula1, String paraula2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String nomTaula;
        if (idioma1.compareTo(idioma2) < 0){
            nomTaula = idioma1 + idioma2;
            contentValues.put(TRADUCCIO_COLUMN_PARAULA1, paraula1);
            contentValues.put(TRADUCCIO_COLUMN_PARAULA2, paraula2);
        } else {
            nomTaula = idioma2 + idioma1;
            contentValues.put(TRADUCCIO_COLUMN_PARAULA1, paraula2);
            contentValues.put(TRADUCCIO_COLUMN_PARAULA2, paraula1);
        }
        db.insert(nomTaula, null, contentValues);
    }

    //TODO: Actualitzar el nombre de traduccions al insertar una traduccio

    public Cursor getTaulesTraduccio(String idioma){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {TRADUCCIONS_COLUMN_IDIOMA1, TRADUCCIONS_COLUMN_IDIOMA2};
        String columnsWhere = TRADUCCIONS_COLUMN_IDIOMA1+"=?"+" OR "+TRADUCCIONS_COLUMN_IDIOMA2+"=?";
        String[] valuesWhere = {idioma, idioma};
        Cursor c = db.query(
                TRADUCCIONS_TABLE_NAME,                 // The table to query
                columns,                                // The columns to return
                columnsWhere,                           // The columns for the WHERE clause
                valuesWhere,                            // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                null                                    // The sort order
        );
        return c;
    }

    public Cursor getParaulesTraduccio(String nomTaulaTrad, String paraula){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {TRADUCCIO_COLUMN_PARAULA1, TRADUCCIO_COLUMN_PARAULA2};
        String columnsWhere = TRADUCCIO_COLUMN_PARAULA1+"=?"+" OR "+TRADUCCIO_COLUMN_PARAULA2+"=?";
        String[] valuesWhere = {paraula, paraula};
        Cursor c = db.query(
                nomTaulaTrad,                           // The table to query
                columns,                                // The columns to return
                columnsWhere,                           // The columns for the WHERE clause
                valuesWhere,                            // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                null                                    // The sort order
        );
        return c;
    }

    public boolean checkIdioma(String idioma){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {IDIOMES_COLUMN_NAME};
        String columnsWhere = IDIOMES_COLUMN_NAME+"=?";
        String[] valuesWhere = {idioma};
        Cursor c = db.query(
                IDIOMES_TABLE_NAME,                     // The table to query
                columns,                                // The columns to return
                columnsWhere,                           // The columns for the WHERE clause
                valuesWhere,                            // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                null                                    // The sort order
        );
        if (c.moveToFirst()) return true;
        return false;
    }

    public boolean checkParaula(String idioma, String paraula){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {IDIOMA_COLUMN_PARAULA};
        String columnsWhere = IDIOMA_COLUMN_PARAULA+"=?";
        String[] valuesWhere = {paraula};
        Cursor c = db.query(
                idioma,                                 // The table to query
                columns,                                // The columns to return
                columnsWhere,                           // The columns for the WHERE clause
                valuesWhere,                            // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                null                                    // The sort order
        );
        if (c.moveToFirst()) return true;
        return false;
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

    public void insertPuntuacio (String name, int punts){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("punts", punts);

        db.insert(PUNTUACIO_TABLE, null, contentValues);
    }

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

    public Cursor getPuntuacions() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"name", "punts"};
        Cursor c = db.query(
                PUNTUACIO_TABLE,                        // The table to query
                columns,                                // The columns to return
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                null                                    // The sort order
        );
        return c;
    }
//
    public void resetTablePuntuacio(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(PUNTUACIO_TABLE_RESET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }
}
