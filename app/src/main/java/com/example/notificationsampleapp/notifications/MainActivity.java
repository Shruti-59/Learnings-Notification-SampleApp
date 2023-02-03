package com.example.notificationsampleapp.notifications;

import static com.example.notificationsampleapp.MyApplication.CHANEL_1_ID;
import static com.example.notificationsampleapp.MyApplication.CHANEL_2_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;
import android.widget.EditText;

import com.example.notificationsampleapp.model.Message;
import com.example.notificationsampleapp.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle, editTextMessage;
    private MediaSessionCompat mediaSession;
    static List<Message> MESSAGES = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextMessage = findViewById(R.id.edit_text_message);

        mediaSession = new MediaSessionCompat(this, "tag");

        //TimeStamp gets generated automatically
        MESSAGES.add(new Message("Good morning!", "Jim"));
        //Null in sender means its user text which goes in "me" in groupChat
        MESSAGES.add(new Message("Hello", null));
        MESSAGES.add(new Message("Hi!", "Jenny"));

    }

    public void sendOnChannel1(View v) {
        sendChannel1Notification(this);
    }

    public static void sendChannel1Notification(Context context) {
      //  String title = editTextTitle.getText().toString().trim();
      //  String message = editTextMessage.getText().toString().trim();

        //Intent which will later open our activity
        Intent activityIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);

        //Intent for BroadCastReceiver
       /* Intent broadcastIntent = new Intent(context, NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", message);
        PendingIntent actionIntent = PendingIntent.getBroadcast(context, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
*/
        Bitmap picture = BitmapFactory.decodeResource(context.getResources(), R.drawable.dog);

        //Intent for RemoteInput
        androidx.core.app.RemoteInput remoteInput = new RemoteInput.Builder("key_text_reply")
                .setLabel("Your answer...").build();

        Intent replyIntent;
        PendingIntent replyPendingIntent =null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            replyIntent = new Intent(context, DirectReplyReceiver.class);
            replyPendingIntent = PendingIntent.getBroadcast(context,
                    0, replyIntent, 0);
        } else {
            //start chat activity instead (PendingIntent.getActivity)
            //cancel notification with notificationManagerCompat.cancel(id)
        }

        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                R.drawable.ic_reply,
                "Reply",
                replyPendingIntent
        ).addRemoteInput(remoteInput).build();

        NotificationCompat.MessagingStyle messagingStyle =
                new NotificationCompat.MessagingStyle("Me");
        messagingStyle.setConversationTitle("Group Chat");

        for (Message chatMessage : MESSAGES) {
            NotificationCompat.MessagingStyle.Message notificationMessage =
                    new NotificationCompat.MessagingStyle.Message(
                            chatMessage.getText(),
                            chatMessage.getTimestamp(),
                            chatMessage.getSender()
                    );
            messagingStyle.addMessage(notificationMessage);
        }


        Notification notification = new NotificationCompat.Builder(context, CHANEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setStyle(messagingStyle)
                .addAction(replyAction)
                .setColor(Color.BLUE)
                //.setContentTitle(title)
               // .setContentText(message)
               // .setLargeIcon(picture)
                //BIG TEXT StYLE
                /*.setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.long_dummy_text))
                        .setBigContentTitle("Big Content Title")
                        .setSummaryText("Summary text"))*/

                //BIG PICTURE StYLE
               /* .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(picture)
                        .bigLargeIcon(null))*/

                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                //we can add upto 3 of these action buttons
               // .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                .build();

        //notificationManager.notify(1, notification);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, notification);
    }

    public void sendOnChannel2(View v) {
        String title = editTextTitle.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        Bitmap artwork = BitmapFactory.decodeResource(getResources(), R.drawable.daisy);

        Notification notification = new NotificationCompat.Builder(this, CHANEL_2_ID)
                .setSmallIcon(R.drawable.ic_two)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(artwork)
                //INBOX STYLE
                /*.setStyle(new NotificationCompat.InboxStyle()
                        //we can add upto seven lines
                        .addLine("This is line 1")
                        .addLine("This is line 2")
                        .addLine("This is line 3")
                        .addLine("This is line 4")
                        .addLine("This is line 5")
                        .addLine("This is line 6")
                        .addLine("This is line 7")
                        .setBigContentTitle("Big Content Title")
                        .setSummaryText("Summary text"))*/
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1,2,3)
                        .setMediaSession(mediaSession.getSessionToken()))
                .addAction(R.drawable.ic_dislike, "Dislike", null)
                .addAction(R.drawable.ic_previous, "Previous", null)
                .addAction(R.drawable.ic_pause, "Pause", null)
                .addAction(R.drawable.ic_next, "Next", null)
                .addAction(R.drawable.ic_like, "Like", null)

                        //.setMediaSession(mediaSession.getSessionToken()))
                .setSubText("Sub Text")

                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(2, notification);
    }

    public void sendProgressOnChannel2(View v) {

        final int progressMax = 100;

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANEL_2_ID)
                .setSmallIcon(R.drawable.ic_two)
                .setContentTitle("Download")
                .setContentText("Download in Progress")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setProgress(progressMax, 0 , false);

        notificationManager.notify(2, notification.build());

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                for(int progress = 0; progress <= progressMax; progress += 10){
                   notification.setProgress(progressMax, progress, false);
                   notificationManager.notify(2, notification.build());
                    SystemClock.sleep(1000);
                }
                notification.setContentText("Download finished")
                                .setProgress(0,0,false);
                notificationManager.notify(2, notification.build());
            }
        });
    }
}