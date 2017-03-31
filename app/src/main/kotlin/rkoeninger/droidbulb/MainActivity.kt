@file:Suppress("DEPRECATION")

package rkoeninger.droidbulb

import android.app.Activity
import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.Camera.Parameters
import android.os.Bundle
import android.view.View
import android.widget.ImageButton

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var camRef: Camera? = null

    override fun onDestroy() {
        super.onDestroy()
        camRef?.let {
            it.parameters.flashMode = Parameters.FLASH_MODE_AUTO
            it.stopPreview()
        }
        camRef = null
    }

    fun onLightToggle(@Suppress("UNUSED_PARAMETER") view: View) {
        val button = findViewById(R.id.toggleButton) as ImageButton

        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            if (camRef == null) {
                val camera = Camera.open()
                camera.parameters.flashMode = Parameters.FLASH_MODE_TORCH
                camera.startPreview()
                camRef = camera
                setButtonIcon(true, button)
            } else {
                camRef?.parameters?.flashMode = Parameters.FLASH_MODE_AUTO
                camRef?.stopPreview()
                camRef = null
                setButtonIcon(false, button)
            }
        }
    }

    fun setButtonIcon(on: Boolean, button: ImageButton) {
        val iconId = if (on) R.drawable.ic_droidbulb else R.drawable.ic_droidbulboff
        button.setImageDrawable(resources.getDrawable(iconId))
    }
}
