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
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.Calendar
import java.util.GregorianCalendar

class MainActivity : AppCompatActivity() {

    val dataModelList = mutableListOf<DataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = Firebase.database
        val myRef = database.getReference("myMemo")

        val listView = findViewById<ListView>(R.id.mainLV)

        val adapter_list = ListViewAdapter(dataModelList)

        listView.adapter = adapter_list

        myRef.child(Firebase.auth.currentUser!!.uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                Log.d("Point1", dataModelList.toString())
                dataModelList.clear()
                Log.d("Point2", dataModelList.toString())

                for (dataModel in snapshot.children){
                    Log.d("Data", dataModel.toString())
                    dataModelList.add(dataModel.getValue(DataModel::class.java)!!)

                }
                adapter_list.notifyDataSetChanged()
                Log.d("DataModel", dataModelList.toString())

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

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
                    val myRef = database.getReference("myMemo").child(Firebase.auth.currentUser!!.uid)

                    val model = DataModel(dateText, enterMemo)

                    myRef
                        .push()
                        .setValue(model)

                    mAlertDialog.dismiss()

                }
            }

        }
    }
}