package kmn.com;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthFragment extends Fragment {
    private Button mEnter;
    private Button mRegister;
    private EditText mLogin;
    private EditText mPassword;

    public static AuthFragment newInstance() {

        Bundle args = new Bundle();

        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
                Intent startProfileIntent = new Intent(getActivity(), ProfileActivity.class);
                startProfileIntent.putExtra(ProfileActivity.USER_KEY, new User(mLogin.getText().toString(), mPassword.getText().toString()));


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
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fr_auth, container, false);

        mLogin = v.findViewById(R.id.etLogin);
        mPassword = v.findViewById(R.id.etPassword);
        mEnter = v.findViewById(R.id.buttonEnter);
        mRegister = v.findViewById(R.id.buttonRegister);

        mEnter.setOnClickListener(mOnEnterClickListener);
        mRegister.setOnClickListener(mOnRegisterClickListener);

        return v;
    }
}