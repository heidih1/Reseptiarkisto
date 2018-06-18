package reseptit;


public class RaakaAineAnnos {

    private Integer id;
    private Integer raakaAineId;
    private Integer annosId;
    private Integer jarjestys;
    private Integer maara;
    private String ohje;

    public RaakaAineAnnos(Integer id, Integer raakaAineId, Integer annosId){
        this.id = id;
        this.raakaAineId = raakaAineId;
        this.annosId = annosId;
    }
    
    public Integer getId(){
        return id;
    }
    
    public Integer getRaakaAineId(){
        return raakaAineId;
    }

    public Integer getAnnosId(){
        return annosId;
    }
    
    public Integer getJarjestys(){
        return jarjestys;
    }
        
    public Integer getMaara(){
        return maara;
    }
        
    public String getOhje(){
        return ohje;
    }
}
