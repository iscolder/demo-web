package com.hw.demoweb.controller;

import com.hw.demoweb.model.Product;
import com.hw.demoweb.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAll());
        return "products";
    }

    @PostMapping(value = "/products")
    public String saveProduct(Model model, @RequestParam String title, @RequestParam Double cost) {
        productService.save(new Product(null, title, cost));
        model.addAttribute("products", productService.getAll());
        return "redirect:/products";
    }

    @PostMapping(value = "/products/{id}")
    public String editProduct(Model model, @PathVariable Long id, @RequestParam String title, @RequestParam Double cost) {
        productService.update(id, new Product(id, title, cost));
        model.addAttribute("products", productService.getAll());
        return "redirect:/products";
    }

    @GetMapping(value = "/products/{id}/cost/{operation}")
    public String editProductCost(Model model, @PathVariable Long id, @PathVariable String operation) {
        productService.changeCost(id, "increment".equals(operation));
        model.addAttribute("products", productService.getAll());
        return "redirect:/products";
    }

    @GetMapping(value = "/products/{id}/delete")
    public String editProduct(Model model, @PathVariable Long id) {
        productService.delete(id);
        model.addAttribute("products", productService.getAll());
        return "redirect:/products";
    }

    @GetMapping(value = "/products/{id}/edit")
    public String getEditProduct(Model model, @PathVariable Long id) {
        model.addAttribute("product", productService.getById(id));
        return "product_edit";
    }

    @GetMapping("/products/{id}")
    public String getProductInfo(Model model, @PathVariable Long id) {
        model.addAttribute("product", productService.getById(id));
        return "product_info";
    }

    @GetMapping("/products/new")
    public String getAddProduct() {
        return "product_new";
    }

}
