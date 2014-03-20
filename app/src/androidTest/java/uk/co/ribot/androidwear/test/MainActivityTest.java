package uk.co.ribot.androidwear.test;

import uk.co.ribot.androidwear.MainActivity;
import uk.co.ribot.androidwear.R;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class uk.co.ribot.androidwear.MainActivityTest \
 * uk.co.ribot.androidwear.tests/android.test.InstrumentationTestRunner
 */
public class MainActivityTest extends BaseTestCase<MainActivity> {
    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void testHelloWorldShown() throws Exception {
        takeScreenshot("main-screen-hello");
        String helloString = getActivity().getString(R.string.hello);
        assertTrue(solo.waitForText(helloString));
    }

    public void testAGoodLuckShown() throws Exception {
        takeScreenshot("main-screen-good-luck");
        String goodLuckString = getActivity().getString(R.string.good_luck);
        assertTrue(solo.waitForText(goodLuckString));
    }
}
