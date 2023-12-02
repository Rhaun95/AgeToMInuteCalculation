package com.example.agetominutecalculation

import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null

    val myCalendar = Calendar.getInstance()
    val year = myCalendar.get(Calendar.YEAR)
    val month = myCalendar.get(Calendar.MONTH)
    val day = myCalendar.get(Calendar.DAY_OF_MONTH)

    var isClicked = true;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeinMinutes)
        tvAgeInMinutes?.text = "0"
        tvSelectedDate?.text = "$day/${month + 1}/$year"

        val btnMin : Button = findViewById(R.id.btnMin)
        val btnH : Button = findViewById(R.id.btnH)
        val tvSub : TextView = findViewById(R.id.tvSub)

        btnMin.setBackgroundColor(getColor(R.color.btnClicked))
        btnMin.setTextColor(Color.WHITE)

        btnMin.setOnClickListener(){
            isClicked = true
            tvSub.text = "in minutes till date"

            btnMin.setBackgroundColor(getColor(R.color.btnClicked))
            btnMin.setTextColor(Color.WHITE)

            btnH.setBackgroundColor(getColor(R.color.bgColor))
            btnH.setTextColor(Color.WHITE)

            if(tvAgeInMinutes?.text != "0"){
                tvAgeInMinutes?.text = (tvAgeInMinutes?.text.toString().toInt() * 60).toString()
            }
        }
        btnH.setOnClickListener(){
            isClicked = false
            tvSub.text = "in hours till date"

            btnH.setBackgroundColor(getColor(R.color.btnClicked))
            btnH.setTextColor(Color.WHITE)

            btnMin.setBackgroundColor(getColor(R.color.bgColor))
            btnMin.setTextColor(Color.WHITE)

            if(tvAgeInMinutes?.text != "0"){
                tvAgeInMinutes?.text = (tvAgeInMinutes?.text.toString().toInt() / 60).toString()
            }
        }

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        btnDatePicker.setOnClickListener(){
            clickDatePicker()
        }

    }

    private fun clickDatePicker(){

        DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
                Toast.makeText(this,
                    "Date: $year.0$month.$dayOfMonth", Toast.LENGTH_SHORT).show()

                val selectedDate = "$dayOfMonth/${month + 1}/$year"

                tvSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.GERMAN)

                val theDate = sdf.parse(selectedDate)
                theDate?.let{
                    if (isClicked) {
                        val selectedDateInMinute = theDate.time / 60000

                        val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                        currentDate?.let {
                            val currentDateInMinutes = currentDate.time/ 60000

                            val differenceInMinutes =  currentDateInMinutes - selectedDateInMinute

                            tvAgeInMinutes?.text = "$differenceInMinutes"
                        }
                    } else {
                        val selectedDateInHour = theDate.time / 3600000

                        val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                        currentDate?.let {
                            val currentDateInHour = currentDate.time/ 3600000

                            val differenceInHours =  currentDateInHour - selectedDateInHour

                            tvAgeInMinutes?.text = "$differenceInHours"
                        }
                    }
                }
        },
        year,
        month,
        day
        ).show()

    }
}