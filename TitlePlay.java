
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Simple partial tester for Title
public class TitlePlay {

	public static void main(String[] args) throws FileNotFoundException, SQLException, IOException {
		List<Title> al = new ArrayList<>();
		al = Title.getAllTitles();
		for (Title t : al) {
			System.out.println(t);
		}

		Title t2 = Title.getOneTitle(113);
		System.out.println("\n" + t2);
	}

}
