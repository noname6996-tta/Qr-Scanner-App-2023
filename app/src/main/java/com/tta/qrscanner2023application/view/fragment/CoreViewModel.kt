package com.tta.qrscanner2023application.view.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tta.qrscanner2023application.data.model.QrCodeEntity
import com.tta.qrscanner2023application.data.model.TypeCode
import com.tta.qrscanner2023application.data.repository.QrCodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CoreViewModel @Inject constructor(
    private val repository: QrCodeRepository
) : ViewModel() {
    val listQrCodeScan = MutableLiveData<List<QrCodeEntity>>()
    val listQrCodeCreate = MutableLiveData<List<QrCodeEntity>>()
    val errorMessage = MutableLiveData<String>()
    val qrCodeEntity = MutableLiveData<QrCodeEntity>()
    fun getListQrByType(typeCode: TypeCode) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repository.getAllQrList(typeCode)
                }
            }
                .onSuccess {
                    if (typeCode == TypeCode.CREATED) {
                        listQrCodeCreate.value = it
                    } else {
                        listQrCodeScan.value = it
                    }
                }
                .onFailure {
                    errorMessage.value = it.message.toString()
                }
        }
    }

    fun insertQrCode(qrCodeEntity: QrCodeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertQr(qrCodeEntity)
        }
    }

    fun deleteQrCode(typeCode: TypeCode, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                repository.deleteQrById(id)
            }.onSuccess {
                getListQrByType(typeCode) // Reload the list after successful deletion
            }.onFailure {
                errorMessage.value = it.message.toString()
            }
        }
    }

    fun getInfoById(id: Int) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repository.getInfoById(id)
                }
            }
                .onSuccess {
                    qrCodeEntity.value = it
                }
                .onFailure {
                    errorMessage.value = it.message.toString()
                }
        }
    }

    fun deleteAllQrCode() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    // UPCItemDB: API này cung cấp thông tin về mã vạch UPC, EAN và ASIN. Bạn có thể tìm kiếm thông tin sản phẩm theo mã vạch.
    fun searchBarcodeOnline(activity: Activity, barcode: String) {
        val client = OkHttpClient()

        val url = "https://api.upcitemdb.com/prod/trial/lookup?upc=$barcode"  // API của UPCItemDB

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
                // Xử lý lỗi, ví dụ: hiển thị thông báo
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    val jsonResponse = response.body()?.string()

                    // Xử lý dữ liệu JSON để lấy thông tin sản phẩm từ mã vạch
                    if (jsonResponse != null) {
                        parseBarcodeData(activity, jsonResponse)
                    }
                }
            }
        })
    }

    fun parseBarcodeData(context: Activity, jsonResponse: String) {
        // Phân tích dữ liệu JSON (có thể sử dụng thư viện như Gson hoặc Moshi)
        // Ví dụ: Lấy tên sản phẩm và thông tin chi tiết
        val productInfo = JSONObject(jsonResponse)
        val productName = productInfo.getJSONArray("items").getJSONObject(0).getString("title")

        // Hiển thị thông tin hoặc chuyển đến màn hình chi tiết
        context.runOnUiThread {
            Toast.makeText(context, "Sản phẩm: $productName", Toast.LENGTH_LONG).show()
        }
    }

    // Nếu không tìm đc thì dùng hàm naày cho đơn giản
    private fun searchBarcodeOnline2(context: Context, barcode: String) {
        val searchUrl = "https://www.google.com/search?q=$barcode"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl))
        context.startActivity(intent)
    }
}