
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

// Video Store Application
public class VideoStore {
	
	private static final String[] MAIN_MENU = {
			"log in",
			"log out",
			"list all titles",
			"list available titles",
			"list my rented titles",
			"rent title",
			"return title",
			"exit"
	};

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		Scanner kybd = new Scanner(System.in);
		Member currentMember = null;
		int userChoice = -1;
		while (userChoice != 7) {
			userChoice = Utils.userChoose(kybd, MAIN_MENU, "Make Your Selection: ");
			if (userChoice == 0) {
				currentMember = login(kybd);
				System.out.println(currentMember);
			} else if (userChoice == 1) {
				currentMember = null;
			} else if (userChoice == 2) {
				listTitles(kybd);
			} else if (userChoice == 3) {
				listAvailableTitles(kybd);
			} else if (userChoice == 4) {
				listMemberRentedTitles(kybd, currentMember);
			} else if (userChoice == 5) {
				rentTitle(kybd, currentMember);
			} else if (userChoice == 6) {
				returnTitle(kybd, currentMember);
			}
		}
	}

	private static void returnTitle(Scanner kybd, Member currentMember) throws FileNotFoundException, SQLException, IOException {
		// ArrayList<Copy> copies = currentMember.rentedCopies(store.getInventory());
		Set<Title> rentedTitles = currentMember.getRentedTitles();
		List<Title> al = new ArrayList<Title>(rentedTitles);
		int i = Utils.userChoose(kybd, al, "Which movie do you wish to return? ");
		Title t = al.get(i);
		currentMember.checkIn(t);
	}

	private static void rentTitle(Scanner kybd, Member currentMember) throws IOException, SQLException {
		List<Title> avails = Title.getAvailableTitles();
		int i = Utils.userChoose(kybd, avails, "Choose your movie: ");
		Title t = avails.get(i);
		currentMember.rent(t);
//		Copy c = store.inventory.findAvailableCopy(t.getName());
//		currentMember.rent(store.getInventory(), c.getId());
	}

	private static void listMemberRentedTitles(Scanner kybd, Member currentMember) throws SQLException, IOException {
		Set<Title> rented = currentMember.getRentedTitles();
		List<Title> al = new ArrayList<Title>(rented);
		Utils.listing(al);
	}

	private static void listAvailableTitles(Scanner kybd) throws FileNotFoundException, SQLException, IOException {
		Utils.listing(Title.getAvailableTitles());
	}

	private static void listTitles(Scanner kybd) throws FileNotFoundException, SQLException, IOException {
		List<Title> allTitles = Title.getAllTitles();
		Utils.listing(allTitles);
	}

	private static Member login(Scanner kybd) throws SQLException, IOException {
		List<Member> members = Member.getAllMembers();
		int c = Utils.userChoose(kybd, members, "Choose the member: ");
		Member member = members.get(c);
		return member;
	}

}
