package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.services.Storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/admin/file")
@RestController
@RequiredArgsConstructor
public class FileController {

    private final StorageService storageService;


}
