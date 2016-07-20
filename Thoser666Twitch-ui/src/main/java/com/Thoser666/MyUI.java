package com.Thoser666;

import javax.servlet.annotation.WebServlet;

import com.Thoser666.samples.MainScreen;
import com.Thoser666.samples.authentication.AccessControl;
import com.Thoser666.samples.authentication.BasicAccessControl;
import com.Thoser666.samples.authentication.LoginScreen;
import com.Thoser666.samples.authentication.LoginScreen.LoginListener;

import com.Thoser666.utils.T666TI18NResourceProvider;
import com.jain.addon.i18N.component.I18NUI;
import com.jain.addon.resource.DefaultI18NResourceProvider;
import com.jain.addon.resource.I18NProvider;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Main UI class of the application that shows either the login screen or the
 * main view of the application depending on whether a user is signed in.
 *
 * The @Viewport annotation configures the viewport meta tags appropriately on
 * mobile devices. Instead of device based scaling (default), using responsive
 * layouts.
 */
@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("mytheme")
@Widgetset("com.Thoser666.MyAppWidgetset")
public class MyUI extends I18NUI
{

    private AccessControl accessControl = new BasicAccessControl();

    public I18NProvider getProvider()
    {
        return provider;
    }

    public void setProvider(I18NProvider provider)
    {
        this.provider = provider;
    }

    I18NProvider provider = this.getI18nProvider();




    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("Thoser666Twitch");
        if (!accessControl.isUserSignedIn())
        {
            provider = T666TI18NResourceProvider.instance();
            setContent(new LoginScreen(accessControl, provider, vaadinRequest.getLocale(),  new LoginListener()
            {
                @Override
                public void loginSuccessful()
                {
                    showMainView();
                }
            }));
        } else {
            showMainView();
        }
    }

    @Override
    protected void initialize(VaadinRequest vaadinRequest)
    {

    }

    protected void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(new MainScreen(MyUI.this));
        getNavigator().navigateTo(getNavigator().getState());
    }

    public static MyUI get() {
        return (MyUI) UI.getCurrent();
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    // my own provider
    public  I18NProvider getI18nProvider() {
        return T666TI18NResourceProvider.instance();
    }
}
