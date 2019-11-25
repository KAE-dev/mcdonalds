package ru.rosbank.javaschool.web.servlet;

import ru.rosbank.javaschool.util.SQLTemplate;
import ru.rosbank.javaschool.web.model.*;
import ru.rosbank.javaschool.web.repository.*;
import ru.rosbank.javaschool.web.service.UserService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

import static ru.rosbank.javaschool.web.constant.Constants.*;

public class FrontServlet extends HttpServlet {

    private UserService userService;


    @Override
    public void init() {
        log("Init");
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/db");
            SQLTemplate sqlTemplate = new SQLTemplate();

            SandwichRepository sandwichRepository = new SandwichRepositoryJdbcImpl(dataSource, sqlTemplate);
            FriesRepository friesRepository = new FriesRepositoryJdbcImpl(dataSource, sqlTemplate);
            DrinkRepository drinkRepository = new DrinkRepositoryJdbcImpl(dataSource, sqlTemplate);

            OrderRepository orderRepository = new OrderRepositoryJdbcImpl(dataSource, sqlTemplate);
            OrderPositionRepository orderPositionRepository = new OrderPositionRepositoryJdbcImpl(dataSource, sqlTemplate);

            userService = new UserService(sandwichRepository, friesRepository, drinkRepository, orderRepository, orderPositionRepository);
            insertInitialData(sandwichRepository);
            insertInitialData(friesRepository);
            insertInitialData(drinkRepository);

        } catch (NamingException e) {
            e.printStackTrace();
        }
    }


    private void insertInitialData(SandwichRepository sandwichRepository) {
        sandwichRepository.save(new SandwichModel(0, "Bigmac", 300, "just a burger", "http://primebeef.ru/images/cms/data/img_3911.jpg"));
        sandwichRepository.save(new SandwichModel(0, "Burger", 250, "not just a burger", "https://the-challenger.ru/wp-content/uploads/2016/10/foodnetwork.com_breakfast_burger-800x533.jpg"));
    }

    private void insertInitialData(FriesRepository friesRepository) {
        friesRepository.save(new FriesModel(0, "Fries", 120, "potato", "https://www.gastronom.ru/binfiles/images/20180212/b7adad13.jpg", "Standard"));
        friesRepository.save(new FriesModel(0, "Fries", 120, "potato", "https://www.gastronom.ru/binfiles/images/20180212/b7adad13.jpg", "Big"));
    }

    private void insertInitialData(DrinkRepository drinkRepository) {
        drinkRepository.save(new DrinkModel(0, "Cola", 100, "cola", "https://static.ruvita.ru/store/product/365x365_1529bf23477e7a4b8207a954a439fe4e.jpg", 500));
        drinkRepository.save(new DrinkModel(0, "Sprite", 130, "sprite", "https://goldapple.ru/media/catalog/product/cache/fb5d06f7acfb2f26f85333624ccbfb5e/0/7/072ae55a2a249e2465cc229d77bf2f06.jpg", 500));
        drinkRepository.save(new DrinkModel(0, "Water", 60, "just water ", "http://6408888.ru/wp-content/uploads/2018/01/bottled222.jpg", 500));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equals("GET")) {
            doGet(req, resp);
        }
        if (req.getMethod().equals("POST")) {
            doPost(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String url = req.getRequestURI().substring(req.getContextPath().length());

        if (url.equals("/")) {
            HttpSession session = req.getSession();
            if (session.isNew()) {
                int orderId = userService.createOrder();
                session.setAttribute(ORDERID, orderId);
            }

            int orderId = (Integer) session.getAttribute(ORDERID);
            req.setAttribute(ORDERITEMS, userService.getAllOrderPosition(orderId));
            req.setAttribute(DTOPRODUCTS, userService.getAllProductDto());
            req.getRequestDispatcher("/WEB-INF/start.jsp").forward(req, resp);
        }

        if (url.equals("/order")) {
            int orderId = (Integer) req.getSession().getAttribute(ORDERID);
            req.setAttribute(ORDERITEMS, userService.getAllOrderPosition(orderId));
            req.getRequestDispatcher("/WEB-INF/order.jsp").forward(req, resp);
        }

        if (url.equals("/detail-information")) {
            int productId = Integer.parseInt(req.getParameter(ID));
            String category = req.getParameter(CATEGORY);
            req.setAttribute(PRODUCT, userService.getById(category, productId));
            req.getRequestDispatcher("/WEB-INF/open-card.jsp").forward(req, resp);
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String url = req.getRequestURI().substring(req.getContextPath().length());

        if (url.equals("/")) {
            int orderId = (Integer) req.getSession().getAttribute(ORDERID);
            int id = Integer.parseInt(req.getParameter(ID));
            String category = req.getParameter(CATEGORY);
            int quantity = Integer.parseInt(req.getParameter(QUANTITY));
            userService.order(orderId, category, id, quantity);
            resp.sendRedirect(url);
        }

        if (url.equals("/order")) {
            if (req.getParameter(ACTION).equals(BUY)) {
                int orderId = (Integer) req.getSession().getAttribute(ORDERID);
                for (int i = 0; i < req.getParameterValues(MODELID).length; i++) {
                    userService.updateOrderPositionModel(
                            orderId,
                            Integer.parseInt(req.getParameterValues(MODELID)[i]),
                            req.getParameterValues(CATEGORY)[i],
                            Integer.parseInt(req.getParameterValues(ID)[i]),
                            Integer.parseInt(req.getParameterValues(QUANTITY)[i])
                    );
                }
                userService.paidOrder(orderId);

                req.getSession().invalidate();
                HttpSession session = req.getSession();
                if (session.isNew()) {
                    int newOrderId = userService.createOrder();
                    session.setAttribute(ORDERID, newOrderId);
                }
                req.setAttribute(ORDERID, orderId);
                req.getRequestDispatcher("/WEB-INF/orderdone.jsp").forward(req, resp);
            }
            if (req.getParameter(ACTION).startsWith(DELETE)) {
                int orderPositionModel = Integer.parseInt(req.getParameter(ACTION).substring(DELETE.length()));
                userService.removeOrderPositionModelById(orderPositionModel);
                resp.sendRedirect(url);
            }
        }
    }
}
