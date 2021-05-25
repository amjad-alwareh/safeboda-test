package com.safeboda.test.user.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.safeboda.test.R
import com.safeboda.test.core.Status
import com.safeboda.test.core.exception.NotFoundException
import com.safeboda.test.core.presentation.ui.dismissLoader
import com.safeboda.test.core.presentation.ui.showLoader
import com.safeboda.test.databinding.FragmentSearchBinding
import com.safeboda.test.user.data.model.User
import com.safeboda.test.user.presentation.viewmodel.UserViewModel
import com.safeboda.test.user.presentation.viewmodel.UserViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    @Inject lateinit var viewModelFactory: UserViewModelFactory

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(UserViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.userResponse.collect {
                when (it.status) {
                    Status.LOADING -> showLoading()
                    Status.ERROR -> showError(it.throwable)
                    Status.SUCCESS -> showSuccess(it.data)
                }
            }
        }

        binding.fabSearch.setOnClickListener {
            val text = binding.input.text.toString()

            if (text.isEmpty()) {
                showSnackbar(getString(R.string.type_name_first))
                return@setOnClickListener
            }

            viewModel.searchUser(text)
        }
    }

    private fun showLoading() {
        showLoader()
    }

    private fun showError(throwable: Throwable?) {
        dismissLoader()

        val text = when (throwable) {
            is NotFoundException -> getString(R.string.user_not_found)
            else -> throwable?.message
        }

        showSnackbar(text ?: getString(R.string.wrong))
    }

    private fun showSuccess(user: User?) {
        dismissLoader()
    }

    private fun showSnackbar(text: String) {
        Snackbar.make(binding.main, text, Snackbar.LENGTH_SHORT).show()
    }

}