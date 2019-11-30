package develop.tomo1139.todo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import develop.tomo1139.todo.R
import develop.tomo1139.todo.databinding.ActivityMainBinding
import develop.tomo1139.todo.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val binding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)}
    private val viewModel by lazy {
        val factory = ViewModelProvider.NewInstanceFactory()
        ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.adapter = viewModel.controller.adapter
        binding.todoTitleEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (binding.todoTitleEditText.text.isNotEmpty()) {
                    viewModel.addTodoItem()
                }
                true
            } else {
                false
            }
        }
        viewModel.loadTodoItems()
    }
}
