package com.example.bort.androidkotlin

import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.Camera.Parameters
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
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
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
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

    fun onLightToggle(view: View) {
        val butt = findViewById(R.id.toggleButton) as Button
        butt.text = if (butt.text == "On") "Off" else "On"
        val tv = findViewById(R.id.textView) as TextView
        val flashAvailable = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
        tv.text = if (flashAvailable) "Flash Available" else "FLASH NOT FOUND"
        if (flashAvailable) {
            val cam = if (camRef.size > 0) {
                camRef[0]
            } else {
                val c = Camera.open()
                camRef.add(c)
                c
            }
            if (cam == null) {
                tv.text = "No Camera"
                return
            }
            val p = cam.parameters
            if (p.flashMode == Parameters.FLASH_MODE_TORCH) {
                p.flashMode = Parameters.FLASH_MODE_AUTO
                cam.parameters = p
                cam.stopPreview()
                tv.text = "Light Turned Off"
            } else {
                p.flashMode = Parameters.FLASH_MODE_TORCH
                cam.parameters = p
                cam.startPreview()
                tv.text = "Light Turned On"
            }
        }
    }
}
