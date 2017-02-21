
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Simple partial tester for Member
//
public class MemberPlay {

	public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
		Member m = new Member(1000);
		System.out.println(m);

		Title t = Title.getOneTitle(102);
		Title t2 = Title.getOneTitle(103);
		Title t3 = Title.getOneTitle(104);
		System.out.println(t);
		m.rent(t);
		m.rent(t2);
		m.rent(t3);
		System.out.println(m.getRentedTitles());
		m.checkIn(t);
		System.out.println(m.getRentedTitles());
		System.out.println(m.getRentals());

	}

}
