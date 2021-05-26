package com.safeboda.test.user.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.safeboda.test.R
import com.safeboda.test.core.utils.load
import com.safeboda.test.core.utils.number
import com.safeboda.test.core.utils.text
import com.safeboda.test.databinding.FragmentUserBinding
import com.safeboda.test.user.data.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment(R.layout.fragment_user) {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private var user: User? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = arguments?.getSerializable("USER") as User

        binding.ivUser.load(user?.avatarUrl)
        binding.tvName.text = user?.name
        binding.tvLogin.text = getString(R.string.login, user?.login)
        binding.tvCreated.text = user?.createdAt
        binding.tvBio.text = user?.bio.text()
        binding.tvType.text = user?.type
        binding.tvLocation.text = user?.location.text()
        binding.tvEmail.text = user?.email.text()
        binding.tvCompany.text = user?.company.text()
        binding.tvRepos.text = getString(R.string.repos, user?.publicRepos.number())
        binding.tvGists.text = getString(R.string.gists, user?.publicGists.number())
        binding.tvFollowing.text = getString(R.string.following, user?.following.number())
        binding.tvFollowers.text = getString(R.string.follower, user?.followers.number())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}