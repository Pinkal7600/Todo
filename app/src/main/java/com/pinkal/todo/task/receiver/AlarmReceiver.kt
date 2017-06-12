package com.pinkal.todo.task.receiver

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.support.v7.app.NotificationCompat
import com.pinkal.todo.R
import com.pinkal.todo.MainActivity
import com.pinkal.todo.utils.toastMessage

/**
 * Created by Pinkal on 12/6/17.
 */
class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val mNotificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//        val taskRowId = intent!!.extras.getInt(Constant.TASK_ID)
//        val taskTitle = intent.extras.getString(Constant.TASK_TITLE)
//        val taskTask = intent.extras.getString(Constant.TASK_TASK)
//
//        val rowId = taskRowId.toString()
        val notificationIntent = Intent(context, MainActivity::class.java)
//        notificationIntent.putExtra(Constant.ROW_ID, rowId)
//        notificationIntent.putExtra(Constant.NOTIFICATION, true)
        notificationIntent.action = "EDIT"
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationFinishIntent = Intent(context, MainActivity::class.java)
//        notificationFinishIntent.putExtra(Constant.ROW_ID, rowId)
//        notificationFinishIntent.putExtra(Constant.NOTIFICATION, true)
        notificationFinishIntent.action = "FINISH"
        val pendingFinishIntent = PendingIntent.getActivity(context, 0, notificationFinishIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val title = "Hello title"//taskTitle//task title
        val message = "Hello message"//taskTask//task
        val icon = R.mipmap.ic_launcher
        val time = System.currentTimeMillis()

        val notification = NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setColor(Color.argb(225, 225, 87, 34))
                .setSmallIcon(icon)
                .setWhen(time)
                .addAction(R.drawable.ic_edit_black_24dp, "Edit", pendingIntent)
                .addAction(R.drawable.ic_check_black_24dp, "Finish", pendingFinishIntent)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build()

        notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL
        notification.defaults = notification.defaults or Notification.DEFAULT_SOUND
        notification.defaults = notification.defaults or Notification.DEFAULT_VIBRATE
        mNotificationManager.notify(Integer.parseInt("1"/*rowId*/), notification)

        var action = intent?.action

        if (action == null) {
            action = intent?.action
        } else {
            if ("EDIT".equals(action)) {
                toastMessage(context, "EDIT")
            } else if ("FINISH".equals(action)) {
                toastMessage(context, "FINISH")
            }
        }
    }
}