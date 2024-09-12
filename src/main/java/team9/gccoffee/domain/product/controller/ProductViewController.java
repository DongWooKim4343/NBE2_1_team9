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
public class ProductViewController {

    private final ProductRepository productRepository;






    //완료됨
    @GetMapping("/product")
    String viewproduts(Model model) {
        List<Product> result = productRepository.findAll();
        model.addAttribute("items", result);
        return "list";
    }

    //수정중
    @GetMapping("/product/{id}")
    String viewproduct(@PathVariable("id") Long id, Model model) {
        Optional<Product> result = productRepository.findByProductId(id);
        model.addAttribute("items", result.get());
        return "listeach";
    }
}
