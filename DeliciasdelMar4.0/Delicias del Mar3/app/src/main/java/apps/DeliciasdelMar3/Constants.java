package apps.DeliciasdelMar3;



public class Constants {
    // nombre base de datos
    public static final String DB_NAME = "EUSEBIO_DB";
    //version de base de datos
    public static final int DB_VERSION = 1;
    //nombre de la tabla
    public static final String TABLE_NAME = "TABLA_EUSEBIO";
    //columnas/campos de la tabla
    public static final String C_ID = "ID";
    public static final String C_NAME = "NAME";
    public static  final String C_IMAGE = "IMAGE";
    public static final String C_BIO = "BIO";
    public static final String C_PRICE = "PRICE";


    //Crea la tabla Query
    public static  final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +" ("
            + C_ID+ " INTEGER PRIMARY KEY,"
            + C_NAME+ " TEXT,"
            + C_IMAGE+ " TEXT,"
            + C_BIO+ " TEXT,"
            + C_PRICE + " TEXT"
            + ")";
    //string que inserta datos para que se precarguen a la app al iniciar por primera vez
    public static final String INSERT_INTO = "INSERT INTO "+ TABLE_NAME +"(ID, NAME, IMAGE, BIO, PRICE)" +
            "VALUES " +
            "('1', 'Arroz con Mariscos','arroz', 'Arroz con delicias marinas con camar6n, caracol, calamar, trozos de pescado, langostino y vegetales.', '$ 40.000'),"+
            "('2', 'Mojarra Frita','mojarra', 'Mojarra bien frita acompañado de Patacones y Arroz con coco.', '$ 30.000'),"+
            "('3', 'Cazuela de Mariscos','cazuela', 'Típica sopa cartagenera, extracto de coco, camarón, caracol, calamar, pescado y langostino. Acompañado con arroz con coco.', '$ 50.000'),"+
            "('4', 'Pulpo en Salsa','pulpo', 'Delicioso Pulpo Bañado en salsa golf.', '$ 40.000');";


}
