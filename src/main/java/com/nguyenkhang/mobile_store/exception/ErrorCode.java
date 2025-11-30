package com.nguyenkhang.mobile_store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    NOT_FOUND_API_PATH(4040, "The requested API path does not exist!", HttpStatus.NOT_FOUND),
    UNAUTHORIZED(6666, "You don't have permission!", HttpStatus.FORBIDDEN),
    INTERNAL_ERROR(7777, "Internal server error!", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(8888, "Unauthenticated!", HttpStatus.UNAUTHORIZED),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception error!", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1112, "User existed!", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1111, "Invalid message key!", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1113, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1114, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1115, "Email existed!", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1116, "User not existed!", HttpStatus.BAD_REQUEST),
    USER_CAN_NOT_DELETE(1116, "User can not delete!", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS(1117, "Invalid username or password!", HttpStatus.UNAUTHORIZED),
    INVALID_OLD_PASSWORD(1117, "Invalid old password!", HttpStatus.UNAUTHORIZED),
    CONFIRM_PASSWORD_NOT_MATCH(1117, "New password and confirm password don't match!!", HttpStatus.BAD_REQUEST),

    ROLE_EXISTED(1118, "Role existed!", HttpStatus.BAD_REQUEST),
    BRAND_NOT_EXISTED(1119, "Brand not existed!", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_NUMBER(
            1122, "Invalid phone number (must start with 0 or +84 and be 10 digits)", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_REQUIRED(1123, "Phone number  is required!", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_EXISTED(1124, "Phone number existed!", HttpStatus.BAD_REQUEST),

    BRAND_NAME_REQUIRED(1123, "Brand name is required!", HttpStatus.BAD_REQUEST),
    DESCRIPTION_REQUIRED(1501, "Description is required!", HttpStatus.BAD_REQUEST),

    CATEGORY_NAME_REQUIRED(1123, "Category name is required!", HttpStatus.BAD_REQUEST),

    INVALID_EMAIL(1122, "Invalid email!", HttpStatus.BAD_REQUEST),
    EMAIL_REQUIRED(1123, "Email is required!", HttpStatus.BAD_REQUEST),

    CATEGORY_NOT_EXISTED(1125, "Category not existed!", HttpStatus.BAD_REQUEST),
    CATEGORY_EXISTED(1125, "Category already existed!", HttpStatus.BAD_REQUEST),

    VOUCHER_NAME_REQUIRED(1200, "Voucher name is required!", HttpStatus.BAD_REQUEST),
    VOUCHER_CODE_REQUIRED(1201, "Voucher code is required!", HttpStatus.BAD_REQUEST),
    VOUCHER_DISCOUNT_INVALID(1202, "Discount value must be greater than 0!", HttpStatus.BAD_REQUEST),
    VOUCHER_MIN_PRICE_INVALID(1203, "Minimum applicable price cannot be negative!", HttpStatus.BAD_REQUEST),
    VOUCHER_MAX_DISCOUNT_INVALID(1204, "Maximum discount amount cannot be negative!", HttpStatus.BAD_REQUEST),
    VOUCHER_END_DATE_INVALID(1205, "End date must be in the future!", HttpStatus.BAD_REQUEST),
    VOUCHER_EXISTED(1206, "Voucher already exists!", HttpStatus.BAD_REQUEST),
    VOUCHER_NOT_EXISTED(1207, "Voucher not existed!", HttpStatus.BAD_REQUEST),

    POSITION_NOT_EMPTY(1300, "Position cannot be empty!", HttpStatus.BAD_REQUEST),
    HIRE_DATE_NOT_BLANK(1301, "Rental date cannot be blank!", HttpStatus.BAD_REQUEST),
    HIRE_DATE_INVALID(1302, "Hire date cannot be in the future!", HttpStatus.BAD_REQUEST),
    SALARY_INVALID(1303, "Salary must be greater than 0!", HttpStatus.BAD_REQUEST),
    USER_NOT_BLANK(1303, "User must not be left blank!", HttpStatus.BAD_REQUEST),
    STAFF_NOT_EXISTED(1303, "Staff not existed!", HttpStatus.BAD_REQUEST),
    GENDER_REQUIRED(1200, "Gender is required!", HttpStatus.BAD_REQUEST),

    ADDRESS_REQUIRED(1400, "Address is required!", HttpStatus.BAD_REQUEST),
    CUSTOMER_ID_REQUIRED(1401, "Customer id is required!", HttpStatus.BAD_REQUEST),
    CUSTOMER_NOT_EXISTED(1402, "Customer not existed!", HttpStatus.BAD_REQUEST),
    FULL_NAME_REQUIRED(1402, "Full name is required!", HttpStatus.BAD_REQUEST),

    PRODUCT_NAME_NOT_BLANK(1500, "Product name is required!", HttpStatus.BAD_REQUEST),
    PRODUCT_DESCRIPTION_NOT_BLANK(1501, "Product description is required!", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_NULL(1502, "Category is required!", HttpStatus.BAD_REQUEST),
    BRAND_NOT_NULL(1503, "Brand is required!", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_EXISTED(1504, "Product not existed!", HttpStatus.BAD_REQUEST),
    PRODUCT_VARIANT_NOT_EXISTED(1505, "Product variant not existed!", HttpStatus.BAD_REQUEST),
    PRODUCT_VARIANT_EXISTED(1506, "Product variant already existed!", HttpStatus.BAD_REQUEST),
    SKU_REQUIRED(1507, "Sku is required!", HttpStatus.BAD_REQUEST),
    OPTION_VALUES_REQUIRED(1508, "Option value is required!", HttpStatus.BAD_REQUEST),

    VARIANT_CAN_NOT_DELETE(1116, "Product variant can not delete!", HttpStatus.BAD_REQUEST),
    PRODUCT_CAN_NOT_DELETE(1116, "Product can not delete!", HttpStatus.BAD_REQUEST),


    PRODUCT_REQUIRED(1509, "Product is required!", HttpStatus.BAD_REQUEST),
    PRODUCT_VARIANT_REQUIRED(1508, "Product variant is required!", HttpStatus.BAD_REQUEST),
    VARIANT_IMAGE_FILE_REQUIRED(1508, "Variant image is required!", HttpStatus.BAD_REQUEST),
    MAIN_IMAGE_FILE_REQUIRED(1508, "Product main image is required!", HttpStatus.BAD_REQUEST),


    PRODUCT_PRICE_INVALID(1509, "Product variant price must be a number and greater than 0!", HttpStatus.BAD_REQUEST),
    PRODUCT_PRICE_REQUIRED(1509, "Product variant price is required!", HttpStatus.BAD_REQUEST),
    QUANTITY_INVALID(1510, "Quantity must be a number and greater than or equal 0 ", HttpStatus.BAD_REQUEST),
    QUANTITY_REQUIRED(1511, "Quantity is required! ", HttpStatus.BAD_REQUEST),

    CART_ITEM_QUANTITY_INVALID(1600, "Quantity must be a number and greater than 0 ", HttpStatus.BAD_REQUEST),
    CART_ITEM_NOT_EXIST(1600, "Cart item not existed!", HttpStatus.BAD_REQUEST),
    CART_NOT_EXIST(1602, "Cart not existed!", HttpStatus.BAD_REQUEST),
    CART_CLEAR_ERROR(1602, "An error occurred while deleting the entire cart!", HttpStatus.BAD_REQUEST),

    OPTION_NAME_INVALID(1700, "Option names must contain at least one letter!", HttpStatus.BAD_REQUEST),
    OPTION_VALUE_INVALID(1701, "Option value must contain at least one letter!", HttpStatus.BAD_REQUEST),
    OPTION_ID_REQUIRED(1702, "Option is required!", HttpStatus.BAD_REQUEST),
    OPTION_NOT_EXISTED(1700, "Option not existed!", HttpStatus.BAD_REQUEST),
    OPTION_EXISTED(1700, "Option existed!", HttpStatus.BAD_REQUEST),
    OPTION_VALUE_EXISTED(1700, "Option value existed!", HttpStatus.BAD_REQUEST),
    OPTION_VALUE_NOT_EXISTED(1700, "Option value not existed!", HttpStatus.BAD_REQUEST),

    OPTION_VALUE_EMPTY(2282, "OptionValues cannot be empty for signature!", HttpStatus.BAD_REQUEST),

    RECEIVER_NAME_REQUIRED(1232, "Receiver name is required! ", HttpStatus.BAD_REQUEST),
    PROVINCE_REQUIRED(1313, "Province is required! ", HttpStatus.BAD_REQUEST),
    DISTRICT_REQUIRED(1313, "District is required! ", HttpStatus.BAD_REQUEST),
    WARD_REQUIRED(1313, "Ward is required! ", HttpStatus.BAD_REQUEST),

    // ddoiwj chinh lai message
    ORDER_CREATE_FAIL(6466, "An error occurred while creating the order! ", HttpStatus.BAD_REQUEST),
    ORDER_CONFIRM_FAIL(6466, "An error occurred while confirm the order! ", HttpStatus.BAD_REQUEST),

    PRODUCT_JUST_SOLD_OUT(8756, "The product is currently out of stock. Please try again! ", HttpStatus.CONFLICT),

    ORDER_NOT_EXIST(4577, "Order not existed! ", HttpStatus.BAD_REQUEST),
    ORDER_NOT_CONFIRMED(6885, "Order must be CONFIRMED before it can be updated! ", HttpStatus.BAD_REQUEST),
    ORDER_UPDATE_STATUS_INVALID(6885, "Order must be CONFIRMED before updating!", HttpStatus.BAD_REQUEST),
    ORDER_CONFIRM_STATUS_INVALID(6885, "Order can only be confirmed when status is PENDING!", HttpStatus.BAD_REQUEST),
    ORDER_CHANGE_INFO_STATUS_INVALID(
            6885, "Order information can only be change when status is PENDING!", HttpStatus.BAD_REQUEST),

    ORDER_CANCEL_STATUS_INVALID(6885, "Order can only be canceled when status is PENDING!", HttpStatus.BAD_REQUEST),

    ORDER_ALREADY_CANCELED(6885, "Order has already been canceled!", HttpStatus.BAD_REQUEST),

    INSUFFICIENT_STOCK(6346, "Insufficient inventory! ", HttpStatus.BAD_REQUEST),
    ORDER_COORDINATES_MISSING(6885, "Missing delivery coordinates!", HttpStatus.BAD_REQUEST),
    //

    WAREHOUSE_NOT_EXISTED(4653, "Warehouse not existed! ", HttpStatus.BAD_REQUEST),
    WAREHOUSE_NOT_FOUND(4653, "Warehouse not found! ", HttpStatus.BAD_REQUEST),
    WAREHOUSE_NAME_REQUIRED(9294, "Warehouse name is required!", HttpStatus.BAD_REQUEST),
    WAREHOUSE_PRIORITY_REQUIRED(9294, "Warehouse priority is required!", HttpStatus.BAD_REQUEST),
    PRIORITY_ALREADY_EXISTED(9294, "Warehouse priority already existed!", HttpStatus.BAD_REQUEST),

    SUPPLIER_NAME_REQUIRED(3382, "Supplier name is required!", HttpStatus.BAD_REQUEST),
    SUPPLIER_NOT_EXISTED(4653, "Supplier not existed! ", HttpStatus.BAD_REQUEST),

    PURCHASE_ORDER_NOT_EXISTED(4653, "Purchase order not existed! ", HttpStatus.BAD_REQUEST),
    STATUS_IS_NOT_CHANGE_TO_APPROVED(
            4653, "Purchase order status NOT change when status is APPROVED or CANCELLED!", HttpStatus.BAD_REQUEST),
    STATUS_IS_NOT_CHANGE_TO_CANCEL(
            4653, "Purchase order status NOT change when status is CANCELLED!", HttpStatus.BAD_REQUEST),
    DELETE_FAIL(4653, "Purchase order can't delete when status is APPROVED or CANCELLED!", HttpStatus.BAD_REQUEST),

    INVENTORY_NOT_EXISTED(4653, "Inventory not existed! ", HttpStatus.BAD_REQUEST),
    INVENTORY_TRANSACTION_NOT_EXISTED(4653, "Inventory transaction not existed! ", HttpStatus.BAD_REQUEST),

    CONCURRENT_MODIFICATION(
            3645,
            "Inventory record was updated by another transaction. Please retry your operation.",
            HttpStatus.CONFLICT),

    INVALID_GROUP_BY(4653, "Group by invalid! ", HttpStatus.BAD_REQUEST),

    IMAGE_UPLOAD_FAIL(4653, "Image upload failed!", HttpStatus.BAD_REQUEST),
    IMAGE_DELETE_FAIL(4653, "Image delete failed!", HttpStatus.BAD_REQUEST),

    PAYMENT_NOT_EXISTED(4653, "Payment not existed! ", HttpStatus.BAD_REQUEST),

    PRICE_REQUIRED(1509, "Price is required!", HttpStatus.BAD_REQUEST),
    PRICE_INVALID(1509, "Price must be a number and greater than 0!", HttpStatus.BAD_REQUEST),
    WAREHOUSE_REQUIRED(9294, "Warehouse is required!", HttpStatus.BAD_REQUEST),
    SUPPLIER_REQUIRED(9294, "Supplier is required!", HttpStatus.BAD_REQUEST),

    TOTAL_PRICE_INVALID(1509, "Total price must be a number and greater than 0!", HttpStatus.BAD_REQUEST),
    TOTAL_PRICE_REQUIRED(1509, "Total price is required!", HttpStatus.BAD_REQUEST),
    ;

    int code;
    String message;
    HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
