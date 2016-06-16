package com.Thoser666.samples.authentication;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import com.Thoser666.beans.PasswordChange;
import com.Thoser666.utils.Crypt;
import com.jain.addon.i18N.component.I18NWindow;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import db.DBConnection;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * UI content when the user is not logged in yet.
 */
public class LoginScreen extends CssLayout {

    private TextField username;
    private PasswordField password;
    private Button login;
    private Button forgotPassword;
    private LoginListener loginListener;
    private AccessControl accessControl;

    public LoginScreen(AccessControl accessControl, LoginListener loginListener) {
        this.loginListener = loginListener;
        this.accessControl = accessControl;

        //Detect, which  Login-Info we need
        readDatabase();

        buildUI();
        username.focus();
    }

    /*
     *check if we have already a user.
     * If not, change info on Loginscreen
     */
    private int readDatabase()
    {
        int i = 0;
        DBConnection conn = null;
        try
        {
            conn = new DBConnection();
            i = Integer.parseInt(conn.customQueryWithResult("SELECT count(*) AS count FROM t666t_user"));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return i;
    }

    private void buildUI() {
        addStyleName("login-screen");

        // login form, centered in the available part of the screen
        Component loginForm = buildLoginForm();

        // layout to center login form when there is sufficient screen space
        // - see the theme for how this is made responsive for various screen
        // sizes
        VerticalLayout centeringLayout = new VerticalLayout();
        centeringLayout.setStyleName("centering-layout");
        centeringLayout.addComponent(loginForm);
        centeringLayout.setComponentAlignment(loginForm,
                Alignment.MIDDLE_CENTER);

        // information text about logging in
        CssLayout loginInformation = buildLoginInformation();

        addComponent(centeringLayout);
        addComponent(loginInformation);
    }

    private Component buildLoginForm() {
        FormLayout loginForm = new FormLayout();

        loginForm.addStyleName("login-form");
        loginForm.setSizeUndefined();
        loginForm.setMargin(false);

        loginForm.addComponent(username = new TextField("Username", "admin"));
        username.setWidth(15, Unit.EM);
        loginForm.addComponent(password = new PasswordField("Password"));
        password.setWidth(15, Unit.EM);
        password.setDescription("Write anything");
        CssLayout buttons = new CssLayout();
        buttons.setStyleName("buttons");
        loginForm.addComponent(buttons);

        buttons.addComponent(login = new Button("Login"));
        login.setDisableOnClick(true);
        login.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    login();
                } finally {
                    login.setEnabled(true);
                }
            }
        });
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        login.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        buttons.addComponent(forgotPassword = new Button("Forgot password?"));
        forgotPassword.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                showNotification(new Notification("Hint: Try anything"));
            }
        });
        forgotPassword.addStyleName(ValoTheme.BUTTON_LINK);
        return loginForm;
    }

    private CssLayout buildLoginInformation() {
        CssLayout loginInformation = new CssLayout();
        loginInformation.setStyleName("login-information");
        Label loginInfoText = new Label(
                "<h1>Login Information</h1>"
                        + "Log in as &quot;admin&quot; to have full access. Log in with your name for access.",
                ContentMode.HTML);
        // If we have only 1 user, change text
        if (this.readDatabase()== 1)
        {
            loginInfoText.setValue("Seems that this is your first start. Login with admin/admin and add a user and change the admin password !");
        }
        loginInformation.addComponent(loginInfoText);
        return loginInformation;
    }

    // check if password is still the unencrypted "admin"
    private void login() {
        DBConnection conn = null;

        try
        {
            conn = new DBConnection();
            String pwd = conn.customQueryWithResult("SELECT password from t666t_user u where u.loginname='admin'");
            if (pwd.equalsIgnoreCase("admin"))
            {
                // enter new Adminpwd
                I18NWindow window = new I18NWindow();
                window.setModal(true);
                VerticalLayout layout = new VerticalLayout();
                window.setContent(layout);
                layout.setWidth("100%");
                layout.setSpacing(true);
                layout.setMargin(true);

                PasswordField oldPwd = new PasswordField("Old password");
                PasswordField newPwd1 = new PasswordField("New password");
                PasswordField newPwd2 = new PasswordField("Repeat New password");

                // Validations
                oldPwd.focus();
                oldPwd.setNullRepresentation("0");
                oldPwd.setNullSettingAllowed(true);
                oldPwd.addValidator(new NullValidator("may not be empty", false));
                oldPwd.setImmediate(true);
//
//
//                newPwd1.setImmediate(true);
//                newPwd1.addValidator(new NullValidator("cannot be empty", false));
//                newPwd1.setNullSettingAllowed(false);
//                newPwd2.setImmediate(true);
//                newPwd2.addValidator(new NullValidator("cannot be empty", false));
//                newPwd2.setNullSettingAllowed(false);

                HorizontalLayout hor = new HorizontalLayout();
                Button save = new Button("Save");
                Button cancel = new Button("Cancel");
                layout.addComponent(oldPwd);
                layout.addComponent(newPwd1);
                layout.addComponent(newPwd2);
                hor.addComponent(save);
                hor.addComponent(cancel);
                layout.addComponent(hor);
                this.getUI().addWindow(window);

                // Clicklistener
                DBConnection finalConn = conn;
                save.addClickListener(new Button.ClickListener()
                {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent)
                    {
                        String tmp1 = oldPwd.getValue();
                        String tmp2 = newPwd1.getValue();
                        String tmp3 = newPwd2.getValue();
                        if (tmp1.length() == 0)
                        {
                            Notification.show("old password field may not be empty", Notification.Type.ERROR_MESSAGE);
                            oldPwd.focus();
                        }
                        else
                        if  (tmp2.length() == 0)
                        {
                            Notification.show("new password field may not be empty", Notification.Type.ERROR_MESSAGE);
                            newPwd1.focus();
                        }
                        else
                        if  (tmp3.length() == 0)
                        {
                            Notification.show("repeat new password field may not be empty", Notification.Type.ERROR_MESSAGE);
                            newPwd2.focus();
                        }
                        else
                        if (!(tmp2.equals(tmp3)))
                        {
                            Notification.show("entered Passwords are not equal", Notification.Type.ERROR_MESSAGE);
                            newPwd1.focus();
                        }
                        else
                        {
                            // encrypt and save to H2
                            Crypt crypt = new Crypt();
                            String erg = null;
                            try
                            {

                                erg = crypt.encrypt(tmp2);
                            } catch (NoSuchPaddingException e)
                            {
                                e.printStackTrace();
                            } catch (NoSuchAlgorithmException e)
                            {
                                e.printStackTrace();
                            } catch (InvalidKeyException e)
                            {
                                e.printStackTrace();
                            } catch (BadPaddingException e)
                            {
                                e.printStackTrace();
                            } catch (IllegalBlockSizeException e)
                            {
                                e.printStackTrace();
                            }

                            String query = new String("UPDATE T666T_USER SET password= '" + erg + "' WHERE loginname = 'admin'");
                            String erg1 = finalConn.customExecuteWithResult(query);
System.out.println("erg= " + erg1);

                        }
                    }
                });

                cancel.addClickListener(new Button.ClickListener()
                {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent)
                    {
                        window.close();
                    }
               });
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
//        if (accessControl.signIn(username.getValue(), password.getValue())) {
//            loginListener.loginSuccessful();
//        } else {
//            showNotification(new Notification("Login failed",
//                    "Please check your username and password and try again.",
//                    Notification.Type.HUMANIZED_MESSAGE));
//            username.focus();
//        }
    }

    private void showNotification(Notification notification) {
        // keep the notification visible a little while after moving the
        // mouse, or until clicked
        notification.setDelayMsec(2000);
        notification.show(Page.getCurrent());
    }

    public interface LoginListener extends Serializable {
        void loginSuccessful();
    }
}
