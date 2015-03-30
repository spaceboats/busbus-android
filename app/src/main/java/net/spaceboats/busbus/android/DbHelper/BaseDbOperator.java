package net.spaceboats.busbus.android.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.spaceboats.busbus.android.Entites.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zralston on 3/16/15.
 */
abstract class BaseDbOperator<T extends Entity> {

    Context mContext;

    public BaseDbOperator(Context context) {
        mContext = context;
        if(!DbManager.isInitialized()) {
            DbManager.initDb(context.getApplicationContext());
        }
    }

    public void insert(T entity) {
        SQLiteDatabase db = DbManager.getDatabase();

        try {
            insertSubEntities(entity);
            db.replace(getTableName(), null, getContentValues(entity));
        }
        finally {
            DbManager.closeDatabase();
        }
    }

    public void delete(T entity) {
        SQLiteDatabase db = DbManager.getDatabase();

        try {
            db.delete(getTableName(), getDeleteWhereClause(), getDeleteWhereArgs(entity));
        }
        finally {
            DbManager.closeDatabase();
        }
    }

    public void insertAsTransaction(T entity) {
        SQLiteDatabase db = DbManager.getDatabase();

        db.beginTransaction();
        try {
            insert(entity);
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            DbManager.closeDatabase();
        }
    }

    public void deleteAsTransaction(T entity) {
        SQLiteDatabase db = DbManager.getDatabase();

        db.beginTransaction();
        try {
            delete(entity);
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            DbManager.closeDatabase();
        }
    }

    public void insertSubEntities(T entity) {
        // Should be implemented if the entity has other entities that need to be stored.
        // i.e. Arrival has both a stop and a route.
    }

    public List<Entity> query() {
        List<Entity> entities = new ArrayList<>();
        SQLiteDatabase db = DbManager.getDatabase();
        Cursor cursor = db.query(getTableName(), getColumns(), null, null, null, null, null);

        try {
            while (cursor.moveToNext()) {
                entities.add(getNewEntity(cursor));
            }
        }
        finally {
            DbManager.closeDatabase();
            cursor.close();
        }

        return entities;
    }

    public T queryWithId(String id) {
        SQLiteDatabase db = DbManager.getDatabase();
        String[] args = {id};
        T entity = null;
        Cursor cursor = db.query(getTableName(), getColumns(), getIdSelection(), args, null, null, null);

        try {
            if(cursor.moveToFirst())
                entity = getNewEntity(cursor);
        }
        finally {
            DbManager.closeDatabase();
            cursor.close();
        }

        return entity;
    }

    protected String getDeleteWhereClause() {
        String[] columns = getColumns();

        String result = "";
        for(int i = 0; i < columns.length - 1; i++) {
            result += columns[i] + " = ? and ";
        }

        result += columns[columns.length - 1] + " = ?";
        return result;
    }

    protected String[] getDeleteWhereArgs(T entity) {
        ContentValues contentValues = getContentValues(entity);
        String[] columns = getColumns();

        String[] args = new String[contentValues.size()];
        for(int i = 0; i < contentValues.size(); i++) {
            args[i] = contentValues.getAsString(columns[i]);
        }

        return args;
    }

    protected abstract String getTableName();
    protected abstract ContentValues getContentValues(T entity);
    protected abstract String[] getColumns();
    protected abstract T getNewEntity(Cursor cursor);
    protected abstract String getIdSelection();

}
