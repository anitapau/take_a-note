package edu.tacoma.uw.ahanag22.take_a_note_on_android.Note;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.tacoma.uw.ahanag22.take_a_note_on_android.SignInActivity;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 *
 * @author anita paudel & ahana ghosh
 */
public class NoteContent implements Serializable {
    //id for the note column
    public static final String ID = "id";
    //note description column or the note itself
    public static final String NOTE_DESC = "longDesc";
    //userid column
    public static final String USER_ID = "userid";
    //noteid as file name
    private String mNoteId;
    //description or written note
    private String mLongDesc;
    //user id to save id in the database with note
    private String mUserId;

    /**
     * Constructor to initialize the value
     *
     * @param id
     * @param noteDesc
     */
    public NoteContent(String id, String userid, String noteDesc) {
        mUserId = userid;
        mNoteId = id;
        mLongDesc = noteDesc;
    }


    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns note list if success.
     *
     * @param noteJson
     * @return reason or null if successful.
     */
    public static String parseNoteJson(String noteJson, List<NoteContent> noteContentList) {
        String reason = null;
        if (noteJson != null && !noteJson.isEmpty()) {
            try {
                JSONArray arr = new JSONArray(noteJson);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    NoteContent noteContent = new NoteContent(obj.getString(NoteContent.ID), obj.getString(NoteContent.USER_ID), obj.getString(NoteContent.NOTE_DESC));
                    noteContentList.add(noteContent);
                }
            } catch (JSONException e) {
                reason = "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

    /**
     * getter to get the id
     *
     * @return note id
     */
    public String getId() {
        return mNoteId;
    }

    /**
     * getter to get the note
     *
     * @return note description
     */
    public String getNoteDesc() {
        return mLongDesc;
    }

    /*
    *getter to get the user id
    * @return userid
     */
    public String getUserId() {
        return mUserId;
    }

    public void setId(String theNoteId) {
        if (theNoteId.isEmpty()) {
            throw new IllegalArgumentException("file name is not given");
        }
        this.mNoteId = theNoteId;
    }

    /*
    *set the user id
    * @param theUserId user's id
     */
    public void setUserId(String theUserId) {
        if (theUserId.isEmpty()) {
            throw new IllegalArgumentException("userid is not valid");
        }
        this.mUserId = theUserId;
    }

    //set the note description
    //@param theNoteDesc notedescription
    public void setNoteDesc(String theNoteDesc) {
        this.mLongDesc = theNoteDesc;
    }
    public void setmUserId(String theUserid) {
        this.mUserId = theUserid;
    }
}

