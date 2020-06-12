package casia.isiteam.api.neo4j.common.manage.parms;

/**
 * ClassName: BasicParms
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/20
 * Email: zhiyou_wang@foxmail.com
 */
public class BasicParms extends Neo4jParms{
    public final static String APPLICATION = "application";
    public final static String CONFIG_APPLICATION = "config/application.properties";

    /**
     * db
     */
    protected final static String DBNAME = "dbname";
    protected final static String BLOT = "blot";
    protected final static String URL = "url";
    protected final static String JDBC_DRIVER= "org.neo4j.jdbc.Driver";
    protected final static String BOLT_COLON_SLASH = "bolt://";
    protected final static String JDBC_BOLT_COLON_SLASH = "jdbc:neo4j:bolt://";
    protected final static String USERNAME = "username";
    protected final static String PASSWORD = "password";
    protected final static String INITIALSIZE = "initialSize";
    protected final static String MAXACTIVE = "maxActive";
    protected final static String MINIDLE = "minIdle";
    protected final static String MAXWAIT = "maxWait";
    protected final static String VALIDATIONQUERY = "validationQuery";
    protected final static String TESTONBORROW = "testOnBorrow";
    protected final static String TESTONRETURN = "testOnReturn";
    protected final static String TESTWHILEIDLE	 = "testWhileIdle";
    protected final static String PROPERTY_PREFIX	 = "druid";

    /**
     * regex
     */
    protected final static String DB_KEY = "^spring.datasource.neo4j.\\S*.\\S*";
    protected final static String IP = "(2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2}";

    /**
     * symbol
     */
    public final static String SLASH_DOT = "\\.";
    public final static String DOT = ".";
    public final static String COMMA = ",";
    public final static String SINGLE_QUOTE = "'";
    public final static String COLON = ":";
    public final static String SEMICOLON = ";";
    public final static String N = "\n";
    public final static String T = "\t";
    public final static String BLANK = " ";
    public final static String LINE = "|";
    public final static String NONE = "";
    public final static String STAR = "*";
    public final static String BREAK_LINE = "~";
    public final static String SLASH = "/";
    public final static String NULL = null;
    public final static String QUESTION = "?";
    public final static String EQUAL = "=";
    public final static String CROSS = "-";
    public final static String CROSS_D = "->";
    public final static String PARENTHESES = "()";
    public final static String LEFT_PARENTHESES = "(";
    public final static String LEFT_BRACES = "{";
    public final static String LEFT_BRACKETS = "[";
    public final static String RIGHT_PARENTHESES = ")";
    public final static String RIGHT_BRACES = "}";
    public final static String RIGHT_BRACKETS = "]";

    public final static String RANGE_PATH_1_6 = "*1..6";
    public final static String RANGE_PATH_1_ = "*1..";

    protected final static String VIRTUALURI = "bolt+routing://127.0.0.1:1";
    protected final static String VALIDATIONQUERYCQL = "match (n) return * limit 1";

}
