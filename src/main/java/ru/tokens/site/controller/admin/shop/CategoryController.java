package ru.tokens.site.controller.admin.shop;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.shop.Category;
import ru.tokens.site.entities.shop.Product;
import ru.tokens.site.services.shop.CategoryService;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("admin/shop/category")
public class CategoryController {

    private static final Logger log = LogManager.getLogger("CategoryController");

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addCategory(Map<String, Object> model) {
        model.put("categoryForm", new CategoryForm());
        return "shop/category/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ModelAndView addCategory(Map<String, Object> model, CategoryForm form) {
        String categoryName = form.getName();
        String message;
        if (!this.categoryService.checkCatNameForDuplications(categoryName)) {
            message = "Please use another name for category.";
            model.put("message", message);
            return new ModelAndView("shop/category/add");
        }
        Category category = new Category(categoryName);
        this.categoryService.create(category);

        return new ModelAndView(new RedirectView("/admin/shop/category/list", true, false));

    }

    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String listCategory(Map<String, Object> model) {
        List<Category> categories = this.categoryService.getAll();
        model.put("categories", categories);
        return "shop/category/list";
    }

    @RequestMapping(value = "edit/{categoryId:\\d+}", method = RequestMethod.GET)
    public String editCategory(Map<String, Object> model,
            @PathVariable("categoryId") long categoryId) {
        Category category = this.categoryService.find(categoryId);
        CategoryForm form = new CategoryForm();
        form.setName(category.getCategoryName());
        model.put("categoryForm", form);
        return "shop/category/edit";
    }

    @RequestMapping(value = "edit/{categoryId:\\d+}", method = RequestMethod.POST)
    public ModelAndView editCategory(Map<String, Object> model,
            @PathVariable("categoryId") long categoryId,
            CategoryForm form) {
        String message;
        String newCatName = form.getName();
        Category category = this.categoryService.find(categoryId);

        if (newCatName.equals(category.getCategoryName())) {
            return new ModelAndView(new RedirectView("/admin/shop/category/list", true, false));
        }

        if (!this.categoryService.checkCatNameForDuplications(newCatName)) {
            message = "Please use another name for category.";
            form.setName(category.getCategoryName());
            model.put("message", message);
            model.put("categoryForm", form);
            return new ModelAndView("shop/category/edit");
        }

        category.setCategoryName(newCatName);
        this.categoryService.create(category);

        return new ModelAndView(new RedirectView("/admin/shop/category/list", true, false));
    }

    @RequestMapping(value = "delete/{categoryId:\\d+}", method = RequestMethod.GET)
    public ModelAndView deleteCategory(Map<String, Object> model,
            @PathVariable("categoryId") long categoryId) {

        String message;
        Category category = this.categoryService.find(categoryId);
        if (!category.getItems().isEmpty()) {
            message = "Category is not empty! Remove items from category than try again.";
            List<Category> categories = this.categoryService.getAll();
            model.put("categories", categories);
            model.put("message", message);
            return new ModelAndView("shop/category/list");

        }
        this.categoryService.delete(categoryId);
        message = "Category successfully deleted.";
        model.put("message", message);
        return new ModelAndView(new RedirectView("/admin/shop/category/list", true, false));
    }

    @RequestMapping(value = "view/{categoryId:\\d+}", method = RequestMethod.GET)
    public String viewCategory(Map<String, Object> model,
            @PathVariable("categoryId") long categoryId) {
        Category category = this.categoryService.find(categoryId);
        List<Product> products = category.getItems();
        model.put("category", category);
        model.put("products", products);
        return "shop/category/view";
    }

    public static class CategoryForm {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
