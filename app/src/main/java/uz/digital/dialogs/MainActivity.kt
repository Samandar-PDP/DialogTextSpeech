package uz.digital.dialogs

import android.app.ProgressDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        val simple: Button = findViewById(R.id.btn1)
        val single: Button = findViewById(R.id.btn2)
        val multi: Button = findViewById(R.id.btn3)
        val confirm: Button = findViewById(R.id.btn4)
        val full: Button = findViewById(R.id.btn5)
        val progress: Button = findViewById(R.id.btn6)
        val custom: Button = findViewById(R.id.btn7)
        val horizontal: Button = findViewById(R.id.btn8)

        simple.click {
            simpleDialog()
        }
        single.click {
            singleChoice()
        }
        multi.click {
            multiChoice()
        }
        confirm.click {
            confirm()
        }
        full.click {
            full()
        }
        custom.click {
            custom()
        }
        progress.click {
            progress()
        }
        horizontal.click {
            horizontal()
        }
    }

    private fun confirm() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("This is confirm dialog")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Accept") { di, _ ->
            di.dismiss()
        }
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { di, _ ->
            di.dismiss()
        }
        alertDialog.show()
    }

    private fun full() {
        AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen)
            .apply {
                setTitle("Full screen dialog")
                setNegativeButton("Cancel", null)
                setPositiveButton("Ok", null)
            }.show()
    }

    private fun custom() {
        val view = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null)
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setView(view)
        val no: MaterialButton = view.findViewById(R.id.no)
        val yes: MaterialButton = view.findViewById(R.id.yes)
        no.click {
            alertDialog.dismiss()
        }
        yes.click {
            finish()
        }
        alertDialog.show()
    }

    private fun progress() {
        val progress = ProgressDialog(this)
        progress.setTitle("j")
        progress.setMessage("bjlbjlkf")
        progress.show()
    }

    private fun horizontal() {
        val progress = ProgressDialog(this)
        progress.max = 100
        progress.setTitle("Horizontal progress dialog")
        progress.setMessage("Loading...")
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progress.show()
        val handle: Handler = object : Handler(mainLooper) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                progress.incrementProgressBy(5)
            }
        }
        Thread {
            try {
                while (progress.progress <= progress.max) {
                    Thread.sleep(300L)
                    handle.sendMessage(handle.obtainMessage())
                    if (progress.progress == progress.max) {
                        progress.dismiss()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun multiChoice() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Single choice")
        alertDialog.setPositiveButton("Ok", null)
        val items = arrayOf("Java", "Kotlin", "Android", "Flutter", "Dart", "Swift", "IOS")
        val booleans = booleanArrayOf(false, false, false, false, false, false, false)
        alertDialog.setMultiChoiceItems(items, booleans) { _, which, _ ->

        }
        alertDialog.show()
    }

    private fun singleChoice() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Single choice")
        alertDialog.setPositiveButton("Ok", null)
        val items = arrayOf("Java", "Kotlin", "Android", "Flutter", "Dart", "Swift", "IOS")
        alertDialog.setSingleChoiceItems(items, -1) { _, which ->
            toast(items[which])
        }
        alertDialog.show()
    }

    private fun simpleDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Dialog")
            setMessage("This is the simple dialog")
            setPositiveButton("Ok") { _, _ ->
                finish()
            }
            setNegativeButton("Cancel", null)
        }.show()
    }

    private fun Button.click(action: (View) -> Unit) {
        this.setOnClickListener {
            action(it)
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}