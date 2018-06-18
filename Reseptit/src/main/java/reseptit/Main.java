package reseptit;

import dao.AnnosDao;
import dao.RaakaAineAnnosDao;
import dao.RaakaAineDao;
import java.sql.*;
import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {
    
     public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:reseptiarkisto.db");      
        RaakaAineDao raakaAineDao = new RaakaAineDao(database);
        AnnosDao annosDao = new AnnosDao(database, raakaAineDao);
        RaakaAineAnnosDao raakaAineAnnosDao = new RaakaAineAnnosDao(database);
        
        Spark.get("/jaatelot", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/jaatelot", (req, res) ->{
            Annos annos = new Annos(null, req.queryParams("nimi"));
            annosDao.saveOrUpdate(annos);
            
            res.redirect("/jaatelot");
            return "";
        });
        
        Spark.get("/ainekset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("ainekset", raakaAineDao.findAll());

            return new ModelAndView(map, "ainekset");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/ainekset", (req, res) ->{
            RaakaAine raakaAine = new RaakaAine(null, req.queryParams("nimi"));
            raakaAineDao.saveOrUpdate(raakaAine);
            
            res.redirect("/ainekset");
            return "";
        });
        
        Spark.get("/annokset", (req,res)->{
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());
            map.put("ainekset", raakaAineDao.findAll());
            return new ModelAndView(map, "annokset");
            
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/annokset/:id", (req, res) -> {
            Integer annosId = Integer.parseInt(req.params(":id"));
            Integer raakaAineId = Integer.parseInt(req.queryParams("raakaAineId"));
            
            RaakaAineAnnos raa = new RaakaAineAnnos(-1, raakaAineId, annosId);
            raakaAineAnnosDao.saveOrUpdate(raa);
            res.redirect("/annokset");
            return "";
        });
        
         
     }
    
}
