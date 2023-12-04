package com.martink.downloadanddbfill;

import org.apache.log4j.Logger;

public final class Constants {

    private Constants() {
    }

    //logger
    public static final Logger LOGGER = Logger.getLogger("");

    //tagNames
    public static final String TAG_NAME_MUNICIPALITY = "vf:Obec";
    public static final String TAG_NAME_MUNICIPALITY_SECTION = "vf:CastObce";

    //tables
    public static final String TABLE_MUNICIPALITY = "Obec";
    public static final String TABLE_MUNICIPALITY_SECTION = "Cast_Obce";

    //columns
    public static final String MUNICIPALITY_NAME = "NazevObce";
    public static final String MUNICIPALITY_CODE = "KodObce";
    public static final String MUNICIPALITY_SECTION_NAME = "NazevCastiObce";
    public static final String MUNICIPALITY_SECTION_CODE = "KodCastiObce";

    //elements
    public static final String ELEMENT_MUNICIPALITY_CODE = "obi:Kod";
    public static final String ELEMENT_MUNICIPALITY_NAME = "obi:Nazev";
    public static final String ELEMENT_MUNICIPALITY_SECTION_NAME = "coi:Nazev";
    public static final String ELEMENT_MUNICIPALITY_SECTION_CODE = "coi:Kod";

}

