import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

	public static int userChoose(Scanner kybd, String[] choices, String title) {
		int choice = -1;
		System.out.println(title);
		for (int i = 0; i < choices.length; i++) {
			System.out.print("\t[" + i + "] " + choices[i]);
		}
		choice = kybd.nextInt();
		return choice;
	}

	public static int userChoose(Scanner kybd, List choices, String title) {
		String[] strArray = new String[choices.size()];
		int i = 0;
		for (Object s: choices) {
			strArray[i++] = s.toString();
		}
		return userChoose(kybd, strArray, title);
	}

	public static int[] userChoices(Scanner kybd, String title) {
		System.out.print(title);
		int first = kybd.nextInt();
		String rest = kybd.nextLine();
		ArrayList<Integer> inp = new ArrayList<>();
		inp.add(first);
		Scanner restOfLine = new Scanner(rest);
		while (restOfLine.hasNextInt()) {
			inp.add(restOfLine.nextInt());
		}
		int siz = inp.size();
		int[] choices = new int[siz];
		int pos = 0;
		for (Integer i : inp) {
			choices[pos++] = i;
		}
		restOfLine.close();
		return choices;
	}

	public static int userChooseListing(Scanner kybd, String[] choices) {
		int choice = -1;
		System.out.println("Make a Selection: ");
		for (int i = 0; i < choices.length; i++) {
			System.out.println("\t[" + i + "] " + choices[i]);
		}
		choice = kybd.nextInt();
		return choice;
	}

	public static void listing(String[] choices) {
		for (int i = 0; i < choices.length; i++) {
			System.out.println("\t[" + i + "] " + choices[i]);
		}
	}
	/*
	public static void listing(List<String> choices) {
		listing((String[]) choices.toArray());
	}
	*/

	public static void listing(Object[] choices) {
		for (int i = 0; i < choices.length; i++) {
			System.out.print("\t[" + i + "] " + choices[i].toString());
		}
	}

	public static void listing(List choices) {
		listing(choices.toArray());
	}

}
