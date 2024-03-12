package com.belajar.biodata

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var editTextNama: EditText
    private lateinit var editTextTanggalLahir: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextNama = findViewById(R.id.editTextNama)
        editTextTanggalLahir = findViewById(R.id.editTextTanggalLahir)

        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)
        buttonSubmit.setOnClickListener {
            val nama = editTextNama.text.toString()
            val tanggalLahir = editTextTanggalLahir.text.toString()

            if (nama.isNotEmpty() && tanggalLahir.isNotEmpty()) {
                val age = calculateAge(tanggalLahir)
                Toast.makeText(
                    this,
                    "Nama: $nama, Usia: $age",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Harap isi semua field!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        editTextTanggalLahir.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, day)
                val formattedDate =
                    "${selectedDate.get(Calendar.YEAR)}-${selectedDate.get(Calendar.MONTH) + 1}-${selectedDate.get(
                        Calendar.DAY_OF_MONTH
                    )}"
                editTextTanggalLahir.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun calculateAge(birthDate: String): String {
        val dob = Calendar.getInstance()
        val current = Calendar.getInstance()

        val dateParts = birthDate.split("-")
        dob.set(dateParts[0].toInt(), dateParts[1].toInt() - 1, dateParts[2].toInt())

        var years = current.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        var months = current.get(Calendar.MONTH) - dob.get(Calendar.MONTH)
        var days = current.get(Calendar.DAY_OF_MONTH) - dob.get(Calendar.DAY_OF_MONTH)

        if (days < 0) {
            months--
            val maxDaysInMonth = current.getActualMaximum(Calendar.DAY_OF_MONTH)
            days += maxDaysInMonth
        }
        if (months < 0) {
            years--
            months += 12
        }

        return "$years tahun, $months bulan, $days hari"
    }
}