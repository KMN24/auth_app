package kmn.com;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//Всех пользователей, с которыми мы будем работать внутри приложения,
// мы будем хранить в SharedPreferences. Для этого создадим класс SharedPreferencesHelper.
public class SharedPreferencesHelper {
    public static final String SHARED_PREF_NAME = "SHARED_PREF_NAME";
    public static final String USERS_KEY = "USERS_KEY";
    public static final Type USERS_TYPE = new TypeToken<List<User>>() {
    }.getType();


    private SharedPreferences mSharedPreferences;
    private Gson mGson = new Gson();

    public SharedPreferencesHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    // Нам нужно описать саму логику сохранения пользователей в SharedPreferences.
    // Для этого нам нужно создать два метода — getUsers и addUser. Начнём с метода getUsers.
    // Это public метод, который возвращает нам список юзеров. [ЗВУК] Называться он будет getUsers.
    // Для того чтобы хранить нам список пользователей внутри SharedPreferences, необходимо использовать
    // формат данных JSON, потому что сам SharedPreferences не позволяет сохранять ни Serializable, ни Parcelable объекты,
    // но позволяет сохранять строки, поэтому мы наш список пользователей будем форматировать в JSON и сохранять как строку.
    // Соответственно, чтобы получить список пользователей, нам нужно из JSON превратить её в список пользователей.
    // List <User> users равняется. Тут нам поможет библиотека JSON. Gson, если быть точнее; private Gson mGson = new Gson;
    // mGson.from Json. Тут он у нас попросит строку, из которой необходимо будет создать лист юзеров.
    // Это у нас будет mSharedPreferences.get String. Ему нужно будет передать ключ. Ключ у нас будет USERS_KEY,
    // который мы сейчас с вами создадим. И дефолтное значение параметра.
    public List<User> getUsers() {
        List<User> users = mGson.fromJson(mSharedPreferences.getString(USERS_KEY, ""), USERS_TYPE);
        return users == null ? new ArrayList<User>() : users;
    }

    public boolean addUser(User user) {
        List<User> users = getUsers();
        for (User u : users) {
            if (u.getmLogin().equalsIgnoreCase(user.getmLogin())) { // если данный акк был уже создан
                return false;
            }
        }
        users.add(user); // если данный акк не был создан раньше то тогда добавляем акк
        mSharedPreferences.edit().putString(USERS_KEY, mGson.toJson(users, USERS_TYPE)).apply();
        return true;
    }

    public boolean saveOrOverrideUser(User user) {
        List<User> users = getUsers();
        for (User u : users) {
            if (u.getmLogin().equalsIgnoreCase(user.getmLogin())) {
                users.remove(u);
                break;
            }
        }
        users.add(user);
        mSharedPreferences.edit().putString(USERS_KEY, mGson.toJson(users, USERS_TYPE)).apply();
        return true;
    }

    public List<String> getSuccessLogins() {
        List<String> successLogins = new ArrayList<>();
        List<User> allUsers = getUsers();
        for (User user : allUsers) {
            if (user.hasSuccessLogin()) {
                successLogins.add(user.getmLogin());
            }
        }
        return successLogins;
    }

    public boolean login(User user) {
        List<User> users = getUsers();
        for (User u : users) {
            if (user.getmLogin().equalsIgnoreCase(u.getmLogin())
                    && user.getmPassword().equals(u.getmPassword())) {
                u.setHasSuccessLogin(true);
                mSharedPreferences.edit().putString(USERS_KEY, mGson.toJson(users, USERS_TYPE)).apply();
                return true;
            }
        }
        return false;
    }

}
