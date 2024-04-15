package com.joon.diet_record_app

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import java.util.Calendar
import java.util.GregorianCalendar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val writeButton = findViewById<ImageView>(R.id.writeBtn)
        writeButton.setOnClickListener {

            val mDialogView = LayoutInflater.from(this).inflate(androidx.core.R.layout.custom_dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Excercise Record Dialog")

            val mAlertDialog = mBuilder.show()

            val DateSelectBtn = mAlertDialog.findViewById<Button>(R.id.dateSelectBtn)

            DateSelectBtn?.setOnClickListener {

            mAlertDialog.findViewById<Button>(R.id.dateSelectBtn)?.setOnClickListener {

                val days = GregorianCalendar()
                val year : Int = days.get(Calendar.YEAR)
                val month : Int = days.get(Calendar.MONTH)
                val date : Int = days.get(Calendar.DATE)


                val dlg = DatePickerDialog(this,object : DatePickerDialog.OnDateSetListener{
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int
                    ) {
                        Log.d("MAIN", "${year}, ${month  + 1}, ${dayOfMonth}")
                        DateSelectBtn.setText("${year}, ${month  + 1}, ${dayOfMonth}")
                    }

                }, year, month, date)

                dlg.show()

            }

            }

        }
    }
}