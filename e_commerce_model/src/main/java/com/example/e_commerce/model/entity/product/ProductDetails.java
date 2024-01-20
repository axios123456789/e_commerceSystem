package com.example.e_commerce.model.entity.product;

import com.example.e_commerce.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class ProductDetails extends BaseEntity {

	private Long productId;
	private String imageUrls;

}