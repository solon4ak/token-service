package ru.tokens.site.services.shop;

import org.springframework.stereotype.Service;

/**
 *
 * @author solon4ak
 */
@Service
public class ShopValidator {

    public boolean validateQuantity(String productId, String quantity) {
        
        boolean errorFlag = false;
        if (!productId.isEmpty() && !quantity.isEmpty()) {
            int i = -1;

            try {
                i = Integer.parseInt(quantity);
            } catch (NumberFormatException nfe) {
                System.out.println("User did not enter a number in the quantity field");
            }

            if (i < 0 || i > 99) {

                errorFlag = true;
            }
        }
        return errorFlag;
    }
}
