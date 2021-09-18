package com.example.countrycapitalquiz.model.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countrycapitalquiz.constant.Performance
import com.example.countrycapitalquiz.model.Country
import com.example.countrycapitalquiz.model.Result
import kotlin.collections.set

class GameViewModel : ViewModel() {

    private lateinit var _result: Result
    val result get() = _result

    private lateinit var _countries: List<Country>

    private var _isCountryGame = false
    val isCountryGame get() = _isCountryGame

    private var _length = 0
    val length get() = _length

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val _questionCount = MutableLiveData<Int>()
    val questionCount: LiveData<Int> get() = _questionCount

    private val _currentCountry = MutableLiveData<Country>()
    val currentCountry: LiveData<Country> get() = _currentCountry

    fun setCountries(countries: List<Country>) {
        _countries = countries
    }

    fun setIsCountryGame(isCountryGame: Boolean) {
        _isCountryGame = isCountryGame
    }

    fun setLength(length: Int) {
        _length = length
    }

    fun resetGame() {
        _score.value = 0
        _questionCount.value = 0
        _result = Result(LinkedHashMap())
    }

    fun nextQuestion(): Boolean {
        return if (_questionCount.value!! < _length) {
            getNextQuestion()
            true
        } else false
    }

    private fun getNextQuestion() {
        val country = _countries.filter { !_result.answers.keys.contains(it) }.random()
        _result.answers[country] = false
        _currentCountry.value = country
        _questionCount.value = (_questionCount.value)?.inc()
    }

    fun isAnswerCorrect(answer: String): Boolean {
        val country = _currentCountry.value!!
        val isCorrect = when (isCountryGame) {
            true -> answer.equals(country.countryName, true)
            else -> answer.equals(country.capitalName, true)
        }
        return if (isCorrect) {
            _result.answers[country] = true
            increaseScore()
            true
        } else false
    }

    private fun increaseScore() {
        _score.value = _score.value?.inc()
    }

    fun calculatePerformance(): Performance {
        val performanceFraction = _score.value!!.toFloat() / _length.toFloat()
        return when {
            performanceFraction < Performance.MEDIOCRE.value -> Performance.POOR
            performanceFraction < Performance.GOOD.value -> Performance.MEDIOCRE
            performanceFraction < Performance.EXCELLENT.value -> Performance.GOOD
            else -> Performance.EXCELLENT
        }
    }

}