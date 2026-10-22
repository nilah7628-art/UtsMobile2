package com.example.uts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uts.adapter.ProductAdapter
import com.example.uts.databinding.FragmentDashboardBinding
import com.example.uts.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.uts.model.ProductResponse

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productAdapter = ProductAdapter { product ->
            startActivity(Intent(requireContext(), ProductDetailActivity::class.java).putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, product.id))
        }
        binding.rvProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProducts.adapter = productAdapter
        binding.swipeRefresh.setOnRefreshListener { loadProducts() }
        loadProducts()
    }

    private fun loadProducts() {
        binding.swipeRefresh.isRefreshing = true
        binding.tvLoading.visibility = View.VISIBLE
        binding.tvLoading.text = "Memuat produk..."
        ApiConfig.apiService.getProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (_binding == null) return
                binding.swipeRefresh.isRefreshing = false
                binding.tvLoading.visibility = View.GONE
                val products = response.body()?.products
                if (response.isSuccessful && products != null) {
                    productAdapter.submitList(products.take(25))
                    binding.tvProductCount.text = "${products.take(25).size} produk"
                } else {
                    showError("Gagal mengambil data produk (${response.code()})")
                }
            }
            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                if (_binding == null) return
                binding.swipeRefresh.isRefreshing = false
                showError("Koneksi gagal. Periksa internet lalu tarik layar untuk mencoba lagi.")
            }
        })
    }

    private fun showError(message: String) {
        binding.tvLoading.visibility = View.VISIBLE
        binding.tvLoading.text = message
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
