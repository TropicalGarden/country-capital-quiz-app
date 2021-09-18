package com.example.countrycapitalquiz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.countrycapitalquiz.R
import com.example.countrycapitalquiz.databinding.FragmentMenuBinding
import com.example.countrycapitalquiz.model.view.GameViewModel

class MenuFragment : Fragment() {

    private val gameViewModel: GameViewModel by activityViewModels()

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.menuFragment = this
    }

    fun startGameLengthFragment(isCountryGame: Boolean) {
        gameViewModel.resetGame()
        gameViewModel.setIsCountryGame(isCountryGame)
        findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToLengthFragment())
    }

    fun startLearnFragment() {
        findNavController().navigate(R.id.action_menuFragment_to_learnFragment)
    }

    fun exitGame() {
        activity?.finish()
    }

}