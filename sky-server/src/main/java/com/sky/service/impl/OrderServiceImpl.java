package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.AddressBook;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.exception.OrderBusinessException;
import com.sky.mapper.*;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.service.ShoppingCartService;
import com.sky.vo.DishVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private UserMapper userMapper;


    @Transactional
    @Override
    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO) {

        //1.オーダーオブジェクトを作成し、numberを作る、金額を算出
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO, orders);
        //現時点のミリ秒の値を取得
        Long currentTimeMillis = System.currentTimeMillis();

        //それをnumberとして設置する
        String number = currentTimeMillis.toString();
        orders.setNumber(number);

        //2.userIdと作成時間を代入
        orders.setUserId(BaseContext.getCurrentId());
        orders.setOrderTime(LocalDateTime.now());

        //3.オーダー状態や支払い状態を設置する
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setPayStatus(Orders.UN_PAID);

        //3.5電話番号、ユーザーネームとユーザーidを挿入
        Long addressBookId = ordersSubmitDTO.getAddressBookId();
        AddressBook addressBook = addressBookMapper.getById(addressBookId);
        orders.setUserId(BaseContext.getCurrentId());
        //orders.setAddress(addressBook.getDetail());
        orders.setPhone(addressBook.getPhone());
        orders.setUserName(addressBook.getConsignee());


        //4.表に挿入、idを返す
        orderMapper.save(orders);

        //5.ショッピングカートの内容を注文詳細にコピー（orderIdの内容を忘れないように）
        List<ShoppingCart> shoppingCarts = shoppingCartService.list();
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (ShoppingCart shoppingCart : shoppingCarts) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(shoppingCart, orderDetail);

            //orderIdを挿入
            orderDetail.setOrderId(orders.getId());
            orderDetails.add(orderDetail);
        }

        orderDetailMapper.add(orderDetails);

        //6.ショッピングカートの内容を消す
        shoppingCartService.clean();


        //7.orderVOオブジェクトを作成、オーダーナンバーを代入
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .orderTime(orders.getOrderTime())
                .orderNumber(number)
                .orderAmount(orders.getAmount())
                .id(orders.getId()).build();


        return orderSubmitVO;
    }

    @Override
    public PageResult getHistoryOrders(Integer page, Integer pageSize, Integer status) {
        //1.pageHelperを起動
        PageHelper.startPage(page, pageSize);
        OrdersPageQueryDTO ordersPageQueryDTO = new OrdersPageQueryDTO();
        ordersPageQueryDTO.setStatus(status);
        ordersPageQueryDTO.setUserId(BaseContext.getCurrentId());

        //2.dataを取得

        //orderListを取得
        List<OrderVO> list = new ArrayList<>();

        Page<Orders> pages = orderMapper.pageQuery(ordersPageQueryDTO);
        if (pages != null && pages.getTotal() > 0)
            for (Orders orders : pages.getResult()) {
                Long orderId = orders.getId();

                //orderDetails取得
                List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(orderId);

                //orderListにorderDetailを挿入
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orders, orderVO);
                orderVO.setOrderDetailList(orderDetailList);

                //listに挿入
                list.add(orderVO);
            }


        //3.pageResultに代入
        return new PageResult(pages.getTotal(), list);
    }

    @Override
    public OrderVO getOrderDetail(Long id) {
        //1.order表から調べる
        OrderVO orderVO = orderMapper.getById(id);

        //2.orderDetailList表から調べる
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(id);

        //3.orderVOにorderDetailに代入
        orderVO.setOrderDetailList(orderDetailList);

        return orderVO;
    }

    @Override
    public void cancelOrder(Long id) {
        //1.注文存在するかどうか検証
        OrderVO orderVO = orderMapper.getById(id);
        if (orderVO == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }

        //2.存在するなら、注文はすでに始まっているかどうか確認
        if (orderVO.getStatus() > 2) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        //3. 注文状態を更新
        Orders orders = new Orders();
        BeanUtils.copyProperties(orderVO, orders);

        //キャンセル原因を追加
        orders.setCancelReason("お客様にキャンセルされた");

        //キャンセル時間を追加
        orders.setCancelTime(LocalDateTime.now());

        //キャンセル状態に設定
        orders.setStatus(Orders.CANCELLED);

        orderMapper.update(orders);
    }

    @Override
    @Transactional
    public void repetition(Long id) {
        //1.注文情報を取得
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(id);

        //2.カートを空にする
        shoppingCartService.clean();

        //3.カートに挿入

        List<ShoppingCart> shoppingCartList =
                orderDetailList.stream().map(orderDetail -> {
                    //orderDetailオブジェクトをshoppingCartオブジェクトにする
                    ShoppingCart shoppingCart = new ShoppingCart();
                    BeanUtils.copyProperties(orderDetail, shoppingCart);
                    //ユーザーIDを代入
                    shoppingCart.setUserId(BaseContext.getCurrentId());
                    //作成時間を代入
                    shoppingCart.setCreateTime(LocalDateTime.now());
                    return shoppingCart;
                }).collect(Collectors.toList());

        //shoppingCart表に挿入
        shoppingCartMapper.insertBatch(shoppingCartList);
    }

    @Override
    public PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {

        //1.pageHelperを起動
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());

        //2.dataを取得
        Page<Orders> page = orderMapper.pageQuery(ordersPageQueryDTO);


        List<OrderVO> list = page.getResult().stream().map(x -> {
            //orderDishsを取得
            List<String> orderDishList= new ArrayList<>();

            Long orderId = x.getId();
            List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(orderId);
            for (OrderDetail orderDetail : orderDetailList) {

               String orderDish = orderDetail.getName() + "*" + orderDetail.getNumber() + ";";
           orderDishList.add(orderDish);
            }

           String orderDishes  = String.join("",orderDishList);

            //orderVOに値を代入
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(x, orderVO);
            orderVO.setOrderDishes(orderDishes);

            //アドレスを代入
            AddressBook addressBook = addressBookMapper.getById(orderVO.getAddressBookId());
            String address =
//                      addressBook.getProvinceName()
//                    + addressBook.getCityName()
//                    + addressBook.getDistrictName()
                     addressBook.getDetail();
            orderVO.setAddress(address);

            return orderVO;
        }).collect(Collectors.toList());


        return new PageResult(page.getTotal(), list);
    }

    @Override
    public OrderStatisticsVO countByStatus() {

        Integer toBeConfirmed = orderMapper.countByStatus(Orders.TO_BE_CONFIRMED);
        Integer confirmed = orderMapper.countByStatus(Orders.CONFIRMED);
        Integer deliveryInProgress = orderMapper.countByStatus(Orders.DELIVERY_IN_PROGRESS);


        return OrderStatisticsVO.builder()
                .toBeConfirmed(toBeConfirmed)
                .confirmed(confirmed)
                .deliveryInProgress(deliveryInProgress)
                .build();
    }
}
