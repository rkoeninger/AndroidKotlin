package rkoeninger.droidbulb

import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.Camera.Parameters
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    var camRef: MutableList<Camera> = mutableListOf()
    var flashMode = Parameters.FLASH_MODE_AUTO

    fun onLightToggle(@Suppress("UNUSED_PARAMETER") view: View) {
        val butt = findViewById(R.id.toggleButton) as Button
        butt.text = if (butt.text == "On") "Off" else "On"
        val flashAvailable = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

        if (flashAvailable) {
            val cam =
                if (camRef.size > 0) {
                    camRef[0]
                } else {
                    val c = Camera.open()
                    camRef.add(c)
                    c
                }

            if (cam == null) {
                return
            }

            val p = cam.parameters

            if (p.flashMode == Parameters.FLASH_MODE_TORCH) {
                p.flashMode = Parameters.FLASH_MODE_AUTO
                cam.parameters = p
                cam.stopPreview()
            } else {
                p.flashMode = Parameters.FLASH_MODE_TORCH
                cam.parameters = p
                cam.startPreview()
            }
        }
    }
}
