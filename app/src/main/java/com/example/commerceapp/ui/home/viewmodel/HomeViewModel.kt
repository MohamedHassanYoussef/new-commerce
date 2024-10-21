import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.commerceapp.model.ProductCount
import com.example.commerceapp.model.Repository
import com.example.commerceapp.network.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _productCount = MutableStateFlow<State<Response<ProductCount?>>>(State.Loading)
    val productCount: StateFlow<State<Response<ProductCount?>>> = _productCount

    init {
        getProductCount()
    }

    private fun getProductCount() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getCountOfProducts().collect { count ->
                    Log.d("dfgfhj", "getProductCount: $count")
                    _productCount.value = State.Success(count)
                }
            } catch (e: Exception) {
                _productCount.value = State.Error(e)
                e.printStackTrace()
            }
        }
    }
}
