package com.example.beautystop.view

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.beautystop.R
import com.google.firebase.auth.FirebaseAuth

lateinit var notificationChannel: NotificationChannel
private val channel_id = "notification"
lateinit var builder: Notification.Builder
private const val description = "notification"
lateinit var notificationManager: NotificationManager

class OrderFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notification()

        val customerRecognition: TextView = view.findViewById(R.id.customer_email_tv)
        var email = FirebaseAuth.getInstance().currentUser!!.email!!
        var index = email.indexOf('@');
        email = email.substring(0, index)

        customerRecognition.text = email


    }

    fun notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channel_id, description, NotificationManager.IMPORTANCE_HIGH)



            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(requireActivity(), channel_id)
                .setSmallIcon(R.drawable.splash)
                .setContentTitle("Your Order is Confirmed!")
                .setContentText("Thank you!")
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.splash))

        } else {
            builder = Notification.Builder(requireActivity())
                .setSmallIcon(R.drawable.splash)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.splash))
        }
        notificationManager.notify(1234, builder.build())
    }

}