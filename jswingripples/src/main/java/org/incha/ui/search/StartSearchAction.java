package org.incha.ui.search;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages search upon user search action.
 */
class StartSearchAction implements ActionListener {
    private List<String> words;
    private JTextField searchedWords;

    StartSearchAction(JTextField searchedWords) {
        this.searchedWords = searchedWords;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        words = new ArrayList<>();

        final String text = searchedWords.getText();
        final String cleanedText = text.trim();
        if (text != null && cleanedText.length() > 0) {
            words = getWordList(cleanedText);
            //search = new Search(words);
            print(words);
        }
    }

    private List<String> getWordList(String text) {
        words.clear();
        final StringBuffer word = new StringBuffer("");
        for (int i = 0; i < text.length(); i ++) {
            if (text.charAt(i) == ';') {  // Finds a word separator
                words.add(word.toString().trim());  // New word is added to the list
                word.delete(0, word.length());  // StringBuffer is cleared to store another word
            }
            else {
                word.append(text.charAt(i));  // Character concatenated to current word
            }
        }
        words.add(word.toString().trim());
        return words;
    }

    // Prints received words. For testing purposes only.
    private void print(List<String> words) {
        for(String word : words) {
            System.out.println(word);
        }
    }
}
