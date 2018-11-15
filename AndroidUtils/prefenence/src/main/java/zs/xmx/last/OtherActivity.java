package zs.xmx.last;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import zs.xmx.R;

public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        String preferenceIntent = getIntent().getStringExtra("intent_key");
        Toast.makeText(this, preferenceIntent, Toast.LENGTH_SHORT).show();
    }
}
