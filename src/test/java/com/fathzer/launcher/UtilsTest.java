package com.fathzer.launcher;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilsTest {

    @Test
    public void testReplaceAll() {
        // Test basic replacement
        assertEquals("Hello World", Utils.replaceAll("Hello There", "There", "World"));
        
        // Test multiple replacements
        assertEquals("banana banana banana", 
            Utils.replaceAll("apple apple apple", "apple", "banana"));
        
        // Test no match
        assertEquals("no change", 
            Utils.replaceAll("no change", "notfound", "replacement"));
        
        // Test empty target
        assertEquals("no change", 
            Utils.replaceAll("no change", "", "should not be used"));
            
        // Test null source
        assertNull(Utils.replaceAll(null, "test", "test"));
        
        // Test empty source
        assertEquals("", Utils.replaceAll("", "test", "test"));
        
        // Test empty replacement
        assertEquals("Hello  World", 
            Utils.replaceAll("Hello Beautiful World", "Beautiful", ""));
            
        // Test special characters
        assertEquals("a-b-c", 
            Utils.replaceAll("a b c", " ", "-"));
            
        // Test overlapping patterns
        assertEquals("xoxo", 
            Utils.replaceAll("abcabc", "abc", "xo"));
    }

    @Test
    public void testEdgeCases() {
        // Test replacing with the same string
        assertEquals("same same same", 
            Utils.replaceAll("same same same", "same", "same"));
        
        // Test target at start
        assertEquals("start middle end", 
            Utils.replaceAll("# middle end", "# ", "start "));
            
        // Test target at end
        assertEquals("start middle end", 
            Utils.replaceAll("start middle #", " #", " end"));
    }
}
