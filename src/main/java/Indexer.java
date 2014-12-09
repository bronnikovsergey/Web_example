import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public enum Indexer {
    INSTANCE;

    final Logger LOG = LoggerFactory.getLogger(Indexer.class);

    private HashMap<String, HashSet<Integer>> invertIndex;
    private File[] files;

    private Indexer() {

        String s;
        String[] words;

        invertIndex = new HashMap<String, HashSet<Integer>>();
        HashSet<Integer> extraSet;

        try {
            File catalog = new File(System.getProperty("user.dir") + "/src/main/TXTfiles");
            files = catalog.listFiles();
            assert files != null;
            char[] punctuationMarks = {'-', '.', '…', '?', ',', '”', '“', '"', ':', '!', ';', '(', ')'};
            for (int i = 0; i < files.length; i++) {
                File file = files[i];

                Scanner sc = new Scanner(file);
                HashSet<Integer> setOfIndexOfFiles = new HashSet<Integer>();
                setOfIndexOfFiles.add(i);

                while (sc.hasNextLine()) {
                    s = sc.nextLine().toLowerCase();
                    for (char punctuationMark : punctuationMarks) {
                        s = s.replace(punctuationMark, ' ');
                    }
                    words = s.split(" ");
                    for (String word : words) {
                        if (invertIndex.containsKey(word)) {
                            extraSet = invertIndex.get(word);
                            HashSet<Integer> indexOfSuitableFiles = new HashSet<Integer>();
                            indexOfSuitableFiles.addAll(extraSet);
                            indexOfSuitableFiles.add(i);
                            invertIndex.put(word, indexOfSuitableFiles);
                        } else {
                            invertIndex.put(word, setOfIndexOfFiles);
                        }
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            LOG.error("no files", ex);
            // bad gateway
        }
    }

    public static Indexer getInstance() {
        return INSTANCE;
    }

    public ArrayList<String> search(String inputString) {

        ArrayList<String> filesWithInputWords = new ArrayList<String>();


        if (!inputString.equals("")) {
            String[] inputWords = inputString.toLowerCase().split(" ");
            boolean check = true;

            for (String aString : inputWords) {
                LOG.debug(aString);
                if (!invertIndex.containsKey(aString)) {
                    check = false;
                }
            }

            if (check && inputWords.length != 0) {

                HashSet<Integer> finalSet = new HashSet<Integer>(invertIndex.get(inputWords[0]));

                for (String aFullField : inputWords) {
                    HashSet<Integer> intersect = new HashSet<Integer>(invertIndex.get(aFullField));
                    finalSet.retainAll(intersect);
                }

                for (Integer index : finalSet) {
                    filesWithInputWords.add("<p>" + files[index].getName() + "</p>");
                }

            } else {
                LOG.debug("no words");
                filesWithInputWords.add("<p> no words in all files </p>");
            }

        } else {
            LOG.debug("empty form");
            filesWithInputWords.add("<p> empty form </p>");
        }


        return filesWithInputWords;
    }
}

