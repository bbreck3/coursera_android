package graphicslab.example.com.lab7_graphicslab;

import graphicslab.example.com.lab7_graphicslab.BubbleActivity;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

public class BubbleActivityFling extends
		ActivityInstrumentationTestCase2<BubbleActivity> {
	private Solo solo;

	public BubbleActivityFling() {
		super(BubbleActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		getInstrumentation().runOnMainSync(new Runnable() {
			@Override
			public void run() {
				getActivity().getWindow().addFlags(
						WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
			}
		});
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testRun() {

		int delay = 2000;

		// Wait for activity: 'course.labs.TouchLab.BubbleActivity'
		solo.waitForActivity(BubbleActivity.class,
				delay);

		solo.clickOnActionBarItem(graphicslab.example.com.lab7_graphicslab.R.id.menu_still_mode);

		solo.sleep(delay);

		// Click to create a bubble
		solo.clickOnScreen(100, 100);

		solo.sleep(delay);

		// Assert that a bubble was displayed
		assertEquals(
				"Bubble hasn't appeared",
				1,
				solo.getCurrentViews(
						graphicslab.example.com.lab7_graphicslab.BubbleActivity.BubbleView.class)
						.size());

		// Fling the bubble
		solo.drag(100, 500, 100, 500, 3);

		// Give bubble time to leave screen
		solo.sleep(delay);

		// Assert that the bubble has left the screen
		assertEquals(
				"Bubble hasn't left the screen",
				0,
				solo.getCurrentViews(
						graphicslab.example.com.lab7_graphicslab.BubbleActivity.BubbleView.class)
						.size());
	}
}
