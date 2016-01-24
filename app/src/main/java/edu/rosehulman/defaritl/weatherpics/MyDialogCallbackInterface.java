package edu.rosehulman.defaritl.weatherpics;

/**
 * Created by defaritl on 1/23/2016.
 */
public interface MyDialogCallbackInterface {

    public void callbackAddButton(String caption, String url);

    public void callbackDeleteButton(String key);

    public void callbackEditButton(Weatherpic weatherpic);

}
