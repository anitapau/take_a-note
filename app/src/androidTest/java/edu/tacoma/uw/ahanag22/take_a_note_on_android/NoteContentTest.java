package edu.tacoma.uw.ahanag22.take_a_note_on_android;

import junit.framework.Assert;

import org.junit.Test;

import edu.tacoma.uw.ahanag22.take_a_note_on_android.Note.NoteContent;

/**
 * Created by anita on 5/29/2017.
 */

public class NoteContentTest {
    NoteContent note = new NoteContent("test", "TestEmail@uw.edu", "description");
    @Test
    public void testNoteContentConstructor() {
        NoteContent n = new NoteContent("test","mmuppa", "description");
        Assert.assertNotNull(new NoteContent("test","mmuppa", "description"));
        Assert.assertEquals("test",n.getId());
        Assert.assertEquals("description",n.getNoteDesc());
        Assert.assertEquals("mmuppa",n.getUserId());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadSetId() {
        // Account testP = new Account("mmuppauw.edu", "test1@3");
        NoteContent n = new NoteContent("test", "test", "test1@3");
        n.setUserId("");
       // Assert.fail("userid is not valid");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testsetBadFileNameInconstructor() {
        NoteContent n = new NoteContent("test", "test", "test1@3");
        n.setId("");
       // Assert.fail("note created with empty filename");
    }

    @Test
    public void testgetId() {
        // Account testP = new Account("mmuppauw.edu", "test1@3");
        NoteContent n = new NoteContent("test", "test", "test1@3");
        Assert.assertEquals("test", n.getId());
    }
    @Test
    public void testgetUserid() {
        NoteContent n = new NoteContent("test", "test", "test1@3");
        Assert.assertEquals("test", n.getUserId());
    }

    @Test
    public void testgetNoteDesc() {
        // Account testP = new Account("mmuppauw.edu", "test1@3");
        NoteContent n = new NoteContent("test", "test", "test1@3");
        Assert.assertNotSame("test1@3", n.getId());
        Assert.assertEquals("test1@3", n.getNoteDesc());
    }

}
