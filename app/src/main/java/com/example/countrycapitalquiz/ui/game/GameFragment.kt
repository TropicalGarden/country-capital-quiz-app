package com.example.countrycapitalquiz.ui.game

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.countrycapitalquiz.R
import com.example.countrycapitalquiz.databinding.FragmentGameBinding
import com.example.countrycapitalquiz.model.view.GameViewModel

class GameFragment : Fragment() {

    private val gameViewModel: GameViewModel by activityViewModels()

    private lateinit var binding: FragmentGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customBackNavigation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            gameFragment = this@GameFragment
            lifecycleOwner = viewLifecycleOwner
            gameViewModel = this@GameFragment.gameViewModel
            isCountryGame = this@GameFragment.gameViewModel.isCountryGame
        }
    }

    fun onSubmitAnswer() {
        val answer = binding.textInputEditText.text.toString()

        if (gameViewModel.isAnswerCorrect(answer)) {
            resetLayout()
            accessibleCorrect()
            if (!gameViewModel.nextQuestion()) {
                startResultFragment()
            }
        } else {
            setErrorTextField(true)
        }
    }

    fun onSkipAnswer() {
        if (gameViewModel.nextQuestion()) {
            resetLayout()
        } else {
            startResultFragment()
        }
    }

    private fun startResultFragment() {
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToResultFragment())
    }

    private fun resetLayout() {
        setErrorTextField(false)
        scrollToTop()
        hideSoftKeyboard()
    }

    private fun setErrorTextField(isError: Boolean) {
        if (isError) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }

    private fun scrollToTop() {
        binding.scrollView.fullScroll(ScrollView.FOCUS_UP)
    }

    private fun hideSoftKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun accessibleCorrect() {
        binding.root.announceForAccessibility(getString(R.string.correct))
    }

    private fun customBackNavigation() {
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(GameFragmentDirections.actionGameFragmentToMenuFragment())
                }
            })
    }

}