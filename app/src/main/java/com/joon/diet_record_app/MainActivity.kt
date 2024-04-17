package com.joon.diet_record_app

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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

            var dateText = ""

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

                        dateText = "${year}, ${month  + 1}, ${dayOfMonth}"
                    }

                }, year, month, date)

                dlg.show()

            }

                val saveBtn = mAlertDialog.findViewById<Button>(R.id.saveBtn)
                saveBtn?.setOnClickListener {

                    val enterMemo = mAlertDialog.findViewById<EditText>(R.id.enterMemo)?.text.toString()

                    val database = Firebase.database
                    val myRef = database.getReference("myMemo")

                    val model = DataModel(dateText, enterMemo)

                    myRef
                        .push()
                        .setValue(model)

                }
            }

        }
    }
}