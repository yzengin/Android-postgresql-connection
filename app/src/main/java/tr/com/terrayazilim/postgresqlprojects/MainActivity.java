package tr.com.terrayazilim.postgresqlprojects;

/*
 * Terra Software Information LLC.
 * PostgreSQL Android connect JDBC
 */

import java.io.Reader;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity {

    String phone = " ";
    private String[] ulkeler = {"Sakarya","Trabzon"};
    ListView listemiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqlThread.start();
        TextView yenitext2 = (TextView) findViewById( R.id.textView );

        listemiz=(ListView) findViewById(R.id.listView1);
        ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, android.R.id.text1, ulkeler);
        listemiz.setAdapter(veriAdaptoru);
        System.out.println("Phone Deneme"+phone);
        yenitext2.setText("Phone Deneme"+phone);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    Thread sqlThread = new Thread() {
        public void run() {
            try {
                DB_Settings db = new DB_Settings();
                Class.forName("org.postgresql.Driver");
                String userName = "yakup";
                String pass = "yzengin4642";
                String DbUrl = "jdbc:postgresql://93.89.236.20:5432/testdb";

                Connection conn = DriverManager.getConnection(DbUrl,userName,pass);

                //STEP 4: Execute a query
                System.out.println("Database List"+databaseList());
                System.out.println("SQL Cümleciği oluşturuluyor...");
                Statement st = conn.createStatement();
                String stsql;
                stsql = "SELECT * FROM Terra_App";
                //stsql = "ALTER TABLE Terra_App ALTER COLUMN lat TYPE numeric";
                ResultSet rs = st.executeQuery(stsql);
                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                int colCount = resultSetMetaData.getColumnCount();
                HashMap<Integer, Array> hm = new HashMap<>();
              // System.out.println("Coloum "+col);

               /* for(int i=0;i<colCount;i++){
                    hm.put( i,rs.getArray( i ) );
                }

                Array values = hm.get( 1 );

                System.out.println("Hashmap"+);*/

                //STEP 5: Extract data from result set
                while(rs.next()){
                        String id = rs.getString( "id" );
                        String name = rs.getString( "name" );
                        String surename = rs.getString( "surename" );
                        String password = rs.getString( "password" );
                        phone = rs.getString( "phone" );
                        String lat = rs.getString( "lat" );
                        String lng = rs.getString( "lng" );



                        System.out.print(id);
                        System.out.print("Name = "+name);
                        System.out.println(surename);
                        System.out.print("Password = "+password);
                        System.out.println(phone);
                    System.out.println("LatLong "+lat+" "+lng);

                }

                //STEP 6: Clean-up environment
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException se) {
                System.out.println("Catch1 Error: " + se.toString());
            } catch (ClassNotFoundException e) {
                System.out.println("Catch2 Error: " + e.getMessage());
            }
        }
    };



}