package stavka.stavki.games.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import stavka.stavki.games.model.Config
import stavka.stavki.games.net.Resource
import stavka.stavki.games.net.repository.IndexRepository
import stavka.stavki.games.net.response.IndexResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class MainViewModel constructor(
    private val indexRepository: IndexRepository,
) : ViewModel() {

    /****************************************************************************************************
     ** MutableLiveData
     ****************************************************************************************************/
    private val indexLiveData: MutableLiveData<Resource<IndexResponse>> = MutableLiveData()

    /****************************************************************************************************
     ** LiveData
     ****************************************************************************************************/
    fun indexResponse(): LiveData<Resource<IndexResponse>> = indexLiveData

    /****************************************************************************************************
     ** HomeFragment
     ****************************************************************************************************/
    fun loadIndex(config: Config) {
        viewModelScope.launch {
            supervisorScope {
                try {
                    indexLiveData.postValue(Resource.Loading)
                    val callFromApi = async { indexRepository.loadDataFromApi(config) }

                    val apiResponse = callFromApi.await()
                    val remoteData = apiResponse.body()
                    if (apiResponse.isSuccessful && remoteData != null) {
                        indexLiveData.postValue(Resource.Success(remoteData))
                    } else {
                        indexLiveData.postValue(Resource.Error(Exception("Not found")))
                    }
                } catch (ex: Exception) {
                    indexLiveData.postValue(Resource.Error(Exception("No connection")))
                }
            }

        }
    }
}