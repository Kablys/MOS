package mos;

import java.util.List;

//Vykdomas procesas tampa:
    //Blokuotu, paprašius resurso kurio jis nesulaukia
    //Pasiruošusiu, praradus procesorių (ne dėl kito resurso trūkumo priežasties)
//Blokuotas procesas tampa:
    //Pasiruošusiu, kai jam suteikiamas reikalingas resursas
    //Blokuotu-sustabdytu, jei einamasis procesas jį sustabdo
//Pasiruošęs procesas tampa:
    //Vykdomu, kai gaunamas procesoriaus resursas
    //Pasiruošusiu-sustabdytu, kai einamasis procesas jį sustabdo
//Blokuotas-sustabdytas tampa:
    //Blokuotu, jei einamasis procesas aktyvuoja procesą
    //Pasiruošusiu-sustabdytu,  jei procesui suteikiamas resursas dėl kurio jis užsiblokavo
//Pasiruošęs-sustabdytas tampa:
    //Pasiruošusiu, jei einamasis procesas aktyvuoja procesą
enum ProcState{
    READY, ONGOING, BLOCKED, READY_STOPPED, BLOCKED_STOPPED
}

public class Process {
    //descriptor
    int ID;         //ID - išorinis vardas
    Process parent; //Tėvas (procesas)
    ProcState state;//Būsena
    //Procesoriaus būsena
        //Režimas (SUPER/USER)
        //Registrų reikšmės (TMP, IC, C)
    // FIXME Shouldn't be recursive list
    List<Process> children;     //Vaikų (procesų) sąrašas
    List<Resource> createdRes;  //Sukurtų resursų sąrašas
    List<Resource> receivedRes; //Pasiekiamų (gautų) resursų sąrašas
    // TODO think about enum or other structure
    int priotity;//Prioritetas

    //Kurti procesą
        //Argumentai:
            //Tėvinis procesas
            //Pradinė procesoriaus būsena
            //Prioritetas
            //Perduodamų elementų sąrašas
            //Išorinis vardas.
        //Iš argumentų sukuriamas proceso deskriptorius
        //Procesas pridedamas prie bendro procesų sąrašo
        //Procesas pridedamas prie tėvinio proceso vaikų sąrašo
    //Naikinti procesą
        //Rekursyviai naikinami proceso sukurti resursai ir vaikai
        //Pašalinamas iš tėvinio proceso vaikų sąrašo
        //Atlaisvinami visi gauti resursai
        //Procesas pašalinamas iš bendro procesų sąrašo
        //Jei sunaikinto proceso būsena buvo vykdomas - kviečiamas planuotojas
    //Stabdyti procesą
        //Proceso būsena keičiama iš blokuotas į blokuotas-sustabdytas arba iš pasiruošęs į pasiruošęs-sustabdytas.
    //Aktyvuoti procesą
        //Proceso būsena keičiama iš blokuotas-sustabdytas į blokuotas arba iš pasiruošęs-sustabdytas į pasiruošęs.
        //Kviečiamas planuotojas
}