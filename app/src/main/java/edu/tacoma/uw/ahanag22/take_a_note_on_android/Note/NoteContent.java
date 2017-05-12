package edu.tacoma.uw.ahanag22.take_a_note_on_android.Note;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 *
 */
public class NoteContent {

    /**
     * An array of Note items.
     */
    public static final List<NoteItem> ITEMS = new ArrayList<NoteItem>();

    public String getMyNoteDetail() {
        return myNoteDetail;
    }

    public void setMyNoteDetail(String myNoteDetail) {
        this.myNoteDetail = myNoteDetail;
    }

    public static final String ID = "id", NOTE_DETAIL = "prereqs";
    public String myNoteDetail;

    /**
     * A map of note items, by ID.
     */
    public static final Map<String, NoteItem> ITEM_MAP = new HashMap<String, NoteItem>();

    private static final int COUNT = 25;

    static {
        // Add note items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createNoteItem(i));
        }
    }

    public NoteContent(String myNoteDetail) {
        this.myNoteDetail = myNoteDetail;
    }
    /*
    adding a note item
     */
    private static void addItem(NoteItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
    /*
    create and returns a note item
     */
    private static NoteItem createNoteItem(int position) {
        return new NoteItem(String.valueOf(position), "Note " + position);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A Note Item representing a piece of content.
     */
    public static class NoteItem implements Serializable {
        public final String id;
        public final String content;

        public NoteItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}

