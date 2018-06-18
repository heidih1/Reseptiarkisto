package dao;


import reseptit.RaakaAine;
import reseptit.Database;
import reseptit.Annos;
import dao.Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AnnosDao implements Dao<Annos, Integer> {
    
    private Database database;
    private Dao<RaakaAine, Integer> raakaAineDao;
    
    public AnnosDao(Database database, Dao<RaakaAine, Integer>raakaAineDao){
        this.database = database;
        this.raakaAineDao = raakaAineDao;
    }
    @Override
    public Annos findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Annos WHERE id = ?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if(!hasOne){
            return null;
        }
        
        Annos a = new Annos(key, rs.getString("nimi"));
        
        rs.close();
        stmt.close();
        connection.close();
        
        return a;   
    }

    @Override
    public List<Annos> findAll() throws SQLException {
        List<Annos> annokset = new ArrayList<>();
        
        try(Connection conn = database.getConnection();
            ResultSet result = conn.prepareStatement("SELECT id, name FROM Annos").executeQuery()){
                while(result.next()){

                    annokset.add(new Annos(result.getInt("id"), result.getString("nimi")));
                }
        }
        
        return annokset;
        
    }

    @Override
    public Annos saveOrUpdate(Annos object) throws SQLException {
        
        Annos byName = findByName(object.getNimi());

        if (byName != null) {
            return byName;
        }

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Annos (id, raakaAine, nimi) VALUES (?, ?, ?)");
            stmt.setInt(1, object.getId());
            stmt.setObject(2, object.getRaakaAine());
            stmt.setString(3, object.getNimi());
            
            
            stmt.executeUpdate();
         
        }      
        return findByName(object.getNimi());
    }
    
    
    private Annos findByName(String nimi) throws SQLException {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT*FROM Annos WHERE nimi = ?");
            stmt.setString(1, nimi);

            ResultSet result = stmt.executeQuery();

            if (!result.next()) {
                return null;
            }
            

            return new Annos(result.getInt("id"), result.getString("nimi"));
        }
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
       Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Annos WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }


    
}
