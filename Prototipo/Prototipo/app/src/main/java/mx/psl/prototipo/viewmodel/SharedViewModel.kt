package mx.psl.prototipo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author Pablo Spínola López
 *
 * Esta clase no se utiliza
 *
 */

class SharedViewModel: ViewModel() {
    val data = MutableLiveData<MutableMap<String, String?>>()

    fun addValue(key: String, value: String?) {
        val currentData = data.value ?: mutableMapOf()
        currentData[key] = value
        data.value = currentData
    }
}