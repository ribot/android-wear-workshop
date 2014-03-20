package uk.co.ribot.androidwear.util;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import uk.co.ribot.androidwear.R;
import uk.co.ribot.androidwear.model.Issue;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtils {
    public static void notify(final Context context, final Issue issue) {
        /*
         * Ideally you would create the notification without the large icon, then add it later
         * To keep things simple for the demo we're building the whole notification at once.
         */
        Picasso.with(context)
                .load(issue.user.avatarUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        buildAndNotify(context, issue, bitmap);
                    }

                    @Override
                    public void onBitmapFailed() {
                        buildAndNotify(context, issue, null);
                    }
                });
    }

    private static void buildAndNotify(Context context, Issue issue, Bitmap bigIcon) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(issue.title)
                .setContentText(issue.body);

        if (bigIcon != null) {
            builder.setLargeIcon(bigIcon);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(issue.id, builder.build());
    }
}