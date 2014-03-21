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

    public static void dismiss(Context context, Issue issue) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(issue.id);
    }

    private static void buildAndNotify(Context context, Issue issue, Bitmap bigIcon) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_general)
                .setContentTitle(issue.title)
                .setContentText(issue.body);

        if (bigIcon != null) {
            builder.setLargeIcon(bigIcon);
        }

        Intent closeIntent = GitHubService.getActionCloseIntent(context, issue);
        PendingIntent closePendingIntent = PendingIntent.getService(context, 0, closeIntent, 0);
        builder.addAction(R.drawable.ic_stat_close, context.getString(R.string.close), closePendingIntent);

        

        RemoteInput commentRemoteInput = new RemoteInput.Builder(GitHubService.EXTRA_COMMENT)
                .setLabel("What's your comment")
                .build();

        Intent commentIntent = GitHubService.getActionCommentIntent(context, issue);
        
        PendingIntent commentPendingIntent = PendingIntent.getService(context, 0, commentIntent, 0);
        
        Action commentAction = new Action.Builder(R.drawable.ic_stat_comment, context.getString(R.string.comment), commentPendingIntent)
                .addRemoteInput(commentRemoteInput)
                .build();

        Notification notificaion = new WearableNotifications.Builder(builder).addAction(commentAction).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(issue.id, notificaion);
    }
}
