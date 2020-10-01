package com.example.mailboxtracker


import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TabHost
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.show_bluetooth_devices.*
import java.util.*


class ShowBluetoothActivity:AppCompatActivity() {
    private var lstvw: ListView? = null
    private var aAdapter: ArrayAdapter<*>? = null
    private val bAdapter = BluetoothAdapter.getDefaultAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_bluetooth_devices)
        val tabs = findViewById<View>(R.id.tabhost) as TabHost
        tabs.setup()
        var spec = tabs.newTabSpec("tag1")
        spec.setContent(R.id.tab1)
        spec.setIndicator("Info")
        tabs.addTab(spec)
        spec = tabs.newTabSpec("tag2")
        spec.setContent(R.id.tab2)
        spec.setIndicator("Scan")
        tabs.addTab(spec)
        spec = tabs.newTabSpec("tag3")
        spec.setContent(R.id.tab3)
        spec.setIndicator("options")
        tabs.addTab(spec)
        btnGet.setOnClickListener {
            if (bAdapter == null) {
                Toast.makeText(
                    applicationContext,
                    "Bluetooth Not Supported",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                bAdapter.startDiscovery()
                val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
                registerReceiver(mReceiver, filter)
            }
        }

        btnLock.setOnClickListener {
            // ToDo: Here will check passcode with centralized mail system
            this.textViewInvalidCode.isInvisible = false
            if(editTextTextCode.text.toString() == "1234"){
                this.textViewInvalidCode.text="Command Executed Successfully!"
                this.textViewInvalidCode.setTextColor(Color.parseColor("#10FB3D"));
            }else{
                this.textViewInvalidCode.text="Invalid PassCode. Please try again"
                this.textViewInvalidCode.setTextColor(Color.parseColor("#FF3F33"));
            }
        }
    }
    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device = intent
                    .getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                val list = ArrayList<Any>()
                val devicename = device?.name
                val macAddress = device?.address
                list.add("Name: " + devicename + "MAC Address: " + macAddress)
                lstvw = findViewById<View>(R.id.deviceList) as ListView
                aAdapter = ArrayAdapter(
                    applicationContext,
                    R.layout.show_bluetooth_devices,
                    R.id.textView1,
                    list
                )
                lstvw!!.setAdapter(aAdapter)
            }
        }
    }
}