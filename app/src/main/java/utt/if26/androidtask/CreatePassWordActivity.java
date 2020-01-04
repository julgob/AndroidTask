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
import java.security.SecureRandom;

public class CreatePassWordActivity extends AppCompatActivity {

    TextView field1;
    TextView field2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pass_word);
        this.field1 = findViewById(R.id.newPassword1Field);
        this.field2 = findViewById(R.id.newPassword2Field);
    }

    public void createPassword(View v){

        if(!field1.getText().toString().equals( field2.getText().toString())){
           Toast t =  Toast.makeText(this,"You made an error repeating the password",Toast.LENGTH_LONG);
           t.show();
           //ALSO CHECK PASSWORD ISNOT EMPTY
        }else if(field1.getText().toString().equals("")){
            Toast t =  Toast.makeText(this,"Please enter a password",Toast.LENGTH_LONG);
            t.show();        }
        else {
            SharedPreferences pref = getSharedPreferences("pref",0);
            SharedPreferences.Editor edit = pref.edit();
            edit.putBoolean("passwordIsCreated",true);
            edit.commit();
            MessageDigest messdi;
            try{
               messdi = MessageDigest.getInstance("PBKDF2WithHmacSHA1");
                SecureRandom secureRandom =new SecureRandom();
                byte saltBytes[] = new byte[20];
                secureRandom.nextBytes(saltBytes);
                byte[] passwordByte = Base64.decode(field1.getText().toString(),Base64.NO_WRAP);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
                outputStream.write( passwordByte);
                outputStream.write( saltBytes);
                byte concatBytes[] = outputStream.toByteArray( );
                byte[] hashedPassword = messdi.digest(concatBytes);
                edit.putString("password",Base64.encodeToString(hashedPassword, Base64.NO_WRAP));
                edit.putString("salt",Base64.encodeToString(saltBytes, Base64.NO_WRAP));
                edit.commit();
                Intent it = new Intent(this,AuthenticateActivity.class);
                startActivity(it);
            }catch (NoSuchAlgorithmException e){
                Toast t =  Toast.makeText(this,"Your phone cannot hash the password, you cannot use the app",Toast.LENGTH_LONG);
                t.show();
            }catch (IOException e){

            }

        }
    }
}
