package android.training.notepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.appcompat.widget.Toolbar
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.LocalDateTime

class NoteDetailActivity : AppCompatActivity() {

    companion object
    {
        val REQUEST_EDIT_NOTE = 1
        val EXTRA_NOTE = "note"
        val EXTRA_NOTE_INDEX = "noteIndex"

        val ACTION_SAVE_NOTE = "android.training.notepad.actions.ACTION_SAVE_NOTE"
        val ACTION_DELETE_NOTE = "android.training.notepad.actions.ACTION_DELETE_NOTE"
    }

    lateinit var note : Note
    var noteIndex : Int = -1

    lateinit var titleView : TextView
    lateinit var textView : TextView
    lateinit var menuItem : MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this);

        setContentView(R.layout.activity_note_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        note = intent.getParcelableExtra(EXTRA_NOTE)!!
        noteIndex = intent.getIntExtra(EXTRA_NOTE_INDEX, -1)

        titleView = findViewById(R.id.title)
        textView = findViewById(R.id.text)
        titleView.text = note.name
        textView.text = note.text

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.activity_note_detail, menu)

        if((note.name == "") && (note.text == ""))
        {
            menuItem = menu.findItem(R.id.action_delete)
            menuItem.isVisible = false
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            R.id.action_save ->
            {
                saveNote()
                return true
            }

            R.id.action_delete ->
            {
                showConfirmDeleteNoteDialog()
                return true
            }

            else ->
            {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun saveNote()
    {
        note.name = titleView.text.toString()
        note.text = textView.text.toString()

        if(note.name == "" && note.text == "")
        {
            finish()
        }

        else
        {
            note.dateNote = LocalDateTime.now()

            intent = Intent(ACTION_SAVE_NOTE)
            intent.putExtra(EXTRA_NOTE, note as Parcelable)
            intent.putExtra(EXTRA_NOTE_INDEX, noteIndex)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }


    }

    private fun showConfirmDeleteNoteDialog() {
        val confirmFragment = DeleteNoteDialogFragment(note.name)

        confirmFragment.listener = object : DeleteNoteDialogFragment.ConfirmDeleteDialogListener{
            override fun onDialogPositiveClick() { deleteNote() }

            override fun onDialogNegativeClick() {}

        }

        confirmFragment.show(supportFragmentManager, "confirmDeleteDialog" )
    }

    fun deleteNote()
    {
        intent = Intent(ACTION_DELETE_NOTE)
        intent.putExtra(EXTRA_NOTE_INDEX, noteIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }


}
