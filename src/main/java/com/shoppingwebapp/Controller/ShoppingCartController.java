//package com.shoppingwebapp.Controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.shoppingwebapp.Dao.ProductRepository;
//import com.shoppingwebapp.Entity.Item;
//
//@CrossOrigin(allowCredentials = "true", origins = "http://localhost:5173", allowedHeaders = "http://localhost:5173")
//@RestController
//@RequestMapping(path = "/shoppingcart")
//public class ShoppingCartController {
//    @Autowired
//    private ProductRepository productRepository;
//
//    @PostMapping(path = "/add")
//    public String add(@RequestParam Integer id, @RequestParam Integer quantity, HttpSession session) {
//        if (session.getAttribute("cart") == null) {
//            List<Item> cart = new ArrayList<Item>();
//            cart.add(new Item(productRepository.findById(id).get(), quantity));
//            session.setAttribute("cart", cart);
//        } else {
//            List<Item> cart = (List<Item>) session.getAttribute("cart");
//            int index = this.exists(id, cart);
//            if (index == -1) {
//                cart.add(new Item(productRepository.findById(id).get(), quantity));
//            } else {
//                int newQuantity = cart.get(index).getQuantity() + quantity;
//                cart.get(index).setQuantity(newQuantity);
//            }
//            session.setAttribute("cart", cart);
//        }
//        return "Success!";
//    }
//
//    @PostMapping(path = "/remove")
//    public String remove(@RequestParam Integer id, HttpSession session) {
//        List<Item> cart = (List<Item>) session.getAttribute("cart");
//        int index = this.exists(id, cart);
//        if (index == -1) {
//            return "Fail!";
//        }
//        cart.remove(index);
//        session.setAttribute("cart", cart);
//        return "Success!";
//    }
//
//    @PostMapping(path = "/showShoppingCart")
//    public List<Item> showShoppingCart(HttpSession session) {
//        List<Item> cart = (List<Item>) session.getAttribute("cart");
//        return cart;
//    }
//
//    private int exists(Integer id, List<Item> cart) {
//        for (int i = 0; i < cart.size(); i++) {
//            if (cart.get(i).getProduct().getId() == id) {
//                return i;
//            }
//        }
//        return -1;
//    }
//}
