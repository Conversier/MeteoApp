package ch.supsi.dti.isin.meteoapp.db;

public class DbSchema {
    public static final class DbTable{
        public static final String NAME = "MeteoApp";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String WNAME = "wname";
            public static final String TEMP = "temp";
            public static final String MINTEMP = "mintemp";
            public static final String MAXTEMP = "maxtemp";
            public static final String DESCRIPTION = "description";


        }
    }
}
