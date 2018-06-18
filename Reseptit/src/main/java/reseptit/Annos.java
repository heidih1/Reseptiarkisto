package reseptit;


import java.util.List;


public class Annos {
    Integer id;
    String nimi;
    Integer maara;
    RaakaAine raakaAine;   
    List<RaakaAine> raakaAineet;
       
    
    public Annos(Integer id, String nimi){
        this.id = id;
        this.raakaAine = raakaAine;
        this.nimi = nimi;         
    }
    
    public Integer getId(){
        return id;
    }
    
    public void setId(Integer id){
        this.id = id;
    }
    
    public RaakaAine getRaakaAine(){
        return raakaAine;
    }
    
    public void setRaakaAine(RaakaAine raakaAine){
        this.raakaAine = raakaAine;
    }
    
    public String getNimi(){
        return nimi;
    }
    
    public void setNimi(String nimi){
        this.nimi = nimi;
    }
}
