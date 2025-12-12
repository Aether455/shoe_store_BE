package com.nguyenkhang.mobile_store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    // --- 10xx: SYSTEM & COMMON ERRORS ---
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi hệ thống chưa được định nghĩa!", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_ERROR(1001, "Lỗi nội bộ máy chủ!", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1002, "Message key không hợp lệ!", HttpStatus.BAD_REQUEST),
    NOT_FOUND_API_PATH(1003, "Đường dẫn API không tồn tại!", HttpStatus.NOT_FOUND),
    IMAGE_UPLOAD_FAIL(1004, "Tải ảnh lên thất bại!", HttpStatus.BAD_REQUEST),
    IMAGE_DELETE_FAIL(1005, "Xóa ảnh thất bại!", HttpStatus.BAD_REQUEST),
    INVALID_GROUP_BY(1006, "Tham số Group by không hợp lệ!", HttpStatus.BAD_REQUEST),

    // --- 11xx: AUTHENTICATION & AUTHORIZATION ---
    UNAUTHENTICATED(1100, "Bạn chưa đăng nhập hoặc phiên đăng nhập đã hết hạn!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1101, "Bạn không có quyền truy cập tài nguyên này!", HttpStatus.FORBIDDEN),
    INVALID_CREDENTIALS(1102, "Tên đăng nhập hoặc mật khẩu không chính xác!", HttpStatus.UNAUTHORIZED),
    INVALID_OLD_PASSWORD(1103, "Mật khẩu cũ không chính xác!", HttpStatus.UNAUTHORIZED),
    CONFIRM_PASSWORD_NOT_MATCH(1104, "Mật khẩu xác nhận không khớp!", HttpStatus.BAD_REQUEST),

    // --- 20xx: USER, STAFF & CUSTOMER ---
    // User validation
    USER_EXISTED(2001, "Tài khoản người dùng đã tồn tại!", HttpStatus.CONFLICT),
    USER_NOT_EXISTED(2002, "Người dùng không tồn tại!", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(2003, "Tên đăng nhập phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(2004, "Mật khẩu phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    USERNAME_REQUIRED(2005, "Vui lòng nhập tên đăng nhập!", HttpStatus.BAD_REQUEST),
    PASSWORD_REQUIRED(2006, "Vui lòng nhập mật khẩu!", HttpStatus.BAD_REQUEST),
    USER_CAN_NOT_DELETE(2007, "Không thể xóa người dùng này!", HttpStatus.CONFLICT),
    FULL_NAME_REQUIRED(2008, "Họ và tên là bắt buộc!", HttpStatus.BAD_REQUEST),
    GENDER_REQUIRED(2009, "Giới tính là bắt buộc!", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(2010, "Email này đã được sử dụng!", HttpStatus.CONFLICT),
    INVALID_EMAIL(2011, "Định dạng email không hợp lệ!", HttpStatus.BAD_REQUEST),
    EMAIL_REQUIRED(2012, "Email là bắt buộc!", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_EXISTED(2013, "Số điện thoại này đã được sử dụng!", HttpStatus.CONFLICT),
    INVALID_PHONE_NUMBER(
            2014, "Số điện thoại không hợp lệ (phải bắt đầu bằng 0 hoặc +84 và có 10 chữ số)", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_REQUIRED(2015, "Số điện thoại là bắt buộc!", HttpStatus.BAD_REQUEST),

    // Address
    ADDRESS_REQUIRED(2020, "Địa chỉ là bắt buộc!", HttpStatus.BAD_REQUEST),
    ADDRESS_NOT_FOUND(2021, "Không tìm thấy địa chỉ!", HttpStatus.NOT_FOUND),
    PROVINCE_REQUIRED(2022, "Tỉnh/Thành phố là bắt buộc!", HttpStatus.BAD_REQUEST),
    DISTRICT_REQUIRED(2023, "Quận/Huyện là bắt buộc!", HttpStatus.BAD_REQUEST),
    WARD_REQUIRED(2024, "Phường/Xã là bắt buộc!", HttpStatus.BAD_REQUEST),

    // Staff
    POSITION_NOT_EMPTY(2100, "Vị trí công việc không được để trống!", HttpStatus.BAD_REQUEST),
    HIRE_DATE_NOT_BLANK(2101, "Ngày thuê không được để trống!", HttpStatus.BAD_REQUEST),
    HIRE_DATE_INVALID(2102, "Ngày thuê không thể là ngày trong tương lai!", HttpStatus.BAD_REQUEST),
    SALARY_INVALID(2103, "Lương phải lớn hơn 0!", HttpStatus.BAD_REQUEST),
    STAFF_NOT_EXISTED(2104, "Nhân viên không tồn tại!", HttpStatus.NOT_FOUND),
    CANNOT_DELETE_STAFF_LINKED_USER(
            2105,
            "Không thể xóa nhân viên này do đang liên kết với tài khoản User hoặc dữ liệu khác.",
            HttpStatus.CONFLICT),

    // Customer
    CUSTOMER_ID_REQUIRED(2200, "Mã khách hàng là bắt buộc!", HttpStatus.BAD_REQUEST),
    CUSTOMER_NOT_EXISTED(2201, "Khách hàng không tồn tại!", HttpStatus.NOT_FOUND),
    CANNOT_DELETE_CUSTOMER(2202, "Không thể xóa khách hàng này do có dữ liệu liên quan!", HttpStatus.CONFLICT),

    // Role
    ROLE_EXISTED(2300, "Vai trò (Role) đã tồn tại!", HttpStatus.CONFLICT),

    // --- 30xx: CATALOG (PRODUCT, BRAND, CATEGORY, OPTION) ---
    // Brand
    BRAND_NOT_EXISTED(3001, "Thương hiệu không tồn tại!", HttpStatus.NOT_FOUND),
    BRAND_NAME_REQUIRED(3002, "Tên thương hiệu là bắt buộc!", HttpStatus.BAD_REQUEST),
    CANNOT_DELETE_BRAND(3003, "Không thể xóa thương hiệu này do có sản phẩm liên quan!", HttpStatus.CONFLICT),
    DESCRIPTION_REQUIRED(3004, "Mô tả là bắt buộc!", HttpStatus.BAD_REQUEST),

    // Category
    CATEGORY_NOT_EXISTED(3100, "Danh mục không tồn tại!", HttpStatus.NOT_FOUND),
    CATEGORY_EXISTED(3101, "Danh mục đã tồn tại!", HttpStatus.CONFLICT),
    CATEGORY_NAME_REQUIRED(3102, "Tên danh mục là bắt buộc!", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_NULL(3103, "Danh mục không được để trống!", HttpStatus.BAD_REQUEST),
    CANNOT_DELETE_CATEGORY(
            3104, "Không thể xóa danh mục này do có danh mục con hoặc sản phẩm liên quan!", HttpStatus.CONFLICT),

    // Product & Variant
    PRODUCT_NOT_EXISTED(3200, "Sản phẩm không tồn tại!", HttpStatus.NOT_FOUND),
    PRODUCT_REQUIRED(3201, "Thông tin sản phẩm là bắt buộc!", HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_NOT_BLANK(3202, "Tên sản phẩm không được để trống!", HttpStatus.BAD_REQUEST),
    PRODUCT_DESCRIPTION_NOT_BLANK(3203, "Mô tả sản phẩm không được để trống!", HttpStatus.BAD_REQUEST),
    BRAND_NOT_NULL(3204, "Thương hiệu sản phẩm là bắt buộc!", HttpStatus.BAD_REQUEST),
    MAIN_IMAGE_FILE_REQUIRED(3205, "Ảnh chính của sản phẩm là bắt buộc!", HttpStatus.BAD_REQUEST),
    PRODUCT_CAN_NOT_DELETE(3206, "Không thể xóa sản phẩm này!", HttpStatus.CONFLICT),

    PRODUCT_VARIANT_NOT_EXISTED(3210, "Biến thể sản phẩm không tồn tại!", HttpStatus.NOT_FOUND),
    PRODUCT_VARIANT_EXISTED(3211, "Biến thể sản phẩm đã tồn tại!", HttpStatus.CONFLICT),
    PRODUCT_VARIANT_REQUIRED(3212, "Thông tin biến thể là bắt buộc!", HttpStatus.BAD_REQUEST),
    SKU_REQUIRED(3213, "Mã SKU là bắt buộc!", HttpStatus.BAD_REQUEST),
    VARIANT_IMAGE_FILE_REQUIRED(3214, "Ảnh của biến thể là bắt buộc!", HttpStatus.BAD_REQUEST),
    PRODUCT_PRICE_INVALID(3215, "Giá sản phẩm phải là số và lớn hơn 0!", HttpStatus.BAD_REQUEST),
    PRODUCT_PRICE_REQUIRED(3216, "Giá sản phẩm là bắt buộc!", HttpStatus.BAD_REQUEST),
    QUANTITY_INVALID(3217, "Số lượng phải là số nguyên không âm!", HttpStatus.BAD_REQUEST),
    QUANTITY_REQUIRED(3218, "Số lượng là bắt buộc!", HttpStatus.BAD_REQUEST),
    VARIANT_CAN_NOT_DELETE(3219, "Không thể xóa biến thể này!", HttpStatus.CONFLICT),
    PRODUCT_JUST_SOLD_OUT(3220, "Sản phẩm vừa hết hàng. Vui lòng thử lại!", HttpStatus.CONFLICT),
    INSUFFICIENT_STOCK(3221, "Số lượng tồn kho không đủ!", HttpStatus.BAD_REQUEST),

    // Options (Màu sắc, kích thước...)
    OPTION_NOT_EXISTED(3300, "Thuộc tính tùy chọn không tồn tại!", HttpStatus.NOT_FOUND),
    OPTION_EXISTED(3301, "Thuộc tính tùy chọn đã tồn tại!", HttpStatus.CONFLICT),
    OPTION_ID_REQUIRED(3302, "ID thuộc tính là bắt buộc!", HttpStatus.BAD_REQUEST),
    OPTION_NAME_INVALID(3303, "Tên thuộc tính phải chứa ít nhất một chữ cái!", HttpStatus.BAD_REQUEST),
    CANNOT_DELETE_OPTION(3304, "Không thể xóa thuộc tính này do đang được sử dụng!", HttpStatus.CONFLICT),

    OPTION_VALUE_NOT_EXISTED(3310, "Giá trị tùy chọn không tồn tại!", HttpStatus.NOT_FOUND),
    OPTION_VALUE_EXISTED(3311, "Giá trị tùy chọn đã tồn tại!", HttpStatus.CONFLICT),
    OPTION_VALUES_REQUIRED(3312, "Danh sách giá trị tùy chọn là bắt buộc!", HttpStatus.BAD_REQUEST),
    OPTION_VALUE_INVALID(3313, "Giá trị tùy chọn phải chứa ít nhất một chữ cái!", HttpStatus.BAD_REQUEST),
    OPTION_VALUE_EMPTY(3314, "Giá trị tùy chọn không được rỗng!", HttpStatus.BAD_REQUEST),
    CANNOT_DELETE_OPTION_VALUE(3315, "Không thể xóa giá trị này do đang được sử dụng!", HttpStatus.CONFLICT),

    // --- 40xx: ORDER & CART ---
    // Cart
    CART_NOT_EXIST(4001, "Giỏ hàng không tồn tại!", HttpStatus.NOT_FOUND),
    CART_ITEM_NOT_EXIST(4002, "Sản phẩm trong giỏ hàng không tồn tại!", HttpStatus.NOT_FOUND),
    CART_ITEM_QUANTITY_INVALID(4003, "Số lượng sản phẩm trong giỏ phải lớn hơn 0!", HttpStatus.BAD_REQUEST),
    CART_CLEAR_ERROR(4004, "Có lỗi xảy ra khi xóa giỏ hàng!", HttpStatus.INTERNAL_SERVER_ERROR),

    // Order
    ORDER_NOT_EXIST(4100, "Đơn hàng không tồn tại!", HttpStatus.NOT_FOUND),
    ORDER_CREATE_FAIL(4101, "Có lỗi xảy ra khi tạo đơn hàng!", HttpStatus.INTERNAL_SERVER_ERROR),
    ORDER_CONFIRM_FAIL(4102, "Có lỗi xảy ra khi xác nhận đơn hàng!", HttpStatus.INTERNAL_SERVER_ERROR),
    ORDER_NOT_CONFIRMED(4103, "Đơn hàng phải được XÁC NHẬN trước khi cập nhật!", HttpStatus.BAD_REQUEST),

    // Order State Validations
    ORDER_UPDATE_STATUS_INVALID(4104, "Chỉ có thể cập nhật khi đơn hàng đã được xác nhận!", HttpStatus.BAD_REQUEST),
    ORDER_CONFIRM_STATUS_INVALID(
            4105, "Chỉ có thể xác nhận đơn hàng khi trạng thái là PENDING (Chờ xử lý)!", HttpStatus.BAD_REQUEST),
    ORDER_CHANGE_INFO_STATUS_INVALID(
            4106, "Chỉ có thể thay đổi thông tin khi đơn hàng đang PENDING (Chờ xử lý)!", HttpStatus.BAD_REQUEST),
    ORDER_CANCEL_STATUS_INVALID(
            4107, "Chỉ có thể hủy đơn hàng khi trạng thái là PENDING (Chờ xử lý)!", HttpStatus.BAD_REQUEST),
    ORDER_ALREADY_CANCELED(4108, "Đơn hàng đã bị hủy trước đó!", HttpStatus.BAD_REQUEST),
    DUPLICATE_ORDER_STATUS(4109, "Trạng thái đơn hàng trùng lặp, không cần cập nhật!", HttpStatus.BAD_REQUEST),

    ORDER_COORDINATES_MISSING(4110, "Thiếu tọa độ giao hàng!", HttpStatus.BAD_REQUEST),
    RECEIVER_NAME_REQUIRED(4111, "Tên người nhận là bắt buộc!", HttpStatus.BAD_REQUEST),
    ORDER_ALREADY_CONFIRMED(4108, "Đơn hàng đã hoàn thành. Không thể cập nhật!", HttpStatus.BAD_REQUEST),


    // Payment
    PAYMENT_NOT_EXISTED(4200, "Phương thức thanh toán không tồn tại!", HttpStatus.NOT_FOUND),

    // --- 50xx: VOUCHER & PROMOTION ---
    VOUCHER_NOT_EXISTED(5000, "Voucher không tồn tại!", HttpStatus.NOT_FOUND),
    VOUCHER_EXISTED(5001, "Voucher đã tồn tại!", HttpStatus.CONFLICT),
    VOUCHER_NAME_REQUIRED(5002, "Tên voucher là bắt buộc!", HttpStatus.BAD_REQUEST),
    VOUCHER_CODE_REQUIRED(5003, "Mã voucher là bắt buộc!", HttpStatus.BAD_REQUEST),
    VOUCHER_DISCOUNT_INVALID(5004, "Giá trị giảm giá phải lớn hơn 0!", HttpStatus.BAD_REQUEST),
    VOUCHER_MIN_PRICE_INVALID(5005, "Giá trị đơn hàng tối thiểu không được là số âm!", HttpStatus.BAD_REQUEST),
    VOUCHER_MAX_DISCOUNT_INVALID(5006, "Số tiền giảm tối đa không được là số âm!", HttpStatus.BAD_REQUEST),
    VOUCHER_END_DATE_INVALID(5007, "Ngày kết thúc phải lớn hơn thời điểm hiện tại!", HttpStatus.BAD_REQUEST),
    CANNOT_DELETE_VOUCHER_LINKED_ORDER(
            5008, "Không thể xóa voucher do đã được sử dụng trong đơn hàng!", HttpStatus.CONFLICT),

    // --- 60xx: INVENTORY, WAREHOUSE & SUPPLIER ---
    // Warehouse
    WAREHOUSE_NOT_EXISTED(6001, "Kho hàng không tồn tại!", HttpStatus.NOT_FOUND),
    WAREHOUSE_NAME_REQUIRED(6002, "Tên kho hàng là bắt buộc!", HttpStatus.BAD_REQUEST),
    WAREHOUSE_PRIORITY_REQUIRED(6003, "Độ ưu tiên kho là bắt buộc!", HttpStatus.BAD_REQUEST),
    PRIORITY_ALREADY_EXISTED(6004, "Độ ưu tiên kho này đã tồn tại!", HttpStatus.CONFLICT),
    CANNOT_DELETE_WAREHOUSE(6005, "Không thể xóa kho hàng do có dữ liệu liên quan!", HttpStatus.CONFLICT),
    WAREHOUSE_REQUIRED(6006, "Thông tin kho hàng là bắt buộc!", HttpStatus.BAD_REQUEST),

    // Supplier
    SUPPLIER_NOT_EXISTED(6100, "Nhà cung cấp không tồn tại!", HttpStatus.NOT_FOUND),
    SUPPLIER_NAME_REQUIRED(6101, "Tên nhà cung cấp là bắt buộc!", HttpStatus.BAD_REQUEST),
    SUPPLIER_REQUIRED(6102, "Thông tin nhà cung cấp là bắt buộc!", HttpStatus.BAD_REQUEST),
    CANNOT_DELETE_SUPPLIER_LINKED_PURCHASE_ORDER(
            6103,
            "Không thể xóa nhà cung cấp do đang liên kết với phiếu nhập hoặc dữ liệu hoạt động!",
            HttpStatus.CONFLICT),

    // Purchase Order (Nhập hàng)
    PURCHASE_ORDER_NOT_EXISTED(6200, "Phiếu nhập hàng không tồn tại!", HttpStatus.NOT_FOUND),
    STATUS_IS_NOT_CHANGE_TO_APPROVED(
            6201, "Không thể thay đổi trạng thái khi phiếu nhập đã DUYỆT hoặc HỦY!", HttpStatus.BAD_REQUEST),
    STATUS_IS_NOT_CANCEL_TO_COMPLETED(6202, "Không thể hủy khi phiếu nhập đã HOÀN THÀNH!", HttpStatus.BAD_REQUEST),
    DELETE_FAIL_BY_STATUS(
            6203, "Không thể xóa phiếu nhập khi trạng thái là ĐÃ DUYỆT hoặc ĐÃ HỦY!", HttpStatus.BAD_REQUEST),
    PRICE_REQUIRED(6204, "Giá nhập là bắt buộc!", HttpStatus.BAD_REQUEST),
    PRICE_INVALID(6205, "Giá nhập phải là số và lớn hơn 0!", HttpStatus.BAD_REQUEST),
    TOTAL_PRICE_REQUIRED(6206, "Tổng tiền là bắt buộc!", HttpStatus.BAD_REQUEST),
    TOTAL_PRICE_INVALID(6207, "Tổng tiền phải là số và lớn hơn 0!", HttpStatus.BAD_REQUEST),
    STATUS_IS_NOT_CHANGE_TO_COMPLETED(
            6208, "Không thể thay đổi trạng thái khi phiếu nhập chưa duyệt hoặc đã bị hủy!", HttpStatus.BAD_REQUEST),
    DUPLICATE_STATUS(6029, "Trạng thái phiếu nhập trùng lặp!", HttpStatus.BAD_REQUEST),
    PURCHASE_ORDER_CAN_NOT_DELETE(6010, "Không thể xóa  phiếu nhập này!", HttpStatus.CONFLICT),

    // Inventory Transaction
    INVENTORY_NOT_EXISTED(6300, "Thông tin tồn kho không tồn tại!", HttpStatus.NOT_FOUND),
    INVENTORY_TRANSACTION_NOT_EXISTED(6301, "Giao dịch kho không tồn tại!", HttpStatus.NOT_FOUND),
    CONCURRENT_MODIFICATION(
            6302, "Dữ liệu kho đã bị thay đổi bởi giao dịch khác. Vui lòng thử lại!", HttpStatus.CONFLICT),
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
