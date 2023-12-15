package stu.edu.vn.thigk;

import android.content.Intent;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;

public class SelectImage extends AppCompatActivity {

    public static Intent openFileChooser() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
        }
        return intent;
    }
}
