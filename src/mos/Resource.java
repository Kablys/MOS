package mos;


import java.util.ArrayList;
import java.util.List;

public class Resource<Type> {
    //descriptor
    int ID;         //ID - išorinis vardas
    String name;
    Process parent; //Tėvas (procesas)
    Boolean message;//Pakartotinio panaudojamumo flag’as (pvz. žinutės = false = vienkartinis)
    List<Type> element = new ArrayList<>();//Elementų sąrašas. Skaidomi resursai padalinti į elementus, kurie gali būti užimti ar laisvi.
    //Šio resurso elementų sąrašas kurių laukia individualūs procesai
    public Resource(String name) {
        this.name = name;
    }
    //Map<Process, List<ResourceElement>>
    //FIXME do we really need this
    //Resurso paskirstytojas

    //primitives
        //Kurti resursą
            //Argumentai:
                //resursą kuriantis procesas
                //resurso išorinis vardas
            //Pridedamas prie bendro resursų sąrašo
            //Pridedamas prie proceso sukurtų resursų sąrašo
        //Naikinti resursą
            //Pašalinamas iš jo tėvo sukurtų resursų sąrašo
            //Atblokuojami procesai, laukiantys šio resurso
            //Pašalinamas iš bendro resursų sąrašo
        //Prašyti resurso
            //Kviečiamas atitinkamo resurso paskirstytojas
        //Atlaisvinti resursą
            //Resurso elementas grąžinamas į resurso deskriptoriuje esantį elementų sąrašą
            //Kviečiamas atitinkamo resurso paskirstytojas
}