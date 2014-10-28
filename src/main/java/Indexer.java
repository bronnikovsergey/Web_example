import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public enum Indexer {
    INSTANCE;

    private HashMap<String, HashSet<Integer>> invertIndex;

    private Indexer() throws FileNotFoundException {

        String str;
        String[] arrayString;

        invertIndex = new HashMap<String, HashSet<Integer>>();
        HashSet<Integer> extra;

        File f1 = new File(System.getProperty("user.dir") + "/src/main/TXTfiles");
        File files[] = f1.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            Scanner sc = new Scanner(file);
            HashSet<Integer> set = new HashSet<Integer>();
            set.add(i);

            while (sc.hasNextLine()) {
                str = sc.nextLine();
                arrayString = str.split(" ");
                for (String word : arrayString) {
                    if (invertIndex.containsKey(word)) {
                        extra = invertIndex.get(word);
                        HashSet<Integer> some = new HashSet<Integer>();
                        some.addAll(extra);
                        some.add(i);
                        invertIndex.put(word, some);
                    } else {
                        invertIndex.put(word, set);
                    }
                }
            }
        }
    }

    public static Indexer getInstance() {
        return INSTANCE;
    }

    public ArrayList<String> search(String input) {

        ArrayList<String> result = new ArrayList<String>();

        if (!input.equals("")) {
            String[] inputArr = input.split(" ");
            boolean check = true;

            for (String aString : inputArr) {
                if (!invertIndex.containsKey(aString)) {
                    check = false;
                }
            }

            if (check) {
                HashSet<Integer> wSet = new HashSet<Integer>(invertIndex.get(inputArr[0]));

                for (String aFullField : inputArr) {
                    HashSet<Integer> intersect = new HashSet<Integer>(invertIndex.get(aFullField));
                    wSet.retainAll(intersect);
                }

                for (Integer index : wSet) {
                    result.add("<p>" + index + "</p>");
                    //out.println("<p>" + namesOfFiles[index] + "</p>");
                }

            } else {
                result.add("<p> no words in all files </p>");
                //out.println("<p> no words in all files </p>");
            }

        } else {
            result.add("<p> empty form </p>");
            //out.println("<p> empty form </p>");
        }
        return result;
    }

    public static void main(String[] args) {
    }
}