package io.founder.co.mem.moh.mobileymeme;


import android.app.Activity;
import android.content.Context;

import io.founder.co.mem.moh.mobileymeme.Class.User;

public class LoginContract {

    public interface View {
        void ShowProgressBar();

        void ShowMessage(String message);

        void hideProgressBar();

    }

    public interface Presenter {
        void Login(String username, String password);

        void LoginResponse(User user);
        void goRegister(Context activity);

    }

    public interface Model {
        void Login(String username, String password);
        void goRegister();

    }
}
