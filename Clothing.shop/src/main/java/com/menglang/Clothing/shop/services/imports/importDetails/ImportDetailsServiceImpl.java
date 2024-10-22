package com.menglang.Clothing.shop.services.imports.importDetails;

import com.menglang.Clothing.shop.dto.imports.details.ImportDetailsRequest;
import com.menglang.Clothing.shop.entity.ColorEntity;
import com.menglang.Clothing.shop.entity.ImportDetailsEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.SizeEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.helpers.GetEntitiesById;
import com.menglang.Clothing.shop.repositories.ImportDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImportDetailsServiceImpl implements ImportDetailsService {

    @Autowired
    private final ImportDetailsRepository importDetailsRepository;

    @Autowired
    private final GetEntitiesById getEntity;


    @Override
    public ImportDetailsEntity create(ImportDetailsRequest data) throws Exception {

        ColorEntity color = getEntity.findColorById(data.color());
        SizeEntity size = getEntity.findSizeById(data.size());
        ProductEntity product = getEntity.findProductById(data.product());

        return ImportDetailsEntity.builder()
                .importCost(data.importCost())
                .color(color)
                .size(size)
                .product(product)
                .quantity(data.quantity())
                .build();
    }

    @Override
    public ImportDetailsEntity update(Long id, ImportDetailsRequest data) throws Exception {
        ImportDetailsEntity details = findImportDetailsById(id);
        ColorEntity color = getEntity.findColorById(data.color());
        SizeEntity size = getEntity.findSizeById(data.size());
        ProductEntity product = getEntity.findProductById(data.product());

        details.setColor(color);
        details.setSize(size);
        details.setProduct(product);
        details.setImportCost(data.importCost());

        return details;
    }

    private void validateProduct(Long pid, Long colorId, Long sizeId) throws Exception {
        ProductEntity product = getEntity.findProductById(pid);
        boolean isExistSize = product.getSizes().stream().anyMatch(s -> s.getId().equals(sizeId));
        boolean isExistColor = product.getSizes().stream().anyMatch(s -> s.getId().equals(colorId));
        if (!isExistColor) throw new CustomMessageException("Color Not found", "404");
        if (!isExistSize) throw new CustomMessageException("Size Not found", "404");

    }

    private ImportDetailsEntity findImportDetailsById(Long id) throws Exception {
        return importDetailsRepository.findById(id).orElseThrow(() -> new CustomMessageException("Import details not found", "404"));
    }

}
