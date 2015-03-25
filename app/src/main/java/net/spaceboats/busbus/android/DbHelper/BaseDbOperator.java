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
public abstract class BaseDbOperator {
    // TODO: See if I can change this to use generics instead.
    // i.e "BaseDbOperator<T extends Entity>"

    Context mContext;

    public BaseDbOperator(Context context) {
        mContext = context;
        if(!DbManager.isInitialized()) {
            DbManager.initDb(context.getApplicationContext());
        }
    }

    public void insert(Entity entity) {
        SQLiteDatabase db = DbManager.getDatabase();

        try {
            insertSubEntities(entity);
            db.replace(getTableName(), null, getContentValues(entity));
        }
        finally {
            DbManager.closeDatabase();
        }
    }

    public void insertAsTransaction(Entity entity) {
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

    public void insertSubEntities(Entity entity) {
        // Should be implemented if the entity has other entities that need to be stored.
        // i.e. Arrival has both a stop and a route.
    }

    public List<Entity> query() {
        List<Entity> entities = new ArrayList<>();
        SQLiteDatabase db = DbManager.getDatabase();

        try {
            Cursor cursor = db.query(getTableName(), getColumns(), null, null, null, null, null);
            while (cursor.moveToNext()) {
                entities.add(getNewEntity(cursor));
            }
            cursor.close();
        }
        finally {
            DbManager.closeDatabase();
        }

        return entities;
    }

    public Entity queryWithId(String id) {
        SQLiteDatabase db = DbManager.getDatabase();
        String[] args = {id};
        Entity entity;
        try {
            Cursor cursor = db.query(getTableName(), getColumns(), getIdSelection(), args, null, null, null);
            cursor.moveToFirst();
            entity = getNewEntity(cursor);
            cursor.close();
        }
        finally {
            DbManager.closeDatabase();
        }

        return entity;
    }

    protected abstract String getTableName();
    protected abstract ContentValues getContentValues(Entity entity);
    protected abstract String[] getColumns();
    protected abstract Entity getNewEntity(Cursor cursor);
    protected abstract String getIdSelection();

}
