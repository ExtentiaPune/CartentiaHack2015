package com.extentia.cartentia.common;

/**
 * Created by Extentia on 8/29/2015.
 */
public class Constants {

    public static final String NOTIFICATION_PATH = "/cartentia/hackevent";
    public static final String NOTIFICATION_TITLE = "title";
    public static final String NOTIFICATION_CONTENT = "content";
    public static final String ACTION_DISMISS = "com.extentia.cartentia.DISMISS";

    public static class Url {
        public static final String HOST_URL = "http://121.243.26.108:8001/api/";
        public static final String IMAGE_HOST_URL = "http://121.243.26.108:8001/";
        public static final String LOGIN_URL = HOST_URL + "users?username=%s&password=%s&populate=role";
        public static final String GET_PRODUCT_URL = HOST_URL + "products/";
        public static final String GET_ORDER_HISTORY_URL = HOST_URL + "orders?userID=%s&statusID=%s";
        public static final String GET_MY_CART = HOST_URL + "cart?&populate=productID";
        public static final String PLACE_ORDER = "http://121.243.26.108:8001/placeorder";
        public static final String ADD_CART = "http://121.243.26.108:8001/cart";

    }

    public static class Keys{
        public static final String PRODUCTID_KEY = "productId";

    }

}
