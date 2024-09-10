package team9.gccoffee.domain.product.controller;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team9.gccoffee.domain.product.domain.Product;
import team9.gccoffee.domain.product.repository.ProductRepository;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    //수정 안됨
    @PostMapping("/api/v1/product")
    String addproducts(Model model) {

        return "redirect:/list";
    }

    //완료됨
    @GetMapping("/api/v1/product")
    String viewproduts(Model model) {
        List<Product> result = productRepository.findAll();
        model.addAttribute("items", result);
        return "list";
    }

    //수정중
    @GetMapping("/api/v1/product/{id}")
    String viewproduct(@PathVariable("id") Long id, Model model) {
        Optional<Product> result = productRepository.findByproductId(id);
        System.out.println(id);
        System.out.println(result.get());
//        model.addAttribute("items", result);

        return "listeach";
    }
}
