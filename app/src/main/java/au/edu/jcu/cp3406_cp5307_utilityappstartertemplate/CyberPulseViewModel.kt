package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CyberPulseViewModel : ViewModel() {

    private val _alertCount = MutableStateFlow(1)
    val alertCount: StateFlow<Int> = _alertCount

    private val _category = MutableStateFlow("All")
    val category: StateFlow<String> = _category

    fun updateAlertCount(count: Int) {
        _alertCount.value = count
    }

    fun updateCategory(newCategory: String) {
        _category.value = newCategory
    }
}