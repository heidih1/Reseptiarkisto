package reseptit;


import reseptit.Annos;
import java.util.ArrayList;
import java.util.List;



public class RaakaAine {
    Integer id;
    String nimi;
    List<Annos> annokset;
  
    public RaakaAine(Integer id, String nimi){
        this.id = id;
        this.nimi = nimi;       
        this.annokset = new ArrayList<>();
    }
       
    public void setId(){
        this.id = id;
    }
    public Integer getId(){
        return id;
    }
    
    public void setNimi(){
        this.nimi = nimi;
    }
    
    public String getNimi(){
        return nimi;
    }
    
}
