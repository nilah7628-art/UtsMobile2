package com.example.uts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uts.databinding.ItemProductBinding
import com.example.uts.model.Product
import java.util.Locale

class ProductAdapter(
    private val onClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val items = mutableListOf<Product>()

    fun submitList(products: List<Product>) {
        items.clear()
        items.addAll(products)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(items[position])
    override fun getItemCount(): Int = items.size

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.tvProductTitle.text = product.title
            binding.tvProductPrice.text = String.format(Locale.getDefault(), "\$%.2f", product.price)
            binding.tvProductStock.text = "Stok: ${product.stock}"
            binding.tvProductRating.text = String.format(Locale.getDefault(), "★ %.1f", product.rating)
            Glide.with(binding.imgProduct)
                .load(product.thumbnail)
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(binding.imgProduct)
            binding.root.setOnClickListener { onClick(product) }
        }
    }
}
