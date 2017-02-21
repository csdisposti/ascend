
import java.sql.*;

import java.util.*;

/**
 * Created by cdisp on 12/10/2016.
 */
public class Member {

    //A member of our video store.
    // Members have first and last names, an address,
    //phone number, and the date they joined our store.
    //They are uniquely identified by an id number.
    private Integer idNumber;
    private MemberData memData = new MemberData();
    private CheckoutData chOutD = new CheckoutData();

    //working as of 12/11
    //Constructor to look up a known member in the database
    public Member(int memberNumber) throws java.io.IOException, java.sql.SQLException {
        idNumber = memberNumber;
        Connection conn = DbConnection.accessDbConnection().getConnection();
        Statement stat = conn.createStatement();
        ResultSet result = stat.executeQuery("SELECT * FROM member WHERE member_id = " + idNumber);

        while (result.next()) {
            memData.setFname(result.getString("fname"));
            memData.setLname(result.getString("lname"));
            memData.setStreet(result.getString("street"));
            memData.setCity(result.getString("city"));
            memData.setPhone(result.getString("phone"));
            memData.setMemberSince(result.getDate("start_Date"));
        }
    }

    //working as of 12/12 woo hoo!
    //Rent a video of the given title. We scan the copies for the desired title and choose an available copy to check out.
    public boolean rent(Title t) throws java.io.IOException, java.sql.SQLException {
        Connection conn = DbConnection.accessDbConnection().getConnection();
        Statement stat = conn.createStatement();

        int copyId;
        int titleId = t.getTitleId();
        ResultSet result = stat.executeQuery("SELECT * FROM copy WHERE title_id = " + titleId);
        if (result.next()) {
            copyId = result.getInt("copy_id");
            java.util.Date date = new java.util.Date();
            Timestamp ti = new java.sql.Timestamp(date.getTime());
            chOutD = new CheckoutData(ti, copyId);

            String insertSQL = "INSERT INTO rental(checkout_date, copy_id, member_id) values(?, ?, ?)";

            PreparedStatement preStat = conn.prepareStatement(insertSQL);
            preStat.setTimestamp(1, ti);
            preStat.setInt(2, copyId);
            preStat.setInt(3, idNumber);
            preStat.executeUpdate();

            Statement statNext = conn.createStatement();
            statNext.execute("UPDATE copy SET status = '" + "OUT STORE" + "' WHERE copy_id = " + copyId);

            return true;
        }
        else {
            return false;
        }
    }

    //working as of 12/12
    //Return a checked-out video to the store. We assume that a given user has only 1 copy of a particular movie checked out.
    //We must scan the copies of the given title to locate the one that this member has.
    //That copy is returned to the store's available inventory.
    public void checkIn(Title t) throws java.sql.SQLException, java.io.IOException {
        Connection conn = DbConnection.accessDbConnection().getConnection();
        Statement stat = conn.createStatement();

        int copyId;
        int memId;
        int titleId = t.getTitleId();

        ResultSet result = stat.executeQuery("SELECT * FROM rental INNER JOIN copy ON copy.copy_id = rental.copy_id WHERE title_id = "
                + titleId + " AND member_id = " + idNumber);
        while (result.next()) {
            copyId = result.getInt("copy_id");
            memId = result.getInt("member_id");

            //String checkIn = "UPDATE rental INNER JOIN copy ON copy.copy_id = rental.copy_id SET checkin_Date = ? AND status = ? WHERE copy_id = ?";
            String checkIn = "UPDATE rental SET checkin_Date = ? WHERE member_id = ? AND copy_id = ?";
            PreparedStatement preStat = conn.prepareStatement(checkIn);
            preStat.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));
            preStat.setInt(2, memId);
            preStat.setInt(3, copyId);
            preStat.executeUpdate();

            Statement statNext = conn.createStatement();
            statNext.execute("UPDATE copy SET status = '" + "IN STORE" + "' WHERE copy_id = " + copyId);
        }
    }

    //working as of 12/11
    //Return a String containing the member id, first name, last name, address,
    // phone number and member start date (if a start date is available--return "no date" if not)
    @Override
    public String toString() {
        return idNumber + " " + memData.getFname() + " " + memData.getLname() + " " + memData.getStreet() +
                " " + memData.getCity() + " " + memData.getPhone() + " " + memData.getMemberSince();
    }

    //working as of 12/12 finally!
    //Return the titles that this member has currently checked out.
    public Set<Title> getRentedTitles() throws java.sql.SQLException, java.io.IOException {
        //returns the set of Titles we have checked out
        Connection conn = DbConnection.accessDbConnection().getConnection();
        Statement stat = conn.createStatement();
        Set<Title> rt = new HashSet<>();
        int titleId = 0;
       ResultSet result = stat.executeQuery("SELECT * FROM copy INNER JOIN rental ON copy.copy_id = rental.copy_id WHERE member_id = " +
                idNumber + " AND status = '" + "OUT STORE" + "'");
       while (result.next()) {
            titleId = result.getInt("title_id");
            Title tr = new Title(titleId);
            rt.add(tr);
        }
        return rt;
    }

    //working as of 12/12 had to add a toString method to CheckoutData
    //Find the titles that this member has checked out and return the data associated with that checkout.
    public Map<Title, CheckoutData> getRentals() throws java.sql.SQLException, java.io.IOException {
        //Returns Map keyed by Title of checkout data associated with a title.
        Connection conn = DbConnection.accessDbConnection().getConnection();
        Statement stat = conn.createStatement();
        Map<Title, CheckoutData> tCO = new HashMap<>();
        int titleId;
        ResultSet result = stat.executeQuery("SELECT * FROM rental INNER JOIN copy ON copy.copy_id = rental.copy_id WHERE member_id = " +
                idNumber + " AND status = '" + "OUT STORE" + "'");
        getRentedTitles();
        while (result.next()) {
            titleId = result.getInt("title_id");
            Title title = new Title(titleId);
            Timestamp coD = result.getTimestamp("checkout_date");
            int chI = result.getInt("copy_id");
            chOutD = new CheckoutData(coD, chI);
            tCO.put(title, chOutD);
        }
        return tCO;
    }

    //working as of 12/11
    //Static utility to return the member information for all of the store's customers
    public static List<Member> getAllMembers() throws java.sql.SQLException, java.io.IOException {
        //Returns All of the store's members
        Connection conn = DbConnection.accessDbConnection().getConnection();
        Statement stat = conn.createStatement();
        List<Member> m = new ArrayList<>();
        int memID;
        ResultSet result = stat.executeQuery("SELECT * FROM member");

        while (result.next()) {
            memID = result.getInt("member_id");
            Member member = new Member(memID);
            m.add(member);
        }
        return m;
        }
    }


