package dao;

import reseptit.RaakaAineAnnos;
import reseptit.Database;
import dao.Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



public class RaakaAineAnnosDao implements Dao<RaakaAineAnnos, Integer> {
    private Database database;

    public RaakaAineAnnosDao(Database database){
        this.database = database;
    }
    
    @Override
    public RaakaAineAnnos findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Annos FROM RaakaAineAnnos WHERE id = ?");
        stmt.setObject(1, key);
        
        ResultSet result = stmt.executeQuery();
        boolean hasOne = result.next();
        if(!hasOne){
            return null;
        }
        
        RaakaAineAnnos r = new RaakaAineAnnos(result.getInt("id"), result.getInt("raakaAineId"), result.getInt("annosId"));
        
        result.close();
        stmt.close();
        connection.close();
        
        return r;  
    }
    

    @Override
    public List<RaakaAineAnnos> findAll() throws SQLException {
        List<RaakaAineAnnos> raakaAineet = new ArrayList<>();
        
        try(Connection conn = database.getConnection();
            ResultSet result = conn.prepareStatement("SELECT id, name FROM RaakaAine").executeQuery()){
                while(result.next()){
                    raakaAineet.add(new RaakaAineAnnos(result.getInt("id"), result.getInt("raakaAineId"), result.getInt("annosId")));
                }
        }      
        return raakaAineet;}

    @Override
    public RaakaAineAnnos saveOrUpdate(RaakaAineAnnos object) throws SQLException {
            try (Connection conn = database.getConnection()) {

                PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaAineAnnos (id, raakaAineId, annosId) VALUES (?, ?, ?)");

                stmt.setInt(1, object.getId());
                stmt.setInt(2, object.getRaakaAineId());                
                stmt.setInt(3, object.getAnnosId());

                stmt.executeUpdate();
        }
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
       Connection conn = database.getConnection();
       PreparedStatement stmt = conn.prepareStatement("DELETE FROM RaakaAineAnnos WHERE id = ?");

       stmt.setInt(1, key);
       stmt.executeUpdate();

       stmt.close();
       conn.close();
    }
    
}
