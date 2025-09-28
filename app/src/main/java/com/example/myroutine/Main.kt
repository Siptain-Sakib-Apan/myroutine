package com.example.myroutine

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class Main : AppCompatActivity () {

    private lateinit var listView: ListView
    private lateinit var addButton: Button
    private lateinit var prefs: SharedPreferences
    private lateinit var adapter: ArrayAdapter<String>
    private val displayList = mutableListOf<String>()

    private val PREFS_NAME = "routine_list"

    private lateinit var clearAllButton: Button
    private val checkedList = mutableListOf<Boolean>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
    prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val savedSet = prefs.getStringSet(PREFS_NAME, emptySet())
        displayList.addAll(savedSet ?: emptySet())
    initializeViews()
        loadTasks()
    setupListView()
    }


    private fun initializeViews() {
        listView = findViewById(R.id.listM)
        addButton = findViewById(R.id.adbtn)
        clearAllButton = findViewById(R.id.btnClearAll)

    addButton.setOnClickListener { showAddNotificationDialog() }
        clearAllButton.setOnClickListener { clearAllTasks() }
    }

    private fun setupListView() {
        adapter = object : ArrayAdapter<String>(this, R.layout.item_task, R.id.itemText, displayList) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val checkBox = view.findViewById<CheckBox>(R.id.checkBoxTask)
                val textView = view.findViewById<TextView>(R.id.itemText)

                // Avoid multiple listeners
                checkBox.setOnCheckedChangeListener(null)

                // Make sure checkedList has enough items
                while (checkedList.size < displayList.size) {
                    checkedList.add(false)
                }

                checkBox.isChecked = checkedList[position]
                textView.paint.isStrikeThruText = checkBox.isChecked

                // Update strike-through in real-time
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    checkedList[position] = isChecked
                    textView.paint.isStrikeThruText = isChecked
                    textView.invalidate()
                    saveTasks()
                }

                return view
            }
        }

        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            showNotificationOptions(position)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            deleteNotification(position)
            true
        }
    }

    private fun showNotificationOptions(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Routine Options")
            .setItems(arrayOf("Edit" , "Delete")) { _, which ->
                when (which) {
                    0 -> editNotification(position)
                    1 -> deleteNotification(position)
                }
            }
            .setNegativeButton("cancel", null)
            .show()
    }

    private fun showAddNotificationDialog() {

        val editText = EditText(this)
        editText.hint ="Enter Task"

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add New Task")
            .setView(editText)
            .setPositiveButton("add") { _, _ ->
                val input = editText.text.toString()
                if (input.isNotEmpty()) {
                    displayList.add(input)
                    checkedList.add(false)
                    adapter.notifyDataSetChanged()
                    saveTasks()
                }
            }
            .setNegativeButton("Cancel",  null)
            .create()

        dialog.show()
    }

    private fun editNotification(position: Int) {
        val currentText = displayList[position]
        val editText = EditText(this)
        editText.setText(currentText)
        editText.setSelection(currentText.length)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit Task")
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                val updatedText = editText.text.toString()
                if (updatedText.isNotEmpty()) {
                    displayList[position] = updatedText
                    adapter.notifyDataSetChanged()
                    saveTasks()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun deleteNotification(position: Int){
        AlertDialog.Builder(this)
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton("Delete") { _, _ ->
                displayList.removeAt(position)
                checkedList.removeAt(position)
                adapter.notifyDataSetChanged()
                saveTasks()
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }
    private fun clearAllTasks() {
        if (displayList.isNotEmpty()) {
            AlertDialog.Builder(this)
                .setTitle("Clear All Tasks")
                .setMessage("Are you sure you want to delete all tasks?")
                .setPositiveButton("Yes") { _, _ ->
                    displayList.clear()
                    checkedList.clear()
                    adapter.notifyDataSetChanged()
                    saveTasks()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun saveTasks() {
        val editor = prefs.edit()

        // Save as "task|checked" strings
        val combinedSet = displayList.mapIndexed { index, task ->
            "$task|${checkedList.getOrElse(index) { false }}"
        }.toSet()

        editor.putStringSet(PREFS_NAME, combinedSet)
        editor.apply()
    }

    private fun loadTasks() {
        val savedSet = prefs.getStringSet(PREFS_NAME, emptySet())
        displayList.clear()
        checkedList.clear()

        savedSet?.forEach { combined ->
            val parts = combined.split("|")
            val task = parts[0]
            val checked = parts.getOrNull(1)?.toBoolean() ?: false

            displayList.add(task)
            checkedList.add(checked)
        }
    }
}

