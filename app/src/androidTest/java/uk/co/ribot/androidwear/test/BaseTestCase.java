package uk.co.ribot.androidwear.test;

import android.test.ActivityInstrumentationTestCase2;
import android.app.Activity;

import com.robotium.solo.Solo;
import com.squareup.spoon.Spoon;

public abstract class BaseTestCase<T extends Activity> extends ActivityInstrumentationTestCase2<T> {
    public Solo solo;

    public BaseTestCase(Class cls) {
        super(cls);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        try {
            solo.finishOpenedActivities();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        super.tearDown();
    }

    public void takeScreenshot(String tag) {
        Spoon.screenshot(solo.getCurrentActivity(), tag);
    }
}
