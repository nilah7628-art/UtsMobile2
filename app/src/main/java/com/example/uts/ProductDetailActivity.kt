package com.example.uts

import android.R
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.uts.databinding.ActivityProductDetailBinding
import com.example.uts.model.Product
import com.example.uts.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    companion object { const val EXTRA_PRODUCT_ID = "product_id" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail Produk"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnAllReviews.setOnClickListener { showAllReviews(currentProduct) }
        loadProduct(intent.getIntExtra(EXTRA_PRODUCT_ID, 1))
    }

    private var currentProduct: Product? = null

    private fun loadProduct(id: Int) {
        binding.progressDetail.visibility = View.VISIBLE
        ApiConfig.apiService.getProductById(id).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                binding.progressDetail.visibility = View.GONE
                val product = response.body()
                if (response.isSuccessful && product != null) {
                    currentProduct = product
                    showProduct(product)
                } else Toast.makeText(this@ProductDetailActivity, "Data produk tidak ditemukan", Toast.LENGTH_LONG).show()
            }
            override fun onFailure(call: Call<Product>, t: Throwable) {
                binding.progressDetail.visibility = View.GONE
                Toast.makeText(this@ProductDetailActivity, "Gagal memuat detail. Periksa koneksi internet.", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun showProduct(p: Product) {
        binding.tvDetailTitle.text = p.title
        binding.tvDetailPrice.text = String.format(Locale.getDefault(), "\$%.2f", p.price)
        binding.tvDetailId.text = "ID Produk: ${p.id}"
        binding.tvDetailDescription.text = p.description
        binding.tvDetailCategory.text = "Kategori: ${p.category}"
        binding.tvDetailStock.text = "Stok: ${p.stock}"
        binding.tvDetailStatus.text = "Status: ${p.availabilityStatus}"
        binding.tvDetailRating.text = String.format(Locale.getDefault(), "Rating: ★ %.1f", p.rating)
        binding.tvDetailTags.text = "Tags: ${p.tags.joinToString(", ").ifBlank { "-" }}"
        binding.tvDetailDimensions.text = "Dimensi (P x L x T): ${p.dimensions.width} x ${p.dimensions.height} x ${p.dimensions.depth}"

        val firstReview = p.reviews.firstOrNull()
        binding.tvReviewHeading.text = "Review pertama"
        binding.tvFirstReview.text = if (firstReview == null) "Belum ada review" else "★ ${firstReview.rating}/5 — ${firstReview.comment}\n${firstReview.reviewerName}"
        binding.btnAllReviews.visibility = if (p.reviews.size > 1) View.VISIBLE else View.GONE

        val imageUrl = p.images.firstOrNull() ?: p.thumbnail
        Glide.with(this).load(imageUrl).fitCenter().placeholder(R.drawable.ic_menu_gallery).error(R.drawable.ic_menu_report_image).into(binding.imgDetailProduct)
    }

    private fun showAllReviews(product: Product?) {
        if (product == null) return
        if (product.reviews.isEmpty()) {
            AlertDialog.Builder(this).setTitle("Review Produk").setMessage("Belum ada review.").setPositiveButton("Tutup", null).show()
            return
        }
        val content = product.reviews.mapIndexed { index, review ->
            "${index + 1}. ★ ${review.rating}/5\n${review.comment}\n${review.reviewerName} (${review.date.take(10)})"
        }.joinToString("\n\n")
        val textView = TextView(this).apply { text = content; textSize = 15f; setPadding(40, 24, 40, 24) }
        AlertDialog.Builder(this).setTitle("Semua Review (${product.reviews.size})").setView(textView).setPositiveButton("Tutup", null).show()
    }

    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
