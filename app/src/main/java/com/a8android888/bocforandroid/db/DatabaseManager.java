package com.a8android888.bocforandroid.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Janz on 2017/2/16.
 */
public class DatabaseManager {
    private static DatabaseManager instance;
    private static SQLiteDatabase mDatabase;

    private DatabaseManager(){

    }

    public static DatabaseManager getInstance(){
        if(instance==null){
            synchronized (DatabaseManager.class){
                if(instance==null){
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        File file= new File("/data/data/com.a8android888.bocforandroid"
                );
        if(mDatabase==null || !mDatabase.isOpen()) {
            // Opening new database
            mDatabase= SQLiteDatabase.openOrCreateDatabase(file, null);
        }
        else
        {
            if(mDatabase.isDbLockedByCurrentThread())
            {
                mDatabase.close();
                mDatabase= SQLiteDatabase.openOrCreateDatabase(file, null);
            }
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
            mDatabase.close();
    }
}
