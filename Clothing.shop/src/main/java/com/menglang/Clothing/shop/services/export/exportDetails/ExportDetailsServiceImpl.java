package com.menglang.Clothing.shop.services.export.exportDetails;

import com.menglang.Clothing.shop.dto.export.details.ExportDetailsRequest;
import com.menglang.Clothing.shop.entity.ColorEntity;
import com.menglang.Clothing.shop.entity.ExportDetailsEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.SizeEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.helpers.GetEntitiesById;
import com.menglang.Clothing.shop.repositories.ExportDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExportDetailsServiceImpl implements ExportDetailsService {
    @Autowired
    private final ExportDetailsRepository exportDetailsRepository;

    @Autowired
    private final GetEntitiesById getEntity;

    @Override
    public ExportDetailsEntity create(ExportDetailsRequest data) throws Exception {
        ColorEntity color = getEntity.findColorById(data.color());
        SizeEntity size = getEntity.findSizeById(data.size());
        ProductEntity product = getEntity.findProductById(data.product());

        return ExportDetailsEntity.builder()
                .color(color)
                .size(size)
                .product(product)
                .quantity(data.quantity())
                .build();
    }

    @Override
    public ExportDetailsEntity update(Long id, ExportDetailsRequest data) throws Exception {
        ExportDetailsEntity details = findExportDetailsById(id);
        ColorEntity color = getEntity.findColorById(data.color());
        SizeEntity size = getEntity.findSizeById(data.size());
        ProductEntity product = getEntity.findProductById(data.product());

        details.setColor(color);
        details.setSize(size);
        details.setProduct(product);

        return details;
    }

    private ExportDetailsEntity findExportDetailsById(Long id) throws Exception {
        return exportDetailsRepository.findById(id).orElseThrow(() -> new CustomMessageException("Import details not found", "404"));
    }
}
