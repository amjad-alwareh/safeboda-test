package com.safeboda.test.user.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    @Inject lateinit var viewModelFactory: UserViewModelFactory

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserViewModel
    private var isResponseViewed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabSearch.setOnClickListener {
            val text = binding.input.text.toString()

            if (text.isEmpty()) {
                showSnackbar(getString(R.string.type_name_first))
                return@setOnClickListener
            }

            viewModel.searchUser(text)
        }

        requestUser()
    }

    private fun requestUser() {
        viewModel.userResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    isResponseViewed = false
                    showLoading()
                }
                Status.ERROR -> showError(it.throwable)
                Status.SUCCESS -> {
                    if (isResponseViewed) return@observe
                    showSuccess(it.data)
                }
            }
        })
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
        isResponseViewed = true
        dismissLoader()
        val action = SearchFragmentDirections.actionSearchToUser(user)
        findNavController().navigate(action)
    }

    private fun showSnackbar(text: String) {
        Snackbar.make(binding.main, text, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}