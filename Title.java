import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by cdisp on 12/10/2016.
 */
public class Title {
    private int databaseId = 0;
    private String name = " ";
    private Date publicationDate = null;


    //Constructor to create a Title object.
    public Title(int id) throws java.sql.SQLException, java.io.FileNotFoundException, java.io.IOException {
        databaseId = id;
        Connection conn = DbConnection.accessDbConnection().getConnection();
        Statement stat = conn.createStatement();
        ResultSet result = stat.executeQuery("SELECT * FROM title WHERE title_id = " + databaseId);
        while (result.next()) {
            databaseId = result.getInt("title_id");
            name = result.getString("title_name");
            publicationDate = result.getDate("published_date");
        }
    }

    //Getter for the title id
    public int getTitleId() {
        //Returns database id of this title
        return databaseId;
    }

    //Getter for the name of this movie
    public String getName() throws java.io.IOException, java.sql.SQLException {
        //Returns: name
        return name;
    }

    //Getter for the date this movie was published
    public Date getPublished() throws java.sql.SQLException, java.io.IOException {
        //Returns: publication date of this Title
        return publicationDate;
    }


    @Override
    public int hashCode() {
        int result;
        result = (databaseId / 11);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj instanceof Title) && (((Title) obj).getTitleId() == this.databaseId)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return databaseId + " " + name + " " + publicationDate;
    }

    //working as of 12/11
    //Static utility to look up all titles in our inventory and return a list if them.
    public static List<Title> getAllTitles() throws java.sql.SQLException, java.io.FileNotFoundException, java.io.IOException {
        //Returns All titles
        Connection conn = DbConnection.accessDbConnection().getConnection();
        Statement stat = conn.createStatement();
        List<Title> t = new ArrayList<>();
        int titleId;
        ResultSet result = stat.executeQuery("SELECT * FROM title");
        while (result.next()) {
            titleId = result.getInt("title_id");
            Title title = new Title(titleId);
            t.add(title);
        }
        return t;
    }

    //working as of 12/11
    // Static utility to lookup a given title using its key and return its object.
    public static Title getOneTitle(int key) throws java.sql.SQLException, java.io.FileNotFoundException, java.io.IOException {
        //Returns: Title object associated with the given db key
        Connection conn = DbConnection.accessDbConnection().getConnection();
        Statement stat = conn.createStatement();
        int titleId = 0;
        Title title = new Title(titleId);
        ResultSet result = stat.executeQuery("SELECT * FROM title WHERE title_id = " + key);
        while (result.next()) {
            titleId = result.getInt("title_id");
            title = new Title(titleId);
        }
        return title;
    }

    //working as of 12/11
    //Static utility to return a list of Titles that are available
    public static List<Title> getAvailableTitles() throws java.sql.SQLException, java.io.FileNotFoundException, java.io.IOException {
        // Returns: all Titles that have at least one available copy
        Connection conn = DbConnection.accessDbConnection().getConnection();
        Statement stat = conn.createStatement();
        List<Title> t = new ArrayList<>();
        int titleId;
        ResultSet result = stat.executeQuery("SELECT * FROM title INNER JOIN copy ON title.title_id = copy.title_id WHERE copy.status = '" +
                        "IN STORE" +"'");
        getAllTitles();
        while (result.next()) {
            titleId = result.getInt("title_id");
            Title titleAvail = new Title(titleId);
            t.add(titleAvail);
       }
            return t;
        }
    }