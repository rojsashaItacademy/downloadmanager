package ru.trinitydigital.downloadmanager

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.widget.PopupMenu
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import ru.trinitydigital.downloadmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var dm: DownloadManager? = null

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
                Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        registerReceiver(broadcastReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager

        setupListeners()
    }

    private fun setupListeners() {
        binding?.btnLoad?.setOnLongClickListener {
            val popup = PopupMenu(this, it)
            popup.inflate(R.menu.popup)
            popup.show()
            download()
            false
        }
    }

    private fun download() {
        dm?.enqueue(
                DownloadManager.Request(Uri.parse(LINK_VIDEO))
                        .setAllowedNetworkTypes(
                                DownloadManager.Request.NETWORK_MOBILE or
                                        DownloadManager.Request.NETWORK_WIFI
                        )
                        .setTitle("Download File.mp4")
                        .setDescription("This is very important file")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                        .setDestinationInExternalFilesDir(
//                                applicationContext , Environment.DIRECTORY_DOWNLOADS,   "managerDownload24.mp4"
//                        )
                        .setDestinationInExternalPublicDir(
                                Environment.DIRECTORY_DOWNLOADS,
                                "managerDownload24"
                        )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    companion object {
        private const val LINK_VIDEO = "https://images.unsplash.com/photo-1569974507005-6dc61f97fb5c?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MXx8anBnfGVufDB8fDB8&ixlib=rb-1.2.1&auto=format&fit=crop&w=900&q=60"
    }
}


class s : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

    }

}