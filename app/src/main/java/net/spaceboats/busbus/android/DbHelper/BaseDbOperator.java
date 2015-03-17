package net.spaceboats.busbus.android.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.spaceboats.busbus.android.Entity;

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
    }

    public void insert(Entity entity) {
        List<Entity> entities = new ArrayList<>();
        entities.add(entity);
        insert(entities);
    }

    public void insert(Entity entity, SQLiteDatabase db) {
        List<Entity> entities = new ArrayList<>();
        entities.add(entity);
        insert(entities, db);
    }

    public List<Entity> query() {
        FavoritesDbHelper sqlHelper = new FavoritesDbHelper(mContext);
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        return query(db);
    }

    public void insert(List<Entity> entities) {
        FavoritesDbHelper sqlHelper = new FavoritesDbHelper(mContext);
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            insert(entities, db);
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }
    }

    public void insert(List<Entity> entities, SQLiteDatabase db) {
        for (int i = 0; i < entities.size(); i++) {
            insertSubEntities(entities.get(i), db);
            db.insert(getTableName(), null, getContentValues(entities.get(i)));
        }
    }

    public void insertSubEntities(Entity entity, SQLiteDatabase db) {
        // Should be implemented if the entity has other entities that need to be stored.
        // i.e. Arrival has both a stop and a route.
    }

    public List<Entity> query(SQLiteDatabase db) {
        List<Entity> entities = new ArrayList<>();

        Cursor cursor = db.query(getTableName(), getColumns(), null, null, null, null, null);
        while(cursor.moveToNext()) {
            entities.add(getNewEntity(cursor, db));
        }
        cursor.close();

        return entities;
    }

    protected abstract String getTableName();
    protected abstract ContentValues getContentValues(Entity entity);
    protected abstract String[] getColumns();
    protected abstract Entity getNewEntity(Cursor cursor, SQLiteDatabase db);

}
