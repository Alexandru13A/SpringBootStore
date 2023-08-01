package com.alexandru.SpringBootStore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private long productId;
    @NotEmpty
    private String name;

    @NotEmpty
    private String shortDescription;

    @NotEmpty
    private String longDescription;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "99999.99", inclusive = true)
    private BigDecimal price;

    @NotEmpty
    private String category;

    @NotEmpty
    private String brand;

    @NotEmpty
    private String type;

    @NotEmpty
    private String gender;

    private byte[] productImage;


}
