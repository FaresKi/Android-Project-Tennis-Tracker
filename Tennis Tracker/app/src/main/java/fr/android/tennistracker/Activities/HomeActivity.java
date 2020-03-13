package fr.android.tennistracker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import fr.android.tennistracker.R;

public class HomeActivity extends AppCompatActivity {

    Intent intent ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button button =findViewById( R.id.button);
        button.setOnClickListener(view->onClick(view));
    }

    
    public void onClick(View v) {
        intent = new Intent(getApplicationContext(),NewMatchActivity.class);
        this.startActivity(intent);
    }
}
