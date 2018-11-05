import java.util.Scanner;

public class UniqueWords {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Give me a sentence:");
		String sentence = input.nextLine();
		String[] words = sentence.split(" ");
		String uniqueWords = new String("");
		for (int i = 0; i < words.length; i++) {
			if (!uniqueWords.contains(words[i])) {
				uniqueWords = uniqueWords + " " + words[i];
			}
		}
		String[] uniqueWordsArray = uniqueWords.split(" ");
		System.out.println(uniqueWordsArray.length - 1);
	}
	
}