package sac4java.mydb;

import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnDB;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDB = (Button) findViewById(R.id.btndb);

        btnDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager = new DBManager(getApplicationContext());
                dbManager.open();
                Log.i("DB PATH", String.valueOf(getApplicationContext().getDatabasePath("testdb")));
                Log.i("Environment Dir:", String.valueOf(Environment.getDataDirectory()));

                boolean result= dbManager.insert("Sachin","Cool","Mahaarashtra");
                if(result){
                    Toast.makeText(MainActivity.this, "Data inserted",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(MainActivity.this, "Data Not inserted",Toast.LENGTH_SHORT).show();
                }

                //fetch data

                Cursor cursor = dbManager.fetch();

                if(null != cursor){
                    do {
                        String str = cursor.getString(0)+">>"+cursor.getString(1)+">>"+cursor.getString(2)+"||";
                        Log.i("CURSOR***********",str);
                    } while (cursor.moveToNext());

                }

            }
        });

    }
}
