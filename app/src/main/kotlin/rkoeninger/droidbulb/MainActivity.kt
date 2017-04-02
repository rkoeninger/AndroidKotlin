@file:Suppress("DEPRECATION")

package rkoeninger.droidbulb

import android.app.Activity
import android.content.pm.PackageManager.FEATURE_CAMERA_FLASH
import android.hardware.Camera
import android.hardware.Camera.Parameters.FLASH_MODE_AUTO
import android.hardware.Camera.Parameters.FLASH_MODE_TORCH
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import rkoeninger.droidbulb.R.drawable.ic_droidbulb
import rkoeninger.droidbulb.R.drawable.ic_droidbulboff
import rkoeninger.droidbulb.R.id.toggleButton
import rkoeninger.droidbulb.R.layout.activity_main

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
    }

    override fun onDestroy() {
        setLightOn(false)
        super.onDestroy()
    }

    var camRef: Camera? = null

    fun onLightToggle(@Suppress("UNUSED_PARAMETER") view: View) {
        setLightOn(camRef == null)
    }

    fun setLightOn(on: Boolean) {
        val button = findViewById(toggleButton) as ImageButton

        if (on and packageManager.hasSystemFeature(FEATURE_CAMERA_FLASH)) {
            val camera = Camera.open()
            camera.parameters.flashMode = FLASH_MODE_TORCH
            camera.startPreview()
            button.setImageDrawable(resources.getDrawable(ic_droidbulb))
            camRef = camera
        } else {
            camRef?.parameters?.flashMode = FLASH_MODE_AUTO
            camRef?.stopPreview()
            button.setImageDrawable(resources.getDrawable(ic_droidbulboff))
            camRef = null
        }
    }
}
