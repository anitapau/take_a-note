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
 */
public class NoteContent implements Serializable {

    public static final String ID = "id";
    public static final String NOTE_DESC = "longDesc";
    public static final String USER_ID = "userid";
    private String mNoteId;
    private String mLongDesc;
    private String mUserId;
    /**
     * Constructor to initialize the value
     * @param id
     * @param noteDesc
     */
    public NoteContent(String id, String userid, String noteDesc) {

        setId(id);
        setUserId(userid);
        setNoteDesc(noteDesc);
    }


    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns course list if success.
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
                    NoteContent noteContent = new NoteContent(obj.getString(NoteContent.ID),obj.getString(NoteContent.USER_ID), obj.getString(NoteContent.NOTE_DESC));
                    noteContentList.add(noteContent);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

    public String getId() {
        return mNoteId;
    }

    public String getNoteDesc() {
        return mLongDesc;
    }
    public String getUserId() {
        return mUserId;
    }
    public void setId(String theNoteId) {
        if(theNoteId.isEmpty()) {
            throw new IllegalArgumentException("file name is not given");
        }
        this.mNoteId = theNoteId;
    }
    public void setUserId(String theUserId)
    {
        if(theUserId.isEmpty()) {
            throw new IllegalArgumentException("userid is not valid");
        }
        this.mUserId = theUserId;
    }
    public void setNoteDesc(String theNoteDesc) {
        this.mLongDesc = theNoteDesc;
    }
}

