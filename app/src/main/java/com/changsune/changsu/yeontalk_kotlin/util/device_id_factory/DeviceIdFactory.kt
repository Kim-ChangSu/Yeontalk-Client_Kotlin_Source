package com.changsune.changsu.yeontalk_kotlin.util.device_id_factory

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.telephony.TelephonyManager

class DeviceIdFactory(context: Context) {

    var deviceId: String = ""

    companion object {

        val PREFS_DEVICE_ID = "device_id"
        val PREFS_FILE = "device_id.xml"
    }

    init {

        if (deviceId == "") {
            synchronized(DeviceIdFactory::class.java) {
                if (deviceId == "") {
                    val prefs = context.getSharedPreferences(PREFS_FILE, 0)
                    deviceId = prefs.getString(PREFS_DEVICE_ID, "")
                    if (deviceId == "") {
                        val androidId = Settings.Secure.getString(context.contentResolver, "android_id")
                        if ("9774d56d682e549c" != androidId) {
                            deviceId = androidId
                        } else if (ActivityCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE") != 0) {

                        } else {
                            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                            if (Build.VERSION.SDK_INT >= 26) {
                                deviceId = tm.imei
                            } else {
                                deviceId = tm.deviceId
                            }
                        }
                        prefs.edit().putString(PREFS_DEVICE_ID, deviceId!!.toString()).commit()
                    }
                }
            }
        }
    }

}
