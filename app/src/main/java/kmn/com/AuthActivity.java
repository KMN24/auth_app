package kmn.com;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AuthActivity extends AppCompatActivity {
    private Button mEnter;
    private Button mRegister;
    private EditText mLogin;
    private EditText mPassword;

    private View.OnClickListener mOnRegisterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // todo Обработка нажатия по этой кнопке
        }
    };

    private View.OnClickListener mOnEnterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // todo Обработка нажатия по этой кнопке
            if (isEmailValid() && isPasswordValid()) {
                // вход в приложение если данные верны
                Intent startProfileIntent = new Intent(AuthActivity.this, ProfileActivity.class );
                startProfileIntent.putExtra(ProfileActivity.EMAIL_KEY, mLogin.getText().toString());
                startProfileIntent.putExtra(ProfileActivity.PASSWORD_KEY, mPassword.getText().toString());

                startActivity(startProfileIntent);
            } else {
                showMessage(R.string.login_input_error);
            }
        }
    };

    private boolean isEmailValid() {
        return !TextUtils.isEmpty(mLogin.getText())
                && Patterns.EMAIL_ADDRESS.matcher(mLogin.getText()).matches();
        //И также добавим корректность ввода самого email. Для этого используем Patterns.
        // EMAIL_ADDRESS.matcher и передадим в него тот же текст из логина. И вызовем метод matches.
    }

    private boolean isPasswordValid() {
        return !TextUtils.isEmpty(mPassword.getText());
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Bundle обертка над массивом данных
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_auth);

        mLogin = findViewById(R.id.etLogin);
        mPassword = findViewById(R.id.etPassword);
        mEnter = findViewById(R.id.buttonEnter);
        mRegister = findViewById(R.id.buttonRegister);

        mEnter.setOnClickListener(mOnEnterClickListener);
        mRegister.setOnClickListener(mOnRegisterClickListener);
    }
}