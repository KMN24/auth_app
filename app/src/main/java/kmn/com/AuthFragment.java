package kmn.com;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

public class AuthFragment extends Fragment {
    private Button mEnter;
    private Button mRegister;
    private AutoCompleteTextView mLogin;
    private EditText mPassword;
    private SharedPreferencesHelper mSharedPreferencesHelper;

    private ArrayAdapter<String> mLoginedUsersAdapter;

    public static AuthFragment newInstance() {

        Bundle args = new Bundle();

        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View.OnClickListener mOnEnterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEmailValid() && isPasswordValid()) {
                User user = mSharedPreferencesHelper.login(
                        mLogin.getText().toString(),
                        mPassword.getText().toString());
                if (user != null) {
                    Intent startProfileIntent = new Intent(getActivity(), ProfileActivity.class);
                    startProfileIntent.putExtra(ProfileActivity.USER_KEY, user);
                    startActivity(startProfileIntent);
                    getActivity().finish();
                } else {
                    showMessage(R.string.login_error);
                }
            } else {
                showMessage(R.string.input_error);
            }
            for (User user : mSharedPreferencesHelper.getUsers()) {
                if (user.getmLogin().equalsIgnoreCase(mLogin.getText().toString())
                        && user.getmPassword().equals(mPassword.getText().toString())) {
                    break;
                }
            }

        }
    };

    private View.OnClickListener mOnRegisterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // todo Обработка нажатия по этой кнопке
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, RegistrationFragment.newInstance())
                    .addToBackStack(RegistrationFragment.class.getName()) // вернемся назад при нажатии кнопки назад
                    .commit();
        }
    };

    private View.OnFocusChangeListener mOnLoginFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                mLogin.showDropDown();
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

        mSharedPreferencesHelper = new SharedPreferencesHelper(getActivity());

        mLogin = v.findViewById(R.id.etLogin);
        mPassword = v.findViewById(R.id.etPassword);
        mEnter = v.findViewById(R.id.buttonEnter);
        mRegister = v.findViewById(R.id.buttonRegister);

        mEnter.setOnClickListener(mOnEnterClickListener);
        mRegister.setOnClickListener(mOnRegisterClickListener);
        mLogin.setOnFocusChangeListener(mOnLoginFocusChangeListener);
        mLoginedUsersAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                mSharedPreferencesHelper.getSuccessLogins()
        );
        mLogin.setAdapter(mLoginedUsersAdapter);
        return v;
    }
}