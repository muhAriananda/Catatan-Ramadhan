package id.buhankita.catatanramadhan.ui.base.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.buhankita.catatanramadhan.R
import id.buhankita.catatanramadhan.data.model.note.Note
import id.buhankita.catatanramadhan.databinding.ActivityNoteAddUpdateBinding
import id.buhankita.catatanramadhan.utils.Constant.PATH_NOTE
import id.buhankita.catatanramadhan.utils.Constant.PATH_USER
import kotlinx.android.synthetic.main.activity_note_add_update.*
import java.text.SimpleDateFormat
import java.util.*

class NoteAddUpdateActivity : AppCompatActivity() {

    private val TAG = NoteAddUpdateActivity::class.java.simpleName

    private lateinit var binding: ActivityNoteAddUpdateBinding

    private val user = Firebase.auth.currentUser
    private val userId = user?.uid.toString()

    private val db = Firebase.firestore
    private val noteCollectionRef = db.collection(PATH_USER).document(userId).collection(PATH_NOTE)

    private val mCalender = Calendar.getInstance()

    private var docId: String? = null
    private var note: Note? = null
    private var isEdit = false

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_ID = "extra_id"
    }

    private val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        mCalender.set(Calendar.YEAR, year)
        mCalender.set(Calendar.MONTH, month)
        mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateDateLabel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_note_add_update)

        note = intent.getParcelableExtra(EXTRA_NOTE)
        docId = intent.getStringExtra(EXTRA_ID)
        Log.d(TAG, "Doc id is : $docId")

        initView()
    }

    private fun initView() {
        val actionBarTitle: String

        //Check is Add or Edit
        if (note != null) {
            isEdit = true
        } else {
            note = Note()
        }

        if (isEdit) {
            actionBarTitle = getString(R.string.update)
            note?.let { note ->
                binding.note = note
            }
        } else {
            actionBarTitle = getString(R.string.add)
        }

        //Set actionBar
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
            R.id.action_save_note -> getNote()?.let { saveNote(it) }
            R.id.action_update_note -> getNote()?.let { updateNote(it) }
            R.id.action_delete_note -> showDialogDelete()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getNote(): Note? {
        val date = edt_note_date.editText?.text.toString().trim()
        val puasa = cb_fasting.isChecked
        val fajr = cb_fajr.isChecked
        val dzuhur = cb_dzuhur.isChecked
        val ashar = cb_ashar.isChecked
        val magrib = cb_magrib.isChecked
        val isya = cb_isya.isChecked
        val juz = edt_juz.editText?.text.toString().toInt()
        val surah = edt_surah.editText?.text.toString().trim()
        val ayat = edt_ayat.editText?.text.toString().toInt()

        if (date.isEmpty()) {
            edt_note_date.editText?.error = getString(R.string.error_message_field)

        } else {
            note?.let { note ->
                note.date = date
                note.puasa = puasa
                note.subuh = fajr
                note.dzhuhur = dzuhur
                note.ashar = ashar
                note.magrib = magrib
                note.isya = isya
                note.juz = juz
                note.surah = surah
                note.ayat = ayat
            }
        }
        return note
    }

    private fun saveNote(note: Note) {
        isLoading(true)
        noteCollectionRef.add(note)
            .addOnSuccessListener { documentReference ->
                isLoading(false)
                Toast.makeText(this, "Successfully add", Toast.LENGTH_LONG).show()
                Log.d(TAG, "DocumentSnapshot added with ID : ${documentReference.id}")
                finish()
            }
            .addOnFailureListener { exception ->
                isLoading(false)
                Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
                Log.w(TAG, "DocumentSnapshot failed add : ${exception.message}")
            }
    }

    private fun updateNote(note: Note) {
        val documentCollectionRef = noteCollectionRef.document(docId!!)
        Log.d(TAG, "Doc update id is : $docId")
        documentCollectionRef
            .set(note, SetOptions.merge())
            .addOnCompleteListener {
                Toast.makeText(this, "Success update", Toast.LENGTH_LONG).show()
                finish()
            }
            .addOnFailureListener { Toast.makeText(this, "Can't update", Toast.LENGTH_LONG).show() }
    }

    private fun deleteNote() {
        val documentCollectionRef = noteCollectionRef.document(docId!!)
        documentCollectionRef
            .delete()
            .addOnCompleteListener {
                Toast.makeText(this, "Success delete", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { Toast.makeText(this, "Can't delete", Toast.LENGTH_LONG).show() }
    }

    private fun showDialogDelete() {
        val builder =
            AlertDialog.Builder(this, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
        builder.setTitle(getString(R.string.delete))
        builder.setMessage(getString(R.string.question_delete))
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            deleteNote()
            finish()
        }
        builder.create().show()
    }

    private fun updateDateLabel() {
        val mFormat = "dd/MM/yyyy"
        val dateFormat = SimpleDateFormat(mFormat, Locale.getDefault())
        val date = dateFormat.format(mCalender.time)

        edt_note_date.editText?.setText(date)
    }

    private fun isLoading(state: Boolean) {
        if (state) {
            bg_view.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE
        } else {
            bg_view.visibility = View.INVISIBLE
            progressBar.visibility = View.INVISIBLE
        }
    }
}