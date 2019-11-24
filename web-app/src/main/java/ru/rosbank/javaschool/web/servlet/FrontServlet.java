package ru.rosbank.javaschool.web.servlet;

import ru.rosbank.javaschool.util.SQLTemplate;
import ru.rosbank.javaschool.web.constant.Constants;
import ru.rosbank.javaschool.web.dto.ProductDto;
import ru.rosbank.javaschool.web.model.*;
import ru.rosbank.javaschool.web.repository.*;
import ru.rosbank.javaschool.web.service.AdminMcDonaldsService;
import ru.rosbank.javaschool.web.service.UserMcDonaldsService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

public class FrontServlet extends HttpServlet {

    private UserMcDonaldsService userMcDonaldsService;
    private AdminMcDonaldsService adminMcDonaldsService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String url = req.getRequestURI().substring(req.getContextPath().length());

        if (url.equals("/")) {
            int orderId = (Integer) req.getSession().getAttribute(Constants.ORDERID);
            int id = Integer.parseInt(req.getParameter(Constants.ID));
            String category = req.getParameter(Constants.CATEGORY);
            int quantity = Integer.parseInt(req.getParameter(Constants.QUANTITY));
            userMcDonaldsService.order(orderId, category, id, quantity);
            resp.sendRedirect(url);
        }

        if (url.equals("/order")) {
            if (req.getParameter(Constants.ACTION).equals(Constants.BUY)) {
                int orderId = (Integer) req.getSession().getAttribute(Constants.ORDERID);
                for (int i = 0; i < req.getParameterValues(Constants.MODELID).length; i++) {
                    userMcDonaldsService.updateOrderPositionModel(
                            orderId,
                            Integer.parseInt(req.getParameterValues(Constants.MODELID)[i]),
                            req.getParameterValues(Constants.CATEGORY)[i],
                            Integer.parseInt(req.getParameterValues(Constants.ID)[i]),
                            Integer.parseInt(req.getParameterValues(Constants.QUANTITY)[i])
                    );
                }
                userMcDonaldsService.paidOrder(orderId);

                req.getSession().invalidate();
                HttpSession session = req.getSession();
                if (session.isNew()) {
                    int newOrderId = userMcDonaldsService.createOrder();
                    session.setAttribute(Constants.ORDERID, newOrderId);
                }
                req.setAttribute(Constants.ORDERID, orderId);
                req.getRequestDispatcher("/WEB-INF/payment-successful.jsp").forward(req, resp);
            }
            if (req.getParameter(Constants.ACTION).startsWith((Constants.DELETE))) {
                System.out.println(Constants.ACTION);
                System.out.println(Constants.DELETE.length());
                System.out.println(req.getParameter(Constants.ACTION).substring(Constants.DELETE.length()));

                int orderPositionModel = Integer.parseInt(req.getParameter(Constants.ACTION).substring(Constants.DELETE.length()));
                userMcDonaldsService.removeOrderPositionModelById(orderPositionModel);
                resp.sendRedirect(url);
            }
        }


        if (url.equals("/admin")) {
            // getParameter - POST (BODY FORM)
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            int priceRub = Integer.parseInt(req.getParameter("priceRub"));
            String description = req.getParameter("description");
            String imageUrl = req.getParameter("imageUrl");
            adminMcDonaldsService.save(new ProductModel(id, name, priceRub, description, imageUrl) {
                @Override
                public String getCategory() {
                    return null;
                }

                @Override
                public ProductDto toDto() {
                    return null;
                }
            });
            resp.sendRedirect(url);
        }
    }




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String url = req.getRequestURI().substring(req.getContextPath().length());

        if (url.equals("/")) {
            HttpSession session = req.getSession();
            if (session.isNew()) {
                int orderId = userMcDonaldsService.createOrder();
                session.setAttribute(Constants.ORDERID, orderId);
            }

            int orderId = (Integer) session.getAttribute(Constants.ORDERID);
            req.setAttribute(Constants.ORDERITEMS, userMcDonaldsService.getAllOrderPosition(orderId));
            req.setAttribute(Constants.DTOPRODUCTS, userMcDonaldsService.getAllProductDto());
            req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
        }

        if (url.equals("/order")) {
            int orderId = (Integer) req.getSession().getAttribute(Constants.ORDERID);
            req.setAttribute(Constants.ORDERITEMS, userMcDonaldsService.getAllOrderPosition(orderId));
            req.getRequestDispatcher("/WEB-INF/order.jsp").forward(req, resp);
        }

        if (url.equals("/detail-information")) {
            int productId = Integer.parseInt(req.getParameter(Constants.ID));
            String category = req.getParameter(Constants.CATEGORY);
            req.setAttribute(Constants.PRODUCT, userMcDonaldsService.getById(category, productId));
            req.getRequestDispatcher("/WEB-INF/detail-information.jsp").forward(req, resp);
        }



        if (url.startsWith("/admin")) {
            // TODO: work with admin panel
            req.setAttribute(Constants.DTOPRODUCTS, adminMcDonaldsService.getAllProductModel());
            req.getRequestDispatcher("/WEB-INF/admin/frontpage.jsp").forward(req, resp);
        }

        if (url.startsWith("/admin/edit")) {
                int id = Integer.parseInt(req.getParameter("id"));
                String category = req.getParameter(Constants.CATEGORY);
                req.setAttribute(Constants.ITEM, adminMcDonaldsService.getById(category, id));
                req.setAttribute(Constants.DTOPRODUCTS, adminMcDonaldsService.getAllProductModel());
                req.getRequestDispatcher("/WEB-INF/admin/frontpage.jsp").forward(req, resp);
            }
        }



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

            userMcDonaldsService = new UserMcDonaldsService(sandwichRepository, friesRepository, drinkRepository, orderRepository, orderPositionRepository);
            adminMcDonaldsService = new AdminMcDonaldsService(sandwichRepository, friesRepository, drinkRepository, orderRepository, orderPositionRepository);

            insertInitialData(sandwichRepository);
            insertInitialData(friesRepository);
            insertInitialData(drinkRepository);

        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    private void insertInitialData(DrinkRepository drinkRepository) {
        drinkRepository.save(new DrinkModel(0, "Cola", 100, "cola",  "https://images-na.ssl-images-amazon.com/images/I/81mEIp4PMBL._SL1500_.jpg", 500));
        drinkRepository.save(new DrinkModel(0, "Sprite", 130, "sprite", "https://s.winestyle.ru/images_gen/533/53336/0_0_orig.jpg", 500));
        drinkRepository.save(new DrinkModel(0, "Water", 60, "just water ", "http://6408888.ru/wp-content/uploads/2018/01/bottled222.jpg", 500));
    }

    private void insertInitialData(SandwichRepository sandwichRepository) {
        sandwichRepository.save(new SandwichModel(0, "Bigmac", 300, "just a burger", "http://primebeef.ru/images/cms/data/img_3911.jpg"));
        sandwichRepository.save(new SandwichModel(0, "Burger", 250, "not just a burger", "https://the-challenger.ru/wp-content/uploads/2016/10/foodnetwork.com_breakfast_burger-800x533.jpg"));
    }

    private void insertInitialData(FriesRepository friesRepository) {
        friesRepository.save(new FriesModel(0, "Fries", 120, "potato", "https://www.gastronom.ru/binfiles/images/20180212/b7adad13.jpg", "Standard"));
        friesRepository.save(new FriesModel(0, "Fries", 120, "potato", "https://www.gastronom.ru/binfiles/images/20180212/b7adad13.jpg", "Big"));
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
    public void destroy() {
        log("destroy");
    }
}
