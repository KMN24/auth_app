package kmn.com;

import java.io.Serializable;

public class User implements Serializable {
    private String mLogin;
    private String mPassword;
    private String mPhotoUri;
    private boolean mHasSuccessLogin;

    public User(String mLogin, String mPassword) {
        this.mLogin = mLogin;
        this.mPassword = mPassword;
    }

    public String getmLogin() {
        return mLogin;
    }

    public void setmLogin(String mLogin) {
        this.mLogin = mLogin;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getPhotoUri() {
        return mPhotoUri;
    }

    public void setPhotoUri(String photoUri) {
        mPhotoUri = photoUri;
    }

    public boolean hasSuccessLogin() {
        return mHasSuccessLogin;
    }

    public void setHasSuccessLogin(boolean hasSuccessLogin) {
        mHasSuccessLogin = hasSuccessLogin;
    }
}

