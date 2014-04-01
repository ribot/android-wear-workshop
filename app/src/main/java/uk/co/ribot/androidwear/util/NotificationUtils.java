package uk.co.ribot.androidwear.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import uk.co.ribot.androidwear.GitHubService;
import uk.co.ribot.androidwear.R;
import uk.co.ribot.androidwear.model.Comment;
import uk.co.ribot.androidwear.model.Issue;
import android.app.Notification;
import android.preview.support.wearable.notifications.RemoteInput;
import android.preview.support.wearable.notifications.WearableNotifications;
import android.preview.support.wearable.notifications.WearableNotifications.Action;
import java.util.List;
import java.util.ArrayList;
import android.support.v4.app.NotificationCompat.BigTextStyle;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtils {
    public static void notify(final Context context, final Issue issue, final List<Comment> comments) {
        /*
         * Ideally you would create the notification without the large icon, then add it later
         * To keep things simple for the demo we're building the whole notification at once.
         */
        Picasso.with(context)
                .load(issue.user.avatarUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        buildAndNotify(context, issue, bitmap, comments);
                    }

                    @Override
                    public void onBitmapFailed() {
                        buildAndNotify(context, issue, null, comments);
                    }
                });
    }

    public static void dismiss(Context context, Issue issue) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(issue.id);
    }

    private static void buildAndNotify(Context context, Issue issue, Bitmap bigIcon, List<Comment> comments) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_general)
                .setContentTitle(issue.title)
                .setContentText(issue.body);

        if (bigIcon != null) {
            builder.setLargeIcon(bigIcon);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(issue.id, builder.build());
    }
}
