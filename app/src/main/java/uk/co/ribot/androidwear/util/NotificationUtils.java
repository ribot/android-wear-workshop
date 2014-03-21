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

        builder.addAction(R.drawable.ic_stat_close, context.getString(R.string.close), getCloseActionIntent(context, issue));

        WearableNotifications.Builder wearableBuilder = new WearableNotifications.Builder(builder);
        wearableBuilder.addAction(getCommentAction(context, issue));

        wearableBuilder.addPages(getCommentPages(context, comments));

        Notification notificaion = wearableBuilder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(issue.id, notificaion);
    }

    private static PendingIntent getCloseActionIntent(Context context, Issue issue) {
        Intent closeIntent = GitHubService.getActionCloseIntent(context, issue);
        PendingIntent closePendingIntent = PendingIntent.getService(context, 0, closeIntent, 0);
        return closePendingIntent;
    }

    private static Action getCommentAction(Context context, Issue issue) {
        RemoteInput commentRemoteInput = new RemoteInput.Builder(GitHubService.EXTRA_COMMENT)
                .setLabel("What's your comment")
                .build();

        Intent commentIntent = GitHubService.getActionCommentIntent(context, issue);
        PendingIntent commentPendingIntent = PendingIntent.getService(context, 0, commentIntent, 0);
        
        return new Action.Builder(R.drawable.ic_stat_comment, context.getString(R.string.comment), commentPendingIntent)
                .addRemoteInput(commentRemoteInput)
                .build();
    }

    private static List<Notification> getCommentPages(Context context, List<Comment> comments) {
        ArrayList pages = new ArrayList<Notification>();

        for (Comment comment : comments) {
            BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
            bigTextStyle.setBigContentTitle(comment.user.username)
                    .bigText(comment.body);
            
            Notification notification = new NotificationCompat.Builder(context)
                    .setStyle(bigTextStyle)
                    .build();
            
            pages.add(notification);
        }

        return pages;
    }
}
