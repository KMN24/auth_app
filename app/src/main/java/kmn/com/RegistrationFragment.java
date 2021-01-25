package kmn.com;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.Nullable;

public class RegistrationFragment extends Fragment {

    private EditText mLogin;
    private EditText mPassword;
    private EditText mPasswordAgain;
    private Button mRegistration;
    private SharedPreferencesHelper mSharedPreferencesHelper;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    private View.OnClickListener mOnRegistrationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isInputValid()) {
                boolean isAdded = mSharedPreferencesHelper.addUser(new User(
                        mLogin.getText().toString(),
                        mPassword.getText().toString()
                ));

                // если пользователь добавился то вернет true, не добавился то вернет false
                if (isAdded) {
                    showMessage(R.string.login_register_success);
                } else {
                    showMessage(R.string.login_register_error);
                }
            } else {
                showMessage(R.string.input_error);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_registration, container, false);

        mSharedPreferencesHelper = new SharedPreferencesHelper(getActivity());

        mLogin = view.findViewById(R.id.etLogin);
        mPassword = view.findViewById(R.id.etPassword);
        mPasswordAgain = view.findViewById(R.id.tvPasswordAgain);
        mRegistration = view.findViewById(R.id.btnRegistration);

        mRegistration.setOnClickListener(mOnRegistrationClickListener);

        return view;
    }

    private boolean isInputValid() {
        String email = mLogin.getText().toString();
        if (isEmailValid(email) && isPasswordsValid()) {
            return true;
        }

        return false;
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordsValid() {
        String password = mPassword.getText().toString();
        String passwordAgain = mPasswordAgain.getText().toString();

        return password.equals(passwordAgain)
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(passwordAgain);
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
    }

}
