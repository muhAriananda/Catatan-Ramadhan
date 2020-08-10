package id.buhankita.catatanramadhan.ui.base.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.buhankita.catatanramadhan.R
import id.buhankita.catatanramadhan.data.model.note.Note
import kotlinx.android.synthetic.main.activity_note_add_update.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class NoteAddUpdateActivity : AppCompatActivity() {

    private val fireStoreRef = Firebase.firestore
    private val user = Firebase.auth.currentUser

    private val mCalender = Calendar.getInstance()

    private var isEdit = false
    private var note: Note? = null

    companion object {
        const val EXTRA_NOTE = "extra_note"
    }

    private val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        mCalender.set(Calendar.YEAR, year)
        mCalender.set(Calendar.MONTH, month)
        mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateDateLabel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_add_update)

        note = intent.getParcelableExtra(EXTRA_NOTE)

        //Check is Add or Edit
        if (note != null) {
            isEdit = true
        } else {
            note = Note()
        }

        val actionBarTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.update)

            if (note != null) {
                note?.let {

                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
        }

        supportActionBar?.apply {
            title = actionBarTitle
            setDisplayHomeAsUpEnabled(true)
        }

        //Get date from date picker
        edt_note_date.editText?.setOnClickListener {
            DatePickerDialog(
                this,
                dateListener,
                mCalender.get(Calendar.YEAR),
                mCalender.get(Calendar.MONTH),
                mCalender.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_note_update, menu)
        } else {
            menuInflater.inflate(R.menu.menu_note_add, menu)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save_note -> {

            }

            R.id.action_update_note -> {

            }

            R.id.action_delete_note -> {

            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveNote(note: Note) = CoroutineScope(Dispatchers.IO).launch {
        val userId = user?.uid.toString()
        val noteCollectionRef =
            fireStoreRef.collection("users").document(userId).collection("notes")
        try {
            noteCollectionRef.add(note).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@NoteAddUpdateActivity,
                    "Successfully saved data",
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@NoteAddUpdateActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateDateLabel() {
        val mFormat = "dd/MM/yyyy"
        val dateFormat = SimpleDateFormat(mFormat, Locale.getDefault())
        val date = dateFormat.format(mCalender.time)

        edt_note_date.editText?.setText(date)
    }
}