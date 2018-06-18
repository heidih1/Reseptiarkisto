package dao;


import reseptit.RaakaAine;
import reseptit.Database;
import dao.Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class RaakaAineDao implements Dao<RaakaAine, Integer> {
    private Database database;

    
    public RaakaAineDao(Database database){
        this.database = database;
    }
    
    @Override
    public RaakaAine findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM RaakaAine WHERE name = ?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if(!hasOne){
            return null;
        }
        
        RaakaAine r = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
        
        rs.close();
        stmt.close();
        connection.close();
        
        return r;   
    }

    @Override
    public List<RaakaAine> findAll() throws SQLException {
        List<RaakaAine> raakaAineet = new ArrayList<>();
        
        try(Connection conn = database.getConnection();
            ResultSet result = conn.prepareStatement("SELECT id, name FROM RaakaAine").executeQuery()){
                while(result.next()){
                    raakaAineet.add(new RaakaAine(result.getInt("id"), result.getString("nimi")));
                }
        }      
        return raakaAineet;

    }

    @Override
    public RaakaAine saveOrUpdate(RaakaAine object) throws SQLException {
        RaakaAine byName = findByName(object.getNimi());

        if (byName != null) {
            return byName;
        }

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaAine (id, nimi) VALUES (?,?)");
            stmt.setInt(1, object.getId());
            stmt.setString(2, object.getNimi());
            stmt.executeUpdate();
        }      
        return findByName(object.getNimi());
    }
    
    private RaakaAine findByName(String nimi) throws SQLException {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, name FROM Annos WHERE nimi = ?");
            stmt.setString(1, nimi);

            ResultSet result = stmt.executeQuery();

            if (!result.next()) {
                return null;
            }

            return new RaakaAine(result.getInt("id"), result.getString("nimi"));
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM RaakaAine WHERE name = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    
}
