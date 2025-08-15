package com.example.lab18_karaoke_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DatabaseAssetHelper {

    public static final String DB_NAME = "arrirang.sqlite";
    private static final String DB_FOLDER_SUFFIX = "/databases/";

    private Context context;

    public DatabaseAssetHelper(Context context) {
        this.context = context;
    }

    private File dbFile() {
        return new File(context.getApplicationInfo().dataDir + DB_FOLDER_SUFFIX + DB_NAME);
    }

    /** Chỉ copy nếu chưa tồn tại */
    public void copyIfNeeded() {
        File outFile = dbFile();
        if (outFile.exists()) {
            return;
        }

        if (outFile.getParentFile() != null) {
            outFile.getParentFile().mkdirs();
        }

        try (InputStream input = context.getAssets().open(DB_NAME);
             FileOutputStream output = new FileOutputStream(outFile)) {

            byte[] buf = new byte[8 * 1024];
            int r;
            while ((r = input.read(buf)) != -1) {
                output.write(buf, 0, r);
            }
            output.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("DB", "Copied " + outFile.getAbsolutePath());
    }

    public SQLiteDatabase openDatabase() {
        copyIfNeeded();
        return SQLiteDatabase.openDatabase(
                dbFile().getAbsolutePath(),
                null,
                SQLiteDatabase.OPEN_READWRITE
        );
    }
}
