
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Words {
  private static File DICT_FILE = new File("/usr/share/dict/words");
  
  public static List<String> getAllWords() throws FileNotFoundException {
    Scanner scanner = new Scanner(DICT_FILE);
    ArrayList<String> allWords = new ArrayList<>();
    while (scanner.hasNext()) {
      allWords.add(scanner.next());
    }
    return allWords;
  }
  
  public static List<String> getPracticeWords(Set<Character> characters)
      throws FileNotFoundException {
    List<String> allWords = getAllWords();
    List<String> practiceWords = new ArrayList<>();
    for (String word : allWords) {
      boolean canPractice = true;
      for (int i = 0; i < word.length(); i++) {
        if (!characters.contains(word.charAt(i))) {
          canPractice = false;
        }
      }
      if (canPractice) {
        practiceWords.add(word);
      }
    }
    return practiceWords;
  }
}