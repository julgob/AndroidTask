package utt.if26.androidtask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthenticateActivity extends AppCompatActivity {

    TextView passwordField ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
        passwordField = findViewById(R.id.passwordField);
        SharedPreferences pref = getSharedPreferences("pref",0);
        if(!pref.getBoolean("passwordIsCreated",false)){
            Intent intent = new Intent(this,CreatePassWordActivity.class);
            startActivity(intent);
        }
    }

    public void auth(View v){
        SharedPreferences pref = getSharedPreferences("pref",0);
        String saltString = pref.getString("salt","michel");
        byte[] saltBytes = Base64.decode(saltString,Base64.NO_WRAP);

        String hashedPasswordString = pref.getString("password","slfd hfldfh dhld fdjf hdljf d");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        try{
            byte[] tme = Base64.encode(passwordField.getText().toString().getBytes(),Base64.NO_WRAP);
            String tempS = new String(tme);
            byte[] passwordByte = Base64.decode(tempS,Base64.NO_WRAP);
            outputStream.write( passwordByte);
            outputStream.write( saltBytes);
            byte concatBytes[] = outputStream.toByteArray( );
            MessageDigest messdi = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = messdi.digest(concatBytes);

            String finalString = Base64.encodeToString(hashedPassword,Base64.NO_WRAP);
            if(finalString.equals(hashedPasswordString)){
                Intent it =new Intent(this,NavigationDrawerActivity.class);
                startActivity(it);
            }else {
                Toast t = Toast.makeText(this,"Wrong PassWord",Toast.LENGTH_LONG);
                t.show();
            }
        }catch (IOException e){}
        catch (NullPointerException e){
            Toast t = Toast.makeText(this,"Wrong PassWord",Toast.LENGTH_LONG);
            t.show();
        }catch (NoSuchAlgorithmException e){
            Toast t = Toast.makeText(this,"Cant create hash",Toast.LENGTH_LONG);
            t.show();        }
    }
}
