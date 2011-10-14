package avermedia.com.ipd1;
 
import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 

public class TestContentProviderActivity extends Activity {
  
 EditText inputName, inputNumber;
 Button buttonAdd;
  
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
        inputName = (EditText)findViewById(R.id.name);
        inputNumber = (EditText)findViewById(R.id.number);
         
        inputName.setText("Coding");
        inputNumber.setText("12345678");
         
     buttonAdd = (Button)findViewById(R.id.add);
      
     buttonAdd.setOnClickListener(new Button.OnClickListener(){
 
   public void onClick(View arg0) {
    // TODO Auto-generated method stub
     
    String contactName = inputName.getText().toString();
    String contactNumber = inputNumber.getText().toString();
     
    ContentValues contentValues = new ContentValues();
    contentValues.put(People.NAME, contactName);
    contentValues.put(People.STARRED, 1);
    Uri newContactUri = getContentResolver().insert(People.CONTENT_URI, contentValues);
     
    Uri phoneUri = Uri.withAppendedPath(newContactUri, People.Phones.CONTENT_DIRECTORY);
    contentValues.clear();
    contentValues.put(People.Phones.TYPE, People.TYPE_MOBILE);
    contentValues.put(People.NUMBER, contactNumber);
     
    getContentResolver().insert(phoneUri, contentValues);
     
    Toast.makeText(TestContentProviderActivity.this, 
      contactName + " : " + contactNumber, 
      Toast.LENGTH_LONG).show();
   }});
    }
}