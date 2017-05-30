package edu.tacoma.uw.ahanag22.take_a_note_on_android.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import edu.tacoma.uw.ahanag22.take_a_note_on_android.Note.NoteContent;
import edu.tacoma.uw.ahanag22.take_a_note_on_android.R;
import edu.tacoma.uw.ahanag22.take_a_note_on_android.SignInActivity;

/**
 * Created by anita on 5/27/2017.
 */

public class NoteDB {
    public static final int DB_VERSION = 1;
    //database name
    public static final String DB_NAME = "Note.db";
    //note table in the database
    private static final String NOTE_TABLE = "savenote";
    //helper for the database
    private NoteDBHelper mNoteDBHelper;
    //sqllite database instance
    private SQLiteDatabase mSQLiteDatabase;

    /**
     * constructor to create note database
     *
     * @param context context
     */
    public NoteDB(Context context) {
        mNoteDBHelper = new NoteDBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mNoteDBHelper.getWritableDatabase();
    }

    /**
     * Returns the list of courses from the local list table.
     *
     * @return list of notes
     */
    public List<NoteContent> getNotes() {

        String[] columns = {
                "id", "userid", "longDesc"
        };

        Cursor c = mSQLiteDatabase.query(
                NOTE_TABLE,  // The table to query
                columns,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        c.moveToFirst();
        List<NoteContent> list = new ArrayList<NoteContent>();
        for (int i = 0; i < c.getCount(); i++) {
            String id = c.getString(0);
            String longDesc = c.getString(2);
            NoteContent noteContent = new NoteContent(id, SignInActivity.muserId, longDesc);
            list.add(noteContent);
            c.moveToNext();
        }
        return list;
    }

    /**
     * Inserts the course into the local sqlite table. Returns true if successful, false otherwise.
     *
     * @param id
     * @param longDesc
     * @param userid;
     * @return true or false
     */
    public boolean insertCourse(String id, String userid, String longDesc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("userid", SignInActivity.muserId);
        contentValues.put("longDesc", longDesc);
        long rowId = mSQLiteDatabase.insert("savenote", null, contentValues);
        return rowId != -1;
    }

    public void closeDB() {
        mSQLiteDatabase.close();
    }

    /**
     * Delete all the data from the COURSE_TABLE
     */
    public void deleteNotes() {
        mSQLiteDatabase.delete(NOTE_TABLE, null, null);
    }


    /**
     * Updates the row of values for the given row id.
     *
     * @param id
     * @param longDesc
     * @return false if 0 rows were updated. true otherwise.
     */
    public boolean updateNoteContent(String id, String userid, String longDesc) {
        ContentValues contentValues = new ContentValues();
        // contentValues.put("id", id);
        //contentValues.put("userid", userid);
        contentValues.put("longDesc", longDesc);
        String whereClause = "id='" + id + "'";
        mSQLiteDatabase.execSQL("UPDATE " + NOTE_TABLE + " SET longDesc='" + longDesc + "' " + "WHERE id='" + id + "' AND userid='" + SignInActivity.muserId + "';");
//       if(mSQLiteDatabase.update(COURSE_TABLE, contentValues,  null) == 0){
//           return false;
//       }
        return true;
    }

    /**
     * NoteDBhelper class to assist in sqllite connection
     */
    class NoteDBHelper extends SQLiteOpenHelper {

        private final String CREATE_NOTECONTENT_SQL;

        private final String DROP_NOTECONTENT_SQL;

        public NoteDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            CREATE_NOTECONTENT_SQL = context.getString(R.string.CREATE_NOTECONTENT_SQL);
            DROP_NOTECONTENT_SQL = context.getString(R.string.DROP_NOTECONTENT_SQL);

        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_NOTECONTENT_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_NOTECONTENT_SQL);
            onCreate(sqLiteDatabase);
        }
    }


}
