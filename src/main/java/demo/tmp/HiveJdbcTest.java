package demo.tmp;

import java.sql.*;

public class HiveJdbcTest {
    //
    public static void main(String[] args) throws SQLException {
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Connection con = DriverManager.getConnection("jdbc:hive2://tahdp5:10000/test;initFile=hdfs://mycluster/datacenter/warehouse/upload/renwujie/testHiveJDBCInitFile.sql", "bigdata_etl", "MH79Pw*m");
            //;initFile=hdfs://mycluster/datacenter/warehouse/upload/renwujie/testHiveJDBCInitFile.sql
        Statement stmt = con.createStatement();
        String tableName = "test.test";
        ResultSet resultSet = stmt.executeQuery("select * from " + tableName);
        while(resultSet.next()) {
            System.out.println(resultSet.getString(2));
        }
    }
}
