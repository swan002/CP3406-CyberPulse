package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CyberPulseViewModel : ViewModel() {

    private val repository = CyberNewsRepository()

    private val _alerts = MutableStateFlow<List<CyberAlert>>(emptyList())
    val alerts: StateFlow<List<CyberAlert>> = _alerts

    private val _alertCount = MutableStateFlow(1)
    val alertCount: StateFlow<Int> = _alertCount

    private val _category = MutableStateFlow("All")
    val category: StateFlow<String> = _category

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        refreshAlerts()
    }

    fun refreshAlerts() {
        viewModelScope.launch {
            _isLoading.value = true
            _alerts.value = repository.getRemoteAlerts()
            _isLoading.value = false
        }
    }

    fun updateAlertCount(count: Int) {
        _alertCount.value = count
    }

    fun updateCategory(newCategory: String) {
        _category.value = newCategory
    }
}